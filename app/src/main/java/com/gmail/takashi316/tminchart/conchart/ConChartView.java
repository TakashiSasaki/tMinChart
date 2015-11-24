package com.gmail.takashi316.tminchart.conchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
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

import com.gmail.takashi316.tminchart.DailyChineseCharacter;
import com.gmail.takashi316.tminchart.R;

import java.util.ArrayList;
import java.util.Random;


public class ConChartView extends View {

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
    private String string;
    private Typeface typeface;

    static final private Random random = new Random();
    static DisplayMetrics displayMetrics;

    public ConChartView(Context context, double width_inch, double intensity, final ArrayList<ConChartView> conChartViews, String string, int n_stroke, Typeface typeface) {
        super(context);
        this.typeface = typeface;

        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();

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
        this.color = Color.rgb((int) Math.max(intensity, 0), (int) Math.max(intensity, 0), (int) Math.max(intensity, 0));
        this.mTextPaint.setColor(color);
        this.mTextPaint.setTextSize(pixels);
        this.mTextPaint.setTypeface(this.typeface);
        if (string == null) {
            this.string = getTconString(n_stroke);
        } else {
            this.string = string;
        }//if

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean previously_touched = touched;
                if (conChartViews != null) {
                    for (ConChartView conChartView : conChartViews) {
                        conChartView.touched = false;
                    }//for
                }//if
                touched = !previously_touched;
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (ConChartView conChartView : conChartViews) {
                            conChartView.invalidate();
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

    public ConChartView(Context context) {
        this(context, null);
    }//Seventeen

    public ConChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }//Seventeen

    public ConChartView(Context context, AttributeSet attrs, int defStyle) {
        this(context, context.obtainStyledAttributes(attrs, R.styleable.ConChartView, defStyle, 0).getInt(R.styleable.ConChartView_widthInch, 1),
                context.obtainStyledAttributes(attrs, R.styleable.ConChartView, defStyle, 0).getFloat(R.styleable.ConChartView_intensity, 1),
                null,
                context.obtainStyledAttributes(attrs, R.styleable.ConChartView, defStyle, 0).getString(R.styleable.ConChartView_string),
                context.obtainStyledAttributes(attrs, R.styleable.ConChartView, defStyle, 0).getInt(R.styleable.ConChartView_nStroke, 17), Typeface.MONOSPACE);
    }//Seventeen

    private void invalidateTextPaintAndMeasurements() {
        try {
            mTextPaint.setColor(this.color);
            mTextWidth = mTextPaint.measureText(this.string);

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
        canvas.drawText(this.string, width_margin, canvas_height - font_metrics.bottom - (height_margin / 2), mTextPaint);
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


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width_mode = MeasureSpec.getMode(widthMeasureSpec);
        int width_size = MeasureSpec.getSize(widthMeasureSpec);
        int height_mode = MeasureSpec.getMode(heightMeasureSpec);
        int height_size = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(getSuggestedMinimumWidth(), width_mode),
                MeasureSpec.makeMeasureSpec(getSuggestedMinimumHeight(), height_mode));
    }//onMeasure

    private String getTconString(int n_stroke) {
        String s = (new Character(DailyChineseCharacter.getInstance().getRandomCharOfNStrokes(n_stroke))).toString();
        return s;
        //final int r = random.nextInt(TCON_STRINGS.length);
        //return TCON_STRINGS[r];
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