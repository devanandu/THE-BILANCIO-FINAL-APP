package hitha.ken.deva.abin.bilancio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by deva on 25/04/17.
 */

public class DB_transactions extends SQLiteOpenHelper {
        public static final String dbname="bilancio_trans";
        public static final String tbname="Transactions";
        public static final String no="ID";
        public static final String amount="amount";
        public static final String notes="notes";
        public static final String type="type";
        public static final String cat="category";
        public DB_transactions(Context context) {
            super(context, dbname, null,1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table Transactions(id integer primary key,amount integer,type text,category text,notes text,created_at DATETIME DEFAULT CURRENT_TIMESTAMP)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists "+tbname);
            onCreate(db);

        }
        public boolean addtransact(String typ,String catg,String amt,String note){
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = new Date();
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues content=new ContentValues();
            content.put(cat,catg);
            content.put(type,typ);
            content.put("created_at",dateFormat.format(date));
            content.put(amount,amt);
            content.put(notes,note);

            return db.insert(tbname, null, content) != -1;
        }
        public Cursor display()
        {
            SQLiteDatabase db=this.getWritableDatabase();
            Cursor result=db.rawQuery("select * from "+tbname+" order by id desc",null);
            return result;
        }

    }
