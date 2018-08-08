package de.qaware.smartlab.actuator.adapter.adapters.projectbase.service.github;

import com.jcabi.github.*;
import com.jcabi.http.Request;
import com.jcabi.http.Response;
import com.jcabi.http.response.JsonResponse;
import com.jcabi.http.wire.RetryWire;
import de.qaware.smartlab.actuator.adapter.adapters.generic.AbstractActuatorAdapter;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.info.github.GithubInfo;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.service.generic.IProjectBaseAdapter;
import de.qaware.smartlab.core.data.workgroup.IProjectBaseInfo;
import de.qaware.smartlab.core.exception.ServiceFailedException;
import de.qaware.smartlab.core.filesystem.ITempFileManager;
import de.qaware.smartlab.core.miscellaneous.Property;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@ConditionalOnProperty(
        prefix = Property.Prefix.GITHUB,
        name = Property.Name.GITHUB,
        havingValue = Property.Value.TRUE)
@Slf4j
public class GithubAdapter extends AbstractActuatorAdapter implements IProjectBaseAdapter {

    public static final String ACTUATOR_TYPE = "github";
    private static final boolean HAS_LOCAL_API = false;

    private final Github github;
    private final ITempFileManager tempFileManager;
    private final Path downloadsTempFileSubDir;

    public GithubAdapter(
            // TODO: String literals
            @Qualifier("githubApiKey") String githubApiKey,
            ITempFileManager tempFileManager,
            // TODO: String literals
            @Qualifier("downloadsTempFileSubDir") Path downloadsTempFileSubDir) {
        super(ACTUATOR_TYPE, HAS_LOCAL_API);
        this.github = new RtGithub(new RtGithub(githubApiKey).entry().through(RetryWire.class));
        this.tempFileManager = tempFileManager;
        this.downloadsTempFileSubDir = downloadsTempFileSubDir;
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
            GithubInfo githubInfo = (GithubInfo) projectBaseInfo;
            Repo repository = this.github.repos().get(new Coordinates.Simple(
                    githubInfo.getUser(),
                    githubInfo.getRepository()));
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
            GithubInfo githubInfo = (GithubInfo) projectBaseInfo;
            Repo repository = this.github.repos().get(new Coordinates.Simple(
                    githubInfo.getUser(),
                    githubInfo.getRepository()));
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
