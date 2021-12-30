package com.abbp.istockmobilesalenew;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import me.myatminsoe.mdetect.Rabbit;

public class saleorder_entry extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {
    public static ArrayList<category> categories = new ArrayList<>();
    public static ArrayList<class_item>class_items=new ArrayList<>();
    public static RecyclerView gridview,gridclassview,gridcodeview;
    public static ArrayList<unitforcode> unitforcodes = new ArrayList<>();
    RecyclerView rcvUnit;
    public static RecyclerView rcvSP;
    AlertDialog CustomerSetup=null;
    AlertDialog setupFilter=null;
    ArrayList<usr_code> filteredCode =new ArrayList<>();
    ArrayList<category> filteredList = new ArrayList<>();
    ArrayList<usr_code> filtereddesc= new ArrayList<>();
    ArrayList <usr_code> filteredbrand= new ArrayList<>();
    ArrayList<class_item>filteredclass=new ArrayList<>();
    public static Button imgCustomerTownship;
    public static Button imgCustomerGroup;
    public static boolean use_Category2;
    JSONArray data;
    JSONArray cust;
    AlertDialog addDialog = null;
    AlertDialog comfirmMsg=null;
    RecyclerView rrvv,rrvvc;
    CategoryAdapter ad;
    int isCredit=0;
    ClassAdapter cad;
    public static long tranid;
    public static ItemAdapter itemAdapter;
    public static String  fitercode;
    SharedPreferences sh_ip;
    SharedPreferences sh_port;
    DateFormat dateFormat;
    String url;
    static ListView entrygrid;
    public static boolean openEditText=false;
    public static boolean isCodeFinding=false;
    public static boolean isCategory=false;
    String invoice_no="";
    RequestQueue requestQueue;
    public static ArrayList<Sale_head_main> sh = new ArrayList<>();
    public static ArrayList<sale_det> sd = new ArrayList<>();
    public static ArrayList<usr_code> usr_codes = new ArrayList<>();
    public static ArrayList<PriceLevel> priceLevels=new ArrayList<>();
    boolean isqty=false;
    public static int itemPosition;
    ImageButton imgDeleteAll, imgDelete, imgSummary, imgEdit, imgConfrim, imgPrint,imgHeader,imgBack,imgLogout;
    ImageButton imgAllDetails;//added by YLT on [15-06-2020]
    AlertDialog dialog,custdia;
    AlertDialog.Builder builder;
    ArrayList<String> keys = new ArrayList<>();
    String sqlstring  ="";
    String keynum="";
    AlertDialog msg;
    public static priceLevelAdapter pad;
    public static UnitAdapter uad;
    Date voudate;
    AlertDialog disDa = null;
    public static TextView txtChangeQty,txtChangePrice,txtamt,txtdate,txtsqty,txtsmqty,txtsprice;
    EditText txtinvoiceNo;
    public  static TextView txtitemDisAmt,txtper,txttotal,txtfoc,txtitemdiscount,txtvouper,txtvoudis,txtpaid,txtnet,txttaxamt,txttaxamT,txtshowUnit,txtoutstand,txtShowSP,txtdocid;;
    ProgressDialog pb;
    com.applandeo.materialcalendarview.CalendarView calendar;
    public static long selected_custgroupid=-1;
    public  static long selected_townshipid=-1;
    public static boolean creditcustomer=false;
    public static double dis_percent;
    public static double disamt;
    public static boolean dis_typepercent=false;
    public  static boolean changeheader=false;
    RelativeLayout rlUnit,rlLevel;
    TextView txtEdit,txtDel,txtDelAll,txtComfirm,txtBack;
    public static TextView txttax;
    RelativeLayout viewEdit,viewDel,viewDelAll,viewConfirm,viewBack;
    public static int Use_Tax=0;
    static double taxpercent=0.0;
    AlertDialog da = null;
    AlertDialog salechange=null;
    Long ConfirmedTranid;
    Boolean voudis=false;
    Boolean paiddis=false;
    boolean startOpen=true;
    TextView tvAmount;
    public  static double net_amount=0.0;
    public  static double tax_amount=0.0;
    public  static String voudDiscountString="";
    public static boolean isCreditcustomer=false;
    public static double totalAmt_tmp = 0.0;
    public static double qty_tmp = 0.0;
    public static double vouDis_tmp =0.0;
    public static  double itemDis_tmp = 0.0;
    public static double foc_tmp = 0.0;
    public static double paidAmt_tmp = 0.0;
    public static double taxper_tmp = .0;
    public static double taxamt_tmp = 0.0;
    public static double calresult_tmp = 0.0;
    public static double netamt_tmp=0.0;
    public static double ItemdisTax_tmp = 0;
    public static int Tax_Type=0;
    public static int caltax=100;
    public static boolean logout=false;
    public static boolean use_location,use_customergroup,use_township,use_salesperson,Use_Delivery;
    public static boolean bill_not_print;
    public static int billprintcount=0;
    String detRemark="";
    String headRemark="";
    // String itemdis="Normal";
    TextView headremark,detremark;
    Intent intent;
    boolean comfirm=false;
    DatePickerDialog pickerDialog;
    public static CustGroupAdapter cgd;
    public static TownshipAdapter td;
    public static CustomerAdapter cd;
    public static PaymentTypeAdapter pd;
    public static LocationAdapter ld;
    public static SalesmenAdpater sm;
    TextView txtProgress,txtTable;
    public static Button btnpaytype,btncustgroup,btncustomer,btntownship,btnSalesmen;
    public static Button btnStlocation;
    Button btndiscount,btndetail;
    public static ImageButton imgSearchCode,imgFilterCode,imgFilterClear,imgPrinter;
    ImageButton imgScanner,btncustadd;
    public static Context datacontext;
    EditText etdSearchCode;
    public static  double paid,changeamount;
    public static  boolean fromSaleChange,frombillcount=false;
    public TextView tvChange;
    static Double voudisper=0.0;
    public static RelativeLayout rlchangePrice;
    public static RelativeLayout taxlo;
    public static ArrayList<Salesmen>SaleVouSalesmen=new ArrayList<>();
    public static double custDis=0.0;
    public static int due_in_days=0;
    public static  double credit_limit;
    int defloc=1;
    int defunit=-1;
    int def_cashid;
    CheckBox chkDeliver;//YLT


    SharedPreferences sh_printer,sh_ptype;
    String ToDeliver="";
    private AlertDialog downloadAlert;
    private Context context;
    private ProgressBar pbDownload;
    private int custcount;
    public  static int  editUnit_type=0;
    private boolean allcustomer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saleorder_entry);
        defloc=frmlogin.det_locationid;
        defunit=frmmain.defunit;
        def_cashid=frmlogin.def_cashid;
        datacontext=saleorder_entry.this;
        rlchangePrice=(RelativeLayout)findViewById(R.id.rlchangePrice);
        comfirm=false;
        logout=false;
        sh_printer=getSharedPreferences("printer",MODE_PRIVATE);
        sh_ptype=getSharedPreferences("ptype",MODE_PRIVATE);
        sh_ip = getSharedPreferences("ip", MODE_PRIVATE);
        sh_port = getSharedPreferences("port", MODE_PRIVATE);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        entrygrid = findViewById(R.id.entrygrid);
        pb=new ProgressDialog(saleorder_entry.this,R.style.AlertDialogTheme);
        pb.setMessage("Please a few seconds");
        pb.setTitle("iStock");
        itemPosition = -1;
        entrygrid.setOnItemLongClickListener(this);
        entrygrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemPosition=position;
            }
        });
        isCodeFinding=false;
        SetUI();
        if(frmmain.withoutclass.equals("true")){
            gridview.setVisibility(View.VISIBLE);
            BindingCategory();
        }else {
            gridclassview.setVisibility(View.VISIBLE);
            gridcodeview.setVisibility(View.VISIBLE);
            BindingClass();
        }
        if(sd.size()>0)sd.clear();
        if(sh.size()>0)sh.clear();
        getHeader();
        getData();
        getSystemSetting();
        //if(!Use_Delivery)
       // {
      //      chkDeliver.setVisibility(View.GONE);
      //  }
       // else
       // {
       //     chkDeliver.setVisibility(View.VISIBLE);
      //  }
        Tax_Type=getTaxType();
        caltax=caltaxsetting();
        fitercode="Code";
        isCategory=true;
        imgFilterCode.setVisibility(View.GONE);
        GetBillPrintCount();
    }
    private void GetBillPrintCount() {
        GetAppSetting getAppSetting=new GetAppSetting("Bill_Print_Count");
        billprintcount=Integer.parseInt(getAppSetting.getSetting_Value());
    }
    private void getSystemSetting() {
        Cursor cursor=DatabaseHelper.rawQuery("select use_location,use_customerGroup,use_township,use_salesperson,Use_Delivery,use_Category2 from SystemSetting");
        if(cursor!=null&&cursor.getCount()!=0)
        {
            if(cursor.moveToFirst())
            {
                do {
                    use_location=cursor.getInt(cursor.getColumnIndex("use_location"))==1?true:false;
                    use_customergroup=cursor.getInt(cursor.getColumnIndex("use_customerGroup"))==1?true:false;
                    use_township=cursor.getInt(cursor.getColumnIndex("use_township"))==1?true:false;
                    use_salesperson=cursor.getInt(cursor.getColumnIndex("use_salesperson"))==1?true:false;
                    Use_Delivery=cursor.getInt(cursor.getColumnIndex("Use_Delivery"))==1?true:false;
                    use_Category2=cursor.getInt(cursor.getColumnIndex("use_Category2"))==1?true:false; //added by YLT on 03-09-2020
                }while (cursor.moveToNext());


            }

        }
        if(Use_Tax==0){
            taxlo.setVisibility(View.GONE);
        }
        else {
            taxlo.setVisibility(View.VISIBLE);
            if(frmlogin.tax==0){
                txttaxamt.setEnabled(false);
            }else {
                txttaxamt.setEnabled(true);
            }

        }

    }

    private  void GetCustomerOutstand(long customerid)
    {
        try {

            String ip = sh_ip.getString("ip", "empty");
            String port = sh_port.getString("port", "empty");
            String data="userid="+frmlogin.LoginUserid+"&tranid="+sh.get(0).getTranid()+"&customerid="+customerid+"&date="+sh.get(0).getDate();
            url = "http://"+ip+":"+port+"/api/DataSync/GetData?"+data;
            requestQueue = Volley.newRequestQueue(this);
            final Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    txtoutstand.setText(response);

                }

            };

            final Response.ErrorListener error = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                }
            };
            StringRequest req = new StringRequest(Request.Method.GET, url, listener, error);
            requestQueue.add(req);


        } catch (Exception ee) {

        }

    }

    private void SetUI() {

        SaleVouSalesmen.clear();
        TextView tvUnit=findViewById(R.id.unit);
        chkDeliver=findViewById(R.id.chkToDeliver);
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
        tvUnit.setVisibility(use_unit==true?View.VISIBLE:View.GONE);
        imgFilterClear=findViewById(R.id.imgClearFilter);
        imgFilterClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCategory=true;
                switch (imgFilterCode.getVisibility())
                {
                    case View.GONE:
                        if(frmmain.withoutclass.equals("true")){
                            BindingCategory();
                            break;
                        }else if(frmmain.withoutclass.equals("false")){
                            filteredCode.clear();
                            filtereddesc.clear();
                            filteredList.clear();
                            usr_codes.clear();
                            gridcodeview.clearFocus();
                            BindingClass();
                            break;
                        }

                    case View.VISIBLE:
                        UsrcodeAdapter usrcodead = new UsrcodeAdapter(saleorder_entry.this,usr_codes,gridview,categories,"SaleOrder");//added by YLT
                        gridview.setAdapter(usrcodead);
                        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getApplicationContext(), 4);
                        gridview.setLayoutManager(gridLayoutManager1);
                        break;
                }
            }
        });
        imgFilterCode=findViewById(R.id.imgFilterCode);
        imgSearchCode=findViewById(R.id.imgSearchCode);
        imgSearchCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    AlertDialog.Builder searchBuilder=new AlertDialog.Builder(saleorder_entry.this,R.style.AlertDialogTheme);
                    View view=getLayoutInflater().inflate(R.layout.searchbox,null);
                    searchBuilder.setView(view);
                    EditText etdSearch=view.findViewById(R.id.etdSearch);
                    ImageButton btnSearch=view.findViewById(R.id.imgOK);
                    ImageButton btnFilterCode=view.findViewById(R.id.imgFilterCode);
                    btnFilterCode.setVisibility(View.VISIBLE);
                    btnFilterCode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PopupMenu popup = new PopupMenu(saleorder_entry.this,btnFilterCode);
                            //Inflating the Popup using xml file
                            popup.getMenuInflater().inflate(R.menu.filtermenu, popup.getMenu());
                            Menu pp=popup.getMenu();
                            if(frmmain.withoutclass.equals("true")){
                                pp.findItem(R.id.cclass).setVisible(false);
                            }else {
                                pp.findItem(R.id.cclass).setVisible(true);
                            }


                            if(!use_Category2) { //for show hide brand //added by YLT on 03-09-2020
                                MenuItem menuitem = popup.getMenu().getItem(4);
                                menuitem.setVisible(false);
                            }

                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    switch (item.getItemId())
                                    {
                                        case R.id.code:
                                            fitercode="Code";
                                            break;
                                        case R.id.description:
                                            fitercode="Description";
                                            break;
                                        case R.id.category:
                                            fitercode="Category";
                                            break;
                                        case R.id.cclass:
                                            fitercode="Class";
                                            break;
                                        case R.id.brand:
                                            fitercode= "Brand";
                                            break;
                                        case R.id.shortdes:
                                            fitercode="Short";
                                            break;
                                    }
                                    return true;
                                }
                            });
                            popup.show();//showing popup menu
                        }
                    });
                    btnSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!etdSearch.getText().toString().isEmpty())
                            {
                                SearchItem(etdSearch.getText().toString());
                                msg.dismiss();
                            }
                        }
                    });

                    msg=searchBuilder.create();
                    msg.show();
                }
                catch (Exception ee)
                {

                }

            }
        });
        imgFilterCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(saleorder_entry.this,R.style.AlertDialogTheme);
                View view = getLayoutInflater().inflate(R.layout.showposuser, null);
                // builder.setCancelable(false);
                builder.setView(view);
                ListView lv = (ListView) view.findViewById(R.id.lsvposuer);
                ArrayList<String> s = new ArrayList<>();
                s.add("Code");
                s.add("Description");
                ArrayAdapter<String> item=new ArrayAdapter<>(saleorder_entry.this,android.R.layout.simple_list_item_1,s);
                lv.setAdapter(item);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        fitercode=s.get(position);
                        dialog.dismiss();
                    }
                });
                dialog=builder.create();
                dialog.show();
            }
        });


        txtDel=findViewById(R.id.txtDelete);
        txtDelAll=findViewById(R.id.txtDelAll);
        txtEdit=findViewById(R.id.txtedit);
        txtComfirm=findViewById(R.id.txtConfirm);
        txtBack=findViewById(R.id.txtBack);
        viewConfirm=findViewById(R.id.viewConfirm);
        viewEdit=findViewById(R.id.viewEdit);
        viewDel=findViewById(R.id.viewDelete);
        viewDelAll=findViewById(R.id.viewDelAll);
        viewBack=findViewById(R.id.viewBack);
        txtDel.setOnClickListener(this);
        txtDelAll.setOnClickListener(this);
        txtEdit.setOnClickListener(this);
        txtComfirm.setOnClickListener(this);
        txtBack.setOnClickListener(this);
        viewBack.setOnClickListener(this);
        viewDelAll.setOnClickListener(this);
        viewDel.setOnClickListener(this);
        viewEdit.setOnClickListener(this);
        viewConfirm.setOnClickListener(this);
        gridview = findViewById(R.id.recycler_without_class);
        gridclassview=findViewById(R.id.recycler_category);
        gridcodeview=findViewById(R.id.recycler_usr_code);
        imgDelete = findViewById(R.id.delete);
        imgEdit = findViewById(R.id.edit);
        imgDeleteAll = findViewById(R.id.delall);
        // imgSummary = findViewById(R.id.summary);
        // imgSummary.setOnClickListener(this);
        imgEdit.setOnClickListener(this);
        imgDeleteAll.setOnClickListener(this);
        imgDelete.setOnClickListener(this);
        txttotal=findViewById(R.id.txtTotalAmt);
        imgConfrim=findViewById(R.id.imgconfirm);
        imgConfrim.setOnClickListener(this);
        imgPrint=findViewById(R.id.imgHeader);
        imgPrint.setOnClickListener(this);

        imgAllDetails=findViewById(R.id.imgAllDetails);//added by YLT on [15-06-2020]
        imgAllDetails.setOnClickListener(this);//added by YLT on [15-06-2020]

        imgPrinter=findViewById(R.id.imgPrintSelection);
        imgPrinter.setOnClickListener(this);

        txtdocid=findViewById(R.id.txtdocid);
        taxlo=findViewById(R.id.taxlo);
        txtdate=findViewById(R.id.txtdate);
        txtdate.setOnClickListener(this);
        imgHeader=findViewById(R.id.imgHeader);
        imgHeader.setOnClickListener(this);
        imgLogout=findViewById(R.id.imgLogout);
        imgLogout.setOnClickListener(this);
        txtfoc=findViewById(R.id.txtFocAmt);
        txtitemdiscount=findViewById(R.id.txtitemDisAmt);
        txtvoudis=findViewById(R.id.txtvoudisamt);
        txtpaid=findViewById(R.id.txtPaidAmt);
        txtnet=findViewById(R.id.txtNetAmt);
        imgBack=findViewById(R.id.back);
        imgBack.setOnClickListener(this);
        txttaxamt=findViewById(R.id.txttaxamt);
        txttax=findViewById(R.id.txttax);
       // btndetail=findViewById(R.id.btndetail);
        String tax="Tax"+(getTax()>0?"( "+getTax()+"% )":"");
        txttax.setText(tax);
        txtoutstand=findViewById(R.id.txtPreviousAmt);
        txttaxamt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keynum = txttaxamt.getText().toString();
                if(frmlogin.tax==1) {
                    showKeyPad(txttaxamt, txttax);
                }
            }
        });


       // btndetail.setOnClickListener(new View.OnClickListener() {
          //  @Override
          //  public void onClick(View v) {
             //   String taxper;
            //    String txttax;
             //   if(sd.size()>0){
            //        taxper="Tax"+(sh.get(0).getTax_per()>0?"( "+sh.get(0).getTax_per()+"% )":"");
            //        txttax=String.valueOf(sh.get(0).getTax_amount());
             //   }else {
             //       taxper="Tax"+(getTax()>0?"( "+getTax()+"% )":"");
             //       txttax="0";
            //    }

           //     String vouper="Vou Discount";
           //     if(custDis>0){
          //          vouper="Vou Discount( "+custDis+"% )";
           //         sh.get(0).setDiscount_per(custDis);
           //         getSummary();
           //     }else {
           //         vouper="Vou Discount"+(sh.get(0).getDiscount_per()>0?"( "+sh.get(0).getDiscount_per()+"% )":"");
           //     }
           //     String total=txttotal.getText().toString();
          //      String txtvou=String.valueOf(sh.get(0).getDiscount());
          //      String txtpaidamt=String.valueOf(sh.get(0).getPaid_amount());
         //       String txtfoc=String.valueOf(sh.get(0).getFoc_amount());
         //       String txtitem=String.valueOf(sh.get(0).getIstemdis_amount());
         //       detailvou(taxper,total,txtvou,vouper,txtpaidamt,txttax,txtfoc,txtitem);
         //   }
       // });


        //region for detail YLT on [15-06-2020
        imgAllDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taxper;
                String txttax;
                if(sd.size()>0){
                    taxper="Tax"+(sh.get(0).getTax_per()>0?"( "+sh.get(0).getTax_per()+"% )":"");
                    txttax=String.valueOf(sh.get(0).getTax_amount());
                }else {
                    taxper="Tax"+(getTax()>0?"( "+getTax()+"% )":"");
                    txttax="0";
                }

                String vouper="Vou Discount";
                if(custDis>0){
                    vouper="Vou Discount( "+custDis+"% )";
                    sh.get(0).setDiscount_per(custDis);
                    getSummary();
                }else {
                    vouper="Vou Discount"+(sh.get(0).getDiscount_per()>0?"( "+sh.get(0).getDiscount_per()+"% )":"");
                }
                String total=txttotal.getText().toString();


                String txtvou=String.valueOf(sh.get(0).getDiscount());
                String txtpaidamt=String.valueOf(sh.get(0).getPaid_amount());
                String txtfoc=String.valueOf(sh.get(0).getFoc_amount());
                String txtitem=String.valueOf(sh.get(0).getIstemdis_amount());
                detailvou(taxper,total,txtvou,vouper,txtpaidamt,txttax,txtfoc,txtitem);
            }
        });
