package com.abbp.istockmobilesalenew;

import android.database.Cursor;

public class GetTranid {
    private  long tranid;

    public  GetTranid()
    {
       // GetIncrementTranid();
       GetLastestTranid();
    }

    private void GetLastestTranid() {
        try
        {
            Cursor cursor=DatabaseHelper.rawQuery("select tranid from Tranid");
            if(cursor!=null&&cursor.getCount()!=0)
            {
                if(cursor.moveToFirst())
                {
                    do {

                        tranid=cursor.getLong(cursor.getColumnIndex("tranid"));
                        tranid=(tranid-1);
                        DatabaseHelper.execute("update Tranid set tranid="+tranid);


                    }while (cursor.moveToNext());

                }

            }
            else
            {
                tranid=0;
                tranid=(tranid-1);
                DatabaseHelper.execute("insert into tranid values("+tranid+")");

            }
            cursor.close();
    }
        catch (Exception ee)
        {

        }
    }


    private void GetIncrementTranid(){
        Cursor cursor=DatabaseHelper.rawQuery("select count(*) as tranidcount from Sale_Head_Main");
        if(cursor!=null&&cursor.getCount()!=0)
        {
            if(cursor.moveToFirst())
            {
                do {

                    tranid=cursor.getLong(cursor.getColumnIndex("tranidcount"));
                    //tranid=(tranid-1);
                    //DatabaseHelper.execute("update Tranid set tranid="+tranid);


                }while (cursor.moveToNext());

            }

        }
    }

    public long getTranid() {
        return tranid;
    }
}
