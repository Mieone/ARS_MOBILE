package heardun.in.ars.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import heardun.in.ars.ArsRequest;
import heardun.in.ars.R;
import heardun.in.ars.Usersession;
import heardun.in.ars.config.Constants;
import heardun.in.ars.config.Serverconfig;
import heardun.in.ars.doto.GetCustomers;

import heardun.in.ars.utils.Getcustomers;

/**
 * Created by headrun on 13/10/15.
 */
public class Allocations extends Fragment {
    public String TAG = Allocations.this.getClass().getSimpleName();
    StringRequest string_req;
    ListView listview;
    ProgressBar progressbar;
    Getcustomers customerAdapter;
    Context context;
    Usersession usersession;

    public Allocations() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.dashboard, container, false);

        listview = (ListView) rootview.findViewById(R.id.datadisplay);
        progressbar = (ProgressBar) rootview.findViewById(R.id.progressbar);
        context = container.getContext();
        usersession = new Usersession(context);
        getCustomers();
        return rootview;
    }

    public void getCustomers() {

        progressbar.setVisibility(View.VISIBLE);
        Log.i(TAG, "getcustomers");
        string_req = new StringRequest(Request.Method.GET, Serverconfig.SERVER_ENDPOINT + Serverconfig.getcustomers,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i(TAG, "response is" + response);
                        try {
                            JSONArray jarray = new JSONArray(response);
                            if (jarray.length() > 0)
                                Constants.custmoers_data.clear();
                            for (int i = 0; i < jarray.length(); i++) {
                                JSONObject jobj = jarray.getJSONObject(i);
                                Constants.custmoers_data.add(
                                        new GetCustomers(jobj.getString("priority"), jobj.getString("active_flag"), jobj.getString("cust_code"), jobj.getString("cust_type")));
                            }
                            if (Constants.custmoers_data.size() > 0) {
                                customerAdapter = new Getcustomers(context, Constants.custmoers_data);
                                listview.setAdapter(customerAdapter);
                            }

                            Log.i(TAG, "list size is" + Constants.custmoers_data.size());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressbar.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "error response is" + error);
                progressbar.setVisibility(View.GONE);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
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
                }

                return headers;
            }
        };

        string_req.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        3,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

        );

        string_req.setTag(TAG);
        ArsRequest.getInstance(getActivity()).addToRequestQueue(string_req);
    }

}


