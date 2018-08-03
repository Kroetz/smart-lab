package de.qaware.smartlabcore.data.workgroup;

import de.qaware.smartlabcore.data.generic.IDtoConverter;
import de.qaware.smartlabcore.data.workgroup.dto.ProjectBaseInfoDto;
import de.qaware.smartlabcore.data.workgroup.dto.WorkgroupDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WorkgroupConverter implements IDtoConverter<IWorkgroup, WorkgroupDto> {

    private final IDtoConverter<IKnowledgeBaseInfo, ProjectBaseInfoDto> projectBaseInfoConverter;

    public WorkgroupConverter(IDtoConverter<IKnowledgeBaseInfo, ProjectBaseInfoDto> projectBaseInfoConverter) {
        this.projectBaseInfoConverter = projectBaseInfoConverter;
    }

    @Override
    public WorkgroupDto toDto(IWorkgroup workgroup) {
        return WorkgroupDto.builder()
                .id(workgroup.getId())
                .name(workgroup.getName())
                .memberIds(workgroup.getMemberIds())
                .projectBaseInfo(this.projectBaseInfoConverter.toDto(workgroup.getKnowledgeBaseInfo()))
                .build();
    }

    @Override
    public IWorkgroup toEntity(WorkgroupDto workgroup) {
        return Workgroup.builder()
                .id(workgroup.getId())
                .name(workgroup.getName())
                .memberIds(workgroup.getMemberIds())
                .knowledgeBaseInfo(this.projectBaseInfoConverter.toEntity(workgroup.getProjectBaseInfo()))
                .build();
    }
}
