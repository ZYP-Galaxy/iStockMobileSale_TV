package com.abbp.istockmobilesalenew;

import android.database.Cursor;

public class GetAppSetting {

    private String Setting_Name;
    private String Setting_Value;

    public GetAppSetting(String setting_Name) {
        Setting_Name = setting_Name;
    }

    public String getSetting_Value() {
        String sqlString = "select Setting_No,Setting_Name,Setting_Value from AppSetting where Setting_Name='"+Setting_Name+"'";
        Cursor cursor = DatabaseHelper.rawQuery(sqlString);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    Setting_Value=cursor.getString(cursor.getColumnIndex("Setting_Value"));
                } while (cursor.moveToNext());

            }

        }
        cursor.close();
        return Setting_Value;
    }
}
