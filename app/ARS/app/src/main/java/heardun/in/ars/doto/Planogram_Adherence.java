package heardun.in.ars.doto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by headrun on 20/10/15.
 */
public class Planogram_Adherence {

    public String key;
    public List<Planogram_values> values = new ArrayList<>();

    public Planogram_Adherence(String key, List<Planogram_values> values) {
        this.key = key;
        this.values = values;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Planogram_values> getValues() {
        return values;
    }

    public void setValues(List<Planogram_values> values) {
        this.values = values;
    }
}
