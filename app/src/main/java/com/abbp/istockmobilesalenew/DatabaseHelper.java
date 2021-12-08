package com.abbp.istockmobilesalenew;



import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.content.ContentValues.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static SQLiteDatabase sqliteDb;
    private static DatabaseHelper instance;
    private static final int DATABASE_VERSION = 1;

    private Context context;
    static Cursor cursor = null;

    DatabaseHelper(Context context, String name, CursorFactory factory,
                   int version) {
        super(context, name, factory, version);
        this.context = context;

    }

    private static void initialize(Context context, String databaseName) {
        if (instance == null) {

            if (!checkDatabase(context, databaseName)) {

                try {
                    copyDataBase(context, databaseName);
                } catch (IOException e) {

                    System.out.println( databaseName
                            + " does not exists ");
                }
            }

            instance = new DatabaseHelper(context, databaseName, null,
                    DATABASE_VERSION);
            sqliteDb = instance.getWritableDatabase();

            System.out.println("instance of  " + databaseName + " created ");
        }
    }

    public static final DatabaseHelper getInstance(Context context,
                                                   String databaseName) {
        initialize(context, databaseName);
        return instance;
    }

    public SQLiteDatabase getDatabase() {
        return sqliteDb;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static void copyDataBase(Context aContext, String databaseName)
            throws IOException {

        InputStream myInput = aContext.getAssets().open(databaseName);

        String outFileName = getDatabasePath(aContext, databaseName);

        File f = new File("/data/data/" + aContext.getPackageName()
                + "/databases/");
        if (!f.exists())
            f.mkdir();

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();

        System.out.println(databaseName + " copied");
    }

    public static boolean checkDatabase(Context aContext, String databaseName) {
        SQLiteDatabase checkDB = null;

        try {
            String myPath = getDatabasePath(aContext, databaseName);

            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);

            checkDB.close();
        } catch (SQLiteException e) {

            System.out.println(databaseName + " does not exists");
        }

        return checkDB != null ? true : false;
    }

    private static String getDatabasePath(Context aContext, String databaseName) {
        return "/data/data/" + aContext.getPackageName() + "/databases/"
                + databaseName;
    }
   //select
    public static Cursor rawQuery(String query) {
        try {
            if (sqliteDb.isOpen()) {
                sqliteDb.close();
            }
            sqliteDb = instance.getWritableDatabase();

            cursor = null;
            cursor = sqliteDb.rawQuery(query, null);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
        return cursor;
    }
    public  static  Cursor DistinctCategorySelectQuery(String TableName,String[] columns,String order)
    {
        try {
            if (sqliteDb.isOpen()) {
                sqliteDb.close();
            }
            sqliteDb = instance.getWritableDatabase();

            cursor = null;
            cursor = sqliteDb.query(true,TableName,columns,null,null,null,null,order,null);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
        return cursor;
    }

    public  static Cursor DistinctSelectQuery(String TableName, String[] columns)
    {
        try {
        if (sqliteDb.isOpen()) {
            sqliteDb.close();
        }
        sqliteDb = instance.getWritableDatabase();

        cursor = null;
        cursor = sqliteDb.query(true,TableName,columns,null,null,null,null,null,null);
    } catch (Exception e) {
        Log.d(TAG, e.getMessage());
        e.printStackTrace();
    }
        return cursor;
    }
    public  static Cursor DistinctSelectQuerySelection (String TableName, String[] columns, String selection, String[] selectionArgs)
    {
        try {
            if (sqliteDb.isOpen()) {
                sqliteDb.close();
            }
            sqliteDb = instance.getWritableDatabase();

            cursor = null;
            cursor=sqliteDb.query(true,TableName,columns,selection,selectionArgs,null,null,null,null);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
        return cursor;
    }

    public  static Cursor DistinctSelectQuerySelectionWithCondition (String TableName, String[] columns, String selection, String[] selectionArgs,String order)
    {
        try {
            if (sqliteDb.isOpen()) {
                sqliteDb.close();
            }
            sqliteDb = instance.getWritableDatabase();

            cursor = null;
            cursor=sqliteDb.query(true,TableName,columns,selection,selectionArgs,null,null,order,null);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
        return cursor;
    }

    //insert,update,delete into assets database
    public static void execute(String query) {
        try {
            if (sqliteDb.isOpen()) {
                sqliteDb.close();
            }
            sqliteDb = instance.getWritableDatabase();
            sqliteDb.execSQL(query);
        } catch (Exception e) {
            System.out.println("DB ERROR  " + e.getMessage());
            e.printStackTrace();
        }
    }
}