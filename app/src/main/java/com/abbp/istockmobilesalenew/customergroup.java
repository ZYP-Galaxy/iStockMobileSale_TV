package com.abbp.istockmobilesalenew;

public class customergroup {
    private   long custgroupid;
    private String name;
    private String Shortname;

    public long getCustgroupid() {
        return custgroupid;
    }

    public void setCustgroupid(long custgroupid) {
        this.custgroupid = custgroupid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortname() {
        return Shortname;
    }

    public void setShortname(String shortname) {
        Shortname = shortname;
    }

    public customergroup(long custgroupid, String name, String shortname) {
        this.custgroupid = custgroupid;
        this.name = name;
        Shortname = shortname;
    }

}
