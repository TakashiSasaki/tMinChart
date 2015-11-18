package com.gmail.takashi316.tminchart.minchart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.gmail.takashi316.tminchart.R;

public class HeaderLabelView extends View {
    private int indexOrigin;
    private final int DEFAULT_INDEX_ORIGIN = 0;
    private int indexOffset = 1;
    private final int DEFAULT_INDEX_OFFSET = 0;
    private boolean isAlphabetical = false;
    private final boolean DEFAULT_IS_ALPHABETICAL = false;
    private String text = "";
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float textDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    public HeaderLabelView(Context context) {
        super(context);
        init(null, 0);
    }

    public HeaderLabelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public HeaderLabelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.HeaderLabelView, defStyle, 0);

        this.indexOffset = a.getInt(R.styleable.HeaderLabelView_indexOffset, DEFAULT_INDEX_OFFSET);
        this.indexOrigin = a.getInt(R.styleable.HeaderLabelView_indexOrigin, DEFAULT_INDEX_ORIGIN);
        this.isAlphabetical = a.getBoolean(R.styleable.HeaderLabelView_isAlphabetical, DEFAULT_IS_ALPHABETICAL);
        if (this.isAlphabetical) {
            this.text = new String(new byte[]{(byte) (this.indexOrigin + this.indexOffset + 64)});
        } else {
            this.text = Integer.toString(this.indexOrigin + this.indexOffset);
        }//if

        mExampleString = a.getString(
                R.styleable.HeaderLabelView_exampleString);
        mExampleColor = a.getColor(
                R.styleable.HeaderLabelView_textColor,
                mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        textDimension = a.getDimension(
                R.styleable.HeaderLabelView_textDimension,
                textDimension);

        if (a.hasValue(R.styleable.HeaderLabelView_backgroundDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.HeaderLabelView_backgroundDrawable);
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
        mTextPaint.setTextSize(textDimension);
        mTextPaint.setColor(mExampleColor);
        mTextWidth = mTextPaint.measureText(mExampleString);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        final int paddingLeft = getPaddingLeft();
        final int paddingTop = getPaddingTop();
        final int paddingRight = getPaddingRight();
        final int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // Draw the text.
        canvas.drawText(this.text,
                paddingLeft + (contentWidth - mTextWidth) / 2,
                paddingTop + (contentHeight + mTextHeight) / 2,
                mTextPaint);

        // Draw the example drawable on top of the text.
        if (mExampleDrawable != null) {
            mExampleDrawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            mExampleDrawable.draw(canvas);
        }
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
        return textDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        textDimension = exampleDimension;
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
        this.setMeasuredDimension(100, 100);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
