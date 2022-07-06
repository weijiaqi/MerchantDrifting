package com.merchant.drifting.mvp.model.entity;

import java.util.List;

public class GoodsListEntity {


        private String goods_code;
        private String sku_code;
        private String goods_name;
        private String price;
        private Integer explore_id;
        private String large_image;
        private String small_image;
        private String sweet;
        private String intro;
        private String temperature;
        private String ingredient;

        public String getGoods_code() {
            return goods_code;
        }

        public void setGoods_code(String goods_code) {
            this.goods_code = goods_code;
        }

        public String getSku_code() {
            return sku_code;
        }

        public void setSku_code(String sku_code) {
            this.sku_code = sku_code;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public Integer getExplore_id() {
            return explore_id;
        }

        public void setExplore_id(Integer explore_id) {
            this.explore_id = explore_id;
        }

        public String getLarge_image() {
            return large_image;
        }

        public void setLarge_image(String large_image) {
            this.large_image = large_image;
        }

        public String getSmall_image() {
            return small_image;
        }

        public void setSmall_image(String small_image) {
            this.small_image = small_image;
        }

        public String getSweet() {
            return sweet;
        }

        public void setSweet(String sweet) {
            this.sweet = sweet;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getIngredient() {
            return ingredient;
        }

        public void setIngredient(String ingredient) {
            this.ingredient = ingredient;
        }

}
