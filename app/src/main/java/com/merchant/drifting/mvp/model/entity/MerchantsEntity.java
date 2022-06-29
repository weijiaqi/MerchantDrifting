package com.merchant.drifting.mvp.model.entity;

public class MerchantsEntity {
    private String title;

    public MerchantsEntity(String title) {
        this.title = title;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
