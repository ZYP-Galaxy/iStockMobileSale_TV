package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.abbp.istockmobilesalenew.sale_entry.itemDis_tmp;
import static com.abbp.istockmobilesalenew.sale_entry.itemPosition;
import static com.abbp.istockmobilesalenew.sale_entry.sd;

public class itemAdapter extends BaseAdapter {
    Context context;
    boolean isqty = false;
    boolean isSalePrice=false;
    boolean isgallon=false;
    boolean isAmount=false;
    String keynum = "";
    AlertDialog msg,dialog,da;
    ArrayList<unitforcode> uc=new ArrayList<>();
    public static TextView txtamt;
    TextView tv4;
    TextView tv5;
    boolean startOpen;
    public  static  itemAdapter itemAd;
    public  static double disamt;
    public static String formname;//added by YLT

    public itemAdapter(Context context) {
         this.context = context;
         formname="Sale";

    }
    public itemAdapter(Context context,String frm) {//added by YLT
        this.context = context;
        formname=frm;//added by YLT

    }

    @Override


    public int getCount()
    {
        if(formname =="SaleOrder")
        {
            return saleorder_entry.sd.size();//added by YLT
        }
        else {
            return sale_entry.sd.size();
        }
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
        LayoutInflater lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = lf.inflate(R.layout.dataitem, null, false);
        TextView tv = (TextView) convertView.findViewById(R.id.sr);
        TextView tv1 = (TextView) convertView.findViewById(R.id.desc);
        TextView tv2 = (TextView) convertView.findViewById(R.id.qty);
        TextView tv3 = (TextView) convertView.findViewById(R.id.unit);
        TextView tvgallon = convertView.findViewById(R.id.gallon);
        if(formname=="Sale") {

            if (frmlogin.use_oil == 1) {
                tvgallon.setVisibility(View.VISIBLE);
                isgallon=false;
            } else {
                tvgallon.setVisibility(View.GONE);
                isgallon=false;
            }

            String qtyAsString = String.format("%." + frmmain.qty_places + "f", sale_entry.sd.get(position).getGallon());
            tvgallon.setText(qtyAsString);

            tvgallon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isqty = false;
                    isSalePrice=false;
                    isgallon=true;
                    keynum=tv2.getText().toString();
                    showKeyPad(tvgallon, tvgallon, position);
                    itemPosition=-1;
                }
            });
        }
        else
        {
            tvgallon.setVisibility(View.GONE);
            isgallon=false;
        }

        boolean use_unit=false;
        Cursor cursorplvl=DatabaseHelper.rawQuery("select use_unit from SystemSetting");
        if(cursorplvl!=null&&cursorplvl.getCount()!=0)
        {
            if(cursorplvl.moveToFirst())
            {
                do {
                    use_unit=cursorplvl.getInt(cursorplvl.getColumnIndex("use_unit"))==1?true:false;
                }while (cursorplvl.moveToNext());


            }

        }
        cursorplvl.close();
        tv3.setVisibility(use_unit==true? View.VISIBLE: View.GONE);
        tv4 = (TextView) convertView.findViewById(R.id.amt);
        tv5=convertView.findViewById(R.id.txtDelete);

        if(formname =="SaleOrder")//added by YLT
        {
            tv.setText(String.valueOf(saleorder_entry.sd.get(position).getSr()));
            tv1.setText(saleorder_entry.sd.get(position).getDesc());
            Double unit_qty = saleorder_entry.sd.get(position).getUnit_qty();
            String qtyAsString = String.format("%." + frmmain.qty_places + "f", unit_qty);
            tv2.setText(qtyAsString);
            tv3.setText(saleorder_entry.sd.get(position).getUnit_short());
            double amt = saleorder_entry.sd.get(position).getSale_price() * saleorder_entry.sd.get(position).getUnit_qty();
            String numberAsString = String.format("%,." + frmmain.price_places + "f", amt);
            tv4.setText(String.valueOf(numberAsString));
        }
        else {
            tv.setText(String.valueOf(sale_entry.sd.get(position).getSr()));
            tv1.setText(sale_entry.sd.get(position).getDesc());
            Double unit_qty = sale_entry.sd.get(position).getUnit_qty();
            String qtyAsString = String.format("%." + frmmain.qty_places + "f", unit_qty);
            tv2.setText(qtyAsString);
            tv3.setText(sale_entry.sd.get(position).getUnit_short());
            double amt = sale_entry.sd.get(position).getSale_price() * sale_entry.sd.get(position).getUnit_qty();
            String numberAsString = String.format("%,." + frmmain.price_places + "f", amt);
            tv4.setText(String.valueOf(numberAsString));
        }


        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isqty = true;
                isSalePrice=false;
                isgallon=false;
                keynum=tv2.getText().toString();
                showKeyPad(tv2, tv2, position);
                itemPosition=-1;
            }
        });
        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder bd=new AlertDialog.Builder(context,R.style.AlertDialogTheme);
                bd.setTitle("iStock");
                bd.setMessage("Are you sure want to delete this row?");
                bd.setCancelable(false);
                bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                        if(formname =="SaleOrder")//added by YLT
                        {
                            saleorder_entry.sd.remove(position);
                            itemPosition = -1;
                            for (int i = 0; i < saleorder_entry.sd.size(); i++) {
                                saleorder_entry.sd.get(i).setSr(i + 1);
                            }
                            saleorder_entry.getData();
                            saleorder_entry.getSummary();
                            if (saleorder_entry.sd.size() == 0) {
                                String tax = "Tax" + (saleorder_entry.getTax() > 0 ? "( " + saleorder_entry.getTax() + "% )" : "");
                                saleorder_entry.txttax.setText(tax);
                                saleorder_entry.sh.get(0).setTax_per(saleorder_entry.getTax());
                                saleorder_entry.sh.get(0).setTax_amount(0.0);
                                saleorder_entry.sh.get(0).setDiscount(0.0);
                                saleorder_entry.sh.get(0).setDiscount_per(0);
                                saleorder_entry.sh.get(0).setPaid_amount(0);
                                saleorder_entry.txtvoudis.setText("0");
                                saleorder_entry.txtpaid.setText("0");
                                saleorder_entry.getSummary();
                            }
                            dialog.dismiss();
                        }
                        else {
                            sale_entry.sd.remove(position);
                            itemPosition = -1;
                            for (int i = 0; i < sale_entry.sd.size(); i++) {
                                sale_entry.sd.get(i).setSr(i + 1);
                            }
                            sale_entry.getData();
                            sale_entry.getSummary();
                            if (sale_entry.sd.size() == 0) {
                                String tax = "Tax" + (sale_entry.getTax() > 0 ? "( " + sale_entry.getTax() + "% )" : "");
                                sale_entry.txttax.setText(tax);
                                sale_entry.sh.get(0).setTax_per(sale_entry.getTax());
                                sale_entry.sh.get(0).setTax_amount(0.0);
                                sale_entry.sh.get(0).setDiscount(0.0);
                                sale_entry.sh.get(0).setDiscount_per(0);
                                sale_entry.sh.get(0).setPaid_amount(0);
                                sale_entry.txtvoudis.setText("0");
                                sale_entry.txtpaid.setText("0");
                                sale_entry.getSummary();
                            }
                            dialog.dismiss();
                        }
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
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (formname == "SaleOrder")//added by YLT
                {
                    isqty = false;
                    isSalePrice = true;
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.changesaleprice, null);
                    builder.setView(view);
                    TextView txtheader = view.findViewById(R.id.caption);


                    String title = saleorder_entry.sd.get(position).getDesc();
                    txtheader.setText(title);
                    TextView txtChangePrice = view.findViewById(R.id.txtChangePrice);
                    RelativeLayout rlAmount=view.findViewById(R.id.rlAmount);
                     rlAmount.setVisibility(View.INVISIBLE);
                    txtamt = view.findViewById(R.id.txtChangeAmt);
                    ImageButton save = view.findViewById(R.id.imgSave);
                    //txtChangePrice.setText(String.valueOf(sale_entry.sd.get(position).getSale_price()));
                    txtChangePrice.setText(String.format("%,." + frmmain.price_places + "f", saleorder_entry.sd.get(position).getSale_price()));
                    disamt = saleorder_entry.sd.get(position).getSale_price() - saleorder_entry.sd.get(position).getDis_price();
                    Double amt = Double.parseDouble(ClearFormat(txtChangePrice.getText().toString())) * saleorder_entry.sd.get(position).getUnit_qty();

                    String numberAsString = String.format("%,." + frmmain.price_places + "f", amt);
                    txtamt.setText(numberAsString);

                    //not change_price in sale entry entrygrid amount click modified by ABBP
                    //add open_price modified by ABBP
                    txtChangePrice.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //keynum=txtChangePrice.getText().toString();
                            int op = saleorder_entry.sd.get(position).getOpen_price();
                            if (frmlogin.change_price == 1 || op == 1) {
                                keynum = String.format("%,." + frmmain.price_places + "f", Double.parseDouble(ClearFormat(txtChangePrice.getText().toString())));
                                showKeyPad(txtChangePrice, txtChangePrice, position);
                            }
                            Double amt = Double.parseDouble(ClearFormat(txtChangePrice.getText().toString())) * saleorder_entry.sd.get(position).getUnit_qty();
                            String numberAsString = String.format("%,." + frmmain.price_places + "f", amt);
                            txtamt.setText(numberAsString);

                        }
                    });

                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            saleorder_entry.sd.get(position).setSale_price(Double.parseDouble(ClearFormat(txtChangePrice.getText().toString())));
                            saleorder_entry.sd.get(position).setDis_price(Double.parseDouble(ClearFormat(txtChangePrice.getText().toString())));

                            if (saleorder_entry.sd.get(position).getDis_type() == 3
                                    || saleorder_entry.sd.get(position).getDis_type() == 4
                                    || saleorder_entry.sd.get(position).getDis_type() == 6
                                    || saleorder_entry.sd.get(position).getDis_type() == 7) {
                                saleorder_entry.sd.get(position).setDis_percent(0);
                                saleorder_entry.sd.get(position).setDis_price(saleorder_entry.sd.get(position).getSale_price());
                            } else if (saleorder_entry.sd.get(position).getDis_type() == 1
                                    || saleorder_entry.sd.get(position).getDis_type() == 2) {
                                double dispercent = saleorder_entry.sd.get(position).getDis_type() == 1 ? 0.05 : 0.1;
                                double discount = saleorder_entry.sd.get(position).getDis_type() == 1 ? 5 : 10;
                                saleorder_entry.sd.get(position).setDis_percent(discount);
                                double dis_price = saleorder_entry.sd.get(position).getSale_price() - (saleorder_entry.sd.get(position).getSale_price() * (dispercent));
                                saleorder_entry.sd.get(position).setDis_price(dis_price);
                            } else if (saleorder_entry.sd.get(position).getDis_type() == 5) {
                                if (saleorder_entry.sd.get(position).getDis_percent() > 0) {
                                    double dis_percent = saleorder_entry.sd.get(position).getDis_percent();
                                    saleorder_entry.sd.get(position).setDis_percent(dis_percent);
                                    double dis_price = saleorder_entry.sd.get(position).getSale_price() - (saleorder_entry.sd.get(position).getSale_price() * (dis_percent / 100));
                                    saleorder_entry.sd.get(position).setDis_price(dis_price);


                                } else {
                                    double dis_percent = 0;
                                    saleorder_entry.sd.get(position).setDis_percent(dis_percent);
                                    double dis_price = saleorder_entry.sd.get(position).getSale_price() - disamt;
                                    saleorder_entry.sd.get(position).setDis_price(dis_price);
                                }
                            }

                            saleorder_entry.getSummary();
                            saleorder_entry.entrygrid.setAdapter(itemAd);
                            saleorder_entry.entrygrid.setSelection(position);
                            itemPosition = -1;
                            da.dismiss();

                        }
                    });
                    da = builder.create();
                    da.show();
                }
                //for Sale
                else {
                    isqty = false;
                    isSalePrice = true;
                    isgallon=false;
                    isAmount=false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.changesaleprice, null);
                    builder.setView(view);
                    TextView txtheader = view.findViewById(R.id.caption);
                    String title = sale_entry.sd.get(position).getDesc();
                    txtheader.setText(title);
                    TextView txtChangePrice = view.findViewById(R.id.txtChangePrice);
                    TextView txtAmount=view.findViewById(R.id.txtAmount);
                    RelativeLayout rlAmount=view.findViewById(R.id.rlAmount);
                    if(frmlogin.use_oil!=1)
                    {
                        rlAmount.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        rlAmount.setVisibility(View.VISIBLE);
                    }
                    txtamt = view.findViewById(R.id.txtChangeAmt);
                    ImageButton save = view.findViewById(R.id.imgSave);
                    //txtChangePrice.setText(String.valueOf(sale_entry.sd.get(position).getSale_price()));
                    txtChangePrice.setText(String.format("%,." + frmmain.price_places + "f", sale_entry.sd.get(position).getSale_price()));
                    disamt = sale_entry.sd.get(position).getSale_price() - sale_entry.sd.get(position).getDis_price();
                    Double amt = Double.parseDouble(ClearFormat(txtChangePrice.getText().toString())) * sale_entry.sd.get(position).getUnit_qty();

                    String numberAsString = String.format("%,." + frmmain.price_places + "f", amt);
                    txtamt.setText(numberAsString);
                    txtAmount.setText(numberAsString);

                    //not change_price in sale entry entrygrid amount click modified by ABBP
                    //add open_price modified by ABBP
                    txtChangePrice.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //keynum=txtChangePrice.getText().toString();
                            int op = sale_entry.sd.get(position).getOpen_price();
                            if (frmlogin.change_price == 1 || op == 1) {
                                keynum = String.format("%." + frmmain.price_places + "f", Double.parseDouble(ClearFormat(txtChangePrice.getText().toString())));
                                showKeyPad(txtChangePrice, txtChangePrice, position);
                            }
                            Double amt = Double.parseDouble(ClearFormat(txtChangePrice.getText().toString())) * sale_entry.sd.get(position).getUnit_qty();
                            String numberAsString = String.format("%,." + frmmain.price_places + "f", amt);
                            txtamt.setText(numberAsString);

                        }
                    });
                    txtAmount.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isAmount=true;
                            keynum = String.format("%." + frmmain.price_places + "f", Double.parseDouble(ClearFormat(txtAmount.getText().toString())));
                            showKeyPad(txtChangePrice, txtAmount, position);

                        }
                    });

                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            sale_entry.sd.get(position).setSale_price(Double.parseDouble(ClearFormat(txtChangePrice.getText().toString())));
                            sale_entry.sd.get(position).setDis_price(Double.parseDouble(ClearFormat(txtChangePrice.getText().toString())));

                            if (sale_entry.sd.get(position).getDis_type() == 3
                                    || sale_entry.sd.get(position).getDis_type() == 4
                                    || sale_entry.sd.get(position).getDis_type() == 6
                                    || sale_entry.sd.get(position).getDis_type() == 7) {
                                sale_entry.sd.get(position).setDis_percent(0);
                                sale_entry.sd.get(position).setDis_price(sale_entry.sd.get(position).getSale_price());
                            } else if (sale_entry.sd.get(position).getDis_type() == 1
                                    || sale_entry.sd.get(position).getDis_type() == 2) {
                                double dispercent = sale_entry.sd.get(position).getDis_type() == 1 ? 0.05 : 0.1;
                                double discount = sale_entry.sd.get(position).getDis_type() == 1 ? 5 : 10;
                                sale_entry.sd.get(position).setDis_percent(discount);
                                double dis_price = sale_entry.sd.get(position).getSale_price() - (sale_entry.sd.get(position).getSale_price() * (dispercent));
                                sale_entry.sd.get(position).setDis_price(dis_price);
                            } else if (sale_entry.sd.get(position).getDis_type() == 5) {
                                if (sale_entry.sd.get(position).getDis_percent() > 0) {
                                    double dis_percent = sale_entry.sd.get(position).getDis_percent();
                                    sale_entry.sd.get(position).setDis_percent(dis_percent);
                                    double dis_price = sale_entry.sd.get(position).getSale_price() - (sale_entry.sd.get(position).getSale_price() * (dis_percent / 100));
                                    sale_entry.sd.get(position).setDis_price(dis_price);


                                } else {
                                    double dis_percent = 0;
                                    sale_entry.sd.get(position).setDis_percent(dis_percent);
                                    double dis_price = sale_entry.sd.get(position).getSale_price() - disamt;
                                    sale_entry.sd.get(position).setDis_price(dis_price);
                                }
                            }

                            sale_entry.getSummary();
                            sale_entry.entrygrid.setAdapter(itemAd);
                            sale_entry.entrygrid.setSelection(position);
                            itemPosition = -1;
                            da.dismiss();

                        }
                    });
                    da = builder.create();
                    da.show();

                }
            }

        });
       tv3.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               try {

                   String sqlString;
                   int unitcount=0;
                   String filter;
                   Cursor cursor = null;
                   if(uc.size()>0)uc.clear();
                   AlertDialog.Builder bd = new AlertDialog.Builder(context);
                   LayoutInflater inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                   View view=inflater.inflate(R.layout.changeheadervalue, null);
                   bd.setCancelable(false);
                   bd.setView(view);
                   RecyclerView rv = view.findViewById(R.id.rcvChange);
                   ImageButton imgClose=view.findViewById(R.id.imgNochange);
                   da = bd.create();
                   imgClose.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           da.dismiss();
                       }
                   });
                   EditText etdSearch=view.findViewById(R.id.etdSearch);
                   ImageButton imgSearch=view.findViewById(R.id.imgSearch);
                   etdSearch.setVisibility(View.GONE);
                   imgSearch.setVisibility(View.GONE);
                    if(formname=="SaleOrder")//added by YLT
                    {
                        sqlString = "select * from Usr_Code where unit>0 and code=" + saleorder_entry.sd.get(position).getCode() + " order by unit_type";
                        cursor = DatabaseHelper.rawQuery(sqlString);
                    }
                    else {
                        sqlString = "select * from Usr_Code where unit>0 and code=" + sale_entry.sd.get(position).getCode() + " order by unit_type";
                        cursor = DatabaseHelper.rawQuery(sqlString);
                    }
                   unitcount=cursor.getCount();
                   if (cursor != null && cursor.getCount() != 0) {
                       if (cursor.moveToFirst()) {
                           do {
                               int code = cursor.getInt(cursor.getColumnIndex("code"));
                               String usr_code = cursor.getString(cursor.getColumnIndex("usr_code"));
                               int unit_type = cursor.getShort(cursor.getColumnIndex("unit_type"));
                               int unit = cursor.getInt(cursor.getColumnIndex("unit"));
                               String unitname = cursor.getString(cursor.getColumnIndex("unitname"));
                               String shortdes = cursor.getString(cursor.getColumnIndex("unitshort"));
                               double saleprice = cursor.getDouble(cursor.getColumnIndex("sale_price"));
                               double saleprice1 = cursor.getDouble(cursor.getColumnIndex("sale_price1"));
                               double saleprice2 = cursor.getDouble(cursor.getColumnIndex("sale_price2"));
                               double saleprice3 = cursor.getDouble(cursor.getColumnIndex("sale_price3"));
                               double sqty = cursor.getDouble((cursor.getColumnIndex("smallest_unit_qty")));
                               uc.add(new unitforcode(code, usr_code, unit_type, unit, unitname, shortdes, saleprice, sqty,saleprice1,saleprice2,saleprice3));

                           } while (cursor.moveToNext());

                       }
                   }

                   cursor.close();

                   UnitAdapter ad = new UnitAdapter(context,uc,position,itemAd,da,formname);
                   rv.setAdapter(ad);
                   GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 4);
                   rv.setLayoutManager(gridLayoutManager);
                   if (unitcount>0)
                   {
                       da.show();
                   }
                   cursor = null;
                   itemPosition=-1;


               }
               catch (Exception e)
               {
                   da.dismiss();
               }
           }
       });

        return convertView;
    }

    private void showKeyPad(TextView txt, TextView source, int itemposition) {

        startOpen = true;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.keypad, null);
        float density = context.getResources().getDisplayMetrics().density;
        final PopupWindow pw = new PopupWindow(layout, (int) density * 310, (int) density * 500, true);
        pw.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pw.setOutsideTouchable(false);
        pw.showAsDropDown(txt);
        Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnc, btndec, btndel, btnenter, btnper;
        btn1 = layout.findViewById(R.id.txt1);
        btn2 = layout.findViewById(R.id.txt2);
        btn3 = layout.findViewById(R.id.txt3);
        btn4 = layout.findViewById(R.id.txt4);
        btn5 = layout.findViewById(R.id.txt5);
        btn6 = layout.findViewById(R.id.txt6);
        btn7 = layout.findViewById(R.id.txt7);
        btn8 = layout.findViewById(R.id.txt8);
        btn9 = layout.findViewById(R.id.txt9);
        btn0 = layout.findViewById(R.id.txt0);
        btnc = layout.findViewById(R.id.txtc);
        btndec = layout.findViewById(R.id.txtdec);
        btnenter = layout.findViewById(R.id.txtenter);
        btndel = layout.findViewById(R.id.txtdel);
        btnper = layout.findViewById(R.id.btnpercent);
        TextView txtNum = layout.findViewById(R.id.txtNum);
        btnper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btnper.getText());
                keynum = txtNum.getText().toString();
            }
        });
        txtNum.setText(String.valueOf(keynum));

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                if(txtNum.getText().equals("0")){
                    txtNum.setText(btn1.getText());
                    keynum = txtNum.getText().toString();
                }
                else {
                    txtNum.setText(keynum + btn1.getText());
                    keynum = txtNum.getText().toString();
                }

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btn2.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btn3.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btn4.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btn5.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btn6.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btn7.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btn8.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btn9.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btn0.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText("0");
                keynum = txtNum.getText().toString();
            }
        });
        btndec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btndec.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btndel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (keynum.length() != 0) {
                    keynum = keynum.substring(0, keynum.length() - 1);
                    txtNum.setText(keynum);

                }
                if(keynum.length()==0){
                    keynum="0";
                    txtNum.setText(keynum);
                }
                startOpen = false;

            }
        });
        btnenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Double check = Double.parseDouble(keynum);
                    if (isqty)
                    {

                        if(formname=="SaleOrder")
                        {
                            check = check > 0 ? check : 1;
                            saleorder_entry.sd.get(itemposition).setUnit_qty(check);
                            source.setText(String.valueOf(check));
                            GetSpecialPrice(itemposition);
                            saleorder_entry.entrygrid.setAdapter(itemAd);
                            saleorder_entry.entrygrid.setSelection(itemposition);
                            saleorder_entry.getSummary();
                        }
                        else {
                            check = check > 0 ? check : 1;
                            sale_entry.sd.get(itemposition).setUnit_qty(check);
                            source.setText(String.valueOf(check));
                            GetSpecialPrice(itemposition);

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

                            sale_entry.entrygrid.setAdapter(itemAd);
                            sale_entry.entrygrid.setSelection(itemposition);
                            sale_entry.getSummary();
                        }
                    }
                    else if(isgallon)
                    {
                        check = check > 0 ? check : 1;
                        sale_entry.sd.get(itemposition).setGallon(check);
                        source.setText(String.valueOf(check));
                        Cursor cursor=DatabaseHelper.rawQuery("select smallest_unit_qty from usr_code where code="+sale_entry.sd.get(itemposition).getCode()+
                                " and unit_type="+sale_entry.sd.get(itemposition).getUnt_type()
                        );
                        if(cursor!=null&&cursor.getCount()!=0)
                        {
                            if(cursor.moveToFirst())
                            {
                                do {
                                    double sqty=cursor.getDouble(cursor.getColumnIndex("smallest_unit_qty"));
                                    double unit_qty=sale_entry.sd.get(itemposition).getGallon()/sqty;
                                    sale_entry.sd.get(itemposition).setUnit_qty(unit_qty);

                                }while (cursor.moveToNext());

                            }

                        }
                        cursor.close();


                        sale_entry.entrygrid.setAdapter(itemAd);
                        sale_entry.entrygrid.setSelection(itemposition);
                        sale_entry.getSummary();
                    }
                    else if(isSalePrice)
                    {
                        if(formname=="SaleOrder") //added by YLT
                        {
                            check = check > 0 ? check : 0;
                            saleorder_entry.sd.get(itemposition).setSale_price(check);
                            Double amt = check * saleorder_entry.sd.get(itemposition).getUnit_qty();
                            String numberAsString = String.format("%,." + frmmain.price_places + "f", amt);
                            txtamt.setText(numberAsString);
                            source.setText(String.format("%,." + frmmain.price_places + "f", check));
                            //sale_entry.getSummary();

                        }

                        else {
                            check = check > 0 ? check : 0;
                            if(!isAmount)
                            {
                                sale_entry.sd.get(itemposition).setSale_price(check);
                                Double amt = check * sale_entry.sd.get(itemposition).getUnit_qty();
                                String numberAsString = String.format("%,." + frmmain.price_places + "f", amt);
                                txtamt.setText(numberAsString);

                            }
                            else
                            {
                                double unit_qty=check/sale_entry.sd.get(itemposition).getSale_price();
                                sale_entry.sd.get(itemposition).setUnit_qty(unit_qty);
                                double sale_price=check/unit_qty;
                                sale_entry.sd.get(itemposition).setSale_price(sale_price);
                                txt.setText(String.format("%,." + frmmain.price_places + "f", sale_price));

                                Cursor cursor=DatabaseHelper.rawQuery("select smallest_unit_qty from usr_code where code="+sale_entry.sd.get(itemposition).getCode()+
                                        " and unit_type="+sale_entry.sd.get(itemposition).getUnt_type()
                                );
                                if(cursor!=null&&cursor.getCount()!=0)
                                {
                                    if(cursor.moveToFirst())
                                    {
                                        do {
                                            double sqty=cursor.getDouble(cursor.getColumnIndex("smallest_unit_qty"));
                                            double gallon=unit_qty*sqty;
                                            sale_entry.sd.get(itemposition).setGallon(gallon);

                                        }while (cursor.moveToNext());

                                    }

                                }
                                cursor.close();
                            }

                            source.setText(String.format("%,." + frmmain.price_places + "f", check));

                            //sale_entry.getSummary();
                        }
                        isAmount=false;
                    }

                    isqty = false;
                    startOpen = false;
                    pw.dismiss();

                } catch (Exception e) {
                    keynum = "0";
                    startOpen=true;
                    if (isqty)
                    {
                        keynum="1";
                    }
                    txtNum.setText(keynum);
                    AlertDialog.Builder bd = new AlertDialog.Builder(context,R.style.AlertDialogTheme);
                    bd.setTitle("iStock");
                    bd.setMessage("Number Format is incompatiable with data type");
                    bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            msg.dismiss();
                        }
                    });
                    msg = bd.create();
                    msg.show();
                    pw.dismiss();
                    isqty=false;
                }


            }
        });


    }
    public void GetSpecialPrice(int position) {
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

        if(formname=="SaleOrder")//added by YLT
        {
            double discount = saleorder_entry.sd.get(position).getSale_price() - saleorder_entry.sd.get(position).getDis_price();
            if (useSpecialPrice) {
                String sql = "select sale_price,price_level from S_SalePrice where code=" + saleorder_entry.sd.get(position).getCode() +
                        " and unit_type=" + saleorder_entry.sd.get(position).getUnt_type() + " and ( " +
                        saleorder_entry.sd.get(position).getUnit_qty() + " between min_qty and max_qty)";
                cursor = DatabaseHelper.rawQuery(sql);

                if (cursor != null && cursor.getCount() != 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            double sale_price = cursor.getDouble(cursor.getColumnIndex("Sale_Price"));
                            level = cursor.getInt(cursor.getColumnIndex("price_level"));
                            String price_level = level == 0 ? "SP" : level == 1 ? "SP1" : level == 2 ? "SP2" : "SP3";
                            saleorder_entry.sd.get(position).setSale_price(sale_price);
                            saleorder_entry.sd.get(position).setPriceLevel(price_level);
                            CalculateItemDiscount(position, discount);
                        } while (cursor.moveToNext());

                    }

                } else {
                    level = 0;
                    saleorder_entry.sd.get(position).setSale_price(getSalePrice("SP", position));
                    saleorder_entry.sd.get(position).setPriceLevel("SP");
                    CalculateItemDiscount(position, discount);
                }
                cursor.close();

            }
        }
        else {
            double discount = sale_entry.sd.get(position).getSale_price() - sale_entry.sd.get(position).getDis_price();
            if (useSpecialPrice) {
                String sql = "select sale_price,price_level from S_SalePrice where code=" + sale_entry.sd.get(position).getCode() +
                        " and unit_type=" + sale_entry.sd.get(position).getUnt_type() + " and ( " +
                        sale_entry.sd.get(position).getUnit_qty() + " between min_qty and max_qty)";
                cursor = DatabaseHelper.rawQuery(sql);

                if (cursor != null && cursor.getCount() != 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            double sale_price = cursor.getDouble(cursor.getColumnIndex("Sale_Price"));
                            level = cursor.getInt(cursor.getColumnIndex("price_level"));
                            String price_level = level == 0 ? "SP" : level == 1 ? "SP1" : level == 2 ? "SP2" : "SP3";
                            sale_entry.sd.get(position).setSale_price(sale_price);
                            sale_entry.sd.get(position).setPriceLevel(price_level);
                            CalculateItemDiscount(position, discount);
                        } while (cursor.moveToNext());

                    }

                } else {
                    level = 0;
                    sale_entry.sd.get(position).setSale_price(getSalePrice("SP", position));
                    sale_entry.sd.get(position).setPriceLevel("SP");
                    CalculateItemDiscount(position, discount);
                }
                cursor.close();

            }
        }


    }
    private double getSalePrice(String pricelevel,int itemposistion) {

        double sale_price=0;
        String level="";
        switch (pricelevel)
        {
            case "SP":
                level="uc.sale_price";
                break;
            case "SP1":
                level="uc.sale_price1";
                break;
            case "SP2":
                level="uc.sale_price2";
                break;
            case "SP3":
                level="uc.sale_price3";
                break;
        }
        if(formname=="SaleOrder") //added by YLT
        {
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
            return sale_price;
        }
        else {
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
            return sale_price;
        }

    }
    public   void CalculateItemDiscount(int itemPosition,double dis)
    {

        if(formname=="SaleOrder")//added by YLT
        {
            if (saleorder_entry.sd.get(itemPosition).getDis_type() == 3
                    || saleorder_entry.sd.get(itemPosition).getDis_type() == 4
                    || saleorder_entry.sd.get(itemPosition).getDis_type() == 6
                    || saleorder_entry.sd.get(itemPosition).getDis_type() == 7) {
                saleorder_entry.sd.get(itemPosition).setDis_percent(0);
                saleorder_entry.sd.get(itemPosition).setDis_price(sd.get(itemPosition).getSale_price());
            } else if (saleorder_entry.sd.get(itemPosition).getDis_type() == 1
                    || saleorder_entry.sd.get(itemPosition).getDis_type() == 2) {
                double dispercent = saleorder_entry.sd.get(itemPosition).getDis_type() == 1 ? 0.05 : 0.1;
                double discount = saleorder_entry.sd.get(itemPosition).getDis_type() == 1 ? 5 : 10;
                saleorder_entry.sd.get(itemPosition).setDis_percent(discount);
                double dis_price = saleorder_entry.sd.get(itemPosition).getSale_price() - (sd.get(itemPosition).getSale_price() * (dispercent));
                saleorder_entry.sd.get(itemPosition).setDis_price(dis_price);
            } else if (saleorder_entry.sd.get(itemPosition).getDis_type() == 5) {
                if (saleorder_entry.sd.get(itemPosition).getDis_percent() > 0) {
                    double dis_percent = saleorder_entry.sd.get(itemPosition).getDis_percent();
                    saleorder_entry.sd.get(itemPosition).setDis_percent(dis_percent);
                    double dis_price = saleorder_entry.sd.get(itemPosition).getSale_price() - (saleorder_entry.sd.get(itemPosition).getSale_price() * (dis_percent / 100));
                    sd.get(itemPosition).setDis_price(dis_price);


                } else {
                    double dis_percent = saleorder_entry.sd.get(itemPosition).getDis_percent();
                    saleorder_entry.sd.get(itemPosition).setDis_percent(dis_percent);
                    double dis_price = saleorder_entry.sd.get(itemPosition).getSale_price() - dis;
                    saleorder_entry.sd.get(itemPosition).setDis_price(dis_price);
                }
            }
        }
        else {
            if (sale_entry.sd.get(itemPosition).getDis_type() == 3
                    || sale_entry.sd.get(itemPosition).getDis_type() == 4
                    || sale_entry.sd.get(itemPosition).getDis_type() == 6
                    || sale_entry.sd.get(itemPosition).getDis_type() == 7) {
                sale_entry.sd.get(itemPosition).setDis_percent(0);
                sale_entry.sd.get(itemPosition).setDis_price(sd.get(itemPosition).getSale_price());
            } else if (sale_entry.sd.get(itemPosition).getDis_type() == 1
                    || sale_entry.sd.get(itemPosition).getDis_type() == 2) {
                double dispercent = sale_entry.sd.get(itemPosition).getDis_type() == 1 ? 0.05 : 0.1;
                double discount = sale_entry.sd.get(itemPosition).getDis_type() == 1 ? 5 : 10;
                sale_entry.sd.get(itemPosition).setDis_percent(discount);
                double dis_price = sale_entry.sd.get(itemPosition).getSale_price() - (sd.get(itemPosition).getSale_price() * (dispercent));
                sale_entry.sd.get(itemPosition).setDis_price(dis_price);
            } else if (sale_entry.sd.get(itemPosition).getDis_type() == 5) {
                if (sale_entry.sd.get(itemPosition).getDis_percent() > 0) {
                    double dis_percent = sale_entry.sd.get(itemPosition).getDis_percent();
                    sale_entry.sd.get(itemPosition).setDis_percent(dis_percent);
                    double dis_price = sale_entry.sd.get(itemPosition).getSale_price() - (sale_entry.sd.get(itemPosition).getSale_price() * (dis_percent / 100));
                    sd.get(itemPosition).setDis_price(dis_price);


                } else {
                    double dis_percent = sale_entry.sd.get(itemPosition).getDis_percent();
                    sale_entry.sd.get(itemPosition).setDis_percent(dis_percent);
                    double dis_price = sale_entry.sd.get(itemPosition).getSale_price() - dis;
                    sale_entry.sd.get(itemPosition).setDis_price(dis_price);
                }
            }
        }

    }
    public  void getItemAdpater(itemAdapter itemAdapter) {
        itemAd=itemAdapter;
    }
    private  String ClearFormat(String s)
    {
        return s.replace(",","");
    }


}

