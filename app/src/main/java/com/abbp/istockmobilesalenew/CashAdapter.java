package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CashAdapter extends  RecyclerView.Adapter<CashAdapter.MyViewHolder>{

    Context context;
    ArrayList<Cash> data=new ArrayList<>();
    Button btn;
    Button btnpay;
    androidx.appcompat.app.AlertDialog da;
    public CashAdapter(Context context, ArrayList<Cash> data, Button btn, Button btnpay,androidx.appcompat.app.AlertDialog da) {
        this.context = context;
        this.data = data;
        this.btn = btn;
        this.btnpay = btnpay;
        this.da=da;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lf= LayoutInflater.from(parent.getContext());
        View v=lf.inflate(R.layout.headerbinding,parent,false);
        return new CashAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.btn1.setText(" "+data.get(position).getName());
        holder.btn1.setBackgroundResource(R.drawable.usercodegradiant);
        holder.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setText(data.get(position).getName());
                sale_entry.sh.get(0).setDef_cashid(data.get(position).getCash_id());
                da.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button btn1;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            btn1=itemView.findViewById(R.id.info_text);
        }
    }
}
