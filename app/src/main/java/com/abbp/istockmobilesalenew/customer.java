package com.abbp.istockmobilesalenew;

public class customer {
    long customerid;
    String name;
    String customercode;
    boolean credit;
    double custDis;
    int due_in_days;
    double credit_limit;

    public long getCustomerid() {
        return customerid;
    }

    public String getName() {
        return name;
    }

    public String getCustomercode() {
        return customercode;
    }

    public boolean isCredit() {
        return credit;
    }

    public void setCustomerid(long customerid) {
        this.customerid = customerid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCustDis() {
        return custDis;
    }

    public void setCustDis(double custDis) {
        this.custDis = custDis;
    }

    public void setCustomercode(String customercode) {
        this.customercode = customercode;
    }

    public void setCredit(boolean credit) {
        this.credit = credit;
    }

    public int getDue_in_days() {
        return due_in_days;
    }

    public void setDue_in_days(int due_in_days) {
        this.due_in_days = due_in_days;
    }

    public double getCredit_limit() {
        return credit_limit;
    }

    public void setCredit_limit(double credit_limit) {
        this.credit_limit = credit_limit;
    }

    public customer(long customerid, String name, String customercode, boolean credit,double custDis,int due_in_days,double credit_limit) {
        this.customerid = customerid;
        this.name = name;
        this.customercode = customercode;
        this.credit = credit;
        this.custDis=custDis;
        this.due_in_days=due_in_days;
        this.credit_limit=credit_limit;
    }
}
