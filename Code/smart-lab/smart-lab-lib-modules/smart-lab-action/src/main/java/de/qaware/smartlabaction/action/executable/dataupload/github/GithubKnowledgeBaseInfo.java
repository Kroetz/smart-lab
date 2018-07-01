package de.qaware.smartlabaction.action.executable.dataupload.github;

import com.jcabi.github.Coordinates;
import de.qaware.smartlabcore.data.workgroup.IKnowledgeBaseInfo;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@Slf4j
public class GithubKnowledgeBaseInfo implements IKnowledgeBaseInfo {

    private Coordinates.Simple repository;

    private GithubKnowledgeBaseInfo(Coordinates.Simple repository) {
        this.repository = repository;
    }

    public static GithubKnowledgeBaseInfo of(Coordinates.Simple repository) {
        return new GithubKnowledgeBaseInfo(repository);
    }

    @Override
    public String getServiceId() {
        return GithubService.SERVICE_ID;
    }
}
