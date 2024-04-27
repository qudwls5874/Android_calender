package com.example.myapplication.dataclass;

import java.util.List;

public class DefaultListData {

    private String name;
    private List<DefaultListDataD> List;

    public DefaultListData(String name, List<DefaultListDataD> List) {
        this.name = name;
        this.List = List;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.util.List<DefaultListDataD> getList() {
        return List;
    }

    public void setList(java.util.List<DefaultListDataD> list) {
        List = list;
    }
}



