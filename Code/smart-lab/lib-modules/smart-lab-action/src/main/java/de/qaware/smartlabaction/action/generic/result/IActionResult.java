package de.qaware.smartlabaction.action.generic.result;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.qaware.smartlabaction.action.speechtotext.ITranscript;
import de.qaware.smartlabcommons.miscellaneous.Constants;

import java.util.Optional;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = Constants.JSON_TYPE_PROPERTY_NAME)
public interface IActionResult {

    Void getVoidValue();
    Optional<byte[]> getByteArrayValue();
    Optional<ITranscript> getTranscriptValue();
}
