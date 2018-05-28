package de.qaware.smartlabsampledata.provider;

import de.qaware.smartlabcommons.miscellaneous.Property;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(
        prefix = Property.Prefix.SAMPLE_DATA,
        name = Property.Name.SAMPLE_DATA,
        havingValue = Property.Value.SampleData.NO_SAMPLE_DATA)
public class EmptySampleDataProvider extends AbstractSampleDataProvider {

    public EmptySampleDataProvider() {
        super();
    }
}
