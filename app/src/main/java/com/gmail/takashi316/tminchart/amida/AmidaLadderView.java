package com.gmail.takashi316.tminchart.amida;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by sasaki on 2015/03/02.
 */
public class AmidaLadderView extends ImageView {

    private int lineWidth;
    private int[] leftLadders;
    private int[] rightLadders;
    private int ladderHeight;
    private int lineContrast;

    public AmidaLadderView(Context context, int line_width, int[] left_ladders, int[] right_ladders, int ladder_height, int line_contrast) {
        super(context);
        this.lineWidth = line_width;
        this.lineContrast = line_contrast;
        this.leftLadders = left_ladders;
        this.rightLadders = right_ladders;
        this.ladderHeight = ladder_height;
        this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.setBackgroundColor(Color.WHITE);
    }//AmidaStrip

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.argb(255, 255 - lineContrast, 255 - lineContrast, 255 - lineContrast));
        paint.setStrokeWidth(this.lineWidth);
        canvas.drawLine(canvas.getWidth() / 2, 0, canvas.getWidth() / 2, canvas.getHeight(), paint);
        for (int i = 0; i < this.leftLadders.length; ++i) {
            final float y = (float) canvas.getHeight() / (float) ladderHeight * (float) leftLadders[i];
            canvas.drawLine(canvas.getWidth() / 2, y, 0, y, paint);
        }//for
        for (int i = 0; i < this.rightLadders.length; ++i) {
            final float y = (float) canvas.getHeight() / (float) ladderHeight * (float) rightLadders[i];
            canvas.drawLine(canvas.getWidth() / 2, y, canvas.getWidth(), y, paint);
        }//for
    }//onDraw

}//AmidaLadderView

