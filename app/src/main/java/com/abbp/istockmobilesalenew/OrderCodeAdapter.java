package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderCodeAdapter  extends BaseAdapter {
    Context context;
    ArrayList<so_det> data=new ArrayList<>();

    public OrderCodeAdapter(Context context, ArrayList<so_det> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater lf= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=lf.inflate(R.layout.orderitem,null,false);
        TextView tv= (TextView) convertView.findViewById(R.id.desc);
        TextView tv1= (TextView) convertView.findViewById(R.id.qty);
        TextView tv2= (TextView) convertView.findViewById(R.id.price);
        tv.setText(data.get(position).getDesc());
        String qtyAsString = String.format("%." + frmmain.qty_places + "f", data.get(position).getUnit_qty());
        qtyAsString=qtyAsString+" "+data.get(position).getUnit_short();
        tv1.setText(qtyAsString);
        String numberAsString = String.format("%,." + frmmain.price_places + "f",data.get(position).getPrice() );
        tv2.setText(numberAsString);
        return convertView;
    }
}
