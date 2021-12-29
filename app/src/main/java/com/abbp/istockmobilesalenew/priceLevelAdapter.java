package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.database.Cursor;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class priceLevelAdapter extends RecyclerView.Adapter<priceLevelAdapter.MyViewHolder> {
    Context context;
    ArrayList<PriceLevel> priceLevels = new ArrayList<>();
    int itemposistion;
    TextView txtprice, txtnet, txtqty;
    int row_index = -1;
    private static String formname;

    public priceLevelAdapter(Context context, int itemposistion, TextView txtprice, TextView txtqty, TextView txtnet, int row_index) {
        this.context = context;
        this.itemposistion = itemposistion;
        this.txtprice = txtprice;
        this.txtqty = txtqty;
        this.txtnet = txtnet;
        this.row_index = row_index;
        formname = "Sale";
        Binding_PriceLevel();
    }

    public priceLevelAdapter(Context context, int itemposistion, TextView txtprice, TextView txtqty, TextView txtnet, int row_index, String frm) {
        this.context = context;
        this.itemposistion = itemposistion;
        this.txtprice = txtprice;
        this.txtqty = txtqty;
        this.txtnet = txtnet;
        this.row_index = row_index;
        formname = frm;
        Binding_PriceLevel();
    }

    public void Binding_PriceLevel() {
        for (int i = 0; i < 4; i++) {
            String SP = i == 0 ? "SP" : "SP" + i;
            PriceLevel priceLevel = new PriceLevel(i, SP);
            priceLevels.add(priceLevel);
        }
    }

    @Override
    public priceLevelAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        View v = null;
        v = lf.inflate(R.layout.unitbinding, parent, false);
        return new priceLevelAdapter.MyViewHolder(v);
    }

    //not allow_pricelevel in price lewel Adapter for sale entry Editinfo btn click Level SP modified by ABBP
    @Override
    public void onBindViewHolder(priceLevelAdapter.MyViewHolder holder, int position) {
        holder.btn.setText(" " + priceLevels.get(position).getLevel_name());
        if (frmlogin.allow_priceLevel == 0) {
            holder.btn.setEnabled(false);
        }
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formname == "Sale Order") {
                    saleorder_entry.sd.get(itemposistion).setPriceLevel(priceLevels.get(position).getLevel_name());
                    saleorder_entry.txtShowSP.setText(priceLevels.get(position).getLevel_name());
                    double sale_price = getSalePrice(priceLevels.get(position).getLevel_name());
                    saleorder_entry.sd.get(itemposistion).setSale_price(sale_price);
                    saleorder_entry.sd.get(itemposistion).setDis_price(sale_price);
                    // txtprice.setText(String.valueOf(sale_price));
                    double amt = Double.parseDouble(txtqty.getText().toString()) * sale_price;
                    // txtnet.setText(String.valueOf(amt));
                    SummaryFormat(txtnet, amt);
                    SummaryFormat(txtprice, sale_price);
                    saleorder_entry.getSummary();

                    row_index = position;
                    saleorder_entry.pad.notifyDataSetChanged();
                } else {
                    sale_entry.sd.get(itemposistion).setPriceLevel(priceLevels.get(position).getLevel_name());
                    sale_entry.txtShowSP.setText(priceLevels.get(position).getLevel_name());
                    double sale_price = getSalePrice(priceLevels.get(position).getLevel_name());
                    sale_entry.sd.get(itemposistion).setSale_price(sale_price);
                    sale_entry.sd.get(itemposistion).setDis_price(sale_price);
                    // txtprice.setText(String.valueOf(sale_price));
                    double amt = Double.parseDouble(txtqty.getText().toString()) * sale_price;
                    // txtnet.setText(String.valueOf(amt));
                    SummaryFormat(txtnet, amt);
                    SummaryFormat(txtprice, sale_price);
                    sale_entry.getSummary();

                    row_index = position;
                    sale_entry.pad.notifyDataSetChanged();
                }

            }
        });
        if (row_index == position) {
            holder.btn.setBackgroundResource(R.drawable.usercodegradiant);
        } else {
            holder.btn.setBackgroundResource(R.drawable.unitgradiant);
        }
    }

    private static void SummaryFormat(TextView source, double value) {
        String numberAsString = String.format("%,." + frmmain.price_places + "f", value);
        if (source != null) {
            source.setText(numberAsString);
        }
    }

    private double getSalePrice(String pricelevel) {

        double sale_price = 0;
        String level = "";
        switch (pricelevel) {
            case "SP":
                level = "uc.sale_price";
                break;
            case "SP1":
                level = "uc.sale_price1";
                break;
            case "SP2":
                level = "uc.sale_price2";
                break;
            case "SP3":
                level = "uc.sale_price3";
                break;
        }
        if (formname == "Sale Order") {
            String sqlString = "select uc.unit_type,code,description," + level + ",smallest_unit_qty,unitname,unitshort,CalNoTax from Usr_Code uc " +
                    " where code=" + saleorder_entry.sd.get(itemposistion).getCode() + " and unit_type=" + saleorder_entry.sd.get(itemposistion).getUnt_type();
            Cursor cursor = DatabaseHelper.rawQuery(sqlString);
            if (cursor != null && cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {
                        switch (pricelevel) {
                            case "SP":
                                level = "sale_price";
                                break;
                            case "SP1":
                                level = "sale_price1";
                                break;
                            case "SP2":
                                level = "sale_price2";
                                break;
                            case "SP3":
                                level = "sale_price3";
                                break;
                        }
                        sale_price = cursor.getDouble(cursor.getColumnIndex(level));

                    } while (cursor.moveToNext());


                }

            }
            cursor.close();
        } else {
            String sqlString = "select uc.unit_type,code,description," + level + ",smallest_unit_qty,unitname,unitshort,CalNoTax from Usr_Code uc " +
                    " where code=" + sale_entry.sd.get(itemposistion).getCode() + " and unit_type=" + sale_entry.sd.get(itemposistion).getUnt_type();
            Cursor cursor = DatabaseHelper.rawQuery(sqlString);
            if (cursor != null && cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {
                        switch (pricelevel) {
                            case "SP":
                                level = "sale_price";
                                break;
                            case "SP1":
                                level = "sale_price1";
                                break;
                            case "SP2":
                                level = "sale_price2";
                                break;
                            case "SP3":
                                level = "sale_price3";
                                break;
                        }
                        sale_price = cursor.getDouble(cursor.getColumnIndex(level));

                    } while (cursor.moveToNext());


                }

            }
            cursor.close();
        }
        return sale_price;

    }

    @Override
    public int getItemCount() {
        return priceLevels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button btn;

        public MyViewHolder(View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.btnunit);
        }
    }
}
