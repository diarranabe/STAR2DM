package star.mcoknabe.dev.star2dm.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import star.mcoknabe.dev.star2dm.R;
import star.mcoknabe.dev.star2dm.model.BusRoute;

/**
 * Created by matok on 05/01/2018.
 */

public class BusSpinnerArrayAdapter extends ArrayAdapter<BusRoute> {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private final List<BusRoute> busLists;
    private final int mResource;
    BusRoute busData;

    public BusSpinnerArrayAdapter(@NonNull Context context, @LayoutRes int resource,
                                  @NonNull List busList) {
        super(context, resource, 0, busList);

        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        busLists = busList;
    }

    @Nullable
    @Override
    public BusRoute getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public @NonNull View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent){
        View view;

        if (convertView == null) {
            view = mInflater.inflate(mResource, parent, false);
        } else {
            view = convertView;
        }



        TextView name = (TextView) view.findViewById(R.id.textSpinner);
        ImageView imageback = (ImageView) view.findViewById(R.id.imagebackSpinner);

        busData = busLists.get(position);

        name.setText(busData.getShortName());
        name.setTextColor(Color.parseColor("#"+busData.getTextColor())) ;
        imageback.setBackgroundColor(Color.parseColor("#"+busData.getColor()));

        return view;
    }
}