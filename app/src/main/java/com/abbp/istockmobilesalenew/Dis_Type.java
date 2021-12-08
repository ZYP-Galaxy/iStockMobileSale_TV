package com.abbp.istockmobilesalenew;

public class Dis_Type {
    long dis_type;
    String name;
    String shortdes;
    long discount;

    public Dis_Type(long dis_type, String name, String shortdes, long discount) {
        this.dis_type = dis_type;
        this.name = name;
        this.shortdes = shortdes;
        this.discount = discount;
    }

    public long getDis_type() {
        return dis_type;
    }

    public void setDis_type(long dis_type) {
        this.dis_type = dis_type;
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

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }



}