// endregion

//Added By abbp barcode scanner on 19/6/2019

        imgScanner=findViewById(R.id.imgscanner);
        imgScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(saleorder_entry.this,frmscancode.class);
                startActivity(i);
            }
        });


//def customer outstand
        GetCustomerOutstand(1);

//**************************************************************************************************


//removed by abbp for detail btn in sale entry on 18/6/2019
        /*
        txtpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeheader=false;
                if(isCreditcustomer&& sh.get(0).getPay_type()==2) {
                    keynum = txtpaid.getText().toString();
                    paiddis=true;
                    showKeyPad(txtpaid, txtpaid);
                }
                else
                {

                    AlertDialog.Builder ab=new AlertDialog.Builder(sale_entry.this,R.style.AlertDialogTheme);
                    ab.setTitle("iStock");
                    ab.setMessage("Only Allow in Credit.");

                    ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            msg.dismiss();
                        }
                    });
                    msg=ab.create();
                    msg.show();
                }



            }
        });
        txtvoudis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeheader=false;
                voudis=true;
                keynum=txtvoudis.getText().toString();
                showKeyPad(txtpaid,txtvoudis);
            }
        });*/
//***************************************************************************************************
    }
    private void detailvou(String taxper,String total,String txtvou,String vouper,String txtpaidamt,String txttaxam,String txtfocamt,String txtitem){
        AlertDialog.Builder bd=new AlertDialog.Builder(saleorder_entry.this);
        View view=getLayoutInflater().inflate(R.layout.frmdetailconvoucher,null);
        bd.setCancelable(false);
        bd.setView(view);
        TextView txttotal=view.findViewById(R.id.txtTotalAmt);
        txtitemDisAmt=view.findViewById(R.id.txtitemDisAmt);
        txtper=view.findViewById(R.id.txttax);
        txtvouper=view.findViewById(R.id.txtvoudis);
        TextView txtfoc=view.findViewById(R.id.txtFocAmt);
        txtitemdiscount=view.findViewById(R.id.txtitemDisAmt);
        txttaxamT=view.findViewById(R.id.txttaxamt);
        txtvoudis=view.findViewById(R.id.txtvoudisamt);
        txtpaid=view.findViewById(R.id.txtPaidAmt);
        taxlo=view.findViewById(R.id.taxlo);
        txtitemDisAmt.setText(txtitem);
        txtvouper.setText(vouper);
        txtper.setText(taxper);
        txttotal.setText(total);
        txtvoudis.setText(txtvou);
        txtpaid.setText(txtpaidamt);
        txttaxamT.setText(txttaxam);
        txtfoc.setText(txtfocamt);

        if(Use_Tax==0){
            taxlo.setVisibility(View.GONE);
        }else {
            taxlo.setVisibility(View.VISIBLE);
            if(frmlogin.tax==0){
                txttaxamT.setEnabled(false);
            }else {
                txttaxamT.setEnabled(true);
            }

        }
        if(frmlogin.discount==0){
            txtvoudis.setEnabled(false);
        }
        else{
            txtvoudis.setEnabled(true);
        }


        txtvoudis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeheader=false;
                voudis=true;
                keynum=txtvoudis.getText().toString();
                showKeyPad(txtpaid,txtvoudis);



            }
        });
//Added by abbp else case on 26/6/2019
        txttaxamT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keynum = txttaxamT.getText().toString();
                if(frmlogin.tax==1) {
                    showKeyPad(txttaxamT, txtper);
                }
            }
        });


        txtpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeheader=false;
                if(isCreditcustomer&& sh.get(0).getPay_type()==2) {
                    keynum = txtpaid.getText().toString();
                    paiddis=true;
                    showKeyPad(txtpaid, txtpaid);
                }
                else
                {

                    AlertDialog.Builder ab=new AlertDialog.Builder(saleorder_entry.this,R.style.AlertDialogTheme);
                    ab.setTitle("iStock");
                    ab.setMessage("Only Allow in Credit.");

                    ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            msg.dismiss();
                        }
                    });
                    msg=ab.create();
                    msg.show();
                }



            }
        });

        ImageButton btnsave=view.findViewById(R.id.imgSavedet);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                custDis=sh.get(0).getDiscount_per();
                sh.get(0).setDiscount_per(custDis);
                getSummary();
                dialog.dismiss();
            }
        });

        dialog=bd.create();
        dialog.show();


    }
    private void SearchItem(String s) {


        if(frmmain.withoutclass.equals("true")){
            rrvv=gridview;
        }else if(frmmain.withoutclass.equals("false")){
            rrvvc=gridclassview;
            rrvv=gridcodeview;
        }

        switch (fitercode)
        {
            case "Category":
                filteredList = new ArrayList<>();
                Cursor cursorcate =  DatabaseHelper.DistinctSelectQuerySelection("Usr_Code",new String[]{"category","categoryname","class"},"categoryname LIKE?",new String[]{"%"+s+"%"});
                if(filteredList.size()>0) filteredList.clear();
                if(frmmain.withoutclass.equals("false")){
                    filteredCode.clear();
                    filteredList.add(new category("Back"));
                }

                if(cursorcate!=null&&cursorcate.getCount()!=0)
                {
                    if(cursorcate.moveToFirst())
                    {
                        do {
                            long category=cursorcate.getLong(cursorcate.getColumnIndex("category"));
                            String name=cursorcate.getString(cursorcate.getColumnIndex("categoryname"));
                            long classid=cursorcate.getLong(cursorcate.getColumnIndex("class"));
                            filteredList.add(new category(category,classid,name));
                        }while (cursorcate.moveToNext());

                    }

                }
                cursorcate.close();



                if(frmmain.withoutclass.equals("true")){

                    rrvv=gridview;
                    CategoryAdapter ad=new CategoryAdapter(saleorder_entry.this,filteredList,rrvv,"SaleOrder");//YLT
                    rrvv.setAdapter(ad);
                    GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getApplicationContext(), 4);
                    rrvv.setLayoutManager(gridLayoutManager1);
                }
                else if(frmmain.withoutclass.equals("false")){
                    rrvvc=gridclassview;
                    CategoryAdapter ad=new CategoryAdapter(saleorder_entry.this,filteredList,rrvvc,"SaleOrder");//YLT
                    rrvv=gridcodeview;
                    rrvvc.setAdapter(ad);
                    LinearLayoutManager lc = new LinearLayoutManager(saleorder_entry.this,LinearLayoutManager.HORIZONTAL,false);
                    rrvvc.setLayoutManager(lc);
                }

                break;
            case "Code":
                filteredCode = new ArrayList<>();
                if(isCategory)
                {
                    Cursor cursor = DatabaseHelper.DistinctSelectQuerySelection("Usr_Code",new String[]{"usr_code","description"},"usr_code LIKE?",new String[]{s});
                    if(saleorder_entry.usr_codes.size()>0)
                        if(frmmain.withoutclass.equals("true")){
                            filteredCode.add(new usr_code("Back","Back"));
                        }
                    if(cursor!=null&&cursor.getCount()!=0)
                    {
                        if(cursor.moveToFirst())
                        {
                            do {
                                String usr_code=cursor.getString(cursor.getColumnIndex("usr_code"));
                                String description=cursor.getString(cursor.getColumnIndex("description"));
                                filteredCode.add(new usr_code(usr_code,description));
                            }while (cursor.moveToNext());

                        }
                        cursor.close();
                    }else{
                        /*String ss=null;
                        Cursor alcursor = DatabaseHelper.rawQuery("select usr_code from Alias_Code where al_code Like '"+s+"'");
                        if(alcursor!=null&&alcursor.getCount()!=0){
                            if(alcursor.moveToNext()){
                                do{
                                    ss=alcursor.getString(alcursor.getColumnIndex("usr_code"));
                                }while (alcursor.moveToNext());
                            }
                        }
                        alcursor.close();*/
                        Cursor urcursor = DatabaseHelper.rawQuery("select usc.usr_code,usc.description from Alias_Code al join Usr_Code usc on usc.usr_code=al.usr_code where al.al_code LIKE '"+s+"'");
                        if(saleorder_entry.usr_codes.size()>0)
                            if(frmmain.withoutclass.equals("true")){
                                filteredCode.add(new usr_code("Back","Back"));
                            }
                        if(urcursor!=null&&urcursor.getCount()!=0){
                            if(urcursor.moveToNext()){
                                do{
                                    String usr_code=urcursor.getString(urcursor.getColumnIndex("usr_code"));
                                    String description=urcursor.getString(urcursor.getColumnIndex("description"));
                                    filteredCode.add(new usr_code(usr_code,description));
                                }while (urcursor.moveToNext());
                            }
                        }
                        urcursor.close();
                    }


                }
                else {

                    filteredCode.add(new usr_code("Back", "Back"));
                    for (usr_code item : usr_codes) {
                        if (item.getUsr_code() != "Back") {
                            if (item.getUsr_code().toLowerCase().contains(s.toString().toLowerCase())) {
                                filteredCode.add(item);
                            }
                        }
                    }
                }
                UsrcodeAdapter usrcodead = new UsrcodeAdapter(saleorder_entry.this, filteredCode, rrvv, categories,"SaleOrder");//added by YLT
                rrvv.setAdapter(usrcodead);
                GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getApplicationContext(), 4);
                rrvv.setLayoutManager(gridLayoutManager1);

                break;
            case "Description":
            case "Short":
                filtereddesc= new ArrayList<>();

                if(isCategory)
                {
                    Cursor cursor = null;//DatabaseHelper.DistinctSelectQuerySelection("Usr_Code",new String[]{"usr_code","description"},"description LIKE?",new String[]{"%"+s+"%"});
                    if(fitercode.equals("Description"))
                        cursor=DatabaseHelper.DistinctSelectQuerySelection("Usr_Code",new String[]{"usr_code","description"},"description LIKE?",new String[]{"%"+s+"%"});
                    else//Added by KLM 08122020 for Short Search
                        cursor=DatabaseHelper.DistinctSelectQuerySelection("Usr_Code",new String[]{"usr_code","description"},"shortdes LIKE?",new String[]{"%"+s+"%"});

                    if(saleorder_entry.usr_codes.size()>0) saleorder_entry.usr_codes.clear();
                    if(cursor!=null&&cursor.getCount()!=0)
                    {
                        if(frmmain.withoutclass.equals("true")){
                            filtereddesc.add(new usr_code("Back","Back"));
                        }
                        if(cursor.moveToFirst())
                        {
                            do {
                                String usr_code=cursor.getString(cursor.getColumnIndex("usr_code"));
                                String description=cursor.getString(cursor.getColumnIndex("description"));
                                filtereddesc.add(new usr_code(usr_code,description));
                            }while (cursor.moveToNext());

                        }

                    }
                    cursor.close();
                }
                else {
                    for (usr_code item : usr_codes) {
                        if (item.getUsr_code() != "Back") {
                            if (item.getDescription().toLowerCase().contains(s.toString().toLowerCase())) {
                                filtereddesc.add(item);
                            }
                        }
                    }
                }
                UsrcodeAdapter descad = new UsrcodeAdapter(saleorder_entry.this,filtereddesc,rrvv,categories,"SaleOrder");//added by YLT
                rrvv.setAdapter(descad);
                GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getApplicationContext(), 4);
                rrvv.setLayoutManager(gridLayoutManager2);
                break;

            case "Class":
                filteredclass=new ArrayList<>();
                Cursor cursorclass =  DatabaseHelper.DistinctSelectQuerySelection("Usr_Code",new String[]{"class","classname"},"classname LIKE?",new String[]{"%"+s+"%"});
                if(filteredclass.size()>0){
                    filteredclass.clear();
                }
                if (cursorclass != null && cursorclass.getCount() != 0) {
                    if (cursorclass.moveToFirst()) {
                        do {
                            long classid = cursorclass.getLong(cursorclass.getColumnIndex("class"));
                            String name = cursorclass.getString(cursorclass.getColumnIndex("classname"));
                            filteredclass.add(new class_item(classid,name));
                        } while (cursorclass.moveToNext());

                    }

                }
                cursorclass.close();

                cad = new ClassAdapter(saleorder_entry.this, filteredclass, gridclassview,"SaleOrder");//added by YLT
                gridclassview.setAdapter(cad);
                LinearLayoutManager classlayoutmanger = new LinearLayoutManager(saleorder_entry.this,LinearLayoutManager.HORIZONTAL,false);
                gridclassview.setLayoutManager(classlayoutmanger);
                break;

                ///added by YLT for Search Brand on 02-09-2020


            case "Brand": //added by YLT
                filteredbrand= new ArrayList<>();
                //Cursor cursorbrand=  DatabaseHelper.DistinctSelectQuerySelection("Usr_Code",new String[]{"BrandID","BrandName"},"BrandName LIKE?",new String[]{"%"+s+"%"});
                Cursor cursorbrand = DatabaseHelper.rawQuery("select distinct usr_code,description from Usr_Code where  BrandID in (select BrandID from Usr_Code where BrandName like  '%"+s+"%')");
