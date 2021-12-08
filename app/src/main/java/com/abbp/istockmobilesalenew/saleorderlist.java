package com.abbp.istockmobilesalenew;

public class saleorderlist {
    private String date;
    private String docid;
    private String pay_type;
    private String currency;
    private double net_amount;
    private String usershort;
    private String customer_name;
    private int cancel=0;

    public int getTranid() {
        return tranid;
    }

    private int tranid;


    public saleorderlist(int tranid, String date, String docid, String pay_type, String currency, double net_amount, String usershort,String customer_name,int cancel) {

        this.tranid=tranid;
        this.date = date;
        this.docid = docid;
        this.pay_type = pay_type;
        this.currency = currency;
        this.net_amount=net_amount;
        this.usershort=usershort;
        this.customer_name=customer_name;
        this.cancel=cancel;
    }

    public String getDate() {
        return date;
    }
    public  int getCancel()
    {
        return cancel;
    }

    public String getDocid() {
        return docid;
    }

    public String getPay_type() {
        return pay_type;
    }

    public String getCurrency() {
        return currency;
    }

    public double getNet_amount() {
        return net_amount;
    }

    public String getUsershort() {
        return usershort;
    }

    public void setUsershort(String usershort) {
        this.usershort = usershort;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }
}
