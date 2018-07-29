package de.qaware.smartlabaction.action.result;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UuidActionResult extends AbstractActionResult<UUID> {

    private UuidActionResult(UUID value) {
        super(value);
    }

    public static UuidActionResult of(UUID value) {
        return new UuidActionResult(value);
    }
}
