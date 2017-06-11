package hitha.ken.deva.abin.bilancio;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digits.sdk.android.Digits;
import com.google.firebase.auth.FirebaseAuth;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "z2pCqVB1v1GRk4SKHwKa16qtL";
    private static final String TWITTER_SECRET = "cq66zAjMZZS1NCDa7AVOZJymOLdUPP0OOWgiqzIdPwdGOby4rg";

    FirebaseAuth mAuth;
    SharedPreferences mPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Menu m=navigationView.getMenu();
        //MenuItem limit=m.findItem(R.id.limit);
        //limit.setTitle("helooo");
        mAuth= FirebaseAuth.getInstance();
        mPreferences= getSharedPreferences("MyPref", MODE_PRIVATE);
        String first = mPreferences.getString("loginid", null);
        first_time_check();
        //setContentView(R.layout.activity_main);
        topheader();
        Intent intent = new Intent(this,checkrequests.class);
        if(!(isMyServiceRunning(checkrequests.class))&&first!=null)
        {
            Log.e("service:","started");
            this.startService(intent);
        }
        if(first!=null)
        {Intent req_service = new Intent(this,reqAccept_Service.class);
        this.startService(req_service);
        Log.e("service:","request");}

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Intent i=new Intent(this,link.class);
            startActivity(i);
        } else if (id == R.id.nav_gallery) {
            Intent i=new Intent(this,Reminder.class);
            startActivity(i);


        } else if (id == R.id.nav_slideshow) {
            Intent i=new Intent(this,request_page.class);
            startActivity(i);

        } else if (id == R.id.limit) {
            alertbox();
        } else if (id == R.id.nav_share) {
            setbalance();
        } else if (id == R.id.pay) {
            Intent i=new Intent(this,pay.class);
            startActivity(i);
        }
        else if (id == R.id.product) {
            Intent i=new Intent(this,product_list.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.e("service:","present");
                return true;
            }
        }
        Log.e("service:","absent");
        return false;
    }
    private boolean first_time_check() {

        String first = mPreferences.getString("loginid", null);
        if((first == null)){
            Intent i = new Intent(MainActivity.this,login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
        int day=mPreferences.getInt("daylimit",0);
        if(day!=0)
        {
            DB_limit db=new DB_limit(this);
        db.nextday(day+"");}
        day=mPreferences.getInt("monthlimit",0);
        if(day!=0)
        {
            DB_limit db=new DB_limit(this);
            db.nextmonth(day+"");}
        return false;

    }
    public void addexp(View v)
    {
        Intent i=new Intent(this,addexpense.class);
        startActivity(i);
    }
    public void products(View v)
    {
        Intent i=new Intent(this,product_list.class);
        startActivity(i);
    }
    public void pay(View v)
    {
        Intent i=new Intent(this,buy_list.class);
        startActivity(i);
    }
    public void view(View v)
    {
        Intent i=new Intent(this,history.class);
        startActivity(i);
    }
    public void addlist(View v)
    {
        Intent i=new Intent(this,buy_list.class);
        startActivity(i);
    }

    public void addmember(View v)
    {
        Intent i=new Intent(this,link.class);
        startActivity(i);
    }
    private void topheader()
    {

        String first = mPreferences.getString("loginid", null);
        if((first != null))
        {
            DB_user db=new DB_user(this);
            Cursor c=db.getdetails();
            if(c.getCount()!=0) {
                c.moveToFirst();
                TextView t = (TextView) findViewById(R.id.hello);
                TextView tx = (TextView) findViewById(R.id.wallet);
                t.setText("Hi," + c.getString(0).toString());
                tx.setText("Your Wallet Balance :\u20B9 " + c.getString(1).toString()+" ");
            }
        }

    }
    public void onResume(){
        super.onResume();

        String first = mPreferences.getString("loginid", null);
        if((first != null))
            topheader();
    }
    void alertbox()
    {
        LayoutInflater linf = LayoutInflater.from(this);
        final View view = linf.inflate(R.layout.dialog, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set Expense Limits");
        builder.setView(view);
        final EditText t=(EditText)view.findViewById(R.id.day);
        final EditText u=(EditText)view.findViewById(R.id.month);
        mPreferences= getSharedPreferences("MyPref", MODE_PRIVATE);
        int day = mPreferences.getInt("daylimit",0);
        int month = mPreferences.getInt("monthlimit",0);
        t.setText(day+"");
        t.setSelection(t.getText().length());
        u.setText(month+"");
        u.setSelection(u.getText().length());
        final DB_limit db=new DB_limit(this);
        builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor = mPreferences.edit();
                if(t.getText().equals("")||t.getText().toString().equals("0"))
                {
                    editor.putInt("daylimit",0);
                    db.deleteday();
                    //Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                }
                else
                {
                        editor.putInt("daylimit",Integer.parseInt(t.getText().toString()));
                    db.setTbday(Integer.parseInt(t.getText().toString()));
                }
                if(u.getText().equals("")||u.getText().toString().equals("0"))
                {
                    editor.putInt("monthlimit",0);
                    db.deletemonth();
                    //Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    editor.putInt("monthlimit",Integer.parseInt(u.getText().toString()));
                    editor.commit();
                    db.setTbmonth(Integer.parseInt(u.getText().toString()));}
                editor.commit();
            }

        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    void setbalance()
    {
        final String currentbalance;
        final DB_user db=new DB_user(this);
        Cursor c=db.getdetails();
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(MainActivity.this);
        alert.setTitle("Update Wallet Balance");
        edittext.setInputType(2);
        edittext.setTextSize(18);
        if(c.getCount()!=0) {
            c.moveToFirst();
            edittext.setText(c.getString(1).toString());
        }
        currentbalance=edittext.getText().toString();
        edittext.setBackground(null);
        edittext.setSelection(edittext.getText().length());
        edittext.setGravity(Gravity.CENTER);
        alert.setView(edittext);
        alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                db.setbalance(currentbalance,"Expense");
                db.setbalance(edittext.getText().toString(),"Income");
                topheader();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
                alert.show();
        }
}