package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.widget.Toast;

import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.textparser.PrinterTextParserImg;

import java.io.File;
import java.io.FileOutputStream;

public class BluetoothPrinter {
    private Context context;
    private Paint textPaint;
    private  double net_amount;
    public BluetoothPrinter(Context context,double net_amount) {
        this.context=context;
        this.net_amount=net_amount;
    }
    public void GetTitle()
    {
        try {
            String fontPath = "fonts/zg.ttf"; /* You can use any font or       use default */
            String text="";
            Cursor cursor = DatabaseHelper.rawQuery("select title from SystemSetting");
            if (cursor != null && cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {
                        text+=cursor.getString(cursor.getColumnIndex("title"))+"\n\r   "+"\n\r  ";

                    } while (cursor.moveToNext());
                }
            }
            cursor.close();

            text+="    Sale Voucher";
            Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
            int height=0;
            String [] rows = text.split("\n\r");
            height=31*rows.length;/* Specify Length of Image File */
            FileOutputStream fop = null;
            File file;   /* Specify the path where you want to save the image */
            file = new File(context.getExternalFilesDir("/"),"title.jpg");

            textPaint=new TextPaint();
            textPaint.setColor(Color.WHITE);
            textPaint. setTextAlign(Paint.Align.LEFT);
            textPaint.setTextSize(25f);
            textPaint.setAntiAlias(true);

            /* Optional to set Rect */    final Rect bounds = new Rect();
            textPaint.getTextBounds(text, 0, text.length(), bounds);

            final Bitmap bmp = Bitmap.createBitmap(525, height, Bitmap.Config.ARGB_8888); //use ARGB_8888 for better quality
            final Canvas canvas = new Canvas(bmp);
            textPaint.setStyle(Paint.Style.FILL); //fill the background with blue color
            canvas.drawRect(0, 0, 525, height, textPaint);
            textPaint.setColor(Color.BLACK);
            textPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));

            float y=28;
            /* Custom your layout here */
            for(int i =0;i<rows.length;i++){
                if(i==4){
                    textPaint.setTypeface(Typeface.DEFAULT_BOLD);
                    textPaint.setTextSize(20);
                }else{
                    textPaint.setTypeface(tf);
                    textPaint.setTextSize(25f);
                }
                canvas.drawText(rows[i], 5, y, textPaint);
                y=y+28;
            }

            FileOutputStream stream = new FileOutputStream(file); //create your FileOutputStream here
            bmp.compress(Bitmap.CompressFormat.JPEG, 85, stream);
            bmp.recycle();
            stream.close();
            Toast.makeText(context,"Image has been saved", Toast.LENGTH_LONG).show();

        }
        catch (Exception ee)
        {
            Toast.makeText(context,ee.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void GetDetail()
    {
        try {
            String fontPath = "fonts/zg.ttf"; /* You can use any font or       use default */
            String text="";
            for(int i=0;i<sale_entry.sd.size();i++)
            {
                String item=sale_entry.sd.get(i).getDesc();
                double amt=sale_entry.sd.get(i).getUnit_qty()*sale_entry.sd.get(i).getSale_price();
                int len=item.length();
                if(len>20)
                {
                    item=item.substring(0,20);
                }
                text =text+ item+"       "+CurrencyFormat(amt)+"\n\r ";
                text=text+"("+ CurrencyFormat(sale_entry.sd.get(i).getUnit_qty())+"  "+sale_entry.sd.get(i).getUnit_short()+"x"
                        +CurrencyFormat(sale_entry.sd.get(i).getSale_price())+")";
                text=text+"\n\r \n\r";
            }
            Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
            int height = 0;
            String[] rows = text.split("\n\r");
            height = 31 * rows.length;/* Specify Length of Image File */
            FileOutputStream fop = null;
            File file;   /* Specify the path where you want to save the image */
            file = new File(context.getExternalFilesDir("/"), "detail.jpg");

            textPaint = new TextPaint();
            textPaint.setColor(Color.WHITE);
            textPaint.setTextAlign(Paint.Align.LEFT);
            textPaint.setTextSize(25f);
            textPaint.setAntiAlias(true);

            /* Optional to set Rect */
            final Rect bounds = new Rect();
            textPaint.getTextBounds(text, 0, text.length(), bounds);

            final Bitmap bmp = Bitmap.createBitmap(525, height, Bitmap.Config.ARGB_8888); //use ARGB_8888 for better quality
            final Canvas canvas = new Canvas(bmp);
            textPaint.setStyle(Paint.Style.FILL); //fill the background with blue color
            canvas.drawRect(0, 0, 525, height, textPaint);
            textPaint.setColor(Color.BLACK);
            textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

            float y = 28;
            /* Custom your layout here */
            for (int i = 0; i < rows.length; i++) {
                if (i == i)
                {
                    textPaint.setTypeface(tf);
                    textPaint.setTextSize(25f);
                } else {
                    textPaint.setTypeface(tf);
                    textPaint.setTextSize(25f);
                }
                canvas.drawText(rows[i], 5, y, textPaint);
                y = y + 28;
            }

            FileOutputStream stream = new FileOutputStream(file); //create your FileOutputStream here
            bmp.compress(Bitmap.CompressFormat.JPEG, 85, stream);
            bmp.recycle();
            stream.close();
            Toast.makeText(context, "Image has been saved", Toast.LENGTH_LONG).show();
        }
        catch (Exception ee)
        {

        }
    }
    public void GetHeader()
    {
        try {
            String fontPath = "fonts/zg.ttf"; /* You can use any font or       use default */
            String text="Date       :"+sale_entry.sh.get(0).getDate()+"\n\r\n\r";
            text=text+"Invoice   :";
            text=text+(sale_entry.sh.get(0).getInvoice_no().equals("")?sale_entry.sh.get(0).getDocid():sale_entry.sh.get(0).getInvoice_no());
            text=text+"\n\r\n\r";
            text=text+"Customer:";
            Cursor cursor = DatabaseHelper.rawQuery("select customer_name from Customer where customerid="+sale_entry.sh.get(0).getCustomerid());
            if (cursor != null && cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {
                        text+=cursor.getString(cursor.getColumnIndex("customer_name"))+"\n\rmj ";

                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
            int height=0;
            String [] rows = text.split("\n\r");
            height=31*rows.length;/* Specify Length of Image File */
            FileOutputStream fop = null;
            File file;   /* Specify the path where you want to save the image */
            file = new File(context.getExternalFilesDir("/"),"header.jpg");
            textPaint=new TextPaint();
            textPaint.setColor(Color.WHITE);
            textPaint. setTextAlign(Paint.Align.LEFT);
            textPaint.setTextSize(25f);
            textPaint.setAntiAlias(true);

            /* Optional to set Rect */    final Rect bounds = new Rect();
            textPaint.getTextBounds(text, 0, text.length(), bounds);

            final Bitmap bmp = Bitmap.createBitmap(525, height, Bitmap.Config.ARGB_8888); //use ARGB_8888 for better quality
            final Canvas canvas = new Canvas(bmp);
            textPaint.setStyle(Paint.Style.FILL); //fill the background with blue color
            canvas.drawRect(0, 0, 525, height, textPaint);
            textPaint.setColor(Color.BLACK);
            textPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));

            float y=28;
            /* Custom your layout here */
            for(int i =0;i<rows.length;i++) {
                if (i == i) {
                    textPaint.setTypeface(tf);
                    textPaint.setTextSize(25f);
                } else {
                    textPaint.setTypeface(tf);
                    textPaint.setTextSize(25f);
                }
                canvas.drawText(rows[i], 5, y, textPaint);
                y = y + 28;
            }
            FileOutputStream stream = new FileOutputStream(file); //create your FileOutputStream here
            bmp.compress(Bitmap.CompressFormat.JPEG, 85, stream);
            bmp.recycle();
            stream.close();
            Toast.makeText(context, "Image has been saved", Toast.LENGTH_LONG).show();
        }
        catch (Exception ee)
        {
            Toast.makeText(context, ee.getMessage(), Toast.LENGTH_LONG).show();
    }

    }
    public void PrintToBT()
    {  try {

            GetTitle();
            GetHeader();
            GetDetail();

            String pathName = context.getExternalFilesDir("/").toString()+"/title.jpg";
            int width = 0;
            int level =50;
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = false;
            opts.inSampleSize = 1;
            opts.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = BitmapFactory.decodeFile(pathName, opts);



            String pathName1 = context.getExternalFilesDir("/").toString()+"/detail.jpg";
            int width1 = 0;
            int level1 =50;
            BitmapFactory.Options opts1 = new BitmapFactory.Options();
            opts1.inJustDecodeBounds = false;
            opts1.inSampleSize = 1;
            opts1.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap1 = BitmapFactory.decodeFile(pathName1, opts1);


        String pathName2 = context.getExternalFilesDir("/").toString()+"/header.jpg";
        int width2 = 0;
        int level2 =50;
        BitmapFactory.Options opts2 = new BitmapFactory.Options();
        opts1.inJustDecodeBounds = false;
        opts1.inSampleSize = 1;
        opts1.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap2 = BitmapFactory.decodeFile(pathName2, opts2);


        EscPosPrinter printer = new EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(), 203, 95f, 32);
        printer
                .printFormattedText(
                        "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer,bitmap)+"</img>\n"+
                                "[L]\n" +
                                "[C]<font size='medium'>No.57, Rm(G/A), Kyeik Wine Pagoda Rd, Dana Thiri Tower, Mayangone Tsp.</font>\n"+
                                "[L]\n" +
                                "[C]<font size='medium'>Ph : 01-554206</font>\n"+
                                "[C]<font size='medium'>Open Daily : From 10:00 Am to 10:00 Pm</font>\n"+
                                "[L]\n"+
                                "[C]----------------------------------\n" +
                                "[L]\n"+
                                "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer,bitmap2)+"</img>\n"+
                                "[L]\n"+
                                "[C]----------------------------------\n" +
                                "[L]   Description[R]Amount\n" +
                                "[L]\n"+
                                "[C]--------------------------------\n" +
                                "[L]\n"+

                                "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer,bitmap1)+"</img>\n"+
                                "[L]\n"+

                                "[C]--------------------------------\n" +
                                "[L]\n"+
                                "[R] AMT:[R]"+CurrencyFormat(sale_entry.sh.get(0).getInvoice_amount())+"\n" +
                                "[L]\n"+
                                "[R]DIS :[R]"+CurrencyFormat(sale_entry.sh.get(0).getDiscount()+sale_entry.sh.get(0).getIstemdis_amount())+"\n" +
                                "[L]\n" +
                                "[R]FOC :[R]"+CurrencyFormat(sale_entry.sh.get(0).getFoc_amount())+"\n" +
                                "[L]\n" +
                                "[R]TAX :[R]"+CurrencyFormat(sale_entry.sh.get(0).getTax_amount())+"\n" +
                                "[L]\n" +
                                "[R]NET :[R]"+CurrencyFormat(net_amount)+"\n" +
                                "[L]\n" +
                                "[C]------------------------------------\n" +
                                "[L]\n"+
                                "[C]<barcode type='ean13' height='10'>831254784551</barcode>\n" +
                                "[L]\n"+
                                "[C]Thank You\n" +
                                "[L]\n"+
                                "[L]\n"+
                                "[L]\n"+
                                "[L]\n"

                );

        Toast.makeText(context,"Printed is successfull", Toast.LENGTH_LONG).show();

    }
        catch (Exception ee)
        {
            Toast.makeText(context,ee.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
    public  String CurrencyFormat(Double amt)
    {
        String numberAsString = String.format("%,." + frmmain.price_places + "f", amt);
        return  numberAsString;
    }

}
