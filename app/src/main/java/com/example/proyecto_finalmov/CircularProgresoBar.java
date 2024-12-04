package com.example.proyecto_finalmov;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CircularProgresoBar extends View {

    private Paint paint;
    private int progress = 0;

    public CircularProgresoBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(0xFF3F51B5); // Color azul
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(15f);
        paint.setAntiAlias(true);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate(); // Redibuja la vista
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width, height) / 2 - 20;

        canvas.drawCircle(width / 2, height / 2, radius, paint);

        Paint arcPaint = new Paint(paint);
        arcPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawArc(
                width / 2 - radius,
                height / 2 - radius,
                width / 2 + radius,
                height / 2 + radius,
                -90,
                (progress * 360) / 100,
                true,
                arcPaint
        );
    }
}
