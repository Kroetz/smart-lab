package de.qaware.smartlabcommons.data.room;

import de.qaware.smartlabcommons.data.device.IDevice;
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
public class Room implements IRoom {

    private String id;
    private String name;
    private Collection<String> deviceIds;
    private Optional<IDevice> minuteTakingDevice;

    public Optional<IDevice> getMinuteTakingDevice() {
        return minuteTakingDevice;
    }

    // This small piece of a builder is needed to tell Lombok about default values (see https://reinhard.codes/2016/07/13/using-lomboks-builder-annotation-with-default-values/)
    public static class RoomBuilder {
        private Optional<IDevice> minuteTakingDevice = Optional.empty();
    }
}
