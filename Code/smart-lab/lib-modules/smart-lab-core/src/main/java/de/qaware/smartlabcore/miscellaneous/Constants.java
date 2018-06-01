package de.qaware.smartlabcore.miscellaneous;

import java.time.Duration;

public abstract class Constants {

    public static final String MINUTE_TAKING = "minuteTaking";
    public static final String ROOM_UNLOCKING = "roomUnlocking";
    public static final String AGENDA_SHOWING = "agendaShowing";

    public static final String JSON_TYPE_PROPERTY_NAME = "type";

    // TODO: Get from configuration object that loads values from properties
    public static Duration MAXIMAL_MEETING_DURATION = Duration.ofMinutes(480);
    public static Duration DEFAULT_MEETING_EXTENSION = Duration.ofMinutes(1);
}
