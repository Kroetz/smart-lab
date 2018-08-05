package de.qaware.smartlabcore.miscellaneous;

public abstract class Property {

    public static abstract class Prefix {
        public static final String MODULARITY = "smart-lab.app.";
        public static final String MEETING_MANAGEMENT_REPOSITORY = "smart-lab.meeting-management";
        public static final String SPEECH_TO_TEXT_SERVICE = "smart-lab.action.speech-to-text.";
        public static final String WINDOW_HANDLING = "smart-lab.delegate.window-handling";
    }

    public static abstract class Name {
        public static final String MODULARITY = "modularity";
        public static final String MEETING_MANAGEMENT_REPOSITORY = "repository";
        public static final String SPEECH_TO_TEXT_SERVICE = "service";
        public static final String WINDOW_HANDLING = "enabled";
    }

    public static abstract class Value {
        public static final String TRUE = "true";
        public static final String FALSE = "false";
        public static abstract class Modularity {
            public static final String MICROSERVICE = "microservice";
            public static final String MONOLITH = "monolith";
        }
        public static abstract class MeetingManagementRepository {
            public static final String MOCK = "mock";
            public static final String GOOGLE_CALENDAR = "googleCalendar";
        }
        public static abstract class SpeechToTextService {
            public static final String WATSON = "watson";
            public static final String REMEETING = "remeeting";
        }
    }
}
