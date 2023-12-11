package com.example.myapplication;

public class Expense {

    private String expID;
    private String transID;
    private String catID;
    private float expAmount;

    public Expense() {
    }

    public Expense(String expID, String transID, String catID, float expAmount) {
        this.expID = expID;
        this.transID = transID;
        this.catID = catID;
        this.expAmount = expAmount;
    }

    public Expense(String catID, float expAmount) {
        this.catID = catID;
        this.expAmount = expAmount;
    }

    public String getExpID() {
        return expID;
    }

    public void setExpID(String expID) {
        this.expID = expID;
    }

    public String getTransID() {
        return transID;
    }

    public void setTransID(String transID) {
        this.transID = transID;
    }

    public String getCatID() {
        return catID;
    }

    public void setCatID(String catID) {
        this.catID = catID;
    }

    public float getExpAmount() {
        return expAmount;
    }

    public void setExpAmount(float expAmount) {
        this.expAmount = expAmount;
    }
}
