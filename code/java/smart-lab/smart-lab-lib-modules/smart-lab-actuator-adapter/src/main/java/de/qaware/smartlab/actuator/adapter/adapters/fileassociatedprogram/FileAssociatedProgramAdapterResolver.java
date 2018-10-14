package de.qaware.smartlab.actuator.adapter.adapters.fileassociatedprogram;

import de.qaware.smartlab.core.resolver.AbstractResolver;
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
        super(getFileAssociatedProgramAdaptersByActuatorType(programAdapters));
    }

    private static Set<Map.Entry<String, IFileAssociatedProgramAdapter>> getFileAssociatedProgramAdaptersByActuatorType(Optional<List<IFileAssociatedProgramAdapter>> programAdapters) {
        return programAdapters
                .map(adapters -> adapters
                        .stream()
                        .collect(toMap(IFileAssociatedProgramAdapter::getActuatorType, identity()))
                        .entrySet())
                .orElse(emptySet());
    }

    @Override
    protected String getErrorMessage(String actuatorType) {
        return format("The file-associated program type \"%s\" is unknown", actuatorType);
    }
}
