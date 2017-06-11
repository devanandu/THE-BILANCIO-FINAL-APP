package hitha.ken.deva.abin.bilancio;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.XYSeriesRenderer;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link expense_graph.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link expense_graph#newInstance} factory method to
 * create an instance of this fragment.
 */
public class expense_graph extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    LinearLayout chartlayout;
    RadioGroup rg;
    RadioButton e,i;
    int exp[],inc[];
    String[] expense_array,income_array;
    private OnFragmentInteractionListener mListener;

    public expense_graph() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment expense_graph.
     */
    // TODO: Rename and change types and number of parameters
    public static expense_graph newInstance(String param1, String param2) {
        expense_graph fragment = new expense_graph();
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
        View v=inflater.inflate(R.layout.fragment_expense_graph, container, false);
        chartlayout = (LinearLayout)v.findViewById(R.id.lllayout);
        expense_array = getResources().getStringArray(R.array.expense);
        income_array = getResources().getStringArray(R.array.income);
        rg=(RadioGroup)v.findViewById(R.id.group);
        e=(RadioButton) v.findViewById(R.id.exp);
        i=(RadioButton) v.findViewById(R.id.inc);
        exp=new int[expense_array.length];
        inc=new int[income_array.length];
        drawPieChart(0);
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    drawPieChart(1);
            }
        });
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawPieChart(0);
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void drawPieChart(int type) {
        chartlayout.removeAllViews();
        final CategorySeries categorySeries = new CategorySeries("Pie chart categories");
        if(type==0)
        {
            expense();
            for (int i = 0; i < expense_array.length; i++) {
                if (exp[i] != 0)
                    categorySeries.add(expense_array[i]+":\u20B9"+exp[i], exp[i]);
            }
        }
        if(type==1)
        {
            income();
            for (int i = 0; i < income_array.length; i++) {
                if (inc[i] != 0)
                    categorySeries.add(income_array[i]+":\u20B9"+inc[i], inc[i]);
            }
        }
        
            int[] colors = {Color.GREEN, Color.RED, Color.GRAY, Color.BLUE, Color.CYAN, Color.YELLOW};
            final DefaultRenderer defaultRenderer = new DefaultRenderer();
            for (int i = 0; i < categorySeries.getItemCount(); i++) {
                XYSeriesRenderer seriesRenderer = new XYSeriesRenderer();
                // Specify render options
                seriesRenderer.setColor(colors[i]);
                seriesRenderer.setDisplayChartValues(true);
                seriesRenderer.setDisplayChartValuesDistance(0);
                seriesRenderer.setChartValuesTextSize(10);
                seriesRenderer.setShowLegendItem(true);
                seriesRenderer.setHighlighted(true);
                defaultRenderer.addSeriesRenderer(seriesRenderer);
                defaultRenderer.setLabelsTextSize(30);
                defaultRenderer.setLabelsColor(Color.BLACK);
            }
defaultRenderer.setLegendHeight(0);
            // Specify default render options
            defaultRenderer.setPanEnabled(false);
            defaultRenderer.setAntialiasing(true);
            defaultRenderer.setShowLabels(true);
            defaultRenderer.setShowLegend(true);
            //defaultRenderer.setDisplayValues(true);
            defaultRenderer.setZoomButtonsVisible(true);
            //defaultRenderer.setExternalZoomEnabled(true);
            //defaultRenderer.setChartTitle("Pie Chart");
            defaultRenderer.setChartTitleTextSize(30);
            defaultRenderer.setLegendTextSize(30);
            defaultRenderer.setShowAxes(true);
            defaultRenderer.setScale((float)(0.9));
            //defaultRenderer.setFitLegend(true);
            // Get pie chart view
            final GraphicalView chartView = ChartFactory.getPieChartView(getActivity(), categorySeries, defaultRenderer);
            // Add the pie chart view to the layout to show
            chartlayout.addView(chartView);
            //defaultRenderer.setClickEnabled(true);
            chartView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SeriesSelection seriesSelection = chartView.getCurrentSeriesAndPoint();
                    if (seriesSelection == null) {
                        Toast.makeText(getActivity().getApplicationContext(), "No chart element selected", Toast.LENGTH_SHORT)
                                .show();
                    }else {
                        for (int i = 0; i < categorySeries.getItemCount(); i++) {
                            defaultRenderer.getSeriesRendererAt(i).setHighlighted(i == seriesSelection.getPointIndex());
                        }
                        chartView.repaint();
                        Toast.makeText(
                                getActivity().getApplicationContext(),
                                "Chart data point index " + seriesSelection.getPointIndex() + " selected"
                                        + " point value=" + seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    boolean expense() {
        for (int i=0;i<expense_array.length;i++)
            exp[i]=0;
        DB_transactions db = new DB_transactions(getActivity());
        Cursor c = db.display();
        if (c.getCount() == 0) {
            Toast.makeText(getActivity().getApplicationContext(), "Error..!! nothing to display..!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            while (c.moveToNext()) {
                if (c.getString(2).equals("Expense")) {
                    switch (c.getString(3)) {
                        case "Food":
                            exp[0] = exp[0] + c.getInt(1);
                            break;
                        case "Transport":
                            exp[1] = exp[1] + c.getInt(1);
                            break;
                        case "Petrol":
                            exp[2] = exp[2] + c.getInt(1);
                            break;
                        case "Entertainment":
                            exp[3] = exp[3] + c.getInt(1);
                            break;
                        case "Shopping":
                            exp[4] = exp[4] + c.getInt(1);
                            break;
                        case "Other":
                            exp[5] = exp[5] + c.getInt(1);
                            break;
                    }
                }
            }
            return true;
        }
    }
    boolean income() {
        for (int i = 0; i <income_array.length; i++)
            inc[i] = 0;
        DB_transactions db = new DB_transactions(getActivity());
        Cursor c = db.display();
        if (c.getCount() == 0) {
            Toast.makeText(getActivity().getApplicationContext(), "Error..!! nothing to display..!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            while (c.moveToNext()) {
                if(c.getString(2).equals("Income"))
                {switch (c.getString(3)) {
                    case "Gift":
                        inc[0] = inc[0] + c.getInt(1);
                        break;
                    case "Salary":
                        inc[1] = inc[1] + c.getInt(1);
                        break;
                    case "Other":
                        inc[2] = inc[2] + c.getInt(1);
                        break;

                }
            }
            }
            return true;
        }
    }
}

