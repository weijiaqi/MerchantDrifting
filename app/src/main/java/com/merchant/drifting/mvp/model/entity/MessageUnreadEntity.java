package com.merchant.drifting.mvp.model.entity;

public class MessageUnreadEntity {

        private Integer sys_msg;
        private String sys_title;
        private Integer sys_created_at;
        private Integer order_msg;
        private String order_title;
        private Integer order_created_at;

        public Integer getSys_msg() {
            return sys_msg;
        }

        public void setSys_msg(Integer sys_msg) {
            this.sys_msg = sys_msg;
        }

        public String getSys_title() {
            return sys_title;
        }

        public void setSys_title(String sys_title) {
            this.sys_title = sys_title;
        }

        public Integer getSys_created_at() {
            return sys_created_at;
        }

        public void setSys_created_at(Integer sys_created_at) {
            this.sys_created_at = sys_created_at;
        }

        public Integer getOrder_msg() {
            return order_msg;
        }

        public void setOrder_msg(Integer order_msg) {
            this.order_msg = order_msg;
        }

        public String getOrder_title() {
            return order_title;
        }

        public void setOrder_title(String order_title) {
            this.order_title = order_title;
        }

        public Integer getOrder_created_at() {
            return order_created_at;
        }

        public void setOrder_created_at(Integer order_created_at) {
            this.order_created_at = order_created_at;
        }

}
