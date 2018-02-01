package com.sizer.model;


import com.google.gson.annotations.SerializedName;

public class ApiResponse<T> {
    @SerializedName("resultCode")
    private String resultCode;
    @SerializedName("message")
    private String message;
    @SerializedName("dataType")
    private String dataType;
    @SerializedName("data")
    private T data;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
