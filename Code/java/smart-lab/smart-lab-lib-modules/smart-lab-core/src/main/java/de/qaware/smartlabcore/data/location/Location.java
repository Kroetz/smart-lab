package de.qaware.smartlabcore.data.location;

import de.qaware.smartlabcore.data.device.entity.DeviceId;
import de.qaware.smartlabcore.data.device.entity.IDevice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location implements ILocation {

    private LocationId id;
    private String name;
    private Collection<DeviceId> deviceIds;
    private Optional<IDevice> minuteTakingDevice;   // TODO: Still needed?

    public Optional<IDevice> getMinuteTakingDevice() {
        return minuteTakingDevice;
    }

    // This small piece of a builder is needed to tell Lombok about default values (see https://reinhard.codes/2016/07/13/using-lomboks-builder-annotation-with-default-values/)
    public static class LocationBuilder {       // TODO: Still needed?
        private Optional<IDevice> minuteTakingDevice = Optional.empty();
    }
}
