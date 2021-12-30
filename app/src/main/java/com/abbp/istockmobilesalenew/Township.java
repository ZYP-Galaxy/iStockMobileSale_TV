package com.abbp.istockmobilesalenew;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class Township {
    private long Townshipid;
    private String name;
    private String TownshipCode;

    public Township(long townshipid, String name, String townshipCode) {
        Townshipid = townshipid;
        this.name = name;
        TownshipCode = townshipCode;
    }

    public long getTownshipid() {
        return Townshipid;
    }

    public void setTownshipid(long townshipid) {
        Townshipid = townshipid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTownshipCode() {
        return TownshipCode;
    }

    public void setTownshipCode(String townshipCode) {
        TownshipCode = townshipCode;
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return name;
    }
}
