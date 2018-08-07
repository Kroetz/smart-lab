package de.qaware.smartlabdataset.factory;

import de.qaware.smartlabcore.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
@Slf4j
public class DataSetFactoryResolver extends AbstractResolver<String, IDataSetFactory> {

    public DataSetFactoryResolver(List<IDataSetFactory> dataSetFactories) {
        super(getFactoriesById(dataSetFactories));
    }

    private static Set<Map.Entry<String, IDataSetFactory>> getFactoriesById(List<IDataSetFactory> factories) {
        return factories.stream()
                .collect(toMap(IDataSetFactory::getId, identity()))
                .entrySet();
    }

    @Override
    protected String getErrorMessage(String dataSetFactoryId) {
        return format("The data set factory \"%s\" is unknown", dataSetFactoryId);
    }
}
