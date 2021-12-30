package com.abbp.istockmobilesalenew;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class Cash {
    int cash_id;
    String name;
    int userid;

    public Cash(int cash_id, String name, int userid) {
        this.cash_id = cash_id;
        this.name = name;
        this.userid = userid;
    }

    public int getCash_id() {
        return cash_id;
    }

    public void setCash_id(int cash_id) {
        this.cash_id = cash_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return name;
    }
}
