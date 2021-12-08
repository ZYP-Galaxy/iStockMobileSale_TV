package com.abbp.istockmobilesalenew;

public class user {
    private int userid;
    private String name;

    public user(int userid, String name) {
        this.userid = userid;
        this.name = name;
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
}
