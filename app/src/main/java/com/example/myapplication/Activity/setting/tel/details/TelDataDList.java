package com.example.myapplication.Activity.setting.tel.details;

import java.util.List;

public class TelDataDList {

    private String name;
    private List<String> List;

    public TelDataDList(String name, List<String> List) {
        this.name = name;
        this.List = List;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.util.List<String> getList() {
        return List;
    }

    public void setList(java.util.List<String> list) {
        List = list;
    }
}
