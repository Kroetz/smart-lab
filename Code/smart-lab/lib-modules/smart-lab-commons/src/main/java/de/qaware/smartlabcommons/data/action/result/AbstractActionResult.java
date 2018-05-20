package de.qaware.smartlabcommons.data.action.result;

import java.util.Optional;

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
}
