package com.abbp.istockmobilesalenew;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import static com.abbp.istockmobilesalenew.sale_entry.use_salesperson;

public class reportviewer extends AppCompatActivity {
    public static HashMap<String, String> header=new HashMap<>();
    public static String title="";
    TextView txtDate,txtCustomer,txtPayType,txtTotalAmt,txtitemDisAmt,txtFocAmt,txtvoudisamt,
            txtPreviousAmt,txttaxamt,txtPaidAmt,txtNetAmt,txtTitle;
    RecyclerView rvItem;
    String Customer,PayType="";
    RelativeLayout rlPrevious;
    ImageButton imgBack,imgPrint;
    SharedPreferences sh_ip;
    SharedPreferences sh_port;
    public static ArrayList<String> Printers=new ArrayList<>();
    public  static  ArrayList<Printer_Type> ptype=new ArrayList<>();
    private AlertDialog showmsg;
    private AlertDialog dialog;
    private ListView lv;
    private int printer_type_id;
    private String invoice_no;
    private String headRemark;
    private String detRemark;
    private double custDis;
    private String UpdateString;
    private String sqlstring;
    ProgressDialog pb ;
    private String printer_name;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rpt_salevoucher);
        sh_ip=getSharedPreferences("ip",MODE_PRIVATE);
        sh_port=getSharedPreferences("port",MODE_PRIVATE);
        setUI();
        BindingHeader();
        BindingDetail();
        pb=new ProgressDialog(reportviewer.this);
    }

    private void setUI() {
        rlPrevious=findViewById(R.id.rlPrevious);
        txtTitle=findViewById(R.id.txtheader);
        rvItem=findViewById(R.id.rvitem);
        txtDate=findViewById(R.id.txtdate);
        txtCustomer=findViewById(R.id.txtname);
        txtPayType=findViewById(R.id.txtpaytype);
        txtTotalAmt=findViewById(R.id.txtTotalAmt);
        txtitemDisAmt=findViewById(R.id.txtitemDisAmt);
        txtFocAmt=findViewById(R.id.txtFocAmt);
        txtvoudisamt=findViewById(R.id.txtvoudisamt);
        txtPreviousAmt=findViewById(R.id.txtPreviousAmt);
        txttaxamt=findViewById(R.id.txttaxamt);
        txtPaidAmt=findViewById(R.id.txtPaidAmt);
        txtNetAmt=findViewById(R.id.txtNetAmt);
        txtTitle.setText(title+" ( "+header.get("docid")+" )");
        imgBack=findViewById(R.id.back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(title.equals("Invoice"))
        {
            rlPrevious.setVisibility(View.GONE);
        }
        imgPrint=findViewById(R.id.imgPrint);
        imgPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              try {
                 Binding_PrinterSetting();
                 ShowPrinter();

              }
              catch (Exception ee)
              {

              }
            }
        });

    }

    private void ShowPrinter() {

        AlertDialog.Builder bd =new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.devicesetting,null);
        bd.setView(view);
        Button btnPrinter=view.findViewById(R.id.btnPrinter);
        Button btnPrinterType=view.findViewById(R.id.btnType);
        ImageButton ImgSave=view.findViewById(R.id.imgSave);
        ImgSave.setImageResource(R.drawable.printertablet);
        btnPrinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(reportviewer.this);
                View view=getLayoutInflater().inflate(R.layout.showposuser,null);
                builder.setView(view);
                lv= (ListView) view.findViewById(R.id.lsvposuer);
                Printer_Type_Adpater ad=new Printer_Type_Adpater(Printers,reportviewer.this);
                lv.setAdapter(ad);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        btnPrinter.setText(Printers.get(position));
                        printer_name=Printers.get(position);
                        if(btnPrinter.getText().toString().trim().equals(""))
                        {
                            btnPrinter.setText("Choose");

                        }
                        showmsg.dismiss();

                    }
                });
                showmsg=builder.create();
                showmsg.show();
            }
        });
        btnPrinterType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(reportviewer.this);
                View view=getLayoutInflater().inflate(R.layout.showposuser,null);
                builder.setView(view);
                lv= (ListView) view.findViewById(R.id.lsvposuer);
                Printer_Type_Adpater ad=new Printer_Type_Adpater(reportviewer.this,ptype);
                lv.setAdapter(ad);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        btnPrinterType.setText(ptype.get(position).getName());
                        if(btnPrinterType.getText().toString().trim().equals(""))
                        {
                            btnPrinterType.setText("Choose");
                        }

                        printer_type_id =ptype.get(position).getPrinter_type_id();
                        showmsg.dismiss();
                    }
                });
                showmsg=builder.create();
                showmsg.show();
            }
        });
        ImgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                updateVoucher();
                String ip = sh_ip.getString("ip", "empty");
                String port = sh_port.getString("port", "empty");
                String sqlUrl="http://" + ip + ":" + port + "/api/DataSync/SaveData?update=true";
                sqlstring=frmlogin.LoginUserid+"&"+sale_entry.sh.get(0).getTranid()+"&"+
                        sale_entry.sh.get(0).getLocationid()+"&"+UpdateString;
                new SaveData().execute(sqlUrl);
                
            }
        });
        dialog=bd.create();
        dialog.show();
    }

    private void BindingDetail() {
     reportAdpater ra=new reportAdpater(reportviewer.this);
     rvItem.setAdapter(ra);
        LinearLayoutManager lm=new LinearLayoutManager(reportviewer.this, LinearLayoutManager.VERTICAL,false);
        rvItem.setLayoutManager(lm);
    }

    private void BindingHeader() {
       Customer=PayType="";
        Cursor cursor=DatabaseHelper.rawQuery("select customer_name from Customer where customerid="+header.get("Customer"));
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {

                    Customer=cursor.getString(cursor.getColumnIndex("customer_name"));

                } while (cursor.moveToNext());

            }

        }
        cursor.close();
        cursor=DatabaseHelper.rawQuery("select name from Payment_Type where pay_type="+header.get("Pay_Type"));
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {

                    PayType=cursor.getString(cursor.getColumnIndex("name"));

                } while (cursor.moveToNext());

            }

        }
        txtDate.setText(header.get("date"));
        txtCustomer.setText(Customer);
        txtPayType.setText(PayType);
        txtTotalAmt.setText(header.get("TotalAmt"));
        txtitemDisAmt.setText(String.format("%,."+frmmain.price_places+"f",Double.parseDouble(header.get("itemDis"))));
