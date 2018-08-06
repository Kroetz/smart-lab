package de.qaware.smartlabactuatoradapter.actuator.fileassociatedprogram;

import de.qaware.smartlabcore.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
@Slf4j
public class FileAssociatedProgramAdapterResolver extends AbstractResolver<String, IFileAssociatedProgramAdapter> {

    public FileAssociatedProgramAdapterResolver(Optional<List<IFileAssociatedProgramAdapter>> programAdapters) {
        super(getFileAssociatedProgramAdaptersByType(programAdapters));
    }

    private static Set<Map.Entry<String, IFileAssociatedProgramAdapter>> getFileAssociatedProgramAdaptersByType(Optional<List<IFileAssociatedProgramAdapter>> programAdapters) {
        return programAdapters
                .map(adapters -> adapters
                        .stream()
                        .collect(toMap(IFileAssociatedProgramAdapter::getDeviceType, identity()))
                        .entrySet())
                .orElse(new HashSet<>());
    }
}
