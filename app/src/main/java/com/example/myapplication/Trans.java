package com.example.myapplication;

public class Trans {

    private String id;
    private String desc;
    private String date;
    private String tranAmount;

    public Trans() {

    }

    public Trans(String desc, String date) {
        this.desc = desc;
        this.date = date;
    }

    public Trans(String desc, String date, String tranAmount) {
        this.desc = desc;
        this.date = date;
        this.tranAmount = tranAmount;
    }

    public Trans(String id, String desc, String date, String tranAmount) {
        this.id = id;
        this.desc = desc;
        this.date = date;
        this.tranAmount = tranAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTranAmount() {
        return tranAmount;
    }

    public void setTranAmount(String tranAmount) {
        this.tranAmount = tranAmount;
    }
}
