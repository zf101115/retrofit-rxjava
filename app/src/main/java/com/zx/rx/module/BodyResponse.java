package com.zx.rx.module;

/**
 * Created by zx on 2017/6/6.
 */

public class BodyResponse {
    protected Integer code;
    protected String message;

    public BodyResponse(Integer status,String detail){
        this.code = status;
        this.message = detail;

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
