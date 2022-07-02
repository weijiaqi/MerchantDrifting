package com.merchant.drifting.mvp.model.entity;

import java.util.List;

public class HaveShopEntity {

        private List<ShopsBean> shops;
        private Integer total;

        public List<ShopsBean> getShops() {
            return shops;
        }

        public void setShops(List<ShopsBean> shops) {
            this.shops = shops;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public static class ShopsBean {
            private Integer shop_id;
            private String shop_name;

            public Integer getShop_id() {
                return shop_id;
            }

            public void setShop_id(Integer shop_id) {
                this.shop_id = shop_id;
            }

            public String getShop_name() {
                return shop_name;
            }

            public void setShop_name(String shop_name) {
                this.shop_name = shop_name;
            }
        }

}
