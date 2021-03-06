package de.qaware.smartlab.core.util;

import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

public abstract class StringUtils {

    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String TAB = "\t";
    public static final String NEW_LINE = "\n";
    public static final String COLON = ":";
    public static final String COMMA = ",";
    public static final String EQUALS = "=";
    public static final String DOUBLE_QUOTES = "\"";
    public static final String DOUBLE_QUOTED_TEMPLATE = DOUBLE_QUOTES + "%s" + DOUBLE_QUOTES;
    public static final String LPAREN = "(";
    public static final String RPAREN = ")";
    public static final String PARENTHESES_TEMPLATE = LPAREN + "%s" + RPAREN;

    public static String removeLineBreaks(String s) {
        return s.replace(NEW_LINE, EMPTY);
    }

    public static String trimEnclosing(String s) {
        return s.substring(1, s.length() - 1);
    }

    public static String utf8ToBase64String(String s) {
        return Base64.getEncoder().encodeToString(s.getBytes(UTF_8));
    }
}
