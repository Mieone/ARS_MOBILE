package heardun.in.ars.config;

import heardun.in.ars.BuildConfig;

/**
 * Created by headrun on 10/10/15.
 */
public class Serverconfig {
   public static String SERVER_ENDPOINT1 = BuildConfig.SERVER_ENDPOINT1;
    //public static String SERVER_ENDPOINT1 = "http://5.9.63.147:9301/";
    public static String SERVER_ENDPOINT = BuildConfig.SERVER_DASHBOARD;
    // public static String SERVER_ENDPOINT = "http://ars-dev.miebach.in/";

    public static String SERVER_ENDPOINT2 = "http://miebach.in:9010/";
    public static String login = "login/";
    public static String getcustomers = "get_customers/";
    public static String get_linechart_data = "get_all_allocations_per_day/";
    public static String planogram_adherence = "planogram_adherence/";
    public static String DIALY_ALLOCATIONS_SUMMARY = "http://5.9.63.147:9301/allocations?date=2016-05-06";

    public static String GRAPH_ALLOCATIONS = SERVER_ENDPOINT + "graph/allocations/";
    public static String GRAPH_INBOUND = SERVER_ENDPOINT + "graph/inbound/";
    public static String GRAPH_MASTER_VALID = SERVER_ENDPOINT + "graph/master_valid/";
    public static String GRAPH_SELLTHRU = SERVER_ENDPOINT + "graph/sellthru_graph/";
    public static String GRAPH_STORE_STUFF = SERVER_ENDPOINT + "graph/store_suff_graph/";
    public static String GRAPH_PLANO_ADH = SERVER_ENDPOINT + "graph/plano_adh_graph/";
    public static String GRAPH_MONTHLY_ALLOC = SERVER_ENDPOINT + "graph/monthly_alloc_graph/";
    public static String ALBL_BRAND = SERVER_ENDPOINT1 + "albl-brand";

    public static String GRAPH_ALLOCATIONS_DETAILS = SERVER_ENDPOINT1 + "graph/allocations/";
    public static String GRAPH_INBOUND_DETAILS = SERVER_ENDPOINT1 + "ARS";
    public static String GRAPH_MASTER_VALID_DETAILS = SERVER_ENDPOINT1 + "Master";
    public static String GRAPH_SELLTHRU_DETAILS = SERVER_ENDPOINT1 + "graph/sellthru_graph/";
    public static String GRAPH_STORE_STUFF_DETAILS = SERVER_ENDPOINT1 + "graph/store_suff_graph/";
    public static String GRAPH_PLANO_ADH_DETAILS = SERVER_ENDPOINT1 + "graph/plano_adh_graph/";
    public static String GRAPH_MONTHLY_ALLOC_DETAILS = SERVER_ENDPOINT1 + "graph/monthly_alloc_graph/";

}
