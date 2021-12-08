package com.abbp.istockmobilesalenew;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//extends AppCompatActivity
public class frmstockstatuslist extends AppCompatActivity {

    CheckBox chkSPorder;
    CheckBox chkRDeliver;
    CheckBox chkInactive;
    CheckBox chkPlus;
    CheckBox chkMinus;
    CheckBox chkZero;
    public static  String Specialfilter;
    public static String url;
    public static RequestQueue requestQueue;
    EditText txtSearchCode;
    EditText txtDescription;
    RecyclerView rcv;
    TextView txtdate;
    TextView txtchooseLocation;
    public static Date fdate,tdate;
    List<Calendar> calendars = new ArrayList<>();
    public static ArrayList<stockstatuslist> stockstatuslists = new ArrayList<>();
    public static ProgressDialog pb;
    public static DateFormat dateFormat,griddateformat;
    ImageButton imgFilterClear,imgAdd,imgEdit,imgDelete,filtermenu,refresh,specialfiltermenu;
    public static boolean use_Category2;
    String filterString="and date between ";
    public static stockstatuslistAdp adp;
    Button selectfilter;
    Button specialSelectfilter;
    Button btnLocation;
    AlertDialog da=null;
    AlertDialog msg;
    int filterV=0;
     boolean checkedRdeliver=false;
     boolean checkedInactive=false;
     boolean checkedSPOrder=false;
     boolean checkedPlus=false;
     boolean checkedMinus=false;
     boolean checkedZero=false;
    public static String PlusValue="";
    public static String MinusValue="";
    public static String ZeroValue="";


