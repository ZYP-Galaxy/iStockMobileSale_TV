package com.abbp.istockmobilesalenew;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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

public class frmsalelist extends AppCompatActivity implements View.OnClickListener {
    public static String url;
    private JsonArrayRequest request;
    public static RequestQueue requestQueue;
    public static ArrayList<salelist> salelists = new ArrayList<>();
    public static SharedPreferences sh_ip,sh_port;
    RecyclerView rcv;
    public static ProgressDialog pb;
    TextView txtdate;
    public static Date fdate,tdate;
    List<Calendar> calendars = new ArrayList<>();
    public static DateFormat dateFormat,griddateformat;
    String filterString="and date between ";
    ImageButton imgFilterClear,imgAdd,imgEdit,imgDelete,filtermenu;
    public static saleListAdp adp;
    public static TextView txtCount,txtUsername,txtTotal;
    public static double total;
    public static boolean use_Category2;
    Button selectfilter;
    AlertDialog da=null;
    AlertDialog msg;
    int filterV=0;
    public static Context listcontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmsalelist);
        listcontext=frmsalelist.this;
        sh_ip=getSharedPreferences("ip",MODE_PRIVATE);
        sh_port=getSharedPreferences("port",MODE_PRIVATE);
        dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        griddateformat=new SimpleDateFormat("dd/MM/yyyy");
        fdate=new Date();
        tdate=new Date();
        txtCount=findViewById(R.id.txtCount);
        txtUsername=findViewById(R.id.txtUsername);
        txtUsername.setText(frmlogin.username);
        txtCount.setText("0");
        txtTotal=(TextView)findViewById(R.id.txtTotal);
        txtTotal.setText("0");
        FilterCustomer.ccid=-1;
        FilterUser.uid=-1;
        FilterLocation.locid=-1;
        getSystemSetting();
        setUI();
        BindingData();

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()== R.id.add) {
            Intent intent=new Intent(frmsalelist.this,sale_entry.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    private void setUI() {
        rcv= findViewById(R.id.rcvsaleList);
        pb=new ProgressDialog(frmsalelist.this,R.style.AlertDialogTheme);
        pb.setTitle("Downloading");
        pb.setMessage("Please Wait...");
        pb.setCancelable(true);
        pb.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pb.setIndeterminate(true);
        txtdate=findViewById(R.id.txtdate);
        txtdate.setOnClickListener(this);
        imgFilterClear=findViewById(R.id.imgFilterClear);
        filtermenu=(ImageButton) findViewById(R.id.filtermenu);
        selectfilter=(Button)findViewById(R.id.selectfilter);
        imgFilterClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtdate.setText("Today");
                selectfilter.setText("Choose");
                FilterCustomer.ccid=-1;
                FilterUser.uid=-1;
                FilterLocation.locid=-1;
                FilterBrand.uid=-1;
                fdate = new Date();
                tdate = new Date();
                BindingData();

            }
        });

        findViewById(R.id.imgAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(frmsalelist.this,sale_entry.class);
                startActivity(intent);
                finish();

            }
        });
        findViewById(R.id.imgEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.imgDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        adp=new saleListAdp(frmsalelist.this,salelists);
        rcv.setAdapter(adp);
        LinearLayoutManager lm=new LinearLayoutManager(frmsalelist.this,LinearLayoutManager.VERTICAL,false);
        rcv.setLayoutManager(lm);

        filtermenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showfiltermenu();
            }
        });
