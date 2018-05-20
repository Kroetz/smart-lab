package de.qaware.smartlabcommons.data.action.result;

public class ByteArrayActionResult extends AbstractActionResult<byte[]> {

    private ByteArrayActionResult(byte[] value) {
        super(value);
    }

    public static ByteArrayActionResult of(byte[] value) {
        return new ByteArrayActionResult(value);
    }
}
