package de.qaware.smartlabsampledata.provider;

import de.qaware.smartlabcore.miscellaneous.Property;
import de.qaware.smartlabsampledata.factory.ISampleDataFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.SAMPLE_DATA,
        name = Property.Name.SAMPLE_DATA,
        havingValue = Property.Value.SampleData.BLUE_GREEN_RED_BLACK_SAMPLE_DATA)
public class BlueGreenRedBlackSampleDataProvider extends AbstractSampleDataProvider {

    public BlueGreenRedBlackSampleDataProvider(
            ISampleDataFactory coastGuardDataFactory,
            ISampleDataFactory forestRangersDataFactory,
            ISampleDataFactory fireFightersDataFactory,
            ISampleDataFactory astronautsDataFactory) {
        super(coastGuardDataFactory, forestRangersDataFactory, fireFightersDataFactory, astronautsDataFactory);
    }
}
