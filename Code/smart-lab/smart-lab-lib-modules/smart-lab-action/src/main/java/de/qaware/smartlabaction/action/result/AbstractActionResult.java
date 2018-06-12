package de.qaware.smartlabaction.action.result;

import de.qaware.smartlabcore.data.action.speechtotext.ITranscript;
import de.qaware.smartlabcore.data.action.generic.result.IActionResult;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
public abstract class AbstractActionResult<T> implements IActionResult {

    private T value;

    AbstractActionResult(T value) {
        this.value = value;
    }

    private <U> Optional<U> getCastedValue(Class<? extends U> castTargetClass) {
        try {
            return Optional.ofNullable(castTargetClass.cast(this.value));
        }
        catch(ClassCastException e) {
            return Optional.empty();
        }
    }

    @Override
    public Void getVoidValue() {
        return null;
    }

    @Override
    public Optional<byte[]> getByteArrayValue() {
        return getCastedValue(byte[].class);
    }

    @Override
    public Optional<ITranscript> getTranscriptValue() {
        return getCastedValue(ITranscript.class);
    }
}
