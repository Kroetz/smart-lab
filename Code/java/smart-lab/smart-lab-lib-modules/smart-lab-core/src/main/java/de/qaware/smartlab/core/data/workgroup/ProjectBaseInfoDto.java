package de.qaware.smartlab.core.data.workgroup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.qaware.smartlab.core.data.generic.IDto;
import lombok.*;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectBaseInfoDto implements IDto {

    String serviceId;
    Map<String, String> projectBaseProperties;
}
