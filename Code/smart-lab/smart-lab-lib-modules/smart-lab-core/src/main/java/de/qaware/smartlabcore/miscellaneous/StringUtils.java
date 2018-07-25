package de.qaware.smartlabcore.miscellaneous;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

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

    public static String trimEnclosing(String s) {
        return s.substring(1, s.length() - 1);
    }

    public static String utf8ToBase64String(String s) {
        try {
            // TODO: String literal
            return Base64.getEncoder().encodeToString(s.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO: Exception type & message
            throw new RuntimeException("Base64 failed");
        }
    }
}
