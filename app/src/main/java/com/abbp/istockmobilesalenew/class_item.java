package com.abbp.istockmobilesalenew;

public class class_item {
    long classid;

    public long getClassid() {
        return classid;
    }

    public String getName() {
        return name;
    }

    String name;
    public class_item(long classid, String name) {
        this.classid = classid;
        this.name = name;
    }


}
