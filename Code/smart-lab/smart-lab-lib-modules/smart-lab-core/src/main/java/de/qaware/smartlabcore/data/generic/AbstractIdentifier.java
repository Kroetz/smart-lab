package de.qaware.smartlabcore.data.generic;

import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode
public abstract class AbstractIdentifier implements IIdentifier {

    protected final String idValue;

    protected AbstractIdentifier(String idValue) {
        this.idValue = idValue;
    }

    @Override
    public String getIdValue() {
        return this.idValue;
    }

    @Override
    public int compareTo(@NotNull String o) {
        return this.idValue.compareTo(o);
    }
}
