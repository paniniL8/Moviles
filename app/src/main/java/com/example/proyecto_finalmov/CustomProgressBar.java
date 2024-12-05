package com.example.proyecto_finalmov;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.ProgressBar;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class CustomProgressBar extends ProgressBar {

    public CustomProgressBar(Context context) {
        super(context);
        init();
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setMax(100);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    public void updateProgress(int progress) {
        setProgress(progress);
        invalidate(); // Asegura que se redibuje la barra
    }

}
