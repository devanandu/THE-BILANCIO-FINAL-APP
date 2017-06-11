package hitha.ken.deva.abin.bilancio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class mock_pay2 extends AppCompatActivity {

    TextView t1;
    String ab,conume,bill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_pay2);
        t1=(TextView)findViewById(R.id.textView5);
        ab=getIntent().getStringExtra("extra");
        t1.setText(ab);
        conume=getIntent().getStringExtra("consume");
        bill=getIntent().getStringExtra("bill");

    }
    public void onPay(View view)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataAccess = database.getReference("Consumer");
        dataAccess.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String detail=dataSnapshot.child(conume).child(bill).child("pay").getValue().toString();
                Intent i=new Intent(mock_pay2.this,mock_pay3.class);
                i.putExtra("pays",detail);
                i.putExtra("consume",conume);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                i.putExtra("bill",bill);
                startActivity(i);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
