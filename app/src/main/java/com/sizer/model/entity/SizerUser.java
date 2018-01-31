package com.sizer.model.entity;


public class SizerUser {
    private String email;
    private String password;
    private String name;
    private Gender gender;
    private String device;
    private Integer measurmentUnit;
    private String promotionCode = "DEBUG_MODE";
    private String manualFolder;
    private String measurmentsJson;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Integer getMeasurmentUnit() {
        return measurmentUnit;
    }

    public void setMeasurmentUnit(Integer measurmentUnit) {
        this.measurmentUnit = measurmentUnit;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public String getManualFolder() {
        return manualFolder;
    }

    public void setManualFolder(String manualFolder) {
        this.manualFolder = manualFolder;
    }

    public String getMeasurmentsJson() {
        return measurmentsJson;
    }

    public void setMeasurmentsJson(String measurmentsJson) {
        this.measurmentsJson = measurmentsJson;
    }
}
