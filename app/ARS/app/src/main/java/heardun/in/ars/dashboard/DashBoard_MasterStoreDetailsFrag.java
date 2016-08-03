package heardun.in.ars.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import heardun.in.ars.R;
import heardun.in.ars.config.Config;
import heardun.in.ars.config.Constants;
import heardun.in.ars.doto.DahsBoardDeatils;
import heardun.in.ars.utils.DividerItemDecoration;
import heardun.in.ars.utils.Utils;

/**
 * Created by sujith on 13/5/16.
 */
public class DashBoard_MasterStoreDetailsFrag extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public String response = "";
    public static String TAG = DashBoard_MasterStoreDetailsFrag.class.getCanonicalName();
    @BindView(R.id.expand_list)
    ExpandableListView expand_list;
    public JSONObject jobj;
    Utils utils;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DashBoard_MasterStoreDetailsFrag() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(Constants.RESPONSE)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            response = getArguments().getString(Constants.RESPONSE);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dash_board_master_details, container, false);
        ButterKnife.bind(this, rootView);

        utils = new Utils(getActivity());
        try {

            jobj = new JSONObject(response);
            JSONArray jarry = jobj.optJSONArray(Constants.RESULTS);
            if (jarry.length() > 0) {
                ArrayList<DahsBoardDeatils> list = (ArrayList) new Gson().
                        fromJson(jobj.optJSONArray(Constants.RESULTS).toString(),
                                new TypeToken<ArrayList<DahsBoardDeatils>>() {
                                }.getType());


                utils.sortDashBoardDetails(list);
                /*utils.showLog(TAG, "master lsit size is " + list.size(), Config.DASHBOARD);

                for (DahsBoardDeatils item : list)
                    utils.showLog(TAG, " master lsit size is " + item.customer_name + " reasone is " + item.reason, Config.DASHBOARD);*/
                expand_list.setAdapter(new ExpandablemasterAdapter(list, getActivity()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }


    public class ExpandablemasterAdapter extends BaseExpandableListAdapter {

        ArrayList<DahsBoardDeatils> list;
        Context context;

        public ExpandablemasterAdapter(ArrayList<DahsBoardDeatils> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @Override
        public int getGroupCount() {
            return list.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return list.get(groupPosition).reason.length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return list.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return list.get(groupPosition).reason[childPosition];
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            DahsBoardDeatils item = (DahsBoardDeatils) getGroup(groupPosition);

            try {
                if (convertView == null) {
                    LayoutInflater infalInflater = (LayoutInflater) this.context
                            .getSystemService(context.LAYOUT_INFLATER_SERVICE);
                    convertView = infalInflater.inflate(R.layout.item_master_header_content, null);
                }

                TextView lblListHeader = (TextView) convertView
                        .findViewById(R.id.customer_name);

                lblListHeader.setTextColor(
                        ContextCompat.getColor(context,
                                utils.dashBoardDetailsReasonColor(DashBoardDetailActivity.title_bar_title)));

                lblListHeader.setText(item.customer_name);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            final String childText = (String) getChild(groupPosition, childPosition);

            try {
                if (convertView == null) {
                    LayoutInflater infalInflater = (LayoutInflater) this.context
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = infalInflater.inflate(R.layout.item_master_content, null);
                }

                TextView txtListChild = (TextView) convertView
                        .findViewById(R.id.reason);
                TextView srno = (TextView) convertView
                        .findViewById(R.id.sr_no);

                txtListChild.setText(childText);
                srno.setText("" + (childPosition + 1));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

}

