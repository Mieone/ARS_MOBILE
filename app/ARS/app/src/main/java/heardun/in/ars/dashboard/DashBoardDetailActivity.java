package heardun.in.ars.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import heardun.in.ars.ArsRequest;

import heardun.in.ars.R;
import heardun.in.ars.activity.MenuAtivity;
import heardun.in.ars.config.Config;
import heardun.in.ars.config.Constants;
import heardun.in.ars.config.Serverconfig;
import heardun.in.ars.utils.Utils;


public class DashBoardDetailActivity extends AppCompatActivity {

    public String TAG = DashBoardDetailActivity.class.getSimpleName();
    /* @BindView(R.id.detail_toolbar)
     Toolbar toolbar;*/
    @BindView(R.id.progress_bar)
    ProgressBar progressbar;
    @BindView(R.id.network_lay)
    RelativeLayout network_lay;
    @BindView(R.id.cirle_image)
    ImageView cirle_image;
//    CollapsingToolbarLayout appBarLayout;

    public int graph_list_type, graph_sel_pos;
    Utils utils;
    Activity activity;
    public String url;
    public static String title_bar_title;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_detail);
        ButterKnife.bind(this);

//        appBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

//        appBarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
//        appBarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);


//        setSupportActionBar(toolbar);
        utils = new Utils(this);
        activity = this;

        // Show the Up button in the action bar.
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setLogo(R.mipmap.ic_launcher);
        }

        cirle_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (utils.isOnline())
                    getServerData(url, graph_list_type, graph_sel_pos);
                else
                    network_lay.setVisibility(View.VISIBLE);
            }
        });

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = getIntent().getExtras();

            graph_list_type = arguments.getInt(Constants.DASH_BOARD_ITEM_TYPE);
            graph_sel_pos = arguments.getInt(Constants.GRAPH_SEL_POS);

            geturl(graph_list_type, graph_sel_pos);
            actionBar.setTitle(title_bar_title);

            utils.showLog(TAG, "graph_list_type " + graph_list_type +
                    " sel pos is " + graph_sel_pos, Config.UTILS);


         /*   DashBoard_InBoundDetailsFrg fragment = new DashBoard_InBoundDetailsFrg();
            //fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();*/

            if (utils.isOnline())
                getServerData(url, graph_list_type, graph_sel_pos);
            else
                network_lay.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            ArsRequest.getInstance(this).cancelRequestQueue(TAG);
            onBackPressed();
