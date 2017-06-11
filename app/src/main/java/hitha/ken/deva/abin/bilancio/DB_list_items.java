package hitha.ken.deva.abin.bilancio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by deva on 28/04/17.
 */

public class DB_list_items extends SQLiteOpenHelper {
    public static final String dbname="bilancio_list";
    public static final String tbname="buylist";
    public static final String itemname="name";
    public static final String type="type";

    public DB_list_items(Context context) {
        super(context, dbname, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table buylist(name text,type text)");
        db.execSQL("insert into buylist values('Other',''),('milk','nos.'),('egg','nos.'),('biscuit','nos.'),('onion','kg'),('rice','kg')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+tbname);
        onCreate(db);
    }
    public boolean add_other(String name,String typ)
    {

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues content=new ContentValues();

        content.put(itemname,name);
        content.put(type,typ);
        return db.insert(tbname, null, content) != -1;
    }
    public Cursor get_all()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor result=db.rawQuery("select * from buylist",null);
        return result;
    }
    public Cursor get_type(String name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor result=db.query(tbname,new String[]{"type"},"name=?",new String[]{name},null,null,null);
        return  result;
    }
}
