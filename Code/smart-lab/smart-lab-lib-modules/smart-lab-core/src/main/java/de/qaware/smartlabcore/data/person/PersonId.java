package de.qaware.smartlabcore.data.person;

import de.qaware.smartlabcore.data.generic.AbstractIdentifier;

public class PersonId extends AbstractIdentifier {

    private PersonId(String idValue) {
        super(idValue);
    }

    public static PersonId of(String idValue) {
        return new PersonId(idValue);
    }
}
