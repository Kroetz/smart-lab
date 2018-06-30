package de.qaware.smartlabcore.data.generic;

import lombok.EqualsAndHashCode;
import lombok.ToString;

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
