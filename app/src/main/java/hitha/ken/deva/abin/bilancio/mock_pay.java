package hitha.ken.deva.abin.bilancio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class mock_pay extends AppCompatActivity {
    String consumerNo,billNo;
    EditText e1,e2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_pay);
        e1= (EditText) findViewById(R.id.con_no);
        e2=(EditText)findViewById(R.id.bill_no);

    }
    public void onBill(View view) {
        consumerNo = e1.getText().toString();
        billNo = e2.getText().toString();
        if (!consumerNo.equals("")&& !billNo.equals("")) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Consumer");
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                public static final String extra = "com.example.abin.bilancio2.d";

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(consumerNo)) {
                        if (dataSnapshot.child(consumerNo).hasChild(billNo)) {
                            String state = dataSnapshot.child(consumerNo).child(billNo).child("Status").getValue().toString();
                            if (state.equals("no")) {
                                String d = dataSnapshot.child(consumerNo).child(billNo).child("Details").getValue().toString();
                                Intent intent = new Intent(mock_pay.this, mock_pay2.class);
                                intent.putExtra("extra", d);
                                intent.putExtra("consume", consumerNo);
                                intent.putExtra("bill", billNo);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(intent);
                            } else
                                Toast.makeText(mock_pay.this, "You Have already paid the bill!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mock_pay.this, "Invalid consumer number or bill number", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(mock_pay.this, "Invalid consumer number or bill number", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                }
            });

        }
        else
        {Toast.makeText(mock_pay.this, "Empty fields...!!", Toast.LENGTH_SHORT).show(); }
    }

}

