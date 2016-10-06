package heardun.in.ars.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.common.ConnectionResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import heardun.in.ars.ArsRequest;
import heardun.in.ars.BuildConfig;
import heardun.in.ars.R;
import heardun.in.ars.Usersession;
import heardun.in.ars.activity.LoginActivity;
import heardun.in.ars.activity.ResponseError;
import heardun.in.ars.config.Config;
import heardun.in.ars.config.Constants;
import heardun.in.ars.config.Serverconfig;
import heardun.in.ars.dashboard.DashBoardDetailActivity;
import heardun.in.ars.dashboard.Dashboard;
import heardun.in.ars.doto.Allocations;
import heardun.in.ars.doto.CommanDataSet;
import heardun.in.ars.doto.DahsBoardDeatils;


/**
 * Created by headrun on 12/10/15.
 */
public class Utils {

    public static String TAG = Utils.class.getSimpleName();
    static Context context;
    public static Usersession usersession;
    public static DateFormat MiebachDate_formate = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat Miebach_Cust_Date = new SimpleDateFormat("dd MMM");
    public static SimpleDateFormat MIEBACH_PARAM_DATE = new SimpleDateFormat("yyyy-MM-dd");
    public SimpleDateFormat Miebach_Cust_Time = new SimpleDateFormat("hh:mm:ss a");
    public static Random rand = new Random();

    public Utils(Context context) {
        this.context = context;
        usersession = new Usersession(context);
    }

    public Utils() {
    }

