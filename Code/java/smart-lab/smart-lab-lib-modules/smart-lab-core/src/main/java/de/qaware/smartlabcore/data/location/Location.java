package de.qaware.smartlabcore.data.location;

import de.qaware.smartlabcore.data.device.entity.DeviceId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder    // TODO: Needed?
@NoArgsConstructor
@AllArgsConstructor
public class Location implements ILocation {

    private LocationId id;
    private String name;
    private Collection<DeviceId> deviceIds;
}
