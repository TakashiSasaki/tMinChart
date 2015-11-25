package com.gmail.takashi316.tminchart.stripe;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.gmail.takashi316.tminchart.R;

import java.util.ArrayList;

class StripeViewParams {
    public boolean horizontal = false;
    public int foregroundWidth = 1;
    public int backgroundWidth = 1;
    public int foregroundColor;
    public int backgroundColor;
    public int frameColor = Color.RED;
    public boolean touched = false;
}

public class StripeView extends FrameView {
    private StripeViewParams stripeViewParams;
    private ArrayList<StripeView> stripeViews;


    public boolean isTouched() {
        return this.stripeViewParams.touched;
    }//isTouched

    public StripeView(Context context) {
        super(context);
        this.stripeViewParams = new StripeViewParams();
        this.init();
    }//StripeViewConstructor

    public StripeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.stripeViewParams = new StripeViewParams();
        this.obtainStyledAttributes(attrs, 0);
        this.init();
    }//StripeView constructor

    public StripeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.stripeViewParams = new StripeViewParams();
        this.obtainStyledAttributes(attrs, defStyle);
        this.init();
    }//StripeView constructor

    private void obtainStyledAttributes(AttributeSet attrs, int defStyle) {
        final TypedArray typed_array = this.getContext().obtainStyledAttributes(
                attrs, R.styleable.StripeView, defStyle, 0);
        this.setHorizontal(typed_array.getBoolean(R.styleable.StripeView_horizontal, this.stripeViewParams.horizontal));
        this.stripeViewParams.foregroundColor = typed_array.getColor(R.styleable.StripeView_foregroundColor, Color.BLACK);
        this.setBackgroundColor(typed_array.getColor(R.styleable.StripeView_backgroundColor, Color.WHITE));
        this.setFrameColor(typed_array.getColor(R.styleable.StripeView_frameColor, this.stripeViewParams.frameColor));
        this.stripeViewParams.touched = typed_array.getBoolean(R.styleable.StripeView_touched, this.stripeViewParams.touched);
        typed_array.recycle();
    }

    private void init() {
        //this.displayDpi = new DisplayDpi(context);

        this.setOnClickListener(new OnClickListener() {

            //@Override
            public void onClick(View v) {
                StripeView touched_stripe_view = (StripeView) v;
                try {
                    for (StripeView stripe_view : StripeView.this.stripeViews) {
                        if (stripe_view != touched_stripe_view) {
                            if (stripe_view.isTouched()) {
                                stripe_view.setTouched(false);
                                stripe_view.postInvalidate();
                            }//if
                        }//if
                    }//for
                } catch (NullPointerException e) {
                }//try
                touched_stripe_view.toggleTouched();
                touched_stripe_view.postInvalidate();
            }//onClick

        });
    }//init

    public void setStripeViews(ArrayList<StripeView> stripe_views) {
        this.stripeViews = stripe_views;
    }//setStripeViews

    public void setHorizontal(boolean horizontal) {
        this.stripeViewParams.horizontal = horizontal;
    }

    public void setForegroundColor(int foreground_color) {
        this.stripeViewParams.foregroundColor = foreground_color;
    }//setForegroundColor

    public void setBackgroundColor(int background_color) {
        this.stripeViewParams.backgroundColor = background_color;
    }//setBackgroundColor

    public void setFrameColor(int frame_color) {
        this.stripeViewParams.frameColor = frame_color;
    }

    public void setForegroundWidth(int foreground_width) {
        this.stripeViewParams.foregroundWidth = foreground_width;
    }

    public void setBackgroundWidth(int background_width) {
        this.stripeViewParams.backgroundWidth = background_width;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width_mode = MeasureSpec.getMode(widthMeasureSpec);
        int width_size = MeasureSpec.getSize(widthMeasureSpec);
        int height_mode = MeasureSpec.getMode(heightMeasureSpec);
        int height_size = MeasureSpec.getSize(heightMeasureSpec);

        //this.displayDpi = new DisplayDpi(getContext());

        if (width_mode == MeasureSpec.UNSPECIFIED) {
            // it corresponds to setting length to  1000000px on layout designer
            Log.v(this.getClass().getSimpleName(), "width_mode = MeasureSpec.UNSPECIFIED");
            width_size = (int) (this.getResources().getDisplayMetrics().xdpi);
        } else if (width_mode == MeasureSpec.EXACTLY) {
            //it corresponds to setting length to 10px or match_parent on layout designer
            Log.v(this.getClass().getSimpleName(), "width_mode = MeasureSpec.EXACTLY");
        } else if (width_mode == MeasureSpec.AT_MOST) {
            //it corresponds to setting length to wrap_content on layout designer
            Log.v(this.getClass().getSimpleName(), "width_mode = MeasureSpec.AT_MOST");
            width_size = (int) (this.getResources().getDisplayMetrics().xdpi);
        } else {
            Log.v(this.getClass().getSimpleName(), "width_mode is unknown");
            width_size = 400;
        }//if

        if (height_mode == MeasureSpec.UNSPECIFIED) {
            Log.v(this.getClass().getSimpleName(), "height_mode = MeasureSpec.UNSPECIFIED");
            height_size = (int) (this.getResources().getDisplayMetrics().ydpi);
        } else if (height_mode == MeasureSpec.EXACTLY) {
            Log.v(this.getClass().getSimpleName(), "height_mode = MeasureSpec.EXACTLY");
        } else if (height_mode == MeasureSpec.AT_MOST) {
            Log.v(this.getClass().getSimpleName(), "height_mode = MeasureSpec.AT_MOST");
            height_size = (int) (this.getResources().getDisplayMetrics().ydpi);
        } else {
            Log.v(this.getClass().getSimpleName(), "height_mode is unknown");
            height_size = 400;
        }//if
        Log.v(this.getClass().getSimpleName(), "width_size=" + width_size + " height_size=" + height_size);
        this.setMeasuredDimension(MeasureSpec.makeMeasureSpec(width_size, width_mode),
                MeasureSpec.makeMeasureSpec(height_size, height_mode));
    }//onMeasure

    private void toggleTouched() {
        this.stripeViewParams.touched = !this.stripeViewParams.touched;
    }//toggleTouched

    private void setTouched(boolean touched) {
        this.stripeViewParams.touched = touched;
    }//setTouched

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final Paint foreground_paint = new Paint();
        foreground_paint.setColor(this.stripeViewParams.foregroundColor);
        foreground_paint.setStyle(Paint.Style.FILL);
        foreground_paint.setStrokeWidth(1);
        final Paint background_paint = new Paint();
        background_paint.setColor(this.stripeViewParams.backgroundColor);
        background_paint.setStyle(Paint.Style.FILL);
        background_paint.setStrokeWidth(1);

        if (this.stripeViewParams.horizontal) {
            int top = this.getPaddingTop();
            int left = this.getPaddingLeft();
            int right = canvas.getWidth() - this.getPaddingRight();
            while (top < canvas.getHeight()) {
                Log.v(this.getClass().getSimpleName(), "horizontal rectangle. left=" + left + " top=" + top + " right=" + right + " bottom=" + top + this.stripeViewParams.backgroundWidth);
                canvas.drawRect(left, top, right, top + this.stripeViewParams.backgroundWidth, background_paint);
                top += this.stripeViewParams.backgroundWidth;
                Log.v(this.getClass().getSimpleName(), "horizontal rectangle. left=" + left + " top=" + top + " right=" + right + " bottom=" + top + this.stripeViewParams.foregroundWidth);
                canvas.drawRect(left, top, right, top + this.stripeViewParams.foregroundWidth, foreground_paint);
                top += this.stripeViewParams.foregroundWidth;
            }//while
        } else {
            int top = this.getPaddingTop();
            int bottom = canvas.getHeight() - this.getPaddingBottom();
            int left = this.getPaddingLeft();
            while (left < canvas.getWidth()) {
                Log.v(this.getClass().getSimpleName(), "vertical rectangle. left=" + left + " top=" + top + " right=" + this.stripeViewParams.backgroundWidth + " bottom=" + bottom);
                canvas.drawRect(left, top, left + this.stripeViewParams.backgroundWidth, bottom, background_paint);
                left += this.stripeViewParams.backgroundWidth;
                Log.v(this.getClass().getSimpleName(), "vertical rectangle. left=" + left + " top=" + top + " right=" + this.stripeViewParams.foregroundWidth + " bottom=" + bottom);
                canvas.drawRect(left, top, left + this.stripeViewParams.foregroundWidth, bottom, foreground_paint);
                left += this.stripeViewParams.foregroundWidth;
            }//while
        }//if
        this.updateFrame(canvas);
    }//onDraw

    void updateFrame(Canvas canvas) {
        if (this.stripeViewParams.touched) {
            this.drawFrame(canvas, this.stripeViewParams.frameColor);
        } else {
            final Paint background_paint = new Paint();
            background_paint.setColor(this.stripeViewParams.backgroundColor);
            background_paint.setStyle(Paint.Style.FILL);
            background_paint.setStrokeWidth(1);
            this.drawFrame(canvas, background_paint);
        }//if
    }//updateFrame

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }//onLayout

}//class StripeView

