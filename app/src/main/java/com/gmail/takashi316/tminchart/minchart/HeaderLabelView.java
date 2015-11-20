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
    private static final int DEFAULT_INDEX_ORIGIN = 0;
    private int indexOffset = 1;
    private static final int DEFAULT_INDEX_OFFSET = 0;
    private boolean isAlphabetical = false;
    private static final boolean DEFAULT_IS_ALPHABETICAL = false;
    private String text;
    private static final int DEFAULT_COLOR = Color.BLACK;
    private int color = DEFAULT_COLOR; // TODO: use a default from R.color...
    private static final int DEFAULT_TEXT_DIMENSION = 100;
    private float textDimension = DEFAULT_TEXT_DIMENSION; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private TextPaint paint;
    private float textWidth;
    private float textHeight;
    private float textAscent;

    public HeaderLabelView(Context context) {
        super(context);
        this.init();
    }

    public HeaderLabelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.applyAttributeSet(attrs, 0);
        this.init();
    }

    public HeaderLabelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.applyAttributeSet(attrs, defStyle);
        this.init();
    }

    public HeaderLabelView(Context context, String text) {
        super(context);
        this.text = text;
        this.init();
    }

    public HeaderLabelView(Context context, int origin, int offset, boolean alphabetical) {
        super(context);
        this.indexOrigin = origin;
        this.indexOffset = offset;
        this.isAlphabetical = alphabetical;
        this.init();
    }

    private void applyAttributeSet(AttributeSet attrs, int defStyle) {
        final TypedArray a = this.getContext().obtainStyledAttributes(
                attrs, R.styleable.HeaderLabelView, defStyle, 0);
        this.indexOffset = a.getInt(R.styleable.HeaderLabelView_indexOffset, this.DEFAULT_INDEX_OFFSET);
        this.indexOrigin = a.getInt(R.styleable.HeaderLabelView_indexOrigin, this.DEFAULT_INDEX_ORIGIN);
        this.isAlphabetical = a.getBoolean(R.styleable.HeaderLabelView_isAlphabetical, this.DEFAULT_IS_ALPHABETICAL);
        this.text = a.getString(R.styleable.HeaderLabelView_text);
        this.color = a.getColor(R.styleable.HeaderLabelView_textColor, this.color);
        this.textDimension = a.getDimension(R.styleable.HeaderLabelView_textDimension, this.DEFAULT_TEXT_DIMENSION);

        if (a.hasValue(R.styleable.HeaderLabelView_backgroundDrawable)) {
            this.mExampleDrawable = a.getDrawable(
                    R.styleable.HeaderLabelView_backgroundDrawable);
            this.mExampleDrawable.setCallback(this);
        }//if
        a.recycle();
        //this.init();
    }//applyAttributeSet

    private void init() {
        this.paint = new TextPaint();
        this.paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        this.paint.setTextAlign(Paint.Align.LEFT);

        if (this.text == null) {
            if (this.isAlphabetical) {
                this.text = new String(new byte[]{(byte) (this.indexOrigin + this.indexOffset + 64)});
            } else {
                this.text = Integer.toString(this.indexOrigin + this.indexOffset);
            }//if
        }//if
        this.paint.setTextSize(this.textDimension);
        this.paint.setColor(this.color);
        this.textWidth = this.paint.measureText(this.text);
        this.textHeight = this.paint.descent() - this.paint.ascent();
        this.textAscent = this.paint.ascent();
    }//init

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        final int paddingLeft = this.getPaddingLeft();
        final int paddingTop = this.getPaddingTop();
        final int paddingRight = this.getPaddingRight();
        final int paddingBottom = this.getPaddingBottom();

        final int contentWidth = this.getWidth() - paddingLeft - paddingRight;
        final int contentHeight = this.getHeight() - paddingTop - paddingBottom;

        // Draw the text.
        canvas.drawText(this.text,
                paddingLeft + (contentWidth - this.textWidth) / 2,
                paddingTop + (contentHeight - this.textHeight) / 2 - this.textAscent,
                this.paint);

        // Draw the example drawable on top of the text.
        if (this.mExampleDrawable != null) {
            this.mExampleDrawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            this.mExampleDrawable.draw(canvas);
        }
    }

    public void setText(String text) {
        this.text = text;
        this.init();
    }

    public void setExampleString(String text) {
        this.text = text;
        this.init();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return this.color;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        this.color = exampleColor;
        this.init();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return this.textDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        this.textDimension = exampleDimension;
        this.init();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return this.mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        this.mExampleDrawable = exampleDrawable;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.setMeasuredDimension((int) this.textWidth + this.getPaddingLeft() + this.getPaddingRight(), (int) this.textHeight + this.getPaddingBottom() + this.getPaddingTop());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
