package de.qaware.smartlabcore.data.sample.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile("noSampleData")
public class EmptySampleDataProvider extends AbstractSampleDataProvider {

    public EmptySampleDataProvider() {
        super();
    }
}
