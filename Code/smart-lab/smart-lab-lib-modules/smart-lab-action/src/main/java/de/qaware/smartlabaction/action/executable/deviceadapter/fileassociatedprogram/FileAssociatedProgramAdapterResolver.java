package de.qaware.smartlabaction.action.executable.deviceadapter.fileassociatedprogram;

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
public class FileAssociatedProgramAdapterResolver extends AbstractResolver<String, IFileAssociatedProgramAdapter> {

    public FileAssociatedProgramAdapterResolver(List<IFileAssociatedProgramAdapter> programAdapters) {
        super(getFileAssociatedProgramAdaptersByType(programAdapters));
    }

    private static Set<Map.Entry<String, IFileAssociatedProgramAdapter>> getFileAssociatedProgramAdaptersByType(List<IFileAssociatedProgramAdapter> programAdapters) {
        return programAdapters.stream()
                .collect(toMap(IFileAssociatedProgramAdapter::getDeviceType, identity()))
                .entrySet();
    }
}
