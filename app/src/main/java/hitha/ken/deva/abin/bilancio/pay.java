package hitha.ken.deva.abin.bilancio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class pay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
    }
    public void kseb(View v)
    {
        String url="https://wss.kseb.in/selfservices/quickpay";
        Intent i=new Intent(this,paybill.class);
        i.putExtra("url",url);
        startActivity(i);
    }
    public void mobrec(View v)
    {
        String url="http://m.rechargeitnow.com/";
        Intent i=new Intent(this,paybill.class);
        i.putExtra("url",url);
        startActivity(i);
    }
    public void other(View v)
    {
        Intent i=new Intent(this,mock_pay.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
    }
}
