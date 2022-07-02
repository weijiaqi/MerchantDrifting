package com.merchant.drifting.mvp.model.entity;

import java.util.List;

public class SystemNotificationEntity {


        private Integer limit;
        private Integer page;
        private Integer count;
        private List<ListBean> list;

        public Integer getLimit() {
            return limit;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private Integer business_msg_id;
            private Integer business_id;
            private Integer shop_id;
            private Integer msg_type;
            private String title;
            private String content;
            private Integer is_read;
            private Integer created_at_int;
            private Integer updated_at_int;

            public Integer getBusiness_msg_id() {
                return business_msg_id;
            }

            public void setBusiness_msg_id(Integer business_msg_id) {
                this.business_msg_id = business_msg_id;
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

            public Integer getMsg_type() {
                return msg_type;
            }

            public void setMsg_type(Integer msg_type) {
                this.msg_type = msg_type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public Integer getIs_read() {
                return is_read;
            }

            public void setIs_read(Integer is_read) {
                this.is_read = is_read;
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
    }

