package de.qaware.smartlabcore.miscellaneous;

public class MeetingConfigurationLanguage {

    // TODO: The following constants are code duplicates that also occur in the ANTLR grammar for the meeting configuration language. Is there a way to eliminate these duplicates?
    public static final String SIGNAL_SEQUENCE = "@@@";
    public static final String CONFIG_TAG_BEGIN = SIGNAL_SEQUENCE + "smart-lab-config-begin";
    public static final String CONFIG_TAG_END = SIGNAL_SEQUENCE + "smart-lab-config-end";
    public static final String WORKGROUP_TAG = SIGNAL_SEQUENCE + "workgroup";
    public static final String AGENDA_TAG_BEGIN = SIGNAL_SEQUENCE + "agenda-begin";
    public static final String AGENDA_TAG_END = SIGNAL_SEQUENCE + "agenda-end";
    public static final String ASSISTANCES_TAG_BEGIN = SIGNAL_SEQUENCE + "assistances-begin";
    public static final String ASSISTANCES_TAG_END = SIGNAL_SEQUENCE + "assistances-end";
}
