package de.qaware.smartlabcore.miscellaneous;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum Language {

    EN_US,
    EN_GB,
    FR_FR,
    DE_DE;

    public static Language of(String language) throws IllegalArgumentException {
        try {
            return Language.valueOf(language.trim().toUpperCase());
        }
        catch(IllegalArgumentException e) {
            log.error("Cannot create language from string \"{}\"", language);
            throw e;
        }
    }
}
