package com.example.fivefivemm.utility;

import net.sf.json.JSONObject;

/**
 * 返回信息类
 */
public class ResponseBody {

    private Integer errorCode;

    private String errorMessage;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ResponseBody() {
    }

    public ResponseBody(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        if (errorCode != null) {
            jsonObject.put("errorCode", errorCode);
        }
        if (errorMessage != null) {
            jsonObject.put("errorMessage", errorMessage);
        }
        return jsonObject.toString();
    }
}