    public void hideKeyboard(View view) {

        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    public void getwindowsize() {

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        Constants.screenheight = displayMetrics.heightPixels / displayMetrics.density;
        Constants.screenwidth = displayMetrics.widthPixels / displayMetrics.density;

        Log.i("TAG", "screen width" + Constants.screenwidth +
                "screen height" + Constants.screenheight);
    }

    public void showProgressBar(ProgressBar prog_bar) {
        showLog(TAG, "show pregoress mode" + prog_bar.getVisibility(), Config.UTILS);
        if (prog_bar.getVisibility() == View.VISIBLE) {
            prog_bar.setVisibility(View.GONE);
            showLog(TAG, "show pregoress gone", Config.UTILS);
        } else {
            prog_bar.setVisibility(View.VISIBLE);
            showLog(TAG, "show pregoress Visible", Config.UTILS);
        }
        showLog(TAG, "show pregoress mode" + prog_bar.getVisibility(), Config.UTILS);
    }

    public static void showLog(String tag, String msg, boolean val) {
        if (val)
            Log.wtf(tag, msg);
    }

    public String stringValCheck(String val) {
        return val != null && !val.isEmpty() ? val : "";
    }

    public void volleyErrorResposne(VolleyError error) {
        showLog(TAG, "" + error, Config.UTILS);
        if (error.networkResponse != null && error.networkResponse.data != null) {
            if (BuildConfig.DEBUG) {
                String errorresonse = new String(error.networkResponse.data);
                Intent i = new Intent(context, ResponseError.class);
                i.putExtra("error", errorresonse);
                context.startActivity(i);
            }

        }
    }

    public static Map<String, String> volleyHeader() {

        Map<String, String> headers = new HashMap<String, String>();
        if (usersession.getusersession().length() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append("sessionid");
            builder.append("=");
            builder.append(usersession.getusersession());
            if (headers.containsKey("Cookie")) {
                builder.append("; ");
                builder.append(headers.get("Cookie"));
            }
            headers.put("Cookie", builder.toString());
            showLog(TAG, " headers " + headers.toString(), Config.UTILS);
            return headers;
        } else {
            context.startActivity(new Intent(context, LoginActivity.class));
        }
        return headers;
    }

    public static int StringFromresourse(String label) {
        showLog(TAG, "alloc title is " + context.getResources().
                        getString(context.getResources().getIdentifier(label, "string", context.getPackageName())),
                Config.UTILS);
        return context.getResources().getIdentifier(label, "string", context.getPackageName());
    }

    public static Date MiebachDate(String date) {
        try {
//            showLog(TAG, "miebach data parse is " + MiebachDate_formate.parse(date).toString(), Config.UTILS);
            return MiebachDate_formate.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
//            showLog(TAG, "miebach data parse is " + new Date().toString(), Config.UTILS);
            return new Date();
        }
    }

    public static void logLargeString(String str) {

        if (str.length() > 3000) {
            String ss = str.substring(0, 3000);
            Log.i("", ss);
            logLargeString(str.substring(3000));
        } else
            Log.i("", str);
    }


    public static LineData generateLineData(int display_type) {

        ArrayList<LineDataSet> sets = new ArrayList<LineDataSet>();
        if (display_type == Constants.GRAPH_SELLTHRU) {
            return graph_SellthruData();

        }

        return new LineData();
    }

    public BarData generateDataBar(int display_type) {

        BarData cd = new BarData();
        cd.clearValues();

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<CommanDataSet> data_list = new ArrayList<CommanDataSet>();
        List<String> data_labels = new ArrayList<>();
        BarDataSet set1;
        xVals.clear();
        data_labels.clear();
        data_list.clear();

        try {

            if (display_type == Constants.GRAPH_MONTHLY_ALLOC_STYLES) {
                data_list.addAll(Constants.GRAPH_MONTHLY_ALLOC_STYLES_LIST);
                //sortCustomDataSet(data_list);
            } else if (display_type == Constants.GRAPH_MONTHLY_ALLOC_SUBBRAND) {
                data_list.addAll(Constants.GRAPH_MONTHLY_ALLOC_SUB_BRANDS_LIST);
                //sortCustomDataSet(data_list);
            } else if (display_type == Constants.GRAPH_STORE_SUFF) {
                data_list.addAll(Constants.GRAPH_STORE_STOCK_SUFF);
            }
//            showLog(TAG, display_type + " bar 1 garaph data list is" + data_list.size(), Config.UTILS);


            if (!data_list.isEmpty()) {
/*

                if (display_type == Constants.GRAPH_MONTHLY_ALLOC_STYLES)
                    data_labels.addAll(Arrays.asList("FASHION", "CORE"));
                else
                    data_labels.addAll(getlabels(data_list, display_type));
*/

                data_labels.addAll(getlabels(data_list, display_type));
                data_labels.removeAll(Arrays.asList("", null));

                int list_size = data_list.size();

                for (int i = 0; i < list_size; i++) {
                    CommanDataSet item = data_list.get(i);

                    if (display_type == Constants.GRAPH_STORE_SUFF)
                        xVals.add(item.getKey_val());
                    else
                        xVals.add(miebachdate(item.getKey_val()));


//                    showLog(TAG, "pos is " + i + " x val is " + item.getKey_val(), Config.UTILS);

                    JSONObject jobj = item.getData();
                    float[] data = new float[data_labels.size()];
                    if (jobj.length() > 0) {

                        if (data_labels.size() > 0) {

                            for (int count = 0; count < data_labels.size(); count++) {
                                if (jobj.has(data_labels.get(count)))
                                    data[count] = (float) jobj.optDouble(data_labels.get(count));
                                else
                                    data[count] = 0.0f;

                                //  showLog(TAG, "count is " + data_labels.get(count) + " item val is " + data[count], Config.UTILS);
                            }
                        }

                        yVals1.add(new BarEntry(data, i));
                    }
                }

                if (data_labels.size() == 1)
                    set1 = new BarDataSet(yVals1, data_labels.get(0));
                else
                    set1 = new BarDataSet(yVals1, "");

                //set1.setBarSpacePercent(50);

                if (display_type == Constants.GRAPH_MONTHLY_ALLOC_STYLES)
                    set1.setColors(getcolors(Constants.GRAPH_STYLES_CLORS, data_labels.size()));
                else if (display_type == Constants.GRAPH_STORE_SUFF)
                    set1.setColors(getcolors(Constants.GRAPH_STOCK_SUFF, data_labels.size()));
                else if (display_type == Constants.GRAPH_MONTHLY_ALLOC_SUBBRAND)
                    set1.setColors(getcolors(Constants.GRAPH_CLORS, data_labels.size()));

                if (display_type == Constants.GRAPH_STORE_SUFF) {
                    set1.setStackLabels(graphLabels(data_labels));
                } else {
                    set1.setStackLabels(data_labels.toArray(new String[data_labels.size()]));
                }

                showLog(TAG, display_type + "get stack labels are " + Arrays.asList(set1.getStackLabels()).toString(), Config.UTILS);
                ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
                dataSets.add(set1);

                showLog(TAG, display_type + "get stack labels are " + Arrays.asList(dataSets.toArray()).toString(), Config.UTILS);

                cd = new BarData(xVals, dataSets);

                showLog(TAG, display_type + "get datasets are " + Arrays.asList(cd.getDataSetByIndex(0).getStackLabels()), Config.UTILS);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        showLog(TAG, display_type + " is chart data is" + Arrays.asList(cd.getDataSets().toArray()).toString(), Config.UTILS);
        return cd;
    }

    public static PieData generateDataPie(int display_type) {

        PieData cd = new PieData();

        ArrayList<Entry> entries = new ArrayList<Entry>();
        ArrayList<String> des = new ArrayList<String>();
        ArrayList<Allocations> data_list = new ArrayList<Allocations>();

        if (display_type == Constants.GRAPH_INBOUND)
            data_list.addAll(Constants.GRAPH_INBOUND_LIST);
        else if (display_type == Constants.GRAPH_MASTER_VALID)
            data_list.addAll(Constants.GRAPH_MASTER_VALID_LIST);

        if (!data_list.isEmpty()) {

            Collections.sort(data_list, new Comparator<Allocations>() {
                @Override
                public int compare(Allocations lhs, Allocations rhs) {
                    return lhs.getTitle().compareTo(rhs.getTitle());
                }
            });


            for (int i = 0; i < data_list.size(); i++) {

                Allocations item = data_list.get(i);

                entries.add(new Entry(Integer.valueOf(item.getVal()), i));
                des.add(item.getTitle() + "(" + item.getVal() + ")");
            }

            PieDataSet d = new PieDataSet(entries, "");

            // space between slices
            d.setSliceSpace(2f);
            //d.setColors(ColorTemplate.VORDIPLOM_COLORS);
            if (display_type == Constants.GRAPH_INBOUND)
                d.setColors(Constants.GRAPH_INBOUND_COLOR);
            else if (display_type == Constants.GRAPH_MASTER_VALID)
                d.setColors(Constants.GRAPH_MASTER_CLOR);

//            showLog(TAG, "display item type is " + display_type + "pi data is " + d.getValueCount(), Config.DASHBOARD);
            cd = new PieData(des, d);
            return cd;
        }
        return cd;
    }

    public static LineData graph_SellthruData() {


        ArrayList<LineDataSet> linedat_sets = new ArrayList<LineDataSet>();
        List<String> keys_list = new ArrayList<String>();
        LineData linedata = new LineData();
        LineDataSet linedata_set;

        int list_size = Constants.GRAPH_SELL_THRU_LIST.size();

        int jobj_len = 0;
        JSONObject jobj = new JSONObject();

        keys_list = getlabels(Constants.GRAPH_SELL_THRU_LIST, Constants.GRAPH_SELLTHRU);
        keys_list.remove(Constants.SELL_THRU_TOTAL);
        Collections.sort(keys_list);

        for (int i = 0; i < list_size; i++) {

            CommanDataSet item = Constants.GRAPH_SELL_THRU_LIST.get(i);
            JSONObject jobj_data = item.getData();

            if (jobj_data.length() > 0) {

                if (!jobj_data.has(Constants.SELL_THRU_TOTAL)) {

                    ArrayList<Entry> entry = new ArrayList<>();

                    ////add the keys data  to entry  list
                    int count = 0;
                    for (String key : keys_list) {
                        entry.add(new Entry(Float.valueOf(jobj_data.has(key) ?
                                jobj_data.optString(key) : "0.00"),
                                count));
                        count++;
                    }

                    linedata_set = new LineDataSet(entry, item.getKey_val());
                    linedata_set.setLineWidth(2.5f);

                    ////set the colors for line
                    if (list_size < Constants.GRAPH_CLORS.length)
                        linedata_set.setColor(Constants.GRAPH_CLORS[i]);
                    else
                        linedata_set.setColor(setcolor(linedata_set.getColors()));

                    linedata_set.setDrawValues(true);
                    linedat_sets.add(linedata_set);
                }
            }
        }

        ////change the short form
        for (int i = 0; i < keys_list.size(); i++) {
            String data = miebachdate(keys_list.get(i));
            keys_list.set(i, data);
        }

        ////set the data to linedata
        linedata = new LineData(keys_list, linedat_sets);

//        showLog(TAG, " sell thru is chart data is" + Arrays.asList(linedata.getDataSets().toArray()).toString(), Config.UTILS);
        return linedata;
    }

    public static void setCommandataList(JSONObject obj, ArrayList<CommanDataSet> list, int graph_list_type) {

        if (obj.length() > 0) {
            list.clear();

            Iterator<?> keys = obj.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                list.add(new CommanDataSet(key, obj.optJSONObject(key)));
            }

            if (graph_list_type == Constants.GRAPH_MONTHLY_ALLOC_STYLES ||
                    graph_list_type == Constants.GRAPH_MONTHLY_ALLOC_SUBBRAND) {
                sortCustomDataSet(list);


                Collections.reverse(list);

                getWeeklyData(list);
//                Collections.reverse(list);

            }
        }
    }

    public static void getWeeklyData(ArrayList<CommanDataSet> list) {

        if (list.size() > Constants.WEEKLY) {
            ArrayList<CommanDataSet> dataset = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                dataset.add(list.get(i));
            }
            //  showLog(TAG, "sub lsit size is " + dataset.size(), Config.UTILS);
            list.clear();
            //showLog(TAG, "data  lsit size is " + list.size(), Config.UTILS);
            list.addAll(dataset);
            //showLog(TAG, "data  lsit size is " + list.size(), Config.UTILS);
            sortCustomDataSet(list);
        }
    }

    public static void setAllocationList(JSONObject obj, ArrayList<Allocations> list) {

        if (obj.length() > 0) {

            Iterator<?> keys = obj.keys();
            list.clear();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                list.add(new Allocations(key, obj.optString(key)));
            }

            Collections.sort(list, new Comparator<Allocations>() {
                @Override
                public int compare(Allocations lhs, Allocations rhs) {
                    return lhs.getTitle().compareToIgnoreCase(rhs.getTitle());
                }
            });
        }
    }

