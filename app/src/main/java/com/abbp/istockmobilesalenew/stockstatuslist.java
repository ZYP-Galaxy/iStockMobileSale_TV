package com.abbp.istockmobilesalenew;

public class stockstatuslist {
    private String usrcode;
    private String description;
    private double saleAmount;
    private  String BalanceQty;
    private  String location;

    public  stockstatuslist( String usrcode,String description,double saleAmount,String BalanceQty , String location)
    {
        this.usrcode= usrcode;
        this.description=description;
        this.saleAmount=saleAmount;
        this.BalanceQty=BalanceQty;
        this.location=location;
    }
    public String getUsrcode() {
        return usrcode;
    }
    public  String getDescription() {return description;}
    public  double getSaleAmount() {return  saleAmount;}
    public String getBalanceQty(){return BalanceQty;}
    public  String getLocation() {return location;}
}
