package heardun.in.ars.doto;

/**
 * Created by headrun on 20/10/15.
 */
public class Planogram_values {

    public String names;
    public Long values;

    public Planogram_values(String names, Long values) {
        this.names = names;
        this.values = values;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public Long getValues() {
        return values;
    }

    public void setValues(Long values) {
        this.values = values;
    }
}
