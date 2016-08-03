package heardun.in.ars.config;

import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import heardun.in.ars.BuildConfig;
import heardun.in.ars.doto.Allocationsdata;
import heardun.in.ars.doto.Allocations;
import heardun.in.ars.doto.BrandsList;
import heardun.in.ars.doto.ChartDataList;
import heardun.in.ars.doto.CommanDataSet;
import heardun.in.ars.doto.GetCustomers;
import heardun.in.ars.doto.Planogram_Adherence;
import heardun.in.ars.doto.Planogram_values;
import heardun.in.ars.doto.SellThru;

/**
 * Created by headrun on 13/10/15.
 */
public class Constants {

    public static final int REQUEST_TIMEOUT = 30 * 1000;
    public static final int REQUEST_RETRY_TIMES = 1;
    public static final int WEEKLY = 7;
    public static final String GRAPH_PLANOGRAM_TOTAL_TITLE = "actual_mos";
    public static final String LOGOUT = "Sign out";


    public static List<GetCustomers> custmoers_data = new ArrayList<GetCustomers>();

    public static float screenwidth = 0;
    public static float screenheight = 0;


    public static int GRAPH_ALLOCATIONS = 1;
    public static int GRAPH_INBOUND = 2;
    public static int GRAPH_MASTER_VALID = 3;
    public static int GRAPH_SELLTHRU = 4;
    public static int GRAPH_STORE_SUFF = 5;
    public static int GRAPH_PLANO_ADH = 6;
    public static int GRAPH_MONTHLY_ALLOC = 7;
    public static int GRAPH_MONTHLY_ALLOC_STYLES = 8;
    public static int GRAPH_MONTHLY_ALLOC_SUBBRAND = 9;
    public static int GRAPH_PLANO_ADH_ACT_QTY = 10;
    public static int GRAPH_PLANO_ADH_OPT_QTY = 11;


    public static ArrayList<ChartDataList> DASHBOARD_CHART_DATA_LIST = new ArrayList<ChartDataList>();
    public static ArrayList<Allocations> Daily_allocations = new ArrayList<Allocations>();
    public static ArrayList<Allocations> GRAPH_INBOUND_LIST = new ArrayList<Allocations>();
    public static ArrayList<Allocations> GRAPH_MASTER_VALID_LIST = new ArrayList<Allocations>();
    public static ArrayList<CommanDataSet> GRAPH_SELL_THRU_LIST = new ArrayList<CommanDataSet>();
    public static ArrayList<CommanDataSet> GRAPH_MONTHLY_ALLOC_STYLES_LIST = new ArrayList<CommanDataSet>();
    public static ArrayList<CommanDataSet> GRAPH_MONTHLY_ALLOC_SUB_BRANDS_LIST = new ArrayList<CommanDataSet>();
    public static ArrayList<CommanDataSet> GRAPH_STORE_STOCK_SUFF = new ArrayList<CommanDataSet>();
    public static ArrayList<CommanDataSet> GRAPH_PLANO_ADH_ACT_QTY_LIST = new ArrayList<CommanDataSet>();
    public static ArrayList<CommanDataSet> GRAPH_PLANO_ADH_OPT_QTY_LIST = new ArrayList<CommanDataSet>();
    public static ArrayList<BrandsList> BRANDSLIST = new ArrayList<BrandsList>();
    public static List<String> Planogram_data_sets = new ArrayList<String>();
    public static int list_count = 0;

    public static int REQUEST_SWIPE = 1;
    public static int REQUEST_SCROLL = 2;
    public static String[] SEL_BARND = new String[2];


    public static int[] DailyAllocationColors = new int[]{Color.rgb(244, 67, 54), Color.rgb(255, 152, 0),
            Color.rgb(139, 195, 74), Color.rgb(96, 125, 139), Color.rgb(0, 188, 212), Color.rgb(121, 85, 72),
    };

