package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.database.Cursor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class classAdapter extends RecyclerView.Adapter<classAdapter.MyViewHolder> {
    Context context;
    ArrayList<class_item> data;
    RecyclerView rv;
    public static String formname;//added by YLT

    boolean firstBind = true;
    int selectedPosition = 0;

    public classAdapter(Context context, ArrayList<class_item> data, RecyclerView rv) {
        this.context = context;
        this.data = data;
        this.rv = rv;
        formname = "Sale";

    }

    //added by YLT
    public classAdapter(Context context, ArrayList<class_item> data, RecyclerView rv, String frm) {
        this.context = context;
        this.data = data;
        this.rv = rv;
        formname = frm; //added by YLT
    }

    @Override
    public classAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        View v = lf.inflate(R.layout.item_class, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(classAdapter.MyViewHolder holder, int position) {

        if (selectedPosition == position) {
            holder.viewIndicator.setBackgroundResource(R.color.colorPrimary);
        } else {
            holder.viewIndicator.setBackgroundResource(R.color.headerbar);
        }

        holder.btn.setText(" " + data.get(position).getName());
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if (formname.equals("SaleOrder"))//added by YLT
                    {
                        saleorder_entry.imgFilterCode.setVisibility(View.GONE);
                        saleorder_entry.fitercode = "Description";
                        Cursor cursor = DatabaseHelper.DistinctSelectQuerySelectionWithCondition("Usr_Code", new String[]{"category", "categoryname", "class"}, "class=?", new String[]{String.valueOf(data.get(position).getClassid())}, "sortcode,categoryname");
                        if (saleorder_entry.categories.size() > 0)
                            saleorder_entry.categories.clear();
                        if (frmmain.withoutclass.equals("false")) {
                            saleorder_entry.categories.add(new category("Back"));
                        }
                        if (cursor != null && cursor.getCount() != 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    long category = cursor.getLong(cursor.getColumnIndex("category"));
                                    String name = cursor.getString(cursor.getColumnIndex("categoryname"));
                                    long classid = cursor.getLong(cursor.getColumnIndex("class"));
                                    saleorder_entry.categories.add(new category(category, classid, name));
                                } while (cursor.moveToNext());
                            }
                        }
                        cursor.close();
                        categoryAdapter ad = new categoryAdapter(context, saleorder_entry.categories, rv, "SaleOrder");//added by YLT
                        rv.setAdapter(ad);
                        LinearLayoutManager classlinear = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                        rv.setLayoutManager(classlinear);

                    } else {
//                        sale_entry.imgFilterCode.setVisibility(View.GONE);
//                        sale_entry.fitercode = "Description";
                        Cursor cursor = DatabaseHelper.DistinctSelectQuerySelectionWithCondition("Usr_Code", new String[]{"category", "categoryname", "class"}, "class=?", new String[]{String.valueOf(data.get(position).getClassid())}, "sortcode,categoryname");
                        if (sale_entry.categories.size() > 0) sale_entry.categories.clear();
//                        if (frmmain.withoutclass.equals("false")) {
//                            sale_entry.categories.add(new category("Back"));
//                        }

                        if (cursor != null && cursor.getCount() != 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    long category = cursor.getLong(cursor.getColumnIndex("category"));
                                    String name = cursor.getString(cursor.getColumnIndex("categoryname"));
                                    long classid = cursor.getLong(cursor.getColumnIndex("class"));
                                    sale_entry.categories.add(new category(category, classid, name));
                                } while (cursor.moveToNext());

                            }
                            cursor.close();
                        }

                        categoryAdapter ad = new categoryAdapter(context, sale_entry.categories, rv);//added by YLT
                        rv.setAdapter(ad);
                        LinearLayoutManager classlinear = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                        rv.setLayoutManager(classlinear);

                    }

                    if (!firstBind) {
                        selectedPosition = position;
                        notifyDataSetChanged();
                    }

                } catch (Exception ee) {
                    GlobalClass.showToast(context, ee.getMessage());
                }
            }
        });

        if (firstBind) {
            holder.btn.performClick();
            firstBind = false;
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        Button btn;
        View viewIndicator;
        LinearLayout itemLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.info_text);
            viewIndicator = itemView.findViewById(R.id.class_indicator);
            itemLayout = itemView.findViewById(R.id.layout_item_class);

        }
    }

}
