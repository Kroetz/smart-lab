package de.qaware.smartlab.actuator.adapter.adapters.fileassociatedprogram;

import de.qaware.smartlab.core.data.generic.AbstractResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.lang.String.format;
import static java.util.Collections.emptySet;
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
                .orElse(emptySet());
    }

    @Override
    protected String getErrorMessage(String deviceType) {
        return format("The file-associated program \"%s\" is unknown", deviceType);
    }
}
