package star.mcoknabe.dev.start2xy;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import star.mcoknabe.dev.start2xy.Adapter.BusSpinnerArrayAdapter;
import star.mcoknabe.dev.start2xy.model.BusRoute;
import star.mcoknabe.dev.start2xy.model.StarContract;


public class One extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    static Bundle args = new Bundle();
    public static final String ARG_PARAM1 = "param1";
    public static final String ARG_PARAM2 = "param2";
    public static final String ARG_PARAM3 = "param3";
    Activity master ;
    FragmentActivity onefragO;

    TextView dateTextview ;
    TextView timerTextview ;

    private Spinner spinnerBus;
    private TextView selectedBus;
    private String selectedBusinfo;
    private RadioButton direction1 ;
    private RadioButton direction2 ;

    static public Button continuer;
    static public Button annuler;

    private RadioGroup radioGroupDestination;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private BusRoute mParam3;

    private static MainActivity mListener;

    public One() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static One newInstance() {
        One fragment = new One();
        return fragment;
    }

    public static One newInstance(String param1, String param2) {
        One fragment = new One();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public  One newInstance1(String param1) {
        Log.d("XXXX"," passed"+param1+" -*- "+mParam2);
        One fragment = new One();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, mParam2);
        fragment.setArguments(args);
        return fragment;
    }

    public  One newInstance2(String param2) {
        One fragment = new One();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, mParam1);
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
       // View view =  View.inflate(this.getContext(),R.layout.fragment_one, container) ;
        View view =  inflater.inflate(R.layout.fragment_one,container, false);

         onefragO =   this.getActivity();

       //  master = (MainActivity) this.getParentFragment().getActivity();


        timerTextview = view.findViewById(R.id.textView4) ;
        dateTextview = view.findViewById(R.id.textView3) ;
        radioGroupDestination = (RadioGroup) view.findViewById(R.id.radiodestination);
        direction1 = view.findViewById(R.id.radioButton) ;
        
        direction2 = view.findViewById(R.id.radioButton2) ;
        selectedBus = view.findViewById(R.id.spinner) ;
        continuer = (Button) view.findViewById(R.id.button4) ;
        annuler = view.findViewById(R.id.button3) ;

        continuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if (direction1.isChecked() || direction2.isChecked())
                mListener.switchFrag (goFrag2(),2)  ;

            }
        });

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            dateTextview.setText(mParam2);
            timerTextview.setText(mParam1);
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mListener = (MainActivity) context;
        }/* else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mParam1 = null;
        mParam3 = null;
        mParam2 = null;
        args =null;
    }

    public void updatetimer(String s){
        mParam1 = s ;
        timerTextview.setText(mParam1);
    }

    public void updatedate(String s){
        mParam2 = s ;
        dateTextview.setText(mParam2);
    }

    public void updatebus(BusRoute s){
        mParam3 = s ;
        String[] direction  = s.getLongName().split("<>",2);
        direction1.setText(direction[0]+" VERS "+direction[1]);
        direction2.setText(direction[1]+" VERS "+direction[0]);
        selectedBus.setText(s.getShortName());
        selectedBus.setTextColor(Color.parseColor("#"+s.getTextColor())) ;
        selectedBus.setBackgroundColor(Color.parseColor("#"+s.getColor()));

        args.putString(TwoFragment.ARG_PARAM1, ""+mParam2);
        args.putString(TwoFragment.ARG_PARAM2, ""+mParam1);
        args.putString(TwoFragment.ARG_PARAM3, ""+mParam3.getShortName());


    }

    public static List<BusRoute> getBusRoute(){

        Cursor cursor = mListener.getContentResolver().query(Uri.withAppendedPath(StarContract.AUTHORITY_URI,StarContract.BusRoutes.CONTENT_PATH),
                null, null, null,
                StarContract.BusRoutes.BusRouteColumns.ROUTE_ID);
        List<BusRoute> busRoutes = new ArrayList<>();
        int i = 0;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                BusRoute item = new BusRoute(
                        cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.ROUTE_ID)),
                        cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.SHORT_NAME)),
                        cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.LONG_NAME)),
                        cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.TYPE)),
                        cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.COLOR)),
                        cursor.getString(cursor.getColumnIndex(StarContract.BusRoutes.BusRouteColumns.TEXT_COLOR))
                );
                i++;
                busRoutes.add(item);
            } while (cursor.moveToNext());
        }

        return busRoutes ;
    }

    public  Bundle goFrag2(){
        Log.d("XXXX", "okm+ hhj");
       Log.e("XXXX", " .>" + args.getString(TwoFragment.ARG_PARAM3) +" ---" + mParam3.getShortName()) ;
        String[] direction  = mParam3.getLongName().split("<>",2);
        int idbt = 0 ;
        if (!direction1.isChecked()){ idbt = 1 ; };
        Log.e("XXXX", " .>" + idbt +" ---" + mParam3.getShortName()) ;

        args.putString(TwoFragment.ARG_PARAM1, ""+mParam2);
        args.putString(TwoFragment.ARG_PARAM2, ""+mParam1);
        args.putString(TwoFragment.ARG_PARAM3, ""+mParam3.getShortName());
        args.putString(TwoFragment.ARG_PARAM4, ""+direction[idbt]);
        args.putString(TwoFragment.ARG_PARAM5, ""+mParam3.getRoute_id());
        args.putString(TwoFragment.ARG_PARAM6, ""+idbt);


        return args ;
    }


}
