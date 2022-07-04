package com.merchant.drifting.mvp.model.entity;

public class ApplyGoodsEntity {

    private int pic;
    private int  type;

    public ApplyGoodsEntity(int pic, int type) {
        this.type = type;
        this.pic = pic;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
