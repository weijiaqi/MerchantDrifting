package com.merchant.drifting.mvp.model.entity;

public class SystemNotificationEntity {
    private String title;

    public SystemNotificationEntity( String title) {
        this.title = title;

    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
