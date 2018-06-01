package de.qaware.smartlabcore.miscellaneous;

public abstract class Property {

    public static abstract class Prefix {
        public static final String MODULARITY = "smartLab.app.";
        public static final String SAMPLE_DATA = "smartLab.app.";
        public static final String SPEECH_TO_TEXT_SERVICE = "smartLab.action.speechToText.";
    }

    public static abstract class Name {
        public static final String MODULARITY = "modularity";
        public static final String SAMPLE_DATA = "sampleData";
        public static final String SPEECH_TO_TEXT_SERVICE = "service";
    }

    public static abstract class Value {
        public static abstract class Modularity {
            public static final String MICROSERVICE = "microservice";
            public static final String MONOLITH = "monolith";
        }
        public static abstract class SampleData {
            public static final String NO_SAMPLE_DATA = "noSampleData";
            public static final String BLUE_GREEN_RED_SAMPLE_DATA = "blueGreenRedSampleData";
            public static final String BLUE_GREEN_RED_BLACK_SAMPLE_DATA = "blueGreenRedBlackSampleData";
        }
        public static abstract class SpeechToTextService {
            public static final String WATSON = "watson";
            public static final String REMEETING = "remeeting";
        }
    }
}
