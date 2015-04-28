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
        final float xdpi = this.displayMetrics.xdpi;
        if (xdpi == 0.0f) {
            return 160;
        } else {
            return xdpi;
        }
    }//getXdpi

    public float getYdpi() {
        final float ydpi = this.displayMetrics.ydpi;
        if (ydpi == 0.0f) {
            return 160;
        } else {
            return ydpi;
        }
    }//getYdpi
}//DisplayDpi
