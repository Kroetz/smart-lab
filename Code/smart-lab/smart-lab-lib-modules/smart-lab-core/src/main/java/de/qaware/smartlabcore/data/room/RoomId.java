package de.qaware.smartlabcore.data.room;

import de.qaware.smartlabcore.data.generic.AbstractIdentifier;

public class RoomId extends AbstractIdentifier {

    private RoomId(String idValue) {
        super(idValue);
    }

    public static RoomId of(String idValue) {
        return new RoomId(idValue);
    }
}
