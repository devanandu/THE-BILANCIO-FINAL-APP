package hitha.ken.deva.abin.bilancio;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class monthlyreport extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int max=0;
    private static boolean flag=false;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int pos=0;
    LinearLayout chartlayout;
    RadioButton i,e;
    Spinner spin;
    private static final int SERIES_NR = 2;
    List<bargraphvalues> values=new ArrayList<>();
    private OnFragmentInteractionListener mListener;
    ArrayAdapter<String> adapter;
    List<String> array = new ArrayList<>();
    public monthlyreport() {
        // Required empty public constructor
    }
    public static monthlyreport newInstance(String param1, String param2) {
        monthlyreport fragment = new monthlyreport();
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
        View v=inflater.inflate(R.layout.fragment_monthlyreport, container, false);
        chartlayout = (LinearLayout)v.findViewById(R.id.bar);
        e=(RadioButton) v.findViewById(R.id.month);
        i=(RadioButton) v.findViewById(R.id.day);
        spin = (Spinner) v.findViewById(R.id.spinner2);
         spin.setVisibility(View.GONE);
        try {
            calcmonthly(8,"Months");
            //calcmonthly(10,"May");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    spin.setVisibility(View.GONE);
                    calcmonthly(8,"Months");
                } catch (ParseException e) {
                    e.printStackTrace();

                }
            }
        });
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    spin.setVisibility(View.VISIBLE);
                    //View vi = spin.getSelectedView();
                    //if(vi!=null)
