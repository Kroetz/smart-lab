package de.qaware.smartlab.core.data.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.qaware.smartlab.core.data.generic.AbstractIdentifier;
import de.qaware.smartlab.core.data.location.LocationId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class EventId extends AbstractIdentifier {

    /*
    The event ID class is composed of an arbitrary name part and the location of the event. This is required because
    some event-management repository implementations (e.g. Google calendar) only have unique event IDs on a PER
    CALENDAR (i.e. per location in the terms of Smart Lab) basis. That means that calls to the API of such an repository
    implementation generally require an event ID and a location/calendar ID. To prevent adopting the API of Smart Lab to
    the API of some external provider the location ID was embedded into the event ID class.
     */

    private static final String ID_PART_DELIMITER = "_";

    private final String nameIdPart;
    private final LocationId locationIdPart;

    private EventId(String composedIdValue) {
        super(composedIdValue);
        String[] idParts = composedIdValue.split(ID_PART_DELIMITER);
        if(idParts.length != 2) throw new IllegalArgumentException("The specified ID string must be decomposable into 2 parts");
        this.nameIdPart = idParts[0];
        this.locationIdPart = LocationId.of(idParts[1]);
    }

    private EventId(String nameIdPart, LocationId locationIdPart) {
        super(nameIdPart + ID_PART_DELIMITER + locationIdPart.getIdValue());
        this.nameIdPart = nameIdPart;
        this.locationIdPart = locationIdPart;
    }

    @JsonCreator
    public static EventId of(@JsonProperty(ID_VALUE_FIELD_NAME) String composedIdValue) {
        return new EventId(composedIdValue);
    }

    public static EventId of(String nameIdPart, LocationId locationIdPart) {
        return new EventId(nameIdPart, locationIdPart);
    }
}
