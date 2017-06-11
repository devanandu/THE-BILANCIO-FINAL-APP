package hitha.ken.deva.abin.bilancio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by deva on 27/04/17.
 */

public class DB_user extends SQLiteOpenHelper {
    public static final String dbname="bilancio_user";
    public static final String tbname="USER";
    public static final String username="name";
    public static final String balance="balance";
    public static final String phno="phno";

    public DB_user(Context context) {
        super(context, dbname, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table USER(name text,balance integer,phno text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+tbname);
        onCreate(db);
    }
    public boolean initial(String name,int bal,String first)
    {

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues content=new ContentValues();

        content.put(username,name);
        content.put(balance,bal);
        content.put(phno,first);
        return db.insert(tbname, null, content) != -1;
    }
    public Cursor getdetails()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor result=db.rawQuery("select * from "+tbname,null);
        return result;

    }
    public void setbalance(String amt,String type)
    {
        //int rs=Integer.parseInt(amt);
        SQLiteDatabase db=this.getWritableDatabase();
        if(type.equals("Expense"))
            db.execSQL("update USER set balance=balance-"+amt);
        else
            db.execSQL("update USER set balance=balance+"+amt);
    }
}

