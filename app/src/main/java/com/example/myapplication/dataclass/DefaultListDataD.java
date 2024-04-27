package com.example.myapplication.dataclass;

public class DefaultListDataD {

    private String dataType;
    private String dataName;

    public DefaultListDataD(String dataType, String dataName) {
        this.dataType = dataType;
        this.dataName = dataName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }
}
