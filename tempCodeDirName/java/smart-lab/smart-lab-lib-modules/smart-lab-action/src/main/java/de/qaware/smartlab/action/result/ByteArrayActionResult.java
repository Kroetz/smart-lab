package de.qaware.smartlab.action.result;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ByteArrayActionResult extends AbstractActionResult<byte[]> {

    private ByteArrayActionResult(byte[] value) {
        super(value);
    }

    @JsonCreator
    public static ByteArrayActionResult of(@JsonProperty(FIELD_NAME_VALUE) byte[] value) {
        return new ByteArrayActionResult(value);
    }
}
