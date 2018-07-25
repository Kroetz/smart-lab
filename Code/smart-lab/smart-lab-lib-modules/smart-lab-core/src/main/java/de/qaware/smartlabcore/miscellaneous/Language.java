package de.qaware.smartlabcore.miscellaneous;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum Language {

    AR_AR,
    DE_DE,
    FR_FR,
    EN_US,
    EN_GB,
    ES_ES,
    JA_JP,
    KO_KR,
    PT_BR,
    ZH_CN;

    public static Language of(String language) throws IllegalArgumentException {
        try {
            return Language.valueOf(language.trim().toUpperCase().replace("-", "_"));
        }
        catch(IllegalArgumentException e) {
            log.error("Cannot create language from string \"{}\"", language);
            throw e;
        }
    }
}
