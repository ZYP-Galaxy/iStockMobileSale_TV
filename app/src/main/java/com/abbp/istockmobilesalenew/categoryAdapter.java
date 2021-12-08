package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.database.Cursor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.MyViewHolder> {
    Context context;
    ArrayList<category> data;
    RecyclerView rv;
    public static String formname;//added by YLT
    public categoryAdapter(Context context, ArrayList<category> data, RecyclerView rv) {
        this.context = context;
        this.data = data;
        this.rv=rv;
        formname="Sale";
    }
    public categoryAdapter(Context context, ArrayList<category> data, RecyclerView rv,String frm) {
        this.context = context;
        this.data = data;
        this.rv=rv;
        formname=frm;//added by YLT
    }



    @Override
    public categoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf= LayoutInflater.from(parent.getContext());
        View v=lf.inflate(R.layout.itembinding,parent,false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(categoryAdapter.MyViewHolder holder, int position) {

            holder.btn.setText(" "+data.get(position).getName());
            if(frmmain.withoutclass.equals("false")){
                if(data.get(position).getName()=="Back" && position==0)
                {
                    holder.btn.setBackgroundResource(R.drawable.categorygradiant);

                }
                else
                    {

                        if(formname=="SaleOrder")//added by YLT
                        {
                            holder.btn.setBackgroundResource(R.drawable.btngradient);
                            rv = saleorder_entry.gridcodeview;

                        }
                        else {
                            holder.btn.setBackgroundResource(R.drawable.btngradient);
                            rv = sale_entry.gridcodeview;
                        }

                }
            }
            else {
                holder.btn.setBackgroundResource(R.drawable.btngradient);
            }


            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (data.get(position).getName() == "Back") {
                            if (frmmain.withoutclass.equals("false")) {

                                if(formname=="SaleOrder")//added by YLT
                                {
                                    rv = saleorder_entry.gridclassview;
                                    saleorder_entry.usr_codes.clear();
                                }
                                else {
                                    rv = sale_entry.gridclassview;
                                    sale_entry.usr_codes.clear();

                                }
                            }
                            if(formname=="SaleOrder")//added by YLT
                            {
                                saleorder_entry.imgFilterCode.setVisibility(View.GONE);
                            }
                            else {
                                sale_entry.imgFilterCode.setVisibility(View.GONE);
                            }
                            Cursor cursor = DatabaseHelper.DistinctCategorySelectQuery("Usr_Code", new String[]{"class", "classname"}, "classname");
                            if(formname=="SaleOrder")//added by YLT
                            {
                                if (saleorder_entry.class_items.size() > 0)
                                    saleorder_entry.class_items.clear();
                            }
                            else {
                                if (sale_entry.class_items.size() > 0)
                                    sale_entry.class_items.clear();
                            }

                            if (cursor != null && cursor.getCount() != 0) {
                                if (cursor.moveToFirst()) {
                                    do {
                                        long classid = cursor.getLong(cursor.getColumnIndex("class"));
                                        String name = cursor.getString(cursor.getColumnIndex("classname"));
                                        if(formname=="SaleOrder")//added by YLT
                                        {
                                            saleorder_entry.class_items.add(new class_item(classid, name));
                                        }
                                        else {
                                            sale_entry.class_items.add(new class_item(classid, name));
                                        }
                                    } while (cursor.moveToNext());

                                }

                            }
                            cursor.close();
                            if(formname=="SaleOrder")//added by YLT
                            {
                                classAdapter ad = new classAdapter(context, saleorder_entry.class_items, rv,"SaleOrder");//added by YLT
                                rv.setAdapter(ad);
                            }
                            else {


                                classAdapter ad = new classAdapter(context, sale_entry.class_items, rv);
                                rv.setAdapter(ad);
                            }
                            LinearLayoutManager classlinear = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                            rv.setLayoutManager(classlinear);

                        } else {

/*
                        if (frmmain.use_pic==1){

                            sale_entry.imgFilterCode.setVisibility(View.GONE);
                            sale_entry.fitercode="Description";
                            Cursor cursor=DatabaseHelper.rawQuery("select uc.usr_code,description,path from Usr_Code uc join usr_code_img uic on uic.usr_code=uc.usr_code where uc.category='"+String.valueOf(data.get(position).getCategory())+"'");
                            if(sale_entry.usr_codes.size()>0) sale_entry.usr_codes.clear();
                            if(cursor!=null&&cursor.getCount()!=0)
                            {
                                if(cursor.moveToFirst())
                                {
                                    do {
                                        String usr_code=cursor.getString(cursor.getColumnIndex("usr_code"));
                                        String description=cursor.getString(cursor.getColumnIndex("description"));
                                        String path=cursor.getString(cursor.getColumnIndex("path"));
                                        sale_entry.usr_codes.add(new usr_code(usr_code,description,path));
                                    }while (cursor.moveToNext());

                                }

                            }
                            cursor.close();
                            usrcodeAdapter ad=new usrcodeAdapter(context,sale_entry.usr_codes,rv,data);
                            rv.setAdapter(ad);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(context.getApplicationContext(),3);
                            rv.setLayoutManager(gridLayoutManager);


                        }else {*/
                            if(formname=="SaleOrder")//added by YLT
                            {
                                saleorder_entry.imgFilterCode.setVisibility(View.GONE);
                                saleorder_entry.fitercode = "Description";
                                Cursor cursor = DatabaseHelper.rawQuery("select distinct usr_code,description from Usr_Code where category=" + data.get(position).getCategory() + " order by usr_code");
                                if (saleorder_entry.usr_codes.size() > 0) saleorder_entry.usr_codes.clear();
                                if (frmmain.withoutclass.equals("true")) {
                                    saleorder_entry.usr_codes.add(new usr_code("Back", "Back"));
                                }
                                if (cursor != null && cursor.getCount() != 0) {
                                    if (cursor.moveToFirst()) {
                                        do {
                                            String usr_code = cursor.getString(cursor.getColumnIndex("usr_code"));
                                            String description = cursor.getString(cursor.getColumnIndex("description"));
                                            saleorder_entry.usr_codes.add(new usr_code(usr_code, description));
                                        } while (cursor.moveToNext());

                                    }

                                }
                                cursor.close();
                                usrcodeAdapter ad = new usrcodeAdapter(context, saleorder_entry.usr_codes, rv, data,"SaleOrder");
                                rv.setAdapter(ad);
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(context.getApplicationContext(), 4);
                                rv.setLayoutManager(gridLayoutManager);
                            }
                            else {

                                sale_entry.imgFilterCode.setVisibility(View.GONE);
                                sale_entry.fitercode = "Description";
                                Cursor cursor = DatabaseHelper.rawQuery("select distinct usr_code,description from Usr_Code where category=" + data.get(position).getCategory() + " order by usr_code");
                                if (sale_entry.usr_codes.size() > 0) sale_entry.usr_codes.clear();
                                if (frmmain.withoutclass.equals("true")) {
                                    sale_entry.usr_codes.add(new usr_code("Back", "Back"));
                                }
                                if (cursor != null && cursor.getCount() != 0) {
                                    if (cursor.moveToFirst()) {
                                        do {
                                            String usr_code = cursor.getString(cursor.getColumnIndex("usr_code"));
                                            String description = cursor.getString(cursor.getColumnIndex("description"));
                                            sale_entry.usr_codes.add(new usr_code(usr_code, description));
                                        } while (cursor.moveToNext());

                                    }

                                }
                                cursor.close();
                                usrcodeAdapter ad = new usrcodeAdapter(context, sale_entry.usr_codes, rv, data);
                                rv.setAdapter(ad);
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(context.getApplicationContext(), 4);
                                rv.setLayoutManager(gridLayoutManager);
                            }

                        }

                    }catch (Exception ee)
                    {
                            System.out.println(ee+" this is exception");
                }
                }
            });

        }




    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {

       Button btn;
        public MyViewHolder(View itemView) {
            super(itemView);
                btn=itemView.findViewById(R.id.info_text);
        }
    }
}
