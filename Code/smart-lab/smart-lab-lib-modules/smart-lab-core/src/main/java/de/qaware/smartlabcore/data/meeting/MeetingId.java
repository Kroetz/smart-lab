package de.qaware.smartlabcore.data.meeting;

import de.qaware.smartlabcore.data.generic.AbstractIdentifier;

public class MeetingId extends AbstractIdentifier {

    private MeetingId(String idValue) {
        super(idValue);
    }

    public static MeetingId of(String idValue) {
        return new MeetingId(idValue);
    }
}
