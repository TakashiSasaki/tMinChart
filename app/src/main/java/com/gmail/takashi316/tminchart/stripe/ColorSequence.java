package com.gmail.takashi316.tminchart.stripe;

import android.graphics.Color;

import java.util.ArrayList;

public class ColorSequence extends ArrayList<Integer> {
    private double start_red, start_green, start_blue, end_red, end_green, end_blue;
    private int n_steps;

    public ColorSequence(int start_color, int end_color, int n_steps, boolean exponential) {
        this.n_steps = n_steps;
        this.start_red = Color.red(start_color);
        this.start_green = Color.green(start_color);
        this.start_blue = Color.blue(start_color);
        this.end_red = Color.red(end_color);
        this.end_green = Color.green(end_color);
        this.end_blue = Color.blue(end_color);
        if (exponential) {
            this.initGeometricSequence();
        } else {
            this.initArithmeticSequence();
        }//if
    }//ColorSequence

    private void initGeometricSequence() {
        final double common_ratio_green = Math.exp(Math.log(this.end_green / this.start_green) / this.n_steps);
        final double common_ratio_red = Math.exp(Math.log(this.end_red / this.start_red) / this.n_steps);
        final double common_ratio_blue = Math.exp(Math.log(this.end_blue / this.start_blue) / this.n_steps);
        for (int i = 0; i <= this.n_steps; ++i) {
            final double current_green = this.start_green * Math.pow(common_ratio_green, i);
            final double current_blue = this.start_green * Math.pow(common_ratio_blue, i);
            final double current_red = this.start_green * Math.pow(common_ratio_red, i);
            final int color = Color.argb(0, (int) Math.round(current_red), (int) Math.round(current_green), (int) Math.round(current_blue));
            this.add(color);
        }//for
    }//initGeometricSequence

    private void initArithmeticSequence() {
        final double common_difference_green = (this.end_green - this.start_green) / n_steps;
        final double common_difference_red = (this.end_red - this.start_red) / n_steps;
        final double common_difference_blue = (this.end_blue - this.start_blue) / n_steps;
        for (int i = 0; i <= this.n_steps; ++i) {
            final double g = this.start_green + common_difference_green * i;
            final double r = this.start_green + common_difference_red * i;
            final double b = this.start_green + common_difference_blue * i;
            final int color = Color.argb(255, (int) Math.round(r), (int) Math.round(g), (int) Math.round(b));
            this.add(color);
        }//for
    }//initArithmeticSequence
}//ColorSequence

