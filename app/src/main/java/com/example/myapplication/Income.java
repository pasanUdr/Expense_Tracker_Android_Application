package com.example.myapplication;

public class Income {

    private String incID;
    private String transID;
    private String catID;
    private String amount;
    private String sum;

    public Income() {
    }

    public Income(String incID, String transID, String catID, String amount) {
        this.incID = incID;
        this.transID = transID;
        this.catID = catID;
        this.amount = amount;
    }

    public Income(String catID, String amount) {
        this.catID = catID;
        this.amount = amount;
    }

//    public Income(String catID) {
//        this.catID = catID;
//    }

    public Income(String amount) {
        this.amount = amount;
    }

//    public Income(String catId) {
//        this.catID = catID;
//    }

    public String getIncID() {
        return incID;
    }

    public void setIncID(String incID) {
        this.incID = incID;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
}