//                if(filteredbrand.size()>0){
//                    filteredbrand.clear();
//                }



                if (cursorbrand != null && cursorbrand.getCount() != 0) {
                    if(frmmain.withoutclass.equals("true")){
                        //filteredbrand.clear();
                        filteredbrand.add(new usr_code("Back","Back"));
                    }
                    if (cursorbrand.moveToFirst()) {
                        do {
                            String usr_code = cursorbrand.getString(cursorbrand.getColumnIndex("usr_code"));
                            String description = cursorbrand.getString(cursorbrand.getColumnIndex("description"));
                            filteredbrand.add(new usr_code(usr_code,description));
                        } while (cursorbrand.moveToNext());

                    }

                }
                cursorbrand.close();

                UsrcodeAdapter usrcodeadforBrand = new UsrcodeAdapter(saleorder_entry.this, filteredbrand, rrvv,"SaleOrder");
                rrvv.setAdapter(usrcodeadforBrand);
                GridLayoutManager gridLayoutManagerBrand = new GridLayoutManager(getApplicationContext(), 4);
                rrvv.setLayoutManager(gridLayoutManagerBrand);


                break;


                ////

        }

    }

    public static double getTax() {

        String sqlString = "select Use_Tax,default_tax_percent from SystemSetting ";
        Cursor cursor = DatabaseHelper.rawQuery(sqlString);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    Use_Tax = cursor.getInt(cursor.getColumnIndex("Use_Tax"));
                    taxpercent=cursor.getDouble(cursor.getColumnIndex("default_tax_percent"));

                } while (cursor.moveToNext());

            }

        }
        cursor.close();
        if(Use_Tax==0) taxpercent=0;
        return  taxpercent;
    }

    private void InsertheadMain() {

        String head = "insert into Sale_Head_Main(tranid,userid,docid,date,invoice_no,locationid,customerid,cash_id,pay_type,due_indays,currency,discount,paid_amount,invoice_amount,invoice_qty,foc_amount,Remark,itemdis_amount)" +
                "values(" +
                sh.get(0).getTranid() + "," +
                sh.get(0).getUserid() + ",'" +
                sh.get(0).getDocid() + "'," +
                "'" + String.format(sh.get(0).getDate(), "yyyy-MM-dd") + "','" +
                sh.get(0).getInvoice_no() + "'," +
                sh.get(0).getLocationid() + "," +
                sh.get(0).getCustomerid() + "," +
                sh.get(0).getDef_cashid() + "," +
                sh.get(0).getPay_type() + "," +
                sh.get(0).getDue_in_days() + "," +
                sh.get(0).getCurrency() + "," +
                sh.get(0).getDiscount() + "," +
                sh.get(0).getPaid_amount() + "," +
                sh.get(0).getInvoice_amount() + "," +
                sh.get(0).getInvoice_qty() + "," +
                sh.get(0).getFoc_amount() + "," +
                "N'"+sh.get(0).getHeadremark()+"',"+
                sh.get(0).getIstemdis_amount()
                + ")";
        sqlstring=head;
        String sqlUrl="";
        String ip = sh_ip.getString("ip", "empty");
        String port = sh_port.getString("port", "empty");
        sqlstring=sqlstring+"&check=true";
        try {
            sqlstring= URLEncoder.encode(sqlstring,"UTF-8").replace("+","%20")
                    .replace("%26","&").replace("%3D","=")
                    .replace("%2C",",")
            ;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sqlUrl = "http://" + ip + ":" + port + "/api/DataSync/GetData?sqlstring="+sqlstring;
        requestQueue = Volley.newRequestQueue(this);
        final Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }

        };

        final Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(saleorder_entry.this, error.getMessage(), Toast.LENGTH_LONG).show();
                pb.dismiss();
            }
        };
        StringRequest req = new StringRequest(Request.Method.GET, sqlUrl, listener, error);
        requestQueue.add(req);

    }
    public static void getData() {
        itemAdapter = new ItemAdapter(datacontext,"SaleOrder");//added by YLT
        itemAdapter.getItemAdpater(itemAdapter);
        entrygrid.setAdapter(itemAdapter);
    }

    private void getHeader() {
        try {

            String ip = sh_ip.getString("ip", "empty");
            String port = sh_port.getString("port", "empty");
            url = "http://"+ip+":"+port+"/api/DataSync/GetHeader?userid="+frmlogin.LoginUserid+"&order=true";//added by YLT
            requestQueue = Volley.newRequestQueue(this);
            final Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {

                        JSONArray jarr = new JSONArray(response);
                        if (sh.size() > 0) sh.clear();
                        for (int i = 0; i < jarr.length(); i++) {
                            JSONObject obj = jarr.getJSONObject(i);
                            tranid=obj.getLong("tranid");
                            String docid=obj.getString("docid");
                           // String deliverValue=obj.getString("deliver");//added by YLT on [15-06-2020]
                            String date=obj.getString("date");
                            long locationid=obj.getLong("locationid");
                            long customerid=obj.getLong("customerid");
                            double tax_per=getTax();
                            //sh.add(new Sale_head_main(tranid, frmlogin.LoginUserid,  "VOU-1",  date,  "",  "",  1,  1,   1, 0,  1,  0,  0,  0,  0,  0,  0,  0,  0,0));
                            sh.add(new Sale_head_main( tranid,  frmlogin.LoginUserid,  "VOU-1",  date,  "",false,  "",  frmlogin.det_locationid,  1, frmlogin.def_cashid,  1, 0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0));
                        }
                        //InsertheadMain();
                        txtdocid.setText(sh.get(0).getDocid());
                        try {
                            String voudate=new SimpleDateFormat("dd/MM/yyyy").format(dateFormat.parse(sh.get(0).getDate()));
                            txtdate.setText(voudate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    } catch (JSONException e) {

                    }
                }


            };

            final Response.ErrorListener error = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    AlertDialog.Builder builder=new AlertDialog.Builder(saleorder_entry.this);
                    builder.setMessage("Please check and reconnect your network");
                    builder.setTitle("iStock");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog1, int which) {
                            Intent intent =new Intent(saleorder_entry.this,frmmain.class);
                            startActivity(intent);
                            finish();
                            dialog.dismiss();
                        }
                    });
                    dialog=builder.create();
                    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialog1) {
                            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                        }
                    });
                    dialog.show();
                }
            };
            StringRequest req = new StringRequest(Request.Method.GET, url, listener, error);
            requestQueue.add(req);


        } catch (Exception ee) {
            AlertDialog.Builder builder=new AlertDialog.Builder(saleorder_entry.this);
            builder.setMessage(ee.getMessage());
            builder.setTitle("iStock");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog1, int which) {
                    Intent intent =new Intent(saleorder_entry.this,frmmain.class);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();
                }
            });
            dialog=builder.create();
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog1) {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                }
            });
            dialog.show();


        }
    }

    private void BindingClass(){
        if(class_items.size()>0){
            class_items.clear();
        }
        Cursor cursor = DatabaseHelper.DistinctCategorySelectQuery("Usr_Code",new String[]{"class","classname"},"classname");
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    long classid = cursor.getLong(cursor.getColumnIndex("class"));
                    String name = cursor.getString(cursor.getColumnIndex("classname"));
                    class_items.add(new class_item(classid,name));
                } while (cursor.moveToNext());

            }

        }
        cursor.close();

        cad = new ClassAdapter(saleorder_entry.this, class_items, gridclassview,"SaleOrder");//added by YLT
        gridclassview.setAdapter(cad);
        LinearLayoutManager classlayoutmanger = new LinearLayoutManager(saleorder_entry.this,LinearLayoutManager.HORIZONTAL,false);
        gridclassview.setLayoutManager(classlayoutmanger);

    }

    private void BindingCategory() {
        if (categories.size() > 0) {
            categories.clear();
        }
        Cursor cursor = DatabaseHelper.DistinctCategorySelectQuery("Usr_Code",new String[]{"category","categoryname","class"},"sortcode,categoryname");
        //Cursor cursor = DatabaseHelper.DistinctSelectQuery("Usr_Code",new String[]{"category","categoryname","class"});
        // Cursor cursor=DatabaseHelper.rawQuery("select * from Usr_Code");
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    long categoryid = cursor.getLong(cursor.getColumnIndex("category"));
                    String name = cursor.getString(cursor.getColumnIndex("categoryname"));
                    long classid = cursor.getLong(cursor.getColumnIndex("class"));
                    categories.add(new category(categoryid, classid, name));
                } while (cursor.moveToNext());

            }

        }
        cursor.close();

        ad = new CategoryAdapter(saleorder_entry.this, categories, gridview,"SaleOrder");//YLT
        gridview.setAdapter(ad);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
        gridview.setLayoutManager(gridLayoutManager);
    }
    @Override
    public void onBackPressed() {
        if(!comfirm && sd.size()>0)
        {
            Context context=this;
            AlertDialog.Builder bd=new AlertDialog.Builder(this,R.style.AlertDialogTheme);
            bd.setTitle("iStock");
            bd.setMessage("Do you Confirm Voucher? if you do not comfirm,you lost your data");
            bd.setCancelable(false);
            bd.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    voucherConfirm();
                    msg.dismiss();
                }
            });
            bd.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    intent = new Intent(context, frmsalelist.class);
                    startActivity(intent);
                    finish();

                }
            });
            bd.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    msg.dismiss();
                }
            });
            msg=bd.create();
            msg.show();
        }
        else
        {
            GoToSaleList();
        }
    }

    public  void GoToSaleList()
    {
        intent = new Intent(this, frmsaleorderlist.class);
        startActivity(intent);
        finish();
    }


    private void ShowPrintSelection() {
        PopupMenu popup = new PopupMenu(saleorder_entry.this,imgPrinter);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.printselectionmenu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(sd.size()>0) {
                    showReport(item.getTitle().toString());
                }
                else
                {
                    AlertDialog.Builder bd=new AlertDialog.Builder(saleorder_entry.this,R.style.AlertDialogTheme);
                    bd.setTitle("iStock");
                    bd.setMessage("No Data To Print");
                    bd.setCancelable(false);
                    bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    bd.create().show();
                }
                return true;
            }
        });
        popup.show();//showing popup menu
    }

    private void showReport(String title) {
        reportviewer.header.put("date",txtdate.getText().toString());
        reportviewer.header.put("Customer",String.valueOf(sh.get(0).getCustomerid()));
        reportviewer.header.put("Pay_Type",String.valueOf(sh.get(0).getPay_type()));
        reportviewer.header.put("TotalAmt",txttotal.getText().toString());
        reportviewer.header.put("itemDis",String.valueOf(Double.parseDouble(txtfoc.getText().toString())-sh.get(0).getFoc_amount()));
        reportviewer.header.put("Foc",String.valueOf(sh.get(0).getFoc_amount()));
        reportviewer.header.put("VouDis",txtvoudis.getText().toString());
        reportviewer.header.put("previous",txtoutstand.getText().toString());
        reportviewer.header.put("tax",txttaxamt.getText().toString());
        reportviewer.header.put("paid",txtpaid.getText().toString());
        reportviewer.header.put("net",txtnet.getText().toString());
        reportviewer.header.put("docid",sh.get(0).getDocid());
        reportviewer.title=title;
        Intent intent=new Intent(saleorder_entry.this,reportviewer.class);
        startActivity(intent);
    }

    private void CleanGarbage() {
        String sqlUrl="";
        String ip = sh_ip.getString("ip", "empty");
        String port = sh_port.getString("port", "empty");
        sqlUrl = "http://"+ip+":"+port+"/api/DataSync/GetData?tranid="+tranid+"&clear=true";
        requestQueue = Volley.newRequestQueue(this);
        final Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            }

        };

        final Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(saleorder_entry.this, error.getMessage(), Toast.LENGTH_LONG).show();
                pb.dismiss();
            }
        };
        StringRequest req = new StringRequest(Request.Method.GET, sqlUrl, listener, error);
        requestQueue.add(req);
    }

    private void LogOut() {

        AlertDialog.Builder  bd=new AlertDialog.Builder(this,R.style.AlertDialogTheme);
        Context context=this;
        bd.setTitle("iStock");
        bd.setMessage("Are You sure to logout?");
        bd.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(!comfirm && sd.size()>0)
                {
                    AlertDialog.Builder conf=new AlertDialog.Builder(context,R.style.AlertDialogTheme);
                    conf.setTitle("iStock");
                    conf.setMessage("Do you Comfirm Voucher? if you do not comfirm,you lost your data");
                    conf.setCancelable(false);
                    conf.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            voucherConfirm();
                            UnLockUser(frmlogin.LoginUserid);
                            dialog.dismiss();
                            msg.dismiss();
                        }
                    });
                    conf.setNegativeButton("N0", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            CleanGarbage();
                            UnLockUser(frmlogin.LoginUserid);
                            dialog.dismiss();
                            msg.dismiss();
                            Intent intent=new Intent(saleorder_entry.this,frmlogin.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    conf.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            msg.dismiss();
                        }
                    });
                    conf.create().show();

                }
                else
                {
                    CleanGarbage();
                    UnLockUser(frmlogin.LoginUserid);
                    dialog.dismiss();
                    msg.dismiss();
                    Intent intent=new Intent(saleorder_entry.this,frmlogin.class);
                    startActivity(intent);
                    finish();

                }
            }
        });
        bd.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                msg.dismiss();
            }
        });
        msg=bd.create();
        msg.show();

    }

    private void UnLockUser(int Userid) {
        String ip=sh_ip.getString("ip","Localhost");
        String port=sh_port.getString("port","80");
        String Device=frmlogin.Device_Name.replace(" ","%20");
        String Url="http://"+ip+":"+port+"/api/DataSync/GetData?userid="+frmlogin.LoginUserid+"&hostname="+Device+"&locked="+false;

        requestQueue = Volley.newRequestQueue(this);

        final Response.Listener<String> listener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DatabaseHelper.execute("delete from Login_User where userid="+frmlogin.LoginUserid);
            }


        };

        final Response.ErrorListener error=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(saleorder_entry.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };


        StringRequest req = new StringRequest(Request.Method.GET,Url,listener,error);
        requestQueue.add(req);
    }
/*
    private  void InitializeHeader(ArrayList<String> id,ArrayList<Button> btn)
    {
        Cursor cursor=null;
        String sqlString="";
        //customer group
        cursor = DatabaseHelper.DistinctSelectQuerySelection("Customer",new String[]{"CustGroupID","CustGroupname","CustGroupCode"},"customerid=?",new String[]{id.get(0)});
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    long custgroupid = cursor.getLong(cursor.getColumnIndex("CustGroupID"));
                    String custgroupname = cursor.getString(cursor.getColumnIndex("CustGroupname"));
                    String shortname = cursor.getString(cursor.getColumnIndex("CustGroupCode"));
                    btn.get(0).setText(custgroupname);
                    saleorder_entry.selected_custgroupid=custgroupid;
                } while (cursor.moveToNext());

            }

        }

        cursor.close();

        //Township
        cursor = DatabaseHelper.DistinctSelectQuerySelection("Customer",new String[]{"Townshipid","Townshipname","TownshipCode"},"customerid=?",new String[]{id.get(0)});
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    long townshipid = cursor.getLong(cursor.getColumnIndex("Townshipid"));
                    String townshipname = cursor.getString(cursor.getColumnIndex("Townshipname"));
                    String shortname = cursor.getString(cursor.getColumnIndex("TownshipCode"));
                    btn.get(1).setText(townshipname);
                    sh.get(0).setTownshipid(townshipid);
                    saleorder_entry.selected_townshipid=townshipid;
                } while (cursor.moveToNext());

            }

        }

        cursor.close();



        //customer
        sqlString = "select customerid,customer_code,customer_name,credit,due_in_days,credit_limit from Customer where customerid="+id.get(0) ;
        cursor = DatabaseHelper.rawQuery(sqlString);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    long customerid = cursor.getLong(cursor.getColumnIndex("customerid"));
                    String customername = cursor.getString(cursor.getColumnIndex("customer_name"));
                    String customercode = cursor.getString(cursor.getColumnIndex("customer_code"));
                    boolean credit = cursor.getInt(cursor.getColumnIndex("credit")) == 1 ? true : false;
                    int due_in_days = cursor.getInt(cursor.getColumnIndex("due_in_days"));
                    double credit_limit=cursor.getDouble(cursor.getColumnIndex("credit_limit"));
                    btn.get(2).setText(customername);
                    isCreditcustomer=credit;
                } while (cursor.moveToNext());

            }

        }
        cursor.close();
        //location

        String locname="Main Store";
        if(defloc!=1) {
            Cursor deflocur = DatabaseHelper.rawQuery("select Location_Name from Location where locationid=" + defloc);
            if (deflocur != null && deflocur.getCount() != 0) {
                if (deflocur.moveToFirst()) {
                    do {

                        locname = deflocur.getString(deflocur.getColumnIndex("Location_Name"));

                    } while (deflocur.moveToNext());
                }
            }
        }

        sqlString = "select locationid,Location_Name,Location_Short,branchID from Location where locationid= "+id.get(1);
        cursor = DatabaseHelper.rawQuery(sqlString);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    long locationid = cursor.getLong(cursor.getColumnIndex("locationid"));
                    String locationname = cursor.getString(cursor.getColumnIndex("Location_Name"));
                    String shortname = cursor.getString(cursor.getColumnIndex("Location_Short"));
                    long branchid = cursor.getLong(cursor.getColumnIndex("branchID"));
                    btn.get(3).setText(locationname);

                    if(defloc!=1){
                        locationid=Long.valueOf(defloc);
                        locationname=locname;
                        btn.get(3).setText(locationname);
                        sh.get(0).setLocationid(locationid);
                    }

                } while (cursor.moveToNext());


            }

        }
        cursor.close();


        //payment_type
        sqlString = "select * from Payment_Type where pay_type="+id.get(2);
        cursor = DatabaseHelper.rawQuery(sqlString);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    int pay_type = cursor.getInt(cursor.getColumnIndex("pay_type"));
                    String pay_typename = cursor.getString(cursor.getColumnIndex("name"));
                    String shortname = cursor.getString(cursor.getColumnIndex("short"));
                    btn.get(4).setText(pay_typename);
                } while (cursor.moveToNext());

            }

        }



    }

 */
    private void CustomerSetup(String name,Button btn,Button btnpay)
    {
        try {

            String sqlString;
            String filter;
            ArrayList<customergroup>cg = new ArrayList<>();
            ArrayList<Township>townships = new ArrayList<>();
            ArrayList<customer>customers = new ArrayList<>();
            ArrayList<Location> locations = new ArrayList<>();
            ArrayList<pay_type> pay_types = new ArrayList<>();
            ArrayList<Salesmen>salesmen=new ArrayList<>();
            Cursor cursor = null;
            AlertDialog.Builder bd = new AlertDialog.Builder(saleorder_entry.this);
            View view = getLayoutInflater().inflate(R.layout.customersetupvalue, null);
            RecyclerView rv = view.findViewById(R.id.rcvChange);
            ImageButton imgClose=view.findViewById(R.id.imgNochange);
            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CustomerSetup.dismiss();
                }
            });
            EditText etdSearch=view.findViewById(R.id.etdSearch);
            ImageButton imgSearch=view.findViewById(R.id.imgSearch);
            imgSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        AlertDialog.Builder searchBuilder=new AlertDialog.Builder(saleorder_entry.this);
                        View view=getLayoutInflater().inflate(R.layout.searchbox,null);
                        searchBuilder.setView(view);
                        EditText etdSearch=view.findViewById(R.id.etdSearch);
                        ImageButton btnSearch=view.findViewById(R.id.imgOK);


                        btnSearch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(!etdSearch.getText().toString().isEmpty())
                                {
                                    switch (name)
                                    {
                                        case "Customer Group":
                                            ArrayList<customergroup> filteredList = new ArrayList<>();

                                            for (customergroup item : cg) {
                                                if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                    filteredList.add(item);
                                                }
                                            }
                                            CustGroupAdapter ad = new CustGroupAdapter(saleorder_entry.this, filteredList, btn, CustomerSetup,"SaleOrder");
                                            CustGroupAdapter.create_customer=true;
                                            rv.setAdapter(ad);
                                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
                                            rv.setLayoutManager(gridLayoutManager);
                                            break;

                                        case "Township":
                                            ArrayList<Township> filteredtownship = new ArrayList<>();

                                            for (Township item : townships) {
                                                if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                    filteredtownship.add(item);
                                                }
                                            }
                                            TownshipAdapter ta = new TownshipAdapter(saleorder_entry.this, filteredtownship, btn, CustomerSetup,"SaleOrder");
                                            rv.setAdapter(ta);
                                            GridLayoutManager gridLayoutManagertown = new GridLayoutManager(getApplicationContext(), 4);
                                            rv.setLayoutManager(gridLayoutManagertown);
                                            break;
                                    }
                                    setupFilter.dismiss();
                                }
                            }
                        });

                        setupFilter=searchBuilder.create();
                        setupFilter.show();
                    }
                    catch (Exception ee)
                    {

                    }
                }
            });
            bd.setView(view);
            CustomerSetup=bd.create();

            switch (name) {

                case "Customer Group":
                    if (cg.size() > 0) {
                        cg.clear();
                    }
                    cursor=DatabaseHelper.rawQuery("select custgroupid,custgroupname,custgroupcode from CustomerGroup");

                    // cursor = DatabaseHelper.DistinctSelectQuery("Customer",new String[]{"CustGroupID","CustGroupname","CustGroupCode"});
                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                long custgroupid = cursor.getLong(cursor.getColumnIndex("custgroupid"));
                                String custgroupname = cursor.getString(cursor.getColumnIndex("custgroupname"));
                                String shortname = cursor.getString(cursor.getColumnIndex("custgroupcode"));
                                if(custgroupid>0)//Added by KLM 22122020
                                    cg.add(new customergroup(custgroupid, custgroupname, shortname));
                            } while (cursor.moveToNext());

                        }

                    }else{
                        cursor = DatabaseHelper.DistinctSelectQuery("Customer",new String[]{"CustGroupID","CustGroupname","CustGroupCode"});
                        if (cursor != null && cursor.getCount() != 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    long custgroupid = cursor.getLong(cursor.getColumnIndex("CustGroupID"));
                                    String custgroupname = cursor.getString(cursor.getColumnIndex("CustGroupname"));
                                    String shortname = cursor.getString(cursor.getColumnIndex("CustGroupCode"));
                                    if(custgroupid>0)//Added by KLM 22122020
                                        cg.add(new customergroup(custgroupid, custgroupname, shortname));
                                } while (cursor.moveToNext());

                            }

                        }
                    }
