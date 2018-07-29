package de.qaware.smartlabaction.action.result;

import lombok.*;

@NoArgsConstructor
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ByteArrayActionResult extends AbstractActionResult<byte[]> {

    private ByteArrayActionResult(byte[] value) {
        super(value);
    }

    public static ByteArrayActionResult of(byte[] value) {
        return new ByteArrayActionResult(value);
    }
}
