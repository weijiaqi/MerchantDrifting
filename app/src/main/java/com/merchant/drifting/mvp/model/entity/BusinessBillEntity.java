package com.merchant.drifting.mvp.model.entity;

import java.util.List;

public class BusinessBillEntity {

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
        private Integer shop_bill_id;
        private Integer business_id;
        private Integer shop_id;
        private String money;
        private Integer status;
        private Integer bill_type;
        private Integer apply_id;
        private Integer created_at_int;
        private Integer updated_at_int;

        public Integer getShop_bill_id() {
            return shop_bill_id;
        }

        public void setShop_bill_id(Integer shop_bill_id) {
            this.shop_bill_id = shop_bill_id;
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

        public Integer getBill_type() {
            return bill_type;
        }

        public void setBill_type(Integer bill_type) {
            this.bill_type = bill_type;
        }

        public Integer getApply_id() {
            return apply_id;
        }

        public void setApply_id(Integer apply_id) {
            this.apply_id = apply_id;
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
