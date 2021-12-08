package com.abbp.istockmobilesalenew;

import java.io.Serializable;

public class Salesmen implements Serializable {
    private  long Salesmen_Id;
    private String Salesmen_Name;
    private String Salesmen_Short;
    private boolean isChecked = false;
    private long branchid;
    public Salesmen(long salesmen_Id, String salesmen_Name, String salesmen_Short) {
        Salesmen_Id = salesmen_Id;
        Salesmen_Name = salesmen_Name;
        Salesmen_Short = salesmen_Short;
    }

    public Salesmen(long salesmen_Id, String salesmen_Name, String salesmen_Short, long branchid) {
        Salesmen_Id = salesmen_Id;
        Salesmen_Name = salesmen_Name;
        Salesmen_Short = salesmen_Short;
        this.branchid = branchid;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public long getSalesmen_Id() {
        return Salesmen_Id;
    }

    public void setSalesmen_Id(long salesmen_Id) {
        Salesmen_Id = salesmen_Id;
    }

    public String getSalesmen_Name() {
        return Salesmen_Name;
    }

    public void setSalesmen_Name(String salesmen_Name) {
        Salesmen_Name = salesmen_Name;
    }

    public String getSalesmen_Short() {
        return Salesmen_Short;
    }

    public void setSalesmen_Short(String salesmen_Short) {
        Salesmen_Short = salesmen_Short;
    }

    public long getBranchid() {
        return branchid;
    }

    public void setBranchid(long branchid) {
        this.branchid = branchid;
    }
}
