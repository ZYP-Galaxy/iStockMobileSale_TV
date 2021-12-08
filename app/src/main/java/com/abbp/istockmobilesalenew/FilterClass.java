package com.abbp.istockmobilesalenew;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class FilterClass extends RecyclerView.Adapter<FilterClass.MyViewHolder> {

    Context context;
    ArrayList<class_item> data=new ArrayList<>();
    Button btn;
    androidx.appcompat.app.AlertDialog da;
    public static long  uid=-1;
    public FilterClass(Context context, ArrayList<class_item> data, Button btn, androidx.appcompat.app.AlertDialog da) {
        this.data = data;
        this.context = context;
        this.btn=btn;
        this.da=da;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf= LayoutInflater.from(parent.getContext());
        View v=lf.inflate(R.layout.headerbinding,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.btn.setText(" "+data.get(position).getName());
        holder.btn.setBackgroundResource(R.drawable.usercodegradiant);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    btn.setText(data.get(position).getName());
                    uid =  data.get(position).getClassid();
                    FilterCustomer.ccid = -1;
                    FilterLocation.locid = -1;

                    da.dismiss();
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

