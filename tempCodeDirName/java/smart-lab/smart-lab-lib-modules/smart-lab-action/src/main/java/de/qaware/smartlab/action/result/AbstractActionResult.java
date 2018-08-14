package de.qaware.smartlab.action.result;

import de.qaware.smartlab.core.data.action.generic.result.IActionResult;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

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

    public ReturnT getValue() {
        return this.value;
    }
}
