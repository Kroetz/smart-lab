package de.qaware.smartlabsampledata.provider;

import de.qaware.smartlabsampledata.factory.ISampleDataFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(BlueGreenRedBlackSampleDataProvider.PROFILE_NAME)
public class BlueGreenRedBlackSampleDataProvider extends AbstractSampleDataProvider {

    public static final String PROFILE_NAME = "blueGreenRedBlackSampleData";

    public BlueGreenRedBlackSampleDataProvider(
            ISampleDataFactory coastGuardDataFactory,
            ISampleDataFactory forestRangersDataFactory,
            ISampleDataFactory fireFightersDataFactory,
            ISampleDataFactory astronautsDataFactory) {
        super(coastGuardDataFactory, forestRangersDataFactory, fireFightersDataFactory, astronautsDataFactory);
    }
}
