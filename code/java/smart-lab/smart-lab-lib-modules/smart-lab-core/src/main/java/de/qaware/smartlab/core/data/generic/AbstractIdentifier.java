package de.qaware.smartlab.core.data.generic;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@EqualsAndHashCode
public abstract class AbstractIdentifier implements IIdentifier {

    protected static final String ID_VALUE_FIELD_NAME = "idValue";
    protected final String idValue;

    protected AbstractIdentifier(String idValue) {
        this.idValue = idValue;
    }

    @Override
    public String getIdValue() {
        return this.idValue;
    }
}
