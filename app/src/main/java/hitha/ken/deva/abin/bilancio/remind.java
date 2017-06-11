package hitha.ken.deva.abin.bilancio;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class remind extends AppCompatActivity {


    Calendar myCalendar= Calendar.getInstance();
    private EditText et,txt;
    TimePicker t;
    int hour = 8;
    int min = 0;
    ArrayAdapter<String> Adapter;
    private ListView lsitView;
    DB_reminders db=new DB_reminders(this);
    Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind);
        et =(EditText)findViewById(R.id.editText3);
        txt =(EditText)findViewById(R.id.editText);
        t=(TimePicker)findViewById(R.id.timePicker2);
        lsitView =(ListView)findViewById(R.id.remlist);
        t.setIs24HourView(true);
        int currentApiVersion = Build.VERSION.SDK_INT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            t.setHour(8);
            t.setMinute(0);
        }

        else {
            t.setCurrentHour(8);
            t.setCurrentMinute(0);
        }
        populate();
    }

    DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();

        }
    };

    public void onClick(View v) {
        // TODO Auto-generated method stub
        new DatePickerDialog(remind.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    public void onClick2(View v){
        setAlarm(myCalendar);
    }


    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et.setText(sdf.format(myCalendar.getTime()));
    }
    private void setAlarm(Calendar target){
        int currentApiVersion = Build.VERSION.SDK_INT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = t.getHour();
            min = t.getMinute();
        }

        else {
            hour = t.getCurrentHour();
            min = t.getCurrentMinute();
        }
        //Log.e(txt.getText()+hour,""+min);
        target.set(Calendar.HOUR_OF_DAY,hour);
        target.set(Calendar.MINUTE,min);
        target.set(Calendar.SECOND,0);
        int day=target.get(Calendar.DAY_OF_MONTH);
        db.addtimer(target.getTime().toString(),txt.getText().toString());
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        intent.putExtra("name",txt.getText().toString());
        intent.putExtra("date",target.getTime().toString());
        intent.putExtra("id",min+hour+day);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(),min+hour+day, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, target.getTimeInMillis(), pendingIntent);
        populate();
    }

    private void populate() {
        c=db.getall();
        if(c.getCount()!=0)
        {
            List<String> product=new ArrayList<>();
            StringBuffer sb = new StringBuffer();
            while (c.moveToNext())
            {
                sb.delete(0,sb.length());
                sb.append(c.getString(2)+"\n"+c.getString(1));
                product.add(sb.toString());
            }
            String[] list = new String[product.size()];
            list=product.toArray(list);
            Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
            lsitView.setAdapter(Adapter);
            lsitView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.e(position+"",c.getCount()+"");
                }
            });
        }
    }
}


