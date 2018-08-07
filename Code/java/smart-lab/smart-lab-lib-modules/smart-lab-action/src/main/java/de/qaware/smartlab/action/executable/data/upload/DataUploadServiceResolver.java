package de.qaware.smartlab.action.executable.data.upload;

import de.qaware.smartlabcore.data.action.dataupload.IDataUploadService;
import de.qaware.smartlabcore.data.generic.AbstractResolver;
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
        super(getDataUploadServicesById(dataUploadServices));
    }

    private static Set<Map.Entry<String, IDataUploadService>> getDataUploadServicesById(Optional<List<IDataUploadService>> dataUploadServices) {
        return dataUploadServices
                .map(services -> services
                        .stream()
                        .collect(toMap(IDataUploadService::getServiceId, identity()))
                        .entrySet())
                .orElse(emptySet());
    }

    @Override
    protected String getErrorMessage(String dataUploadService) {
        return format("The data upload service \"%s\" is unknown", dataUploadService);
    }
}
