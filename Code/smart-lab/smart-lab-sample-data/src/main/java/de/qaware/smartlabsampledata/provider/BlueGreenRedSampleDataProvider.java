package de.qaware.smartlabsampledata.provider;

import de.qaware.smartlabsampledata.factory.ISampleDataFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
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