//        txtitemDisAmt.setText(header.get("itemDis"));
        txtFocAmt.setText(String.format("%,."+frmmain.price_places+"f",Double.parseDouble(header.get("Foc"))));
//        txtFocAmt.setText(header.get("Foc"));
        txtvoudisamt.setText(header.get("VouDis"));
        txtPreviousAmt.setText(header.get("previous"));
        txttaxamt.setText(header.get("tax"));
        txtPaidAmt.setText(header.get("paid"));
        txtNetAmt.setText(header.get("net"));
    }
    private void Binding_PrinterSetting() {
        if(Printers.size()>0) Printers.clear();
        if(ptype.size()>0)ptype.clear();
        String ip = sh_ip.getString("ip", "empty");
        String port = sh_port.getString("port", "empty");
        String url = "http://" + ip + ":" + port + "/api/DataSync/GetPrinter?&printer=true";
        RequestQueue request = Volley.newRequestQueue(reportviewer.this);
        Response.Listener listener=new Response.Listener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Object response) {
                String[] str=response.toString().split("&&");
                String[] prt=str[0].split(",");
                String[]pty=str[1].split(",");
                for(int i=0;i<prt.length;i++)
                {
                    Printers.add(prt[i]);
                }
                for(int i=0;i<pty.length;i++)
                {
                    ptype.add(new Printer_Type((i+1), pty[i]));

                }
            }
        };
        final Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder bd=new AlertDialog.Builder(reportviewer.this);
                bd.setMessage("Process is Fail!Check your Network Connection");
                bd.setTitle("iStock");

                bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog=bd.create();
                dialog.show();
            }
        };

        StringRequest req = new StringRequest(Request.Method.GET, url, listener, error);
        request.add(req);
    }
    private void updateVoucher() {

        try {
            if (sale_entry.sh.get(0).getTranid() < 0) {

                if(sale_entry.sh.get(0).getInvoice_no().equals("NULL") || sale_entry.sh.get(0).getInvoice_no().equals(""))
                {
                    invoice_no="NULL";
                }
                else
                {
                    invoice_no="N'"+sale_entry.sh.get(0).getInvoice_no()+"'";

                }

                if(sale_entry.sh.get(0).getHeadremark().equals("NULL") || sale_entry.sh.get(0).getHeadremark().equals(""))
                {
                    headRemark="NULL";
                }
                else
                {
                    headRemark="N'"+sale_entry.sh.get(0).getHeadremark()+"'";

                }

                String head = "update Sale_Head_Main set\n" +
                        "tranid="+sale_entry.sh.get(0).getTranid()+",\n" +
                        "docid='"+sale_entry.sh.get(0).getDocid()+"',\n" +
                        "date=" +"'" + String.format(sale_entry.sh.get(0).getDate(), "yyyy-MM-dd") + "'," +
                        "invoice_no="+invoice_no+",\n" +
                        "locationid="+sale_entry.sh.get(0).getLocationid()+",\n" +
                        "customerid="+sale_entry.sh.get(0).getCustomerid()+",\n" +
                        "cash_id="+sale_entry.sh.get(0).getDef_cashid()+",\n" +
                        "townshipid="+sale_entry.sh.get(0).getTownshipid()+",\n"+
                        "pay_type="+sale_entry.sh.get(0).getPay_type()+",\n" +
                        "due_indays="+sale_entry.sh.get(0).getDue_in_days()+",\n" +
                        "currency=1,\n" +
                        "discount="+sale_entry.sh.get(0).getDiscount()+",\n" +
                        "paid_amount="+sale_entry.sh.get(0).getPaid_amount()+",\n" +
                        "invoice_amount="+sale_entry.sh.get(0).getInvoice_amount()+",\n" +
                        "invoice_qty="+sale_entry.sh.get(0).getInvoice_qty()+",\n" +
                        "foc_amount="+sale_entry.sh.get(0).getFoc_amount()+",\n" +
                        "itemdis_amount="+sale_entry.sh.get(0).getIstemdis_amount()+",\n" +
                        "net_amount="+ClearFormat(header.get("net").toString())+",\n" +
                        "Remark="+headRemark+",\n"+
                        "tax_amount="+sale_entry.sh.get(0).getTax_amount()+",\n" +
                        "tax_percent="+sale_entry.sh.get(0).getTax_per()+",\n" +
                        "discount_per="+sale_entry.sh.get(0).getDiscount_per()+",\n"+
                        "exg_rate="+1+"\n"+
                        " where tranid="+sale_entry.sd.get(0).getTranid();

                String det = "delete from sale_det where tranid="+sale_entry.sh.get(0).getTranid()+
                        " insert into Sale_Det(tranid,date,unit_qty,qty,sale_price,dis_price,dis_type,dis_percent,remark,unit_type,code,sr,srno,PriceLevel,SQTY,SPrice) values ";


                for(int i=0;i<sale_entry.sd.size();i++)
                {


                    if(sale_entry.sd.get(i).getDetremark().equals("NULL") || sale_entry.sd.get(i).getDetremark().equals("")){
                        detRemark="NULL";
                    }else {
                        detRemark="N'"+sale_entry.sd.get(i).getDetremark()+"'";
                    }


                    if (i < (sale_entry.sd.size() - 1))
                    {
                        det = det + "(" +
                                sale_entry.sd.get(i).getTranid() + "," +
                                "'" + String.format(sale_entry.sh.get(0).getDate(), "yyyy-MM-dd") + "'," +
                                sale_entry.sd.get(i).getUnit_qty() + "," +
                                sale_entry.sd.get(i).getQty() + "," +
                                sale_entry. sd.get(i).getSale_price() + "," +
                                sale_entry.sd.get(i).getDis_price() + "," +
                                sale_entry. sd.get(i).getDis_type()+","+
                                sale_entry. sd.get(i).getDis_percent()+ "," +
                                detRemark+","+
                                sale_entry.sd.get(i).getUnt_type() + "," +
                                sale_entry.sd.get(i).getCode() + "," +
                                (i + 1) + "," +
                                (i + 1) + ",'"+
                                sale_entry.sd.get(i).getPriceLevel()+"',"+
                                getSmallestQty(sale_entry.sd.get(i).getCode(),sale_entry.sd.get(i).getUnit_qty(),sale_entry.sd.get(i).getUnt_type())+","+
                                getSPrice(sale_entry.sd.get(i).getCode()) +" ),";


                    }

                    else {
                        det = det + "(" +
                                sale_entry.sd.get(i).getTranid() + "," +
                                "'" + String.format(sale_entry.sd.get(0).getDate(), "yyyy-MM-dd") + "'," +
                                sale_entry.sd.get(i).getUnit_qty() + "," +
                                sale_entry.sd.get(i).getQty() + "," +
                                sale_entry.sd.get(i).getSale_price() + "," +
                                sale_entry. sd.get(i).getDis_price() + "," +
                                sale_entry.sd.get(i).getDis_type()+","+
                                sale_entry.sd.get(i).getDis_percent()+ "," +
                                detRemark+","+
                                sale_entry. sd.get(i).getUnt_type() + "," +
                                sale_entry.sd.get(i).getCode() + "," +
                                (i + 1)+ "," +
                                (i + 1) + ",'"+
                                sale_entry.sd.get(i).getPriceLevel()+"',"+
                                getSmallestQty(sale_entry.sd.get(i).getCode(),sale_entry.sd.get(i).getUnit_qty(),sale_entry.sd.get(i).getUnt_type())+","+
                                getSPrice(sale_entry.sd.get(i).getCode()) +" )";
                    }

                }
                sale_entry.sh.get(0).getDiscount_per();
                sale_entry.sh.get(0).setDiscount_per(0.0);
                custDis=sale_entry.sh.get(0).getDiscount_per();
                 UpdateString = head +" "+det;
                if(use_salesperson && sale_entry.SaleVouSalesmen.size()>0)
                {
                    String salePerson=" delete from SalesVoucher_Salesmen_Tmp where Sales_TranID="+sale_entry.sh.get(0).getTranid()+" and userid="+frmlogin.LoginUserid+
                            " insert into SalesVoucher_Salesmen_Tmp(Sales_TranID,Salesmen_ID,rmt_copy,userid)"+
                            "values ";
                    for(int i=0;i<sale_entry.SaleVouSalesmen.size();i++)
                    {
                        salePerson=salePerson+"("+
                                sale_entry.sh.get(0).getTranid()+","+
                                sale_entry.SaleVouSalesmen.get(i).getSalesmen_Id()+","+
                                "1,"+frmlogin.LoginUserid+"),";
                    }
                    salePerson=salePerson.substring(0,salePerson.length()-1);
                    UpdateString=UpdateString+" "+salePerson;
                }

            }
        }
        catch (Exception ee)
        {

        }

    }

    private double getSmallestQty(long code, double unit_qty, int unt_type) {
        double sqty=0;
        Cursor cursor=DatabaseHelper.rawQuery("select smallest_unit_qty from usr_code where code="+code+" and unit_type="+unt_type);
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
        return  sqty*unit_qty;
    }

    private double getSPrice(long code) {
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
    private  String ClearFormat(String s) //added by YLT on 18-08-2020
    {
        return s.replace(",","");
    }
    private void PrintVoucher() {
            pb.show();
            String sqlUrl = "";
            String ip = sh_ip.getString("ip", "empty");
            String port = sh_port.getString("port", "empty");
            String printername=printer_name;
            String printertypeid=String.valueOf(printer_type_id);
           // sqlstring = "userid=" + frmlogin.LoginUserid + "&tranid=" + sale_entry.sh.get(0).getTranid() + "&net_amount=" + header.get("net").toString()+"&billcount=1"+"&printername="+printername+"&printertypeid="+printertypeid+"&report="+title;
        sqlstring = "userid=" + frmlogin.LoginUserid + "&tranid=" + sale_entry.sh.get(0).getTranid() + "&net_amount=" + ClearFormat( header.get("net").toString())+"&billcount=1"+"&printername="+printername+"&printertypeid="+printertypeid+"&report="+title;
            try {
                sqlstring= URLEncoder.encode(sqlstring,"UTF-8").replace("+","%20")
                        .replace("%26","&").replace("%3D","=")
                        .replace("%2C",",");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            sqlUrl = "http://" + ip + ":" + port + "/api/DataSync/GetData?" + sqlstring;
            requestQueue = Volley.newRequestQueue(this);
            final Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        AlertDialog.Builder b = new AlertDialog.Builder(reportviewer.this);
                        b.setTitle("iStock");
                        b.setMessage(response);
                        b.setCancelable(false);
                        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                pb.dismiss();
                                dialog.dismiss();
                            }
                        });
                        dialog = b.create();
                        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface alert) {
                                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);

                            }
                        });
                        dialog.show();

                    } catch (Exception e) {
                        pb.dismiss();

                    }
                }

            };

            final Response.ErrorListener error = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    pb.dismiss();
                    AlertDialog.Builder b = new AlertDialog.Builder(reportviewer.this);
                    b.setTitle("iStock");
                    b.setMessage("Printer is not found!");
                    b.setCancelable(false);
                    b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
                    dialog=b.create();
                    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface alert) {
                            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);

                        }
                    });
                    dialog.show();

                }
            };
            StringRequest req = new StringRequest(Request.Method.GET, sqlUrl, listener, error);
            DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            req.setRetryPolicy(retryPolicy);
            requestQueue.add(req);
    }
    public class SaveData extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            HttpURLConnection connection;
            StringBuffer response = new StringBuffer();
            try {
                URL url=new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.addRequestProperty("Content-Type","text/plain");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                if(sqlstring!=null)
                {
                    //connection.setRequestProperty("Content-Length", Integer.toString(sqlstring.length()));
                    connection.getOutputStream().write(sqlstring.getBytes("UTF8"));
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    String inputLine;


                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return  response.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                if (!s.equals("error"))
                {
                   sale_entry.sh.get(0).setDocid(s);
                   txtTitle.setText(title+" ( "+s+" )");
                   sale_entry.txtdocid.setText(s);
                   PrintVoucher();

                }
            }
            catch (Exception ee)
            {


            }
        }
    }
}
