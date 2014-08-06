package com.gmail.takashi316.tminchart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;


/**
 * TODO: document your custom view class.
 */
public class Seventeen extends View {
    static final String[] TCON_STRINGS  = {"講",	"謝", "績", "厳", "縮", "優", "覧", "曖", "臆",	"嚇",
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
    private float xdpi, ydpi;
    private float xpixels, ypixels;
    private float pixels;
    private double intensity;
    private int textSize;
    private int color;

    static private Random random = new Random();

    public Seventeen(Context context, double width_inch, double intensity, ArrayList<Seventeen> seventeens){
        super(context);
        init(null, 0);
        final WindowManager window_manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final Display display = window_manager.getDefaultDisplay();
        final DisplayMetrics display_metrics = new DisplayMetrics();
        display.getMetrics(display_metrics);
        this.xdpi = display_metrics.xdpi;
        this.ydpi = display_metrics.ydpi;
        this.widthInch = width_inch;
        this.intensity = intensity;
        this.xpixels = (float)(this.xdpi * this.widthInch);
        this.ypixels = (float)(this.ydpi * this.widthInch);
        this.pixels = Math.max(xpixels, ypixels);
        this.mExampleString = "a";
        this.color = Color.rgb((int)intensity, (int)intensity, (int)intensity);
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
        } catch (Exception e){
            e.printStackTrace();
        }//try
    }//invalidateTextPaintAndMeasurements

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int canvas_width = canvas.getWidth();
        final int canvas_height = canvas.getHeight();
        mTextPaint.setTextSize(pixels);
        canvas.drawText(getTconString(), 0, canvas_height, mTextPaint);
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width_mode = MeasureSpec.getMode(widthMeasureSpec);
        int width_size = MeasureSpec.getSize(widthMeasureSpec);
        int height_mode = MeasureSpec.getMode(heightMeasureSpec);
        int height_size = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.makeMeasureSpec((int)pixels+1, width_mode),
                MeasureSpec.makeMeasureSpec((int)pixels+1, height_mode));
    }//onMeasure

    private String getTconString(){
        final int r = random.nextInt(TCON_STRINGS.length);
        return TCON_STRINGS[r];
    }//getTconString

}//Seventeen
