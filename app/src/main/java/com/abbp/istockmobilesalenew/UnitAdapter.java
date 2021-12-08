package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.database.Cursor;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.MyViewHolder> {

    Context context;
    ArrayList<unitforcode> data=new ArrayList<>();
    TextView txtprice,txtqty,txtunit_qty,txtnet;
    Button btn,btnDiscount;
    int itemposition;
    boolean showqty=false;
    itemAdapter itemAdapter;
    int row_index=-1;
    androidx.appcompat.app.AlertDialog da;
    private String formname;
    public UnitAdapter(Context context, ArrayList<unitforcode> data, int itemposition, TextView txtprice, TextView txtqty, TextView txtunit_qty, TextView txtnet, Button btnDiscount, int row_index) {
        this.data = data;
        this.context = context;
        this.itemposition=itemposition;
        this.txtnet=txtnet;
        this.txtunit_qty=txtunit_qty;
        this.txtprice=txtprice;
        this.txtqty=txtqty;
        this.btnDiscount=btnDiscount;
        this.row_index=row_index;
        androidx.appcompat.app.AlertDialog da;
        showqty=true;
    }
    public UnitAdapter(Context context, ArrayList<unitforcode> data, int itemposition, TextView txtprice, TextView txtqty, TextView txtunit_qty, TextView txtnet, Button btnDiscount, int row_index,String frm) {
        this.data = data;
        this.context = context;
        this.itemposition=itemposition;
        this.txtnet=txtnet;
        this.txtunit_qty=txtunit_qty;
        this.txtprice=txtprice;
        this.txtqty=txtqty;
        this.btnDiscount=btnDiscount;
        this.row_index=row_index;
        androidx.appcompat.app.AlertDialog da;
        showqty=true;
        formname=frm;
    }

    public UnitAdapter(Context context, ArrayList<unitforcode> data, int itemposition, itemAdapter itemAdapter, androidx.appcompat.app.AlertDialog da) {
        this.data = data;
        this.context = context;
        this.itemposition=itemposition;
        this.itemAdapter=itemAdapter;
        showqty=false;
        this.da=da;

    }
    public UnitAdapter(Context context, ArrayList<unitforcode> data, int itemposition, itemAdapter itemAdapter, androidx.appcompat.app.AlertDialog da, String frm) {
        this.data = data;
        this.context = context;
        this.itemposition=itemposition;
        this.itemAdapter=itemAdapter;
        showqty=false;
        this.da=da;
        formname=frm;

    }
    private long GetPriceLevel() {
        long level=0;
        boolean useUserpricelevel=false;
        boolean useCustpricelevel=false;
        if(formname=="Sale Order"|| formname=="SaleOrder")
        {
            Cursor cursor=DatabaseHelper.rawQuery("select use_user_pricelevel,use_cust_pricelevel from SystemSetting");
            if(cursor!=null&&cursor.getCount()!=0)
            {
                if(cursor.moveToFirst())
                {
                    do {
                        useUserpricelevel=cursor.getInt(cursor.getColumnIndex("use_user_pricelevel"))==1?true:false;
                        useCustpricelevel=cursor.getInt(cursor.getColumnIndex("use_cust_pricelevel"))==1?true:false;
                    }while (cursor.moveToNext());


                }

            }
            cursor.close();
            if(useCustpricelevel)
            {
                String sSql ="select pricelevel from customer where customerid ="+saleorder_entry.sh.get(0).getCustomerid();
                cursor=DatabaseHelper.rawQuery(sSql);
                if(cursor!=null&&cursor.getCount()!=0)
                {
                    if(cursor.moveToFirst())
                    {
                        do {
                            level=cursor.getInt(cursor.getColumnIndex("pricelevel"));
                        }while (cursor.moveToNext());


                    }

                }
                cursor.close();

            }
            else if(useUserpricelevel)
            {
                String sSql = "select saleprice_level from posuser where userid="+ frmlogin.LoginUserid;
                cursor=DatabaseHelper.rawQuery(sSql);
                if(cursor!=null&&cursor.getCount()!=0)
                {
                    if(cursor.moveToFirst())
                    {
                        do {
                            level=cursor.getInt(cursor.getColumnIndex("saleprice_level"));
                        }while (cursor.moveToNext());


                    }

                }
                cursor.close();
            }
            else
            {
                level=0;
            }
        }
        else
        {
            Cursor cursor=DatabaseHelper.rawQuery("select use_user_pricelevel,use_cust_pricelevel from SystemSetting");
            if(cursor!=null&&cursor.getCount()!=0)
            {
                if(cursor.moveToFirst())
                {
                    do {
                        useUserpricelevel=cursor.getInt(cursor.getColumnIndex("use_user_pricelevel"))==1?true:false;
                        useCustpricelevel=cursor.getInt(cursor.getColumnIndex("use_cust_pricelevel"))==1?true:false;
                    }while (cursor.moveToNext());


                }

            }
            cursor.close();
            if(useCustpricelevel)
            {
                String sSql ="select pricelevel from customer where customerid ="+sale_entry.sh.get(0).getCustomerid();
                cursor=DatabaseHelper.rawQuery(sSql);
                if(cursor!=null&&cursor.getCount()!=0)
                {
                    if(cursor.moveToFirst())
                    {
                        do {
                            level=cursor.getInt(cursor.getColumnIndex("pricelevel"));
                        }while (cursor.moveToNext());


                    }

                }
                cursor.close();

            }
            else if(useUserpricelevel)
            {
                String sSql = "select saleprice_level from posuser where userid="+ frmlogin.LoginUserid;
                cursor=DatabaseHelper.rawQuery(sSql);
                if(cursor!=null&&cursor.getCount()!=0)
                {
                    if(cursor.moveToFirst())
                    {
                        do {
                            level=cursor.getInt(cursor.getColumnIndex("saleprice_level"));
                        }while (cursor.moveToNext());


                    }

                }
                cursor.close();
            }
            else
            {
                level=0;
            }
        }
        return  level;
    }
    @Override
    public UnitAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf= LayoutInflater.from(parent.getContext());
        View v=null;
        if(showqty)
        {
            v=lf.inflate(R.layout.unitbinding,parent,false);
        }
        else {
            v = lf.inflate(R.layout.headerbinding, parent, false);
        }
        return new UnitAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UnitAdapter.MyViewHolder holder, int position) {

        holder.btn.setText(" "+data.get(position).getShortdes());
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(formname=="Sale Order"|| formname=="SaleOrder")
                {
                    saleorder_entry.sd.get(itemposition).setUnt_type(data.get(position).getUnit_type());
                    saleorder_entry.sd.get(itemposition).setUnit_short(data.get(position).getShortdes());
                    String  price_level=GetSpecialPriceLevel();
                    saleorder_entry.sd.get(itemposition).setPriceLevel(price_level);

                    int level=0;
                    switch (price_level)
                    {
                        case "SP":
                            saleorder_entry.sd.get(itemposition).setSale_price(data.get(position).getSale_price());
                            saleorder_entry.sd.get(itemposition).setDis_price(data.get(position).getSale_price());
                            saleorder_entry.sd.get(itemposition).setDis_type(0);
                            level=0;

                            break;
                        case "SP1":
                            saleorder_entry.sd.get(itemposition).setSale_price(data.get(position).getSale_Price1());
                            saleorder_entry.sd.get(itemposition).setDis_price(data.get(position).getSale_Price1());
                            saleorder_entry.sd.get(itemposition).setDis_type(0);
                            level=1;
                            break;
                        case "SP2":
                            saleorder_entry.sd.get(itemposition).setSale_price(data.get(position).getSale_price2());
                            saleorder_entry.sd.get(itemposition).setDis_price(data.get(position).getSale_price2());
                            saleorder_entry.sd.get(itemposition).setDis_type(0);
                            level=2;
                            break;
                        case "SP3":
                            saleorder_entry.sd.get(itemposition).setSale_price(data.get(position).getSale_price3());
                            saleorder_entry.sd.get(itemposition).setDis_price(data.get(position).getSale_price3());
                            saleorder_entry.sd.get(itemposition).setDis_type(0);
                            level=3;
                            break;
                    }
                    saleorder_entry.sd.get(itemposition).setQty(saleorder_entry.sd.get(itemposition).getUnit_qty()*data.get(position).getSmallest_unit_qty());
                    if(showqty)
                    {
                        saleorder_entry.editUnit_type=data.get(position).getUnit_type();
                        saleorder_entry.txtshowUnit.setText(data.get(position).getShortdes());
                        txtprice.setText(String.valueOf(saleorder_entry.sd.get(itemposition).getSale_price()));
                        double qty = Double.parseDouble(txtunit_qty.getText().toString()) * data.get(position).getSmallest_unit_qty();
                        txtqty.setText(String.valueOf(qty));
                        double amt= Double.parseDouble(txtunit_qty.getText().toString())*saleorder_entry.sd.get(itemposition).getSale_price();
                        txtnet.setText(String.valueOf(amt));
                        btnDiscount.setText("Normal");
                        saleorder_entry.txtsmqty.setText(String.valueOf(getsqty(saleorder_entry.sd.get(itemposition).getCode(),data.get(position).getUnit_type())));
                        saleorder_entry.txtsprice.setText(String.valueOf(getSprice(saleorder_entry.sd.get(itemposition).getCode())));
                        saleorder_entry.getSummary();
                        row_index=position;
                        saleorder_entry.pad = new priceLevelAdapter(context, itemposition, saleorder_entry.txtChangePrice, saleorder_entry.txtChangeQty, saleorder_entry.txtamt,level);
                        saleorder_entry. rcvSP.setAdapter(saleorder_entry.pad);
                        GridLayoutManager gridPricelevel = new GridLayoutManager(context, 4);
                        saleorder_entry.rcvSP.setLayoutManager(gridPricelevel);
                        SummaryFormat(txtprice,saleorder_entry.sd.get(itemposition).getSale_price());
                        SummaryFormat(txtnet,amt);
                        saleorder_entry.uad.notifyDataSetChanged();


                    }
                    else {
                        saleorder_entry.itemAdapter.notifyDataSetChanged();
                        saleorder_entry.getSummary();
                        da.dismiss();
                    }
                }
                else
                {
                    sale_entry.sd.get(itemposition).setUnt_type(data.get(position).getUnit_type());
                    sale_entry.sd.get(itemposition).setUnit_short(data.get(position).getShortdes());
                    String  price_level=GetSpecialPriceLevel();
                    sale_entry.sd.get(itemposition).setPriceLevel(price_level);





                    int level=0;
                    switch (price_level)
                    {
                        case "SP":
                            sale_entry.sd.get(itemposition).setSale_price(data.get(position).getSale_price());
                            sale_entry.sd.get(itemposition).setDis_price(data.get(position).getSale_price());
                            sale_entry.sd.get(itemposition).setDis_type(0);
                            level=0;

                            break;
                        case "SP1":
                            sale_entry.sd.get(itemposition).setSale_price(data.get(position).getSale_Price1());
                            sale_entry.sd.get(itemposition).setDis_price(data.get(position).getSale_Price1());
                            sale_entry.sd.get(itemposition).setDis_type(0);
                            level=1;
                            break;
                        case "SP2":
                            sale_entry.sd.get(itemposition).setSale_price(data.get(position).getSale_price2());
                            sale_entry.sd.get(itemposition).setDis_price(data.get(position).getSale_price2());
                            sale_entry.sd.get(itemposition).setDis_type(0);
                            level=2;
                            break;
                        case "SP3":
                            sale_entry.sd.get(itemposition).setSale_price(data.get(position).getSale_price3());
                            sale_entry.sd.get(itemposition).setDis_price(data.get(position).getSale_price3());
                            sale_entry.sd.get(itemposition).setDis_type(0);
                            level=3;
                            break;
                    }
                    sale_entry.sd.get(itemposition).setQty(sale_entry.sd.get(itemposition).getUnit_qty()*data.get(position).getSmallest_unit_qty());
                    if(showqty)
                    {
                        sale_entry.editUnit_type=data.get(position).getUnit_type();
                        sale_entry.txtshowUnit.setText(data.get(position).getShortdes());
                        txtprice.setText(String.valueOf(sale_entry.sd.get(itemposition).getSale_price()));
                        double qty = Double.parseDouble(txtunit_qty.getText().toString()) * data.get(position).getSmallest_unit_qty();
                        txtqty.setText(String.valueOf(qty));
                        double amt= Double.parseDouble(txtunit_qty.getText().toString())*sale_entry.sd.get(itemposition).getSale_price();
                        txtnet.setText(String.valueOf(amt));
                        btnDiscount.setText("Normal");
                        sale_entry.txtsmqty.setText(String.valueOf(getsqty(sale_entry.sd.get(itemposition).getCode(),data.get(position).getUnit_type())));
                        sale_entry.txtsprice.setText(String.valueOf(getSprice(sale_entry.sd.get(itemposition).getCode())));
                        sale_entry.getSummary();
                        row_index=position;
                        sale_entry.pad = new priceLevelAdapter(context, itemposition, sale_entry.txtChangePrice, sale_entry.txtChangeQty, sale_entry.txtamt,level);
                        sale_entry. rcvSP.setAdapter(sale_entry.pad);
                        GridLayoutManager gridPricelevel = new GridLayoutManager(context, 4);
                        sale_entry.rcvSP.setLayoutManager(gridPricelevel);
                        SummaryFormat(txtprice,sale_entry.sd.get(itemposition).getSale_price());
                        SummaryFormat(txtnet,amt);
                        sale_entry.uad.notifyDataSetChanged();


                    }
                    else {
                        Cursor cursor=DatabaseHelper.rawQuery("select smallest_unit_qty from usr_code where code="+sale_entry.sd.get(itemposition).getCode()+
                                " and unit_type="+sale_entry.sd.get(itemposition).getUnt_type()
                        );
                        if(cursor!=null&&cursor.getCount()!=0)
                        {
                            if(cursor.moveToFirst())
                            {
                                do {
                                    double sqty=cursor.getDouble(cursor.getColumnIndex("smallest_unit_qty"));
                                    double gallon=sale_entry.sd.get(itemposition).getUnit_qty()*sqty;
                                    sale_entry.sd.get(itemposition).setGallon(gallon);

                                }while (cursor.moveToNext());

                            }

                        }
                        cursor.close();
                        sale_entry.itemAdapter.notifyDataSetChanged();
                        sale_entry.getSummary();
                        da.dismiss();
                    }
                }

            }
        });
        if(row_index==position){
            holder.btn.setBackgroundResource(R.drawable.usercodegradiant);
        }
        else
        {
            holder.btn.setBackgroundResource(R.drawable.unitgradiant);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    private static void SummaryFormat(TextView source,double value) {
        String numberAsString = String.format("%,."+frmmain.price_places+"f", value);
        if(source!=null){
            source.setText(numberAsString);
        }
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button btn;
        public MyViewHolder(View itemView) {
            super(itemView);
            if(showqty)
            {
                btn=itemView.findViewById(R.id.btnunit);
            }
            else
            {
                btn=itemView.findViewById(R.id.info_text);
                btn.setBackgroundResource(R.drawable.usercodegradiant);

            }

        }
    }
    private double getsqty(long code,int unit_type)
    {
        double sqty=0;
        Cursor cursor=DatabaseHelper.rawQuery("select smallest_unit_qty from usr_code where code="+code+" and unit_type="+unit_type);
        if(cursor!=null&&cursor.getCount()!=0)
        {
            if(cursor.moveToFirst())
            {
                do {
                    sqty=cursor.getDouble(cursor.getColumnIndex("smallest_unit_qty"));

                }while (cursor.moveToNext());


            }

        }
        cursor.close();
        return  sqty;
    }
    private  double getSprice(long code)
    {
        double sPrice=0;
        Cursor cursor=DatabaseHelper.rawQuery("select sale_price from usr_code where code="+code+" and unit_type=(select max(unit_type) from usr_code where code="+code+")");
        if(cursor!=null&&cursor.getCount()!=0)
        {
            if(cursor.moveToFirst())
            {
                do {
                    sPrice=cursor.getDouble(cursor.getColumnIndex("sale_price"));

                }while (cursor.moveToNext());


            }

        }
        cursor.close();
        return  sPrice;
    }
    public String GetSpecialPriceLevel()
    {
       String price_level="";

        if(formname=="Sale Order"||formname=="SaleOrder")
        {
             price_level=saleorder_entry.sd.get(itemposition).getPriceLevel();
            long level=0;
            boolean useUserpricelevel=false;
            boolean useCustpricelevel=false;
            boolean useSpecialPrice=false;
            Cursor cursor=DatabaseHelper.rawQuery("select use_user_pricelevel,use_cust_pricelevel,use_specialprice from SystemSetting");
            if(cursor!=null&&cursor.getCount()!=0)
            {
                if(cursor.moveToFirst())
                {
                    do {
                        useUserpricelevel=cursor.getInt(cursor.getColumnIndex("use_user_pricelevel"))==1?true:false;
                        useCustpricelevel=cursor.getInt(cursor.getColumnIndex("use_cust_pricelevel"))==1?true:false;
                        useSpecialPrice=cursor.getInt(cursor.getColumnIndex("use_specialprice"))==1?true:false;
                    }while (cursor.moveToNext());

                }

            }
            cursor.close();

            if(useSpecialPrice)
            {
                String sql="select sale_price,price_level from S_SalePrice where code="+saleorder_entry.sd.get(itemposition).getCode()+
                        " and unit_type="+saleorder_entry.sd.get(itemposition).getUnt_type()+" and ( "+
                        saleorder_entry.sd.get(itemposition).getUnit_qty()+" between min_qty and max_qty)";
                cursor=DatabaseHelper.rawQuery(sql);

                if (cursor != null && cursor.getCount() != 0)
                {
                    if(cursor.moveToFirst())
                    {
                        do {
                            double sale_price=cursor.getDouble(cursor.getColumnIndex("Sale_Price"));
                            level=cursor.getInt(cursor.getColumnIndex("price_level"));
                            price_level=level==0?"SP":level==1?"SP1":level==2?"SP2":"SP3";
                        }while (cursor.moveToNext());

                    }

                }
            }
        }
        else
        {
            price_level=sale_entry.sd.get(itemposition).getPriceLevel();
            long level=0;
            boolean useUserpricelevel=false;
            boolean useCustpricelevel=false;
            boolean useSpecialPrice=false;
            Cursor cursor=DatabaseHelper.rawQuery("select use_user_pricelevel,use_cust_pricelevel,use_specialprice from SystemSetting");
            if(cursor!=null&&cursor.getCount()!=0)
            {
                if(cursor.moveToFirst())
                {
                    do {
                        useUserpricelevel=cursor.getInt(cursor.getColumnIndex("use_user_pricelevel"))==1?true:false;
                        useCustpricelevel=cursor.getInt(cursor.getColumnIndex("use_cust_pricelevel"))==1?true:false;
                        useSpecialPrice=cursor.getInt(cursor.getColumnIndex("use_specialprice"))==1?true:false;
                    }while (cursor.moveToNext());

                }

            }
            cursor.close();

            if(useSpecialPrice)
            {
                String sql="select sale_price,price_level from S_SalePrice where code="+sale_entry.sd.get(itemposition).getCode()+
                        " and unit_type="+sale_entry.sd.get(itemposition).getUnt_type()+" and ( "+
                        sale_entry.sd.get(itemposition).getUnit_qty()+" between min_qty and max_qty)";
                cursor=DatabaseHelper.rawQuery(sql);

                if (cursor != null && cursor.getCount() != 0)
                {
                    if(cursor.moveToFirst())
                    {
                        do {
                            double sale_price=cursor.getDouble(cursor.getColumnIndex("Sale_Price"));
                            level=cursor.getInt(cursor.getColumnIndex("price_level"));
                            price_level=level==0?"SP":level==1?"SP1":level==2?"SP2":"SP3";
                        }while (cursor.moveToNext());

                    }

                }
            }
        }
        return  price_level;
    }

}
