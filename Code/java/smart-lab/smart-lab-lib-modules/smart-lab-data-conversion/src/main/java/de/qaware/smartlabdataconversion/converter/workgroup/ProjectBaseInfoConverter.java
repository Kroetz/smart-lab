package de.qaware.smartlabdataconversion.converter.workgroup;

import de.qaware.smartlabactuatoradapter.actuator.projectbase.info.generic.IProjectBaseInfoFactory;
import de.qaware.smartlabcore.data.generic.IDtoConverter;
import de.qaware.smartlabcore.data.generic.IResolver;
import de.qaware.smartlabcore.data.workgroup.IProjectBaseInfo;
import de.qaware.smartlabcore.data.workgroup.ProjectBaseInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProjectBaseInfoConverter implements IDtoConverter<IProjectBaseInfo, ProjectBaseInfoDto> {

    /*
     * TODO: Map fields automatically without breaking the immutability of the entity classes
     * Maybe http://modelmapper.org/ is appropriate for this task
     */

    private final IResolver<String, IProjectBaseInfoFactory> projectBaseInfoFactoryResolver;

    public ProjectBaseInfoConverter(IResolver<String, IProjectBaseInfoFactory> projectBaseInfoFactoryResolver) {
        this.projectBaseInfoFactoryResolver = projectBaseInfoFactoryResolver;
    }

    @Override
    public ProjectBaseInfoDto toDto(IProjectBaseInfo projectBaseInfo) {
        return ProjectBaseInfoDto.builder()
                .serviceId(projectBaseInfo.getServiceId())
                .projectBaseProperties(projectBaseInfo.getProjectBaseProperties())
                .build();
    }

    @Override
    public IProjectBaseInfo toEntity(ProjectBaseInfoDto projectBaseInfo) {
        IProjectBaseInfoFactory factory = this.projectBaseInfoFactoryResolver.resolve(projectBaseInfo.getServiceId());
        return factory.newInstance(projectBaseInfo.getProjectBaseProperties());
    }
}
