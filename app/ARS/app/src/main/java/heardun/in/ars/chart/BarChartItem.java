package heardun.in.ars.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;

import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

import heardun.in.ars.R;
import heardun.in.ars.config.Config;
import heardun.in.ars.config.Constants;
import heardun.in.ars.utils.MyMarkerView;
import heardun.in.ars.utils.Utils;


public class BarChartItem extends ChartItem {

    private Typeface mTf;
    private String title, total, total_title;
    public String TAG = BarChartItem.this.getClass().getSimpleName();
    int graph_list_type;
    Utils utils;

    LinearLayout.LayoutParams layoutParams;
    LinearLayout.LayoutParams layoutParams1;

    public BarChartItem(ChartData<?> cd, String title, int graph_list_type, Context c, String total_title, String total) {
        super(cd);

        this.title = title;
        this.total = total;
        this.total_title = total_title;
        this.graph_list_type = graph_list_type;
        utils = new Utils(c);
//        utils.showLog(TAG, "title is" + title, Config.UTILS);
        utils.showLog(TAG, "title is" + title + " data is " + Arrays.asList(cd.getDataSets().toArray()).toString(), Config.UTILS);


        //  mTf = Typeface.createFromAsset(c.getAssets(), "OpenSans-Regular.ttf");
    }

    @Override
    public int getItemType() {
        return TYPE_BARCHART;
    }


    @Override
    public View getView(int position, View convertView, Context c) {

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.list_item_barchart, null);
            holder.chart = (BarChart) convertView.findViewById(R.id.chart);
            holder.card_view = (CardView) convertView.findViewById(R.id.card_view);
            holder.barchart_lay = (LinearLayout) convertView.findViewById(R.id.barchart_lay);
            holder.title = (TextView) convertView.findViewById(R.id.graph_title);
            holder.graph_val_lay = (LinearLayout) convertView.findViewById(R.id.graph_val_lay);
            holder.graph_total_title = (TextView) convertView.findViewById(R.id.graph_total_title);
            holder.graph_total_val = (TextView) convertView.findViewById(R.id.graph_total_val);
//            holder.switch_bar = (SwitchCompat) convertView.findViewById(R.id.switch_bar);

            //  holder.barchart_lay.getLayoutParams().width = screenwidth;

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // apply styling
//        MyMarkerView mv = new MyMarkerView(c, R.layout.custom_marker_view, graph_list_type);
//        holder.chart.setMarkerView(mv);


        holder.title.setText(title);
        holder.chart.setDescription("");
        holder.chart.setDrawGridBackground(true);
        holder.chart.setGridBackgroundColor(ContextCompat.getColor(c, R.color.white));
        holder.chart.setPinchZoom(true);
        holder.chart.setDrawBarShadow(false);
        holder.chart.getLegend().setWordWrapEnabled(true);


        XAxis xAxis = holder.chart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        // xAxis.setTypeface(mTf);
        xAxis.disableGridDashedLine();
        xAxis.setDrawAxisLine(false);

        YAxis leftAxis = holder.chart.getAxisLeft();
        // leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5, false);
        leftAxis.setSpaceTop(20f);
        leftAxis.setDrawGridLines(true);
        holder.chart.getAxisRight().setEnabled(false);

        //YAxis rightAxis = holder.chart.getAxisRight();
        //  rightAxis.setTypeface(mTf);
        //rightAxis.setLabelCount(5, false);
        //rightAxis.setSpaceTop(20f);

        //  mChartData.setValueTypeface(mTf);

        // set data
        // do not forget to refresh the chart
//
        holder.chart.animateY(700);
        holder.chart.setTouchEnabled(true);

        // holder.chart.highlightValues(null);


        if (mChartData != null) {
            try {
                utils.showLog(TAG, graph_list_type + " mChartData.getDataSetCount() " + mChartData.getDataSetCount(),
                        Config.BarChartItem);
                if (mChartData.getDataSetCount() > 0) {
                    holder.chart.setData((BarData) mChartData);

                    holder.chart.getData().setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                            return v == 0 ? "" : String.valueOf(v);
                        }
                    });

                    holder.chart.notifyDataSetChanged();


                    Legend l = holder.chart.getLegend();
                    l.setEnabled(true);

                    try {

                        utils.showLog(TAG, graph_list_type + " hiiiiiiiiiiiii ", Config.BarChartItem);

                        utils.showLog(TAG, graph_list_type + " Bar data is " +
                                        Arrays.asList(holder.chart.getBarData().getDataSets()).toString(),
                                Config.BarChartItem);

                        for (int i = 0; i < holder.chart.getBarData().getDataSetCount(); i++) {
                            utils.showLog(TAG, graph_list_type + " legends labels are " +
                                            Arrays.asList(holder.chart.getBarData().getDataSetByIndex(i).getStackLabels()).toString(),
                                    Config.BarChartItem);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (l.getLabels() != null && l.getLabels().length > 6) {
                        setparams(l.getLabels().length);
                    } else {
                        setparams(6);
                    }

                    l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
                    l.setTextColor(Color.BLACK);

                    if (!total.isEmpty()) {
                        utils.showLog(TAG, "total is in bar graph " + total, Config.DASHBOARD);
                        holder.graph_val_lay.setVisibility(View.VISIBLE);
                        holder.graph_total_title.setText("" + total_title);
                        holder.graph_total_val.setText(" - " + total);
                    } else {
                        holder.graph_val_lay.setVisibility(View.GONE);
                    }


                    //  holder.card_view.setLayoutParams(layoutParams);
                    holder.chart.setLayoutParams(layoutParams1);





       /* final ViewHolder finalHolder = holder;
        holder.switch_bar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    finalHolder.chart.setData(new Utils().generateDataBar(Constants.GRAPH_MONTHLY_ALLOC_STYLES));
                else
                    finalHolder.chart.setData(new Utils().generateDataBar(Constants.GRAPH_MONTHLY_ALLOC_SUBBRAND));

            }
        });*/

                    holder.chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                        @Override
                        public void onValueSelected(Entry entry, int i, Highlight highlight) {


                        }

                        @Override
                        public void onNothingSelected() {

                        }
                    });

                } else {
                    setparams(6);
                    holder.chart.setData(new BarData());
                    holder.chart.setLayoutParams(layoutParams1);
                    holder.chart.setTouchEnabled(false);
                    holder.chart.notifyDataSetChanged();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            setparams(6);
            holder.chart.setData(new BarData());
            holder.chart.setLayoutParams(layoutParams1);
            holder.chart.setTouchEnabled(false);
            holder.chart.notifyDataSetChanged();
        }
        holder.chart.invalidate();

        return convertView;
    }

    @Override
    public int graphListType() {
        new Utils().showLog(TAG, "sel pos is " + graph_list_type, Config.DASHBOARD);
        return graph_list_type;
    }

    private static class ViewHolder {
        BarChart chart;
        LinearLayout barchart_lay, graph_val_lay;
        TextView title, graph_total_title, graph_total_val;
        CardView card_view;
//        SwitchCompat switch_bar;

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
