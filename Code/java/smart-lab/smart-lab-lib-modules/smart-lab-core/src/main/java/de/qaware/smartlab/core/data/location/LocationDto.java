package de.qaware.smartlab.core.data.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.qaware.smartlab.core.data.device.DeviceId;
import de.qaware.smartlab.core.data.generic.IDto;
import lombok.*;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationDto implements IDto {

    private LocationId id;
    private String name;
    private Collection<DeviceId> deviceIds;
}
