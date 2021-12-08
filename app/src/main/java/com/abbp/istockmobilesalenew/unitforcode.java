package com.abbp.istockmobilesalenew;

public class unitforcode {
    int code;
    String usr_code;
    int unit_type;
    int unit;
    String unitname;
    String shortdes;
    double sale_price;
    double sale_Price1;
    double sale_price2;
    double sale_price3;
    double smallest_unit_qty;

    public unitforcode(int code, String usr_code, int unit_type, int unit, String unitname, String shortdes, double sale_price, double smallest_unit_qty, double sale_Price1, double sale_price2, double sale_price3) {
        this.code = code;
        this.usr_code = usr_code;
        this.unit_type = unit_type;
        this.unit = unit;
        this.unitname = unitname;
        this.shortdes = shortdes;
        this.sale_price = sale_price;
        this.smallest_unit_qty = smallest_unit_qty;
        this.sale_Price1=sale_Price1;
        this.sale_price2=sale_price2;
        this.sale_price3=sale_price3;
    }

    public double getSale_Price1() {
        return sale_Price1;
    }

    public void setSale_Price1(double sale_Price1) {
        this.sale_Price1 = sale_Price1;
    }

    public double getSale_price2() {
        return sale_price2;
    }

    public void setSale_price2(double sale_price2) {
        this.sale_price2 = sale_price2;
    }

    public double getSale_price3() {
        return sale_price3;
    }

    public void setSale_price3(double sale_price3) {
        this.sale_price3 = sale_price3;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUsr_code() {
        return usr_code;
    }

    public void setUsr_code(String usr_code) {
        this.usr_code = usr_code;
    }

    public int getUnit_type() {
        return unit_type;
    }

    public void setUnit_type(int unit_type) {
        this.unit_type = unit_type;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public String getShortdes() {
        return shortdes;
    }

    public void setShortdes(String shortdes) {
        this.shortdes = shortdes;
    }

    public double getSale_price() {
        return sale_price;
    }

    public void setSale_price(double sale_price) {
        this.sale_price = sale_price;
    }

    public double getSmallest_unit_qty() {
        return smallest_unit_qty;
    }

    public void setSmallest_unit_qty(double smallest_unit_qty) {
        this.smallest_unit_qty = smallest_unit_qty;
    }
}
