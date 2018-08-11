package de.qaware.smartlab.core.constant;

public abstract class Property {

    public static abstract class Prefix {
        public static final String MODULARITY = "smart-lab.app.";
        public static final String EVENT_MANAGEMENT_REPOSITORY = "smart-lab.event-management";
        public static final String WINDOW_HANDLING = "smart-lab.delegate.window-handling";
        public static final String POWER_POINT = "smart-lab.actuator.power-point";
        public static final String SELENIUM = "smart-lab.actuator.selenium";
        public static final String GITHUB = "smart-lab.actuator.github";
    }

    public static abstract class Name {
        public static final String MODULARITY = "modularity";
        public static final String EVENT_MANAGEMENT_REPOSITORY = "repository";
        public static final String WINDOW_HANDLING = "enabled";
        public static final String POWER_POINT = "enabled";
        public static final String SELENIUM = "enabled";
        public static final String GITHUB = "enabled";
    }

    public static abstract class Value {
        public static final String TRUE = "true";
        public static final String FALSE = "false";
        public static abstract class Modularity {
            public static final String MICROSERVICE = "microservice";
            public static final String MONOLITH = "monolith";
        }
        public static abstract class EventManagementRepository {
            public static final String MOCK = "mock";
            public static final String GOOGLE_CALENDAR = "googleCalendar";
        }
    }
}
