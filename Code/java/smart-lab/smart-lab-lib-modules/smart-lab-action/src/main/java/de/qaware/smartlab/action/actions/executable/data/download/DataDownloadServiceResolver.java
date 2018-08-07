package de.qaware.smartlab.action.actions.executable.data.download;

import de.qaware.smartlabcore.data.action.datadownload.IDataDownloadService;
import de.qaware.smartlabcore.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.lang.String.format;
import static java.util.Collections.emptySet;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
@Slf4j
public class DataDownloadServiceResolver extends AbstractResolver<String, IDataDownloadService> {

    public DataDownloadServiceResolver(Optional<List<IDataDownloadService>> dataDownloadServices) {
        super(getDataDownloadServicesById(dataDownloadServices));
    }

    private static Set<Map.Entry<String, IDataDownloadService>> getDataDownloadServicesById(Optional<List<IDataDownloadService>> dataDownloadServices) {
        return dataDownloadServices
                .map(services -> services
                        .stream()
                        .collect(toMap(IDataDownloadService::getServiceId, identity()))
                        .entrySet())
                .orElse(emptySet());
    }

    @Override
    protected String getErrorMessage(String dataDownloadService) {
        return format("The data download service \"%s\" is unknown", dataDownloadService);
    }
}
