package hitha.ken.deva.abin.bilancio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class mock_pay5 extends AppCompatActivity {

    String bill,consume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_pay5);
        bill=getIntent().getStringExtra("bill");
        consume=getIntent().getStringExtra("consume");


    }
    public void oBack(View view) throws InterruptedException {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myref=database.getReference("Consumer");
        myref.child("123456").child("9876").child("Status").setValue("yes");
        Intent i=new Intent(this,mock_pay.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
    }
}