//                    cursor = DatabaseHelper.DistinctSelectQuery("Customer",new String[]{"CustGroupID","CustGroupname","CustGroupCode"});
//                    if (cursor != null && cursor.getCount() != 0) {
//                        if (cursor.moveToFirst()) {
//                            do {
//                                long custgroupid = cursor.getLong(cursor.getColumnIndex("CustGroupID"));
//                                String custgroupname = cursor.getString(cursor.getColumnIndex("CustGroupname"));
//                                String shortname = cursor.getString(cursor.getColumnIndex("CustGroupCode"));
//                                cg.add(new customergroup(custgroupid, custgroupname, shortname));
//                            } while (cursor.moveToNext());
//
//                        }
//
//                    }

                    cursor.close();


                    CustGroupAdapter ad = new CustGroupAdapter(saleorder_entry.this, cg, btn, CustomerSetup,"SaleOrder");
                    CustGroupAdapter.create_customer=true;
                    rv.setAdapter(ad);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
                    rv.setLayoutManager(gridLayoutManager);
                    cursor = null;


                    CustomerSetup.show();

                    break;
                case "Township":
                    cursor = DatabaseHelper.DistinctSelectQuery("Township",new String[]{"townshipid","townshipname","townshipcode"});//Added by KLM 22122020
                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                long townshipid = cursor.getLong(cursor.getColumnIndex("townshipid"));
                                String townshipname = cursor.getString(cursor.getColumnIndex("townshipname"));
                                String shortname = cursor.getString(cursor.getColumnIndex("townshipcode"));
                                if(townshipid>0)//Added by KLM 22122020
                                    townships.add(new Township(townshipid, townshipname, shortname));
                            } while (cursor.moveToNext());

                        }

                    }
                    else
                    {
                        cursor = DatabaseHelper.DistinctSelectQuery("Customer",new String[]{"Townshipid","Townshipname","TownshipCode"});
                        if (cursor != null && cursor.getCount() != 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    long townshipid = cursor.getLong(cursor.getColumnIndex("Townshipid"));
                                    String townshipname = cursor.getString(cursor.getColumnIndex("Townshipname"));
                                    String shortname = cursor.getString(cursor.getColumnIndex("TownshipCode"));
                                    if(townshipid>0)//Added by KLM 22122020
                                        townships.add(new Township(townshipid, townshipname, shortname));
                                } while (cursor.moveToNext());

                            }

                        }
                        CustomerSetup.dismiss();
                    }
//                    cursor = DatabaseHelper.DistinctSelectQuery("Customer",new String[]{"Townshipid","Townshipname","TownshipCode"});
//                    if (cursor != null && cursor.getCount() != 0) {
//                        if (cursor.moveToFirst()) {
//                            do {
//                                long townshipid = cursor.getLong(cursor.getColumnIndex("Townshipid"));
//                                String townshipname = cursor.getString(cursor.getColumnIndex("Townshipname"));
//                                String shortname = cursor.getString(cursor.getColumnIndex("TownshipCode"));
//
//                                townships.add(new Township(townshipid, townshipname, shortname));
//                            } while (cursor.moveToNext());
//
//                        }
//
//                    }
//                    else
//                    {
//                        CustomerSetup.dismiss();
//                    }
                    cursor.close();

                    TownshipAdapter tad = new TownshipAdapter(saleorder_entry.this, townships, btn, CustomerSetup,"SaleOrder");
                    rv.setAdapter(tad);
                    GridLayoutManager gridLayoutManagerTownship = new GridLayoutManager(getApplicationContext(), 4);
                    rv.setLayoutManager(gridLayoutManagerTownship);
                    CustomerSetup.show();

                    break;
            }

        }
        catch (Exception e)
        {
            CustomerSetup.dismiss();
        }
    }
    private void ChangeHeader(String name,Button btn,Button btnpay) {

        try {

            String sqlString;
            String filter;
            ArrayList<customergroup>cg = new ArrayList<>();
            ArrayList<Township>townships = new ArrayList<>();
            ArrayList<customer>customers = new ArrayList<>();
            ArrayList<Location> locations = new ArrayList<>();
            ArrayList<pay_type> pay_types = new ArrayList<>();
            ArrayList<Salesmen>salesmen=new ArrayList<>();
            Cursor cursor = null;
            AlertDialog.Builder bd = new AlertDialog.Builder(saleorder_entry.this);
            View view = getLayoutInflater().inflate(R.layout.changeheadervalue, null);
            bd.setCancelable(false);
            bd.setView(view);
            RecyclerView rv = view.findViewById(R.id.rcvChange);
            ImageButton imgClose=view.findViewById(R.id.imgNochange);
            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnSalesmen.setText("Choose");
                    da.dismiss();
                }
            });
            ImageButton imgChangSave=view.findViewById(R.id.imgChangeSave);
            ImageButton imgClear=view.findViewById(R.id.imgClear);
            ImageButton imgAddCustomer=view.findViewById(R.id.imgAddCustomer);
            ImageButton imgDownloadCustomer=view.findViewById(R.id.imgDowloadCustomer);
            imgDownloadCustomer.setVisibility(View.GONE);
            if(name.equals("Salesmen"))
            {
                imgChangSave.setVisibility(View.VISIBLE);
                imgClear.setVisibility(View.VISIBLE);
            }
            else if(name.equals("Customer"))
            {
                imgAddCustomer.setVisibility(View.VISIBLE);
                imgDownloadCustomer.setVisibility(View.VISIBLE);
            }
            else
            {
                imgChangSave.setVisibility(View.GONE);
                imgClear.setVisibility(View.GONE);

            }
            imgDownloadCustomer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    allcustomer=true;
                    sqlstring="empty";
                    InsertCustomer();
                }
            });

            imgAddCustomer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Add_Customer();

                }
            });
            imgChangSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(SaleVouSalesmen.size()>0) {
                        String salesmen="";
                        if(SaleVouSalesmen.size()>4){

                            for(int i=0;i<4;i++)
                            {
                                if(i!=3) {
                                    salesmen += SaleVouSalesmen.get(i).getSalesmen_Name()+",";
                                }
                                else
                                {
                                    salesmen += SaleVouSalesmen.get(i).getSalesmen_Name()+",...";
                                }
                            }

                        }else {

                            for(int i=0;i<SaleVouSalesmen.size();i++){
                                if(i!=SaleVouSalesmen.size()-1){
                                    salesmen+=SaleVouSalesmen.get(i).getSalesmen_Name()+",";
                                }else {
                                    salesmen+=SaleVouSalesmen.get(i).getSalesmen_Name();
                                }
                            }
                        }

                        btnSalesmen.setText(salesmen);

                    }else{
                        btnSalesmen.setText("Choose");
                    }
                    da.dismiss();
                }
            });

            imgClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SaleVouSalesmen.clear();
                    if(salesmen.size()>0)
                    {
                        salesmen.clear();
                    }
                    Cursor salemenCursor=DatabaseHelper.rawQuery("select Salesmen_id,Salesmen_Name,short from Salesmen s,Location l where s.branchid=l.branchid and l.locationid="+saleorder_entry.sh.get(0).getLocationid());
                    if (salemenCursor != null && salemenCursor.getCount() != 0) {
                        if (salemenCursor.moveToFirst()) {
                            do {
                                long salesmenid = salemenCursor.getLong(salemenCursor.getColumnIndex("Salesmen_id"));
                                String salesmenname = salemenCursor.getString(salemenCursor.getColumnIndex("Salesmen_Name"));
                                String shortname = salemenCursor.getString(salemenCursor.getColumnIndex("short"));
                                salesmen.add(new Salesmen(salesmenid,salesmenname,shortname));
                            } while (salemenCursor.moveToNext());

                        }

                    }

                    salemenCursor.close();


                    SalesmenAdpater sad = new SalesmenAdpater(saleorder_entry.this,salesmen,"SaleOrder");
                    rv.setAdapter(sad);
                    LinearLayoutManager sgridLayoutManager = new LinearLayoutManager(saleorder_entry.this,LinearLayoutManager.VERTICAL,false);
                    rv.setLayoutManager(sgridLayoutManager);
                    salemenCursor = null;
                    da.show();
                }
            });

            openEditText=false;
            EditText etdSearch=view.findViewById(R.id.etdSearch);
            ImageButton imgSearch=view.findViewById(R.id.imgSearch);
            imgSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        AlertDialog.Builder searchBuilder=new AlertDialog.Builder(saleorder_entry.this);
                        View view=getLayoutInflater().inflate(R.layout.searchbox,null);
                        searchBuilder.setView(view);
                        EditText etdSearch=view.findViewById(R.id.etdSearch);
                        ImageButton btnSearch=view.findViewById(R.id.imgOK);

                        //YLT Today
                        if(name =="Customer" || name =="Township") {
                            ImageButton btnFilterCode = view.findViewById(R.id.imgFilterCode);
                            btnFilterCode.setVisibility(View.VISIBLE);
                            btnFilterCode.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    PopupMenu popup = new PopupMenu(saleorder_entry.this,btnFilterCode);
                                    //Inflating the Popup using xml file
                                    popup.getMenuInflater().inflate(R.menu.filtercustomershortmenu, popup.getMenu());
                                    Menu pp=popup.getMenu();

                                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                        @Override
                                        public boolean onMenuItemClick(MenuItem item) {
                                            switch (item.getItemId())
                                            {
                                                case R.id.customername:
                                                    fitercode="Name";
                                                    break;
                                                case R.id.customershort:
                                                    fitercode="Short";
                                                    break;
                                            }
                                            return true;
                                        }
                                    });
                                    popup.show();//showing popup menu
                                }
                            });
                        }
                        //YLT Today












                        btnSearch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(!etdSearch.getText().toString().isEmpty())
                                {
                                    switch (name)
                                    {
                                        case "Customer Group":
                                            ArrayList<customergroup> filteredList = new ArrayList<>();

                                            for (customergroup item : cg) {
                                                if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                    filteredList.add(item);
                                                }
                                            }
                                            CustGroupAdapter ad = new CustGroupAdapter(saleorder_entry.this, filteredList, btn, da,"SaleOrder");
                                            rv.setAdapter(ad);
                                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
                                            rv.setLayoutManager(gridLayoutManager);
                                            break;
                                        case "Customer":

                                            ArrayList<customer> filteredcustomer = new ArrayList<>();


                                            if( fitercode =="Short")
                                            {
                                                //for Search CustomerShort Search
                                                for (customer item : customers) {
                                                    if (item.getCustomercode().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                        filteredcustomer.add(item);
                                                    }
                                                }
                                            }

                                            else {
                                                for (customer item : customers) {
                                                    if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                        filteredcustomer.add(item);
                                                    }
                                                }
                                            }
                                            CustomerAdapter ca = new CustomerAdapter(saleorder_entry.this, filteredcustomer, btn,btnpay, da,"SaleOrder");
                                            rv.setAdapter(ca);
                                            GridLayoutManager gridLayoutManagerCust = new GridLayoutManager(getApplicationContext(), 4);
                                            rv.setLayoutManager(gridLayoutManagerCust);
                                            break;
                                        case "Township":
                                            ArrayList<Township> filteredtownship = new ArrayList<>();

                                            if(fitercode =="Short")
                                            {
                                                // for Search TownshipSearch
                                                for (Township item : townships) {
                                                    if (item.getTownshipCode().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                        filteredtownship.add(item);
                                                    }
                                                }
                                            }
                                            else {

                                                for (Township item : townships) {
                                                    if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                        filteredtownship.add(item);
                                                    }
                                                }
                                            }
                                            TownshipAdapter ta = new TownshipAdapter(saleorder_entry.this, filteredtownship, btn, da,"SaleOrder");
                                            rv.setAdapter(ta);
                                            GridLayoutManager gridLayoutManagertown = new GridLayoutManager(getApplicationContext(), 4);
                                            rv.setLayoutManager(gridLayoutManagertown);
                                            break;
                                        case "Location":
                                            ArrayList<Location> filteredlocation = new ArrayList<>();

                                            for (Location item : locations) {
                                                if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                    filteredlocation.add(item);
                                                }
                                            }
                                            LocationAdapter la = new LocationAdapter(saleorder_entry.this, filteredlocation, btn, da,"SaleOrder");
                                            rv.setAdapter(la);
                                            GridLayoutManager gridLayoutManagerloc = new GridLayoutManager(getApplicationContext(), 4);
                                            rv.setLayoutManager(gridLayoutManagerloc);
                                            break;
                                        case "Payment Type":
                                            ArrayList<pay_type> filteredpaytype = new ArrayList<>();

                                            for (pay_type item : pay_types) {
                                                if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                    filteredpaytype.add(item);
                                                }
                                            }
                                            PaymentTypeAdapter pad = new PaymentTypeAdapter(saleorder_entry.this, filteredpaytype, btn, da,"SaleOrder");
                                            rv.setAdapter(pad);
                                            GridLayoutManager gridLayoutManagerPaymentType = new GridLayoutManager(getApplicationContext(), 4);
                                            rv.setLayoutManager(gridLayoutManagerPaymentType);
                                            da.show();
                                            break;

                                        case "Salesmen":

                                            ArrayList<Salesmen> filteredsalesmen = new ArrayList<>();

                                            for (Salesmen item : salesmen) {
                                                if (item.getSalesmen_Name().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                    filteredsalesmen.add(item);
                                                }
                                            }
                                            sm = new SalesmenAdpater(saleorder_entry.this, filteredsalesmen);
                                            rv.setAdapter(sm);
                                            LinearLayoutManager linearLayoutManagerSalesmen = new LinearLayoutManager(saleorder_entry.this,LinearLayoutManager.VERTICAL,false);
                                            rv.setLayoutManager(linearLayoutManagerSalesmen);
                                            break;
                                    }
                                    msg.dismiss();
                                }
                            }
                        });

                        msg=searchBuilder.create();
                        msg.show();
                    }
                    catch (Exception ee)
                    {

                    }
                }
            });

            da = bd.create();
            switch (name) {

                case "Salesmen":
                    if(salesmen.size()>0)
                    {
                        salesmen.clear();
                    }
                    Cursor salemenCursor=DatabaseHelper.rawQuery("select Salesmen_id,Salesmen_Name,short from Salesmen s,Location l where s.branchid=l.branchid and l.locationid="+saleorder_entry.sh.get(0).getLocationid());
                    if (salemenCursor != null && salemenCursor.getCount() != 0) {
                        if (salemenCursor.moveToFirst()) {
                            do {
                                long salesmenid = salemenCursor.getLong(salemenCursor.getColumnIndex("Salesmen_id"));
                                String salesmenname = salemenCursor.getString(salemenCursor.getColumnIndex("Salesmen_Name"));
                                String shortname = salemenCursor.getString(salemenCursor.getColumnIndex("short"));
                                salesmen.add(new Salesmen(salesmenid,salesmenname,shortname));
                            } while (salemenCursor.moveToNext());

                        }

                    }

                    salemenCursor.close();


                    SalesmenAdpater sad = new SalesmenAdpater(saleorder_entry.this,salesmen,"SaleOrder");
                    rv.setAdapter(sad);
                    LinearLayoutManager sgridLayoutManager = new LinearLayoutManager(saleorder_entry.this,LinearLayoutManager.VERTICAL,false);
                    rv.setLayoutManager(sgridLayoutManager);
                    salemenCursor = null;


                    da.show();



                    break;

                case "Customer Group":
                    if (cg.size() > 0) {
                        cg.clear();
                    }
                    cursor = DatabaseHelper.DistinctSelectQuery("Customer",new String[]{"CustGroupID","CustGroupname","CustGroupCode"});
                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                long custgroupid = cursor.getLong(cursor.getColumnIndex("CustGroupID"));
                                String custgroupname = cursor.getString(cursor.getColumnIndex("CustGroupname"));
                                String shortname = cursor.getString(cursor.getColumnIndex("CustGroupCode"));
                                cg.add(new customergroup(custgroupid, custgroupname, shortname));
                            } while (cursor.moveToNext());

                        }

                    }

                    cursor.close();


                    CustGroupAdapter ad = new CustGroupAdapter(saleorder_entry.this, cg, btn, da,"SaleOrder");
                    rv.setAdapter(ad);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
                    rv.setLayoutManager(gridLayoutManager);
                    cursor = null;


                    da.show();

                    break;
                case "Township":
                    //cursor = DatabaseHelper.DistinctSelectQuery("Customer",new String[]{"Townshipid","Townshipname","TownshipCode"});
                    cursor = DatabaseHelper.DistinctSelectQuerySelection("Customer",new String[]{"Townshipid","Townshipname","TownshipCode"},"CustGroupID=?",new String[]{String.valueOf(selected_custgroupid)});
                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                long townshipid = cursor.getLong(cursor.getColumnIndex("Townshipid"));
                                String townshipname = cursor.getString(cursor.getColumnIndex("Townshipname"));
                                String shortname = cursor.getString(cursor.getColumnIndex("TownshipCode"));

                                townships.add(new Township(townshipid, townshipname, shortname));
                            } while (cursor.moveToNext());

                        }

                    }
                    else
                    {
                        da.dismiss();
                    }
                    cursor.close();

                    TownshipAdapter tad = new TownshipAdapter(saleorder_entry.this, townships, btn, da,"SaleOrder");
                    rv.setAdapter(tad);
                    GridLayoutManager gridLayoutManagerTownship = new GridLayoutManager(getApplicationContext(), 4);
                    rv.setLayoutManager(gridLayoutManagerTownship);
                    da.show();

                    break;
                case "Customer":

                    if(!use_customergroup)
                    {
                        selected_custgroupid=-1;

                    }
                    if(!use_township)
                    {
                        selected_townshipid=-1;

                    }
                    filter = " where  ((CustGroupID<>-1 and CustGroupID=" + selected_custgroupid + ") or " + selected_custgroupid + "=-1)" +
                            " and ((townshipid<>-1 and townshipid=" + selected_townshipid + ") or " + selected_townshipid + "=-1)";

                    sqlString = "select customerid,customer_code,customer_name,credit,Custdiscount,due_in_days,credit_limit from Customer " + filter +" order by customer_code,customer_name";
                    cursor = DatabaseHelper.rawQuery(sqlString);
                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                long customerid = cursor.getLong(cursor.getColumnIndex("customerid"));
                                String customername = cursor.getString(cursor.getColumnIndex("customer_name"));
                                String customercode = cursor.getString(cursor.getColumnIndex("customer_code"));
                                boolean credit = cursor.getInt(cursor.getColumnIndex("credit")) == 1 ? true : false;
                                double custdis=cursor.getDouble(cursor.getColumnIndex("Custdiscount"));
                                int due_in_days = cursor.getInt(cursor.getColumnIndex("due_in_days"));
                                double credit_limit =cursor.getDouble(cursor.getColumnIndex("credit_limit"));
                                customers.add(new customer(customerid, customername, customercode, credit,custdis,due_in_days,credit_limit));
                                //customers.add(new customer(customerid, customername, customercode, credit,custdis));
                            } while (cursor.moveToNext());

                        }

                    }
                    else
                    {
                        da.dismiss();
                    }
                    cursor.close();

                    CustomerAdapter cad = new CustomerAdapter(saleorder_entry.this, customers, btn,btnpay, da,"SaleOrder");
                    rv.setAdapter(cad);
                    GridLayoutManager gridLayoutManagerCustomer = new GridLayoutManager(getApplicationContext(), 4);
                    rv.setLayoutManager(gridLayoutManagerCustomer);
                    da.show();

                    break;
                case "Location":

                    sqlString = "select locationid,Location_Name,Location_Short,branchID from Location ";
                    cursor = DatabaseHelper.rawQuery(sqlString);
                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                long locationid = cursor.getLong(cursor.getColumnIndex("locationid"));
                                String locationname = cursor.getString(cursor.getColumnIndex("Location_Name"));
                                String shortname = cursor.getString(cursor.getColumnIndex("Location_Short"));
                                long branchid = cursor.getLong(cursor.getColumnIndex("branchID"));
                                locations.add(new Location(locationid, locationname, shortname, branchid));
                            } while (cursor.moveToNext());

                        }

                    }
                    else
                    {
                        da.dismiss();
                    }
                    cursor.close();

                    LocationAdapter lad = new LocationAdapter(saleorder_entry.this, locations, btn, da,"SaleOrder");
                    rv.setAdapter(lad);
                    GridLayoutManager gridLayoutManagerLocation = new GridLayoutManager(getApplicationContext(), 4);
                    rv.setLayoutManager(gridLayoutManagerLocation);
                    da.show();

                    break;
                case "Payment Type":

                    if (pay_types.size() > 0) {
                        pay_types.clear();
                    }
                    if(isCreditcustomer) {
                        sqlString = "select * from Payment_Type";
                    }
                    else
                    {
                        sqlString = "select * from Payment_Type where pay_type=1";
                    }
                    cursor = DatabaseHelper.rawQuery(sqlString);
                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                int pay_type = cursor.getInt(cursor.getColumnIndex("pay_type"));
                                String pay_typename = cursor.getString(cursor.getColumnIndex("name"));
                                String shortname = cursor.getString(cursor.getColumnIndex("short"));
                                pay_types.add(new pay_type(pay_type, pay_typename, shortname));
                            } while (cursor.moveToNext());

                        }

                    }
                    else
                    {
                        da.dismiss();
                    }
                    cursor.close();

                    PaymentTypeAdapter pad = new PaymentTypeAdapter(saleorder_entry.this, pay_types, btn, da,"SaleOrder");
                    rv.setAdapter(pad);
                    GridLayoutManager gridLayoutManagerPaymentType = new GridLayoutManager(getApplicationContext(), 4);
                    rv.setLayoutManager(gridLayoutManagerPaymentType);
                    da.show();

                    break;
            }
        }
        catch (Exception e)
        {
            da.dismiss();
        }
    }

    private void Add_Customer() {
        selected_townshipid=1;
        selected_custgroupid=1;
        AlertDialog.Builder addcustomer = new AlertDialog.Builder(saleorder_entry.this);
        View v = getLayoutInflater().inflate(R.layout.customerdetail, null);
        ImageButton imgAddCustomer=v.findViewById(R.id.imgAddCustomer);
        EditText etdCustomerName=v.findViewById(R.id.txtCustomerName);
        EditText etdCustomerCode=v.findViewById(R.id.txtCustomerCode);
        imgCustomerTownship=v.findViewById(R.id.btnTownship);
        imgCustomerGroup=v.findViewById(R.id.btnCustGroup);
        CheckBox chkCredit=v.findViewById(R.id.chkCredit);
        imgCustomerTownship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerSetup("Township",imgCustomerTownship,imgCustomerTownship);
            }
        });
        imgCustomerGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerSetup("Customer Group",imgCustomerGroup,imgCustomerGroup);
            }
        });
        imgAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCredit=chkCredit.isChecked()?1:0;
                String Customer_Name="N'"+etdCustomerName.getText().toString()+"'";
                String Customer_Code="N'"+etdCustomerCode.getText().toString()+"'";
