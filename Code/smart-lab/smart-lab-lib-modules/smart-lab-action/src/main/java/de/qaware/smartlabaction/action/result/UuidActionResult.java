package de.qaware.smartlabaction.action.result;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UuidActionResult extends AbstractActionResult<UUID> {

    public UuidActionResult(UUID value) {
        super(value);
    }

    public static UuidActionResult of(UUID value) {
        return new UuidActionResult(value);
    }
}
