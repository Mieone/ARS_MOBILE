package heardun.in.ars.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import heardun.in.ars.R;
import heardun.in.ars.doto.GetCustomers;

/**
 * Created by headrun on 13/10/15.
 */
public class Getcustomers extends BaseAdapter {

    public String TAG = Getcustomers.this.getClass().getName();
    Context context;
    List<GetCustomers> list = new ArrayList<>();
    LayoutInflater inflater;

    public Getcustomers(Context context, List<GetCustomers> list) {
        this.context = context;
        this.list = list;
        Log.i(TAG, "list size is" + list.size());

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class getCustomer_Fields {

        TextView activity;
        TextView priority;
        TextView cust_code;
        TextView cust_type;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        try {
            getCustomer_Fields holder = null;

            if (convertView == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.getcustomers, null);
                holder = new getCustomer_Fields();

                holder.activity = (TextView) convertView.findViewById(R.id.active);
                holder.priority = (TextView) convertView.findViewById(R.id.priority);
                holder.cust_code = (TextView) convertView.findViewById(R.id.customer_Code);
                holder.cust_type = (TextView) convertView.findViewById(R.id.customer_type);

            } else {
                holder = (getCustomer_Fields) convertView.getTag();
            }
            Log.i(TAG, "list position is" + list.get(position));

            GetCustomers customer = list.get(position);

            holder.activity.setText(customer.getActive_flag());
            holder.priority.setText(customer.getPriority());
            holder.cust_code.setText(customer.getCustmoer_code());
            holder.cust_type.setText(customer.getCustomer_type());
            Log.i(TAG, "holder.activity" + holder.activity.getText());

        }catch (Exception e){
            e.printStackTrace();
        }
            return convertView;
        }

}
