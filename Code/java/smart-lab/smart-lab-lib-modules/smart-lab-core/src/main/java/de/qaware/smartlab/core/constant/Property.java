package de.qaware.smartlab.core.constant;

public abstract class Property {

    public static abstract class Prefix {
        public static final String EVENT_MANAGEMENT_REPOSITORY = "smart-lab.event-management";
    }

    public static abstract class Name {
        public static final String EVENT_MANAGEMENT_REPOSITORY = "repository";
    }

    public static abstract class Value {

        public static abstract class EventManagementRepository {
            public static final String MOCK = "mock";
            public static final String GOOGLE_CALENDAR = "googleCalendar";
        }
    }
}
