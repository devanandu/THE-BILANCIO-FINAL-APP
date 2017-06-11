package hitha.ken.deva.abin.bilancio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class mock_pay3 extends AppCompatActivity {
    TextView textView;
    String pays,conume,bill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_pay3);
        textView=(TextView)findViewById(R.id.textView9);
        pays=getIntent().getStringExtra("pays");
        conume=getIntent().getStringExtra("consume");
        bill=getIntent().getStringExtra("bill");
        textView.setText(pays);


    }
    public void onProceed(View view)
    {
        Intent i=new Intent(this,mock_pay4. class);
        i.putExtra("consume",conume);
        // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.putExtra("bill",bill);
        startActivity(i);
    }

}
