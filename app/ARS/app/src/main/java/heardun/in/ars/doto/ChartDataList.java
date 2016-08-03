package heardun.in.ars.doto;

import heardun.in.ars.chart.ChartItem;

/**
 * Created by sujith on 18/5/16.
 */
public class ChartDataList {

    int dash_board_item;
    ChartItem chartitem;

    public ChartDataList(int dash_board_item, ChartItem chartitem) {
        this.dash_board_item = dash_board_item;
        this.chartitem = chartitem;
    }

    public int getDash_board_item() {
        return dash_board_item;
    }

    public void setDash_board_item(int dash_board_item) {
        this.dash_board_item = dash_board_item;
    }

    public ChartItem getChartitem() {
        return chartitem;
    }

    public void setChartitem(ChartItem chartitem) {
        this.chartitem = chartitem;
    }
}
