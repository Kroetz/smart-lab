package de.qaware.smartlabdata.converter.workgroup;

import de.qaware.smartlabaction.action.actor.projectbase.info.generic.IKnowledgeBaseInfoFactory;
import de.qaware.smartlabcore.data.generic.IDtoConverter;
import de.qaware.smartlabcore.data.generic.IResolver;
import de.qaware.smartlabcore.data.workgroup.IKnowledgeBaseInfo;
import de.qaware.smartlabcore.data.workgroup.ProjectBaseInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProjectBaseInfoConverter implements IDtoConverter<IKnowledgeBaseInfo, ProjectBaseInfoDto> {

    private final IResolver<String, IKnowledgeBaseInfoFactory> knowledgeBaseInfoFactoryResolver;

    public ProjectBaseInfoConverter(IResolver<String, IKnowledgeBaseInfoFactory> knowledgeBaseInfoFactoryResolver) {
        this.knowledgeBaseInfoFactoryResolver = knowledgeBaseInfoFactoryResolver;
    }

    @Override
    public ProjectBaseInfoDto toDto(IKnowledgeBaseInfo projectBaseInfo) {
        return ProjectBaseInfoDto.builder()
                .serviceId(projectBaseInfo.getServiceId())
                .projectBaseProperties(projectBaseInfo.getKnowledgeBaseProperties())
                .build();
    }

    @Override
    public IKnowledgeBaseInfo toEntity(ProjectBaseInfoDto projectBaseInfo) {
        // TODO: Exception message and type
        IKnowledgeBaseInfoFactory factory = this.knowledgeBaseInfoFactoryResolver
                .resolve(projectBaseInfo.getServiceId())
                .orElseThrow(RuntimeException::new);
        return factory.newInstance(projectBaseInfo.getProjectBaseProperties());
    }
}
