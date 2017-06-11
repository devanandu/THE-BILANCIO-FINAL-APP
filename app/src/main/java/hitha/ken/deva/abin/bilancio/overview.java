package hitha.ken.deva.abin.bilancio;

import android.content.Context;
import android.database.Cursor;
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
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link overview.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link overview#newInstance} factory method to
 * create an instance of this fragment.
 */
public class overview extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private adapter_trans mAdapter;
    private List<Object> array = new ArrayList<>();

    public overview() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment overview.
     */
    // TODO: Rename and change types and number of parameters
    public static overview newInstance(String param1, String param2) {
        overview fragment = new overview();
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
        try {
            create();
        } catch (ParseException e) {
            Log.e("dasdas", "Parsing ISO8601 datetime failed", e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_overview, container, false);
        recyclerView = (RecyclerView)v.findViewById(R.id.recy);
        mAdapter = new adapter_trans(array);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        return v;
    }
    public void create() throws ParseException {
       DB_transactions db = new DB_transactions(getActivity());
        Cursor c = db.display();
        if (c.getCount() == 0)
            Toast.makeText(getActivity().getApplicationContext(), "Error..!! nothing to display..!", Toast.LENGTH_SHORT).show();
        else {
            String s="";
            while (c.moveToNext()) {
                DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d=iso8601Format.parse(c.getString(5));
                if(!s.equals(c.getString(5).substring(0,10)))
                {

                    s=c.getString(5).substring(0,10);
                    array.add(d);
                    //Log.e("h","j");
                }
                //Log.e(""+d.toString(),""+c.getString(5).substring(0,10));
                record rec=new record(c.getString(3),c.getString(4),c.getString(1),c.getString(2),c.getString(5).substring(11,16));
                array.add(rec);

                //Log.e(""+d.toString(),""+d.getMonth());

                //Toast.makeText(getApplicationContext(),sb, Toast.LENGTH_SHORT).show();
            }
//            Log.e("  ",""+c.getString(5).substring(0,9));
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
