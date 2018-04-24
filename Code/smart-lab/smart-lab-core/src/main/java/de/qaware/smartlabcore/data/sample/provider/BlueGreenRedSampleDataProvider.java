package de.qaware.smartlabcore.data.sample.provider;

import de.qaware.smartlabcore.data.sample.factory.ISampleDataFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile(BlueGreenRedSampleDataProvider.PROFILE_NAME)
public class BlueGreenRedSampleDataProvider extends AbstractSampleDataProvider {

    public static final String PROFILE_NAME = "blueGreenRedSampleData";

    public BlueGreenRedSampleDataProvider(
            ISampleDataFactory coastGuardDataFactory,
            ISampleDataFactory forestRangersDataFactory,
            ISampleDataFactory fireFightersDataFactory) {
        super(coastGuardDataFactory, forestRangersDataFactory, fireFightersDataFactory);
    }
}
