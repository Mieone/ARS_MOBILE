package heardun.in.ars.dashboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import heardun.in.ars.R;
import heardun.in.ars.config.Constants;
import heardun.in.ars.doto.DahsBoardDeatils;
import heardun.in.ars.utils.DividerItemDecoration;
import heardun.in.ars.utils.Utils;

/**
 * A fragment representing a single Item detail screen.
 * <p>
 * on handsets.
 */
public class DashBoard_InBoundDetailsFrg extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public String response = "";

    @BindView(R.id.item_list)
    RecyclerView item_list;
    public JSONObject jobj;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DashBoard_InBoundDetailsFrg() {
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
        View rootView = inflater.inflate(R.layout.dash_bard_inbound_details, container, false);
        ButterKnife.bind(this, rootView);

        item_list.setHasFixedSize(true);
        item_list.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.divider));

        try {
            jobj = new JSONObject(response);
            JSONArray jarry = jobj.optJSONArray(Constants.RESULTS);
            if (jarry.length() > 0)
                item_list.setAdapter(new SimpleItemRecyclerViewAdapter((ArrayList) new Gson().
                        fromJson(jobj.optJSONArray(Constants.RESULTS).toString(),
                                new TypeToken<ArrayList<DahsBoardDeatils>>() {
                                }.getType())));

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Show the dummy content as text in a TextView.

        return rootView;
    }


    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private ArrayList<DahsBoardDeatils> mlist;

        public SimpleItemRecyclerViewAdapter(ArrayList<DahsBoardDeatils> list) {
            this.mlist = list;

            new Utils().sortDashBoardDetails(mlist);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_inbound_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mlist.get(position);

            holder.mIdView.setTextColor(ContextCompat.getColor(getActivity(),
                    new Utils().dashBoardDetailsReasonColor(DashBoardDetailActivity.title_bar_title)));
            String item;
            holder.mIdView.setText(mlist.get(position).customer_name);
            if (DashBoardDetailActivity.title_bar_title.contains(Constants.ERROR)) {
                item = Arrays.toString(mlist.get(position).reason);
                holder.mContentView.setText(item.substring(1, item.length() - 1));
            } else {
                holder.mContentView.setText("");
            }
        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public DahsBoardDeatils mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.customer_name);
                mContentView = (TextView) view.findViewById(R.id.reason);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }

}
