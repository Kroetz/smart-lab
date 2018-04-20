package de.qaware.smartlabcore.data.sample.provider;

import de.qaware.smartlabcore.data.sample.factory.ISampleDataFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile("blueGreenRedBlackSampleData")
public class BlueGreenRedBlackSampleDataProvider extends AbstractSampleDataProvider {

    public BlueGreenRedBlackSampleDataProvider(
            ISampleDataFactory coastGuardDataFactory,
            ISampleDataFactory forestRangersDataFactory,
            ISampleDataFactory fireFightersDataFactory,
            ISampleDataFactory astronautsDataFactory) {
        super(coastGuardDataFactory, forestRangersDataFactory, fireFightersDataFactory, astronautsDataFactory);
    }
}
