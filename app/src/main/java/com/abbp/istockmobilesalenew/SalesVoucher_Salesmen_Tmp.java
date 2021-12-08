package com.abbp.istockmobilesalenew;

public class SalesVoucher_Salesmen_Tmp {

private long  Sales_TranID;
private long Salesmen_ID;
private int rmt_copy;
private int userid;

    public SalesVoucher_Salesmen_Tmp(long sales_TranID, long salesmen_ID, int rmt_copy, int userid) {
        Sales_TranID = sales_TranID;
        Salesmen_ID = salesmen_ID;
        this.rmt_copy = rmt_copy;
        this.userid = userid;
    }

    public long getSales_TranID() {
        return Sales_TranID;
    }

    public void setSales_TranID(long sales_TranID) {
        Sales_TranID = sales_TranID;
    }

    public long getSalesmen_ID() {
        return Salesmen_ID;
    }

    public void setSalesmen_ID(long salesmen_ID) {
        Salesmen_ID = salesmen_ID;
    }

    public int getRmt_copy() {
        return rmt_copy;
    }

    public void setRmt_copy(int rmt_copy) {
        this.rmt_copy = rmt_copy;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
