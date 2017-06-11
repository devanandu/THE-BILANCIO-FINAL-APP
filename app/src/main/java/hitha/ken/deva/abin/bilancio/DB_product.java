package hitha.ken.deva.abin.bilancio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

/**
 * Created by deva on 28/04/17.
 */

public class DB_product extends SQLiteOpenHelper {
    public static final String PRODUCTDATA="Product";
    public static final String TABLENAME="ProdInfo";

    public static final String PRDUCTNAME="PRONAME";
    public static final String PURCHASETDATE="PURDATE";
    public static final String EXPIRYDATE="EXDATE";
    public static final String SERVICENO="CONTACT";
    public static final String PURCHASEDFROM="PLACE";
    public static final String image="IMAGE";


    public DB_product(Context context) {
        super(context, PRODUCTDATA, null, 2);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" create table " + TABLENAME + " (PRONAME TEXT,PURDATE TEXT,EXDATE TEXT,CONTACT TEXT,PLACE TEXT,IMAGE BLOB,id integer primary key)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLENAME);
        onCreate(db);

    }
    public boolean addProduct(String proname, String puradate, String exdate, String contact, String place, Bitmap img){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(PRDUCTNAME,proname);
        contentValues.put(PURCHASETDATE,puradate);
        contentValues.put(EXPIRYDATE,exdate);
        contentValues.put(SERVICENO,contact);
        contentValues.put(PURCHASEDFROM,place);
        if(img!=null)
            contentValues.put(image,Utils.getImageBytes(img));
        long result=db.insert(TABLENAME,null, contentValues);
        if(result == -1)
            return  false;
        else
            return true;

    }
    public Cursor get_all()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor result=db.rawQuery("select * from "+TABLENAME,null);
        return result;
    }
    public Cursor get_image(int i)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor result=db.rawQuery("select * from "+TABLENAME+" where id="+i,null);
        //Cursor result=db.query(TABLENAME,new String[]{"IMAGE"},"id=?",new String[]{i+""},null,null,null);
        return result;
    }
    void delete_all()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLENAME,null,null);
        db.close();
    }


}
