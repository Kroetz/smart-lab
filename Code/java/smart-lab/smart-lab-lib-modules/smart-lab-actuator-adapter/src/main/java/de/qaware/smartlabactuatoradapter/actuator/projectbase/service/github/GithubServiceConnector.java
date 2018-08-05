package de.qaware.smartlabactuatoradapter.actuator.projectbase.service.github;

import com.jcabi.github.*;
import com.jcabi.http.Request;
import com.jcabi.http.Response;
import com.jcabi.http.response.JsonResponse;
import com.jcabi.http.wire.RetryWire;
import de.qaware.smartlabactuatoradapter.actuator.projectbase.info.github.GithubProjectBaseInfo;
import de.qaware.smartlabcore.data.workgroup.IProjectBaseInfo;
import de.qaware.smartlabcore.exception.ServiceFailedException;
import de.qaware.smartlabcore.filesystem.ITempFileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;

@Component
@Slf4j
public class GithubServiceConnector implements IGithubService {

    public static final String SERVICE_ID = "github";

    private final Github github;
    private final ITempFileManager tempFileManager;
    private final Path downloadsTempFileSubDir;

    public GithubServiceConnector(
            String githubApiKey,
            ITempFileManager tempFileManager,
            Path downloadsTempFileSubDir) {
        this.github = new RtGithub(new RtGithub(githubApiKey).entry().through(RetryWire.class));
        this.tempFileManager = tempFileManager;
        this.downloadsTempFileSubDir = downloadsTempFileSubDir;
    }

    @Override
    public String getServiceId() {
        return GithubServiceConnector.SERVICE_ID;
    }

    @Override
    public void upload(
            IProjectBaseInfo projectBaseInfo,
            String uploadMessage,
            String dir,
            String fileName,
            String dataToUpload) throws ServiceFailedException {
        String path = dir + "/" + fileName;
        try {
            acceptPendingRepoInvitations();
            // TODO: Eliminate String literals (get committer name and email from github configuration)
            JsonObject committer = Json.createObjectBuilder()
                    .add("name", "Smart-Lab-Test")
                    .add("email", "hanswurst@byom.de").build();
            // TODO: casting smells
            // TODO: Check for casting exception and throw illegalstateexception
            GithubProjectBaseInfo githubProjectBaseInfo = (GithubProjectBaseInfo) projectBaseInfo;
            Repo repository = this.github.repos().get(new Coordinates.Simple(
                    githubProjectBaseInfo.getUser(),
                    githubProjectBaseInfo.getRepository()));
            JsonObject jsonObject = Json.createObjectBuilder()
                    .add("message", uploadMessage)
                    .add("path", path)
                    .add("committer", committer)
                    .add("content", Base64.getEncoder().encodeToString((dataToUpload).getBytes("utf-8")))
                    .build();
            repository.contents().create(jsonObject);
        }
        catch(Exception e) {
            String errorMessage = format("Could not upload data to file %s", path);
            log.error(errorMessage, e);
            throw new ServiceFailedException(errorMessage, e);
        }
    }

    @Override
    public Path download(
            IProjectBaseInfo projectBaseInfo,
            String filePath) throws ServiceFailedException {
        try {
            acceptPendingRepoInvitations();
            GithubProjectBaseInfo githubProjectBaseInfo = (GithubProjectBaseInfo) projectBaseInfo;
            Repo repository = this.github.repos().get(new Coordinates.Simple(
                    githubProjectBaseInfo.getUser(),
                    githubProjectBaseInfo.getRepository()));
            Content file = repository.contents().get(filePath);
            return this.tempFileManager.saveToTempFile(this.downloadsTempFileSubDir, file.raw());
        }
        // Assertion errors are thrown by jcabi when the file to download does not exist
        catch(Exception | AssertionError e) {
            String errorMessage = format("Could not download file %s", filePath);
            log.error(errorMessage, e);
            throw new ServiceFailedException(errorMessage, e);
        }
    }

    private Set<String> getPendingRepoInvitationIds() throws IOException {
        // TODO: eliminate string literals
        JsonArray invitations = github.entry()
                .uri().path("/user/repository_invitations").back()
                .method(Request.GET)
                .fetch()
                .as(JsonResponse.class)
                .json()
                .readArray();
        Set<String> invitationIds = new HashSet<>();
        for (int i = 0; i < invitations.size(); i++) {
            JsonObject invitation = invitations.getJsonObject(i);
            invitationIds.add(String.valueOf(invitation.getInt("id")));
        }
        return invitationIds;
    }

    private void acceptPendingRepoInvitations() throws IOException {
        acceptRepoInvitations(getPendingRepoInvitationIds());
    }

    private void acceptRepoInvitation(String invitationId) throws IOException {
        // TODO: eliminate string literals
        Response response = this.github.entry()
                .uri().path("/user/repository_invitations/" + invitationId).back()
                .method(Request.PATCH)
                .fetch();
    }

    private void acceptRepoInvitations(Set<String> invitationIds) throws IOException {
        for(String invitationId : invitationIds) {
            acceptRepoInvitation(invitationId);
        }
    }
}