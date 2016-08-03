package heardun.in.ars.doto;

import org.json.JSONObject;

/**
 * Created by sujith on 9/5/16.
 */
public class CommanDataSet {
    String key_val;
    JSONObject data;


    public CommanDataSet(String key_val, JSONObject data) {
        this.key_val = key_val;
        this.data = data;
    }

    public String getKey_val() {
        return key_val;
    }

    public void setKey_val(String key_val) {
        this.key_val = key_val;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }
}
