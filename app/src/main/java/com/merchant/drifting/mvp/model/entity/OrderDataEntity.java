package com.merchant.drifting.mvp.model.entity;

public class OrderDataEntity {
    private String title;

    public OrderDataEntity( String title) {
        this.title = title;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
