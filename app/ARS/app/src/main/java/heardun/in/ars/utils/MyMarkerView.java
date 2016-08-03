
package heardun.in.ars.utils;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.Utils;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Iterator;

import heardun.in.ars.R;
import heardun.in.ars.config.Config;
import heardun.in.ars.config.Constants;
import heardun.in.ars.doto.CommanDataSet;

/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
public class MyMarkerView extends MarkerView {

    private String TAG = MyMarkerView.class.getSimpleName();
    private TextView tvContent;

    heardun.in.ars.utils.Utils utils;
    int graph_list_type;

    public MyMarkerView(Context context, int layoutResource, int graph_list_type) {
        super(context, layoutResource);
        utils = new heardun.in.ars.utils.Utils(context);
        this.graph_list_type = graph_list_type;
        utils.showLog(TAG, "mark view", Config.UTILS);
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;

            utils.showLog(TAG, "mark view height is " + ce.getHigh(), Config.UTILS);
            tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));
        } else {
            if (graph_list_type == Constants.GRAPH_SELLTHRU) {
                utils.showLog(TAG, "graph_list_type is " + graph_list_type + " index is " + e.getXIndex() + " mark view val is ", Config.UTILS);
                tvContent.setText("" + utils.sellThruMarkView(e.getXIndex()));
            } else {
                utils.showLog(TAG, "graph_list_type is " + graph_list_type + " index is " + e.getXIndex() + " mark view val is " + getMarkValues(e.getXIndex()), Config.UTILS);
                tvContent.setText("" + getMarkValues(e.getXIndex()));
            }
        }
    }

    @Override
    public int getXOffset(float v) {
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset(float v) {
        return -getHeight();
    }

    private String getMarkValues(int index) {

        String val = "";
        CommanDataSet dataSet;
        if (graph_list_type == Constants.GRAPH_MONTHLY_ALLOC_SUBBRAND) {
            dataSet = Constants.GRAPH_MONTHLY_ALLOC_SUB_BRANDS_LIST.get(index);
            return utils.JSonObjtoString(dataSet.getData());
        } else if (graph_list_type == Constants.GRAPH_MONTHLY_ALLOC_STYLES) {
            utils.showLog(TAG, "styles list size is " + Constants.GRAPH_MONTHLY_ALLOC_STYLES_LIST.size(), Config.UTILS);
            dataSet = Constants.GRAPH_MONTHLY_ALLOC_STYLES_LIST.get(index);
            utils.showLog(TAG, "obj data is" + dataSet.getKey_val() + " , " + dataSet.getData().toString(), Config.UTILS);
            return utils.JSonObjtoString(dataSet.getData());
        } else if (graph_list_type == Constants.GRAPH_PLANO_ADH_OPT_QTY) {
            dataSet = Constants.GRAPH_PLANO_ADH_OPT_QTY_LIST.get(index);
            return utils.JSonObjtoString(dataSet.getData());
        } else if (graph_list_type == Constants.GRAPH_PLANO_ADH_ACT_QTY) {
            dataSet = Constants.GRAPH_PLANO_ADH_ACT_QTY_LIST.get(index);
            return utils.JSonObjtoString(dataSet.getData());
        } else if (graph_list_type == Constants.GRAPH_STORE_SUFF) {
            dataSet = Constants.GRAPH_STORE_STOCK_SUFF.get(index);
            return utils.JSonObjtoString(dataSet.getData());
        } else if (graph_list_type == Constants.GRAPH_SELLTHRU) {
            dataSet = Constants.GRAPH_SELL_THRU_LIST.get(index);
            return utils.JSonObjtoString(dataSet.getData());
        }
        return val;

    }
}
