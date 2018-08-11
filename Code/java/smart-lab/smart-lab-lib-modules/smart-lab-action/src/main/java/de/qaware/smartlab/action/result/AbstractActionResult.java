package de.qaware.smartlab.action.result;

import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import de.qaware.smartlab.core.data.action.speechtotext.ITranscript;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import static de.qaware.smartlab.core.constant.Constants.VOID;
import static java.lang.String.format;

@Getter
@ToString
@EqualsAndHashCode
@Slf4j
public abstract class AbstractActionResult<ReturnT> implements IActionResult {

    protected static final String FIELD_NAME_VALUE = "value";
    private ReturnT value;

    protected AbstractActionResult(ReturnT value) {
        this.value = value;
    }

    private <CastT> CastT getCastedValue(Class<? extends CastT> castTargetClass) {
        try {
            return castTargetClass.cast(this.value);
        }
        catch(ClassCastException e) {
            String errorMessage = format(
                    "The value %s of the action result must be of the type %s",
                    this.value.toString(),
                    castTargetClass.getName());
            log.error(errorMessage);
            throw new IllegalStateException(errorMessage, e);
        }
    }

    @Override
    public Void getVoidValue() {
        return VOID;
    }

    @Override
    public byte[] getByteArrayValue() {
        return getCastedValue(byte[].class);
    }

    @Override
    public ITranscript getTranscriptValue() {
        return getCastedValue(ITranscript.class);
    }

    @Override
    public UUID getUuidValue() {
        return getCastedValue(UUID.class);
    }
}
