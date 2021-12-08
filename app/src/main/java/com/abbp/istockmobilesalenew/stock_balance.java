package com.abbp.istockmobilesalenew;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class stock_balance extends AppCompatActivity implements View.OnClickListener{
    Button choosefilter,locfilter,btndate;
    DatePickerDialog pickerDialog;
    Date voudate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_balance);

        setUI();

    }


    private void setUI(){
        choosefilter=(Button)findViewById(R.id.choosefilter);
        choosefilter.setOnClickListener(this);
        locfilter=(Button)findViewById(R.id.locfilter);
        locfilter.setOnClickListener(this);
        btndate=(Button)findViewById(R.id.btndate);
        btndate.setOnClickListener(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {


        switch (v.getId())
        {
            case R.id.choosefilter:
                showpopupmenu();
                break;

            case R.id.btndate:
                changedate();
                break;

            case R.id.locfilter:
                break;

        }

    }

    private void showpopupmenu(){
        PopupMenu popup = new PopupMenu(stock_balance.this,choosefilter);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.filtermenu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.code:
                        Toast.makeText(getBaseContext(),"Code",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.description:
                        Toast.makeText(getBaseContext(),"Description",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.category:
                        Toast.makeText(getBaseContext(),"Category",Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();//showing popup menu
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void changedate() {

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
                    btndate.setText(new SimpleDateFormat("dd/MM/yyyy").format(voudate));
                  //  sh.get(0).setDate(dateFormat.format(voudate));
                }
                else
                {
                    btndate.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
                }

            }
        },mYear,mMonty,mDay);
        pickerDialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(stock_balance.this,frmmain.class);
        startActivity(intent);
        finish();
    }

}
