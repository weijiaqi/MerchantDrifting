package com.merchant.drifting.data.entity;

public class TransactionEntity {
    private int pic;
    private String title;

    public TransactionEntity(int pic, String title) {
        this.title = title;
        this.pic = pic;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
