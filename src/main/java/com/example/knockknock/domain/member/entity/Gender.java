package com.example.knockknock.domain.member.entity;

public enum Gender {
    MALE("M"),
    FEMALE("F");

    private final String code;

    Gender(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static Gender fromCode(String code) {
        for (Gender gender : Gender.values()) {
            if (gender.getCode().equals(code)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
