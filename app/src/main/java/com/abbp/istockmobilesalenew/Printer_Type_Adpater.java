package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Printer_Type_Adpater extends BaseAdapter {
    private Context context;
    private ArrayList<Printer_Type> data = new ArrayList<>();
    private  ArrayList<String> printer=new ArrayList<>();
    private  boolean check;

    public Printer_Type_Adpater(Context context, ArrayList<Printer_Type> data) {
        this.context = context;
        this.data = data;
        this.check=false;
    }

    public Printer_Type_Adpater(ArrayList<String> printer,Context context) {
        this.context = context;
        this.printer = printer;
        this.check=true;
    }

    @Override
    public int getCount() {

        int size=check==true?printer.size():data.size();
        return size;
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.posuserterm, null);
        TextView tv=view.findViewById(R.id.txtitem);
        String value=check==true?printer.get(position):data.get(position).getName();
        tv.setText(value);
        return view;
    }
}
