package com.gmail.takashi316.tminchart;

/**
 * Created by sasaki on 2014/08/07.
 */
final public class ConstantValues {
    static public final double RATIO_5_TO_HALF = Math.pow(10.0, 0.2 * Math.log10(0.5));
    static public final double RATIO_10_TO_HALF = Math.pow(10.0, 0.1 * Math.log10(0.5));
    static public final double RATIO_20_TO_HALF = Math.pow(10.0, 0.05 * Math.log10(0.5));
    static public final double TCON_CHART_MAX_INCH = 0.5;
    static public final double TCON_CHART_SIZE_RATIO = RATIO_10_TO_HALF;
    static public final int TCON_CHART_ROWS = 100;
    static public final double TCON_CHART_CONTRAST_RATIO = RATIO_5_TO_HALF;
    static public final int TCON_CHART_COLUMNS = 40;
    static public final double TMIN_CHART_MAX_GAP_INCH = 0.2;
    static public final int TMIN_CHART_COUNT = 160;
    static public final double TMIN_CHART_RATIO = RATIO_20_TO_HALF;

}
