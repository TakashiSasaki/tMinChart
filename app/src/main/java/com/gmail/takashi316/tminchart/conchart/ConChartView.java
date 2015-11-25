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

class ConChartViewParams {
    public double widthInch;
    public String string;
    public double intensity;
    public int color;
    public float pixels;
}

public class ConChartView extends View {
    private Context context;
    private ConChartViewParams conChartViewParams;
    private TextPaint mTextPaint;
    static private float xdpi, ydpi;
    private float xpixels, ypixels;
    private boolean touched = false;
    //private String string;
    private int stroke;
    private Typeface typeface;

    static final private int DEFAULT_WIDTH_INCH = 1;
    static final private float DEFAULT_INTENSITY = 1;
    static final private Typeface DEFAULT_TYPEFACE = Typeface.MONOSPACE;
    static final private int DEFAULT_STROKE = 17;

    static final private Random random = new Random();
    static DisplayMetrics displayMetrics;

    public ConChartView(Context context, double width_inch, double intensity, final ArrayList<ConChartView> conChartViews, String string, int n_stroke, Typeface typeface) {
        super(context);
        this.context = context;
        this.conChartViewParams = new ConChartViewParams();
        this.conChartViewParams.widthInch = width_inch;
        this.typeface = typeface;
        this.stroke = n_stroke;
        this.conChartViewParams.intensity = intensity;
        this.conChartViewParams.string = string;

        this.mTextPaint = new TextPaint();

        // Update TextPaint and text measurements from attributes
        this.invalidateTextPaintAndMeasurements();

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean previously_touched = ConChartView.this.touched;
                if (conChartViews != null) {
                    for (ConChartView conChartView : conChartViews) {
                        conChartView.touched = false;
                    }//for
                }//if
                ConChartView.this.touched = !previously_touched;
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
                            Log.e(this.getClass().getSimpleName(), e.getMessage());
                        }
                    }//run
                });//post
            }//onClick
        });//setOnClickListener
    }// custom constructor

    public ConChartView(Context context) {
        super(context);
        this.context = context;
    }//Seventeen

    public ConChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.obtainStyledAttributes(context, attrs, 0);
        this.invalidateTextPaintAndMeasurements();
    }//Seventeen

    public ConChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        this.obtainStyledAttributes(context, attrs, defStyle);
        this.invalidateTextPaintAndMeasurements();
    }//Seventeen

    private void obtainStyledAttributes(Context context, AttributeSet attrs, int defStyle) {
        this.conChartViewParams.widthInch = context.obtainStyledAttributes(attrs, R.styleable.ConChartView, defStyle, 0).getInt(R.styleable.ConChartView_widthInch, DEFAULT_WIDTH_INCH);
        this.conChartViewParams.intensity = context.obtainStyledAttributes(attrs, R.styleable.ConChartView, defStyle, 0).getFloat(R.styleable.ConChartView_intensity, DEFAULT_INTENSITY);
        this.conChartViewParams.string = context.obtainStyledAttributes(attrs, R.styleable.ConChartView, defStyle, 0).getString(R.styleable.ConChartView_string);
        this.stroke = context.obtainStyledAttributes(attrs, R.styleable.ConChartView, defStyle, 0).getInt(R.styleable.ConChartView_nStroke, 17);
    }

    private void invalidateTextPaintAndMeasurements() {
        if (displayMetrics == null) {
            final WindowManager window_manager = (WindowManager) this.context.getSystemService(Context.WINDOW_SERVICE);
            final Display display = window_manager.getDefaultDisplay();
            displayMetrics = new DisplayMetrics();
            display.getMetrics(displayMetrics);
        }
        xdpi = displayMetrics.xdpi;
        ydpi = displayMetrics.ydpi;
        this.xpixels = (float) (xdpi * this.conChartViewParams.widthInch);
        this.ypixels = (float) (ydpi * this.conChartViewParams.widthInch);
        this.conChartViewParams.pixels = Math.max(this.xpixels, this.ypixels);
        this.conChartViewParams.color = Color.rgb((int) Math.max(this.conChartViewParams.intensity, 0), (int) Math.max(this.conChartViewParams.intensity, 0), (int) Math.max(this.conChartViewParams.intensity, 0));
        this.mTextPaint.setColor(this.conChartViewParams.color);
        this.mTextPaint.setTextSize(this.conChartViewParams.pixels);
        this.mTextPaint.setTextAlign(Paint.Align.LEFT);
        this.mTextPaint.setTypeface(this.typeface);
        if (this.conChartViewParams.string == null) {
            this.conChartViewParams.string = this.getTconString(this.stroke);
        }
        //mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        //Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
    }//invalidateTextPaintAndMeasurements

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int canvas_width = canvas.getWidth();
        final int canvas_height = canvas.getHeight();
        final Paint.FontMetrics font_metrics = this.mTextPaint.getFontMetrics();
        final float width_margin = (canvas_width - this.conChartViewParams.pixels) / 2;
        final float height_margin = (canvas_height - this.conChartViewParams.pixels) / 2;
        canvas.drawText(this.conChartViewParams.string, width_margin, canvas_height - font_metrics.bottom - (height_margin / 2), this.mTextPaint);
        if (this.touched) {
            //this.setBackgroundColor(Color.RED);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                final Drawable frame = this.getResources().getDrawable(R.drawable.frame);
                this.setBackgroundDrawable(frame);
            } else {
                final Drawable frame = this.getResources().getDrawable(R.drawable.frame);
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
        this.setMeasuredDimension(MeasureSpec.makeMeasureSpec(this.getSuggestedMinimumWidth(), width_mode),
                MeasureSpec.makeMeasureSpec(this.getSuggestedMinimumHeight(), height_mode));
    }//onMeasure

    private String getTconString(int n_stroke) {
        String s = (new Character(DailyChineseCharacter.getInstance().getRandomCharOfNStrokes(n_stroke))).toString();
        return s;
        //final int r = random.nextInt(TCON_STRINGS.length);
        //return TCON_STRINGS[r];
    }//getTconString

    public Pair<Float, Integer> getResult() {
        return new Pair<Float, Integer>(this.conChartViewParams.pixels, (int) this.conChartViewParams.intensity);
    }//getResult

    public boolean isTouched() {
        return this.touched;
    }

    @Override
    public String toString() {
        return "(" + Float.toString(this.conChartViewParams.pixels) + "," + Integer.toString((int) this.conChartViewParams.intensity) + ")";
    }//toString
}//Seventeen