   /* public static int[] DailyAllocationColors = new int[]{Color.rgb(244, 67, 54),Color.rgb(139, 195, 74),Color.rgb(121, 85, 72),
            Color.rgb(255, 152, 0),Color.rgb(96, 125, 139),
              Color.rgb(0, 188, 212),
    };*/
/*
    public static int[] GRAPH_CLORS = new int[]{Color.rgb(255, 152, 0), Color.rgb(0, 188, 212),
            Color.rgb(139, 195, 74), Color.rgb(96, 125, 139), Color.rgb(240, 220, 47), Color.rgb(203, 75, 75),
            Color.rgb(148, 64, 237), Color.rgb(244, 67, 54),
    };*/

    public static int[] GRAPH_CLORS = new int[]{
            Color.rgb(255, 152, 0), Color.rgb(0, 188, 212),
            Color.rgb(139, 195, 74), Color.rgb(96, 125, 139), Color.rgb(240, 220, 47),
            Color.rgb(203, 75, 75), Color.rgb(148, 64, 237), Color.rgb(244, 67, 54),
            Color.rgb(124, 181, 236), Color.rgb(67, 67, 72), Color.rgb(144, 237, 125),
            Color.rgb(247, 163, 92), Color.rgb(128, 133, 233), Color.rgb(241, 92, 128),
            Color.rgb(228, 211, 84), Color.rgb(43, 144, 143), Color.rgb(244, 91, 91),
            Color.rgb(255, 92, 79), Color.rgb(121, 85, 72), Color.rgb(0, 150, 136),
    };

    public static int[] GRAPH_STYLES_CLORS = new int[]{Color.rgb(149, 206, 255), Color.rgb(92, 92, 97)};

    public static int[] GRAPH_SUBBRAND_CLORS = new int[]{Color.rgb(149, 206, 255), Color.rgb(92, 92, 97),
            Color.rgb(144, 237, 125), Color.rgb(247, 163, 62), Color.rgb(139, 195, 74), Color.rgb(240, 220, 47),
            Color.rgb(96, 125, 139), Color.rgb(203, 75, 75), Color.rgb(0, 188, 212)};

    public static int[] GRAPH_STOCK_SUFF = new int[]{Color.rgb(255, 92, 79), Color.rgb(139, 195, 74),
            Color.rgb(96, 125, 139)};


    public static int[] GRAPH_INBOUND_COLOR = new int[]{Color.rgb(196, 76, 60), Color.rgb(139, 195, 74)};
    public static int[] GRAPH_MASTER_CLOR = new int[]{Color.rgb(244, 162, 54), Color.rgb(196, 76, 60), Color.rgb(139, 195, 74)};


    public static final String BRAND_CHANNEL = "brand_channel";
    public static final String DATE = "date";
    public static final String STYLE_CLASS = "style_class";
    public static final String SUBBRAND = "subbrand";
    public static final String BRAND_CHANNEL_DATA = BuildConfig.BRAND_CHANNEL;
    public static final String BRAND_DATA = BuildConfig.BRAND;
    public static final String CHANNEL_DATA = BuildConfig.CHANNEL;
    public static final String CORE = "CORE";
    public static final String FASHION = "FASHION";
    public static final String SUCESS = "Success";
    public static final String ERROR = "Error";
    public static final String CHECK = "Check";
    public static final String QTY_NORM = "quantity_norm";
    public static final String OPT_NORM = "options_norm";
    public static final String ACTUAL = "actual";
    public static final String ACTUAL_TITLE = "Actual Quantity";
    public static final String NORM = "norm";
    public static final String NORM_TITlE = "Norm Quantity";
    public static final String TYPE_PARAM = "type";
    public static final String RESULTS = "results";
    public static final String DASH_BOARD_ITEM_TYPE = "dash_board_item_type";
    public static final String GRAPH_SEL_POS = "GRAPH_SEL_POS";
    public static final String RESPONSE = "response";
    public static final String BRAND = "brand";
    public static final String CHANNEL = "channel";
    public static final String SELL_THRU_OVERALL = "sell_thru_overall";
    public static final String SELL_THRU_TOTAL = "total_sell_thru";
    public static final String USERNAME = "username";
    public static final String GRAPH_MONTHLY_ALLOC_TITLE = "Monthly Allocation Summary";

    public static int REFFRESH_PAGE = 0;
    public static String GRAPH_SELL_THRU_TOTAL_TITLE = "Sell Thru";
    public static String GRAPH_PLANOGRAPH_TOTAL_TITLE = "Actual MOS";
}
