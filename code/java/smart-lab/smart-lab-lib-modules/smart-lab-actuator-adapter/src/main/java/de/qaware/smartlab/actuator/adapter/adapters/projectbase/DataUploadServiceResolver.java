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
public class DataUploadServiceResolver extends AbstractResolver<String, IDataUploadService> {

    public DataUploadServiceResolver(Optional<List<IDataUploadService>> dataUploadServices) {
        super(getDataUploadServicesByActuatorType(dataUploadServices));
    }

    private static Set<Map.Entry<String, IDataUploadService>> getDataUploadServicesByActuatorType(Optional<List<IDataUploadService>> dataUploadServices) {
        return dataUploadServices
                .map(services -> services
                        .stream()
                        .collect(toMap(IDataUploadService::getActuatorType, identity()))
                        .entrySet())
                .orElse(emptySet());
    }

    @Override
    protected String getErrorMessage(String actuatorType) {
        return format("The data upload service type \"%s\" is unknown", actuatorType);
    }
}
