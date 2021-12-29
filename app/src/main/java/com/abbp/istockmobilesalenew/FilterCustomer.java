package com.abbp.istockmobilesalenew;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class FilterCustomer extends RecyclerView.Adapter<FilterCustomer.MyViewHolder> {

    Context context;
    ArrayList<customer> data = new ArrayList<>();
    Button btn;
    androidx.appcompat.app.AlertDialog da;
    public static String formname;//added by YLT
    public static int ccid = -1;

    public FilterCustomer(Context context, ArrayList<customer> data, Button btn, androidx.appcompat.app.AlertDialog da) {
        this.data = data;
        this.context = context;
        this.btn = btn;
        this.da = da;
        formname = "Sale";
    }

    public FilterCustomer(Context context, ArrayList<customer> data, Button btn, androidx.appcompat.app.AlertDialog da, String frm) {
        this.data = data;
        this.context = context;
        this.btn = btn;
        this.da = da;
        formname = frm;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        View v = lf.inflate(R.layout.headerbinding, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.btn.setText(" " + data.get(position).getName());
        holder.btn.setBackgroundResource(R.drawable.usercodegradiant);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formname == "SaleOrder") {
                    btn.setText(data.get(position).getCustomerid() + ":" + data.get(position).getName());
                    ccid = (int) data.get(position).getCustomerid();
                    FilterUser.uid = -1;
                    FilterLocation.locid = -1;
                    frmsaleorderlist.BindingData();
                    da.dismiss();
                } else if (formname == "frmoutstand") {
                    btn.setText(data.get(position).getCustomerid() + ":" + data.get(position).getName());
                    ccid = (int) data.get(position).getCustomerid();
                    da.dismiss();
                } else {
                    btn.setText(data.get(position).getCustomerid() + ":" + data.get(position).getName());
                    ccid = (int) data.get(position).getCustomerid();
                    FilterUser.uid = -1;
                    FilterLocation.locid = -1;
                    frmsalelist.BindingData();
                    da.dismiss();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        Button btn;

        public MyViewHolder(View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.info_text);
        }
    }
}

