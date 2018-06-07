package de.qaware.smartlabaction.action.generic.result;

import lombok.Data;

import java.util.Optional;

@Data
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
