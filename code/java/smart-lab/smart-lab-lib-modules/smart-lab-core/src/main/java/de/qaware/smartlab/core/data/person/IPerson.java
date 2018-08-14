package de.qaware.smartlab.core.data.person;

import de.qaware.smartlab.core.data.generic.IEntity;

public interface IPerson extends IEntity<PersonId> {

    String getName();
    String getEmail();
    PersonRole getRole();
}
