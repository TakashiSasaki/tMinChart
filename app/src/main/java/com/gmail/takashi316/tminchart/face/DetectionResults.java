package com.gmail.takashi316.tminchart.face;

import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by sasaki on 2015/02/25.
 */
public class DetectionResults {
    enum MAGNIFICATION {
        TOO_LARGE, TOO_SMALL, NONE
    }//MAGNIFICATION

    final private ArrayList<Rect> results;
    int nResults;

    public DetectionResults(int n_results) {
        this.nResults = n_results;
        this.results = new ArrayList<Rect>();
    }// the constructor

    public void clear() {
        results.clear();
    }

    public void add(Rect r) {
        this.results.add(r);
        if (this.results.size() > this.nResults) {
            this.results.remove(0);
        }//if
        if (this.results.size() > this.nResults) {
            this.results.remove(0);
        }//if
    }//add

    static private MAGNIFICATION compareOne(Rect r, int base_width, int base_height, double max_magnification, double min_magnification) {
        if (r.width() > base_width * max_magnification && r.height() > base_height * max_magnification) {
            return MAGNIFICATION.TOO_LARGE;
        } else if (r.width() < base_width * min_magnification && r.height() < base_height * min_magnification) {
            return MAGNIFICATION.TOO_SMALL;
        } else {
            return MAGNIFICATION.NONE;
        }//if
    }//compare

    public char[] getResultsAsChars(int base_width, int base_height, double min_magnification, double max_magnification) {
        char[] chars = new char[this.results.size()];
        for (int i = 0; i < chars.length; ++i) {
            switch (this.compareOne(this.results.get(i), base_width, base_height, min_magnification, max_magnification)) {
                case NONE:
                    chars[i] = '=';
                    continue;
                case TOO_SMALL:
                    chars[i] = '-';
                    continue;
                case TOO_LARGE:
                    chars[i] = '+';
                    continue;
            }//switch
        }//for
        return chars;
    }//getResults

    public MAGNIFICATION compare(int base_width, int base_height, double min_magnification, double max_magnification) {
        int n_too_small = 0;
        int n_too_large = 0;
        for (Rect r : this.results) {
            switch (this.compareOne(r, base_width, base_height, min_magnification, max_magnification)) {
                case NONE:
                    continue;
                case TOO_SMALL:
                    ++n_too_small;
                    continue;
                case TOO_LARGE:
                    ++n_too_large;
                    continue;
            }//switch
        }//for
        if (n_too_large == this.results.size()) {
            return MAGNIFICATION.TOO_LARGE;
        } else if (n_too_small == this.results.size()) {
            return MAGNIFICATION.TOO_SMALL;
        } else {
            return MAGNIFICATION.NONE;
        }//if
    }//compare

    public double getAverage(int base_width, int base_height) {
        double sum = 0;
        for (Rect r : this.results) {
            sum += r.width();
            sum += r.height();
        }//for
        final double denominator = ((base_height + base_width) * (double) this.results.size());
        return sum / denominator;
    }//getAverage

}//DetectionResults
