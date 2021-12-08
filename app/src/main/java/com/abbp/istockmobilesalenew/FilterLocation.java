package com.abbp.istockmobilesalenew;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class FilterLocation extends RecyclerView.Adapter<FilterLocation.MyViewHolder> {

    Context context;
    ArrayList<Location> data=new ArrayList<>();
    Button btn;
    androidx.appcompat.app.AlertDialog da;
    public static String formname;//added by YLT
    public static int locid=-1;
    public FilterLocation(Context context, ArrayList<Location> data, Button btn, androidx.appcompat.app.AlertDialog da) {
        this.data = data;
        this.context = context;
        this.btn=btn;
        this.da=da;
        formname="Sale";
    }


    public FilterLocation(Context context, ArrayList<Location> data, Button btn, androidx.appcompat.app.AlertDialog da, String frm) {
        this.data = data;
        this.context = context;
        this.btn=btn;
        this.da=da;
        formname=frm;
    }

    @Override
    public FilterLocation.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf= LayoutInflater.from(parent.getContext());
        View v=lf.inflate(R.layout.headerbinding,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FilterLocation.MyViewHolder holder, final int position) {
        holder.btn.setText(" "+data.get(position).getName());
        holder.btn.setBackgroundResource(R.drawable.usercodegradiant);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(formname=="SaleOrder")
                {
                    btn.setText(data.get(position).getLocationid() + ":" + data.get(position).getName());
                    locid = (int) data.get(position).getLocationid();
                    FilterUser.uid = -1;
                    FilterCustomer.ccid = -1;
                    frmsaleorderlist.BindingData();
                    da.dismiss();
                }
                else if (formname=="frmstockstatus") {
                    btn.setText(data.get(position).getLocationid() + ":" + data.get(position).getName());
                    locid = (int) data.get(position).getLocationid();
                    FilterUser.uid = -1;
                    FilterCustomer.ccid = -1;
                   // frmsalelist.BindingData();
                    da.dismiss();
                }

              else {
                    btn.setText(data.get(position).getName());
                    locid = (int) data.get(position).getLocationid();
                    FilterUser.uid = -1;
                    FilterCustomer.ccid = -1;
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


