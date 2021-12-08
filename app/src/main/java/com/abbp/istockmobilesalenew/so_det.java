package com.abbp.istockmobilesalenew;

public class so_det {
    private double unit_qty;
    private int unit_type;
    private double price;
    private double dis_price;
    private long dis_type;
    private double discount;
    private long code;
    private String unit_short;
    private String desc;
    private String pricelevel;
    private long so_id;
    private int so_sr;
    private String usr_code;

    public so_det(double unit_qty, int unit_type, double price, double dis_price, long dis_type, double discount, long code, String unit_short, String desc, String pricelevel, long so_id, int so_sr,String usr_code) {
        this.unit_qty = unit_qty;
        this.unit_type = unit_type;
        this.price = price;
        this.dis_price = dis_price;
        this.dis_type = dis_type;
        this.discount = discount;
        this.code = code;
        this.unit_short = unit_short;
        this.desc = desc;
        this.pricelevel = pricelevel;
        this.so_id = so_id;
        this.so_sr = so_sr;
        this.usr_code=usr_code;
    }

    public String getUsr_code() {
        return usr_code;
    }

    public void setUsr_code(String usr_code) {
        this.usr_code = usr_code;
    }

    public double getUnit_qty() {
        return unit_qty;
    }

    public void setUnit_qty(double unit_qty) {
        this.unit_qty = unit_qty;
    }

    public int getUnit_type() {
        return unit_type;
    }

    public void setUnit_type(int unit_type) {
        this.unit_type = unit_type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDis_price() {
        return dis_price;
    }

    public void setDis_price(double dis_price) {
        this.dis_price = dis_price;
    }

    public long getDis_type() {
        return dis_type;
    }

    public void setDis_type(long dis_type) {
        this.dis_type = dis_type;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getUnit_short() {
        return unit_short;
    }

    public void setUnit_short(String unit_short) {
        this.unit_short = unit_short;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPricelevel() {
        return pricelevel;
    }

    public void setPricelevel(String pricelevel) {
        this.pricelevel = pricelevel;
    }

    public long getSo_id() {
        return so_id;
    }

    public void setSo_id(long so_id) {
        this.so_id = so_id;
    }

    public int getSo_sr() {
        return so_sr;
    }

    public void setSo_sr(int so_sr) {
        this.so_sr = so_sr;
    }
}
