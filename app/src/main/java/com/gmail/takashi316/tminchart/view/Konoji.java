package com.gmail.takashi316.tminchart.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.gmail.takashi316.tminchart.R;

import java.util.ArrayList;
import java.util.Random;


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
    private float gapInch;
    private float width_inch;
    private float xdpi;
    private float ydpi;
    private int orientation;
    private boolean touched = false;
    static Random random = new Random();
    private ArrayList<Konoji> konojis;

    public boolean isTouched() {
        return touched;
    }//isTouched

    public Konoji(Context context) {
        super(context);
        init(null, 0);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Konoji(Context context, float gap_inch, float width_inch, ArrayList<Konoji> konojis) {
        super(context);
        init(null, 0);
        gapInch = gap_inch;
        final WindowManager window_manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final Display display = window_manager.getDefaultDisplay();
        final DisplayMetrics display_metrics = new DisplayMetrics();
        display.getMetrics(display_metrics);
        this.xdpi = display_metrics.xdpi;
        this.ydpi = display_metrics.ydpi;
        this.orientation = random.nextInt(3) * 3;
        this.width_inch = width_inch;
        this.konojis = konojis;
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

        this.gapInch = a.getInt(R.styleable.Konoji_gap, 30);
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
                if (konojis != null) {
                    for (Konoji konoji : konojis) {
                        konoji.touched = false;
                    }//for
                }//if
                touched = true;
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    public void run() {
                        for (Konoji konoji : konojis) {
                            konoji.invalidate();
                        }
                        Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                        if (touched) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }//try
    }//invalidateTextPaintAndMeasurements

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width_mode = MeasureSpec.getMode(widthMeasureSpec);
        int width_size = MeasureSpec.getSize(widthMeasureSpec);
        int height_mode = MeasureSpec.getMode(heightMeasureSpec);
        int height_size = MeasureSpec.getSize(heightMeasureSpec);
        height_size = (int) (width_inch * xdpi);
        width_size = (int) (width_inch * ydpi);
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(width_size, width_mode),
                MeasureSpec.makeMeasureSpec(height_size, height_mode));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (touched) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                final Drawable frame = getResources().getDrawable(R.drawable.frame);
                this.setBackgroundDrawable(frame);
            } else {
                final Drawable frame = getResources().getDrawable(R.drawable.frame);
                this.setBackground(frame);
            }//if
        } else {
            setBackgroundColor(Color.WHITE);
            //konoji_paint.setColor(Color.BLACK);
        }//if
        final int xgap = (int) (xdpi * gapInch);
        final int ygap = (int) (ydpi * gapInch);
        final int view_width = (int) (width_inch * xdpi);
        final int view_height = (int) (width_inch * ydpi);
        final int top_margin = (view_width - xgap * 3) / 2;
        final int left_margin = (view_width - ygap * 3) / 2;
        final Paint konoji_paint = new Paint();
        canvas.drawRect(left_margin, top_margin, xgap * 3 - 1 + left_margin, ygap * 3 - 1 + top_margin, konoji_paint);
        final Paint gap_paint = new Paint();
        gap_paint.setColor(Color.WHITE);
        switch (orientation) {
            case 0:
                canvas.drawRect(left_margin + xgap, top_margin, xgap * 2 - 1 + left_margin, ygap * 2 - 1 + top_margin, gap_paint);
                break;
            case 3:
                canvas.drawRect(left_margin + xgap, top_margin + ygap, xgap * 3 - 1 + left_margin, ygap * 2 - 1 + top_margin, gap_paint);
                break;
            case 6:
                canvas.drawRect(left_margin + xgap, top_margin + ygap, xgap * 2 - 1 + left_margin, ygap * 3 - 1 + top_margin, gap_paint);
                break;
            case 9:
                canvas.drawRect(left_margin, top_margin + ygap, xgap * 2 - 1 + left_margin, ygap * 2 - 1 + top_margin, gap_paint);
                break;
            default:
                canvas.drawLine(left_margin, top_margin, xgap * 3 - 1 + left_margin, ygap * 3 - 1 + ygap, gap_paint);
                canvas.drawLine(xgap * 3 - 1 + left_margin, top_margin, left_margin, ygap * 3 - 1 + ygap, gap_paint);
                break;
        }//switch
    }

    private void fillCanvas(Canvas canvas, Paint paint) {
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
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
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }

    public float getGapInch() {
        return this.gapInch;
    }
}
