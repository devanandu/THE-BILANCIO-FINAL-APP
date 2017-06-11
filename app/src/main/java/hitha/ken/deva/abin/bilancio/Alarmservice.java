package hitha.ken.deva.abin.bilancio;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Alarmservice extends Service {
    public Alarmservice() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        DB_reminders db=new DB_reminders(getApplicationContext());
        db.delete(intent.getStringExtra("date"));
        return START_NOT_STICKY;
    }
}
