package de.qaware.smartlabaction.action.executable.external.github.service;

import com.jcabi.github.Github;
import com.jcabi.github.Repo;
import com.jcabi.github.RtGithub;
import com.jcabi.http.Request;
import com.jcabi.http.Response;
import com.jcabi.http.response.JsonResponse;
import com.jcabi.http.wire.RetryWire;
import de.qaware.smartlabaction.action.executable.external.github.GithubKnowledgeBaseInfo;
import de.qaware.smartlabcore.data.workgroup.IKnowledgeBaseInfo;
import de.qaware.smartlabcore.exception.ServiceFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class GithubService implements IGithubService {

    public static final String SERVICE_ID = "github";
    private Github github;

    public GithubService(String githubApiKey) {
        this.github = new RtGithub(new RtGithub(githubApiKey).entry().through(RetryWire.class));
    }

    @Override
    public String getServiceId() {
        return GithubService.SERVICE_ID;
    }

    @Override
    public void upload(IKnowledgeBaseInfo knowledgeBaseInfo, String dataToUpload) throws ServiceFailedException {
        try {
            acceptPendingRepoInvitations();
            // TODO: Eliminate String literals (get committer name and email from github configuration)
            // TODO: Get path (from assistance config) and commit message as arguments
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
            Path path = Paths.get("minutes/" + LocalDateTime.now().format(formatter) + ".txt");
            String commitMessage = "Upload minutes taken by Smart-Lab";
            JsonObject committer = Json.createObjectBuilder().add("name", "Smart-Lab-Test").add("email", "hanswurst@byom.de").build();
            GithubKnowledgeBaseInfo githubKnowledgeBaseInfo;
            githubKnowledgeBaseInfo = (GithubKnowledgeBaseInfo) knowledgeBaseInfo;
            Repo repository = this.github.repos().get(githubKnowledgeBaseInfo.getRepository());
            JsonObject jsonObject = Json.createObjectBuilder()
                    .add("message", commitMessage)
                    .add("path", path.toString())
                    .add("committer", committer)
                    .add("content", Base64.getEncoder().encodeToString((dataToUpload).getBytes("utf-8")))
                    .build();
            repository.contents().create(jsonObject);
        }
        catch(Exception e) {
            throw new ServiceFailedException(e);
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
