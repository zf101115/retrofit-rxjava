package com.zx.rx.module;

import java.io.Serializable;

/**
 * Created by zx on 2017/6/6.
 */

public class BaseBean implements Serializable {
    protected Integer status;
    protected String detail;
    protected String state;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
