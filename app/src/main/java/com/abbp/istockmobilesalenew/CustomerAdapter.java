package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.database.Cursor;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {

    Context context;
    ArrayList<customer> data=new ArrayList<>();
    Button btn;
    Button btnpay;
androidx.appcompat.app.AlertDialog da;
    public static String formname;//added by YLT
        public CustomerAdapter(Context context, ArrayList<customer> data, Button btn, Button btnpay, androidx.appcompat.app.AlertDialog da) {
            this.data = data;
            this.context = context;
            this.btn=btn;
            this.da=da;
            this.btnpay=btnpay;
            formname="Sale";
    }
    public CustomerAdapter(Context context, ArrayList<customer> data, Button btn, Button btnpay, androidx.appcompat.app.AlertDialog da, String frm) {
        this.data = data;
        this.context = context;
        this.btn=btn;
        this.da=da;
        this.btnpay=btnpay;
        formname=frm;
    }

    @Override
    public CustomerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf= LayoutInflater.from(parent.getContext());
        View v=lf.inflate(R.layout.headerbinding,parent,false);
        return new CustomerAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CustomerAdapter.MyViewHolder holder, int position) {
            if(formname=="SaleOrder")
            {
                holder.btn.setText(" " + data.get(position).getName());
                holder.btn.setBackgroundResource(R.drawable.usercodegradiant);
                holder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        btn.setText( data.get(position).getName());
                        saleorder_entry.sh.get(0).setCustomerid(data.get(position).getCustomerid());
                        saleorder_entry.isCreditcustomer = data.get(position).isCredit();
                        saleorder_entry.custDis = data.get(position).getCustDis();
                        saleorder_entry.sh.get(0).setDiscount_per(saleorder_entry.custDis);
                        saleorder_entry.due_in_days = data.get(position).getDue_in_days();
                        int due_in_days = saleorder_entry.due_in_days;
                        int def_payment = frmlogin.def_payment;
                        saleorder_entry.credit_limit = data.get(position).getCredit_limit();
                        if (saleorder_entry.isCreditcustomer) {
                            if (def_payment == 2) {
                                saleorder_entry.sh.get(0).setPay_type(2);
                                saleorder_entry.sh.get(0).setDue_in_days(due_in_days);
                                btnpay.setText("Credit");
                            } else {
                                saleorder_entry.sh.get(0).setPay_type(1);
                                btnpay.setText("Cash Down");
                                saleorder_entry.txtpaid.setText("0");
                                saleorder_entry.sh.get(0).setPaid_amount(0);

                            }
                            saleorder_entry.getSummary();
                        } else {
                            saleorder_entry.sh.get(0).setPay_type(1);
                            btnpay.setText("Cash Down");
                            saleorder_entry.txtpaid.setText("0");
                            saleorder_entry.sh.get(0).setPaid_amount(0);
                            saleorder_entry.getSummary();
                        }
                        long specialPrice = GetPriceLevel();

                        String sale_price = specialPrice == 0 ? "uc.sale_price" : "uc.sale_price" + specialPrice;
                        String SP = specialPrice == 0 ? "SP" : "SP" + specialPrice;
                        if (specialPrice != -1) {
                            ChangePriceLevelSale(specialPrice);
                        }
                        da.dismiss();

                    }
                });
            }

            else {
                holder.btn.setText(" " + data.get(position).getName());
                holder.btn.setBackgroundResource(R.drawable.usercodegradiant);
                holder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        btn.setText( data.get(position).getName());
                        sale_entry.sh.get(0).setCustomerid(data.get(position).getCustomerid());
                        sale_entry.isCreditcustomer = data.get(position).isCredit();
                        sale_entry.custDis = data.get(position).getCustDis();
                        sale_entry.sh.get(0).setDiscount_per(sale_entry.custDis);
                        sale_entry.due_in_days = data.get(position).getDue_in_days();
                        int due_in_days = sale_entry.due_in_days;
                        int def_payment = frmlogin.def_payment;
                        sale_entry.credit_limit = data.get(position).getCredit_limit();
                        if (sale_entry.isCreditcustomer) {
                            if (def_payment == 2) {
                                sale_entry.sh.get(0).setPay_type(2);
                                sale_entry.sh.get(0).setDue_in_days(due_in_days);
                                btnpay.setText("Credit");
                            } else {
                                sale_entry.sh.get(0).setPay_type(1);
                                btnpay.setText("Cash Down");
                                sale_entry.txtpaid.setText("0");
                                sale_entry.sh.get(0).setPaid_amount(0);

                            }
                            sale_entry.getSummary();
                        } else {
                            sale_entry.sh.get(0).setPay_type(1);
                            btnpay.setText("Cash Down");
                            sale_entry.txtpaid.setText("0");
                            sale_entry.sh.get(0).setPaid_amount(0);
                            sale_entry.getSummary();
                        }
                        long specialPrice = GetPriceLevel();

                        String sale_price = specialPrice == 0 ? "uc.sale_price" : "uc.sale_price" + specialPrice;
                        String SP = specialPrice == 0 ? "SP" : "SP" + specialPrice;
                        if (specialPrice != -1) {
                            ChangePriceLevelSale(specialPrice);
                        }
                        da.dismiss();

                    }
                });
            }
    }

    private void ChangePriceLevelSale(long specialPrice) {
        String sale_price=specialPrice==0?"uc.sale_price":"uc.sale_price"+specialPrice;
        String SP=specialPrice==0?"SP":"SP"+specialPrice;
        if(formname=="SaleOrder")
        {
            for (int i = 0; i < saleorder_entry.sd.size(); i++) {
                String sqlString = "select uc.unit_type,code,description," + sale_price + ",smallest_unit_qty,unitname,unitshort,CalNoTax from Usr_Code uc " +
                        " where uc.unit_type=" + saleorder_entry.sd.get(i).getUnt_type() + " and uc.code=" + saleorder_entry.sd.get(i).getCode() + " order by code";;
                Cursor cursor = DatabaseHelper.rawQuery(sqlString);
                if (cursor != null && cursor.getCount() != 0) {
                    if (cursor.moveToFirst()) {
                        do {

                            double price = cursor.getDouble(cursor.getColumnIndex(sale_price));
                            saleorder_entry.sd.get(i).setPriceLevel(SP);
                            if (!SP.equals(saleorder_entry.sd.get(i).getPriceLevel())) {
                                saleorder_entry.sd.get(i).setSale_price(price);
                                saleorder_entry.sd.get(i).setDis_price(price);
                                saleorder_entry.sd.get(i).setDis_type(0);
                            }

                        } while (cursor.moveToNext());


                    }

                }
                cursor.close();

            }
            saleorder_entry.itemAdapter.notifyDataSetChanged();
            saleorder_entry.getSummary();
        }
        else {

            for (int i = 0; i < sale_entry.sd.size(); i++) {
                String sqlString = "select uc.unit_type,code,description," + sale_price + ",smallest_unit_qty,unitname,unitshort,CalNoTax from Usr_Code uc " +
                        " where uc.unit_type=" + sale_entry.sd.get(i).getUnt_type() + " and uc.code=" + sale_entry.sd.get(i).getCode();
                Cursor cursor = DatabaseHelper.rawQuery(sqlString);
                if (cursor != null && cursor.getCount() != 0) {
                    if (cursor.moveToFirst()) {
                        do {

                            double price = cursor.getDouble(cursor.getColumnIndex(sale_price));
                            sale_entry.sd.get(i).setPriceLevel(SP);
                            if (!SP.equals(sale_entry.sd.get(i).getPriceLevel())) {
                                sale_entry.sd.get(i).setSale_price(price);
                                sale_entry.sd.get(i).setDis_price(price);
                                sale_entry.sd.get(i).setDis_type(0);
                            }

                        } while (cursor.moveToNext());


                    }

                }
                cursor.close();

            }
            sale_entry.itemAdapter.notifyDataSetChanged();
            sale_entry.getSummary();
        }

    }

    private long GetPriceLevel() {
        long level=0;
        boolean useUserpricelevel=false;
        boolean useCustpricelevel=false;
        Cursor cursor=DatabaseHelper.rawQuery("select use_user_pricelevel,use_cust_pricelevel from SystemSetting");
        if(cursor!=null&&cursor.getCount()!=0)
        {
            if(cursor.moveToFirst())
            {
                do {
                    useUserpricelevel=cursor.getInt(cursor.getColumnIndex("use_user_pricelevel"))==1?true:false;
                    useCustpricelevel=cursor.getInt(cursor.getColumnIndex("use_cust_pricelevel"))==1?true:false;
                }while (cursor.moveToNext());


            }

        }
        cursor.close();
        if(useCustpricelevel) {
            if (formname == "SaleOrder") {
                String sSql = "select pricelevel from customer where customerid =" + saleorder_entry.sh.get(0).getCustomerid();
                cursor = DatabaseHelper.rawQuery(sSql);

            } else {
                String sSql = "select pricelevel from customer where customerid =" + sale_entry.sh.get(0).getCustomerid();
                cursor = DatabaseHelper.rawQuery(sSql);
            }
            if(cursor!=null&&cursor.getCount()!=0)
            {
                if(cursor.moveToFirst())
                {
                    do {
                        level=cursor.getInt(cursor.getColumnIndex("pricelevel"));
                    }while (cursor.moveToNext());


                }

            }
            cursor.close();

        }
        //else if(useUserpricelevel)
        //{
           // String sSql = "select saleprice_level from posuser where userid="+ frmlogin.LoginUserid;
           // cursor=DatabaseHelper.rawQuery(sSql);
            //if(cursor!=null&&cursor.getCount()!=0)
            //{
                //if(cursor.moveToFirst())
               // {
                    //do {
                        //level=cursor.getInt(cursor.getColumnIndex("saleprice_level"));
                   // }while (cursor.moveToNext());


                //}

           // }
            //cursor.close();
        //}
        else
        {
            level=-1;
        }
        return  level;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button btn;
        public MyViewHolder(View itemView) {
            super(itemView);
            btn=itemView.findViewById(R.id.info_text);
        }
    }
}
