package de.qaware.smartlabcore.data.meeting;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.qaware.smartlabcore.data.generic.AbstractIdentifier;
import de.qaware.smartlabcore.data.location.LocationId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MeetingId extends AbstractIdentifier {

    /*
    The meeting ID class is composed of an arbitrary name part and the location of the meeting. This is required because
    some meeting management repository implementations (e.g. Google calendar) only have unique meeting IDs on a PER
    CALENDAR (i.e. per location in the terms of Smart Lab) basis. That means that calls to the API of such an repository
    implementation generally require an meeting ID and a location/calendar ID. To prevent adopting the API of Smart Lab to
    the API of some external provider the location ID was embedded into the meeting ID class.
     */

    private static final String ID_PART_DELIMITER = "_";

    private final String nameIdPart;
    private final LocationId locationIdPart;

    private MeetingId(String composedIdValue) {
        super(composedIdValue);
        String[] idParts = composedIdValue.split(ID_PART_DELIMITER);
        if(idParts.length != 2) throw new IllegalArgumentException("The specified ID string must be decomposable into 2 parts");
        this.nameIdPart = idParts[0];
        this.locationIdPart = LocationId.of(idParts[1]);
    }

    private MeetingId(String nameIdPart, LocationId locationIdPart) {
        super(nameIdPart + ID_PART_DELIMITER + locationIdPart.getIdValue());
        this.nameIdPart = nameIdPart;
        this.locationIdPart = locationIdPart;
    }

    @JsonCreator
    public static MeetingId of(@JsonProperty(ID_VALUE_FIELD_NAME) String composedIdValue) {
        return new MeetingId(composedIdValue);
    }

    public static MeetingId of(String nameIdPart, LocationId locationIdPart) {
        return new MeetingId(nameIdPart, locationIdPart);
    }
}
