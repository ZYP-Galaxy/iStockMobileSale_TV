package com.abbp.istockmobilesalenew;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class Location {
    private long locationid;
    private String name;
    private String shortdes;
    private long branchid;

    public Location(long locationid, String name, String shortdes, long branchid) {
        this.locationid = locationid;
        this.name = name;
        this.shortdes = shortdes;
        this.branchid = branchid;
    }

    public long getLocationid() {
        return locationid;
    }

    public void setLocationid(long locationid) {
        this.locationid = locationid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortdes() {
        return shortdes;
    }

    public void setShortdes(String shortdes) {
        this.shortdes = shortdes;
    }

    public long getBranchid() {
        return branchid;
    }

    public void setBranchid(long branchid) {
        this.branchid = branchid;
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return name;
    }
}
