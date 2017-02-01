package com.pythoncat.circleprogressbarview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.IntRange;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created  on 2017/1/21.
 */

public class CircleProgressBar extends View {

    private final boolean useCenter;
    private int progress;

    private final int borderColor;
    private final float borderWidth;
    private final boolean textVisible;
    private float progressWidth;
    private int progressColor;
    private boolean borderVisible;
    private final int textColor;
    private float textSize;
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private int mRadius;
    private Rect bounds;
    private RectF oval;

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleProgressBar, 0, 0);
        // progress
        progress = array.getInt(R.styleable.CircleProgressBar_progress, 35);
        progressWidth = array.getDimension(R.styleable.CircleProgressBar_progressWidth, 20);
        Log.d("A", "progressWidth = " + progressWidth);
        progressColor = array.getColor(R.styleable.CircleProgressBar_progressColor, Color.BLUE);
        // border
        borderColor = array.getColor(R.styleable.CircleProgressBar_borderColor, Color.GRAY);
        borderWidth = array.getDimension(R.styleable.CircleProgressBar_bordersWidth, dp2Px(context, 0.3f));
        Log.d("A", "borderWidth = " + borderWidth);
        borderVisible = array.getBoolean(R.styleable.CircleProgressBar_borderVisible, true);
        // text
        // textSize --> todo
        textSize = array.getDimension(R.styleable.CircleProgressBar_textSize, dp2Px(context, 12));
        Log.d("A", "textSize = " + textSize);
        textColor = array.getColor(R.styleable.CircleProgressBar_textColor, Color.RED);
        textVisible = array.getBoolean(R.styleable.CircleProgressBar_textVisible, true);
        useCenter = array.getBoolean(R.styleable.CircleProgressBar_useCenter, false);
        array.recycle();
    }

    public static int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        init(w, h);
    }

    private void init(int w, int h) {
        this.mWidth = w;
        this.mHeight = w;
        this.mRadius = Math.min(w, h) / 2;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        bounds = new Rect();

        oval = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width;
        int height;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = widthSize / 2;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = heightSize / 2;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 1. drawRing
        drawRing(canvas);
        // 2. drawText
        drawText(canvas);
        // 3.drawProgress
        drawProgress(canvas);
    }

    private void drawProgress(Canvas canvas) {
        mPaint.setColor(progressColor);
        if (useCenter) {
            oval.set(0, 0, mRadius * 2, mRadius * 2);
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mPaint.setStrokeWidth(0);
        } else {
            float proRadius = mRadius - borderWidth - progressWidth / 2;
            oval.set(mRadius - proRadius, mRadius - proRadius, mRadius + proRadius, mRadius + proRadius);
            mPaint.setStrokeWidth(progressWidth);
            mPaint.setStyle(Paint.Style.STROKE);
        }
        mPaint.setAntiAlias(false);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(oval, -90, progress * 1f / 100 * 360, useCenter, mPaint);
    }

    private void drawText(Canvas canvas) {
        if (textVisible) {
            mPaint.setStrokeWidth(0);
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mPaint.setColor(textColor);
            mPaint.setTextSize(textSize); // todo ->
            mPaint.setTextAlign(Paint.Align.CENTER); // 文字对齐方式为文字的中间(底部中点)
            String text;
            text = "" + progress + "%";
            mPaint.getTextBounds(text, 0, text.length(), bounds);
            Log.d("A", "" + bounds);
            // 让文字居中 ("要显示的文字",view横轴中点，view纵轴中点+文字高度/2,画笔)
            canvas.drawText(text, mRadius, mRadius + bounds.height() / 2, mPaint);
        }
    }

    private void drawRing(Canvas canvas) {
        if (borderVisible) {
            mPaint.setColor(borderColor);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(borderWidth);
            canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mPaint); // outer circle
            canvas.drawCircle(mWidth / 2, mHeight / 2,
                    mRadius - progressWidth - borderWidth, mPaint); // inner circle
        }
    }

    public synchronized void setProgress(@IntRange(from = 0, to = 100) int progress) {
        this.progress = progress;
        postInvalidate();
    }
}