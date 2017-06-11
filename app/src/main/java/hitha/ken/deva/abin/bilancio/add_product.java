package hitha.ken.deva.abin.bilancio;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link add_product.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link add_product#newInstance} factory method to
 * create an instance of this fragment.
 */
public class add_product extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Calendar myCalendar= Calendar.getInstance();
    private OnFragmentInteractionListener mListener;

    EditText editName,editDate,editExpiry,editService,editFrom;
    Button bt_sel;
    DB_product myDb;
    DB_reminders db;
    public ImageView imageView;
    Bitmap photo;

    public add_product() {
        // Required empty public constructor
    }

    public static add_product newInstance(String param1, String param2) {
        add_product fragment = new add_product();
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
        View v= inflater.inflate(R.layout.add_product,container,false);
        myDb=new DB_product(getActivity());
        db=new DB_reminders(getActivity());
        imageView =(ImageView)v.findViewById(R.id.cam);
        imageView.setImageResource(R.mipmap.camera);
        editName=(EditText)v.findViewById(R.id.con_no);
        editDate=(EditText)v.findViewById(R.id.bill_no);
        editExpiry=(EditText)v.findViewById(R.id.editText3);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy", Locale.getDefault());
        Date date = new Date();
        editDate.setText(dateFormat.format(date));
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR,2);
        date=c.getTime();
        editExpiry.setText(dateFormat.format(date));
        editExpiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(),date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editService=(EditText)v.findViewById(R.id.editText4);
        editFrom=(EditText)v.findViewById(R.id.editText5);
        bt_sel=(Button) v.findViewById(R.id.add_to_db);
        bt_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editName.getText().toString().trim().equals(""))
                    Toast.makeText(getActivity(),"Enter product name",Toast.LENGTH_LONG).show();
                else {
                    boolean inserted = myDb.addProduct(editName.getText().toString(),
                            editDate.getText().toString(),
                            editExpiry.getText().toString(),
                            editService.getText().toString(),
                            editFrom.getText().toString(), photo);
                    setAlarm(myCalendar);
                    if (inserted == true)
                        Toast.makeText(getActivity(), "Data inserted", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getActivity(), "Data not inserted", Toast.LENGTH_LONG).show();
                    reset();
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,0);
            }
        });
        return v;

    }
    DatePickerDialog.OnDateSetListener date1 =new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();

        }
    };
    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editExpiry.setText(sdf.format(myCalendar.getTime()));
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
        public void onFragmentInteraction(Uri uri);
    }

     public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && data!=null) {
            photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            ByteArrayOutputStream out=new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG,100,out);
            byte ByteArr[]=out.toByteArray();}
        }
    void reset()
    {
        editFrom.setText("");
        editName.setText("");
        editService.setText("");
        imageView.setImageResource(R.mipmap.camera);
    }
    private void setAlarm(Calendar target){
        int currentApiVersion = Build.VERSION.SDK_INT;
        //Log.e(txt.getText()+hour,""+min);
        target.set(Calendar.HOUR_OF_DAY,8);
        target.set(Calendar.MINUTE,0);
        target.set(Calendar.SECOND,0);
        int day=target.get(Calendar.DAY_OF_MONTH);
        db.addtimer(target.getTime().toString(),editName.getText().toString());
        Intent intent = new Intent(getActivity().getBaseContext(), AlarmReceiver.class);
        intent.putExtra("name",editName.getText().toString());
        intent.putExtra("date",target.getTime().toString());
        intent.putExtra("id",21+day);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getBaseContext(),21+day, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, target.getTimeInMillis(), pendingIntent);
    }
}


