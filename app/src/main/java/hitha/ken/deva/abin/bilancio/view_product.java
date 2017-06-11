package hitha.ken.deva.abin.bilancio;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link view_product.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link view_product#newInstance} factory method to
 * create an instance of this fragment.
 */
public class view_product extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ArrayAdapter<String> Adapter;
    private ListView lsitView;
    Cursor c;
    ImageView img;
    DB_product db;

    public view_product() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment view_product.
     */
    // TODO: Rename and change types and number of parameters
    public static view_product newInstance(String param1, String param2) {
        view_product fragment = new view_product();
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
        View v= inflater.inflate(R.layout.fragment_view_product, container, false);
        lsitView =(ListView)v.findViewById(R.id.pro_list);
        populate();
        return v;
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
    public void populate() {
        db=new DB_product(getActivity());
        c=db.get_all();
        if(c.getCount()!=0)
        {
            List<String> product=new ArrayList<>();
            StringBuffer sb = new StringBuffer();
            while (c.moveToNext())
            {
                sb.delete(0,sb.length());
                sb.append("\nProduct Name           : " + c.getString(0) +
                        "\nPurchased On           : " + c.getString(1) + "\n");
                sb.append(  "Warranty Expires On: " + c.getString(2) +
                        "\nService Details         : " + c.getString(3) + "\n");
                sb.append(  "Stored Location     : " + c.getString(4));
                product.add(sb.toString());
            }
            String[] list = new String[product.size()];
            list=product.toArray(list);
            Adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,list);
            lsitView.setAdapter(Adapter);
            lsitView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    c=db.get_image(position+1);
                    Log.e(position+"",c.getCount()+"");
                    c.moveToLast();
                    byte[] blob = c.getBlob(c.getColumnIndex("IMAGE"));
                    disp(blob);
                    Log.e(position+"",c.getCount()+"");
                }
            });
        }
    }
    public void disp(byte[] blob)
    {
        if(blob==null)
            Toast.makeText(getActivity(),"No photo for this product..!!",Toast.LENGTH_LONG).show();
        else{
            Intent i = new Intent(getActivity(),image_pro.class);
        Bundle b=new Bundle();
        b.putByteArray("image_url",blob);
        i.putExtras(b);
        startActivity(i);}
    }
    public void onResume()
    {
        super.onResume();
        populate();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    if(isVisibleToUser)
        populate();
    }
}
