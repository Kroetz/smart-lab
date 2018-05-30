package de.qaware.smartlabaction.action.uploaddata;

import de.qaware.smartlabcommons.data.action.uploaddata.IUploadDataService;
import de.qaware.smartlabcommons.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UploadDataServiceResolver extends AbstractResolver<String, IUploadDataService> {

    public UploadDataServiceResolver(List<IUploadDataService> uploadDataServices) {
        super(UploadDataServiceResolver.getMicrophoneAdaptersById(uploadDataServices));
    }

    private static Map<String, IUploadDataService> getMicrophoneAdaptersById(List<IUploadDataService> uploadDataServices) {
        return uploadDataServices.stream().collect(Collectors.toMap(IUploadDataService::getServiceId, Function.identity()));
    }
}
