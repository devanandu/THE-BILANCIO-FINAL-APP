package hitha.ken.deva.abin.bilancio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class request_page extends AppCompatActivity {
    ProgressBar p;
    private ListView lsitView;
    private adapter_request mAdapter;
    DatabaseReference myRef,myacc,myuser;
    ArrayAdapter<String> Adapter;
    List<String> requests=new ArrayList<>();
    String first,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_page);
        lsitView =(ListView)findViewById(R.id.lstview);
        p=(ProgressBar)findViewById(R.id.progressBar3);
        SharedPreferences mPreferences= getSharedPreferences("MyPref", MODE_PRIVATE);
        first = mPreferences.getString("loginid", null);
        viewrequests();

    }

    private void viewrequests() {
        p.setVisibility(View.VISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("REQUESTS");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(first)) {
                    Log.e("hi", "message exists");
                    int i=0;
                    Iterator<DataSnapshot> dataSnapshots = dataSnapshot.child(first).getChildren().iterator();
                    while (dataSnapshots.hasNext()) {
                        DataSnapshot dataSnapshotChild = dataSnapshots.next();
                        Log.e("hi", dataSnapshotChild.toString());
                        if(dataSnapshotChild.getValue().toString().equals("false"))
                        {
                            requests.add(dataSnapshotChild.getKey().toString());
                            i++;
                        }
                    }
                    p.setVisibility(View.GONE);
                    create(requests);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void create(List<String> s)
    {
        String[] list = new String[s.size()];
        list=s.toArray(list);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        lsitView.setAdapter(Adapter);
        lsitView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("hello",Adapter.getItem(position));
                myuser=database.getReference("USERS").child(Adapter.getItem(position));
                myuser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Log.e("hi",dataSnapshot.getValue().toString()+dataSnapshot.getValue().toString().lastIndexOf("email"));
                        name=dataSnapshot.getValue().toString();
                        name=name.substring(6,name.lastIndexOf("email")-2);
                        Log.e("dsfsd",name);
                        //name.
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                dialog(Adapter.getItem(position));
            }
        });
    }
    public void dialog(final String s)
    {
        //Log.e("dd",name);
        AlertDialog.Builder a=new AlertDialog.Builder(request_page.this);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("REQUESTS");
        myacc=database.getReference("ACCEPTED");


        a.setMessage("Do you want to accept request ?").setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                myRef.child(first).child(s).setValue("true");

                add_accept(s,first);


                Toast.makeText(request_page.this,"Request Accepted..",Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Decline", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                myRef.child(first).child(s).removeValue();
                Toast.makeText(request_page.this,"Request Declined..",Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog b=a.create();

        b.setTitle("Request from "+ s );
        b.show();
    }
    public void add_accept(String rec,String sen)
    {

        Log.e("545",name);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        boolean request=true;
        myacc=database.getReference("ACCEPTED");
        myacc.child(rec).child(sen).setValue("true");
        DB_linkmembers db=new DB_linkmembers(this);
        db.add_member(name,rec,"true");
    }

}