//                sqlstring=" INSERT INTO Customer_Tmp (customercode,name,townshipid,credit,CustGroupid,updated,inactive,userid)"+
//                        "values("+Customer_Code+","+Customer_Name+","+selected_townshipid+","+isCredit+","+selected_custgroupid+",1,0,"+frmlogin.LoginUserid+")";
                if(etdCustomerName.getText().toString().trim().isEmpty() && etdCustomerCode.getText().toString().trim().isEmpty())
                {


                    Toast.makeText(saleorder_entry.this,"Please set Cutomer Name and Code", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    sqlstring = " INSERT INTO Customer_Tmp (customercode,name,townshipid,credit,CustGroupid,updated,inactive,userid,due_in_days)" +
                            "values(" + Customer_Code + "," + Customer_Name + "," + selected_townshipid + "," + isCredit + "," + selected_custgroupid + ",1,0," + frmlogin.LoginUserid+","+due_in_days + ")";
                }
                if(frmlogin.Font_Language.equals("Unicode"))
                {
                    sqlstring= Rabbit.uni2zg(sqlstring);
                }
                allcustomer=false;
                InsertCustomer();

                addDialog.dismiss();
            }
        });
        addcustomer.setView(v);
        addDialog=addcustomer.create();
        addDialog.show();

    }

    private void voucherConfirm()
    {

       // if(sh.get(0).getPay_type()==1) {
        //    AlertDialog.Builder change = new AlertDialog.Builder(saleorder_entry.this);
         //   change.setCancelable(false);
         //   View v = getLayoutInflater().inflate(R.layout.savechange, null);
         //   ImageButton imgSave=v.findViewById(R.id.imgSave);
         //   ImageButton imgClose=v.findViewById(R.id.imgClose);
          //  tvAmount=v.findViewById(R.id.txtAmount);
        //    tvAmount.setText(txtnet.getText().toString());
          //  tvChange=v.findViewById(R.id.txtChange);
          //  TextView tvPaid=v.findViewById(R.id.txtpaidAmount);
          //  CheckBox chkPrint=v.findViewById(R.id.chkPrint);
         //   chkPrint.setChecked(false);
         //   bill_not_print=false;
         //   chkPrint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           //     @Override
          //      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
          //          bill_not_print=isChecked;
          //      }
          //  });
          //  TextView tvBillCount=v.findViewById(R.id.billcount);
          //  tvBillCount.setText(String.valueOf(billprintcount));
          //  RelativeLayout rlBillCount=v.findViewById(R.id.rlBillCount);
          //  TextView txtAmounttext=v.findViewById(R.id.txtAmounttext);
          //  tvBillCount.setOnClickListener(new View.OnClickListener() {
          //      @Override
          //      public void onClick(View v) {
          //          frombillcount=true;
          //          keynum=tvBillCount.getText().toString();
           //         showKeyPad(txtAmounttext,tvBillCount);

           //     }
          //  });
           // rlBillCount.setOnClickListener(new View.OnClickListener() {
          //      @Override
          //      public void onClick(View v) {
          //          frombillcount=true;
          //          keynum=tvBillCount.getText().toString();
          //          showKeyPad(txtAmounttext,tvBillCount);
          //      }
          //  });



         //   RelativeLayout rlPaid=v.findViewById(R.id.rlPaid);
          //  rlPaid.setOnClickListener(new View.OnClickListener() {
          //      @Override
          //      public void onClick(View v) {
          //          keynum=tvPaid.getText().toString();
           //         fromSaleChange=true;
           //         showKeyPad(tvAmount,tvPaid);


           //     }
          //  });
          //  tvPaid.setOnClickListener(new View.OnClickListener() {
          //      @Override
          //      public void onClick(View v) {
           //         keynum=tvPaid.getText().toString();
          //          fromSaleChange=true;
          //          showKeyPad(tvAmount,tvPaid);
          //      }
          //  });

          //  imgSave.setOnClickListener(new View.OnClickListener() {
         //       @Override
         //       public void onClick(View v) {
          //          salechange.dismiss();
         //           if(tvPaid.getText().toString().trim().isEmpty())
         //           {
          //              paid=0;
          //              paid=Double.parseDouble(txtnet.getText().toString().trim());
          //          }
          //          else
           //         {
           //             paid=Double.parseDouble(tvPaid.getText().toString().trim());
           //             if(paid==0)
            //            {
              //              paid=Double.parseDouble(txtnet.getText().toString().trim());
           //             }
            //        }
            //        if(tvChange.getText().toString().trim().isEmpty())
            //        {
              //          changeamount=0;
            //        }
             //       else
             //       {
             //           changeamount=Double.parseDouble(tvChange.getText().toString().trim());
             //       }
             //       updateVoucher();
             //       ConfirmedTranid = Long.parseLong("0");
            //    }
          //  });
         //   imgClose.setOnClickListener(new View.OnClickListener() {
         //       @Override
         //       public void onClick(View v) {

          //          comfirm=false;
            //        salechange.dismiss();
           //     }
          //  });
         //   change.setView(v);
         //   salechange = change.create();
         //   salechange.show();
      //  }
       // else {

        //    //This Customer's Credit Limit is Over.Do you want to continue ???

        //    double outstandamt = net_amount + Double.parseDouble(ClearFormat(txtoutstand.getText().toString()));
         //   if (outstandamt > saleorder_entry.credit_limit && saleorder_entry.credit_limit!=0) {
         //       if(frmlogin.Allow_Over_Credit_Limit == 1) {
         //           AlertDialog.Builder bd = new AlertDialog.Builder(datacontext, R.style.AlertDialogTheme);
         //           bd.setTitle("iStock");
          //          bd.setMessage("This Customer's Credit Limit is Over.Do you want to continue ???");
           //         bd.setCancelable(false);
          //          bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
           //             @Override
             //           public void onClick(DialogInterface dialog, int which) {
            //                updateVoucher();
             //               ConfirmedTranid = Long.parseLong("0");
            //                dialog.dismiss();
             //           }
             //       });

              //      bd.setNegativeButton("No", new DialogInterface.OnClickListener() {
               //         @Override
               //         public void onClick(DialogInterface dialog, int which) {
               //             dialog.dismiss();
               //         }
                //    });
                //    bd.create().show();
               // }
               // else
               // {
                //    AlertDialog.Builder bd = new AlertDialog.Builder(datacontext, R.style.AlertDialogTheme);
               //     bd.setTitle("iStock");
                //    bd.setMessage("This Customer's Credit Limit is Over");
                //    bd.setCancelable(false);
                //    bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                  //      @Override
                  //      public void onClick(DialogInterface dialog, int which) {
                   //         dialog.dismiss();
                   //     }
                  //  });
                  //  bd.create().show();
               // }
           // }
           // else
           // {
                updateVoucher();
                ConfirmedTranid = Long.parseLong("0");
            //}
        //}


    }

    public static void getSummary()
    {
        sh.get(0).setTax_per(getTax());
        totalAmt_tmp = 0.0;
        qty_tmp = 0.0;
        vouDis_tmp =0.0;
        itemDis_tmp = 0.0;
        paidAmt_tmp = 0.0;
        taxamt_tmp = 0.0;
        calresult_tmp = 0.0;
        netamt_tmp=0.0;
        ItemdisTax_tmp=0.0;
        foc_tmp=0.0;
        if(sd.size()>0)
        {
            for(int i=0;i<saleorder_entry.sd.size();i++)
            {
                totalAmt_tmp+=saleorder_entry.sd.get(i).getUnit_qty()*saleorder_entry.sd.get(i).getSale_price();
                qty_tmp+=sd.get(i).getUnit_qty();
                if(sd.get(i).getDis_type()==1||sd.get(i).getDis_type()==2 ||sd.get(i).getDis_type()
                        ==5)
                {
                    itemDis_tmp+=(sd.get(i).getSale_price()-sd.get(i).getDis_price())*saleorder_entry.sd.get(i).getUnit_qty();
                    sh.get(0).setIstemdis_amount(itemDis_tmp);
                }
                else if(sd.get(i).getDis_type()==3||sd.get(i).getDis_type()==4 ||sd.get(i).getDis_type()
                        ==6||sd.get(i).getDis_type()==7)
                {
                    foc_tmp+=sd.get(i).getSale_price()*saleorder_entry.sd.get(i).getUnit_qty();
                    sh.get(0).setFoc_amount(foc_tmp);
                }

            }
            sh.get(0).setInvoice_qty(qty_tmp);
            sh.get(0).setInvoice_amount(totalAmt_tmp);

            vouDis_tmp=sh.get(0).getDiscount();
            paidAmt_tmp=sh.get(0).getPaid_amount();

            if(sh.get(0).getDiscount_per()>0) {
                vouDis_tmp=sh.get(0).getDiscount_per();
                if (taxper_tmp > 0) {
                    vouDis_tmp = (((totalAmt_tmp) * vouDis_tmp) / 100);
                } else {
                    vouDis_tmp = (((totalAmt_tmp - itemDis_tmp) * vouDis_tmp) / 100);
                }
                sh.get(0).setDiscount(vouDis_tmp);
            }

            taxper_tmp=sh.get(0).getTax_per();
            double calresult=0.0;
            for(int i=0;i<sd.size();i++)
            {
                if(sd.get(i).getCalNoTax()==0&& sd.get(i).getDis_type()!=3&& sd.get(i).getDis_type()!=4 && sd.get(i).getDis_type()!=6 && sd.get(i).getDis_type()!=7)
                {
                    calresult+=sd.get(i).getSale_price()*sd.get(i).getUnit_qty();
                }
            }
            taxamt_tmp=calresult;

            if(Use_Tax==0){
                taxamt_tmp = 0;
            }else {
                if(Tax_Type==0)
                {
                    if (taxper_tmp > 0)
                        taxamt_tmp = ((taxamt_tmp - vouDis_tmp - itemDis_tmp ) * taxper_tmp) / caltax;
                    else
                        taxamt_tmp = 0;
                }
                else
                {
                    if (taxper_tmp > 0)
                        taxamt_tmp = ((taxamt_tmp) * taxper_tmp) / caltax;
                    else
                        taxamt_tmp = 0;
                }

            }


            GetAppSetting getAppSetting=new GetAppSetting("Tax_Exclusive");
            if(getAppSetting.getSetting_Value().equals("Y"))
            {
                if (taxamt_tmp != 0)
                    netamt_tmp = ((totalAmt_tmp - vouDis_tmp - itemDis_tmp - foc_tmp - paidAmt_tmp) + taxamt_tmp);
                else
                    netamt_tmp = (totalAmt_tmp - vouDis_tmp - itemDis_tmp - foc_tmp - paidAmt_tmp);
            }
            else
            {
                netamt_tmp = (totalAmt_tmp - vouDis_tmp - itemDis_tmp - foc_tmp - paidAmt_tmp);
                // taxamt_tmp=0;
            }



        }
        tax_amount=taxamt_tmp;
        // txttaxamT.setText(String.valueOf(tax_amount));
        txttaxamt.setText(String.valueOf(tax_amount));
        sh.get(0).setTax_amount(tax_amount);
        net_amount=netamt_tmp;
        if((net_amount+paidAmt_tmp)<paidAmt_tmp)
        {
            AlertDialog.Builder bd=new AlertDialog.Builder(datacontext,R.style.AlertDialogTheme);
            bd.setTitle("iStock");
            bd.setMessage("Paid Amount is more than Net Amount");
            bd.setCancelable(false);
            bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    paidAmt_tmp=net_amount+paidAmt_tmp;
                    sh.get(0).setPaid_amount(paidAmt_tmp);
                    getSummary();
                    dialog.dismiss();
                }
            });
            bd.create().show();
        }
        else {
            SummaryFormat(txtnet, netamt_tmp);
            SummaryFormat(txtvoudis, sh.get(0).getDiscount());
            SummaryFormat(txtpaid, paidAmt_tmp);
            SummaryFormat(txttotal, totalAmt_tmp);
            SummaryFormat(txtfoc, foc_tmp + itemDis_tmp+sh.get(0).getDiscount());
            sh.get(0).setTax_amount(taxamt_tmp);
            SummaryFormat(txttaxamt, tax_amount);
            SummaryFormat(txttaxamT,tax_amount);
        }
        if(foc_tmp==0)sh.get(0).setFoc_amount(0);
        if(itemDis_tmp==0)sh.get(0).setIstemdis_amount(0);
    }

    private static int getTaxType() {
        String sqlString = "select TaxCal from SystemSetting ";
        int TaxCal=0;
        Cursor cursor = DatabaseHelper.rawQuery(sqlString);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    TaxCal = cursor.getInt(cursor.getColumnIndex("taxCal"));

                } while (cursor.moveToNext());

            }

        }
        cursor.close();
        return TaxCal;
    }


    private static int caltaxsetting() {
        String sqlString = "select Setting_Value from AppSetting where Setting_Name='CalTaxSetting'";
        int caltax = 100;
        String tax = "100";
        Cursor cursor = DatabaseHelper.rawQuery(sqlString);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    tax = cursor.getString(cursor.getColumnIndex("Setting_Value"));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        caltax = Integer.parseInt(tax);
        return caltax;
    }

    private static void SummaryFormat(TextView source,double value) {
        String numberAsString = String.format("%,."+frmmain.price_places+"f", value);
        if(source!=null){
            source.setText(numberAsString);
        }
    }
    //key pad size change modified by ABBP
    private void showKeyPad(TextView txt,TextView source) {

        if(sd.size()==0)
        {
            return;
        }
        startOpen=true;
        LayoutInflater inflater = (LayoutInflater) saleorder_entry.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.keypad,null);
        float density=saleorder_entry.this.getResources().getDisplayMetrics().density;
        final PopupWindow pw = new PopupWindow(layout, (int)density*310, (int)density*500, true);
        pw.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pw.setOutsideTouchable(false);
        pw.showAsDropDown(txt);
        Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0,btnc,btndec,btndel,btnenter,btnper;
        btn1=layout.findViewById(R.id.txt1);
        btn2=layout.findViewById(R.id.txt2);
        btn3=layout.findViewById(R.id.txt3);
        btn4=layout.findViewById(R.id.txt4);
        btn5=layout.findViewById(R.id.txt5);
        btn6=layout.findViewById(R.id.txt6);
        btn7=layout.findViewById(R.id.txt7);
        btn8=layout.findViewById(R.id.txt8);
        btn9=layout.findViewById(R.id.txt9);
        btn0=layout.findViewById(R.id.txt0);
        btnc=layout.findViewById(R.id.txtc);
        btndec=layout.findViewById(R.id.txtdec);
        btnenter=layout.findViewById(R.id.txtenter);
        btndel=layout.findViewById(R.id.txtdel);
        if(frombillcount)
        {
            btndec.setEnabled(false);
        }
        else
        {
            btndec.setEnabled(true);
        }
        btnper=layout.findViewById(R.id.btnpercent);
        TextView txtNum=layout.findViewById(R.id.txtNum);
        if(voudis) btnper.setVisibility(View.VISIBLE);
        btnper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(startOpen==true)
                {
                    keynum="";
                    startOpen=false;
                }
                txtNum.setText(keynum+btnper.getText());
                keynum=txtNum.getText().toString();
            }
        });
        txtNum.setText(String.valueOf(keynum));

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startOpen==true)
                {
                    keynum="";
                    startOpen=false;
                }

                txtNum.setText(keynum+btn1.getText());
                keynum=txtNum.getText().toString();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startOpen==true)
                {
                    keynum="";
                    startOpen=false;
                }
                txtNum.setText(keynum+btn2.getText());
                keynum=txtNum.getText().toString();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startOpen==true)
                {
                    keynum="";
                    startOpen=false;
                }
                txtNum.setText(keynum+btn3.getText());
                keynum=txtNum.getText().toString();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startOpen==true)
                {
                    keynum="";
                    startOpen=false;
                }
                txtNum.setText(keynum+btn4.getText());
                keynum=txtNum.getText().toString();
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startOpen==true)
                {
                    keynum="";
                    startOpen=false;
                }
                txtNum.setText(keynum+btn5.getText());
                keynum=txtNum.getText().toString();
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startOpen==true)
                {
                    keynum="";
                    startOpen=false;
                }
                txtNum.setText(keynum+btn6.getText());
                keynum=txtNum.getText().toString();
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startOpen==true)
                {
                    keynum="";
                    startOpen=false;
                }
                txtNum.setText(keynum+btn7.getText());
                keynum=txtNum.getText().toString();
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startOpen==true)
                {
                    keynum="";
                    startOpen=false;
                }
                txtNum.setText(keynum+btn8.getText());
                keynum=txtNum.getText().toString();
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startOpen==true)
                {
                    keynum="";
                    startOpen=false;
                }
                txtNum.setText(keynum+btn9.getText());
                keynum=txtNum.getText().toString();
            }
        });
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startOpen==true)
                {
                    keynum="";
                    startOpen=false;
                }
                txtNum.setText(keynum+btn0.getText());
                keynum=txtNum.getText().toString();
            }
        });
        btnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startOpen==true)
                {
                    keynum="";
                    startOpen=false;
                }
                txtNum.setText("0");
                keynum=txtNum.getText().toString();
            }
        });
        btndec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startOpen==true)
                {
                    keynum="";
                    startOpen=false;
                }
                txtNum.setText(keynum+btndec.getText());
                keynum=txtNum.getText().toString();
            }
        });
        btndel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(keynum.length()!=0) {
                    keynum=keynum.substring(0,keynum.length()-1);
                    txtNum.setText(keynum);


                }
                startOpen=false;

            }
        });
        btnenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double voudisAmt = 0.0;
                    boolean voudispercent = false;
                    if (voudis) {
                        sh.get(0).setDiscount_per(0);
                        sh.get(0).setDiscount(0);
                        txtvoudis.setText("0");
                        getSummary();
                    }
                    if (paiddis) {
                        sh.get(0).setPaid_amount(0);
                        txtpaid.setText("0");
                        getSummary();
                    }
                    if (keynum.length() > 0) {
                        if (source == txtvoudis) {
                            if (keynum.contains("%")) {
                                keynum = keynum.substring(0, keynum.length() - 1);
                                voudisAmt = Double.parseDouble(keynum);
                                sh.get(0).setDiscount_per(voudisAmt);
                                voudisper = voudisAmt;
                                txtvouper.setText("Vou Discount" + (voudisper > 0 ? "( " + voudisper + "% )" : ""));
                                voudispercent = true;
                            } else {
                                voudDiscountString = "";
                                txtvouper.setText("Vou Discount");
                                sh.get(0).setDiscount_per(0);
                            }
                        }


                        Double check = Double.parseDouble(keynum);
                        if (isqty) {
                            check = check > 0 ? check : 1.0;
                            GetSpecialPrice(itemPosition, check, editUnit_type);
                        }


//msg box and condition for voudisAmt modified by ABBP
                        if (voudispercent) {
                            if (voudisAmt > 100) {
                                sh.get(0).setDiscount(0);
                                sh.get(0).setDiscount_per(0);
                                AlertDialog.Builder builder = new AlertDialog.Builder(saleorder_entry.this, R.style.AlertDialogTheme);
                                builder.setTitle("iStock");
                                builder.setMessage(" Discount Percent should be up to 100% ");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        getSummary();
                                        msg.dismiss();
                                    }
                                });
                                msg = builder.create();
                                msg.show();
                            }

                        } else {

                            if (source.getText().toString().contains("Tax")) {
                                sh.get(0).setTax_per(check);
                                String tax = "Tax ( " + check + "% )";
                                txttax.setText(tax);
                                source.setText(tax);
                                txt.setText(String.valueOf(sh.get(0).getTax_amount()));
                            } else {

                                source.setText(String.valueOf(check));
                            }
                        }

                        if (changeheader) {

                            Double amt = Double.parseDouble(ClearFormat(txtChangeQty.getText().toString())) * Double.parseDouble(ClearFormat(txtChangePrice.getText().toString()));
                            String numberAsString = String.format("%,." + frmmain.price_places + "f", amt);
                            txtamt.setText(numberAsString);
                            sd.get(itemPosition).setUnit_qty(Double.parseDouble(ClearFormat(txtChangeQty.getText().toString())));

                            changeheader = false;
                        } else {

                            String paid = ClearFormat(txtpaid.getText().toString());
                            String dis = ClearFormat(txtvoudis.getText().toString());
                            String net = ClearFormat(txtnet.getText().toString());
                            sh.get(0).setDiscount(Double.parseDouble(dis));
                            sh.get(0).setPaid_amount(Double.parseDouble(paid));

                        }
                        if (isqty) {
                            String numberAsString = String.format("%." + frmmain.qty_places + "f", Double.parseDouble(ClearFormat(source.getText().toString())));
                            source.setText(numberAsString);
                        } else {
                            if (!source.getText().toString().contains("Tax")) {
                                String numberAsString = String.format("%,." + frmmain.price_places + "f", Double.parseDouble(ClearFormat(source.getText().toString())));
                                source.setText(numberAsString);
                            }
                        }
                        isqty = false;
                        startOpen = false;
                        if (voudis) {
                            if (check > net_amount) {
                                sh.get(0).setDiscount_per(0);
                                sh.get(0).setDiscount(0);
                                txtvoudis.setText("0");
                                new AlertDialog.Builder(saleorder_entry.this, R.style.AlertDialogTheme)
                                        .setTitle("iStock")
                                        .setMessage("Voucher Discount is more than Net_Amount")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                getSummary();
                                            }
                                        }).create().show();

                            } else {
                                getSummary();
                            }
                        } else {
                            getSummary();
                        }
                        voudis = false;
                        paiddis = false;

                        if (fromSaleChange) {
                            if (Double.parseDouble(keynum) < Double.parseDouble(ClearFormat(txtnet.getText().toString())) && Double.parseDouble(keynum) > 0) {
                                AlertDialog.Builder bd = new AlertDialog.Builder(saleorder_entry.this, R.style.AlertDialogTheme);
                                bd.setTitle("iStock");
                                bd.setMessage("Paid Amount is less than Net Amount");
                                bd.setCancelable(false);
                                bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        changeamount = 0;
                                        tvChange.setText("0");
                                        source.setText("0");
                                        dialog.dismiss();
                                    }
                                });
                                bd.create().show();
                            } else {
                                changeamount = Double.parseDouble(keynum) - Double.parseDouble(ClearFormat(txtnet.getText().toString()));
                                SummaryFormat(tvChange, changeamount);
                            }
                        }
                        fromSaleChange = false;
                        if (frombillcount) {
                            changeamount = Integer.parseInt(keynum);
                            source.setText(String.valueOf(keynum));
                        }
                        frombillcount = false;
                        keynum = "";
                        pw.dismiss();
                    }
                }
                catch (Exception e) {
                    keynum="0";
                    startOpen=true;
                    if (isqty)
                    {
                        keynum="1";

                    }
                    txtNum.setText(keynum);
                    AlertDialog.Builder bd=new AlertDialog.Builder(saleorder_entry.this,R.style.AlertDialogTheme);
                    bd.setTitle("iStock");
                    bd.setMessage("Number Format is incompatiable with data type");
                    bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            msg.dismiss();
                        }
                    });
                    msg=bd.create();
                    msg.show();
                    pw.dismiss();
                    isqty=false;
                }
            }
        });

    }
    private  String ClearFormat(String s)
    {
        return s.replace(",","");
    }


    private void Confirm() {
        String ip = sh_ip.getString("ip", "empty");
        String port = sh_port.getString("port", "empty");
        String sqlUrl="http://" + ip + ":" + port + "/api/DataSync/SaveData?"+"order=order";//added by YLT
        sqlstring=sqlstring+"&"+sh.get(0).getTranid()+"&"+paid+"&"+changeamount;
        new saleorder_entry.SaveData().execute(sqlUrl);
    }
    /*Added by abbp customer quick set up*/

    private void InsertCustomer(){
        String ip = sh_ip.getString("ip", "empty");
        String port = sh_port.getString("port", "empty");
        String sqlUrl="http://" + ip + ":" + port + "/api/DataSync/SaveData?userid="+frmlogin.LoginUserid+"&language="+frmlogin.Font_Language+"&allcustomer="+allcustomer;
        new saleorder_entry.addCust().execute(sqlUrl);
    }


    private void PrintVoucher(long tranid) {


        if(frmlogin.Confirm_PrintVou==1 && bill_not_print==false && billprintcount>0 && ConfirmedTranid>0) {
            pb.show();
            String sqlUrl = "";
            String ip = sh_ip.getString("ip", "empty");
            String port = sh_port.getString("port", "empty");
            String printername=sh_printer.getString("printer","Choose");
            String printertypeid=sh_ptype.getString("ptype","-1");
            if(frmlogin.Cashier_PrinterType!=-1 && !frmlogin.Cashier_Printer.equals("null"))
            {
                printername=frmlogin.Cashier_Printer;
                printertypeid=String.valueOf(frmlogin.Cashier_PrinterType);
            }

            sqlstring = "userid=" + frmlogin.LoginUserid + "&tranid=" + tranid + "&net_amount=" + txtnet.getText().toString()+"&billcount="+billprintcount+"&printername="+printername+"&printertypeid="+printertypeid+"&report=empty";
            try {
                sqlstring=URLEncoder.encode(sqlstring,"UTF-8").replace("+","%20")
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
                        AlertDialog.Builder b = new AlertDialog.Builder(saleorder_entry.this);
                        b.setTitle("iStock");
                        b.setMessage(response);
                        b.setCancelable(false);
                        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                pb.dismiss();
                                dialog.dismiss();
                                if (sh.size() > 0) sh.clear();
                                if (sd.size() > 0) sd.clear();
                                if(comfirm) {
                                    intent = new Intent(saleorder_entry.this, saleorder_entry.class);
                                    finish();
                                    startActivity(intent);
                                }
                                else if(logout) {
                                    intent = new Intent(getApplicationContext(), frmlogin.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    intent = new Intent(getApplicationContext(),frmsalelist.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                        dialog = b.create();
                        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialog1) {
                                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
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
                    AlertDialog.Builder b = new AlertDialog.Builder(saleorder_entry.this);
                    b.setTitle("iStock");
                    b.setMessage("Printer is not found!");
                    b.setCancelable(false);
                    b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (sh.size() > 0) sh.clear();
                            if (sd.size() > 0) sd.clear();
                            if(comfirm) {
                                intent = new Intent(saleorder_entry.this, saleorder_entry.class);
                                finish();
                                startActivity(intent);
                            }
                            else if(logout)
                            {
                                intent = new Intent(getApplicationContext(),frmlogin.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                intent = new Intent(getApplicationContext(), frmsalelist.class);
                                startActivity(intent);
                                finish();
                            }

                            dialog.dismiss();

                        }
                    });
                    dialog=b.create();
                    dialog.show();

                }
            };
            StringRequest req = new StringRequest(Request.Method.GET, sqlUrl, listener, error);
            DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            req.setRetryPolicy(retryPolicy);
            requestQueue.add(req);
        }
        else
        {
            if (sh.size() > 0) sh.clear();
            if (sd.size() > 0) sd.clear();
            if(comfirm) {
                intent = new Intent(saleorder_entry.this, saleorder_entry.class);
                finish();
                startActivity(intent);
            }
            else if(logout) {
                intent = new Intent(getApplicationContext(), frmlogin.class);
                startActivity(intent);
                finish();
            }
            else
            {
                intent = new Intent(this, frmsalelist.class);
                startActivity(intent);
                finish();
            }
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void ChangeVouDate() {
        final Calendar c=Calendar.getInstance();
        int mYear=c.get(Calendar.YEAR);
        int mMonty=c.get(Calendar.MONTH);
        int mDay=c.get(Calendar.DAY_OF_MONTH);

        pickerDialog=new DatePickerDialog(this,R.style.DatePickerDialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                String selectedDate=dayOfMonth+"/"+(month+1)+"/"+year;
                try {
                    voudate =new SimpleDateFormat("dd/MM/yyyy").parse(selectedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date todate=new Date();
                if(voudate.getTime() <=todate.getTime()) {
                    txtdate.setText(new SimpleDateFormat("dd/MM/yyyy").format(voudate));
                    sh.get(0).setDate(dateFormat.format(voudate));
                }
                else
                {
                    txtdate.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
                }

            }
        },mYear,mMonty,mDay);
        pickerDialog.show();

    }

    private void updateVoucher() {

        try {
            if (tranid < 0) {

                if(sh.get(0).getInvoice_no().equals("NULL") || sh.get(0).getInvoice_no().equals(""))
                {
                    invoice_no="NULL";
                }
                else
                {
                    invoice_no="N'"+sh.get(0).getInvoice_no()+"'";

                }

                if(sh.get(0).getHeadremark().equals("NULL") || sh.get(0).getHeadremark().equals(""))
                {
                    headRemark="NULL";
                }
                else
                {
                    headRemark="N'"+sh.get(0).getHeadremark()+"'";

                }
                if(Use_Delivery)
                {
                    if(chkDeliver.isChecked()) {
                        ToDeliver = ",deliver=1";
                    }
                    else
                    {
                        ToDeliver="";
                    }
                }
                else
                {
                    ToDeliver="";
                }

                String head = "update Sale_Ord_Head set\n" +
                        "tranid="+sh.get(0).getTranid()+",\n" +
                        "docid='"+sh.get(0).getDocid()+"',\n" +
                        "date=" +"'" + String.format(sh.get(0).getDate(), "yyyy-MM-dd") + "'," +
                        "due_date=" +"'" + String.format(sh.get(0).getDate(), "yyyy-MM-dd") + "'," +
                        "invoice_no="+invoice_no+",\n" +
                        "locationid="+sh.get(0).getLocationid()+",\n" +
                        "customerid="+sh.get(0).getCustomerid()+",\n" +
                        "cash_id="+sh.get(0).getDef_cashid()+",\n" +
                        "townshipid="+sh.get(0).getTownshipid()+",\n"+
                        "pay_type="+sh.get(0).getPay_type()+",\n" +
                       // "due_indays="+sh.get(0).getDue_in_days()+",\n" +
                        "currency=1,\n" +
                        "discount="+sh.get(0).getDiscount()+",\n" +
                        "paid_amount="+sh.get(0).getPaid_amount()+",\n" +
                        "invoice_amount="+sh.get(0).getInvoice_amount()+",\n" +
                        "invoice_qty="+sh.get(0).getInvoice_qty()+",\n" +
                        "foc_amount="+sh.get(0).getFoc_amount()+",\n" +
                       // "itemdis_amount="+sh.get(0).getIstemdis_amount()+",\n" +
                        "net_amount="+net_amount+",\n" +
                        "Remark="+headRemark+",\n"+
                       // "tax_amount="+sh.get(0).getTax_amount()+",\n" +
                       // "tax_percent="+sh.get(0).getTax_per()+",\n" +
                        "discount_per="+sh.get(0).getDiscount_per()+",\n"+
                        "exg_rate="+1+"\n"+ToDeliver+
                        " where tranid="+sd.get(0).getTranid();

                String det = "delete from Sale_Ord_Det where tranid="+sh.get(0).getTranid()+
                        " insert into Sale_Ord_Det(tranid,unit_qty,qty,sale_price,dis_price,dis_type,dis_percent,remark,unit_type,code,sr,srno,SQTY,SPrice) values ";


                for(int i=0;i<sd.size();i++)
                {


                    if(sd.get(i).getDetremark().equals("NULL") || sd.get(i).getDetremark().equals("")){
                        detRemark="NULL";
                    }else {
                        detRemark="N'"+sd.get(i).getDetremark()+"'";
                    }


                    if (i < (sd.size() - 1))
                    {
                        det = det + "(" +
                                sd.get(i).getTranid() + "," +
                               // "'" + String.format(sh.get(0).getDate(), "yyyy-MM-dd") + "'," +
                                sd.get(i).getUnit_qty() + "," +
                                sd.get(i).getQty() + "," +
                                sd.get(i).getSale_price() + "," +
                                sd.get(i).getDis_price() + "," +
                                sd.get(i).getDis_type()+","+
                                sd.get(i).getDis_percent()+ "," +
                                detRemark+","+
                                sd.get(i).getUnt_type() + "," +
                                sd.get(i).getCode() + "," +
                                (i + 1) + "," +
                                (i + 1) + ","+
                               // sd.get(i).getPriceLevel()+"',"+
                                getSmallestQty(sd.get(i).getCode(),sd.get(i).getUnit_qty(),sd.get(i).getUnt_type())+","+
                                getSPrice(sd.get(i).getCode(),getSmallestQty(sd.get(i).getCode(),sd.get(i).getUnit_qty(),sd.get(i).getUnt_type()),sd.get(i).getUnit_qty(),sd.get(i).getSale_price()) +" ),";


                    }

                    else {
                        det = det + "(" +
                                sd.get(i).getTranid() + "," +
                               // "'" + String.format(sd.get(0).getDate(), "yyyy-MM-dd") + "'," +
                                sd.get(i).getUnit_qty() + "," +
                                sd.get(i).getQty() + "," +
                                sd.get(i).getSale_price() + "," +
                                sd.get(i).getDis_price() + "," +
                                sd.get(i).getDis_type()+","+
                                sd.get(i).getDis_percent()+ "," +
                                detRemark+","+
                                sd.get(i).getUnt_type() + "," +
                                sd.get(i).getCode() + "," +
                                (i + 1)+ "," +
                                (i + 1) + ","+
                               // sd.get(i).getPriceLevel()+"',"+
                                getSmallestQty(sd.get(i).getCode(),sd.get(i).getUnit_qty(),sd.get(i).getUnt_type())+","+
                                getSPrice(sd.get(i).getCode(),getSmallestQty(sd.get(i).getCode(),sd.get(i).getUnit_qty(),sd.get(i).getUnt_type()),sd.get(i).getUnit_qty(),sd.get(i).getSale_price()) +" )";
                    }

                }
                sh.get(0).getDiscount_per();
                sh.get(0).setDiscount_per(0.0);
                custDis=sh.get(0).getDiscount_per();
                sqlstring = head +" "+det;
                if(use_salesperson && SaleVouSalesmen.size()>0)
                {
                    String salePerson=" delete from SaleOrdVoucher_Salesmen_Tmp where SaleOrd_TranID="+sh.get(0).getTranid()+" and userid="+frmlogin.LoginUserid+
                            "insert into SaleOrdVoucher_Salesmen_Tmp(SaleOrd_TranID,Salesmen_ID,rmt_copy,userid)"+
                            "values ";
                    for(int i=0;i<SaleVouSalesmen.size();i++)
                    {
                        salePerson=salePerson+"("+
                                sh.get(0).getTranid()+","+
                                SaleVouSalesmen.get(i).getSalesmen_Id()+","+
                                "1,"+frmlogin.LoginUserid+"),";
                    }
                    salePerson=salePerson.substring(0,salePerson.length()-1);
                    sqlstring=sqlstring+" "+salePerson;

                }
                if(frmlogin.Font_Language.equals("Unicode"))
                {
                    sqlstring= Rabbit.uni2zg(sqlstring);
                }
                Confirm();
                SaleVouSalesmen.clear();

            }
        }
        catch (Exception ee)
        {

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

    private double getSPrice(long code, double sqty,double unit_qty,double sale_price) {
        double sPrice=0;
       /* Cursor cursor=DatabaseHelper.rawQuery("select sale_price from usr_code where code="+code+" and unit_type=(select max(unit_type) from usr_code where code="+code+")");
        if(cursor!=null&&cursor.getCount()!=0)
        {
            if(cursor.moveToFirst())
            {
                do {
                    sPrice=cursor.getDouble(cursor.getColumnIndex("sale_price"));

                }while (cursor.moveToNext());


            }

        }
        cursor.close();*/

        sPrice=(sale_price*unit_qty)/sqty;

        return  sPrice;

    }



    @SuppressLint("WrongViewCast")
    public  void EditInfo()
    {
        if (itemPosition > -1 && sd.size()>0) {

            changeheader=true;
            editUnit_type=sd.get(itemPosition).getUnt_type();
            Cursor cursor=null;
            String sqlString="";
            AlertDialog.Builder builder=new AlertDialog.Builder(saleorder_entry.this,R.style.AlertDialogTheme);
            View view=getLayoutInflater().inflate(R.layout.editinfo,null);
            builder.setCancelable(false);
            builder.setView(view);
            rlUnit=view.findViewById(R.id.rlUnit);
            rlLevel=view.findViewById(R.id.rllevel);
            txtShowSP=view.findViewById(R.id.showSP);
            txtShowSP.setText(sd.get(itemPosition).getPriceLevel());
            TextView txtheader=view.findViewById(R.id.caption);
            txtsqty=view.findViewById(R.id.txtChangeSQty);
            txtChangePrice=view.findViewById(R.id.txtChangePrice);
            txtamt=view.findViewById(R.id.txtChangeAmt);
            String title=sd.get(itemPosition).getDesc();
            txtheader.setText(title);
            txtshowUnit=view.findViewById(R.id.showpkg);
            txtshowUnit.setText(sd.get(itemPosition).getUnit_short());
            txtChangeQty=view.findViewById(R.id.txtChangeQty);
            txtChangeQty.setText(String.valueOf(sd.get(itemPosition).getUnit_qty()));
            ImageButton imgaddQty,imgsubQty;
            rcvUnit=view.findViewById(R.id.rcvUnit);
            rcvSP=view.findViewById(R.id.rcvSP);
            btndiscount=view.findViewById(R.id.btndiscountype);
            txtsmqty=view.findViewById(R.id.txtSQTY);
            txtsprice=view.findViewById(R.id.txtSprice);
            txtsmqty.setText(String.valueOf(getsqty(sd.get(itemPosition).getCode(),editUnit_type)*Double.parseDouble(txtChangeQty.getText().toString())));
            txtChangeQty.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    txtsmqty.setText(String.valueOf(getsqty(sd.get(itemPosition).getCode(),editUnit_type)*Double.parseDouble(s.toString())));
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            txtsprice.setText(String.valueOf(getSPrice(sd.get(itemPosition).getCode(),getSmallestQty(sd.get(itemPosition).getCode(),
                    sd.get(itemPosition).getUnit_qty(),
                    sd.get(itemPosition).getUnt_type()),
                    sd.get(itemPosition).getUnit_qty(),
                    sd.get(itemPosition).getSale_price())));

            RelativeLayout rlsqty=view.findViewById(R.id.rlSQTY);
            RelativeLayout rlsprice=view.findViewById(R.id.rlSprice);

            ShowHideSmallestQty(rlsqty,rlsprice);

            boolean use_multipricelvl=false;
            boolean use_unit=false;
            Cursor cursorplvl=DatabaseHelper.rawQuery("select use_multipricelvl,use_unit from SystemSetting");
            if(cursorplvl!=null&&cursorplvl.getCount()!=0)
            {
                if(cursorplvl.moveToFirst())
                {
                    do {
                        use_multipricelvl=cursorplvl.getInt(cursorplvl.getColumnIndex("use_multipricelvl"))==1?true:false;
                        use_unit=cursorplvl.getInt(cursorplvl.getColumnIndex("use_unit"))==1?true:false;
                    }while (cursorplvl.moveToNext());


                }

            }
            cursorplvl.close();


            if(frmlogin.discount==0){
                btndiscount.setEnabled(false);
            }else {
                btndiscount.setEnabled(true);
            }

            if(use_multipricelvl) {

                //binding pricelevel
                rlLevel.setVisibility(View.VISIBLE);
                int row_index=-1;
                switch (sd.get(itemPosition).getPriceLevel())
                {
                    case "SP":
                        row_index=0;
                        break;
                    case "SP1":
                        row_index=1;
                        break;
                    case "SP2":
                        row_index=2;
                        break;
                    case "SP3":
                        row_index=3;
                        break;
                }
                pad = new priceLevelAdapter(saleorder_entry.this, itemPosition, txtChangePrice, txtChangeQty, txtamt,row_index,"Sale Order");
                rcvSP.setAdapter(pad);
                GridLayoutManager gridPricelevel = new GridLayoutManager(getApplicationContext(), 4);
                rcvSP.setLayoutManager(gridPricelevel);
            }
            else {
                rlLevel.setVisibility(View.GONE);
            }

            //binding unit
            if(use_unit) {
                rlUnit.setVisibility(View.VISIBLE);

                if (unitforcodes.size() > 0) {
                    unitforcodes.clear();
                }
                sqlString = "select * from Usr_Code where unit>0 and code=" + sd.get(itemPosition).getCode() + " order by unit_type ";
                cursor = DatabaseHelper.rawQuery(sqlString);
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
                            unitforcodes.add(new unitforcode(code, usr_code, unit_type, unit, unitname, shortdes, saleprice, sqty, saleprice1, saleprice2, saleprice3));

                        } while (cursor.moveToNext());

                    }
                }
                cursor.close();
                int row_index=-1;
//Added by abbp default unit type on 09/7/2019
                if(defunit==1){
                    row_index=0;
                }else if(defunit==2){
                    row_index=1;
                }else if(defunit==3){
                    row_index=2;
                }


                switch (sd.get(itemPosition).getUnt_type())
                {
                    case 1:
                        row_index=0;
                        defunit=-1;
                        break;
                    case 2:
                        row_index=1;
                        defunit=-1;
                        break;
                    case 3:
                        row_index=2;
                        defunit=-1;
                        break;
                }



                uad = new UnitAdapter(saleorder_entry.this, unitforcodes, itemPosition, txtChangePrice, txtsqty, txtChangeQty, txtamt,btndiscount,row_index,"Sale Order");
                rcvUnit.setAdapter(uad);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
                rcvUnit.setLayoutManager(gridLayoutManager);
                cursor = null;
            }
            else
            {
                rlUnit.setVisibility(View.GONE);
            }

            imgaddQty=view.findViewById(R.id.imgAddqty);
            imgsubQty=view.findViewById(R.id.imgSubqty);


            //Initialize Dis_Type
            sqlString = "select * from Dis_Type where dis_type= "+sd.get(itemPosition).getDis_type();
            cursor = DatabaseHelper.rawQuery(sqlString);
            if (cursor != null && cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {
                        long dis_type = cursor.getLong(cursor.getColumnIndex("dis_type"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String shortname = cursor.getString(cursor.getColumnIndex("short"));
                        int paid=cursor.getInt(cursor.getColumnIndex("paid"));
                        long discount=cursor.getLong(cursor.getColumnIndex("discount"));
                        if(dis_type==5) {
                            if(sd.get(itemPosition).getDis_percent()>0)
                            {
                                btndiscount.setText(sd.get(itemPosition).getDis_percent()+"%");

                            }
                            else
                            {
                                double dis=sd.get(itemPosition).getSale_price()-sd.get(itemPosition).getDis_price();

                                btndiscount.setText(String.valueOf(dis));
                            }
                        }
                        else
                        {
                            btndiscount.setText(name);
                        }
//                        itemdis=btndiscount.getText().toString();
                    } while (cursor.moveToNext());

                }

            } else {
                disDa.dismiss();
            }

            cursor.close();
            btndiscount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        String sqlString;
                        Cursor cursor = null;
                        AlertDialog.Builder bd = new AlertDialog.Builder(saleorder_entry.this,R.style.AlertDialogTheme);
                        View view = getLayoutInflater().inflate(R.layout.changeheadervalue, null);
                        bd.setView(view);
                        RecyclerView rv = view.findViewById(R.id.rcvChange);
                        ImageButton imgClose=view.findViewById(R.id.imgNochange);
                        EditText etdSearch=view.findViewById(R.id.etdSearch);
                        ImageButton imgSearch=view.findViewById(R.id.imgSearch);
                        etdSearch.setVisibility(View.GONE);
                        imgSearch.setVisibility(View.GONE);
                        disDa = bd.create();
                        imgClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                disDa.dismiss();
                            }
                        });
                        ArrayList<Dis_Type> cg = new ArrayList<>();
                        if (cg.size() > 0) {
                            cg.clear();
                        }

                        sqlString = "select * from Dis_Type ";
                        cursor = DatabaseHelper.rawQuery(sqlString);
                        if (cursor != null && cursor.getCount() != 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    long dis_type = cursor.getLong(cursor.getColumnIndex("dis_type"));
                                    String name = cursor.getString(cursor.getColumnIndex("name"));
                                    String shortname = cursor.getString(cursor.getColumnIndex("short"));
                                    int paid=cursor.getInt(cursor.getColumnIndex("paid"));
                                    long discount=cursor.getLong(cursor.getColumnIndex("discount"));
                                    cg.add(new Dis_Type(dis_type,name,shortname,discount));
                                } while (cursor.moveToNext());

                            }

                        } else {
                            disDa.dismiss();
                        }

                        cursor.close();

                        DisTypeAdapter ad = new DisTypeAdapter(saleorder_entry.this, cg, btndiscount, disDa,itemPosition,txtChangeQty,"Sale Order");
                        rv.setAdapter(ad);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
                        rv.setLayoutManager(gridLayoutManager);
                        cursor = null;
                        disDa.show();


                    } catch (Exception e) {
                        disDa.dismiss();
                    }
                }
            });
