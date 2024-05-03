package com.example.myapplication.dataclass;

public class DefaultListDataD {


    private String dataName;
    private String dataContent;

    public DefaultListDataD(String dataName, String dataContent) {
        this.dataName = dataName;
        this.dataContent = dataContent;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataContent() {
        return dataContent;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }
}
