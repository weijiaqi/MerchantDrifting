package com.merchant.drifting.mvp.model.entity;

import java.util.List;

public class ShopStaticOrderEntity {


    private List<RankingBean> ranking;
    private Integer this_week_ratio;
    private Integer this_week_total;
    private Integer today_ratio;
    private Integer today_total;
    private double turnover;
    private double this_week_turnover;

    public double getThis_week_turnover() {
        return this_week_turnover;
    }

    public void setThis_week_turnover(double this_week_turnover) {
        this.this_week_turnover = this_week_turnover;
    }

    private List<TrendingBean> trending;

    public List<RankingBean> getRanking() {
        return ranking;
    }

    public void setRanking(List<RankingBean> ranking) {
        this.ranking = ranking;
    }

    public Integer getThis_week_ratio() {
        return this_week_ratio;
    }

    public void setThis_week_ratio(Integer this_week_ratio) {
        this.this_week_ratio = this_week_ratio;
    }

    public Integer getThis_week_total() {
        return this_week_total;
    }

    public void setThis_week_total(Integer this_week_total) {
        this.this_week_total = this_week_total;
    }

    public Integer getToday_ratio() {
        return today_ratio;
    }

    public void setToday_ratio(Integer today_ratio) {
        this.today_ratio = today_ratio;
    }

    public Integer getToday_total() {
        return today_total;
    }

    public void setToday_total(Integer today_total) {
        this.today_total = today_total;
    }

    public double getTurnover() {
        return turnover;
    }

    public void setTurnover(double turnover) {
        this.turnover = turnover;
    }

    public List<TrendingBean> getTrending() {
        return trending;
    }

    public void setTrending(List<TrendingBean> trending) {
        this.trending = trending;
    }

    public static class RankingBean {
        private Integer sales_volume;
        private Integer sku_id;
        private String sku_name;

        public Integer getSales_volume() {
            return sales_volume;
        }

        public void setSales_volume(Integer sales_volume) {
            this.sales_volume = sales_volume;
        }

        public Integer getSku_id() {
            return sku_id;
        }

        public void setSku_id(Integer sku_id) {
            this.sku_id = sku_id;
        }

        public String getSku_name() {
            return sku_name;
        }

        public void setSku_name(String sku_name) {
            this.sku_name = sku_name;
        }
    }

    public static class TrendingBean {
        private String sales_volume;
        private String yesterday_sales_volume;
        private String hours;

        public String getSales_volume() {
            return sales_volume;
        }

        public void setSales_volume(String sales_volume) {
            this.sales_volume = sales_volume;
        }

        public String getYesterday_sales_volume() {
            return yesterday_sales_volume;
        }

        public void setYesterday_sales_volume(String yesterday_sales_volume) {
            this.yesterday_sales_volume = yesterday_sales_volume;
        }

        public String getHours() {
            return hours;
        }

        public void setHours(String hours) {
            this.hours = hours;
        }
    }

}