    public static String miebachdate(String date) {
        return Miebach_Cust_Date.format(MiebachDate(date));
    }

    public BarData generateData_GroupBar(int display_type) {

        BarData cd = new BarData();
        cd.clearValues();
        ArrayList<BarEntry> yVals_actual_qty = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals_opt_qty = new ArrayList<BarEntry>();
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<CommanDataSet> data_list = new ArrayList<CommanDataSet>();
        ArrayList<BarDataSet> set1 = new ArrayList<>();
        xVals.clear();
        data_list.clear();

        if (display_type == Constants.GRAPH_PLANO_ADH_ACT_QTY) {

            data_list.addAll(Constants.GRAPH_PLANO_ADH_ACT_QTY_LIST);
        } else if (display_type == Constants.GRAPH_PLANO_ADH_OPT_QTY) {

            data_list.addAll(Constants.GRAPH_PLANO_ADH_OPT_QTY_LIST);
        }

        showLog(TAG, display_type + " data size is " + data_list.size(),
                Config.UTILS);

        if (data_list.size() > 0) {
            try {

                for (int list = 0; list < data_list.size(); list++) {

                    CommanDataSet item = data_list.get(list);

                    xVals.add(item.getKey_val());

                    JSONObject obj = item.getData();

                    if (obj.length() > 0) {
                        yVals_actual_qty.add(new BarEntry((float) obj.optInt(Constants.ACTUAL), list));
                        yVals_opt_qty.add(new BarEntry((float) obj.optInt(Constants.NORM), list));
                    }


                }

                BarDataSet barDataSet1 = new BarDataSet(yVals_actual_qty, Constants.ACTUAL_TITLE);
                barDataSet1.setColor(Color.rgb(255, 152, 0));
                BarDataSet barDataSet2 = new BarDataSet(yVals_opt_qty, Constants.NORM_TITlE);
                barDataSet2.setColor(Color.rgb(96, 125, 139));

                set1.add(barDataSet1);
                set1.add(barDataSet2);
                cd = new BarData(xVals, set1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        showLog(TAG, display_type + " is chart data is" + Arrays.asList(cd.getDataSets().toArray()).toString(), Config.UTILS);
        return cd;
    }

    public void dashBoardValueSel_process(int dash_board_graph_list_type, int graph_sel_data_pos) {

        ArsRequest.getInstance(context).cancelRequestQueue(Dashboard.class.getSimpleName());
        showLog(TAG, "dash board type is " + dash_board_graph_list_type, Config.UTILS);
        context.startActivity(new Intent(context, DashBoardDetailActivity.class).
                putExtra(Constants.DASH_BOARD_ITEM_TYPE, dash_board_graph_list_type).
                putExtra(Constants.GRAPH_SEL_POS, graph_sel_data_pos));

    }


    public static void sortCustomDataSet(ArrayList<CommanDataSet> data_list) {

        if (!data_list.isEmpty())
            Collections.sort(data_list, new Comparator<CommanDataSet>() {
                @Override
                public int compare(CommanDataSet o1, CommanDataSet o2) {
                    return o1.getKey_val().compareTo(o2.getKey_val());
                }
            });
/*

        for (CommanDataSet item : data_list)
            showLog(TAG, "sortCustomDataSet " + item.getKey_val(), Config.UTILS);
*/

    }

    public static List<String> getlabels(ArrayList<CommanDataSet> list, int display_type) {

        List<String> labels = new ArrayList<>();
        labels.clear();
        for (CommanDataSet item : list) {
            try {
                JSONObject jobj = item.getData();

                Iterator<String> keys = jobj.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    if (!labels.contains(key))
                        labels.add(key);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (display_type == Constants.GRAPH_STORE_SUFF) {
            Collections.reverse(labels);
        } else {
            Collections.sort(labels);
        }

        showLog(TAG, display_type + " get labels are " + labels.toString(), Config.UTILS);
        return labels;
    }

    public static int[] getcolors(int[] colors, int size) {

        try {
            if (size <= colors.length)
                return Arrays.copyOfRange(colors, 0, size);
            else {
                int[] colorslist = new int[size];
                for (int i = 0; i < size; i++) {
                    colorslist[i] = getRandomColor();
                }

                return colorslist;

            }
        } catch (Exception e) {
            e.printStackTrace();
            return ColorTemplate.JOYFUL_COLORS;
        }

    }


    public static String[] graphLabels(List<String> labelslist) {


        String[] labels = new String[labelslist.size()];
        int count = 0;
        for (String item : labelslist) {
            showLog(TAG, " item is " + item, Config.UTILS);
            labels[count] = context.getResources().getString(StringFromresourse(item));
            count++;
        }

        return labels;
    }

    public static int getRandomColor() {
        return Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));

    }


    public static int setcolor(List<Integer> colors) {

        for (; ; ) {
            int color = getRandomColor();
            if (!colors.contains(color)) {
                return color;
            }
        }

    }

    public int dashBoardDetailsReasonColor(String reason_type) {

        if (reason_type.contains(Constants.ERROR))
            return R.color.reason_error;
        else if (reason_type.contains(Constants.SUCESS))
            return R.color.green;
        else if (reason_type.contains(Constants.CHECK))
            return R.color.pi_text_color;

        return R.color.black;
    }

    public static void sortDashBoardDetails(ArrayList<DahsBoardDeatils> list) {
        Collections.sort(list, new Comparator<DahsBoardDeatils>() {
            @Override
            public int compare(DahsBoardDeatils lhs, DahsBoardDeatils rhs) {
                return lhs.customer_name.compareToIgnoreCase(rhs.customer_name);
            }
        });
    }


    public static String JSonObjtoString(JSONObject obj) {

        StringBuilder val = new StringBuilder();
        if (obj.length() > 0) {

            showLog(TAG, "obj  is " + obj.toString(), Config.UTILS);
            Iterator<String> keys = obj.keys();

            List<String> key_list = new ArrayList<String>();
            key_list = iteratetoList(key_list, keys);
            Collections.sort(key_list);

            for (String key : key_list) {
                val.append(" " + key).append(" : " + obj.optString(key) + "\n");
            }
        }
        return val.toString();
    }

    public static <T> List<T> iteratetoList(List<T> list, Iterator<T> iterator) {
        list.clear();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }

        return list;
    }


    public static LinearLayout.LayoutParams setLayoutParams(int lay_width, int lay_height, int margin_left, int margin_top, int margin_right, int margin_bottm) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(lay_width, lay_height);
        params.setMargins(margin_left, margin_top, margin_right, margin_bottm);

        return params;
    }


    public String sellThruMarkView(int index) {

        //showLog(TAG, "sell thru mark view  pos " + index, Config.UTILS);

        JSONObject jobj;
        StringBuilder val = new StringBuilder();
        String keyval = "";
        boolean getval = false;

        for (CommanDataSet item : Constants.GRAPH_SELL_THRU_LIST) {

            // showLog(TAG, "sell thru mark view  key " + item.getKey_val(), Config.UTILS);

            val.append(item.getKey_val());

            if (getval == false) {

                //  showLog(TAG, "sell thru mark try get index val key " + getval, Config.UTILS);

                jobj = item.getData();
                Iterator<String> keys = jobj.keys();
                int count = 0;
                while (keys.hasNext()) {

                    //  showLog(TAG, "sell thru mark try get index val key count " + count, Config.UTILS);
                    if (count == index) {
                        //  showLog(TAG, "sell thru mark try get index val key sucess count " + count, Config.UTILS);
                        keyval = keys.next();
                        //   showLog(TAG, "sell thru mark view index key is " + keyval, Config.UTILS);

                        val.append(jobj.optString(keyval));
                        getval = true;
                        //   showLog(TAG, "sell thru mark view index key is " + keyval + "val is " + jobj.optString(keyval), Config.UTILS);
                        break;
                    }
                    count++;
                    keys.next();
                }
            } else {
                if (!keyval.isEmpty()) {
                    val.append(item.getData().optString(keyval));
                }
                // showLog(TAG, "sell thru mark view index key is " + keyval + "val is " + item.getData().optString(keyval), Config.UTILS);
            }

            val.append("\n");

        }
        //  showLog(TAG, "sell thru mark view String is " + val.toString(), Config.UTILS);
        getval = false;
        return val.toString();
    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            new AlertDialog.Builder(context)
                    .setTitle(context.getResources().getString(R.string.app_name))
                    .setMessage(context.
                            getResources().getString(
                            R.string.internet_error))
                    .setPositiveButton("OK", null).show();
        }
        return false;
    }

