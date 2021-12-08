package com.abbp.istockmobilesalenew;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class FilterBrand extends RecyclerView.Adapter<FilterBrand.MyViewHolder> {
    Context context;
    ArrayList<brand> data=new ArrayList<>();
    Button btn;
    androidx.appcompat.app.AlertDialog da;
    public static String formname;//added by YLT
    public static long uid=-1;
    public FilterBrand(Context context, ArrayList<brand> data, Button btn, androidx.appcompat.app.AlertDialog da) {
        this.data = data;
        this.context = context;
        this.btn=btn;
        this.da=da;
        formname="Sale";
    }

    public FilterBrand(Context context, ArrayList<brand> data, Button btn, androidx.appcompat.app.AlertDialog da, String frm) {
        this.data = data;
        this.context = context;
        this.btn=btn;
        this.da=da;
        formname=frm;
    }

    @Override
    public FilterBrand.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf= LayoutInflater.from(parent.getContext());
        View v=lf.inflate(R.layout.headerbinding,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FilterBrand.MyViewHolder holder, final int position) {
        holder.btn.setText(" "+data.get(position).getBrandName());
        holder.btn.setBackgroundResource(R.drawable.usercodegradiant);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(formname =="SaleOrder")
                {
                    btn.setText(data.get(position).getBrandID() + ":" + data.get(position).getBrandName());
                    uid = data.get(position).getBrandID();
                    FilterCustomer.ccid = -1;
                    FilterLocation.locid = -1;
                    frmsaleorderlist.BindingData();
                    da.dismiss();
                }
                else {
                    btn.setText(data.get(position).getBrandID() + ":" + data.get(position).getBrandName());
                    uid = data.get(position).getBrandID();
                    FilterCustomer.ccid = -1;
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


    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button btn;
        public MyViewHolder(View itemView) {
            super(itemView);
            btn=itemView.findViewById(R.id.info_text);
        }
    }
}
