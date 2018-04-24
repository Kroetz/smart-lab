package de.qaware.smartlabcore.data.sample.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile(EmptySampleDataProvider.PROFILE_NAME)
public class EmptySampleDataProvider extends AbstractSampleDataProvider {

    public static final String PROFILE_NAME = "noSampleData";

    public EmptySampleDataProvider() {
        super();
    }
}
