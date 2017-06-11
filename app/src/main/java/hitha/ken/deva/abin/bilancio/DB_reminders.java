package hitha.ken.deva.abin.bilancio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by deva on 08/05/17.
 */

public class DB_reminders extends SQLiteOpenHelper {
    public static final String dbname="bilancio_reminder";
    public static final String tbname="reminders";
    public static final String id="id";
    //public static final String time="time";
    public static final String date="date";
    public static final String note="note";
    public DB_reminders(Context context) {
        super(context, dbname, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table reminders(id integer primary key,date text,note text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+tbname);
        onCreate(db);
    }
    public boolean addtimer(String d,String n){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put(date,d);
        content.put(note,n);
        return db.insert(tbname, null, content) != -1;
    }
    public Cursor getall()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor result=db.rawQuery("select * from "+tbname+" order by id desc",null);
        return result;
    }
    public boolean delete(String d)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(tbname,date+"='" +d+"'", null) > 0;
    }
}
