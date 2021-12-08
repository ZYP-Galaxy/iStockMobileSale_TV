package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class saleorderListAdp extends RecyclerView.Adapter<saleorderListAdp.MyViewHolder>
{
    public static String url;
    public static SharedPreferences sh_ip,sh_port;
    Context context;
    ArrayList<saleorderlist> data;
    RequestQueue requestQueue;

    public saleorderListAdp(Context context, ArrayList<saleorderlist> data) {
        this.context = context;
        this.data = data;
        sh_ip=context.getSharedPreferences("ip",MODE_PRIVATE);
        sh_port=context.getSharedPreferences("port",MODE_PRIVATE);
    }
    @Override
    public saleorderListAdp.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf= LayoutInflater.from(parent.getContext());
        View v=lf.inflate(R.layout.saleorderlistitem,parent,false);

        return new saleorderListAdp.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {


        myViewHolder.txtdate.setText(" "+data.get(i).getDate());
        myViewHolder.txtdocid.setText(" "+data.get(i).getDocid());
        myViewHolder.txtpaytype.setText(" "+data.get(i).getPay_type());
        myViewHolder.txtcurrency.setText(" "+data.get(i).getCurrency());
        String curformat= String.format("%,."+frmmain.price_places+"f",data.get(i).getNet_amount());
        myViewHolder.txtbalance.setText(curformat);
        myViewHolder.txtuser.setText(" "+data.get(i).getUsershort());
        myViewHolder.txtcustomer.setText(" "+data.get(i).getCustomer_name());
        myViewHolder.txtcancel.setText(" "+data.get(i).getCancel());
        if(data.get(i).getCancel()==1)
        {
            myViewHolder.txtdate.setTextColor(Color.RED);
            myViewHolder.txtdocid.setTextColor(Color.RED);
            myViewHolder.txtpaytype.setTextColor(Color.RED);
            myViewHolder.txtcurrency.setTextColor(Color.RED);
            myViewHolder.txtbalance.setTextColor(Color.RED);
            myViewHolder.txtuser.setTextColor(Color.RED);
            myViewHolder.txtcustomer.setTextColor(Color.RED);
            myViewHolder.txtcancel.setTextColor(Color.RED);
        }


        myViewHolder.imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(frmlogin.allow_so_update==0)
               {
                   return;
               }
                AlertDialog.Builder bd=new AlertDialog.Builder(context,R.style.AlertDialogTheme);
                bd.setTitle("Order list");
                bd.setMessage("Are you sure want to Cancel this row?");
                bd.setCancelable(false);
                bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                        String ip = sh_ip.getString("ip", "empty");
                        String port = sh_port.getString("port", "empty");
                        url = "http://"+ip+":"+port+"/api/DataSync/GetHeader?tranid="+data.get(i).getTranid();
                      //  url = "http://192.168.1.98:50337/api/DataSync/GetHeader?tranid="+data.get(i).getTranid();
                        requestQueue = Volley.newRequestQueue(context);
                        final Response.Listener<String> listener = new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {

                                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                            }


                        };

                        final Response.ErrorListener error = new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                System.out.println("This is error"+error);

                            }
                        };
                        StringRequest req = new StringRequest(Request.Method.GET, url, listener, error);
                        requestQueue.add(req);

                        frmsaleorderlist.GetData();//added by YLT on [29-04-2020]

                    }
                });

                bd.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                bd.create().show();

            }
        });
       // myViewHolder.imgComfirm.setOnClickListener(new View.OnClickListener() {
          //  @Override
         //   public void onClick(View v) {

          //  }
        //});
    }



    @Override
    public int getItemCount() {
        return data.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtdate,txtdocid,txtpaytype,txtcurrency,txtbalance,txtuser,txtcustomer,txtcancel;
        ImageButton imgCancel;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtdate=itemView.findViewById(R.id.txtdate);
            
            txtdocid=itemView.findViewById(R.id.txtdocid);
            txtpaytype=itemView.findViewById(R.id.txtpaytype);
            txtcurrency=itemView.findViewById(R.id.txtcurrency);
            txtbalance=itemView.findViewById(R.id.txtbalance);
            txtuser=itemView.findViewById(R.id.txtuserid);
            txtcustomer=itemView.findViewById(R.id.txtcustomer);
            imgCancel=itemView.findViewById(R.id.imgordCancel);

            txtcancel=itemView.findViewById(R.id.txtCanel);
        }
    }
}
