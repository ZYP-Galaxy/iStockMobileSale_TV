package com.abbp.istockmobilesalenew;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class posuserAdapter extends BaseAdapter {

    Context c;
    ArrayList<posuser> users;

    public posuserAdapter(Context c, ArrayList<posuser> users) {
        this.c = c;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
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
        LayoutInflater lf= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=lf.inflate(R.layout.posuserterm,null,false);
        TextView tv= (TextView) convertView.findViewById(R.id.txtitem);
        tv.setText(users.get(position).getName());
        return convertView;

    }
}


