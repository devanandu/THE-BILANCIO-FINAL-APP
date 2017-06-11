package hitha.ken.deva.abin.bilancio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class mock_pay4 extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5;
    Button b1;
    String cardno,mon,ye,cv,passw,bill,consume;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myref=database.getReference("Consumer");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_pay4);
        e1=(EditText)findViewById(R.id.card);
        e2=(EditText)findViewById(R.id.month);
        e3=(EditText)findViewById(R.id.hi);
        e4=(EditText)findViewById(R.id.cvv);
        e5=(EditText)findViewById(R.id.pass);
        bill=getIntent().getStringExtra("bill");
        consume=getIntent().getStringExtra("consume");
    }
    public void okDone(View view)
    {
        cardno=e1.getText().toString();
        Log.e("card",cardno);
        mon=e2.getText().toString();
        Log.e("mon",mon);
        ye=e3.getText().toString();
        Log.e("year",ye);
        cv=e4.getText().toString();
        Log.e("cvv",cv);
        b1=(Button)findViewById(R.id.ok);

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!cardno.equals("") && !mon.equals("") && !ye.equals("") && !cv.equals("")) {
                    if (dataSnapshot.hasChild(cardno)) {
                        if (dataSnapshot.child(cardno).hasChild(mon)) {
                            if (dataSnapshot.child(cardno).child(mon).hasChild(ye)) {
                                if (dataSnapshot.child(cardno).child(mon).child(ye).hasChild(cv)) {
                                    //Toast.makeText(Main5Activity.this, "yahoo", Toast.LENGTH_SHORT).show();
                                    b1.setVisibility(View.VISIBLE);
                                }
                                else
                                    Toast.makeText(mock_pay4.this, "Invalid Card Details ", Toast.LENGTH_SHORT).show();

                            }
                            else
                                Toast.makeText(mock_pay4.this, "Invalid Card Details", Toast.LENGTH_SHORT).show();

                        }
                        else
                            Toast.makeText(mock_pay4.this, "Invalid Card Details ", Toast.LENGTH_SHORT).show();

                    }
                    else
                        Toast.makeText(mock_pay4.this, "Invalid Card Details", Toast.LENGTH_SHORT).show();

                }
                else
                    Toast.makeText(mock_pay4.this, "Invalid Card Details", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("hai", "Failed to read value.", databaseError.toException());


            }
        });

    }
    public void onPass(View view)
    {
        passw=e5.getText().toString();
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String pa=dataSnapshot.child(cardno).child(mon).child(ye).child(cv).getValue().toString();
                if(pa.equals(passw))
                {
                    Intent i=new Intent(mock_pay4.this,mock_pay5.class);
                    i.putExtra("bill",bill);
                    i.putExtra("consume",consume);
                    i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                    startActivity(i);
                    //myref.child(consume).child(bill).child("Status").setValue("yes");
                    //Toast.makeText(Main5Activity.this, "Payment Success!!", Toast.LENGTH_SHORT).show()

                }
                else
                {
                    Toast.makeText(mock_pay4.this, "Password Incorrect..!!", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //if(myref.child(consume).child(bill).child("Status").equals("yes"))

    }
}
