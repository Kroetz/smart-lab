package de.qaware.smartlabcore.data.device.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor // TODO: Really needed for objects being able to serialize/deserialize?
@AllArgsConstructor
public class Device implements IDevice {

    // TODO: Fachliche Datentypen statt String-IDs
    private String id;
    private String type;
    private String name;
    private String responsibleDelegate;
}
