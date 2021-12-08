package com.abbp.istockmobilesalenew;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class FilterBranch extends RecyclerView.Adapter<FilterBranch.MyViewHolder> {

    Context context;
    ArrayList<Branch> data=new ArrayList<>();
    Button btn;
    androidx.appcompat.app.AlertDialog da;
    public static String formname;//added by YLT
    public static int BranchiID=-1;

    public FilterBranch(Context context, ArrayList<Branch> data, Button btn, androidx.appcompat.app.AlertDialog da) {
        this.data = data;
        this.context = context;
        this.btn=btn;
        this.da=da;

    }
    @Override
    public FilterBranch.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf= LayoutInflater.from(parent.getContext());
        View v=lf.inflate(R.layout.headerbinding,parent,false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(FilterBranch.MyViewHolder holder, final int position) {
        holder.btn.setText(" "+data.get(position).getBranchname());
        holder.btn.setBackgroundResource(R.drawable.usercodegradiant);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (formname == "SaleOrder") {
                    btn.setText(data.get(position).getBranchID() + ":" + data.get(position).getBranchname());
                    BranchiID = (int) data.get(position).getBranchID();
                    FilterUser.uid = -1;
                    FilterCustomer.ccid = -1;
                    // frmsaleorderlist.BindingData();
                    da.dismiss();
              //  }
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
