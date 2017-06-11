package hitha.ken.deva.abin.bilancio;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by deva on 08/05/17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent servcice1=new Intent(context,Alarmservice.class);
        servcice1.putExtra("date",intent.getStringExtra("date"));



        NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(context).setSmallIcon(R.mipmap.bilancio).setContentTitle("BILANCIO-REMINDER").setContentText(intent.getStringExtra("name"));
        NotificationManager mNotificationManager= (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(001,mBuilder.build());
        Toast.makeText(context,
                "AlarmReceiver.onReceive()",
                Toast.LENGTH_LONG).show();
        context.startService(servcice1);
    }
}
