package heardun.in.ars.dashboard;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import butterknife.BindView;
import butterknife.ButterKnife;
import heardun.in.ars.ArsRequest;
import heardun.in.ars.chart.BarChartItem;
import heardun.in.ars.chart.ChartItem;
import heardun.in.ars.chart.LineChartItem;
import heardun.in.ars.chart.PieChartItem;
import heardun.in.ars.R;
import heardun.in.ars.adapters.AllocationAdapter;
import heardun.in.ars.adapters.ChartDataAdapter;
import heardun.in.ars.config.Config;
import heardun.in.ars.doto.Allocations;
import heardun.in.ars.utils.Utils;
import heardun.in.ars.config.Constants;
import heardun.in.ars.config.Serverconfig;

/**
 * Created by headrun on 13/10/15.
 */

public class Dashboard extends Fragment {

    public String TAG = Dashboard.this.getClass().getSimpleName();


    /* @BindView(R.id.dially_allocations_sum)
     RecyclerView dially_allocations_sum;
 */

    @BindView(R.id.graph_list)
    ListView graph_list;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;
    @BindView(R.id.network_lay)
    RelativeLayout network_lay;
    @BindView(R.id.cirle_image)
    ImageView cirle_image;

    ArrayList<ChartItem> list = new ArrayList<ChartItem>();
    Utils utils;


    // DailyAllocationsAdapter allocationAdapter;
    ChartDataAdapter cda;
    Activity activity;

