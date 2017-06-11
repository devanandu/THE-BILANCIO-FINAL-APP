package hitha.ken.deva.abin.bilancio;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by deva on 30/04/17.
 */

public class DB_limit extends SQLiteOpenHelper {

    public static final String dbname="bilancio_limit";
    public static final String tbday="daylimit";
    public static final String tbmonth="monthlimit";
    public static final String day="name";
    public static final String limit="limiter";

    public DB_limit(Context context) {
        super(context, dbname, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table daylimit(name text,limiter integer)");
        db.execSQL("create table monthlimit(name text,limiter integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+tbday);
        db.execSQL("drop table if exists "+tbmonth);
        onCreate(db);
    }
    void setTbday(int amt)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(tbday,null,null);
        ContentValues content=new ContentValues();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        content.put(day,date);
        content.put(limit,amt);
        db.insert(tbday, null, content);
    }
    void setTbmonth(int amt)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(tbmonth,null,null);
        ContentValues content=new ContentValues();
        String date = new SimpleDateFormat("MM").format(new Date());
        content.put(day,date);
        content.put(limit,amt);
        db.insert(tbmonth, null, content);
    }
    public boolean updatedaylimit(String amt) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update daylimit set limiter=limiter-" + amt);
        Cursor c=db.rawQuery("select * from "+tbday,null);
        c.moveToLast();
        Log.e(""+c.getCount()+c.getString(0),""+c.getInt(1));
        if(c.getInt(1)<0)
           return true;
        else
            return false;
        }
    public boolean updatemonthlimit(String amt) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update monthlimit set limiter=limiter-" + amt);
        Cursor c=db.rawQuery("select * from "+tbmonth,null);
        c.moveToLast();
        if(c.getInt(1)<0)
            return true;
        else
            return false;
    }
    void nextday(String day)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c=db.rawQuery("select * from "+tbday+" where name='"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"'",null);
            if(c.getCount()==0)
            {
             updatedaylimit(day);
            }

        }
     void nextmonth(String month)
     {
         SQLiteDatabase db = this.getWritableDatabase();
         Cursor c=db.rawQuery("select * from "+tbmonth+" where name='"+new SimpleDateFormat("MM").format(new Date())+"'",null);
         if(c.getCount()==0)
         {
             updatedaylimit(month);
         }
     }
        void deleteday()
        {
            SQLiteDatabase db=this.getWritableDatabase();
            db.delete(tbday,null,null);
        }
        void deletemonth()
        {
            SQLiteDatabase db=this.getWritableDatabase();
            db.delete(tbmonth,null,null);
        }
}
