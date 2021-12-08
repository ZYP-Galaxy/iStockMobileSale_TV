package com.abbp.istockmobilesalenew;

public class usr_code  {
    private String usr_code;
    private String description;
    private String classname;
    private String path;
    public usr_code(String usr_code, String description) {
        this.usr_code = usr_code;
        this.description = description;

    }

    public usr_code(String usr_code,String description,String path){
        this.usr_code=usr_code;
        this.description=description;
        this.path=path;
    }


    public String getUsr_code() {
        return usr_code;
    }

    public String getDescription() {
        return description;
    }

    public String getPath() {
        return path;
    }
}