    public static SharedPreferences sh_ip,sh_port;
    public static Context listcontext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmstockstatuslist);
        listcontext=frmstockstatuslist.this;
        sh_ip=getSharedPreferences("ip",MODE_PRIVATE);
        sh_port=getSharedPreferences("port",MODE_PRIVATE);
        dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        griddateformat=new SimpleDateFormat("dd/MM/yyyy");
        fdate=new Date();
        tdate=new Date();
        getSystemSetting();
        setUI();
        if( stockstatuslists.size()>0)
        {
            stockstatuslists.clear();
        }

    }

    private void getSystemSetting() {
        Cursor cursor=DatabaseHelper.rawQuery("select use_Category2 from SystemSetting");
        if(cursor!=null&&cursor.getCount()!=0)
        {
           if(cursor.moveToFirst())
            {
                do {
                    use_Category2=cursor.getInt(cursor.getColumnIndex("use_Category2"))==1?true:false;

                }while (cursor.moveToNext());

            }

        }


    }
    private  void setUI()
    {

        rcv=findViewById(R.id.rcvstockstatusList );
        pb=new ProgressDialog(frmstockstatuslist.this,R.style.AlertDialogTheme);
        pb.setTitle("Downloading");
        pb.setMessage("Please Wait...");
        pb.setCancelable(true);
        pb.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pb.setIndeterminate(true);
        btnLocation=findViewById(R.id.txtchooseLocation);
        FilterLocation.locid=frmlogin.det_locationid;
        String sqlString = "select locationid,Location_Name,Location_Short,branchID from Location  where locationid="+frmlogin.det_locationid;
        Cursor cursor = DatabaseHelper.rawQuery(sqlString);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                   btnLocation.setText(cursor.getString(cursor.getColumnIndex("Location_Name")));
                } while (cursor.moveToNext());

            }
        }
        else {
            da.dismiss();
        }
        cursor.close();
        txtdate=findViewById(R.id.txtdate);
        txtdate.setOnClickListener(this::onClick);
        txtchooseLocation=findViewById(R.id.txtchooseLocation);
        txtchooseLocation.setOnClickListener(this::onClick);




        imgFilterClear=findViewById(R.id.imgFilterClear);
        filtermenu=(ImageButton) findViewById(R.id.filtermenu);
        specialfiltermenu=(ImageButton)findViewById(R.id.filterSpecial);
        txtSearchCode=findViewById(R.id.searchcode);
        txtDescription=findViewById(R.id.searchcode);
        refresh=(ImageButton)findViewById(R.id.refresh);
        adp=new stockstatuslistAdp(frmstockstatuslist.this,stockstatuslists);
        rcv.setAdapter(adp);
        LinearLayoutManager lm=new LinearLayoutManager(frmstockstatuslist.this,LinearLayoutManager.VERTICAL,false);
        rcv.setLayoutManager(lm);

        chkSPorder=findViewById(R.id.chkspOrd);
        chkRDeliver= findViewById(R.id.chkRecDel);
        chkInactive= findViewById(R.id.chkInactive);
        chkPlus= findViewById(R.id.chkPlus);
        chkMinus=findViewById(R.id.chkMinus);
        chkZero=findViewById(R.id.chkZero);

        selectfilter=(Button)findViewById(R.id.selectfilter);

        chkPlus.setChecked(true);
        chkMinus.setChecked(true);

        refresh.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showStock();
                showStockStatus();

            }
        }));

        chkRDeliver.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chkRDeliver.isChecked())
                {
                    checkedRdeliver=true;
                }
                else
                {
                    checkedRdeliver=false;
                }

            }
        }));
        chkInactive.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chkInactive.isChecked())
                {
                    checkedInactive=true;
                }
                else
                {
                    checkedInactive=false;
                }

            }
        }));
        chkSPorder.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chkSPorder.isChecked())
                {
                    checkedSPOrder=true;
                }
                else
                {
                    checkedSPOrder=false;
                }

            }
        }));
        chkMinus.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chkMinus.isChecked())
                {
                    MinusValue="<";
                }
                else
                {
                    MinusValue="";
                }

            }
        }));
        chkPlus.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chkPlus.isChecked())
                {
                    PlusValue=">";
                }
                else
                {
                    PlusValue="";
                }

            }
        }));

        chkZero.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chkZero.isChecked())
                {
                    ZeroValue="=";
                }
                else
                {
                    ZeroValue="";
                }

            }
        }));



        imgFilterClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtdate.setText("Today");
                selectfilter.setText("Choose");
                FilterCustomer.ccid=-1;
                FilterBrand.uid =-1;
                FilterUser.uid=-1;
                FilterLocation.locid=-1;
                btnLocation.setText("Choose Location");
                fdate = new Date();
                tdate = new Date();
                chkInactive.setChecked(false);
                chkRDeliver.setChecked(false);
                chkSPorder.setChecked(false);
              //  chkPlus.setChecked(false);
              //  chkMinus.setChecked(false);
                chkZero.setChecked(false);
                txtSearchCode.setText("");
                txtDescription.setText("");
                Specialfilter="";
            }
        });
        filtermenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showfiltermenu();
            }
        });

        specialfiltermenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpecialfiltermenu();
            }
        });

        selectfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectfilter.getText().toString().trim().equals("Choose Class") || (filterV==1 && selectfilter.getText().toString().trim().equals("Choose Class") && !selectfilter.getText().toString().trim().equals("Choose") && !selectfilter.getText().toString().trim().equals("Choose Class"))){
                    filterV=1;
                    selecter("Class",selectfilter);
                }else if(selectfilter.getText().toString().trim().equals("Choose Category") || (filterV==2 && !selectfilter.getText().toString().trim().equals("Choose Category") && !selectfilter.getText().toString().trim().equals("Choose") && !selectfilter.getText().toString().trim().equals("Choose Category"))){
                    filterV=2;
                    selecter("Category",selectfilter);

                }
                else if (selectfilter.getText().toString().trim().equals("Choose Brand") || (filterV==2 && !selectfilter.getText().toString().trim().equals("Choose Brand") && !selectfilter.getText().toString().trim().equals("Choose") && !selectfilter.getText().toString().trim().equals("Choose Brand")))
                {
                    filterV=3;
                    selecter("Brand",selectfilter);
                }
                else if(selectfilter.getText().toString().trim().equals("Choose")){
                    AlertDialog.Builder bd=new AlertDialog.Builder(frmstockstatuslist.this,R.style.AlertDialogTheme);
                    bd.setTitle("iStock");
                    bd.setMessage("Please select filter type! ");
                    bd.setCancelable(false);
                    bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    bd.create().show();
                }
            }
        });
    }

    private void selecter(final String name, final Button btn){
        try {
            String sqlString;
            final ArrayList<class_item> class_items = new ArrayList<>();
            final ArrayList<category> categories=new ArrayList<>();
            final ArrayList<brand> brands = new ArrayList<>();

            Cursor cursor = null;
            AlertDialog.Builder bd = new AlertDialog.Builder(frmstockstatuslist.this,R.style.AlertDialogTheme);
            View view = getLayoutInflater().inflate(R.layout.changeheadervalue, null);
            //bd.setCancelable(false);
            bd.setView(view);
            final RecyclerView rv = view.findViewById(R.id.rcvChange);
            ImageButton imgClose = view.findViewById(R.id.imgNochange);
            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    da.dismiss();
                }
            });
            EditText etdSearch = view.findViewById(R.id.etdSearch);
            ImageButton imgSearch = view.findViewById(R.id.imgSearch);
            imgSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        AlertDialog.Builder searchBuilder = new AlertDialog.Builder(frmstockstatuslist.this,R.style.AlertDialogTheme);
                        View view = getLayoutInflater().inflate(R.layout.searchbox, null);
                        searchBuilder.setView(view);
                        final EditText etdSearch = view.findViewById(R.id.etdSearch);
                        ImageButton btnSearch = view.findViewById(R.id.imgOK);
                        btnSearch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!etdSearch.getText().toString().isEmpty()) {
                                    switch (name) {
                                        case "Class":

                                            ArrayList<class_item> filteredClass = new ArrayList<>();

                                            for (class_item item : class_items) {
                                                if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                    filteredClass.add(item);
                                                }
                                            }
                                            FilterClass ca = new FilterClass(frmstockstatuslist.this, filteredClass, btn,da);
                                            rv.setAdapter(ca);
                                            GridLayoutManager gridLayoutManagerClass = new GridLayoutManager(getApplicationContext(), 4);
                                            rv.setLayoutManager(gridLayoutManagerClass);
                                            break;
                                        case "Category":

                                            ArrayList<category> filteredcategory = new ArrayList<>();

                                            for (category item : categories) {
                                                if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                    filteredcategory.add(item);
                                                }
                                            }
                                            FilterCategory us = new FilterCategory(getApplicationContext(),filteredcategory,btn,da);
                                            rv.setAdapter(us);
                                            GridLayoutManager gridLayoutManagerCategory = new GridLayoutManager(getApplicationContext(), 4);
                                            rv.setLayoutManager(gridLayoutManagerCategory);
                                            break;
                                        case "Brand":

                                            ArrayList<brand> filterbrand = new ArrayList<>();

                                            for (brand item : brands) {
                                                if (item.getBrandName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                    filterbrand.add(item);
                                                }
                                            }
                                            FilterBrand bra = new FilterBrand(getApplicationContext(),filterbrand,btn,da);
                                            rv.setAdapter(bra);
                                            GridLayoutManager gridLayoutManagerBrand = new GridLayoutManager(getApplicationContext(), 4);
                                            rv.setLayoutManager(gridLayoutManagerBrand);
                                            break;



                                    }
                                    msg.dismiss();
                                }
                            }
                        });

                        msg = searchBuilder.create();
                        msg.show();
                    } catch (Exception ee) {

                    }
                }
            });

            da = bd.create();
            switch (name) {
                case "Class":
                     cursor = DatabaseHelper.DistinctCategorySelectQuery("Usr_Code",new String[]{"class","classname"},"classname");
                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                long classid = cursor.getLong(cursor.getColumnIndex("class"));
                                String classname = cursor.getString(cursor.getColumnIndex("classname"));
                                class_items.add(new class_item(classid,classname));
                            } while (cursor.moveToNext());

                        }
                    }
                    cursor.close();

                    FilterClass cad = new FilterClass(frmstockstatuslist.this, class_items, btn, da);
                    rv.setAdapter(cad);
                    GridLayoutManager gridLayoutManagerCustomer = new GridLayoutManager(getApplicationContext(), 4);
                    rv.setLayoutManager(gridLayoutManagerCustomer);
                    da.show();

                    break;
                case "Category":
                     cursor = DatabaseHelper.DistinctCategorySelectQuery("Usr_Code",new String[]{"category","categoryname","class"},"sortcode,categoryname");

                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                long categoryid = cursor.getLong(cursor.getColumnIndex("category"));
                                String categoryname = cursor.getString(cursor.getColumnIndex("categoryname"));
                                long classid = cursor.getLong(cursor.getColumnIndex("class"));
                                categories.add(new category(categoryid, classid, categoryname));
                            } while (cursor.moveToNext());

                        }

                    }
                    cursor.close();
                    FilterCategory us = new FilterCategory(frmstockstatuslist.this,categories,btn,da);
                    rv.setAdapter(us);
                    GridLayoutManager gridLayoutManagerUser = new GridLayoutManager(frmstockstatuslist.this, 4);
                    rv.setLayoutManager(gridLayoutManagerUser);
                    da.show();
                    break;
                case "Brand":
                    cursor = DatabaseHelper.DistinctCategorySelectQuery("Usr_Code",new String[]{"BrandID","BrandName"},"BrandName");

                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                long brandID = cursor.getLong(cursor.getColumnIndex("BrandID"));
                                String brandName = cursor.getString(cursor.getColumnIndex("BrandName"));

                                brands.add(new brand(brandID,brandName));
                            } while (cursor.moveToNext());

                        }

                    }
                    cursor.close();
                    FilterBrand B = new FilterBrand(frmstockstatuslist.this,brands,btn,da);
                    rv.setAdapter(B);
                    GridLayoutManager gridLayoutManagerUserBrand= new GridLayoutManager(frmstockstatuslist.this, 4);
                    rv.setLayoutManager(gridLayoutManagerUserBrand);
                    da.show();
                    break;


            }
        }
        catch (Exception e)
        {
            da.dismiss();
        }
    }
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.txtdate:
                ShowDateFilter();
                break;
            case  R.id.txtchooseLocation:
                ShowLocation(btnLocation);
                //selecter("Choose Location",btnLocation);
                break;
          //  case R.id.refresh:
              //  showStock();
             //   break;

        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(frmstockstatuslist.this,frmmain.class);
        startActivity(intent);
        finish();
    }
    private  void showStockStatus()
    {
        try {

            pb.show();
            stockstatuslists.clear();

            String ip = sh_ip.getString("ip", "empty");
            String port = sh_port.getString("port", "empty");

                        if(chkMinus.isChecked())
                        {
                            MinusValue="<";
                        }
                        else {
                            MinusValue = "";
                        }

                                if(chkPlus.isChecked())
                                {
                                    PlusValue=">";
                                }
                                else
                                {
                                    PlusValue="";
                                }




                                if(chkZero.isChecked())
                                {
                                    ZeroValue="=";
                                }
                                else
                                {
                                    ZeroValue="";
                                }


            if(Specialfilter=="Code")
            {

                //txtSearchCode= findViewById(R.id.searchcode);
                url="http://"+ip+":"+port+"/api/DataSync/GetData'?Userid="+frmlogin.LoginUserid+"&Usr_Code="+txtSearchCode.getText().toString()+"&Description=empty"+"&LocID="+FilterLocation.locid+"&fdate="+dateFormat.format(fdate)+"&tdate="+dateFormat.format(tdate)+"&bchkInactiveStock="+checkedInactive+"&bchkRec="+checkedRdeliver+"&bchkSPorder="+checkedSPOrder+"&category="+FilterCategory.uid+ "&class_id="+FilterClass.uid+"&PlusValue="+PlusValue+"&MinusValue="+MinusValue+"&ZeroValue="+ZeroValue+"&Brand="+FilterBrand.uid;


            }
            else  if (Specialfilter=="Description")
            {
                url="http://"+ip+":"+port+"/api/DataSync/GetData'?Userid="+frmlogin.LoginUserid+"&Usr_Code=empty"+"&Description="+txtDescription.getText().toString()+"&LocID="+FilterLocation.locid+"&fdate="+dateFormat.format(fdate)+"&tdate="+dateFormat.format(tdate)+"&bchkInactiveStock="+checkedInactive+"&bchkRec="+checkedRdeliver+"&bchkSPorder="+checkedSPOrder+"&category="+FilterCategory.uid+ "&class_id="+FilterClass.uid+"&PlusValue="+PlusValue+"&MinusValue="+MinusValue+"&ZeroValue="+ZeroValue+"&Brand="+FilterBrand.uid;
            }
            else
            {
                url="http://"+ip+":"+port+"/api/DataSync/GetData'?Userid="+frmlogin.LoginUserid+"&Usr_Code=empty"+"&Description=empty"+"&LocID="+FilterLocation.locid+"&fdate="+dateFormat.format(fdate)+"&tdate="+dateFormat.format(tdate)+"&bchkInactiveStock="+checkedInactive+"&bchkRec="+checkedRdeliver+"&bchkSPorder="+checkedSPOrder+"&category="+FilterCategory.uid+ "&class_id="+FilterClass.uid+"&PlusValue="+PlusValue+"&MinusValue="+MinusValue+"&ZeroValue="+ZeroValue+"&Brand="+FilterBrand.uid;
            }


            //url="http://"+ip+":"+port+"/api/DataSync/GetData'?Userid="+frmlogin.LoginUserid+"&Usr_Code="+txtSearchCode.getText().toString()+"&Description="+txtDescription.getText().toString()+"&LocID="+FilterLocation.locid+"&fdate="+dateFormat.format(fdate)+"&tdate="+dateFormat.format(tdate)+"&bchkInactiveStock="+checkedInactive+"&bchkRec="+checkedRdeliver+"&bchkSPorder="+checkedSPOrder+"&category="+FilterCategory.uid+ "&class_id="+FilterClass.uid+"&PlusValue="+PlusValue+"&MinusValue="+MinusValue+"&ZeroValue="+ZeroValue;
            requestQueue = Volley.newRequestQueue(listcontext);
            final Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    try {
                        JSONArray jarr=new JSONArray(response);
                        for(int i=0;i<jarr.length();i++)
                        {
                            JSONObject obj=jarr.getJSONObject(i);

                            String usrcode=obj.getString("usrcode");
                            String descripiton=obj.getString("description");
                            double saleAmount=obj.optDouble("saleAmount",0);
                            String balanceAmount=obj.getString("balanceAmount");
                            String location= obj.getString("Location");
                            stockstatuslists.add(new stockstatuslist(usrcode,descripiton,saleAmount,balanceAmount,location));


                        }
                        adp.notifyDataSetChanged();
                        pb.dismiss();
                    } catch (JSONException e) {

                        pb.dismiss();
                        stockstatuslists.clear();
                        adp.notifyDataSetChanged();
                    }catch (Exception e) {

                        pb.dismiss();
                        stockstatuslists.clear();
                        adp.notifyDataSetChanged();
                    }
                }


            };

            final Response.ErrorListener error = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pb.dismiss();
                    stockstatuslists.clear();
                    adp.notifyDataSetChanged();
                }
            };
            StringRequest req = new StringRequest(Request.Method.GET, url, listener, error);
            req.setRetryPolicy(new DefaultRetryPolicy(10000000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(req);



        }
        catch (Exception ee)
        {
            Toast.makeText(listcontext, ee.getMessage(), Toast.LENGTH_LONG).show();
            pb.dismiss();

        }
    }


    private void ShowLocation (Button btn)
    {
        ArrayList<Location>locs=new ArrayList<>();
        String sqlString;
        Cursor cursor = null;
        AlertDialog.Builder bd = new AlertDialog.Builder(frmstockstatuslist.this,R.style.AlertDialogTheme);
        View view = getLayoutInflater().inflate(R.layout.changeheadervalue, null);
        bd.setCancelable(false);
        bd.setView(view);

        final RecyclerView rv = view.findViewById(R.id.rcvChange);
        ImageButton imgClose = view.findViewById(R.id.imgNochange);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                da.dismiss();
            }
        });
        EditText etdSearch = view.findViewById(R.id.etdSearch);
        ImageButton imgSearch = view.findViewById(R.id.imgSearch);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    AlertDialog.Builder searchBuilder = new AlertDialog.Builder(frmstockstatuslist.this,R.style.AlertDialogTheme);
                    View view = getLayoutInflater().inflate(R.layout.searchbox, null);
                    searchBuilder.setView(view);
                    final EditText etdSearch = view.findViewById(R.id.etdSearch);
                    ImageButton btnSearch = view.findViewById(R.id.imgOK);
                    btnSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!etdSearch.getText().toString().isEmpty()) {

                                        ArrayList<Location> filteredlocation = new ArrayList<>();

                                        for (Location item : locs) {
                                            if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                filteredlocation.add(item);
                                            }
                                        }
                                        FilterLocation ca = new FilterLocation(frmstockstatuslist.this, filteredlocation, btn,da,"frmstockstatus");
                                        rv.setAdapter(ca);

                                        GridLayoutManager gridLayoutManagerClass = new GridLayoutManager(getApplicationContext(), 4);
                                        rv.setLayoutManager(gridLayoutManagerClass);


                                msg.dismiss();
                            }
                        }
                    });

                    msg = searchBuilder.create();
                    msg.show();
                } catch (Exception ee) {

                }

            }
        });
        da=bd.create();
      //  RecyclerView rv = view.findViewById(R.id.rcvChange);
                        sqlString = "select locationid,Location_Name,Location_Short,branchID from Location ";
                        cursor = DatabaseHelper.rawQuery(sqlString);
                        if (cursor != null && cursor.getCount() != 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    long locationid = cursor.getLong(cursor.getColumnIndex("locationid"));
                                    String locationname = cursor.getString(cursor.getColumnIndex("Location_Name"));
                                    String shortname = cursor.getString(cursor.getColumnIndex("Location_Short"));
                                    long branchid = cursor.getLong(cursor.getColumnIndex("branchID"));
                                    locs.add(new Location(locationid, locationname, shortname, branchid));
                                } while (cursor.moveToNext());

                            }
                        }
                        else {
                            da.dismiss();
                        }
                 cursor.close();

        FilterLocation lad = new FilterLocation(frmstockstatuslist.this, locs, btn, da,"frmstockstatus");
        rv.setAdapter(lad);
        GridLayoutManager gridLayoutManagerLocation = new GridLayoutManager(getApplicationContext(), 4);
        rv.setLayoutManager(gridLayoutManagerLocation);
        da.show();

    }
    private void showSpecialfiltermenu(){

        PopupMenu popup = new PopupMenu(frmstockstatuslist.this,specialfiltermenu);
        popup.getMenuInflater().inflate(R.menu.filterspecialstockstatusmenu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Code:
                        Specialfilter="Code";
                        return true;
                    case R.id.Description:
                        Specialfilter="Description";
                        return true;

                    default:
                        return false;
                }
            }
        });
        popup.show();//showing popup menu
    }

    private void showfiltermenu(){

        PopupMenu popup = new PopupMenu(frmstockstatuslist.this,filtermenu);
        popup.getMenuInflater().inflate(R.menu.filterstockstatusmenu, popup.getMenu());

        if(!use_Category2) { //for show hide brand
            MenuItem menuitem = popup.getMenu().getItem(2);
            menuitem.setVisible(false);
        }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ClassMenu:
                        selectfilter.setText("Choose Class");
                        return true;
                    case R.id.CategoryMenu:
                        selectfilter.setText("Choose Category");
                        return true;
                    case R.id.BrandMenu:
                        selectfilter.setText("Choose Brand");
                        return  true;
                    default:
                        return false;
                }
            }
        });
        popup.show();//showing popup menu
    }

    private void ShowDateFilter() {
        LayoutInflater inflater = (LayoutInflater) frmstockstatuslist.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.daterangefilterpopup,null);
        float density=frmstockstatuslist.this.getResources().getDisplayMetrics().density;
        final PopupWindow pw = new PopupWindow(layout, (int)density*310, WindowManager.LayoutParams.WRAP_CONTENT, true);
        pw.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pw.setOutsideTouchable(false);
        pw.showAsDropDown(txtdate);
        ImageButton imgDone=layout.findViewById(R.id.imgDone);
        final com.applandeo.materialcalendarview.CalendarView calendarView =layout.findViewById(R.id.calendarView);
        imgDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendars=calendarView.getSelectedDates();
                if(calendars.size()>0) {
                    fdate = calendars.get(0).getTime();
                    tdate = calendars.get(calendars.size() - 1).getTime();
                }
                pw.dismiss();
                txtdate.setText(new SimpleDateFormat("dd/MM/yyyy").format(fdate)+" - "+new SimpleDateFormat("dd/MM/yyyy").format(tdate));

            }
        });
    }


}