//add detremark in editinfo
            detremark=view.findViewById(R.id.txtDetRemark);
            detremark.setText(sd.get(itemPosition).getDetremark()=="NULL"?"":sd.get(itemPosition).getDetremark());


            txtsqty.setText(String.valueOf(sd.get(itemPosition).getQty()));
            imgaddQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    txtChangeQty.setText(String.valueOf(Double.parseDouble(txtChangeQty.getText().toString())+1));
                    sd.get(itemPosition).setUnit_qty(Double.parseDouble(txtChangeQty.getText().toString()));
                    GetSpecialPrice(itemPosition,Double.parseDouble(txtChangeQty.getText().toString()),editUnit_type);
                    Double amt=Double.parseDouble(txtChangeQty.getText().toString())*Double.parseDouble(ClearFormat(txtChangePrice.getText().toString()));
                    String numberAsString = String.format ("%,."+frmmain.price_places+"f", amt);
                    txtamt.setText(numberAsString);
                    double sqty=sd.get(itemPosition).getQty()*Double.parseDouble(txtChangeQty.getText().toString());
                    txtsqty.setText(String.valueOf(sqty));


                }
            });

            imgsubQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Double.parseDouble(txtChangeQty.getText().toString())>=2)
                    {
                        txtChangeQty.setText(String.valueOf(Double.parseDouble(txtChangeQty.getText().toString())-1));
                        sd.get(itemPosition).setUnit_qty(Double.parseDouble(txtChangeQty.getText().toString()));
                        GetSpecialPrice(itemPosition,Double.parseDouble(txtChangeQty.getText().toString()),editUnit_type);
                        Double amt=Double.parseDouble(txtChangeQty.getText().toString())*Double.parseDouble(ClearFormat(txtChangePrice.getText().toString()));
                        String numberAsString = String.format ("%,."+frmmain.price_places+"f", amt);
                        txtamt.setText(String.valueOf(numberAsString));
                        double sqty=sd.get(itemPosition).getQty()*Double.parseDouble(txtChangeQty.getText().toString());
                        txtsqty.setText(String.valueOf(sqty));
                    }
                }
            });
            txtChangeQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isqty=true;
                    changeheader=true;
                    keynum=txtChangeQty.getText().toString();
                    showKeyPad(txtChangeQty,txtChangeQty);
                    double sqty=sd.get(itemPosition).getQty()*Double.parseDouble(txtChangeQty.getText().toString());
                    txtsqty.setText(String.valueOf(sqty));



                }


            });

