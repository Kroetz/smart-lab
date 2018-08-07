package de.qaware.smartlab.action.result;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UuidActionResult extends AbstractActionResult<UUID> {

    private UuidActionResult(UUID value) {
        super(value);
    }

    @JsonCreator
    public static UuidActionResult of(@JsonProperty(VALUE_FIELD_NAME) UUID value) {
        return new UuidActionResult(value);
    }
}
