package com.gmail.takashi316.tminchart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;


/**
 * TODO: document your custom view class.
 */
public class Konoji extends View {
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;
    private float contrast;
    private int gap;
    private int orientation;
    private boolean touched = false;

    public boolean isTouched(){
        return touched;
    }//isTouched

    public Konoji(Context context) {
        super(context);
        init(null, 0);
    }

    public Konoji(Context context, int gap, int orientation, float contrast) {
        super(context);
        init(null, 0);
        this.gap = gap;
        this.orientation = orientation;
        this.contrast = contrast;
        this.mExampleString = "a";

    }

    public Konoji(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public Konoji(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.Konoji, defStyle, 0);

        this.contrast = a.getFloat(R.styleable.Konoji_contrast, 0.5f);
        this.gap = a.getInt(R.styleable.Konoji_gap, 30);
        this.orientation = a.getInt(R.styleable.Konoji_gap, 0);
        this.touched = a.getBoolean(R.styleable.Konoji_touched, false);

        mExampleString = a.getString(
                R.styleable.Konoji_exampleString);
        mExampleColor = a.getColor(
                R.styleable.Konoji_exampleColor,
                mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.Konoji_exampleDimension,
                mExampleDimension);

        if (a.hasValue(R.styleable.Konoji_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.Konoji_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                touched = touched ? false: true;
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    public void run() {
                        invalidate();
                        Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                        if(touched) {
                            vibrator.vibrate(new long[]{0, 60, 60, 60}, -1);
                        } else {
                            vibrator.vibrate(new long[]{0, 100}, -1);
                        }//if
                        ToneGenerator tone_generator = new ToneGenerator(AudioManager.STREAM_SYSTEM, ToneGenerator.MAX_VOLUME);
                        tone_generator.startTone(ToneGenerator.TONE_PROP_BEEP);
                    }//run
                });
            }//onClick
        });
    }

    private void invalidateTextPaintAndMeasurements() {
        try {
            mTextPaint.setTextSize(mExampleDimension);
            mTextPaint.setColor(mExampleColor);
            mTextWidth = mTextPaint.measureText(mExampleString);

            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
            mTextHeight = fontMetrics.bottom;
        } catch (Exception e){
            e.printStackTrace();
        }//try
    }//invalidateTextPaintAndMeasurements

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        final Paint background_paint = new Paint();
        //background_paint.setColor(Color.YELLOW);
        //fillCanvas(canvas, background_paint);
        final Paint konoji_paint = new Paint();
        if(touched) {
            konoji_paint.setColor(Color.RED);
        } else {
            konoji_paint.setColor(Color.BLACK);
        }//if
        canvas.drawRect(0,0,gap*3-1, gap*3-1, konoji_paint);
        final Paint gap_paint = new Paint();
        gap_paint.setColor(Color.WHITE);
        drawKonoji(canvas, gap_paint);
    }//onDraw

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width_mode = MeasureSpec.getMode(widthMeasureSpec);
        int width_size = MeasureSpec.getSize(widthMeasureSpec);
        int height_mode = MeasureSpec.getMode(heightMeasureSpec);
        int height_size = MeasureSpec.getSize(heightMeasureSpec);
        width_size = 80;
        height_size = 80;
        setMeasuredDimension(width_size, height_size);
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }



    private void drawKonoji(Canvas canvas, Paint gap_paint){
        switch(orientation){
            case 0:
                canvas.drawRect(gap, 0, gap*2-1, gap*2-1, gap_paint);
                break;
            case 3:
                canvas.drawRect(gap, gap, gap*3-1, gap*2-1, gap_paint);
                break;
            case 6:
                canvas.drawRect(gap, gap, gap*2-1, gap*3-1, gap_paint);
                break;
            case 9:
                canvas.drawRect(0,gap, gap*2-1, gap*2-1, gap_paint);
                break;
            default:
                canvas.drawLine(0,0, gap*3-1, gap*3-1, gap_paint);
                break;
        }//switch
    }

    private void fillCanvas(Canvas canvas, Paint paint){
        canvas.drawRect(0,0,canvas.getWidth(), canvas.getHeight(), paint);
    }//fillCanvas

    protected void onDraw_(Canvas canvas) {
        //super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // Draw the text.
        mTextPaint = new TextPaint();
        canvas.drawText(mExampleString,
                paddingLeft + (contentWidth - mTextWidth) / 2,
                paddingTop + (contentHeight + mTextHeight) / 2,
                mTextPaint);

        // Draw the example drawable on top of the text.
        if (mExampleDrawable != null) {
            mExampleDrawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            mExampleDrawable.draw(canvas);
        }

        // Draw the text again.
        mTextPaint = new TextPaint();
        canvas.drawText(mExampleString,
                paddingLeft + (contentWidth - mTextWidth) / 2,
                paddingTop + (contentHeight + mTextHeight) / 2,
                mTextPaint);
    }

    /**
     * Gets the example string attribute value.
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }
}