    public static void clearDashBoardItems() {

        Constants.Daily_allocations.clear();
        Constants.GRAPH_INBOUND_LIST.clear();
        Constants.GRAPH_MASTER_VALID_LIST.clear();
        Constants.GRAPH_SELL_THRU_LIST.clear();
        Constants.GRAPH_MONTHLY_ALLOC_STYLES_LIST.clear();
        Constants.GRAPH_MONTHLY_ALLOC_SUB_BRANDS_LIST.clear();
        Constants.GRAPH_STORE_STOCK_SUFF.clear();
        Constants.GRAPH_PLANO_ADH_ACT_QTY_LIST.clear();
        Constants.GRAPH_PLANO_ADH_OPT_QTY_LIST.clear();

    }

    public static void update_FCMToken(final String token) {

        showLog(TAG, "update fcm token: server call ", Config.UTILS);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Serverconfig.ALBL_BRAND,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                showLog(TAG, "update FCM Token error : " + error.toString(), Config.UTILS);

            }

        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("fcm_token", token);

                showLog(TAG, "update fcm token params" + params, Config.UTILS);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return volleyHeader();
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                Constants.REQUEST_TIMEOUT,
                Constants.REQUEST_RETRY_TIMES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        stringRequest.setTag(TAG);
        ArsRequest.getInstance(context).addToRequestQueue(stringRequest);

    }

    //TODO get device id
    public static String getDeviceID() {

        String device_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        return device_id;
    }


}
