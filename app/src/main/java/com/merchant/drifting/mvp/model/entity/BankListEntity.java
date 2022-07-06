package com.merchant.drifting.mvp.model.entity;

import java.util.List;

public class BankListEntity {

        private Integer bank_card_id;
        private Integer business_id;
        private String name;
        private String card_no;
        private String bank_name;
        private String branch_name;
        private String mobile;
        private Integer created_at_int;
        private Integer updated_at_int;

        public Integer getBank_card_id() {
            return bank_card_id;
        }

        public void setBank_card_id(Integer bank_card_id) {
            this.bank_card_id = bank_card_id;
        }

        public Integer getBusiness_id() {
            return business_id;
        }

        public void setBusiness_id(Integer business_id) {
            this.business_id = business_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCard_no() {
            return card_no;
        }

        public void setCard_no(String card_no) {
            this.card_no = card_no;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getBranch_name() {
            return branch_name;
        }

        public void setBranch_name(String branch_name) {
            this.branch_name = branch_name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
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
