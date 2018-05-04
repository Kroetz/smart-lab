package de.qaware.smartlabsampledata.provider;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(EmptySampleDataProvider.PROFILE_NAME)
public class EmptySampleDataProvider extends AbstractSampleDataProvider {

    public static final String PROFILE_NAME = "noSampleData";

    public EmptySampleDataProvider() {
        super();
    }
}
