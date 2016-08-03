package heardun.in.ars.chart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.mikephil.charting.data.ChartData;

import java.util.ArrayList;

import heardun.in.ars.doto.Allocations;

public abstract class ChartItem {

    protected static final int TYPE_BARCHART = 0;
    protected static final int TYPE_LINECHART = 1;
    protected static final int TYPE_PIECHART = 2;
    protected static final int TYPE_Horizontal_list = 3;

    protected ChartData<?> mChartData;
    protected ArrayList<?> mArrayData;

    public ChartItem(ChartData<?> cd) {
        this.mChartData = cd;
    }

    public ChartItem(ArrayList<?> cd) {
        this.mArrayData = cd;
    }

    public abstract int getItemType();

    public abstract View getView(int position, View convertView, Context c);

    public abstract int graphListType();

}
