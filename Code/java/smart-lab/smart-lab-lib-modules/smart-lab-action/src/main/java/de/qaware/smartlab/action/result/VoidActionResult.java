package de.qaware.smartlab.action.result;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class VoidActionResult extends AbstractActionResult<Void> {

    private VoidActionResult() {
        super(null);
    }

    @JsonCreator
    public static VoidActionResult newInstance() {
        return new VoidActionResult();
    }
}
