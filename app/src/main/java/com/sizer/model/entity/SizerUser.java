package com.sizer.model.entity;


import android.annotation.SuppressLint;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;

public class SizerUser {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("gender")
    @Expose
    private Gender gender;

    @SerializedName("device")
    @Expose
    private String device;

    @SerializedName("measurementUnit")
    @Expose
    private Integer measurementUnit;

    @SerializedName("promotionCode")
    @Expose
    private String promotionCode = "DEBUG_MODE";

    @SerializedName("manualFolder")
    @Expose
    private String manualFolder;

    @SerializedName("measurmentsJson")
    @Expose
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

    public String getGender() {
        return gender.getGender();
    }

    public void setGender(String gender) {
        switch (gender) {
            case "male":
                this.gender = Gender.MALE;
                break;
            case "female":
                this.gender = Gender.FEMALE;
                break;
        }
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Integer getMeasurmentUnit() {
        return measurementUnit;
    }

    public void setMeasurmentUnit(Integer measurmentUnit) {
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

    @SuppressLint("DefaultLocale")
    public String getUrlRequest() {

        return String.format("email=%s"
                + "&password=%s"
                + "&name=%s"
                + "&gender=%s"
                + "&device=%s"
                + "&measurementUnit=%d"
                + "&promotionCode=%s"
                + "&manualFolder=%s"
                + "&measurmentsJson=%s",
            email,
            password,
            name,
            gender.getGender(),
            device,
            measurementUnit,
            promotionCode,
            manualFolder,
            measurementUnit);
    }

    public Map<String, String> getMappedUser() {
        Map<String, String> mappedUser = new HashMap<>();

        mappedUser.put("email", this.email);
        mappedUser.put("password", this.password);
        mappedUser.put("gender", this.gender.getGender());
        mappedUser.put("device", this.device);
        mappedUser.put("measurementUnit", String.valueOf(this.measurementUnit));
        mappedUser.put("promotionCode", this.promotionCode);
        mappedUser.put("manualFolder", this.manualFolder);
        mappedUser.put("measurmentsJson", this.measurmentsJson);

        return mappedUser;
    }
}
