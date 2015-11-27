package com.gmail.takashi316.tminchart.stripe;

import android.graphics.Color;

import java.util.ArrayList;

public class ColorSequence extends ArrayList<Integer> {
    //private double start_red, start_green, start_blue, end_red, end_green, end_blue;
    //private int n_steps;

    enum Mode {
        GEOMETRIC,
        ARITHMETIC,
        INCREMENTAL,
        DECREMENTAL,
        MONOTONE
    }

    Mode mode;
    int startColor;
    int endColor;
    int diffColor;

    private ColorSequence() {
        super();
    }

    static public ColorSequence geometric(final int start_color, final int end_color, final int size) {
        ColorSequence color_sequence = new ColorSequence();
        color_sequence.mode = Mode.GEOMETRIC;
        color_sequence.startColor = start_color;
        color_sequence.endColor = end_color;
        final double start_red = Color.red(start_color);
        final double start_green = Color.green(start_color);
        final double start_blue = Color.blue(start_color);
        final double end_red = Color.red(end_color);
        final double end_green = Color.green(end_color);
        final double end_blue = Color.blue(end_color);

        final double common_ratio_green = Math.exp(Math.log(end_green / start_green) / (size - 1));
        final double common_ratio_red = Math.exp(Math.log(end_red / start_red) / (size - 1));
        final double common_ratio_blue = Math.exp(Math.log(end_blue / start_blue) / (size - 1));
        for (int i = 0; i < size; ++i) {
            final double current_green = start_green * Math.pow(common_ratio_green, i);
            final double current_blue = start_green * Math.pow(common_ratio_blue, i);
            final double current_red = start_green * Math.pow(common_ratio_red, i);
            final int color = Color.rgb((int) Math.round(current_red), (int) Math.round(current_green), (int) Math.round(current_blue));
            color_sequence.add(color);
        }//for
        return color_sequence;
    }//geometric

    static public ColorSequence arithmetic(final int start_color, final int end_color, final int size) {
        ColorSequence color_sequence = new ColorSequence();
        color_sequence.mode = Mode.ARITHMETIC;
        color_sequence.startColor = start_color;
        color_sequence.endColor = end_color;
        final double start_red = Color.red(start_color);
        final double start_green = Color.green(start_color);
        final double start_blue = Color.blue(start_color);
        final double end_red = Color.red(end_color);
        final double end_green = Color.green(end_color);
        final double end_blue = Color.blue(end_color);

        final double common_difference_green = (end_green - start_green) / (size - 1);
        final double common_difference_red = (end_red - start_red) / (size - 1);
        final double common_difference_blue = (end_blue - start_blue) / (size - 1);
        for (int i = 0; i < size; ++i) {
            final double g = start_green + common_difference_green * i;
            final double r = start_green + common_difference_red * i;
            final double b = start_green + common_difference_blue * i;
            final int color = Color.rgb((int) Math.round(r), (int) Math.round(g), (int) Math.round(b));
            color_sequence.add(color);
        }//for
        return color_sequence;
    }//arithmetic

    static public ColorSequence white(final int size) {
        return monotone(Color.WHITE, size);
    }//white

    static public ColorSequence black(final int size) {
        return monotone(Color.BLACK, size);
    }//black

    static public ColorSequence monotone(final int color, final int size) {
        ColorSequence color_sequence = new ColorSequence();
        color_sequence.mode = Mode.MONOTONE;
        color_sequence.startColor = color;
        color_sequence.endColor = color;
        for (int i = 0; i < size; ++i) {
            color_sequence.add(color);
        }//for
        return color_sequence;
    }//monotone

    static public ColorSequence increment(final int start_color, final int diff_color, final int size) {
        ColorSequence color_sequence = new ColorSequence();
        color_sequence.mode = Mode.INCREMENTAL;
        color_sequence.startColor = start_color;
        color_sequence.diffColor = diff_color;
        final double start_red = Color.red(start_color);
        final double start_green = Color.green(start_color);
        final double start_blue = Color.blue(start_color);
        final double diff_red = Color.red(diff_color);
        final double diff_green = Color.green(diff_color);
        final double diff_blue = Color.blue(diff_color);
        for (int i = 0; i < size; ++i) {
            final double red = start_red + diff_red * i;
            final double green = start_green + diff_green * i;
            final double blue = start_blue + diff_blue * i;
            final int color = Color.rgb((int) Math.round(red), (int) Math.round(green), (int) Math.round(blue));
            color_sequence.add(color);
        }//for
        return color_sequence;
    }//increment

    static public ColorSequence decrement(final int start_color, final int diff_color, final int size) {
        ColorSequence color_sequence = new ColorSequence();
        color_sequence.mode = Mode.DECREMENTAL;
        color_sequence.startColor = start_color;
        color_sequence.diffColor = diff_color;
        final double start_red = Color.red(start_color);
        final double start_green = Color.green(start_color);
        final double start_blue = Color.blue(start_color);
        final double diff_red = Color.red(diff_color);
        final double diff_green = Color.green(diff_color);
        final double diff_blue = Color.blue(diff_color);
        for (int i = 0; i < size; ++i) {
            final double red = start_red - diff_red * i;
            final double green = start_green - diff_green * i;
            final double blue = start_blue - diff_blue * i;
            final int color = Color.rgb((int) Math.round(red), (int) Math.round(green), (int) Math.round(blue));
            color_sequence.add(color);
        }//for
        return color_sequence;
    }//decrement

}//ColorSequence

