package hitha.ken.deva.abin.bilancio;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class addexpense extends AppCompatActivity {

    RadioGroup rg;
    Spinner spin;
    ArrayAdapter<CharSequence> adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addexpense);
        rg=(RadioGroup)findViewById(R.id.rg);
        spin = (Spinner) findViewById(R.id.spinner);
        if (rg.getCheckedRadioButtonId() == R.id.expense) {
            adapter = ArrayAdapter.createFromResource(this, R.array.expense, android.R.layout.simple_spinner_item);
            spin.setAdapter(adapter);
        }
        if (rg.getCheckedRadioButtonId() == R.id.income) {
            adapter = ArrayAdapter.createFromResource(this, R.array.income, android.R.layout.simple_spinner_item);
            spin.setAdapter(adapter);
        }
    }
    public void catlist(View v){

        if(rg.getCheckedRadioButtonId()==R.id.expense) {
            adapter = ArrayAdapter.createFromResource(this, R.array.expense, android.R.layout.simple_spinner_item);
            spin.setAdapter(adapter);
        }
        if(rg.getCheckedRadioButtonId()==R.id.income) {
            adapter = ArrayAdapter.createFromResource(this, R.array.income, android.R.layout.simple_spinner_item);
            spin.setAdapter(adapter);
        }
    }
    public void savetransact(View v) {
        DB_transactions db = new DB_transactions(this);
        String type;
        TextView amt = (TextView) findViewById(R.id.amount);
        if(!amt.getText().toString().equals("")) {
            TextView notes = (TextView) findViewById(R.id.notes);
            SharedPreferences mPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
            int day = mPreferences.getInt("daylimit", 0);
            int month = mPreferences.getInt("monthlimit", 0);
            if (rg.getCheckedRadioButtonId() == R.id.expense)
                type = "Expense";
            else
                type = "Income";
            if (db.addtransact(type, spin.getSelectedItem().toString(), amt.getText().toString(), notes.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                DB_user user = new DB_user(this);
                user.setbalance(amt.getText().toString(), type);

                if (type.equals("Expense")) {
                    int i = 0;
                    DB_limit d = new DB_limit(this);
                    if (month != 0) {
                        if (d.updatemonthlimit(amt.getText().toString()))
                            i++;
                    }
                    if (day != 0) {
                        if (d.updatedaylimit(amt.getText().toString()))
                            i++;
                    }
                    if (i > 0)
                        snackbar(i);
                }
            } else
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getApplicationContext(), "Please Enter amount...!!", Toast.LENGTH_SHORT).show();
    }
    void snackbar(int i)
    {
        if(i==2)
        {
            Snackbar.make(getCurrentFocus(), "You have crossed your MONTHLY limit..!!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Ok!", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    }).show();
        }
    else{
            Snackbar.make(getCurrentFocus(), "You have crossed your daily limit..!!", Snackbar.LENGTH_INDEFINITE)
                .setAction("Ok!", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();}
    }
}