//Added by abbp sale list filter on 12/7/2019
        selectfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectfilter.getText().toString().trim().equals("Choose Customer") || (filterV==1 && !selectfilter.getText().toString().trim().equals("Choose User") && !selectfilter.getText().toString().trim().equals("Choose") && !selectfilter.getText().toString().trim().equals("Choose Location") && !selectfilter.getText().toString().trim().equals("Choose Brand"))){
                    filterV=1;
                    selecter("Customer",selectfilter);
                }else if(selectfilter.getText().toString().trim().equals("Choose User") || (filterV==2 && !selectfilter.getText().toString().trim().equals("Choose Customer") && !selectfilter.getText().toString().trim().equals("Choose") && !selectfilter.getText().toString().trim().equals("Choose Location") && !selectfilter.getText().toString().trim().equals("Choose Brand"))){
                    filterV=2;
                    selecter("User",selectfilter);
                }else if(selectfilter.getText().toString().trim().equals("Choose Location") || (filterV==3 && !selectfilter.getText().toString().trim().equals("Choose User") && !selectfilter.getText().toString().trim().equals("Choose") && !selectfilter.getText().toString().trim().equals("Choose Customer") && !selectfilter.getText().toString().trim().equals("Choose Brand"))) {
                    filterV=3;
                    selecter("Location",selectfilter);
                }else if(selectfilter.getText().toString().trim().equals("Choose Brand") || (filterV==4 && !selectfilter.getText().toString().trim().equals("Choose Location") && !selectfilter.getText().toString().trim().equals("Choose") && !selectfilter.getText().toString().trim().equals("Choose Customer"))) {
                    filterV=4;
                    selecter("Brand",selectfilter);



                }else if(selectfilter.getText().toString().trim().equals("Choose")){
                        AlertDialog.Builder bd=new AlertDialog.Builder(frmsalelist.this,R.style.AlertDialogTheme);
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
            final ArrayList<customer> customers = new ArrayList<>();
            final ArrayList<user>users=new ArrayList<>();
            final ArrayList<Location>locs=new ArrayList<>();
            final ArrayList<brand> brands = new ArrayList<>();
            Cursor cursor = null;
            AlertDialog.Builder bd = new AlertDialog.Builder(frmsalelist.this,R.style.AlertDialogTheme);
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

                        AlertDialog.Builder searchBuilder = new AlertDialog.Builder(frmsalelist.this,R.style.AlertDialogTheme);
                        View view = getLayoutInflater().inflate(R.layout.searchbox, null);
                        searchBuilder.setView(view);
                        final EditText etdSearch = view.findViewById(R.id.etdSearch);
                        ImageButton btnSearch = view.findViewById(R.id.imgOK);
                        btnSearch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!etdSearch.getText().toString().isEmpty()) {
                                    switch (name) {
                                        case "Customer":

                                            ArrayList<customer> filteredcustomer = new ArrayList<>();

                                            for (customer item : customers) {
                                                if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                    filteredcustomer.add(item);
                                                }
                                            }
                                            FilterCustomer ca = new FilterCustomer(frmsalelist.this, filteredcustomer, btn, da);
                                            rv.setAdapter(ca);
                                            GridLayoutManager gridLayoutManagerCust = new GridLayoutManager(getApplicationContext(), 4);
                                            rv.setLayoutManager(gridLayoutManagerCust);
                                            break;
                                        case "User":

                                            ArrayList<user> filtereduser = new ArrayList<>();

                                            for (user item : users) {
                                                if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                    filtereduser.add(item);
                                                }
                                            }
                                            FilterUser us = new FilterUser(frmsalelist.this, filtereduser, btn, da);
                                            rv.setAdapter(us);
                                            GridLayoutManager gridLayoutManagerUser = new GridLayoutManager(getApplicationContext(), 4);
                                            rv.setLayoutManager(gridLayoutManagerUser);
                                            break;
                                        case "Location":
                                            ArrayList<Location> filteredloc = new ArrayList<>();

                                            for (Location item : locs) {
                                                if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                    filteredloc.add(item);
                                                }
                                            }
                                            FilterLocation loc = new FilterLocation(frmsalelist.this, filteredloc, btn, da);
                                            rv.setAdapter(loc);
                                            GridLayoutManager gridLayoutManagerLoc = new GridLayoutManager(getApplicationContext(), 4);
                                            rv.setLayoutManager(gridLayoutManagerLoc);
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
                case "Customer":
                    sqlString = "select customerid,customer_code,customer_name,credit,Custdiscount,due_in_days,credit_limit from Customer ";
                    cursor = DatabaseHelper.rawQuery(sqlString);
                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                long customerid = cursor.getLong(cursor.getColumnIndex("customerid"));
                                String customername = cursor.getString(cursor.getColumnIndex("customer_name"));
                                String customercode = cursor.getString(cursor.getColumnIndex("customer_code"));
                                boolean credit = cursor.getInt(cursor.getColumnIndex("credit")) == 1 ? true : false;
                                double custdis=cursor.getInt(cursor.getColumnIndex("Custdiscount"));
                                int due_in_days=cursor.getInt(cursor.getColumnIndex("due_in_days"));
                                double credit_limit=cursor.getDouble(cursor.getColumnIndex("credit_limit"));
                                customers.add(new customer(customerid, customername, customercode, credit,custdis,due_in_days,credit_limit));
                            } while (cursor.moveToNext());

                        }

                    } else {
                        da.dismiss();
                    }
                    cursor.close();

                    FilterCustomer cad = new FilterCustomer(frmsalelist.this, customers, btn, da);
                    rv.setAdapter(cad);
                    GridLayoutManager gridLayoutManagerCustomer = new GridLayoutManager(getApplicationContext(), 4);
                    rv.setLayoutManager(gridLayoutManagerCustomer);
                    da.show();

                    break;
                case "User":
                    sqlString="select userid,name from posuser";
                    cursor = DatabaseHelper.rawQuery(sqlString);
                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                int userid = cursor.getInt(cursor.getColumnIndex("userid"));
                                String usrname = cursor.getString(cursor.getColumnIndex("name"));
                                users.add(new user(userid,usrname));
                            } while (cursor.moveToNext());

                        }

                    } else {
                        da.dismiss();
                    }
                    cursor.close();
                    FilterUser us = new FilterUser(frmsalelist.this, users, btn, da);
                    rv.setAdapter(us);
                    GridLayoutManager gridLayoutManagerUser = new GridLayoutManager(frmsalelist.this, 4);
                    rv.setLayoutManager(gridLayoutManagerUser);
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
                                locs.add(new Location(locationid, locationname, shortname, branchid));
                            } while (cursor.moveToNext());

                        }

                    }
                    else
                    {
                        da.dismiss();
                    }
                    cursor.close();

                    FilterLocation lad = new FilterLocation(frmsalelist.this, locs, btn, da);
                    rv.setAdapter(lad);
                    GridLayoutManager gridLayoutManagerLocation = new GridLayoutManager(getApplicationContext(), 4);
                    rv.setLayoutManager(gridLayoutManagerLocation);
                    da.show();
                    break;



                case "Brand":
                    cursor = DatabaseHelper.DistinctCategorySelectQuery("Usr_Code",new String[]{"BrandID","BrandName"},"BrandName");

                    if (cursor != null && cursor.getCount() != 0) {
                        while (cursor.moveToNext() && cursor.getLong(cursor.getColumnIndex("BrandID"))!=0){
                                long brandID = cursor.getLong(cursor.getColumnIndex("BrandID"));
                                String brandName = cursor.getString(cursor.getColumnIndex("BrandName"));

                                brands.add(new brand(brandID,brandName));
                            }

                        //}
//                        if (cursor.moveToFirst()) {
//                            do {
//                                long brandID = cursor.getLong(cursor.getColumnIndex("BrandID"));
//                                String brandName = cursor.getString(cursor.getColumnIndex("BrandName"));
//
//                                brands.add(new brand(brandID,brandName));
//                            } while (cursor.moveToNext());
//
//                        }

                    }
                    cursor.close();
                    FilterBrand B = new FilterBrand(frmsalelist.this,brands,btn,da);
                    rv.setAdapter(B);
                    GridLayoutManager gridLayoutManagerUserBrand= new GridLayoutManager(frmsalelist.this, 4);
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
// Added by abbp salelist filter on 12/07/2019
    private void showfiltermenu(){

        PopupMenu popup = new PopupMenu(frmsalelist.this,filtermenu);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.filterselectionmenu, popup.getMenu());

        if(!use_Category2) { //for show hide brand
            if(frmlogin.select_location==0){//Added by KLM QA-201187 30112020
                MenuItem menuitem = popup.getMenu().getItem(2);
                menuitem.setVisible(false);
            }

                MenuItem menuitem = popup.getMenu().getItem(3);
                menuitem.setVisible(false);


        }
        else{
            if(frmlogin.select_location==0){//Added by KLM QA-201187 30112020
                MenuItem menuitem = popup.getMenu().getItem(2);
                menuitem.setVisible(false);
            }
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.customermenu:
                        selectfilter.setText("Choose Customer");
                        return true;
                    case R.id.usermenu:
                        selectfilter.setText("Choose User");
                        return true;
                    case R.id.locmenu:
                        selectfilter.setText("Choose Location");
                        return true;
                    case R.id.brandmenu:
                        selectfilter.setText("Choose Brand");
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();//showing popup menu
    }

    public static void BindingData() {
        try {

                DateFormat dateFormat;
                dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                if(salelists.size()>0)salelists.clear();
                if (frmlogin.UseOffline == 1) {

                String sqlstring = "select sh.tranid,ifnull(invoice_no,docid) docid,sh.date,p.short user_short,c.customer_name,pt.short pay_type,sh.net_amount " +
                        " from Sale_Head_Main sh join Posuser p on p.userid=sh.userid join customer c on c.customerid=sh.customerid join Payment_Type pt on pt.pay_type=sh.pay_type"+
                         " ORDER BY DATE ASC,sh.tranid desc,docid ";//added sh.tranid by KLM
                Cursor cursor = DatabaseHelper.rawQuery(sqlstring);
                if (cursor != null && cursor.getCount() != 0) {
                    if (cursor.moveToFirst()) {
                        do {

                            int tranid = cursor.getInt(cursor.getColumnIndex("tranid"));
                            String docid = cursor.getString(cursor.getColumnIndex("docid"));
                            String pay_type = cursor.getString(cursor.getColumnIndex("pay_type"));
                            String dateStr = cursor.getString(cursor.getColumnIndex("date"));

                            if(dateStr.split("/").length>1) {
                                String[] ds = dateStr.split("/");
                                ds[0] = ds[0].length() >1 ? ds[0] : "0" + ds[0];
                                ds[1] = ds[1].length()>1 ? ds[1] : "0" + ds[1];
                                dateStr = ds[1] + "/" + ds[0] + "/" + ds[2];
                            }
                            else
                            {
                                dateStr=new SimpleDateFormat("dd/MM/yyyy").format(dateFormat.parse(dateStr));
                            }
                            double net_amount = cursor.getDouble(cursor.getColumnIndex("net_amount"));
                            total += net_amount;
                            String usershort = cursor.getString(cursor.getColumnIndex("user_short"));
                            String customer_name = cursor.getString(cursor.getColumnIndex("customer_name"));
                            salelists.add(new salelist(tranid, dateStr, docid, pay_type, "MMK", net_amount, usershort, customer_name));

                        } while (cursor.moveToNext());

                    }

                }
                cursor.close();
                adp.notifyDataSetChanged();
                txtCount.setText(String.valueOf(salelists.size()));
                txtTotal.setText(String.format("%,." + frmmain.price_places + "f", total));
                pb.dismiss();

            } else {
                GetData();
            }
        }
        catch (Exception ee)
        {
            Toast.makeText(listcontext, ee.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(frmsalelist.this,frmmain.class);
        startActivity(intent);
        finish();
    }

    public static void GetData() {
        try {

            pb.show();
            salelists.clear();
            total=0.0;
            String ip = sh_ip.getString("ip", "empty");
            String port = sh_port.getString("port", "empty");
            url = "http://"+ip+":"+port+"/api/DataSync/GetHeader'?userid="+frmlogin.LoginUserid+"&uid="+FilterUser.uid+"&fdate="+dateFormat.format(fdate)+"&tdate="+dateFormat.format(tdate)+"&ccid="+FilterCustomer.ccid+"&locid="+FilterLocation.locid+"&brandid="+FilterBrand.uid+"&language="+frmlogin.Font_Language;
            requestQueue = Volley.newRequestQueue(listcontext);
            final Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONArray jarr=new JSONArray(response);
                        for(int i=0;i<jarr.length();i++)
                        {
                            JSONObject obj=jarr.getJSONObject(i);
                            int tranid=obj.getInt("tranid");
                            String docid=obj.getString("docid");
                            String currency=obj.getString("currency");
                            String pay_type=obj.getString("pay_type");
                            String dateStr=  obj.getString("date");
                            double net_amount=obj.getDouble("net_amount");
                            total+=net_amount;
                            String usershort=obj.getString("usershort");
                            String customer_name=obj.getString("customer_name");
                            salelists.add(new salelist(tranid,dateStr,docid,pay_type,currency,net_amount,usershort,customer_name));


                        }
                        adp.notifyDataSetChanged();
                        txtCount.setText(String.valueOf(salelists.size()));
                        txtTotal.setText(String.format("%,."+frmmain.price_places+"f",total));
                        pb.dismiss();
                    } catch (JSONException e) {

                        pb.dismiss();
                        salelists.clear();
                        adp.notifyDataSetChanged();
                        txtCount.setText(String.valueOf(salelists.size()));
                        txtTotal.setText("0.0");

                    }
                }


            };

            final Response.ErrorListener error = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pb.dismiss();
                    salelists.clear();
                    adp.notifyDataSetChanged();
                    txtCount.setText(String.valueOf(salelists.size()));
                    txtTotal.setText("0.0");

                }
            };
            StringRequest req = new StringRequest(Request.Method.GET, url, listener, error);
            requestQueue.add(req);



        }
        catch (Exception ee)
        {
            Toast.makeText(listcontext, ee.getMessage(), Toast.LENGTH_LONG).show();
            pb.dismiss();

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.txtdate:
                ShowDateFilter();
                break;
        }
    }

    private void ShowDateFilter() {
        LayoutInflater inflater = (LayoutInflater) frmsalelist.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.daterangefilterpopup,null);
        float density=frmsalelist.this.getResources().getDisplayMetrics().density;
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
                BindingData();

            }
        });
    }
}

