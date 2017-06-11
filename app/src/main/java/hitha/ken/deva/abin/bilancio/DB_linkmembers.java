package hitha.ken.deva.abin.bilancio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by deva on 27/04/17.
 */

public class DB_linkmembers extends SQLiteOpenHelper {
    public static final String dbname="bilancio_memberslist";
    public static final String tbname="members";
    public static final String name="name";
    public static final String phno="phno";
    public static final String status="status";

    public DB_linkmembers(Context context) {
        super(context, dbname, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table members(name text,phno text unique,status text)");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+tbname);
        onCreate(db);
    }
    public boolean add_member(String n,String ph,String sts)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues content=new ContentValues();

        content.put(name,n);
        content.put(phno,ph);
        content.put(status,sts);
        return db.insert(tbname, null, content) != -1;
    }
    public Cursor get_all()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor result=db.rawQuery("select distinct * from "+tbname,null);
        return result;
    }
    public void update_status(String ph)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("UPDATE members SET status='true' WHERE phno='"+ph+"'");
        Log.e("db","error updating");
    }
}
