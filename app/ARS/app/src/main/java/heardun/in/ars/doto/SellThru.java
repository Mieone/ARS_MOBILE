package heardun.in.ars.doto;

import java.util.ArrayList;

/**
 * Created by sujith on 9/5/16.
 */
public class SellThru {

    String storename;
    ArrayList<Allocations> storeStuff = new ArrayList<>();

    public SellThru(String storename, ArrayList<Allocations> storeStuff) {
        this.storename = storename;
        this.storeStuff = storeStuff;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public ArrayList<Allocations> getStoreStuff() {
        return storeStuff;
    }

    public void setStoreStuff(ArrayList<Allocations> storeStuff) {
        this.storeStuff = storeStuff;
    }
}
