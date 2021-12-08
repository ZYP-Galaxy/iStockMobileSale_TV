package com.abbp.istockmobilesalenew;

public class posuser {
    private int userid;
    private String name;
    private int Confirm_PrintVou;
    private int allow_priceLevel;
    private int select_location;
    private int select_customer;
    private int change_date;
    private int change_price;
    private String knockcode;
    private  int def_locationid;
    private  int def_payment;
    private int tax;
    private int discount;
    private  int Allow_Over_Credit_Limit;
    private  int def_cashid;
    private  String Cashier_Printer;
    private  int Cashier_PrinterType;
    private  int use_offline;
    private  int  allow_so_update;
    private int use_oil;
    public posuser(int userid, String name, int confirm_PrintVou, int allow_priceLevel, int select_location, int select_customer, int change_date,int tax,int discount, int change_price, String knockcode, int def_locationid,int def_payment,int Allow_Over_Credit_Limit,int def_cashid,String Cashier_Printer,int Cashier_PrinterType,int use_offline,int  allow_so_update,int use_oil) {
        this.userid = userid;
        this.name = name;
        Confirm_PrintVou = confirm_PrintVou;
        this.allow_priceLevel = allow_priceLevel;
        this.select_location = select_location;
        this.select_customer = select_customer;
        this.change_date = change_date;
        this.tax=tax;
        this.discount=discount;
        this.change_price = change_price;
        this.knockcode = knockcode;
        this.def_locationid = def_locationid;
        this.def_payment=def_payment;
        this.Allow_Over_Credit_Limit=Allow_Over_Credit_Limit;
        this.def_cashid=def_cashid;
        this.Cashier_Printer=Cashier_Printer;
        this.Cashier_PrinterType=Cashier_PrinterType;
        this.use_offline=use_offline;
        this.allow_so_update=allow_so_update;
        this.use_oil=use_oil;
    }
    public posuser(int userid, String name){
        this.userid=userid;
        this.name=name;
    }

    public int getUse_oil() {
        return use_oil;
    }

    public void setUse_oil(int use_oil) {
        this.use_oil = use_oil;
    }

    public int getAllow_so_update() {
        return allow_so_update;
    }

    public void setAllow_so_update(int allow_so_update) {
        this.allow_so_update = allow_so_update;
    }

    public int getUse_offline() {
        return use_offline;
    }

    public void setUse_offline(int use_offline) {
        this.use_offline = use_offline;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getConfirm_PrintVou() {
        return Confirm_PrintVou;
    }

    public void setConfirm_PrintVou(int confirm_PrintVou) {
        Confirm_PrintVou = confirm_PrintVou;
    }

    public int getAllow_priceLevel() {
        return allow_priceLevel;
    }

    public void setAllow_priceLevel(int allow_priceLevel) {
        this.allow_priceLevel = allow_priceLevel;
    }

    public int getSelect_location() {
        return select_location;
    }

    public void setSelect_location(int select_location) {
        this.select_location = select_location;
    }

    public int getSelect_customer() {
        return select_customer;
    }

    public void setSelect_customer(int select_customer) {
        this.select_customer = select_customer;
    }

    public int getChange_date() {
        return change_date;
    }

    public void setChange_date(int change_date) {
        this.change_date = change_date;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getChange_price() {
        return change_price;
    }

    public void setChange_price(int change_price) {
        this.change_price = change_price;
    }

    public String getKnockcode() {
        return knockcode;
    }

    public void setKnockcode(String knockcode) {
        this.knockcode = knockcode;
    }

    public int getDef_locationid() {
        return def_locationid;
    }

    public void setDef_locationid(int def_locationid) {
        this.def_locationid = def_locationid;
    }


    public int getDef_payment() {
        return def_payment;
    }

    public void setDef_payment(int def_payment) {
        this.def_payment = def_payment;
    }

    public int getAllow_Over_Credit_Limit() {
        return Allow_Over_Credit_Limit;
    }

    public void setAllow_Over_Credit_Limit(int Allow_Over_Credit_Limit) {
        this.Allow_Over_Credit_Limit = Allow_Over_Credit_Limit;
    }

    public int getDef_cashid() {
        return def_cashid;
    }

    public void setDef_cashid(int def_cashid) {
        this.def_cashid = def_cashid;
    }


    public String getCashier_Printer() {
        return Cashier_Printer;
    }

    public void setCashier_Printer(String cashier_Printer) {
        Cashier_Printer = cashier_Printer;
    }

    public int getCashier_PrinterType() {
        return Cashier_PrinterType;
    }

    public void setCashier_PrinterType(int cashier_PrinterType) {
        Cashier_PrinterType = cashier_PrinterType;
    }
}
