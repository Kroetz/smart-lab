package de.qaware.smartlab.core.data.action.generic.result;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlab.core.data.action.speechtotext.ITranscript;
import de.qaware.smartlab.core.miscellaneous.Constants;

import java.util.Optional;
import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = Constants.JSON_TYPE_PROPERTY_NAME)
public interface IActionResult {

    Void getVoidValue();
    Optional<byte[]> getByteArrayValue();
    Optional<ITranscript> getTranscriptValue();
    Optional<UUID> getUuidValue();
}