package de.qaware.smartlabaction.action.result;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ByteArrayActionResult extends AbstractActionResult<byte[]> {

    private ByteArrayActionResult(byte[] value) {
        super(value);
    }

    public static ByteArrayActionResult of(byte[] value) {
        return new ByteArrayActionResult(value);
    }
}