    public Dashboard() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.activity_listview_chart, container, false);
        ButterKnife.bind(this, rootview);
        utils = new Utils(getActivity());
        activity = getActivity();

        // list.add(new BarChartItem(onProgressChanged(), context));
        //   allocationAdapter = new DailyAllocationsAdapter(Constants.Daily_allocations, getActivity());
        //    dially_allocations_sum.setAdapter(allocationAdapter);

        setViewInList();
        cda = new ChartDataAdapter(getActivity(), list);
        graph_list.setAdapter(cda);

        if (utils.isOnline())
            serverCallRequeests();

        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (utils.isOnline()) {
                    serverCallRequeests();
                    swipe_refresh.setRefreshing(true);
                } else {
                    swipe_refresh.setRefreshing(false);
                }

            }
        });

        cirle_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (utils.isOnline())
                    serverCallRequeests();
            }
        });

        return rootview;
    }

    public void getServerData(final String url, final int request_for, final int request_type) {

        if (request_for != Constants.REQUEST_SWIPE)
            utils.showProgressBar(progressbar);

        //utils.showLog(TAG, "request for" + request_for + "server call is" + url, Config.DASHBOARD);
        network_lay.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (isAdded() && activity != null) {
                            utils.showLog(TAG, "request for " + request_type + " response is" + response, Config.DASHBOARD);

                            if (request_type == Constants.GRAPH_ALLOCATIONS)
                                responseDialyAllocations(response);
                            else if (request_type == Constants.GRAPH_INBOUND)
                                responseGraphInBound(response);
                            else if (request_type == Constants.GRAPH_MASTER_VALID)
                                responseGraphMasterValid(response);
                            else if (request_type == Constants.GRAPH_SELLTHRU)
                                responseGraphSellThru(response);
                            else if (request_type == Constants.GRAPH_PLANO_ADH)
                                responseGraphPlanoAdh(response);
                            else if (request_type == Constants.GRAPH_STORE_SUFF)
                                responseGraphStoreSuff(response);
                            else if (request_type == Constants.GRAPH_MONTHLY_ALLOC)
                                responseGraphMonthlyAlloc(response);

                            if (progressbar.getVisibility() == View.GONE)
                                swipe_refresh.setRefreshing(false);
                            else
                                utils.showProgressBar(progressbar);
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (isAdded() && activity != null) {
                    utils.volleyErrorResposne(error);

                    if (error instanceof TimeoutError) {
                        //   Log.i(TAG, "time out error");
                    } else if (error instanceof NoConnectionError) {

                        //   network_lay.setVisibility(View.VISIBLE);
                    }

                    if (progressbar.getVisibility() == View.GONE)
                        swipe_refresh.setRefreshing(false);
                    else
                        utils.showProgressBar(progressbar);
                }
            }

        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                if (Constants.SEL_BARND.length > 0) {
                    params.put(Constants.BRAND_CHANNEL, Constants.SEL_BARND[0].trim() +
                            "-" + Constants.SEL_BARND[1].trim());
                }
                params.put(Constants.DATE, utils.MIEBACH_PARAM_DATE.format(new Date()));
                utils.showLog(TAG, "dash board params are" + params, Config.DASHBOARD);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return utils.volleyHeader();
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                Constants.REQUEST_TIMEOUT,
                Constants.REQUEST_RETRY_TIMES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        stringRequest.setTag(TAG);
        ArsRequest.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }

    public void responseDialyAllocations(String response) {

        if (isAdded() && activity != null) {
            try {
                JSONObject jobj = new JSONObject(response);

                Constants.Daily_allocations.clear();

                if (jobj.length() > 0) {
                    Iterator<?> keys = jobj.keys();
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        Constants.Daily_allocations.add(
                                new Allocations(key, utils.stringValCheck(jobj.optString(key))));
                    }

                } else {
                    ArrayList<Allocations> alloc_list = new ArrayList<>();
                    alloc_list.add(new Allocations("total_allocation", "0"));
                    alloc_list.add(new Allocations("CR", "0"));
                    alloc_list.add(new Allocations("FM", "0"));
                    alloc_list.add(new Allocations("SM", "0"));
                    alloc_list.add(new Allocations("NS", "0"));
                    alloc_list.add(new Allocations("CP", "0"));

                    Constants.Daily_allocations.addAll(alloc_list);
                }

                Collections.reverse(Constants.Daily_allocations);

                list.set(0, new AllocationAdapter(Constants.Daily_allocations, Constants.GRAPH_ALLOCATIONS, getActivity()));
                updateItemAtPosition(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void responseGraphInBound(String response) {

        if (isAdded() && activity != null) {
            utils.showLog(TAG, "graph inbound response is" + response, Config.DASHBOARD);
            Constants.GRAPH_INBOUND_LIST.clear();
            try {
                JSONObject jobj = new JSONObject(response);

                if (jobj.length() > 0)
                    Utils.setAllocationList(jobj, Constants.GRAPH_INBOUND_LIST);

                list.set(1, new PieChartItem(utils.generateDataPie(Constants.GRAPH_INBOUND),
                        getResources().getString(R.string.Stores_in_ars), Constants.GRAPH_INBOUND, getActivity()));

                updateItemAtPosition(1);

            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void responseGraphMasterValid(String response) {
        if (isAdded() && activity != null) {
            utils.showLog(TAG, "graph master response is" + response, Config.DASHBOARD);

            try {
                JSONObject jobj = new JSONObject(response);
                Constants.GRAPH_MASTER_VALID_LIST.clear();

                if (jobj.length() > 0)
                    Utils.setAllocationList(jobj, Constants.GRAPH_MASTER_VALID_LIST);
//                    utils.generateDataPie(Constants.GRAPH_MASTER_VALID);

                list.set(2, new PieChartItem(utils.generateDataPie(Constants.GRAPH_MASTER_VALID),
                        getResources().getString(R.string.master_valid), Constants.GRAPH_MASTER_VALID, getActivity()));
                updateItemAtPosition(2);


            } catch (JSONException e1) {
                e1.printStackTrace();
            }

        }
    }

    private void responseGraphSellThru(String response) {
        utils.showLog(TAG, "graph sellthru response is" + response, Config.DASHBOARD);

        try {
            JSONObject jobj = new JSONObject(response);
            Constants.GRAPH_SELL_THRU_LIST.clear();

            if (jobj.length() > 0) {
                Utils.setCommandataList(jobj, Constants.GRAPH_SELL_THRU_LIST, Constants.GRAPH_SELLTHRU);
                utils.sortCustomDataSet(Constants.GRAPH_SELL_THRU_LIST);
            }

            list.set(3, new LineChartItem(utils.generateLineData(Constants.GRAPH_SELLTHRU),
                    getResources().getString(R.string.sell_thru), Constants.GRAPH_SELLTHRU,
                    getActivity(),
                    Constants.GRAPH_SELL_THRU_TOTAL_TITLE,
                    jobj.has(Constants.SELL_THRU_OVERALL) ?
                            jobj.optJSONObject(Constants.SELL_THRU_OVERALL).optString(Constants.SELL_THRU_TOTAL)
                            : "0"));
            updateItemAtPosition(3);


        } catch (JSONException e1) {
            e1.printStackTrace();
        }
    }

    private void responseGraphPlanoAdh(String response) {
        utils.showLog(TAG, "graph planoadh response is" + response, Config.DASHBOARD);

        try {
            JSONObject jobj = new JSONObject(response);
            Constants.GRAPH_PLANO_ADH_ACT_QTY_LIST.clear();
            Constants.GRAPH_PLANO_ADH_OPT_QTY_LIST.clear();

            JSONObject jobj_styles = new JSONObject();
            JSONObject jobj_subbrand = new JSONObject();

            String planoram_total_val = "";

            if (jobj.has(Constants.QTY_NORM))
                jobj_styles = jobj.optJSONObject(Constants.QTY_NORM);

            if (jobj.has(Constants.GRAPH_PLANOGRAM_TOTAL_TITLE))
                planoram_total_val = jobj.optString(Constants.GRAPH_PLANOGRAM_TOTAL_TITLE);

            if (jobj_styles.length() > 0)
                Utils.setCommandataList(jobj_styles, Constants.GRAPH_PLANO_ADH_ACT_QTY_LIST, Constants.GRAPH_PLANO_ADH_ACT_QTY);

            list.set(5, new BarChartItem(utils.generateData_GroupBar(Constants.GRAPH_PLANO_ADH_ACT_QTY),
                    getResources().getString(R.string.quantity_norm), Constants.GRAPH_PLANO_ADH_ACT_QTY,
                    getActivity(),
                    Constants.GRAPH_PLANOGRAPH_TOTAL_TITLE,
                    planoram_total_val));
            updateItemAtPosition(5);


            if (jobj.has(Constants.OPT_NORM))
                jobj_subbrand = jobj.optJSONObject(Constants.OPT_NORM);

            if (jobj_subbrand.length() > 0)
                Utils.setCommandataList(jobj_subbrand, Constants.GRAPH_PLANO_ADH_OPT_QTY_LIST, Constants.GRAPH_PLANO_ADH_OPT_QTY);


            list.set(6, new BarChartItem(utils.generateData_GroupBar(Constants.GRAPH_PLANO_ADH_OPT_QTY),
                    getResources().getString(R.string.options_norm), Constants.GRAPH_PLANO_ADH_OPT_QTY,
                    getActivity(),
                    Constants.GRAPH_PLANOGRAPH_TOTAL_TITLE,
                    planoram_total_val));

            updateItemAtPosition(6);
//


        } catch (JSONException e1) {
            e1.printStackTrace();
        }
    }

    private void responseGraphStoreSuff(String response) {
        utils.showLog(TAG, "graph store suff response is" + response, Config.DASHBOARD);

        try {

            JSONObject jobj = new JSONObject(response);
            Constants.GRAPH_STORE_STOCK_SUFF.clear();
            if (jobj.length() > 0)
                Utils.setCommandataList(jobj, Constants.GRAPH_STORE_STOCK_SUFF, Constants.GRAPH_STORE_SUFF);

            list.set(4, new BarChartItem(utils.generateDataBar(Constants.GRAPH_STORE_SUFF),
                    getResources().getString(R.string.store_stock_suff), Constants.GRAPH_STORE_SUFF,
                    getActivity(), "", ""));
            updateItemAtPosition(4);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void responseGraphMonthlyAlloc(String response) {
        utils.showLog(TAG, "graph monthly alloc response is" + response, Config.DASHBOARD);

        try {
            JSONObject jobj = new JSONObject(response);

            Constants.GRAPH_MONTHLY_ALLOC_STYLES_LIST.clear();
            Constants.GRAPH_MONTHLY_ALLOC_SUB_BRANDS_LIST.clear();

            JSONObject jobj_styles = new JSONObject();
            JSONObject jobj_subbrand = new JSONObject();

            if (jobj.has(Constants.STYLE_CLASS))
                jobj_styles = jobj.optJSONObject(Constants.STYLE_CLASS);

            if (jobj_styles.length() > 0)
                Utils.setCommandataList(jobj_styles, Constants.GRAPH_MONTHLY_ALLOC_STYLES_LIST, Constants.GRAPH_MONTHLY_ALLOC_STYLES);

            utils.showLog(TAG, "weekly style list size is" + Constants.GRAPH_MONTHLY_ALLOC_STYLES_LIST.size(),
                    Config.DASHBOARD);
            list.set(7, new BarChartItem(utils.generateDataBar(Constants.GRAPH_MONTHLY_ALLOC_STYLES),
                    getResources().getString(R.string.mnth_alloc_styles), Constants.GRAPH_MONTHLY_ALLOC_STYLES,
                    getActivity(), "", ""));
            updateItemAtPosition(7);

            if (jobj.has(Constants.SUBBRAND))
                jobj_subbrand = jobj.optJSONObject(Constants.SUBBRAND);

            if (jobj_subbrand.length() > 0)
                Utils.setCommandataList(jobj_subbrand, Constants.GRAPH_MONTHLY_ALLOC_SUB_BRANDS_LIST, Constants.GRAPH_MONTHLY_ALLOC_SUBBRAND);

            list.set(8, new BarChartItem(utils.generateDataBar(Constants.GRAPH_MONTHLY_ALLOC_SUBBRAND),
                    getResources().getString(R.string.mnth_alloc_sub_brand), Constants.GRAPH_MONTHLY_ALLOC_SUBBRAND,
                    getActivity(), "", ""));
            updateItemAtPosition(8);


        } catch (JSONException e1) {
            e1.printStackTrace();
        }

    }

    private void serverCallRequeests() {

        ArsRequest.getInstance(getActivity()).cancelRequestQueue(TAG);

        getServerData(Serverconfig.GRAPH_ALLOCATIONS, Constants.REQUEST_SWIPE, Constants.GRAPH_ALLOCATIONS);
        getServerData(Serverconfig.GRAPH_INBOUND, Constants.REQUEST_SWIPE, Constants.GRAPH_INBOUND);
        getServerData(Serverconfig.GRAPH_MASTER_VALID, Constants.REQUEST_SWIPE, Constants.GRAPH_MASTER_VALID);
        getServerData(Serverconfig.GRAPH_SELLTHRU, Constants.REQUEST_SWIPE, Constants.GRAPH_SELLTHRU);
        getServerData(Serverconfig.GRAPH_STORE_STUFF, Constants.REQUEST_SWIPE, Constants.GRAPH_STORE_SUFF);
        getServerData(Serverconfig.GRAPH_PLANO_ADH, Constants.REQUEST_SWIPE, Constants.GRAPH_PLANO_ADH);
        getServerData(Serverconfig.GRAPH_MONTHLY_ALLOC, Constants.REQUEST_SWIPE, Constants.GRAPH_MONTHLY_ALLOC);

    }

    private void setViewInList() {

        list.clear();
        list.add(new AllocationAdapter(Constants.Daily_allocations, Constants.GRAPH_ALLOCATIONS, getActivity()));

        list.add(new PieChartItem(utils.generateDataPie(Constants.GRAPH_INBOUND),
                getResources().getString(R.string.Stores_in_ars), Constants.GRAPH_INBOUND, getActivity()));

        list.add(new PieChartItem(utils.generateDataPie(Constants.GRAPH_MASTER_VALID),
                getResources().getString(R.string.master_valid), Constants.GRAPH_MASTER_VALID, getActivity()));

        list.add(new LineChartItem(utils.generateLineData(Constants.GRAPH_SELLTHRU),
                getResources().getString(R.string.sell_thru), Constants.GRAPH_SELLTHRU, getActivity(), "", ""));
        list.add(new BarChartItem(utils.generateDataBar(Constants.GRAPH_STORE_SUFF),
                getResources().getString(R.string.store_stock_suff), Constants.GRAPH_STORE_SUFF,
                getActivity(), "", ""));

        list.add(new BarChartItem(utils.generateData_GroupBar(Constants.GRAPH_PLANO_ADH_ACT_QTY),
                getResources().getString(R.string.quantity_norm), Constants.GRAPH_PLANO_ADH_ACT_QTY,
                getActivity(), "", ""));

        list.add(new BarChartItem(utils.generateData_GroupBar(Constants.GRAPH_PLANO_ADH_OPT_QTY),
                getResources().getString(R.string.options_norm), Constants.GRAPH_PLANO_ADH_OPT_QTY,
                getActivity(), "", ""));

        list.add(new BarChartItem(utils.generateDataBar(Constants.GRAPH_MONTHLY_ALLOC_STYLES),
                getResources().getString(R.string.mnth_alloc_styles), Constants.GRAPH_MONTHLY_ALLOC,
                getActivity(), "", ""));

        list.add(new BarChartItem(utils.generateDataBar(Constants.GRAPH_MONTHLY_ALLOC_SUBBRAND),
                getResources().getString(R.string.mnth_alloc_sub_brand), Constants.GRAPH_MONTHLY_ALLOC_SUBBRAND,
                getActivity(), "", ""));

    }

    private void updateItemAtPosition(int position) {
        int visiblePosition = graph_list.getFirstVisiblePosition();
        View view = graph_list.getChildAt(position - visiblePosition);
        graph_list.getAdapter().getView(position, view, graph_list);
    }

}

