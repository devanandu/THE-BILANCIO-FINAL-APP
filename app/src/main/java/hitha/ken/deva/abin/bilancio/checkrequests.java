package hitha.ken.deva.abin.bilancio;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class checkrequests extends Service {
    Notification notification;
    SharedPreferences mPreferences;
    String first;
    int hash;
    public checkrequests() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }
    public void onCreate()
    {
        //Log.e("service:","oncreate");
        mPreferences= getSharedPreferences("MyPref", MODE_PRIVATE);
        first = mPreferences.getString("loginid",null);
        checkreq();
        checklists();
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        //Log.e("service:","onstart");
        return START_STICKY;
    }
    private  void checkreq()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference req=database.getReference("REQUESTS").child(first);
        req.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Log.e("onchildadded",dataSnapshot.getValue().toString());
                if(dataSnapshot.getValue().toString().equals("false"))
                    set_notify("You have new request to link");
            }

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
    private void checklists()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference req=database.getReference("MESSAGES").child(first).child("0status");
        req.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Log.e("onchildadded",dataSnapshot.toString());

                if(dataSnapshot.getValue().toString().equals("false"))
                    set_notify("You have new lists to buy");


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //Log.e("onchildchanged",dataSnapshot.toString());
                if(dataSnapshot.getValue().toString().equals("false"))
                    set_notify("You have new lists to buy");
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
    public void set_notify(String msg)
    {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        Intent intent;
    if(msg.equals("You have new lists to buy"))
    { intent = new Intent(this,buy_list.class);}
     else
    {intent = new Intent(this,request_page.class);}
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        mBuilder.setContentIntent(pendingIntent);

        mBuilder.setSmallIcon(R.drawable.iconimg);
        mBuilder.setContentTitle("BILANCIO");
        mBuilder.setContentText(msg).setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(001, mBuilder.build());
    }
}
