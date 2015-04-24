package com.gmail.takashi316.tminchart;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class DisplayDpi {

    private DisplayMetrics displayMetrics = new DisplayMetrics();

    public DisplayDpi(Context context) {
        final WindowManager window_manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final Display display = window_manager.getDefaultDisplay();
        display.getMetrics(this.displayMetrics);
    }//DisplayDpi

    public float getXdpi() {
        return this.displayMetrics.xdpi;
    }//getXdpi

    public float getYdpi() {
        return this.displayMetrics.ydpi;
    }//getYdpi
}//DisplayDpi
