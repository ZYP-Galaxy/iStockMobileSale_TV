package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class outstandlistAdp extends RecyclerView.Adapter<outstandlistAdp.MyViewHolder> {
    public static String url;
    public static SharedPreferences sh_ip,sh_port;
    Context context;
    ArrayList<outstandlist> data;
    RequestQueue requestQueue;
    public outstandlistAdp(Context context, ArrayList<outstandlist> data) {
        this.context = context;
        this.data = data;
        sh_ip=context.getSharedPreferences("ip",MODE_PRIVATE);
        sh_port=context.getSharedPreferences("port",MODE_PRIVATE);
    }
    @Override
    public outstandlistAdp.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf= LayoutInflater.from(parent.getContext());
        View v=lf.inflate(R.layout.outstandlistitem,parent,false);

        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull outstandlistAdp.MyViewHolder myViewHolder, int position) {

        myViewHolder.Customer.setText(" "+data.get(position).getMerchantInform());
        myViewHolder.Currency.setText(" "+data.get(position).getCurrency());

        String curformat= String.format("%,."+frmmain.price_places+"f",data.get(position).getOpening());
        myViewHolder.Opening.setText(curformat);

        String curformatSale= String.format("%,."+frmmain.price_places+"f",data.get(position).getSale());
        myViewHolder.Sale.setText(curformatSale);

        String curformatRetunIn= String.format("%,."+frmmain.price_places+"f",data.get(position).getReturnIn());
        myViewHolder.ReturnIn.setText(curformatRetunIn);

        String curformatDiscount= String.format("%,."+frmmain.price_places+"f",data.get(position).getDiscount());
        myViewHolder.Discount.setText(curformatDiscount);

        String curformatPaid= String.format("%,."+frmmain.price_places+"f",data.get(position).getPaid());
        myViewHolder.Paid.setText(curformatPaid);

        String curformatClosing= String.format("%,."+frmmain.price_places+"f",data.get(position).getClosing());
        myViewHolder.Balance.setText(curformatClosing);

       // myViewHolder.Balance.setText(" "+String.valueOf(data.get(position).getBalanceQty()));
      //  myViewHolder.Location.setText(" "+data.get(position).getLocation());

    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Customer,Currency,Opening,Sale,ReturnIn,Discount,Paid,Balance;

        public MyViewHolder(View itemView) {
            super(itemView);
            Customer=itemView.findViewById(R.id.txtCustomer);
            Currency=itemView.findViewById(R.id.txtCurrency);
            Opening=itemView.findViewById(R.id.txtOpening);
            Sale=itemView.findViewById(R.id.txtSale);
            ReturnIn=itemView.findViewById(R.id.txtReturnIn);
            Discount=itemView.findViewById(R.id.txtDiscount);
            Paid=itemView.findViewById(R.id.txtPaid);
            Balance=itemView.findViewById(R.id.txtBalance);

        }
    }
}