//            NavUtils.navigateUpTo(this, new Intent(this, MenuAtivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void getServerData(String url, final int request_type, final int graph_sel_pos) {

        utils.showProgressBar(progressbar);
        network_lay.setVisibility(View.GONE);
        utils.showLog(TAG, "dash board details call" + url, Config.DASHBOARDDetails);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (activity != null) {
                            //utils.showLog(TAG, "request for " + request_type + " response is" + response, Config.DASHBOARD);

                            utils.logLargeString("long string response" + response);

                            if (request_type == Constants.GRAPH_ALLOCATIONS) {
//                                responseDialyAllocations(response);

                            } else if (request_type == Constants.GRAPH_INBOUND) {
                                responseGraphInBound(response);
                            } else if (request_type == Constants.GRAPH_MASTER_VALID) {
                                responseGraphMasterValid(response);
                            } else if (request_type == Constants.GRAPH_SELLTHRU) {
//                                responseGraphSellThru(response);
                            } else if (request_type == Constants.GRAPH_PLANO_ADH) {
//                                responseGraphPlanoAdh(response);
                            } else if (request_type == Constants.GRAPH_STORE_SUFF) {
//                                responseGraphStoreSuff(response);
                            } else if (request_type == Constants.GRAPH_MONTHLY_ALLOC) {
//                                responseGraphMonthlyAlloc(response);
                            }

                            utils.showProgressBar(progressbar);
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                utils.volleyErrorResposne(error);

                if (error instanceof TimeoutError) {
                    //   Log.i(TAG, "time out error");
                } else if (error instanceof NoConnectionError) {
                    network_lay.setVisibility(View.VISIBLE);
                }

                utils.showProgressBar(progressbar);
            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put(Constants.BRAND, Constants.SEL_BARND[0].toLowerCase().trim());
                params.put(Constants.CHANNEL, Constants.SEL_BARND[1].toLowerCase().trim());
                params.put(Constants.USERNAME, utils.usersession.getloginname());

//                params.put(Constants.DATE, utils.MIEBACH_PARAM_DATE.format(new Date()));

                if (request_type == Constants.GRAPH_ALLOCATIONS) {
                    //param.putAll(paramsInbound());
                } else if (request_type == Constants.GRAPH_INBOUND) {
                    params.putAll(paramsInbound(graph_sel_pos));
                } else if (request_type == Constants.GRAPH_MASTER_VALID) {
                    params.putAll(paramsMaster(graph_sel_pos));
                } else if (request_type == Constants.GRAPH_SELLTHRU) {
//                                responseGraphSellThru(response);
                } else if (request_type == Constants.GRAPH_PLANO_ADH) {
//                                responseGraphPlanoAdh(response);
                } else if (request_type == Constants.GRAPH_STORE_SUFF) {
//                                responseGraphStoreSuff(response);
                } else if (request_type == Constants.GRAPH_MONTHLY_ALLOC) {
//                                responseGraphMonthlyAlloc(response);
                }

                utils.showLog(TAG, "dash board activity params are" + params, Config.DASHBOARD);
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
        ArsRequest.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void responseGraphMasterValid(String response) {

        Bundle argument = new Bundle();
        argument.putString(Constants.RESPONSE, response);
        DashBoard_MasterStoreDetailsFrag fragment = new DashBoard_MasterStoreDetailsFrag();
        fragment.setArguments(argument);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.item_detail_container, fragment)
                .commitAllowingStateLoss();
    }

    private void responseGraphInBound(String response) {

        Bundle argument = new Bundle();
        argument.putString(Constants.RESPONSE, response);
        DashBoard_InBoundDetailsFrg fragment = new DashBoard_InBoundDetailsFrg();
        fragment.setArguments(argument);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.item_detail_container, fragment)
                .commitAllowingStateLoss();

    }

    private HashMap<String, String> paramsInbound(int pos) {
        HashMap<String, String> param = new HashMap<>();
        param.put(Constants.TYPE_PARAM, Constants.GRAPH_INBOUND_LIST.get(pos).getTitle().toLowerCase());

        return param;

    }

    private HashMap<String, String> paramsMaster(int pos) {
        HashMap<String, String> param = new HashMap<>();
        param.put(Constants.TYPE_PARAM, Constants.GRAPH_MASTER_VALID_LIST.get(pos).getTitle().toLowerCase());
        return param;
    }

    private void geturl(int request_type, int pos) {

        String type = "";

        if (request_type == Constants.GRAPH_ALLOCATIONS) {
            //param.putAll(paramsInbound());
        } else if (request_type == Constants.GRAPH_INBOUND) {
            title_bar_title = Constants.GRAPH_INBOUND_LIST.get(pos).getTitle() +
                    " Stores ( " + Constants.GRAPH_INBOUND_LIST.get(pos).getVal() + " ) ";
            url = Serverconfig.GRAPH_INBOUND_DETAILS;
        } else if (request_type == Constants.GRAPH_MASTER_VALID) {
            title_bar_title = Constants.GRAPH_MASTER_VALID_LIST.get(pos).getTitle() +
                    " Stores ( " + Constants.GRAPH_MASTER_VALID_LIST.get(pos).getVal() + " ) ";
            url = Serverconfig.GRAPH_MASTER_VALID_DETAILS;
        } else if (request_type == Constants.GRAPH_SELLTHRU) {
//                                responseGraphSellThru(response);
        } else if (request_type == Constants.GRAPH_PLANO_ADH) {
//                                responseGraphPlanoAdh(response);
        } else if (request_type == Constants.GRAPH_STORE_SUFF) {
//                                responseGraphStoreSuff(response);
        } else if (request_type == Constants.GRAPH_MONTHLY_ALLOC) {
//                                responseGraphMonthlyAlloc(response);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
