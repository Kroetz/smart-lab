package de.qaware.smartlab.actuator.adapter.adapters.projectbase.service.github;

import com.jcabi.github.*;
import com.jcabi.http.Request;
import com.jcabi.http.response.JsonResponse;
import com.jcabi.http.wire.RetryWire;
import de.qaware.smartlab.actuator.adapter.adapters.generic.AbstractActuatorAdapter;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.info.github.GithubInfo;
import de.qaware.smartlab.actuator.adapter.adapters.projectbase.service.generic.IProjectBaseAdapter;
import de.qaware.smartlab.core.data.workgroup.IProjectBaseInfo;
import de.qaware.smartlab.core.exception.actuator.ActuatorException;
import de.qaware.smartlab.core.filesystem.ITempFileManager;
import lombok.extern.slf4j.Slf4j;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import static de.qaware.smartlab.core.util.StringUtils.removeLineBreaks;
import static java.lang.String.format;

@Slf4j
public class GithubAdapter extends AbstractActuatorAdapter implements IProjectBaseAdapter {

    public static final String ACTUATOR_TYPE = "github";
    private static final boolean HAS_LOCAL_API = false;

    private static final String REST_ENDPOINT_TEMPLATE_CONTENTS = "/repos/%s/%s/contents/%s";
    private static final String REST_ENDPOINT_TEMPLATE_BLOBS = "/repos/%s/%s/git/blobs/%s";
    private static final String REST_ENDPOINT_TEMPLATE_INVITATIONS = "/user/repository_invitations/%s";
    private static final String REST_ENDPOINT_INVITATIONS = "/user/repository_invitations";
    private static final String JSON_PROPERTY_ID = "id";
    private static final String JSON_PROPERTY_NAME = "name";
    private static final String JSON_PROPERTY_EMAIL = "email";
    private static final String JSON_PROPERTY_MESSAGE = "message";
    private static final String JSON_PROPERTY_PATH = "path";
    private static final String JSON_PROPERTY_COMMITTER = "committer";
    private static final String JSON_PROPERTY_CONTENT = "content";
    private static final String JSON_PROPERTY_SHA = "sha";

    private final Github github;
    private final String githubCommitterName;
    private final String githubCommitterEmail;
    private final ITempFileManager tempFileManager;
    private final Path downloadsTempFileSubDir;

    public GithubAdapter(
            String githubApiKey,
            String githubCommitterName,
            String githubCommitterEmail,
            ITempFileManager tempFileManager,
            Path downloadsTempFileSubDir) {
        super(ACTUATOR_TYPE, HAS_LOCAL_API);
        this.github = new RtGithub(new RtGithub(githubApiKey).entry().through(RetryWire.class));
        this.githubCommitterName = githubCommitterName;
        this.githubCommitterEmail = githubCommitterEmail;
        this.tempFileManager = tempFileManager;
        this.downloadsTempFileSubDir = downloadsTempFileSubDir;
    }

    @Override
    public void upload(
            IProjectBaseInfo projectBaseInfo,
            String uploadMessage,
            String dir,
            String fileName,
            byte[] dataToUpload) throws ActuatorException {
        String path = dir + "/" + fileName;
        try {
            acceptPendingRepoInvitations();
            JsonObject committer = Json.createObjectBuilder()
                    .add(JSON_PROPERTY_NAME, this.githubCommitterName)
                    .add(JSON_PROPERTY_EMAIL, this.githubCommitterEmail).build();
            GithubInfo githubInfo;
            try {
                githubInfo = (GithubInfo) projectBaseInfo;
            }
            catch(ClassCastException e) {
                String errorMessage = format(
                        "The project base info %s must be of the type %s",
                        projectBaseInfo.toString(),
                        GithubInfo.class.getName());
                log.error(errorMessage);
                throw new ActuatorException(errorMessage, e);
            }
            Repo repository = this.github.repos().get(new Coordinates.Simple(
                    githubInfo.getUser(),
                    githubInfo.getRepository()));
            JsonObject jsonObject = Json.createObjectBuilder()
                    .add(JSON_PROPERTY_MESSAGE, uploadMessage)
                    .add(JSON_PROPERTY_PATH, path)
                    .add(JSON_PROPERTY_COMMITTER, committer)
                    .add(JSON_PROPERTY_CONTENT, Base64.getEncoder().encodeToString(dataToUpload))
                    .build();
            repository.contents().create(jsonObject);
        }
        catch(Exception e) {
            String errorMessage = format("Could not upload data to file %s", path);
            log.error(errorMessage);
            throw new ActuatorException(errorMessage, e);
        }
    }

