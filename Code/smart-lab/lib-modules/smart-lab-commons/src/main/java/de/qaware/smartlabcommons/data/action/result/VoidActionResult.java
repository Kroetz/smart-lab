package de.qaware.smartlabcommons.data.action.result;

import java.util.Optional;

public class VoidActionResult extends AbstractActionResult {

    private VoidActionResult() {
        super(null);
    }

    public static VoidActionResult instance() {
        return new VoidActionResult();
    }

    @Override
    public Optional<byte[]> getByteArrayValue() {
        return Optional.empty();
    }
}
