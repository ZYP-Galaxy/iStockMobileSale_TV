package com.abbp.istockmobilesalenew;

public class brand {
    long brandID;
    String brandName;

    public brand( long BrandID, String BrandName) {
        this.brandID = BrandID;
        this.brandName = BrandName;

    }
    public brand(String BrandName){
        this.brandName = BrandName;
    }

    public long getBrandID() {
        return brandID;
    }

    public String getBrandName() {
        return brandName;
    }
}
