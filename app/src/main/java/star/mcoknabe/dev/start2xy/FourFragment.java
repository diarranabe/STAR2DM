package star.mcoknabe.dev.start2xy;

import android.content.Context;
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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import star.mcoknabe.dev.start2xy.Adapter.ArretListAdapter;
import star.mcoknabe.dev.start2xy.model.ArretTime;
import star.mcoknabe.dev.start2xy.model.StarContract;
import star.mcoknabe.dev.start2xy.model.Stop;
import star.mcoknabe.dev.start2xy.model.StopTime;

import static star.mcoknabe.dev.start2xy.TwoFragment.agrsone;



public class FourFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private String mParam5;
    private String mParam6;

    private MainActivity mListener;

    TextView ligne ;
    TextView dateheur ;
    TextView dedtination ;
    ListView arretHeurList ;

    public FourFragment() {

        // Required empty public constructor
    }
    List<ArretTime> arretTimes = new ArrayList<>();
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FourFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FourFragment newInstance(String param1, String param2) {
        FourFragment fragment = new FourFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static FourFragment newInstance() {
        FourFragment fragment = new FourFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
            mParam5 = getArguments().getString(ARG_PARAM5);
            mParam6 = getArguments().getString(ARG_PARAM6);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_four,container, false);
        ligne  = view.findViewById(R.id.textViewLigne4);
        dateheur = view.findViewById(R.id.textViewdateHeur4);
        dedtination = view.findViewById(R.id.textViewDestination4);
        arretHeurList = view.findViewById(R.id.listBus4);
        getArretHeur(mParam4,mParam5);

       ArrayList<String> sts  = new ArrayList<>();
       for (ArretTime arretTim: arretTimes){
           sts.add(arretTim.nom +" < -- > " + arretTim.heur) ;
       }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(),R.layout.arretlayout, sts);

       arretHeurList.setAdapter(adapter);

        dateheur.setText(mParam1+" / "+ mParam2);
        ligne.setText(""+mParam6);
        // direction a chercher !
        dedtination.setText(mParam4);

        // Inflate the layout for this fragment
        return view;

    }



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

    }


    public List<ArretTime> getArretHeur(String idTrip, String heur) {


        //String[] selargs = {"19061", "22:30:00"};
        String[] selargs = {idTrip, heur};
        Cursor cursor = mListener.getContentResolver().query(Uri.withAppendedPath(StarContract.AUTHORITY_URI, "arrettoterminus"),
                null, null, selargs,
                null);

        Log.d("STARXTEST", "from provider ... start");
        while (cursor.moveToNext()) {
                ArretTime iten = new ArretTime(cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.NAME)),cursor.getString(cursor.getColumnIndex(StarContract.StopTimes.StopTimeColumns.ARRIVAL_TIME))) ;
            arretTimes.add(iten);
            Log.d("STARXTEST", " var " + iten.nom + " - " + iten.heur);
        }



        return arretTimes;
    }


}
