package com.sizer.model.entity;


public enum Gender {
    MALE("male"), FEMALE("female");

    Gender(String value) {
        this.value = value;
    }

    private String value;

    public String getGender() {
        return this.value;
    }
}
