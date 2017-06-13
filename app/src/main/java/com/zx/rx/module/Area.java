package com.zx.rx.module;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zx on 2017/6/6.
 */

public class Area extends BaseBean {
    private Integer id;
    private String name;
    private Integer level;
    private boolean isSelect;
    private String firstLetter;

    @SerializedName("parent")
    private Integer parent;

    @SerializedName("prov_area")
    private Area provArea;

    public Area(Integer id,String name){
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public Area getProvArea() {
        return provArea;
    }

    public void setProvArea(Area provArea) {
        this.provArea = provArea;
    }
}
