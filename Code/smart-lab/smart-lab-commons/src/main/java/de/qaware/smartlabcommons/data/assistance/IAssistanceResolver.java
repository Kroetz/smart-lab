package de.qaware.smartlabcommons.data.assistance;

import java.util.Optional;

public interface IAssistanceResolver {

    Optional<IAssistance> resolveAssistanceId(String assistanceId);
}
