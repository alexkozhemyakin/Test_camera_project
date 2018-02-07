package com.sizer.model.entity;


import android.util.Size;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;

public class SizerUser {
    @SerializedName("id")
    @Expose
    private String userID;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("height")
    @Expose
    private Double height;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("device")
    @Expose
    private String device = "Android";

    @SerializedName("measurementUnit")
    @Expose
    private String measurementUnit = "cm";

    @SerializedName("promotionCode")
    @Expose
    private String promotionCode = "DEBUG_MODE";

    @SerializedName("manualFolder")
    @Expose
    private String manualFolder;

    @SerializedName("measurmentsJson")
    @Expose
    private String measurmentsJson;

    public SizerUser() {}

    public SizerUser(String userId, String email, String name, String gender) {
        this.userID = userId;
        this.email = email;
        this.name = name;
        this.gender = gender;
    }

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

    public String getGender() {
        return gender.toLowerCase();
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getMeasurmentUnit() {
        return measurementUnit;
    }

    public void setMeasurmentUnit(String measurmentUnit) {
        this.measurementUnit = measurmentUnit;
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

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getHeight() {

        return height;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Map<String, String> getMappedUser() {
        Map<String, String> mappedUser = new HashMap<>();

        mappedUser.put("email", String.valueOf(this.email));
        mappedUser.put("name", this.name);
        mappedUser.put("password", String.valueOf(this.password));
        mappedUser.put("gender", String.valueOf(this.gender.toLowerCase()));
        mappedUser.put("device", String.valueOf(this.device));
        mappedUser.put("measurementUnit", String.valueOf(this.measurementUnit));
        mappedUser.put("promotionCode", String.valueOf(this.promotionCode));
        mappedUser.put("height", String.valueOf(this.height));
        /*
        mappedUser.put("manualFolder", this.manualFolder);
        mappedUser.put("measurmentsJson", this.measurmentsJson);*/

        return mappedUser;
    }


}
