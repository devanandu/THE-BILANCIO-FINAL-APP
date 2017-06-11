package hitha.ken.deva.abin.bilancio;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link send_list.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link send_list#newInstance} factory method to
 * create an instance of this fragment.
 */
public class send_list extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private FirebaseAuth mAuth;
    DatabaseReference myRef;
    String date, first,timestamp;
    StringBuffer sb;
    Spinner spin;

    ImageButton addtolist;
    Button        send;
    SimpleDateFormat sdf;
    LinearLayout l;
    ArrayAdapter<String> adapter;
    DB_list_items db;
    private List<String> array = new ArrayList<>();
    TextView notes,quan,typetxt,displist;
    EditText buylist;
    private List<members> memberses = new ArrayList<>();

    public send_list() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment send_list.
     */
    // TODO: Rename and change types and number of parameters
    public static send_list newInstance(String param1, String param2) {
        send_list fragment = new send_list();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v=inflater.inflate(R.layout.fragment_send_list, container, false);
        db = new DB_list_items(this.getActivity());

        l=(LinearLayout)v.findViewById(R.id.checkboxes);
        spin = (Spinner) v.findViewById(R.id.list);
        notes=(TextView)v.findViewById(R.id.notes);
        quan=(TextView)v.findViewById(R.id.quantityno);
        displist=(TextView)v.findViewById(R.id.displist);
        typetxt=(TextView)v.findViewById(R.id.type);
        send=(Button)v.findViewById(R.id.addlist) ;
        spin.setOnItemSelectedListener(this);
        spin_load();

        member_fetch();

        sb = new StringBuffer();
        mAuth = FirebaseAuth.getInstance();
        buylist= (EditText) v.findViewById(R.id.displist);

        sdf = new SimpleDateFormat("EEE, MMM d, HH:mm:ss");

        SharedPreferences mPreferences = this.getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        first = mPreferences.getString("loginid", null);
        addtolist=(ImageButton)v.findViewById(R.id.add_to_list);
        addtolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spin.getSelectedItem().toString().equals("Other")) {
                    String newitem = notes.getText().toString();
                    if(!newitem.equals(""))
                    {
                        sb.append("* " + newitem + "..");
                        notes.setText("");
                        sb.append(quan.getText());

                        String type = typetxt.getText().toString();
                        sb.append(typetxt.getText() + "\n");

                        displist.setText(sb.toString());
                        if(!db.add_other(newitem,type))
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        array.add(newitem);
                        spin.setAdapter(adapter);}
                }
                else
                {

                    sb.append("* " + spin.getSelectedItem().toString() + ".." + notes.getText().toString() + "..");
                    notes.setText("");

                    sb.append(quan.getText());

                    sb.append(typetxt.getText() + "\n");

                    displist.setText(sb.toString());
                }
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buylist.getText().length() != 0) {
                    boolean any = false;
                    for (int i = 0; i < memberses.size(); i++) {
                        CheckBox c = (CheckBox)v.findViewById(i);
                        if (c.isChecked()) {
                            //Toast.makeText(getApplicationContext(), "working", Toast.LENGTH_SHORT).show();
                            any = true;
                            date = sdf.format(new Date());
                            sdf=new SimpleDateFormat("yyMMddHHmmssZ");
                            timestamp=sdf.format(new Date());
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            myRef = database.getReference("MESSAGES");
                            message m = new message(buylist.getText().toString(), first, date);
                            myRef.child(memberses.get(i).phno).child(timestamp).setValue(m);
                            myRef.child(memberses.get(i).phno).child("0status").child("new").setValue("false");
                            buylist.setText("");
                            sb.setLength(0);
                            Log.e("hi", "list added");
                        }
                    }
                    if (!any)
                        Toast.makeText(getActivity(), "select any member", Toast.LENGTH_SHORT).show();
                }
            }
        });
      return v;
    }
    public void spin_load() {

        Cursor c = db.get_all();
        if (c.getCount() == 0)
            Toast.makeText(getActivity(), "Error..!! ", Toast.LENGTH_SHORT).show();
        else {
            while (c.moveToNext()) {
                array.add(c.getString(0));
            }
            adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_item, array);
            spin.setAdapter(adapter);
        }
    }
    public void member_fetch()
    {
        DB_linkmembers db=new DB_linkmembers(getActivity());
        Cursor c=db.get_all();
        if(c.getCount()!=0)
        {
            while (c.moveToNext()) {
                if(c.getString(2).equals("true"))
                {members m = new members(c.getString(0), c.getString(1), c.getString(2));
                memberses.add(m);}
            }
            //mAdapter.notifyDataSetChanged();
            for(int i = 0; i <memberses.size(); i++) {
                CheckBox cb = new CheckBox(getActivity());
                cb.setText(memberses.get(i).name+"("+memberses.get(i).phno+")");
                cb.setId(i);
                l.addView(cb);
            }
        }

    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getItemAtPosition(position).equals("Other"))
        {
            notes.setHint("add new item..");

        }
        else
        {
            notes.setHint("extra note..");

            Cursor c=db.get_type(spin.getSelectedItem().toString().trim());

            c.moveToLast();
            typetxt.setText(c.getString(0));

            Log.e("hi",c.getString(0));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
