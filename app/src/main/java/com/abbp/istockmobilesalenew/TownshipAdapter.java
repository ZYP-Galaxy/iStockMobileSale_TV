package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.database.Cursor;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class TownshipAdapter extends RecyclerView.Adapter<TownshipAdapter.MyViewHolder> {

    Context context;
    ArrayList<Township> data=new ArrayList<>();
    Button btn;
androidx.appcompat.app.AlertDialog da;
    public static String formname;//added by YLT
        public TownshipAdapter(Context context, ArrayList<Township> data, Button btn, androidx.appcompat.app.AlertDialog da) {
            this.data = data;
            this.context = context;
            this.btn=btn;
            this.da=da;
            formname="Sale";
    }
    public TownshipAdapter(Context context, ArrayList<Township> data, Button btn, androidx.appcompat.app.AlertDialog da, String frm) {
        this.data = data;
        this.context = context;
        this.btn=btn;
        this.da=da;
        formname=frm;
    }

    @Override
    public TownshipAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf= LayoutInflater.from(parent.getContext());
        View v=lf.inflate(R.layout.headerbinding,parent,false);
        return new TownshipAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TownshipAdapter.MyViewHolder holder,int position) {
        holder.btn.setText(" "+data.get(position).getName());
        holder.btn.setBackgroundResource(R.drawable.usercodegradiant);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               btn.setText(data.get(position).getName());
                if(formname =="SaleOrder")
                {
                    saleorder_entry.selected_townshipid = data.get(position).getTownshipid();
                    saleorder_entry.sh.get(0).setTownshipid(data.get(position).getTownshipid());
                }
                else {
                    sale_entry.selected_townshipid = data.get(position).getTownshipid();
                    sale_entry.sh.get(0).setTownshipid(data.get(position).getTownshipid());
                }

                BindMaxCustomer();
               da.dismiss();

            }
        });
    }

    private void BindMaxCustomer() {

            if(formname=="SaleOrder")
            {
                String sqlString = "select customerid,customer_name,credit,CustGroupID,CustGroupname from customer where customerid=(select min(customerid)customerid " +
                        " from customer" +
                        " where Townshipid=" + saleorder_entry.selected_townshipid +
                        " and CustGroupID="+saleorder_entry.selected_custgroupid+
                        " group by Townshipid)";
                Cursor cursor = DatabaseHelper.rawQuery(sqlString);
                if (cursor != null && cursor.getCount() != 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            long customerid = cursor.getLong(cursor.getColumnIndex("customerid"));
                            String customername = cursor.getString(cursor.getColumnIndex("customer_name"));
                            long custgroupid = cursor.getLong(cursor.getColumnIndex("CustGroupID"));
                            String custgroupname = cursor.getString(cursor.getColumnIndex("CustGroupname"));
                            boolean credit = cursor.getInt(cursor.getColumnIndex("credit")) == 1 ? true : false;
                            saleorder_entry.btncustomer.setText(customername);
                            saleorder_entry.btncustgroup.setText( custgroupname);
                            String pay_type = credit == false ? "Cash Down" : "Credit";
                            saleorder_entry.btnpaytype.setText(pay_type);
                            saleorder_entry.sh.get(0).setCustomerid(customerid);
                            saleorder_entry.selected_custgroupid = custgroupid;
                            saleorder_entry.creditcustomer = credit;
                            saleorder_entry.sh.get(0).setPay_type(credit == false ? 1 : 2);
                        } while (cursor.moveToNext());


                    }
                }
            }
            else {
                String sqlString = "select customerid,customer_name,credit,CustGroupID,CustGroupname from customer where customerid=(select min(customerid)customerid " +
                        " from customer" +
                        " where Townshipid=" + sale_entry.selected_townshipid +
                        " and CustGroupID="+sale_entry.selected_custgroupid+
                        " group by Townshipid)";
                Cursor cursor = DatabaseHelper.rawQuery(sqlString);
                if (cursor != null && cursor.getCount() != 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            long customerid = cursor.getLong(cursor.getColumnIndex("customerid"));
                            String customername = cursor.getString(cursor.getColumnIndex("customer_name"));
                            long custgroupid = cursor.getLong(cursor.getColumnIndex("CustGroupID"));
                            String custgroupname = cursor.getString(cursor.getColumnIndex("CustGroupname"));
                            boolean credit = cursor.getInt(cursor.getColumnIndex("credit")) == 1 ? true : false;
                            sale_entry.btncustomer.setText( customername);
                            sale_entry.btncustgroup.setText( custgroupname);
                            String pay_type = credit == false ? "Cash Down" : "Credit";
                            sale_entry.btnpaytype.setText(pay_type);
                            sale_entry.sh.get(0).setCustomerid(customerid);
                            sale_entry.selected_custgroupid = custgroupid;
                            sale_entry.creditcustomer = credit;
                            sale_entry.sh.get(0).setPay_type(credit == false ? 1 : 2);
                        } while (cursor.moveToNext());


                    }
                }
            }
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