//not change_price in sale entry modified by ABBP
            //txtChangePrice.setText(String.valueOf(sd.get(itemPosition).getSale_price()));
            SummaryFormat(txtChangePrice,sd.get(itemPosition).getSale_price());
            ImageButton imgChangePrice=view.findViewById(R.id.imgChangePrice);
            imgChangePrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(frmlogin.change_price ==1){
                        imgChangePrice.setEnabled(false);

                        changeheader=true;
                        keynum=String.format ("%."+frmmain.price_places+"f", Double.parseDouble(txtChangePrice.getText().toString()));
                        showKeyPad(txtChangeQty,txtChangePrice);}
                }
            });
//not change_price in sale entry modified by ABBP
            //open_price add by ABBP
            txtChangePrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    int op=sd.get(itemPosition).getOpen_price();

                    if(frmlogin.change_price == 1 || op==1) {
                        changeheader = true;
                        keynum = String.format("%."+frmmain.price_places+"f", Double.parseDouble(ClearFormat(txtChangePrice.getText().toString())));
                        showKeyPad(txtChangeQty, txtChangePrice);
                    }
                    Double amt=Double.parseDouble(txtChangeQty.getText().toString())*Double.parseDouble(ClearFormat(txtChangePrice.getText().toString()));
                    String numberAsString = String.format ("%,."+frmmain.price_places+"f", amt);
                    txtamt.setText(numberAsString);

                }

            });


            Double amt=Double.parseDouble(txtChangeQty.getText().toString())*Double.parseDouble(ClearFormat(txtChangePrice.getText().toString()));
            String numberAsString = String.format ("%,."+frmmain.price_places+"f", amt);
            txtamt.setText(numberAsString);


            ImageButton save=view.findViewById(R.id.imgSave);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sd.get(itemPosition).setUnit_qty(Double.parseDouble(txtChangeQty.getText().toString()));
                    sd.get(itemPosition).setSale_price(Double.parseDouble(ClearFormat(txtChangePrice.getText().toString())));
                    sd.get(itemPosition).setDis_price(Double.parseDouble(ClearFormat(txtChangePrice.getText().toString())));
                    sd.get(itemPosition).setQty(Double.parseDouble(txtsqty.getText().toString())*sd.get(itemPosition).getUnit_qty());
                    if(sd.get(itemPosition).getDis_type()==3
                            ||sd.get(itemPosition).getDis_type()==4
                            ||sd.get(itemPosition).getDis_type()==6
                            ||sd.get(itemPosition).getDis_type()==7)
                    {
                        sd.get(itemPosition).setDis_percent(0);
                        sd.get(itemPosition).setDis_price(sd.get(itemPosition).getSale_price());
                    }
                    else if (sd.get(itemPosition).getDis_type()==1
                            ||sd.get(itemPosition).getDis_type()==2)
                    {
                        double dispercent=sd.get(itemPosition).getDis_type()==1?0.05:0.1;
                        double discount=sd.get(itemPosition).getDis_type()==1?5:10;
                        sd.get(itemPosition).setDis_percent(discount);
                        double dis_price=sd.get(itemPosition).getSale_price()-(sd.get(itemPosition).getSale_price()*(dispercent));
                        sd.get(itemPosition).setDis_price(dis_price);
                    }
                    else if(sd.get(itemPosition).getDis_type()==5)
                    {
                        if(dis_typepercent)
                        {
                            double dis_percent=saleorder_entry.dis_percent;
                            sd.get(itemPosition).setDis_percent(dis_percent);
                            double dis_price=sd.get(itemPosition).getSale_price()-(sd.get(itemPosition).getSale_price()*(dis_percent/100));
                            sd.get(itemPosition).setDis_price(dis_price);


                        }
                        else
                        {
                            double dis_percent=saleorder_entry.dis_percent;
                            sd.get(itemPosition).setDis_percent(dis_percent);
                            double dis_price=sd.get(itemPosition).getSale_price()-Double.parseDouble(btndiscount.getText().toString());
                            sd.get(itemPosition).setDis_price(dis_price);
                        }
                    }

                    detRemark=detremark.getText().toString().trim().isEmpty()?"NULL":detremark.getText().toString().trim();
                    sd.get(itemPosition).setDetremark(detRemark);


                    //itemAdapter.notifyDataSetChanged();
                    entrygrid.setAdapter(itemAdapter);
                    saleorder_entry.entrygrid.setSelection(itemPosition);
                    getSummary();
                    dialog.dismiss();
                }
            });
            dialog=builder.create();
            dialog.show();

        }
    }

    private void ShowHideSmallestQty(RelativeLayout rlsqty, RelativeLayout rlsprice) {

        boolean SmallestQtyPrice=false;
        Cursor cursor=DatabaseHelper.rawQuery("select SmallestQtyPrice from SystemSetting");
        if(cursor!=null&&cursor.getCount()!=0)
        {
            if(cursor.moveToFirst())
            {
                do {

                    SmallestQtyPrice=cursor.getInt(cursor.getColumnIndex("SmallestQtyPrice"))==1?true:false;
                }while (cursor.moveToNext());

            }

        }
        cursor.close();
        if(SmallestQtyPrice)
        {
            rlsprice.setVisibility(View.VISIBLE);
            rlsqty.setVisibility(View.VISIBLE);
        }
        else
        {
            rlsprice.setVisibility(View.GONE);
            rlsqty.setVisibility(View.GONE);
        }
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit:
            case R.id.txtedit:
            case R.id.viewEdit:
                EditInfo();
                break;
            case R.id.delete:
            case R.id.txtDelete:
            case R.id.viewDelete:
                if(itemPosition==-1) return;
                sd.remove(itemPosition);
                for (int i = 0; i < sd.size(); i++) {
                    sd.get(i).setSr(i + 1);
                }
                getData();
                getSummary();
                itemPosition = -1;
                break;
            case R.id.delall:
            case R.id.txtDelAll:
            case R.id.viewDelAll:


                if (sd.size() > 0) {
                    AlertDialog.Builder bd=new AlertDialog.Builder(datacontext,R.style.AlertDialogTheme);
                    bd.setTitle("iStock");
                    bd.setMessage("Are you sure want to delete all stock lists?");
                    bd.setCancelable(false);
                    bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sd.clear();
                            itemAdapter = new ItemAdapter(saleorder_entry.this,"SaleOrder");//added by YLT
                            entrygrid.setAdapter(itemAdapter);
                            String tax="Tax"+(getTax()>0?"( "+getTax()+"% )":"");
                            txttax.setText(tax);
                            txtfoc.setText("0.00");
                            sh.get(0).setIstemdis_amount(0.0);
                            sh.get(0).setTax_per(getTax());
                            sh.get(0).setTax_amount(0.0);
                            sh.get(0).setPaid_amount(0);
                            sh.get(0).setDiscount_per(0);
                            sh.get(0).setDiscount(0);
                            getSummary();
                            itemPosition = -1;
                            dialog.dismiss();
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
                break;
            case R.id.imgLogout:
                logout=true;
                LogOut();
                break;
            case R.id.imgconfirm:
            case R.id.txtConfirm:
            case R.id.viewConfirm:
                if(saleorder_entry.sd.size()>0) {
                    comfirm = true;
                    voucherConfirm();
                }
                else
               {
                   AlertDialog.Builder bd= new AlertDialog.Builder(saleorder_entry.this,R.style.AlertDialogTheme);
                    bd.setTitle("iStock");
                    bd.setMessage("No data to Confirm!");
                    bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    bd.create().show();

                }


                break;
            case R.id.back:
            case R.id.txtBack:
            case R.id.viewBack:


                if(!comfirm && sd.size()>0)
                {
                    Context context=this;
                    AlertDialog.Builder bd=new AlertDialog.Builder(this,R.style.AlertDialogTheme);
                    bd.setTitle("iStock");
                    bd.setMessage("Do you Comfirm Voucher? if you do not comfirm,you lost your data");
                    bd.setCancelable(false);
                    bd.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            voucherConfirm();
                            msg.dismiss();
                        }
                    });
                    bd.setNegativeButton("N0", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            CleanGarbage();
                            intent = new Intent(context, frmsalelist.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    bd.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            msg.dismiss();
                        }
                    });
                    msg=bd.create();
                    msg.show();
                }
                else
                {        CleanGarbage();
                    intent = new Intent(this, frmsaleorderlist.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.imgPrintSelection:
                ShowPrintSelection();
                break;
            case R.id.imgHeader:
                selected_townshipid=-1;
                selected_custgroupid=-1;

                AlertDialog.Builder builder=new AlertDialog.Builder(saleorder_entry.this);
                View view=getLayoutInflater().inflate(R.layout.headerinfo,null);
                builder.setCancelable(false);
                builder.setView(view);
                RelativeLayout rlCustGroup=view.findViewById(R.id.rlCustGroup);
                RelativeLayout rlTownship=view.findViewById(R.id.rlTownship);
                RelativeLayout rlLocatin=view.findViewById(R.id.rlLocation);
                /*
                RelativeLayout rlsalesmen=view.findViewById(R.id.rlsalesmen);
                btncustgroup=view.findViewById(R.id.btnCustGroup);
                btntownship=view.findViewById(R.id.btnTownship);
                btncustomer=view.findViewById(R.id.btnCustomer);
                btnStlocation=view.findViewById(R.id.location);
                //btnSalesmen=view.findViewById(R.id.salesmen);
                btncustadd=view.findViewById(R.id.imgAddCustomer);

                chkDeliver=view.findViewById(R.id.chkToDeliver); //added by YLT
                chkDeliver.setVisibility(View.GONE);//added by YLT

               // TextView lblToDeliver=findViewById(R.id.lblToDeliver);//added by YLT
               // lblToDeliver.setVisibility(View.GONE);//added by YLT

                if(!use_salesperson)
                {
                    rlsalesmen.setVisibility(View.GONE);
                }
                else {
                    rlsalesmen.setVisibility(View.VISIBLE);
                }

                 */
                if(!use_customergroup)
                {
                    rlCustGroup.setVisibility(View.GONE);

                }
                if(!use_township)
                {
                    rlTownship.setVisibility(View.GONE);

                }
//not select_location in sale entry modified by ABBP
                if(use_location)
                {
                    if(frmlogin.select_location == 0){

                        rlLocatin.setEnabled(false);
                        //btnStlocation.setEnabled(false);
                    }
                    else{

                        rlLocatin.setEnabled(true);
                       // btnStlocation.setEnabled(true);
                    }
                }else{
                    rlLocatin.setVisibility(View.GONE);
                }
                //not select_customer in sale entry modified by ABBP
                if(frmlogin.select_customer == 0){
                    //btncustomer.setEnabled(false);
                }
                TextView txtinvoiceNo=view.findViewById(R.id.txtInvoiceNo);
                txtinvoiceNo.setText(sh.get(0).getInvoice_no()=="NULL"?"":sh.get(0).getInvoice_no());


                //add headremark in header
                headremark=view.findViewById(R.id.txtheadremark);
                headremark.setText(sh.get(0).getHeadremark()=="NULL"?"":sh.get(0).getHeadremark());
/*
//salesmen selcet to btn
                if(SaleVouSalesmen.size()>0){
                    String salesmen="";
                    if(SaleVouSalesmen.size()>4){

                        for(int i=0;i<4;i++) {

                            if(i!=3){
                                salesmen+=SaleVouSalesmen.get(i).getSalesmen_Name()+",";
                            }
                            else {
                                salesmen+=SaleVouSalesmen.get(i).getSalesmen_Name()+",...";
                            }

                        }

                    }else {
                        for(int i=0;i<SaleVouSalesmen.size();i++){
                            if(i!=SaleVouSalesmen.size()-1){
                                salesmen+=SaleVouSalesmen.get(i).getSalesmen_Name()+",";
                            }else {
                                salesmen+=SaleVouSalesmen.get(i).getSalesmen_Name();
                            }
                        }
                    }

                    btnSalesmen.setText(salesmen);
                }

 */


                ImageButton btnsave=view.findViewById(R.id.imgSave);
/*
                btncustgroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ChangeHeader("Customer Group",btncustgroup,btnpaytype);
                    }
                });
                btnSalesmen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        ChangeHeader("Salesmen",btnSalesmen,btnpaytype);
                    }
                });
                btntownship.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ChangeHeader("Township",btntownship,btnpaytype);
                    }
                });

                btncustomer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ChangeHeader("Customer",btncustomer,btnpaytype);
                    }
                });

                btnpaytype=view.findViewById(R.id.btnpaytype);
                btnpaytype.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ChangeHeader("Payment Type",btnpaytype,btnpaytype);

                    }
                });

                Button btnlocation=view.findViewById(R.id.location);

                btnlocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ChangeHeader("Location",btnlocation,btnpaytype);

                    }
                });


 */

                btnsave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        invoice_no=txtinvoiceNo.getText().toString().trim().isEmpty()?"NULL":txtinvoiceNo.getText().toString().trim();
                        sh.get(0).setInvoice_no(invoice_no);
                        headRemark=headremark.getText().toString().trim().isEmpty()?"NULL":headremark.getText().toString().trim();
                        sh.get(0).setHeadremark(headRemark);
                        defloc=1;
                        if(isCreditcustomer)
                        {
                            GetCustomerOutstand(sh.get(0).getCustomerid());
                        }
                        else
                        {
                            txtoutstand.setText("0");
                        }
                        dialog.dismiss();
                    }
                });
                /*
                ArrayList<String> id=new ArrayList<>();
                ArrayList<Button> btn=new ArrayList<>();

                btn.add(btncustgroup);
                btn.add(btntownship);
                btn.add(btncustomer);
                btn.add(btnlocation);
                btn.add(btnpaytype);


                id.add(String.valueOf(sh.get(0).getCustomerid()));
                id.add(String.valueOf(sh.get(0).getLocationid()));
                id.add(String.valueOf(sh.get(0).getPay_type()));


                InitializeHeader(id,btn);


                 */
                dialog=builder.create();
                dialog.show();


                break;
