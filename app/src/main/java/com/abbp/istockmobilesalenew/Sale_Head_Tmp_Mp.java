package com.abbp.istockmobilesalenew;

public class Sale_Head_Tmp_Mp {
    private  long tranid;
    private  int currency;
    private  double exg_rate;
    private  double amount;
    private  double change;

    public Sale_Head_Tmp_Mp(long tranid, int currency, double exg_rate, double amount, double change) {
        this.tranid = tranid;
        this.currency = currency;
        this.exg_rate = exg_rate;
        this.amount = amount;
        this.change = change;
     }

    public long getTranid() {
        return tranid;
    }

    public void setTranid(long tranid) {
        this.tranid = tranid;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public double getExg_rate() {
        return exg_rate;
    }

    public void setExg_rate(double exg_rate) {
        this.exg_rate = exg_rate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }
}
