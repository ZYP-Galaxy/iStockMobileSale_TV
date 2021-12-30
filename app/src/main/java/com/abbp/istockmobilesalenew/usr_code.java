package com.abbp.istockmobilesalenew;

public class usr_code {
    private String usr_code;
    private String description;
    private double saleprice;
    private String classname;
    private String path;

    public usr_code(String usr_code, String description) {
        this.usr_code = usr_code;
        this.description = description;

    }

    public usr_code(String usr_code, String description, Double saleprice) {
        this.usr_code = usr_code;
        this.description = description;
        this.saleprice = saleprice;

    }

    public usr_code(String usr_code, String description, String path) {
        this.usr_code = usr_code;
        this.description = description;
        this.path = path;
    }

    public String getUsr_code() {
        return usr_code;
    }

    public void setUsr_code(String usr_code) {
        this.usr_code = usr_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(double saleprice) {
        this.saleprice = saleprice;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
