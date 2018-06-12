package de.qaware.smartlabaction.action.executable.dataupload;

import de.qaware.smartlabcore.data.action.dataupload.IDataUploadService;
import de.qaware.smartlabcore.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DataUploadServiceResolver extends AbstractResolver<String, IDataUploadService> {

    public DataUploadServiceResolver(List<IDataUploadService> dataUploadServices) {
        super(DataUploadServiceResolver.getMicrophoneAdaptersById(dataUploadServices));
    }

    private static Map<String, IDataUploadService> getMicrophoneAdaptersById(List<IDataUploadService> dataUploadServices) {
        return dataUploadServices.stream().collect(Collectors.toMap(IDataUploadService::getServiceId, Function.identity()));
    }
}
