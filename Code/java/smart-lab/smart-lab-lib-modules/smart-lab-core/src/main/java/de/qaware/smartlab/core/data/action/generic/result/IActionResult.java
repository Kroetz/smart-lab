package de.qaware.smartlab.core.data.action.generic.result;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlab.core.data.action.speechtotext.ITranscript;
import de.qaware.smartlab.core.constant.Constants;

import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = Constants.JSON_TYPE_PROPERTY_NAME)
public interface IActionResult {

    Void getVoidValue();
    byte[] getByteArrayValue();
    ITranscript getTranscriptValue();
    UUID getUuidValue();
}
