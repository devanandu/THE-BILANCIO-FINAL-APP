package hitha.ken.deva.abin.bilancio;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class reqAccept_Service extends Service {
    SharedPreferences mPreferences;
    String first;
    FirebaseDatabase database;
    DatabaseReference myref;
    DB_linkmembers db;
    @Nullable
    @Override
    public void onCreate() {
        //Log.e("service:request", "oncreate");
        mPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        first = mPreferences.getString("loginid", null);
        req_accept();
    }
    public IBinder onBind(Intent intent) {
        return null;
    }
    public int onStartCommand(Intent intent,int flags, int startId) {
        //Log.e("service:request","onstart");
        return START_STICKY;
    }
    public void req_accept()
    {
        db=new DB_linkmembers(this);
        database=FirebaseDatabase.getInstance();
        myref=database.getReference("ACCEPTED").child(first);
       // Log.e("sd","sadaass");
        myref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Log.e("sd","cnanasaskdks");
                if(dataSnapshot!=null)
                {
                    db.update_status(dataSnapshot.getKey().toString());
                    myref.child(dataSnapshot.getKey()).removeValue();
                }}

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
