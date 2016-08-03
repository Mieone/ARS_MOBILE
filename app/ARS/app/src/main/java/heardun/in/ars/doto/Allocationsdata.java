package heardun.in.ars.doto;

/**
 * Created by headrun on 19/10/15.
 */
public class Allocationsdata {

    public String store;
    public int values;

    public Allocationsdata(String store, int values) {
        this.store = store;
        this.values = values;
    }

    public Allocationsdata() {

    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public int getValues() {
        return values;
    }

    public void setValues(int values) {
        this.values = values;
    }
}
