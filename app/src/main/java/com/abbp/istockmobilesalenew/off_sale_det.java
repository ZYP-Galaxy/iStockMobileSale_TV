package com.abbp.istockmobilesalenew;

public class off_sale_det {
        private long tranid;
        private String date;
        private double unit_qty;
        private int open_price;
        private  double qty;
        private  double sale_price;
        private  double dis_price;
        private  long dis_type;
        private   double dis_percent;
        private String Remark;
        private    int unt_type;
        private   long code;
        private  int sr;
        private  int srno;
        private String unit_short;
        private String desc;
        private  int CalNoTax;
        private String PriceLevel;
        private  double sqty;
        private double sprice;
        private String is_exported;

        public off_sale_det(long tranid, int sr, int srno, String date, double unit_qty, double qty, int unit_type, double sale_price, double dis_price, long dis_type, double discount, String detremark, long code, String priceLevel, double sqty, double sprice)
        {
            this.sr=sr;
            this.srno=srno;
            this.tranid = tranid;
            this.date = date;
            this.unit_qty = unit_qty;
            this.qty = qty;
            this.unt_type=unit_type;
            this.sale_price = sale_price;
            this.dis_price = dis_price;
            this.dis_type = dis_type;
            this.dis_percent = discount;
            this.Remark=detremark;
            this.code=code;
            this.PriceLevel=priceLevel;
            this.sqty=sqty;
            this.sprice=sprice;
            this.is_exported=" ";
        }

    public int getSrno() {
        return srno;
    }

    public void setSrno(int srno) {
        this.srno = srno;
    }

    public double getSqty() {
        return sqty;
    }

    public void setSqty(double sqty) {
        this.sqty = sqty;
    }

    public double getSprice() {
        return sprice;
    }

    public void setSprice(double sprice) {
        this.sprice = sprice;
    }

    public String getIs_exported() {
        return is_exported;
    }

    public void setIs_exported(String is_exported) {
        this.is_exported = is_exported;
    }

    public String getPriceLevel() {
            return PriceLevel;
        }

        public void setPriceLevel(String priceLevel) {
            PriceLevel = priceLevel;
        }

        public String getUnit_short() {
            return unit_short;
        }

        public void setUnit_short(String unit_short) {
            this.unit_short = unit_short;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getSr() {
            return sr;
        }

        public void setSr(int sr) {
            this.sr = sr;
        }

        public int getUnt_type() {
            return unt_type;
        }

        public void setUnt_type(int unt_type) {
            this.unt_type = unt_type;
        }

        public long getTranid() {
            return tranid;
        }

        public void setTranid(long tranid) {
            this.tranid = tranid;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public double getUnit_qty() {
            return unit_qty;
        }

        public void setUnit_qty(double unit_qty) {
            this.unit_qty = unit_qty;
        }

        public double getQty() {
            return qty;
        }

        public void setQty(double qty) {
            this.qty = qty;
        }

        public int getOpen_price() {
            return open_price;
        }

        public void setOpen_price(int open_price) {
            this.open_price = open_price;
        }

        public double getSale_price() {
            return sale_price;
        }

        public void setSale_price(double sale_price) {
            this.sale_price = sale_price;
        }

        public double getDis_price() {
            return dis_price;
        }

        public void setDis_price(double dis_price) {
            this.dis_price = dis_price;
        }

        public long getDis_type() {
            return dis_type;
        }

        public void setDis_type(long dis_type) {
            this.dis_type = dis_type;
        }

        public double getDis_percent() {
            return dis_percent;
        }

        public void setDis_percent(double discount) {
            this.dis_percent = discount;
        }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        this.Remark = remark;
    }

    public long getCode() {
            return code;
        }

        public void setCode(long code) {
            this.code = code;
        }

        public int getCalNoTax() {
            return CalNoTax;
        }

        public void setCalNoTax(int calNoTax) {
            CalNoTax = calNoTax;
        }

    }


