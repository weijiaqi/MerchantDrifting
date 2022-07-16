package com.merchant.drifting.mvp.model.entity;

public class WriteOffEntity {


        private String sku_name;
        private String price;
        private String order_sub_sn;
        private String image;
        private String intro;

        public String getSku_name() {
            return sku_name;
        }

        public void setSku_name(String sku_name) {
            this.sku_name = sku_name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOrder_sub_sn() {
            return order_sub_sn;
        }

        public void setOrder_sub_sn(String order_sub_sn) {
            this.order_sub_sn = order_sub_sn;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

}
