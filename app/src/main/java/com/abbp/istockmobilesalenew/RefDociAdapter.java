package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RefDociAdapter  extends BaseAdapter {
    Context context;
    ArrayList<RefDocid> data=new ArrayList<>();

    public RefDociAdapter(Context context, ArrayList<RefDocid> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return  data.size();
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
        convertView=lf.inflate(R.layout.refdocidlist,null,false);
        TextView tv= (TextView) convertView.findViewById(R.id.txtitem);
        tv.setText(data.get(position).getDocid());
        return convertView;
    }
}
