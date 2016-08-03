package heardun.in.ars.adapters;

import android.content.Context;
import android.telecom.Conference;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import heardun.in.ars.chart.ChartItem;
import heardun.in.ars.config.Config;
import heardun.in.ars.utils.Utils;

/**
 * Created by sujith on 9/5/16.
 */
public class ChartDataAdapter extends ArrayAdapter<ChartItem> {

    public String TAG = ChartDataAdapter.class.getSimpleName();
    List<ChartItem> items = new ArrayList<>();

    public ChartDataAdapter(Context context, List<ChartItem> objects) {
        super(context, 0, objects);
        items.addAll(objects);
//        new Utils().showLog(TAG, "list size is " + items.size(), Config.DASHBOARD);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
/*
        new Utils().showLog(TAG, "list size is " + items.size() +
                " pos are " + position, Config.DASHBOARD);
*/
        return getItem(position).getView(position, convertView, getContext());
    }

    @Override
    public int getItemViewType(int position) {
        // return the views type
        return getItem(position).getItemType();
    }

    @Override
    public int getViewTypeCount() {
        return 4; // we have 3 different item-types
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