//                    Log.e("click",spin.toString()+"heloooo"+vi.toString());
//                    Toast.makeText(getActivity().getApplicationContext(), spin.toString()+"heloooo"+vi.toString(), Toast.LENGTH_SHORT).show();

                    //((TextView)vi).setTextColor(Color.WHITE);
                    if(spin.getSelectedItem()!=null)
                        calcmonthly(10,spin.getSelectedItem().toString());
                    else
                    {
                        adapter = new ArrayAdapter<String>(getActivity(),R.layout.spin, array);
                        spin.setAdapter(adapter);
                        spin.setSelection(0, true);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        spin.setSelection(0,false);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(pos!=position)
                {
                    pos=position;
                    try {
                    spin.setSelection(position,false);
                    spin.setVisibility(View.VISIBLE);
                    //Toast.makeText(getActivity().getApplicationContext(),spin.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    calcmonthly(10,spin.getSelectedItem().toString());
                    spin.setSelection(position,false);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            spin.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity().getApplicationContext(),spin.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            calcmonthly(10,spin.getSelectedItem().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private XYMultipleSeriesDataset getTruitonBarDataset() {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        final int nr = values.size();
        Random r = new Random();
        ArrayList<String> legendTitles = new ArrayList<String>();
        legendTitles.add("Income");
        legendTitles.add("Expense");
        for (int i = 0; i < SERIES_NR; i++) {
            CategorySeries series = new CategorySeries(legendTitles.get(i));
            for (int k = 0; k < nr; k++) {
                if(i==0)
                    series.add(values.get(k).inc);
                if(i==1)
                    series.add(values.get(k).exp);
            }

            dataset.addSeries(series.toXYSeries());
        }
        return dataset;
    }

    public XYMultipleSeriesRenderer getTruitonBarRenderer() {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setAxisTitleTextSize(16);
        renderer.setChartTitleTextSize(20);
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15);
        renderer.setZoomEnabled(false);
        renderer.setBarWidth(50);
        renderer.setMarginsColor(Color.TRANSPARENT);
        renderer.setScale((float)0.5);
        renderer.setDisplayValues(true);
        renderer.setMargins(new int[] { 50, 80,40,30});
        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(Color.BLUE);
        r.setChartValuesFormat(new DecimalFormat("\u20B9"));
        r.setChartValuesTextAlign(Paint.Align.RIGHT);
        r.setDisplayChartValues(true);
        //r.setChartValuesTextSize();
        renderer.addSeriesRenderer(r);
        r = new XYSeriesRenderer();
        r.setColor(Color.RED);
        r.setDisplayChartValues(true);
        r.setChartValuesFormat(new DecimalFormat("\u20B9"));
        r.setChartValuesTextAlign(Paint.Align.RIGHT);
        renderer.addSeriesRenderer(r);
        return renderer;
    }

    private void myChartSettings(XYMultipleSeriesRenderer renderer,String label) {
        renderer.setChartTitle("Your monthly cash flows");
        renderer.setXAxisMin(0.5);
        renderer.setXAxisMax(values.size()+1);
        renderer.setYAxisMin(0);
        renderer.setYAxisMax(max+100);
        renderer.setDisplayValues(true);
        //renderer.setMarginsColor(Color);
        for(int i=0;i<values.size();i++)
            renderer.addXTextLabel(i+1,values.get(i).month);
        renderer.setYLabelsAlign(Paint.Align.RIGHT);
        renderer.setBarSpacing(1);
        renderer.setXTitle(label);
        renderer.setYTitle("Rupees");
        renderer.setLegendTextSize(30);
        renderer.setShowGrid(true);
        renderer.setGridColor(Color.GRAY);
        renderer.setApplyBackgroundColor(true);
        renderer.setBackgroundColor(Color.TRANSPARENT);
        renderer.setXLabels(0);
        //renderer.setYLabels(0);// sets the number of integer labels to appear
    }
void calcmonthly(int x,String label) throws ParseException
{
    values.clear();
    chartlayout.removeAllViews();
    DB_transactions db = new DB_transactions(getActivity());
    Cursor c = db.display();
    if (c.getCount() == 0)
        Toast.makeText(getActivity().getApplicationContext(), "Error..!! nothing to display..!", Toast.LENGTH_SHORT).show();
    else {
        String s = "";
        int i=0,e=0;
        while (c.moveToNext()) {
            DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d=iso8601Format.parse(c.getString(5));
            //Log.e("m",d.toString());
            if (!s.equals(d.toString().substring(4,x))) {
                {
                    if(!s.equals(""))
                    {
                        //Log.e("m",s+e+i);
                        if(s.startsWith(label)|| label.equals("Months"))
                        {
                            //Log.e("m",s+e+i);
                            bargraphvalues b=new bargraphvalues(i,e,s);
                        if(x==8 && !flag)
                        {
                            array.add(s);
                        }
                        values.add(b);
                        if(max<i)
                            max=i;
                        if(max<e)
                            max=e;
                        //Log.e("blah",d.toString()+s+" "+i+" "+e);
                        i=0;e=0;
                        }
                    }
                    s=d.toString().substring(4,x);
                }

            }
            if(s.startsWith(label)|| label.equals("Months")) {
                if (c.getString(2).equals("Expense"))
                    e = e + c.getInt(1);
                else
                    i = i + c.getInt(1);
            }
            //Log.e("m",s+e+i);
        }

        if(s.startsWith(label)|| label.equals("Months"))
        {bargraphvalues b=new bargraphvalues(i,e,s);
        if(x==8 && !flag)
        {
            array.add(s);
        }
        values.add(b);
        if(max<i)
            max=i;
        if(max<e)
            max=e;}
        Log.e("sjs",s+" "+i+" "+e);
        XYMultipleSeriesRenderer renderer = getTruitonBarRenderer();
        myChartSettings(renderer,label);
        final GraphicalView chartView = ChartFactory.getBarChartView(getActivity(), getTruitonBarDataset(), renderer, BarChart.Type.DEFAULT);
        // Add the pie chart view to the layout to show
        chartView.setBackgroundColor(Color.TRANSPARENT);
        chartlayout.removeAllViews();
        chartlayout.addView(chartView);
        adapter = new ArrayAdapter<String>(getActivity(),R.layout.spin, array);
        spin.setAdapter(adapter);
        spin.setSelection(0,false);
//        if(x==10)
//        {View vi = spin.getSelectedView();
//        ((TextView)vi).setTextColor(Color.WHITE);}
        //((TextView)v).setTextAlignment(Paint.Align.CENTER);
        flag=true;
    }
}



    }