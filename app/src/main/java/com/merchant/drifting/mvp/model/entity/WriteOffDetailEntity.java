package com.merchant.drifting.mvp.model.entity;

public class WriteOffDetailEntity {

        private Integer write_off_id;
        private Integer order_id;
        private Integer order_sub_id;
        private String order_sub_sn;
        private Integer business_id;
        private Integer shop_id;
        private Integer goods_id;
        private Integer sku_id;
        private String sku_name;
        private String intro;
        private Integer goods_num;
        private String money;
        private Integer status;
        private Integer user_id;
        private String user_name;
        private Integer created_at_int;
        private Integer updated_at_int;
        private String small_image;
        private String large_image;

    public String getSmall_image() {
        return small_image;
    }

    public void setSmall_image(String small_image) {
        this.small_image = small_image;
    }

    public String getLarge_image() {
        return large_image;
    }

    public void setLarge_image(String large_image) {
        this.large_image = large_image;
    }

    public Integer getWrite_off_id() {
            return write_off_id;
        }

        public void setWrite_off_id(Integer write_off_id) {
            this.write_off_id = write_off_id;
        }

        public Integer getOrder_id() {
            return order_id;
        }

        public void setOrder_id(Integer order_id) {
            this.order_id = order_id;
        }

        public Integer getOrder_sub_id() {
            return order_sub_id;
        }

        public void setOrder_sub_id(Integer order_sub_id) {
            this.order_sub_id = order_sub_id;
        }

        public String getOrder_sub_sn() {
            return order_sub_sn;
        }

        public void setOrder_sub_sn(String order_sub_sn) {
            this.order_sub_sn = order_sub_sn;
        }

        public Integer getBusiness_id() {
            return business_id;
        }

        public void setBusiness_id(Integer business_id) {
            this.business_id = business_id;
        }

        public Integer getShop_id() {
            return shop_id;
        }

        public void setShop_id(Integer shop_id) {
            this.shop_id = shop_id;
        }

        public Integer getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(Integer goods_id) {
            this.goods_id = goods_id;
        }

        public Integer getSku_id() {
            return sku_id;
        }

        public void setSku_id(Integer sku_id) {
            this.sku_id = sku_id;
        }

        public String getSku_name() {
            return sku_name;
        }

        public void setSku_name(String sku_name) {
            this.sku_name = sku_name;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public Integer getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(Integer goods_num) {
            this.goods_num = goods_num;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public Integer getCreated_at_int() {
            return created_at_int;
        }

        public void setCreated_at_int(Integer created_at_int) {
            this.created_at_int = created_at_int;
        }

        public Integer getUpdated_at_int() {
            return updated_at_int;
        }

        public void setUpdated_at_int(Integer updated_at_int) {
            this.updated_at_int = updated_at_int;
        }

}
