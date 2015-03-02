package com.gmail.takashi316.tminchart.view;

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
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.gmail.takashi316.tminchart.R;

import java.util.ArrayList;
import java.util.Random;


/**
 * TODO: document your custom view class.
 */
public class Seventeen extends View {
    static final String[] TCON_STRINGS = {"講", "謝", "績", "厳", "縮", "優", "覧", "曖", "臆", "嚇",
            "轄", "環", "擬", "犠", "矯", "謹", "謙", "鍵", "購", "懇",
            "擦", "爵", "醜", "償", "礁", "繊", "鮮", "燥", "霜", "戴",
            "濯", "鍛", "聴", "謄", "瞳", "謎", "鍋", "頻", "闇", "翼", "療", "瞭", "齢"};

    private String mExampleString = ""; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;
    private double widthInch;
    static private float xdpi, ydpi;
    private float xpixels, ypixels;
    private float pixels;
    private double intensity;
    private int textSize;
    private int color;
    private boolean touched = false;
    private String tconString;

    static final private Random random = new Random();
    static DisplayMetrics displayMetrics;

    public Seventeen(Context context, double width_inch, double intensity, final ArrayList<Seventeen> seventeens, String string) {
        super(context);
        init(null, 0);
        if (displayMetrics == null) {
            final WindowManager window_manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            final Display display = window_manager.getDefaultDisplay();
            displayMetrics = new DisplayMetrics();
            display.getMetrics(displayMetrics);
            this.xdpi = displayMetrics.xdpi;
            this.ydpi = displayMetrics.ydpi;
        }//if
        this.widthInch = width_inch;
        this.intensity = intensity;
        this.xpixels = (float) (this.xdpi * this.widthInch);
        this.ypixels = (float) (this.ydpi * this.widthInch);
        this.pixels = Math.max(xpixels, ypixels);
        this.mExampleString = "a";
        this.color = Color.rgb((int) Math.max(intensity, 0), (int) Math.max(intensity, 0), (int) Math.max(intensity, 0));
        this.mTextPaint.setColor(color);
        this.mTextPaint.setTextSize(pixels);
        if (string == null) {
            this.tconString = getTconString();
        } else {
            this.tconString = string;
        }//if

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean previously_touched = touched;
                if (seventeens != null) {
                    for (Seventeen seventeen : seventeens) {
                        seventeen.touched = false;
                    }//for
                }//if
                touched = !previously_touched;
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (Seventeen seventeen : seventeens) {
                            seventeen.invalidate();
                        }//for
                        try {
                            ToneGenerator tone_generator = new ToneGenerator(AudioManager.STREAM_SYSTEM, ToneGenerator.MAX_VOLUME);
                            tone_generator.startTone(ToneGenerator.TONE_PROP_BEEP);
                        } catch (Exception e) {
                            // failed on emulator devices
                            Log.e(getClass().getSimpleName(), e.getMessage());
                        }
                    }//run
                });//post
            }//onClick
        });//setOnClickListener
    }// custom constructor

    public Seventeen(Context context) {
        super(context);
        init(null, 0);
    }

    public Seventeen(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public Seventeen(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.Konoji, defStyle, 0);

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
    }

    private void invalidateTextPaintAndMeasurements() {
        try {
            mTextPaint.setTextSize(mExampleDimension);
            mTextPaint.setColor(mExampleColor);
            mTextWidth = mTextPaint.measureText(mExampleString);

            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
            mTextHeight = fontMetrics.bottom;
        } catch (Exception e) {
            //e.printStackTrace();
        }//try
    }//invalidateTextPaintAndMeasurements

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int canvas_width = canvas.getWidth();
        final int canvas_height = canvas.getHeight();
        final Paint.FontMetrics font_metrics = mTextPaint.getFontMetrics();
        final float width_margin = (canvas_width - pixels) / 2;
        final float height_margin = (canvas_height - pixels) / 2;
        canvas.drawText(this.tconString, width_margin, canvas_height - font_metrics.bottom - (height_margin / 2), mTextPaint);
        if (touched) {
            //this.setBackgroundColor(Color.RED);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                final Drawable frame = getResources().getDrawable(R.drawable.frame);
                this.setBackgroundDrawable(frame);
            } else {
                final Drawable frame = getResources().getDrawable(R.drawable.frame);
                this.setBackground(frame);
            }
        } else {
            this.setBackgroundColor(Color.WHITE);
        }//if
    }//onDraw

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
        canvas.drawText(mExampleString,
                paddingLeft + (contentWidth - mTextWidth) / 2,
                paddingTop + (contentHeight + mTextHeight) / 2,
                mTextPaint);

        // Draw the example drawable on top of the text.
        if (mExampleDrawable != null) {
            mExampleDrawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            mExampleDrawable.draw(canvas);
        }//if
    }//onDraw_

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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width_mode = MeasureSpec.getMode(widthMeasureSpec);
        int width_size = MeasureSpec.getSize(widthMeasureSpec);
        int height_mode = MeasureSpec.getMode(heightMeasureSpec);
        int height_size = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(getSuggestedMinimumWidth(), width_mode),
                MeasureSpec.makeMeasureSpec(getSuggestedMinimumHeight(), height_mode));
    }//onMeasure

    private String getTconString() {
        final int r = random.nextInt(TCON_STRINGS.length);
        return TCON_STRINGS[r];
    }//getTconString

    public Pair<Float, Integer> getResult() {
        return new Pair<Float, Integer>(pixels, (int) intensity);
    }//getResult

    public boolean isTouched() {
        return touched;
    }

    @Override
    public String toString() {
        return "(" + Float.toString(pixels) + "," + Integer.toString((int) intensity) + ")";
    }//toString
}//Seventeen
