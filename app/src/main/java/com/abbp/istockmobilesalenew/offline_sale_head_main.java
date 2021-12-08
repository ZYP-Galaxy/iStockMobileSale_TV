package com.abbp.istockmobilesalenew;

public class offline_sale_head_main {
    private long tranid;
    private  int userid;
    private String docid;
    private String date;
    private String invoice_no;
    private boolean deliverValue;//added by YLT on [15-06-2020]
    private  long locationid;
    private long customerid;
    private int def_cashid;
    private  int pay_type;
    private  int due_in_days;
    private  int currency;
    private double discount;
    private String Remark;
    private  double paid_amount;
    private double invoice_amount;
    private double invoice_qty;
    private double foc_amount;
    private double istemdis_amount;
    private double discount_per;
    private  double tax_amount;
    private  double tax_per;
    private long townshipid;
    private double net_amount;

    public offline_sale_head_main(long tranid, int userid, String docid, String date, String invoice_no,Boolean deliverValue, String headremark, long locationid, long customerid,int def_cashid, int pay_type,int due_in_days, int currency, double discount, double paid_amount, double invoice_amount, double invoice_qty, double foc_amount, double istemdis_amount, double discount_per, double tax_amount, double tax_per,double net_amount) {
        this.tranid = tranid;
        this.userid = userid;
        this.docid = docid;
        this.date = date;
        this.invoice_no = invoice_no;
        this.deliverValue=deliverValue;//added by YLT on [15-06-2020]
        this.locationid = locationid;
        this.customerid = customerid;
        this.def_cashid=def_cashid;
        this.pay_type = pay_type;
        this.due_in_days=due_in_days;
        this.currency = currency;
        this.discount = discount;
        this.Remark=headremark;
        this.paid_amount = paid_amount;
        this.invoice_amount = invoice_amount;
        this.invoice_qty = invoice_qty;
        this.foc_amount = foc_amount;
        this.istemdis_amount = istemdis_amount;
        this.discount_per = discount_per;
        this.tax_amount = tax_amount;
        this.tax_per = tax_per;
        this.townshipid=townshipid;
        this.net_amount=net_amount;
    }


    public long getTranid() {
        return tranid;
    }

    public void setTranid(long tranid) {
        this.tranid = tranid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInvoice_no() {
        return invoice_no;
    }

    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }

    public boolean isDeliverValue() {
        return deliverValue;
    }

    public void setDeliverValue(boolean deliverValue) {
        this.deliverValue = deliverValue;
    }

    public long getLocationid() {
        return locationid;
    }

    public void setLocationid(long locationid) {
        this.locationid = locationid;
    }

    public long getCustomerid() {
        return customerid;
    }

    public void setCustomerid(long customerid) {
        this.customerid = customerid;
    }

    public int getDef_cashid() {
        return def_cashid;
    }

    public void setDef_cashid(int def_cashid) {
        this.def_cashid = def_cashid;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public int getDue_in_days() {
        return due_in_days;
    }

    public void setDue_in_days(int due_in_days) {
        this.due_in_days = due_in_days;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public double getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(double paid_amount) {
        this.paid_amount = paid_amount;
    }

    public double getInvoice_amount() {
        return invoice_amount;
    }

    public void setInvoice_amount(double invoice_amount) {
        this.invoice_amount = invoice_amount;
    }

    public double getInvoice_qty() {
        return invoice_qty;
    }

    public void setInvoice_qty(double invoice_qty) {
        this.invoice_qty = invoice_qty;
    }

    public double getFoc_amount() {
        return foc_amount;
    }

    public void setFoc_amount(double foc_amount) {
        this.foc_amount = foc_amount;
    }

    public double getIstemdis_amount() {
        return istemdis_amount;
    }

    public void setIstemdis_amount(double istemdis_amount) {
        this.istemdis_amount = istemdis_amount;
    }

    public double getDiscount_per() {
        return discount_per;
    }

    public void setDiscount_per(double discount_per) {
        this.discount_per = discount_per;
    }

    public double getTax_amount() {
        return tax_amount;
    }

    public void setTax_amount(double tax_amount) {
        this.tax_amount = tax_amount;
    }

    public double getTax_per() {
        return tax_per;
    }

    public void setTax_per(double tax_per) {
        this.tax_per = tax_per;
    }

    public long getTownshipid() {
        return townshipid;
    }

    public void setTownshipid(long townshipid) {
        this.townshipid = townshipid;
    }

    public double getNet_amount() {
        return net_amount;
    }

    public void setNet_amount(double net_amount) {
        this.net_amount = net_amount;
    }
}
