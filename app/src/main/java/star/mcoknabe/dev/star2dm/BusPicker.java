package star.mcoknabe.dev.star2dm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import star.mcoknabe.dev.star2dm.Adapter.BusListAdapter;

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

        final BusListAdapter adapter = new BusListAdapter(this.getContext(),   FragmentOne.getBusRoute() );
        myList.setAdapter(adapter);
         onefragA =   this.getActivity();

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentOne onefrag = (FragmentOne)   onefragA.getSupportFragmentManager().findFragmentByTag("firstFragment");
                onefrag.updatebus(adapter.getItem(i));
                getDialog().cancel();

            }
        });

        return rootView;

    }


}
