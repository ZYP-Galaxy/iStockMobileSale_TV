package com.abbp.istockmobilesalenew;

public class so_head {
    long so_id;
    int pay_type;
    double discount;
    double discount_per;

    public so_head(long so_id, int pay_type, double discount, double discount_per) {
        this.so_id = so_id;
        this.pay_type = pay_type;
        this.discount = discount;
        this.discount_per = discount_per;
    }

    public long getSo_id() {
        return so_id;
    }

    public void setSo_id(long so_id) {
        this.so_id = so_id;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDiscount_per() {
        return discount_per;
    }

    public void setDiscount_per(double discount_per) {
        this.discount_per = discount_per;
    }
}
