package de.qaware.smartlab.actuator.adapter.adapters.projectbase;

import de.qaware.smartlab.core.resolver.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;
import static java.util.Collections.emptySet;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
@Slf4j
public class DataDownloadServiceResolver extends AbstractResolver<String, IDataDownloadService> {

    public DataDownloadServiceResolver(Optional<List<IDataDownloadService>> dataDownloadServices) {
        super(getDataDownloadServicesByActuatorType(dataDownloadServices));
    }

    private static Set<Map.Entry<String, IDataDownloadService>> getDataDownloadServicesByActuatorType(Optional<List<IDataDownloadService>> dataDownloadServices) {
        return dataDownloadServices
                .map(services -> services
                        .stream()
                        .collect(toMap(IDataDownloadService::getActuatorType, identity()))
                        .entrySet())
                .orElse(emptySet());
    }

    @Override
    protected String getErrorMessage(String actuatorType) {
        return format("The data download service type \"%s\" is unknown", actuatorType);
    }
}
