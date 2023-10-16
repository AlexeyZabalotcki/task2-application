package com.specific.group.dao;

import com.github.cliftonlabs.json_simple.JsonKey;

/**
 * Provides attributes for processing http response and request.
 */
public enum Attributes implements JsonKey {

    ID,
    FIRST_NAME,
    LAST_NAME,
    DEPARTMENT_ID,
    POSITION_ID,
    DEPARTMENT_NAME,
    POSITION_NAME;

    @Override
    public String getKey() {
        return convertString(this.name());
    }

    @Override
    public Object getValue() {
        throw new UnsupportedOperationException();
    }

    private static String convertString(String input) {
        StringBuilder sb = new StringBuilder(input.toLowerCase());
        int index = sb.indexOf("_");
        while (index != -1 && index + 1 < sb.length()) {
            sb.replace(index + 1, index + 2, String.valueOf(Character.toUpperCase(sb.charAt(index + 1))));
            sb.deleteCharAt(index);
            index = sb.indexOf("_", index + 1);
        }
        return sb.toString();
    }
}
