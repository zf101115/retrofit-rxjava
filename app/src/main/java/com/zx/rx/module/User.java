package com.zx.rx.module;

import java.io.Serializable;

/**
 * Created by zx on 2017/5/23.
 */

public class User implements Serializable{
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
