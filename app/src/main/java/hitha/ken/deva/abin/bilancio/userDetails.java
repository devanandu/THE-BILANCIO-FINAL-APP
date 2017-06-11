package hitha.ken.deva.abin.bilancio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class userDetails extends AppCompatActivity {

    private FirebaseAuth mAuth;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_user_details);
    }
    public void userdetails(View v)
    {
        DB_user db = new DB_user(this);
        TextView name = (TextView) findViewById(R.id.name);
        TextView bal = (TextView) findViewById(R.id.bal);
        if(!name.getText().toString().equals("") && !bal.getText().toString().equals(""))
        {
            SharedPreferences mPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
            String first = mPreferences.getString("loginid", null);
            if (db.initial(name.getText().toString(), Integer.parseInt(bal.getText().toString()), first)) {//Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            userupload(first);
            Intent i = new Intent(this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
        else
        {Toast.makeText(getApplicationContext(), "Empty fields.. enter values..!!", Toast.LENGTH_SHORT).show();}
    }
    public void userupload(String phno)
    {
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        TextView name = (TextView) findViewById(R.id.name);
        Log.e("firebase",firebaseUser.toString());
        if(firebaseUser!=null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            myRef = database.getReference("USERS");
            //Toast.makeText(getApplicationContext(), firebaseUser.getEmail(), Toast.LENGTH_SHORT).show();
            user user = new user(firebaseUser.getUid(), firebaseUser.getEmail(),name.getText().toString());
            myRef.child(phno)
                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // successfully added user
                        Toast.makeText(getApplicationContext(), "added", Toast.LENGTH_SHORT).show();
                    } else {
                        // failed to add user
                        Toast.makeText(getApplicationContext(), "error adding", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
            Toast.makeText(getApplicationContext(), "error adding", Toast.LENGTH_SHORT).show();
    }

}
