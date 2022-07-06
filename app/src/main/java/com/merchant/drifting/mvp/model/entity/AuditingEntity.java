package com.merchant.drifting.mvp.model.entity;

import java.util.List;

public class AuditingEntity {


        private String goods_code;
        private String sku_code;
        private String goods_name;
        private Integer explore_id;
        private Integer audit;
        private String large_image;
        private String small_image;
        private String reason;
        private String intro;
        private String sweet;
        private String temperature;
        private String ingredient;
        private Integer apply_at_int;

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

        public Integer getExplore_id() {
            return explore_id;
        }

        public void setExplore_id(Integer explore_id) {
            this.explore_id = explore_id;
        }

        public Integer getAudit() {
            return audit;
        }

        public void setAudit(Integer audit) {
            this.audit = audit;
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

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
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

        public Integer getApply_at_int() {
            return apply_at_int;
        }

        public void setApply_at_int(Integer apply_at_int) {
            this.apply_at_int = apply_at_int;
        }
    }

