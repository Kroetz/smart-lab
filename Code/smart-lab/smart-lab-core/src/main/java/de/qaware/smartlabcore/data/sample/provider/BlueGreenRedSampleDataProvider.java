package de.qaware.smartlabcore.data.sample.provider;

import de.qaware.smartlabcore.data.sample.factory.ISampleDataFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile("blueGreenRedSampleData")
public class BlueGreenRedSampleDataProvider extends AbstractSampleDataProvider {

    public BlueGreenRedSampleDataProvider(
            ISampleDataFactory coastGuardDataFactory,
            ISampleDataFactory forestRangersDataFactory,
            ISampleDataFactory fireFightersDataFactory) {
        super(coastGuardDataFactory, forestRangersDataFactory, fireFightersDataFactory);
    }
}
