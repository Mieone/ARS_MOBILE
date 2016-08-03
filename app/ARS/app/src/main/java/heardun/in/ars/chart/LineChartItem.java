
package heardun.in.ars.chart;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;

import heardun.in.ars.R;
import heardun.in.ars.config.Config;
import heardun.in.ars.utils.MyMarkerView;
import heardun.in.ars.utils.Utils;

public class LineChartItem extends ChartItem {

    private Typeface mTf;
    Utils utils;
    String TAG = LineChartItem.class.getSimpleName();
    String title, total_title, total;

    int graph_list_type;

    LinearLayout.LayoutParams layoutParams;
    LinearLayout.LayoutParams layoutParams1;

    public LineChartItem(ChartData<?> cd, String title, int graph_list_type, Context c, String total_title, String total) {
        super(cd);
        utils = new Utils();
        this.title = title;
        this.total = total;
        this.total_title = total_title;
        this.graph_list_type = graph_list_type;
        utils.showLog(TAG, "line chart", Config.DASHBOARD);
        // mTf = Typeface.createFromAsset(c.getAssets(), "OpenSans-Regular.ttf");
    }

    @Override
    public int getItemType() {
        return TYPE_LINECHART;
    }


    @Override
    public View getView(int position, View convertView, Context c) {

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();
            convertView = LayoutInflater.from(c).inflate(R.layout.list_item_linechart, null);
            holder.chart = (LineChart) convertView.findViewById(R.id.chart);
            holder.card_view = (CardView) convertView.findViewById(R.id.card_view);
            holder.graph_title = (TextView) convertView.findViewById(R.id.graph_title);
            holder.graph_val_lay = (LinearLayout) convertView.findViewById(R.id.graph_val_lay);
            holder.graph_total_title = (TextView) convertView.findViewById(R.id.graph_total_title);
            holder.graph_total_val = (TextView) convertView.findViewById(R.id.graph_total_val);
            convertView.setTag(holder);
            utils.showLog(TAG, "line chart view ", Config.DASHBOARD);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }


//        MyMarkerView mv = new MyMarkerView(c, R.layout.custom_marker_view, graph_list_type);
//        holder.chart.setMarkerView(mv);

        holder.graph_title.setText(title);
        // apply styling
        // holder.chart.setValueTypeface(mTf);


        if (!total.isEmpty()) {
            holder.graph_val_lay.setVisibility(View.VISIBLE);
            holder.graph_total_title.setText("" + total_title);
            holder.graph_total_val.setText(" - " + total + "%");
        } else {
            holder.graph_val_lay.setVisibility(View.GONE);
        }


        holder.chart.setDescription("");
        holder.chart.setDrawGridBackground(false);
        holder.chart.getLegend().setWordWrapEnabled(true);
        holder.chart.setTouchEnabled(true);
        holder.chart.getAxisRight().setEnabled(false);
        holder.chart.setPinchZoom(true);


        XAxis xAxis = holder.chart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        // xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        YAxis leftAxis = holder.chart.getAxisLeft();
        // leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5, false);


        if (mChartData != null && mChartData.getDataSetCount() > 0) {

            holder.chart.setData((LineData) mChartData);
            holder.chart.getData().setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                    return v == 0 ? "" : String.valueOf(v);
                }
            });


            holder.chart.notifyDataSetChanged();


            Legend l = holder.chart.getLegend();

            if (l.getLabels() != null && l.getLabels().length > 6) {
                setparams(l.getLabels().length);
            } else {
                setparams(6);
            }

            //holder.card_view.setLayoutParams(layoutParams);
            holder.chart.setLayoutParams(layoutParams1);

        /*YAxis rightAxis = holder.chart.getAxisRight();
        // rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(5, false);
        rightAxis.setDrawGridLines(false);
*/
            utils.showLog(TAG, "line chart data is " + mChartData.getXValCount(), Config.DASHBOARD);
            // set data

            // do not forget to refresh the chart
            // holder.chart.invalidate();
            holder.chart.animateX(750);


            l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);

        } else {
            setparams(6);
            holder.chart.setData(new LineData());
            holder.chart.setLayoutParams(layoutParams1);
            holder.chart.setTouchEnabled(false);
            holder.chart.notifyDataSetChanged();

        }

        return convertView;
    }

    @Override
    public int graphListType() {
        new Utils().showLog(TAG, "sel pos is " + graph_list_type, Config.DASHBOARD);
        return graph_list_type;
    }

    private static class ViewHolder {
        LineChart chart;
        TextView graph_title;
        CardView card_view;
        LinearLayout graph_val_lay;
        TextView graph_total_title, graph_total_val;
    }

    private void setparams(int length) {

        if (length > 6) {

            //  layoutParams = utils.setLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 900, 6, 10, 6, 10);
            layoutParams1 = utils.setLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 750, 2, 2, 2, 2);

        } else {

            //  layoutParams = utils.setLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 620, 6, 10, 6, 10);

            layoutParams1 = utils.setLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500, 2, 2, 2, 2);
        }
    }

}
