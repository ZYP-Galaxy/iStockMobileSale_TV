package com.abbp.istockmobilesalenew;

public class RefDocid {
    private long tranid;
    private String docid;
    private String date;

    public RefDocid(long tranid, String docid, String date) {
        this.tranid = tranid;
        this.docid = docid;
        this.date = date;
    }

    public long getTranid() {
        return tranid;
    }

    public void setTranid(long tranid) {
        this.tranid = tranid;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
