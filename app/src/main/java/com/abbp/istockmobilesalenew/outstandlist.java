package com.abbp.istockmobilesalenew;

import java.nio.channels.Pipe;

public class outstandlist {
    private  String Customer;
    private  String Currency;
    private double Opening;
    private double Sale;
    private double ReturnIn;
    private double Discount;
    private double Paid;
    private double Balance;

    public outstandlist( String Customer,String Currency, double Opening, double Sale,double ReturnIn,double Discount,double Paid,double Balance)

    {
        this.Customer=Customer;
        this.Currency=Currency;
        this.Opening=Opening;
        this.Sale=Sale;
        this.ReturnIn=ReturnIn;
        this.Discount=Discount;
        this.Paid= Paid;
        this.Balance=Balance;
    }
    public String getMerchantInform() {
        return Customer;
    }
    public String getCurrency() {
        return Currency;
    }
    public double getOpening() {
        return Opening;
    }
    public double getSale() {
        return Sale;
    }
    public double getReturnIn() {
        return ReturnIn;
    }
    public double getDiscount() {
        return Discount;
    }
    public double getPaid() {
        return Paid;
    }
    public double getClosing() {
        return Balance;
    }



}
