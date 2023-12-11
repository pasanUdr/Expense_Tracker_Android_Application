package com.example.myapplication;

public class Category {

    private String id;
    private String catName;
    private String catSum;

    public Category(String id, String catName, String catSum) {
        this.id = id;
        this.catName = catName;
        this.catSum = catSum;
    }

    public Category(){

    }
//    public Category(String id) {
//        this.id = id;
//    }

    public Category(String catName) {
        this.catName = catName;
    }

    public Category(String id, String catName) {
        this.id = id;
        this.catName = catName;
    }

    public String getId() {

        return id;
    }

    public String setId(String id) {

        this.id = id;
        return id;
    }

    public String getCatName() {

        return catName;
    }

    public void setCatName(String catName) {

        this.catName = catName;
    }

    public String toString() {

        return catName;
    }

    public String getCatSum() {
        return catSum;
    }

    public void setCatSum(String catSum) {
        this.catSum = catSum;
    }

}