//not change_date in sale entry txtdate modified by ABBP
            case R.id.txtdate:

                if(frmlogin.change_date == 0){
                    txtdate.setEnabled(false);
                }else {
                    ChangeVouDate();
                }
                break;

        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        itemPosition=position;
        EditInfo();
        return false;
    }


    public class addCust extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.show();
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
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
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

            return response.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String msg=s;
            pb.dismiss();
            try {

                if(msg.equals("Addding New Customer is Fail!!"))
                { AlertDialog.Builder b = new AlertDialog.Builder(saleorder_entry.this,R.style.AlertDialogTheme);
                    b.setTitle("iStock");
                    if(allcustomer)
                    {
                        msg="Customer Downloading Fail!!";
                    }
                    b.setMessage(msg);
                    b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            comfirmMsg.dismiss();

                        }
                    });
                    comfirmMsg=b.create();
                    comfirmMsg.show();

                }
                else
                {
                    if(da!=null) {
                        da.dismiss();
                    }
                    String data=s;
                    AlertDialog.Builder b = new AlertDialog.Builder(saleorder_entry.this,R.style.AlertDialogTheme);
                    b.setTitle("iStock");
                    b.setMessage("Addding New Customer is successful!");
                    b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            comfirmMsg.dismiss();
                            DownloadingCustomer(data);
                        }
                    });
                    comfirmMsg=b.create();
                    if(allcustomer)
                    {
                        DownloadingCustomer(data);
                    }
                    else {
                        comfirmMsg.show();
                    }
                }

            }
            catch (Exception ee)
            {
                pb.dismiss();
                dialog.dismiss();

            }
        }
    }

    private void DownloadingCustomer(String response) {
        JSONArray jarr = null;
        try
        {
            AlertDialog.Builder bdProgress=new AlertDialog.Builder(this,R.style.AlertDialogTheme);
            View view=getLayoutInflater().inflate(R.layout.downloadprocess,null);
            pbDownload=view.findViewById(R.id.progressDownload);
            txtProgress=view.findViewById(R.id.txtProgress);
            txtTable=view.findViewById(R.id.txtTable);
            txtTable.setText("Customer");

            jarr = new JSONArray(response);
            data=jarr.getJSONObject(0).getJSONArray("data");
            cust=data.getJSONObject(0).getJSONArray("customer");
            txtProgress.setText("0/"+cust.length());
            pbDownload.setMax(cust.length());
            bdProgress.setView(view);
            bdProgress.setCancelable(false);
            downloadAlert=bdProgress.create();
            context=this;
            downloadAlert.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            InsertCustomerData();
                            downloadAlert.dismiss();
                        }
                    }).start();
                }
            });

            downloadAlert.show();
        }
        catch (Exception ee)
        {
            Toast.makeText(context,ee.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void InsertCustomerData() {
        try{
            JSONArray township=data.getJSONObject(0).getJSONArray("Township");
            for(int i=0;i<township.length();i++){
                JSONObject jsonObject=township.getJSONObject(i);
                long townshipid=jsonObject.optLong("townshipid",0);
                String townshipname=jsonObject.optString("townshipname","null");
                String townshipcode=jsonObject.optString("townshipcode","null");
                DatabaseHelper.execute("delete from Township where townshipid="+townshipid);
                sqlstring="insert into Township (townshipid,townshipname,townshipcode) values("+townshipid+",'"+townshipname+"','"+townshipcode+"')";
                DatabaseHelper.execute(sqlstring);
            }


            JSONArray customerGroup=data.getJSONObject(0).getJSONArray("CustomerGroup");
            for(int i=0;i<customerGroup.length();i++){
                JSONObject jsonObject=customerGroup.getJSONObject(i);
                long custgroupid=jsonObject.optLong("custgroupid",0);
                String custgroupname=jsonObject.optString("custgroupname","null");
                String custgroupcode=jsonObject.optString("custgroupcode","null");
                DatabaseHelper.execute("delete from CustomerGroup where custgroupid="+custgroupid);
                sqlstring="insert into CustomerGroup (custgroupid,custgroupname,custgroupcode) values("+custgroupid+",'"+custgroupname+"','"+custgroupcode+"')";
                DatabaseHelper.execute(sqlstring);
            }
        }catch (Exception ee){

        }
        try {
            for ( custcount = 0; custcount < cust.length(); custcount++) {
                JSONObject custobj = cust.getJSONObject(custcount);
                long customerid = custobj.getLong("customerid");
                String customername = custobj.optString("customername", "null");
                String customercode = custobj.optString("customercode", "null");
                int credit = custobj.optBoolean("credit", false) == true ? 1 : 0;
                long townshipid = custobj.getLong("Townshipid");
                String townshipname = custobj.optString("Townshipname", "null");
                String townshipcode = custobj.optString("TownshipCode", "null");
                long CustGroupCodeid = custobj.getLong("CustGroupID");
                String CustGroupCodeName = custobj.optString("CustGroupname", "null");
                String CustGroupCodeCode = custobj.optString("CustGroupCode", "null");
                int pricelevel = custobj.optInt("pricelevel", 0);
                double custdis = custobj.optDouble("Custdiscount", 0);
                int due_in_days = custobj.optInt("due_in_days", 0);
                double credit_limit = custobj.optDouble("credit_limit", 0);
                DatabaseHelper.execute("delete from Customer where customerid="+customerid);
                sqlstring = "insert into Customer(customerid,customer_name,customer_code,credit,CustGroupID,CustGroupname,CustGroupCode,Townshipid,Townshipname,TownshipCode,pricelevel,Custdiscount,due_in_days,credit_limit)" +
                        " values(" + customerid + ",'" + customername + "','" + customercode + "'," + credit + "," +
                        CustGroupCodeid + ",'" + CustGroupCodeName + "','" + CustGroupCodeCode + "'," +
                        townshipid + ",'" + townshipname + "','" + townshipcode + "'," + pricelevel + "," + custdis + "," + due_in_days + "," + credit_limit + ")";
                DatabaseHelper.execute(sqlstring);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtProgress.setText((custcount+1)+"/"+cust.length());
                        pbDownload.setProgress(custcount+1);
                    }
                });
                Thread.sleep(3);
            }
        }
        catch (Exception ee)
        {
            Toast.makeText(context,ee.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void GetSpecialPrice(int position,double unit_qty,int editUnit_type) {
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
            String sql="select sale_price,price_level from S_SalePrice where code="+saleorder_entry.sd.get(position).getCode()+
                    " and unit_type="+editUnit_type+" and ( "+
                    unit_qty+" between min_qty and max_qty)";
            cursor=DatabaseHelper.rawQuery(sql);

            if (cursor != null && cursor.getCount() != 0)
            {
                if(cursor.moveToFirst())
                {
                    do {
                        double sale_price=cursor.getDouble(cursor.getColumnIndex("Sale_Price"));
                        level=cursor.getInt(cursor.getColumnIndex("price_level"));
                        String price_level=level==0?"SP":level==1?"SP1":level==2?"SP2":"SP3";
                        saleorder_entry.sd.get(position).setSale_price(sale_price);
                        saleorder_entry.sd.get(position).setPriceLevel(price_level);
                        txtChangePrice.setText(String.valueOf(sale_price));
                    }while (cursor.moveToNext());

                }

            }
            else
            {
                level=0;
                saleorder_entry.sd.get(position).setSale_price(getSalePrice("SP",position));
                saleorder_entry.sd.get(position).setPriceLevel("SP");
                txtChangePrice.setText(String.valueOf(getSalePrice("SP",position)));
            }
            cursor.close();
            int row_index=(int)level;
            pad = new priceLevelAdapter(saleorder_entry.this, itemPosition, txtChangePrice, txtChangeQty, txtamt,row_index);
            rcvSP.setAdapter(pad);
            GridLayoutManager gridPricelevel = new GridLayoutManager(getApplicationContext(), 4);
            rcvSP.setLayoutManager(gridPricelevel);
            getSummary();
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
        String sqlString="select uc.unit_type,code,description,"+level+",smallest_unit_qty,unitname,unitshort,CalNoTax from Usr_Code uc " +
                " where code="+saleorder_entry.sd.get(itemposistion).getCode()+" and unit_type="+editUnit_type;
        Cursor cursor=DatabaseHelper.rawQuery(sqlString);
        if(cursor!=null&&cursor.getCount()!=0)
        {
            if(cursor.moveToFirst())
            {
                do {
                    switch (pricelevel)
                    {
                        case "SP":
                            level="sale_price";
                            break;
                        case "SP1":
                            level="sale_price1";
                            break;
                        case "SP2":
                            level="sale_price2";
                            break;
                        case "SP3":
                            level="sale_price3";
                            break;
                    }
                    sale_price=cursor.getDouble(cursor.getColumnIndex(level));

                }while (cursor.moveToNext());


            }

        }
        cursor.close();
        return sale_price;

    }

    public class SaveData extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.show();
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
            pb.dismiss();
            try {
                if(s.equals("") || s.isEmpty()) {
                    ConfirmedTranid= Long.parseLong("0");
                }
                else
                {
                    ConfirmedTranid = Long.parseLong(s);
                }
                AlertDialog.Builder b = new AlertDialog.Builder(saleorder_entry.this);
                b.setTitle("iStock");
                if(ConfirmedTranid>0)
                {
                    b.setMessage("Confirming Successfully");
                    ConfirmedTranid = Long.parseLong(s);
                }
                else
                {
                    b.setMessage("Confirming unSuccessfully");
                    ConfirmedTranid=Long.parseLong("0");

                }
                b.setCancelable(false);
                b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        pb.dismiss();
                        dialog.dismiss();
                        if(ConfirmedTranid.toString()!="0")
                        {
                            billprintcount=0;
                            PrintVoucher(ConfirmedTranid);
                        }
                    }
                });

                dialog=b.create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface alert) {
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);

                    }
                });
                dialog.show();

            }
            catch (Exception ee)
            {
                pb.dismiss();
                dialog.dismiss();
            }
        }
    }

}
