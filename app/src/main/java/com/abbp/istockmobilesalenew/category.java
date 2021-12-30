package com.abbp.istockmobilesalenew;

public class category {
    long category;
    long classid;
    String name;

    public category(long category, long classid, String name) {
        this.category = category;
        this.classid = classid;
        this.name = name;
    }
    public category(String name){
        this.name=name;
    }

    public long getCategory() {
        return category;
    }

    public long getClassid() {
        return classid;
    }

    public String getName() {
        return name;
    }


}
