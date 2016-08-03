package heardun.in.ars.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import heardun.in.ars.ArsRequest;
import heardun.in.ars.BuildConfig;
import heardun.in.ars.R;
import heardun.in.ars.Usersession;
import heardun.in.ars.config.Config;
import heardun.in.ars.config.Constants;
import heardun.in.ars.config.Serverconfig;
import heardun.in.ars.dashboard.Dashboard;
import heardun.in.ars.doto.BrandsList;
import heardun.in.ars.utils.Utils;

public class MenuAtivity extends AppCompatActivity {

    public String TAG = MenuAtivity.this.getClass().getName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.network_lay)
    RelativeLayout network_lay;
    @BindView(R.id.cirle_image)
    ImageView cirle_image;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;

//    @BindView(R.id.swipe_reffresh)
//    SwipeRefreshLayout swipe_reffresh;

    android.support.v4.app.FragmentTransaction fragmentTransaction;
    Fragment fragment = null;
    Usersession userSession;
    Utils utils;

    MenuItem mpreviousMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ativity);
        utils = new Utils(this);

        ButterKnife.bind(this);

        userSession = new Usersession(getApplication());
        setSupportActionBar(toolbar);

        getMenuItems();

        cirle_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getMenuItems();
            }
        });

        navigationView.
                setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

                    // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Log.i(TAG, "menuItem.getItemId()" + menuItem.getItemId());


                        if (mpreviousMenuItem != null && menuItem != mpreviousMenuItem)
                            mpreviousMenuItem.setChecked(false);

                        menuItem.setChecked(true);
                        mpreviousMenuItem = menuItem;

                        String sel_item = menuItem.getTitle().toString();

                        if (sel_item == Constants.LOGOUT) {
                            userSession.clearsession();
                            startActivity(new Intent(getApplication(), LoginActivity.class));
                            finish();

                        } else {
                            fragment = new Dashboard();
                            setTitle(sel_item);
                            Constants.SEL_BARND = sel_item.split("-");
                            utils.showLog(TAG, "sel barnd is " + Arrays.asList(Constants.SEL_BARND), Config.MENUACTIVITY);
                            utils.clearDashBoardItems();
                        }

                        if (fragment != null && !isFinishing()) {
                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.mainFrame, fragment);
                            fragmentTransaction.commit();
                        }

                        getSupportActionBar().setTitle(menuItem.getTitle());

                        drawer.closeDrawers();
                        return true;
                    }
                });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            ActivityCompat.finishAffinity(this);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    private void getMenuItems() {

        JSONArray jarray_ultd = new JSONArray();
        JSONObject jobj = new JSONObject();

        if (BuildConfig.FLAVOR == "unlimited") {

            try {
                jobj.put(Constants.BRAND, "UNLIMITED");
                jobj.put(Constants.CHANNEL, "EBO");
                jarray_ultd.put(jobj);
                setmenuItems(jarray_ultd);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (BuildConfig.FLAVOR == "bb") {

            try {
                jobj.put(Constants.BRAND, "BB");
                jobj.put(Constants.CHANNEL, "EBO");
                jarray_ultd.put(jobj);
                setmenuItems(jarray_ultd);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (BuildConfig.FLAVOR == "albl") {
            if (utils.isOnline())
                getMenuItemsfromServer();
            else
                network_lay.setVisibility(View.VISIBLE);
        }

    }

    private void getMenuItemsfromServer() {

        utils.showProgressBar(progress_bar);
        network_lay.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Serverconfig.ALBL_BRAND,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //utils.showLog(TAG, "menu response is " + response, Config.MENUACTIVITY);
                        try {
                            JSONObject jobj = new JSONObject(response);

                            JSONArray jarray = jobj.optJSONArray("result");

                            if (jarray.length() > 0)
                                setmenuItems(jarray);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //  swipe_reffresh.setVisibility(View.GONE);
                        utils.showProgressBar(progress_bar);
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                utils.volleyErrorResposne(error);

                if (error instanceof TimeoutError) {
                    //   Log.i(TAG, "time out error");
                } else if (error instanceof NoConnectionError) {

                }
                utils.showProgressBar(progress_bar);
                network_lay.setVisibility(View.VISIBLE);
            }

        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
//                params.put(Constants.BRAND_CHANNEL, Constants.BRAND_CHANNEL_DATA);
                params.put(Constants.DATE, utils.MIEBACH_PARAM_DATE.format(new Date()));
                params.put(Constants.USERNAME, utils.usersession.getloginname());
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
        ArsRequest.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void setmenuItems(JSONArray response) {

        utils.showLog(TAG, "menu items are " + response, Config.MENUACTIVITY);
        Menu menu = navigationView.getMenu();
        menu.setGroupCheckable(R.id.brands, true, true);

        int len = response.length();
        Constants.BRANDSLIST.clear();
        if (len > 0) {
            Constants.BRANDSLIST.addAll((ArrayList) new Gson().fromJson(response.toString(),
                    new TypeToken<ArrayList<BrandsList>>() {
                    }.getType()));

            Collections.sort(Constants.BRANDSLIST, new Comparator<BrandsList>() {
                @Override
                public int compare(BrandsList lhs, BrandsList rhs) {
                    return lhs.BRAND.compareToIgnoreCase(rhs.BRAND);
                }
            });


            int menu_count = 0;
            for (BrandsList item : Constants.BRANDSLIST) {
                menu.add(R.id.brands, menu_count, Menu.NONE, item.BRAND + " - " + item.CHANNEL);
                menu_count++;
            }
            menu.add(Constants.LOGOUT);

            navigationView.getMenu().getItem(0).setChecked(true);
            mpreviousMenuItem = navigationView.getMenu().getItem(0);
            utils.showLog(TAG, "selected items are " + navigationView.getMenu().getItem(0).getTitle().toString(), Config.MENUACTIVITY);

            Constants.SEL_BARND = navigationView.getMenu().getItem(0).getTitle().toString().split("-");
            utils.clearDashBoardItems();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainFrame, new Dashboard());
            fragmentTransaction.commit();

            setTitle(navigationView.getMenu().getItem(0).getTitle());
        } else {
            menu.add(Constants.LOGOUT);
        }
    }
}
