package heardun.in.ars.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

import heardun.in.ars.chart.ChartItem;
import heardun.in.ars.R;
import heardun.in.ars.config.Config;
import heardun.in.ars.utils.Utils;

/**
 * Created by sujith on 10/5/16.
 */
public class AllocationAdapter extends ChartItem {

    String TAG = AllocationAdapter.class.getSimpleName();
    ViewHolder holder = null;
    ArrayList<?> allocatins_list = new ArrayList<>();
    Context context;
    Utils utils;
    DailyAllocationsAdapter adapter;
    int graph_list_type;

    public AllocationAdapter(ArrayList<?> cd, int graph_list_type, Context context) {
        super(cd);
        allocatins_list = mArrayData;
        this.context = context;
        utils = new Utils(context);
        this.graph_list_type = graph_list_type;
        utils.showLog(TAG, "allocation list size is " + allocatins_list.size(), Config.DASHBOARD);
    }

    @Override
    public int getItemType() {
        return TYPE_Horizontal_list;
    }

    @Override
    public View getView(int position, View convertView, Context c) {

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.adapter_horizontal_listview, null);

            holder.listview = (RecyclerView) convertView.findViewById(R.id.listview);
            holder.listview.setLayoutManager(new LinearLayoutManager(c));
            holder.listview.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.HORIZONTAL, false));
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {

            utils.showLog(TAG, "alloc list size is " + allocatins_list.size(), Config.DASHBOARD);
            adapter = new DailyAllocationsAdapter(allocatins_list, context);
            holder.listview.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    @Override
    public int graphListType() {new Utils().showLog(TAG, "sel pos is " +graph_list_type , Config.DASHBOARD);
        return graph_list_type;
    }

    public class ViewHolder {

        RecyclerView listview;

    }

}
