
package heardun.in.ars.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

import heardun.in.ars.R;
import heardun.in.ars.config.Config;
import heardun.in.ars.config.Constants;
import heardun.in.ars.utils.Utils;


public class PieChartItem extends ChartItem {

    public static String TAG = PieChartItem.class.getCanonicalName();
    private Typeface mTf;
    private SpannableString mCenterText;
    public String title;
    Utils utils;
    public int graph_list_type;

    public PieChartItem(ChartData<?> cd, String title, int graph_list_type, Context c) {
        super(cd);

        this.title = title;
        this.graph_list_type = graph_list_type;


//        mTf = Typeface.createFromAsset(c.getAssets(), "OpenSans-Regular.ttf");
        mCenterText = generateCenterText();
        utils = new Utils(c);

        utils.showLog(TAG, "graph item type is " + graph_list_type, Config.DASHBOARD);
    }

    @Override
    public int getItemType() {
        return TYPE_PIECHART;
    }


    @Override
    public View getView(int position, View convertView, Context c) {

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.list_item_piechart, null);

            holder.chart = (PieChart) convertView.findViewById(R.id.chart);
            holder.graph_title = (TextView) convertView.findViewById(R.id.graph_title);

            convertView.setTag(holder);

        } else {
            utils.showLog(TAG, " chart size is " + mChartData.getDataSetCount(), Config.DASHBOARD);
            holder = (ViewHolder) convertView.getTag();
        }

        holder.graph_title.setText(title);

        // apply styling
        holder.chart.setDescription("");

//        holder.chart.setHoleRadius(52f);
//        holder.chart.setTransparentCircleRadius(57f);
        //holder.chart.setCenterText(mCenterText);
        //  holder.chart.setCenterTextTypeface(mTf);
        holder.chart.setCenterTextSize(9f);
        holder.chart.setUsePercentValues(true);
        // holder.chart.setExtraOffsets(5, 10, 50, 10);
        holder.chart.setRotationEnabled(false);
        holder.chart.getLegend().setWordWrapEnabled(true);


        mChartData.setValueTypeface(mTf);
        mChartData.setValueTextSize(11f);
        mChartData.setValueTextColor(Color.BLACK);


        // set data
        try {
            if (mChartData != null) {
                utils.showLog(TAG, "item sieze is" + mChartData.getDataSetCount(), Config.DASHBOARD);

                if (mChartData.getDataSetCount() > 0)
                    holder.chart.setData((PieData) mChartData);

                holder.chart.getData().setValueFormatter(new ValueFormatter() {
                    protected DecimalFormat mFormat = new DecimalFormat("###,###,##0.0");

                    @Override
                    public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                        return v == 0 ? "" : this.mFormat.format((double) v) + "%";
                    }
                });
                holder.chart.notifyDataSetChanged();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Legend l = holder.chart.getLegend();
        l.setPosition(LegendPosition.RIGHT_OF_CHART);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setTextSize(15);


        // do not forget to refresh the chart
        // holder.chart.invalidate();
        holder.chart.animateY(900);
        // holder.chart.setTouchEnabled(true);

        holder.chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {

                try {
                 /*   utils.showLog(TAG, "Constants.GRAPH_INBOUND_LIST size is " + Constants.GRAPH_INBOUND_LIST.size(), Config.DASHBOARD);
                    utils.showLog(TAG, "graph_list_type" + graph_list_type +
                                    " highlight x pos is " + Constants.GRAPH_INBOUND_LIST.get(highlight.getXIndex()).getTitle() +
                                    " entry string is " + entry.toString()
                            , Config.DASHBOARD);*/

//                if (Constants.GRAPH_INBOUND == graph_list_type)
                    utils.dashBoardValueSel_process(graph_list_type, highlight.getXIndex());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        return convertView;
    }

    @Override
    public int graphListType() {
        new Utils().showLog(TAG, "sel pos is " + graph_list_type, Config.DASHBOARD);
        return graph_list_type;
    }

    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("MPAndroidChart\ncreated by\nPhilipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.6f), 0, 14, 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.VORDIPLOM_COLORS[0]), 0, 14, 0);
        s.setSpan(new RelativeSizeSpan(.9f), 14, 25, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, 25, 0);
        s.setSpan(new RelativeSizeSpan(1.4f), 25, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 25, s.length(), 0);
        return s;
    }

    private static class ViewHolder {
        PieChart chart;
        TextView graph_title;
    }
}
