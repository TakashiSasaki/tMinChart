package com.gmail.takashi316.tminchart.stripe;

import java.util.ArrayList;

public class PixelSequence extends ArrayList<Integer> {
    double start, end;
    int steps;

    public PixelSequence(int start, int end, int n_steps, boolean exponential) {
        this.start = start;
        this.end = end;
        this.steps = n_steps;
        if (exponential) {
            initGeometricSequence();
        } else {
            initArithmeticSequence();
        }//if
    }//PixelSequence

    public PixelSequence(int pixels, int n_size) {
        for (int i = 0; i < n_size; ++i) {
            this.add(pixels);
        }
    }//PixelSequence

    private void initGeometricSequence() {
        final double common_ratio = Math.exp(Math.log(this.end / this.start) / this.steps);
        for (int i = 0; i <= this.steps; ++i) {
            final double n_pixels = Math.pow(common_ratio, i) * this.start;
            this.add((int) Math.round(n_pixels));
        }//for
    }//initGeometricSequence

    private void initArithmeticSequence() {
        final double common_diff = (this.end - this.start) / this.steps;
        for (int i = 0; i <= this.steps; ++i) {
            final double n_pixels = this.start + common_diff * i;
            this.add((int) Math.round(n_pixels));
        }//for
    }//initArithmeticSequence
}//PixelSequence
