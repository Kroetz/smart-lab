package de.qaware.smartlabaction.action.executable.data.upload;

import de.qaware.smartlabcore.data.action.dataupload.IDataUploadService;
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
public class DataUploadServiceResolver extends AbstractResolver<String, IDataUploadService> {

    public DataUploadServiceResolver(List<IDataUploadService> dataUploadServices) {
        super(getDataUploadServicesById(dataUploadServices));
    }

    private static Set<Map.Entry<String, IDataUploadService>> getDataUploadServicesById(List<IDataUploadService> dataUploadServices) {
        return dataUploadServices.stream()
                .collect(toMap(IDataUploadService::getServiceId, identity()))
                .entrySet();
    }
}
