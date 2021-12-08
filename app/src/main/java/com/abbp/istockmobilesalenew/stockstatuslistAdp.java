package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class stockstatuslistAdp extends RecyclerView.Adapter<stockstatuslistAdp.MyViewHolder>
{
    public static String url;
    public static SharedPreferences sh_ip,sh_port;
    Context context;
    ArrayList<stockstatuslist> data;
    RequestQueue requestQueue;

    public stockstatuslistAdp(Context context, ArrayList<stockstatuslist> data) {
        this.context = context;
        this.data = data;
        sh_ip=context.getSharedPreferences("ip",MODE_PRIVATE);
        sh_port=context.getSharedPreferences("port",MODE_PRIVATE);
    }
    @Override
    public stockstatuslistAdp.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf= LayoutInflater.from(parent.getContext());
        View v=lf.inflate(R.layout.stockstuslistitem,parent,false);

        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull stockstatuslistAdp.MyViewHolder myViewHolder, int position) {

       myViewHolder.code.setText(" "+data.get(position).getUsrcode());
        myViewHolder.description.setText(" "+data.get(position).getDescription());
       // myViewHolder.saleAmount.setText(" "+String.valueOf(data.get(position).getSaleAmount()));
        String curformat= String.format("%,."+frmmain.price_places+"f",data.get(position).getSaleAmount());
        myViewHolder.saleAmount.setText(curformat);

        myViewHolder.Balance.setText(" "+String.valueOf(data.get(position).getBalanceQty()));
        myViewHolder.Location.setText(" "+data.get(position).getLocation());

    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView code,description,saleAmount,Balance,Location;

        public MyViewHolder(View itemView) {
            super(itemView);
            code=itemView.findViewById(R.id.txtCode);
            description=itemView.findViewById(R.id.txtDescription);
            saleAmount=itemView.findViewById(R.id.txtSaleAmount);
            Balance=itemView.findViewById(R.id.txtBalance);
            Location=itemView.findViewById(R.id.txtLocation);

        }
    }
}
