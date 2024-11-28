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
        // Personaliza la barra de progreso aquÃ­ si es necesario
        setMax(100); // Ejemplo: Establece un mÃ¡ximo de 100 por defecto
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Puedes dibujar algo personalizado aquÃ­ si lo necesitas
    }

    public void updateProgress(int progress) {
        setProgress(progress);
        invalidate(); // Asegura que se redibuje la barra
    }

}
