package com.abbp.istockmobilesalenew;

import android.app.ProgressDialog;
import android.content.Context;
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
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class frmoustandlist  extends AppCompatActivity {
    public static DateFormat dateFormat,griddateformat;
    public static SharedPreferences sh_ip,sh_port;
    public static Context listcontext;
    public static Date fdate,tdate;
    public static RequestQueue requestQueue;
    public static String url;
    public static TextView txtTotal;
    public static double total;
    public static String  fitercode;
    TextView txtChooseBranch;
    TextView txtdate;
    Button btnBranch;
    RecyclerView rcv;

    TextView txtChooseCustomer;
    Button btnCustomer;

    AlertDialog da=null;

    AlertDialog msg;
    public static ProgressDialog pb;
    public static outstandlistAdp adp;
    List<Calendar> calendars = new ArrayList<>();
    public static ArrayList<outstandlist> outstandlistslists = new ArrayList<>();
    ImageButton imgFilterClear,imgAdd,imgEdit,imgDelete,filtermenu,refresh,specialfiltermenu;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmoutstandlist);
        listcontext=frmoustandlist.this;
        sh_ip=getSharedPreferences("ip",MODE_PRIVATE);
        sh_port=getSharedPreferences("port",MODE_PRIVATE);
        dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        griddateformat=new SimpleDateFormat("dd/MM/yyyy");

        fdate=new Date();
        tdate=new Date();
        txtTotal=(TextView)findViewById(R.id.txtTotal);
        txtTotal.setText("0");
        setUI();
        if( outstandlistslists.size()>0)
        {
            outstandlistslists.clear();
        }
    }
    private void setUI()
    {
        rcv=findViewById(R.id.rcvoutstandList );
        pb=new ProgressDialog(frmoustandlist.this,R.style.AlertDialogTheme);
        pb.setTitle("Downloading");
        pb.setMessage("Please Wait...");
        pb.setCancelable(true);
        pb.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pb.setIndeterminate(true);
        adp=new outstandlistAdp(frmoustandlist.this,outstandlistslists);
        rcv.setAdapter(adp);
        LinearLayoutManager lm=new LinearLayoutManager(frmoustandlist.this,LinearLayoutManager.VERTICAL,false);
        rcv.setLayoutManager(lm);
        refresh=(ImageButton)findViewById(R.id.refresh);
        txtdate=findViewById(R.id.txtdate);
        txtdate.setOnClickListener(this::onClick);
        imgFilterClear=findViewById(R.id.imgFilterClear);

        btnBranch=findViewById(R.id.txtBranch);
        txtChooseBranch=findViewById(R.id.txtBranch);
        txtChooseBranch.setOnClickListener(this::onClick);

        btnCustomer=findViewById(R.id.txtCustomer);
        txtChooseCustomer=findViewById(R.id.txtCustomer);
        txtChooseCustomer.setOnClickListener(this::onClick);



        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowResult();
            }
        });

        imgFilterClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtdate.setText("Today");
                fdate = new Date();
                tdate = new Date();
                txtChooseBranch.setText("Choose Branch");
                txtChooseCustomer.setText("Choose Customer");
                FilterCustomer.ccid=0;
                FilterBranch.BranchiID=0;
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(frmoustandlist.this,frmmain.class);
        startActivity(intent);
        finish();
    }
    private  void ShowResult()
    {
        try {
            pb.show();
            outstandlistslists.clear();
            total=0.0;
            String ip = sh_ip.getString("ip", "empty");
            String port = sh_port.getString("port", "empty");

            url="http://"+ip+":"+port+"/api/DataSync/GetData'?Userid="+frmlogin.LoginUserid+"&fdate="+dateFormat.format(fdate)+"&tdate="+dateFormat.format(tdate)+"&CustomerID="+FilterCustomer.ccid+"&BranchID="+FilterBranch.BranchiID+"&TownshipID=0"+"&CustomerGroupID=0";
            requestQueue = Volley.newRequestQueue(listcontext);
            final Response.Listener<String> Listener= new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONArray jarr = new JSONArray(response);
                        for (int i = 0; i < jarr.length(); i++) {
                            JSONObject obj = jarr.getJSONObject(i);

                            String Customer = obj.getString("Customer");
                            String Currency = obj.getString("Currency");
                            double Opening = obj.getDouble("Opening");
                            double Sale = obj.getDouble("Sale");
                            double ReturnIn = obj.getDouble("ReturnIn");
                            double Discount = obj.getDouble("Discount");
                            double Paid = obj.getDouble("Paid");
                            double Balance = obj.getDouble("Balance");
                            total+=Balance;
                            outstandlistslists.add(new outstandlist(Customer, Currency, Opening, Sale, ReturnIn,Discount,Paid,Balance));


                        }
                        adp.notifyDataSetChanged();
                       txtTotal.setText(String.format("%,."+frmmain.price_places+"f",total));
                        pb.dismiss();
                    } catch (JSONException e) {

                        pb.dismiss();
                        outstandlistslists.clear();
                        adp.notifyDataSetChanged();
                    }catch (Exception e) {

                        pb.dismiss();
                        outstandlistslists.clear();
                        adp.notifyDataSetChanged();
                    }
                }
            };
            final Response.ErrorListener error = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pb.dismiss();
                    outstandlistslists.clear();
                    adp.notifyDataSetChanged();
                }
            };
            StringRequest req = new StringRequest(Request.Method.GET, url, Listener, error);
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
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.txtdate:
                ShowDateFilter();
                break;
            case  R.id.txtBranch:
                ShowBranch(btnBranch);
                break;
            case  R.id.txtCustomer:
                ShowCustomer(btnCustomer);
                break;
        }
    }
    private  void ShowCustomer(Button btn)
    {
        ArrayList<customer>Customer=new ArrayList<>();
        String sqlString;
        Cursor cursor = null;
        AlertDialog.Builder bd = new AlertDialog.Builder(frmoustandlist.this,R.style.AlertDialogTheme);
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

                    AlertDialog.Builder searchBuilder = new AlertDialog.Builder(frmoustandlist.this,R.style.AlertDialogTheme);
                    View view = getLayoutInflater().inflate(R.layout.searchbox, null);
                    searchBuilder.setView(view);
                    final EditText etdSearch = view.findViewById(R.id.etdSearch);
                    ImageButton btnSearch = view.findViewById(R.id.imgOK);
                    //YLT
                    ImageButton btnFilterCode = view.findViewById(R.id.imgFilterCode);
                    btnFilterCode.setVisibility(View.VISIBLE);
                    btnFilterCode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PopupMenu popup = new PopupMenu(frmoustandlist.this,btnFilterCode);
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

                    //YLT
                    btnSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!etdSearch.getText().toString().isEmpty()) {

                                ArrayList<customer> filteredCustomer= new ArrayList<>();


                                if( fitercode =="Short")
                                {
                                    //for Search CustomerShort Search
                                    for (customer item : Customer) {
                                        if (item.getCustomercode().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                            filteredCustomer.add(item);
                                        }
                                    }
                                }
                                else {
                                    for (customer item : Customer) {
                                        if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                            filteredCustomer.add(item);
                                        }
                                    }
                                }
                                FilterCustomer ca = new FilterCustomer(frmoustandlist.this, filteredCustomer, btn,da,"frmoutstand");
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
       // RecyclerView rv = view.findViewById(R.id.rcvChange);
        sqlString = "select customerid,customer_code,customer_name,credit,Custdiscount,due_in_days,credit_limit from Customer order by customer_code,customer_name";
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
                    Customer.add(new customer(customerid, customername, customercode, credit,custdis,due_in_days,credit_limit));

                } while (cursor.moveToNext());

            }

        }
        else
        {
            da.dismiss();
        }
        cursor.close();

        FilterCustomer cad = new FilterCustomer(frmoustandlist.this, Customer, btn, da,"frmoutstand");
        rv.setAdapter(cad);
        GridLayoutManager gridLayoutManagerCustomer = new GridLayoutManager(getApplicationContext(), 4);
        rv.setLayoutManager(gridLayoutManagerCustomer);
        da.show();

    }

    private void ShowDateFilter() {
        LayoutInflater inflater = (LayoutInflater) frmoustandlist.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.daterangefilterpopup,null);
        float density=frmoustandlist.this.getResources().getDisplayMetrics().density;
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

    private void ShowBranch (Button btn)
    {
        ArrayList<Branch>Branch=new ArrayList<>();
        String sqlString;
        Cursor cursor = null;
        AlertDialog.Builder bd = new AlertDialog.Builder(frmoustandlist.this,R.style.AlertDialogTheme);
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

                    AlertDialog.Builder searchBuilder = new AlertDialog.Builder(frmoustandlist.this,R.style.AlertDialogTheme);
                    View view = getLayoutInflater().inflate(R.layout.searchbox, null);
                    searchBuilder.setView(view);
                    final EditText etdSearch = view.findViewById(R.id.etdSearch);
                    ImageButton btnSearch = view.findViewById(R.id.imgOK);
                    btnSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!etdSearch.getText().toString().isEmpty()) {

                                ArrayList<Branch> filteredBranch = new ArrayList<>();

                                for (Branch item : Branch) {
                                    if (item.getBranchname().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                        filteredBranch.add(item);
                                    }
                                }
                               // FilerBranch ca = new FilterBranch(frmoustandlist.this, filteredBranch, btn,da);
                                FilterBranch ca = new FilterBranch(frmoustandlist.this, filteredBranch, btn, da);
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
      // sqlString = "select locationid,Location_Name,Location_Short,branchID from Location ";
       // cursor = DatabaseHelper.rawQuery(sqlString);
        cursor=DatabaseHelper.DistinctSelectQuery("Location",new String[]{"branchID","Branch_Name","Branch_short"});
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {

                    long branchid = cursor.getLong(cursor.getColumnIndex("branchID"));
                    String branchname = cursor.getString(cursor.getColumnIndex("Branch_Name"));
                    String branchShort = cursor.getString(cursor.getColumnIndex("Branch_short"));
                    Branch.add(new Branch(branchid, branchname,branchShort));
                } while (cursor.moveToNext());

            }
        }
        else {
            da.dismiss();
        }
        cursor.close();

        FilterBranch Ba = new FilterBranch(frmoustandlist.this, Branch, btn, da);
        rv.setAdapter(Ba);
        GridLayoutManager gridLayoutManagerLocation = new GridLayoutManager(getApplicationContext(), 4);
        rv.setLayoutManager(gridLayoutManagerLocation);
        da.show();

    }
}
