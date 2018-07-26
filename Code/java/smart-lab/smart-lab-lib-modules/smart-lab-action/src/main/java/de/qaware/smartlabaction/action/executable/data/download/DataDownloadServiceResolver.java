package de.qaware.smartlabaction.action.executable.data.download;

import de.qaware.smartlabcore.data.action.datadownload.IDataDownloadService;
import de.qaware.smartlabcore.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
@Slf4j
public class DataDownloadServiceResolver extends AbstractResolver<String, IDataDownloadService> {

    public DataDownloadServiceResolver(List<IDataDownloadService> dataDownloadServices) {
        super(getDataDownloadServicesById(dataDownloadServices));
    }

    private static Set<Map.Entry<String, IDataDownloadService>> getDataDownloadServicesById(List<IDataDownloadService> dataDownloadServices) {
        return dataDownloadServices.stream()
                .collect(toMap(IDataDownloadService::getServiceId, identity()))
                .entrySet();
    }
}
