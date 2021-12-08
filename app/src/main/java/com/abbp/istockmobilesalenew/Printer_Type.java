package com.abbp.istockmobilesalenew;

public class Printer_Type {
    private  int printer_type_id;
    private  String name;

    public Printer_Type(int printer_type_id, String name) {
        this.printer_type_id = printer_type_id;
        this.name = name;
    }

    public int getPrinter_type_id() {
        return printer_type_id;
    }

    public void setPrinter_type_id(int printer_type_id) {
        this.printer_type_id = printer_type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
