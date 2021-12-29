package com.abbp.istockmobilesalenew;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SaleListAdapter extends RecyclerView.Adapter<SaleListAdapter.MyViewHolder> {
    Context context;
    ArrayList<salelist> data;

    public SaleListAdapter(Context context, ArrayList<salelist> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public SaleListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        View v = lf.inflate(R.layout.salelistitem, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SaleListAdapter.MyViewHolder holder, int position) {
        holder.txtdate.setText(" " + data.get(position).getDate());
        holder.txtdocid.setText(" " + data.get(position).getDocid());
        holder.txtpaytype.setText(" " + data.get(position).getPay_type());
        holder.txtcurrency.setText(" " + data.get(position).getCurrency());
        String curformat = String.format("%,." + frmmain.price_places + "f", data.get(position).getNet_amount());
        holder.txtbalance.setText(curformat);
        holder.txtuser.setText(" " + data.get(position).getUsershort());
        holder.txtcustomer.setText(" " + data.get(position).getCustomer_name());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtdate, txtdocid, txtpaytype, txtcurrency, txtbalance, txtuser, txtcustomer;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtdate = itemView.findViewById(R.id.txtdate);
            txtdocid = itemView.findViewById(R.id.txtdocid);
            txtpaytype = itemView.findViewById(R.id.txtpaytype);
            txtcurrency = itemView.findViewById(R.id.txtcurrency);
            txtbalance = itemView.findViewById(R.id.txtbalance);
            txtuser = itemView.findViewById(R.id.txtuserid);
            txtcustomer = itemView.findViewById(R.id.txtcustomer);
        }
    }
}
