package hitha.ken.deva.abin.bilancio;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link buylist.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link buylist#newInstance} factory method to
 * create an instance of this fragment.
 */
public class buylist extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ProgressBar p;
    private FirebaseAuth mAuth;
    DatabaseReference myRef;
    String date,first;
    StringBuffer sb;
    private RecyclerView recyclerView;
    private listadapter mAdapter;
    private List<message> msgs = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public buylist() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment buylist.
     */
    // TODO: Rename and change types and number of parameters
    public static buylist newInstance(String param1, String param2) {
        buylist fragment = new buylist();
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
        View v= inflater.inflate(R.layout.fragment_buylist, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.buylist);
        p=(ProgressBar)v.findViewById(R.id.progressBar2);
        mAdapter = new listadapter(msgs);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        sb=new StringBuffer();
        mAuth= FirebaseAuth.getInstance();

        SharedPreferences mPreferences=this.getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        first = mPreferences.getString("loginid", null);

        viewbuylist();
        return v;
    }
    public void viewbuylist()
    {
        Log.e("hi", "inside");
        p.setVisibility(View.VISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("MESSAGES");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                msgs.clear();
                Log.e("hi", "starting fetching"+dataSnapshot.getValue()+first);
                if(dataSnapshot.hasChild(first)){
                    //TextView t=(TextView)findViewById(R.id.list);
                    Log.e("hi", "message exists");
                    Iterator<DataSnapshot> dataSnapshots = dataSnapshot.child(first).getChildren().iterator();
                    while (dataSnapshots.hasNext()) {
                        DataSnapshot dataSnapshotChild = dataSnapshots.next();
                        message msg = dataSnapshotChild.getValue(message.class);
                        msgs.add(msg);
                        sb.append(msg.msg);
                    }
                    //t.setText(sb.toString());
                    Log.e("hi", "message :"+sb.toString());
                    msgs.remove(0);
                }
                p.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("hi", "oncancelled");
            }
        });
        myRef.child(first).child("0status").child("new").setValue("true");
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
