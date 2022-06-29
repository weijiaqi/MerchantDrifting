package com.merchant.drifting.mvp.model.entity;

public class OrderEntity {
    private String title;

    public OrderEntity( String title) {
        this.title = title;

    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
