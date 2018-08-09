package de.qaware.smartlab.core.data.generic;

public interface IEntity<IdentifierT extends IIdentifier> {

    IdentifierT getId();
}
