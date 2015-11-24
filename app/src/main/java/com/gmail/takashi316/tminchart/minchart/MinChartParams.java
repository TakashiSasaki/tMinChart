package com.gmail.takashi316.tminchart.minchart;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MinChartParams {
    public double minChartMaxGapInch;
    public double minChartRatio;
    public int minChartCount;

    public MinChartParams() {
        this.minChartCount = 200;
        this.minChartMaxGapInch = 0.3;
        this.minChartRatio = Math.pow(0.1, 0.01);
    }

    public void readSharedPreferences(Context context) {
        this.readSharedPreferences(PreferenceManager.getDefaultSharedPreferences(context));
    }

    public void readSharedPreferences(SharedPreferences shared_preferences) {
        try {
            this.minChartMaxGapInch = Double.parseDouble(shared_preferences.getString("tmin_chart_max_gap_inch", Double.toString(this.minChartMaxGapInch)));
            this.minChartRatio = Double.parseDouble(shared_preferences.getString("tmin_chart_ratio", Double.toString(this.minChartRatio)));
            this.minChartCount = Integer.parseInt(shared_preferences.getString("tmin_chart_count", Integer.toString(this.minChartCount)));
        } catch (Exception e) {
            e.printStackTrace();
        }//try
    }
}