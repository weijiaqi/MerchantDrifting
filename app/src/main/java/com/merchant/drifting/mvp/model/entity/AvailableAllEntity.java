package com.merchant.drifting.mvp.model.entity;

import java.util.List;

public class AvailableAllEntity {

        private Integer explore_id;
        private String name;
        private String image;
        private String desc;
        private List<CupsBean> cups;

        public Integer getExplore_id() {
            return explore_id;
        }

        public void setExplore_id(Integer explore_id) {
            this.explore_id = explore_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public List<CupsBean> getCups() {
            return cups;
        }

        public void setCups(List<CupsBean> cups) {
            this.cups = cups;
        }

        public static class CupsBean {
            private String goods_code;
            private String goods_name;
            private Integer explore_id;
            private String small_image;
            private String large_image;
            private String intro;
            private Integer created_at_int;

            public String getGoods_code() {
                return goods_code;
            }

            public void setGoods_code(String goods_code) {
                this.goods_code = goods_code;
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

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public Integer getCreated_at_int() {
                return created_at_int;
            }

            public void setCreated_at_int(Integer created_at_int) {
                this.created_at_int = created_at_int;
            }
        }

}
