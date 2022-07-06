package com.merchant.drifting.mvp.model.entity;

public class GoodsInfoEntity {

        private Integer sku_id;
        private Integer shop_id;
        private String goods_code;
        private String sku_code;
        private String name;
        private String price;
        private Integer explore_id;
        private String goods_image;
        private String intro;
        private String specs;
        private String sweet;
        private String temperature;
        private String ingredient;
        private Integer audit;

        public Integer getSku_id() {
            return sku_id;
        }

        public void setSku_id(Integer sku_id) {
            this.sku_id = sku_id;
        }

        public Integer getShop_id() {
            return shop_id;
        }

        public void setShop_id(Integer shop_id) {
            this.shop_id = shop_id;
        }

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getSpecs() {
            return specs;
        }

        public void setSpecs(String specs) {
            this.specs = specs;
        }

        public String getSweet() {
            return sweet;
        }

        public void setSweet(String sweet) {
            this.sweet = sweet;
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

        public Integer getAudit() {
            return audit;
        }

        public void setAudit(Integer audit) {
            this.audit = audit;
        }
    }

