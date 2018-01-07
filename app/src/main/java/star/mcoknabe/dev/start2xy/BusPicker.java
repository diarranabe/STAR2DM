package star.mcoknabe.dev.start2xy;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import star.mcoknabe.dev.start2xy.Adapter.BusListAdapter;
import star.mcoknabe.dev.start2xy.Adapter.BusSpinnerArrayAdapter;

import static android.view.View.*;

/**
 * Created by matok on 05/01/2018.
 */

public class BusPicker extends DialogFragment  {
    ListView myList ;
    FragmentActivity onefragA ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listbus, container,false);
        getDialog().setTitle("Bus List");
        myList = (ListView) rootView.findViewById(R.id.listViewbus) ;

        final BusListAdapter adapter = new BusListAdapter(this.getContext(),   One.getBusRoute() );
       // BusSpinnerArrayAdapter adapter0 = new BusSpinnerArrayAdapter(this.getContext(),  R.layout.itenspinner, One.getBusRoute() );
        myList.setAdapter(adapter);
         onefragA =   this.getActivity();

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                One onefrag = (One)   onefragA.getSupportFragmentManager().findFragmentByTag("firstFragment");
                onefrag.updatebus(adapter.getItem(i));
                getDialog().cancel();

            }
        });

        return rootView;

    }


}
