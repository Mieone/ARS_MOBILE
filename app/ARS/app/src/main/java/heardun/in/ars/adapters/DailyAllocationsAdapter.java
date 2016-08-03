package heardun.in.ars.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


import butterknife.BindView;
import butterknife.ButterKnife;
import heardun.in.ars.R;
import heardun.in.ars.config.Constants;
import heardun.in.ars.doto.Allocations;
import heardun.in.ars.utils.Utils;

/**
 * Created by sujith on 7/5/16.
 */
public class DailyAllocationsAdapter extends RecyclerView.Adapter<DailyAllocationsAdapter.ViewHolder> {

    ViewHolder holder = null;
    ArrayList<?> allocatins_list = new ArrayList<>();
    Context context;
    Utils utils;

    public DailyAllocationsAdapter(ArrayList<?> allocatins_list, Context context) {

        this.allocatins_list = dataFormateList((ArrayList<Allocations>) allocatins_list);
        if (allocatins_list.isEmpty())
            this.allocatins_list = allocatins_list;
        this.context = context;
        utils = new Utils(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_dailyallocations, parent, false);

        holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Allocations item = (Allocations) allocatins_list.get(position);

        holder.title.setText(context.getResources().getString(utils.StringFromresourse(item.getTitle())));
        holder.value.setText(item.getVal());
        holder.allocation_lay.setBackgroundColor(Constants.DailyAllocationColors[position]);
    }

    @Override
    public int getItemCount() {
        return allocatins_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.value)
        TextView value;

        @BindView(R.id.allocation_lay)
        LinearLayout allocation_lay;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private ArrayList<Allocations> dataFormateList(ArrayList<Allocations> allocatins_list) {

        ArrayList<Allocations> item_list = new ArrayList<>();
        datalist(allocatins_list, "total_allocation", item_list);
        datalist(allocatins_list, "CR", item_list);
        datalist(allocatins_list, "FM", item_list);
        datalist(allocatins_list, "SM", item_list);
        datalist(allocatins_list, "NS", item_list);
        datalist(allocatins_list, "CP", item_list);

        return item_list;

    }

    private void datalist(ArrayList<Allocations> allocatins_list, String label, ArrayList<Allocations> lael_list) {

        for (Allocations item : allocatins_list) {

            if (item.getTitle().contains(label)) {
                lael_list.add(item);
            }
        }

    }
}
