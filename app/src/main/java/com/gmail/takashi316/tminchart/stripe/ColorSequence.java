package com.gmail.takashi316.tminchart.stripe;

import android.graphics.Color;

public class ColorSequence {
    //private double start_red, start_green, start_blue, end_red, end_green, end_blue;
    //private int n_steps;

    final int[] array;

    public ColorSequence(final int length) {
        this.array = new int[length];
    }

    private int size() {
        return array.length;
    }

    public int[] geometric(final int start_color, final int end_color) {
        final double start_red = Color.red(start_color);
        final double start_green = Color.green(start_color);
        final double start_blue = Color.blue(start_color);
        final double end_red = Color.red(end_color);
        final double end_green = Color.green(end_color);
        final double end_blue = Color.blue(end_color);

        final double common_ratio_green = Math.exp(Math.log(end_green / start_green) / (this.size() - 1));
        final double common_ratio_red = Math.exp(Math.log(end_red / start_red) / (this.size() - 1));
        final double common_ratio_blue = Math.exp(Math.log(end_blue / start_blue) / (this.size() - 1));
        for (int i = 0; i < this.size(); ++i) {
            final double current_green = start_green * Math.pow(common_ratio_green, i);
            final double current_blue = start_green * Math.pow(common_ratio_blue, i);
            final double current_red = start_green * Math.pow(common_ratio_red, i);
            final int color = Color.rgb((int) Math.round(current_red), (int) Math.round(current_green), (int) Math.round(current_blue));
            array[i] = color;
        }//for
        return array;
    }//geometric

    public int[] arithmetic(final int start_color, final int end_color) {
        final double start_red = Color.red(start_color);
        final double start_green = Color.green(start_color);
        final double start_blue = Color.blue(start_color);
        final double end_red = Color.red(end_color);
        final double end_green = Color.green(end_color);
        final double end_blue = Color.blue(end_color);

        final double common_difference_green = (end_green - start_green) / (this.size() - 1);
        final double common_difference_red = (end_red - start_red) / (this.size() - 1);
        final double common_difference_blue = (end_blue - start_blue) / (this.size() - 1);
        for (int i = 0; i < this.size(); ++i) {
            final double g = start_green + common_difference_green * i;
            final double r = start_green + common_difference_red * i;
            final double b = start_green + common_difference_blue * i;
            final int color = Color.rgb((int) Math.round(r), (int) Math.round(g), (int) Math.round(b));
            array[i] = color;
        }//for
        return array;
    }//arithmetic

    public int[] white() {
        return color(Color.WHITE);
    }//white

    public int[] black() {
        return color(Color.BLACK);
    }//black

    public int[] color(final int color) {
        for (int i = 0; i < this.size(); ++i) {
            array[i] = color;
        }//for
        return array;
    }//color

    public int[] increment(final int start_color, final int diff_color) {
        final double start_red = Color.red(start_color);
        final double start_green = Color.green(start_color);
        final double start_blue = Color.blue(start_color);
        final double diff_red = Color.red(diff_color);
        final double diff_green = Color.green(diff_color);
        final double diff_blue = Color.blue(diff_color);
        for (int i = 0; i < this.size(); ++i) {
            final double red = start_red + diff_red * i;
            final double green = start_green + diff_green * i;
            final double blue = start_blue + diff_blue * i;
            final int color = Color.rgb((int) Math.round(red), (int) Math.round(green), (int) Math.round(blue));
            array[i] = color;
        }//for
        return array;
    }//increment

    public int[] decrement(final int start_color, final int diff_color) {
        final double start_red = Color.red(start_color);
        final double start_green = Color.green(start_color);
        final double start_blue = Color.blue(start_color);
        final double diff_red = Color.red(diff_color);
        final double diff_green = Color.green(diff_color);
        final double diff_blue = Color.blue(diff_color);
        for (int i = 0; i < this.size(); ++i) {
            final double red = start_red - diff_red * i;
            final double green = start_green - diff_green * i;
            final double blue = start_blue - diff_blue * i;
            final int color = Color.rgb((int) Math.round(red), (int) Math.round(green), (int) Math.round(blue));
            array[i] = color;
        }//for
        return array;
    }//decrement

}//ColorSequence

