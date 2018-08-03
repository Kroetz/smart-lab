package de.qaware.smartlabcore.data.workgroup.dto;

import de.qaware.smartlabcore.data.generic.IDto;
import lombok.*;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ProjectBaseInfoDto implements IDto {

    String serviceId;
    Map<String, String> projectBaseProperties;
}
