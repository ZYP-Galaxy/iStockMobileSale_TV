package com.abbp.istockmobilesalenew;

public class pay_type {
    private  int pay_type;
    private String name;
    private String shortdes;

    public pay_type(int pay_type, String name, String shortdes) {
        this.pay_type = pay_type;
        this.name = name;
        this.shortdes = shortdes;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortdes() {
        return shortdes;
    }

    public void setShortdes(String shortdes) {
        this.shortdes = shortdes;
    }
}
