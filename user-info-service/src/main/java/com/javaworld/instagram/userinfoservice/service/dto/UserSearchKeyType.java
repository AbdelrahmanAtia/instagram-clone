package com.javaworld.instagram.userinfoservice.service.dto;

public enum UserSearchKeyType {
    USER_UUID,
    USER_NAME,
    USER_EMAIL;

    public static UserSearchKeyType fromValue(String value) {
        for (UserSearchKeyType type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException(
                "Invalid UserSearchKeyType value: " + value);
    }
}
