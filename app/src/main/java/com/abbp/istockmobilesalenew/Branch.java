package com.abbp.istockmobilesalenew;

public class Branch {
    private long BranchID;
    private String Branchname;
    private String BranchShort;

    public  Branch(long BranchID, String Branchname, String BranchShort)
    {
        this.BranchID=BranchID;
        this.Branchname=Branchname;
        this.BranchShort=BranchShort;
    }

    public long getBranchID() {
        return BranchID;
    }

    public void setBranchID(long locationid) {
        this.BranchID = BranchID;
    }

    public String getBranchname() {
        return Branchname;
    }

    public void setBranchname(String name) {
        this.Branchname = Branchname;
    }

    public String getBranchShort() {
        return BranchShort;
    }

    public void setBranchShort(String name) {
        this.BranchShort = BranchShort;
    }
}
