package de.qaware.smartlabaction.action.result;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class VoidActionResult extends AbstractActionResult<Void> {

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