    /**
     * This method uses the blobs API rather than the contents API since the latter only supports downloading files
     * up to a size of 1MB. The caveat is that the blobs API requires a SHA code to identify a file.
     * See:
     * https://medium.com/@caludio/how-to-download-large-files-from-github-4863a2dbba3b
     * https://developer.github.com/v3/git/blobs/#get-a-blob
     * https://developer.github.com/v3/repos/contents/#get-contents
     */
    @Override
    public Path download(
            IProjectBaseInfo projectBaseInfo,
            String filePath) throws ActuatorException {
        try {
            acceptPendingRepoInvitations();
            GithubInfo githubInfo = (GithubInfo) projectBaseInfo;
            String sha = getFileSha(githubInfo, Paths.get(filePath));
            String requestUrl = format(REST_ENDPOINT_TEMPLATE_BLOBS, githubInfo.getUser(), githubInfo.getRepository(), sha);
            JsonObject response = this.github.entry()
                    .uri().path(requestUrl).back()
                    .method(Request.GET)
                    .fetch()
                    .as(JsonResponse.class)
                    .json()
                    .readObject();
            // For some reason the Base64 data returned from jcabi-github contains line breaks that must be removed before decoding the string.
            byte[] data = Base64.getDecoder().decode(removeLineBreaks(response.getString(JSON_PROPERTY_CONTENT)));
            return this.tempFileManager.saveToTempFile(this.downloadsTempFileSubDir, data);
        }
        // Assertion errors are thrown by jcabi when the file to download does not exist
        catch(Exception | AssertionError e) {
            String errorMessage = format("Could not download file %s", filePath);
            log.error(errorMessage);
            throw new ActuatorException(errorMessage, e);
        }
    }

    private String getFileSha(GithubInfo githubInfo, Path filePath) throws IllegalArgumentException, IOException {
        String fileName = filePath.getFileName().toString();
        String folderPath = filePath.getParent().toString();
        String requestUrl = format(REST_ENDPOINT_TEMPLATE_CONTENTS, githubInfo.getUser(), githubInfo.getRepository(), folderPath);
        JsonArray fileInfos = this.github.entry()
                .uri().path(requestUrl).back()
                .method(Request.GET)
                .fetch()
                .as(JsonResponse.class)
                .json()
                .readArray();
        for (int i = 0; i < fileInfos.size(); i++) {
            JsonObject fileInfo = fileInfos.getJsonObject(i);
            if(fileInfo.getString(JSON_PROPERTY_NAME).equals(fileName)) return fileInfo.getString(JSON_PROPERTY_SHA);
        }
        throw new IllegalArgumentException(format(
                "The file \"%s\" in the Github repository \"%s\" of user \"%s\" does not exist",
                filePath,
                githubInfo.getRepository(),
                githubInfo.getUser()));
    }

    private Set<String> getPendingRepoInvitationIds() throws IOException {
        JsonArray invitations = github.entry()
                .uri().path(REST_ENDPOINT_INVITATIONS).back()
                .method(Request.GET)
                .fetch()
                .as(JsonResponse.class)
                .json()
                .readArray();
        Set<String> invitationIds = new HashSet<>();
        for (int i = 0; i < invitations.size(); i++) {
            JsonObject invitation = invitations.getJsonObject(i);
            invitationIds.add(String.valueOf(invitation.getInt(JSON_PROPERTY_ID)));
        }
        return invitationIds;
    }

    private void acceptPendingRepoInvitations() throws IOException {
        acceptRepoInvitations(getPendingRepoInvitationIds());
    }

    private void acceptRepoInvitation(String invitationId) throws IOException {
        this.github.entry()
                .uri().path(format(REST_ENDPOINT_TEMPLATE_INVITATIONS, invitationId)).back()
                .method(Request.PATCH)
                .fetch();
    }

    private void acceptRepoInvitations(Set<String> invitationIds) throws IOException {
        for(String invitationId : invitationIds) {
            acceptRepoInvitation(invitationId);
        }
    }
}
