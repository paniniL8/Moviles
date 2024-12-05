package com.example.proyecto_finalmov;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CicloAguaAct extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private ImageView raindrop;
    private TextView stageTextView;
    private View backgroundView;

    // Water cycle stages
    private int currentStage = 0;
    private final String[] stages = {"Evaporación", "Condensación", "Precipitación"};
    private final String[] stageDescriptions = {
            "<b>Evaporación:</b> El agua se calienta y <font color='#FF5722'>se convierte en vapor</font>, subiendo desde la superficie terrestre.",
            "<b>Condensación:</b> El vapor de agua <font color='#3F51B5'>se enfría</font> y forma nubes en la atmósfera.",
            "<b>Precipitación:</b> Las gotas de agua en las nubes <font color='#009688'>caen como lluvia</font>."
    };


    // Screen dimensions
    private int screenWidth;
    private int screenHeight;

    // Movement sensitivity
    private static final float MOVE_SENSITIVITY = 5.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ciclo_agua);

        // Initialize views
        raindrop = findViewById(R.id.raindrop);
        stageTextView = findViewById(R.id.textView);
        backgroundView = findViewById(android.R.id.content);

        // Get screen dimensions
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        // Initialize sensor manager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Initialize first stage
        updateStageDisplay();
        updateBackgroundColor();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register sensor listener with game mode sensitivity
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            float xMovement = -event.values[0] * MOVE_SENSITIVITY;
            float yMovement = event.values[1] * MOVE_SENSITIVITY;


            raindrop.setX(Math.max(0, Math.min(screenWidth - raindrop.getWidth(),
                raindrop.getX() + xMovement)));
            raindrop.setY(Math.max(0, Math.min(screenHeight - raindrop.getHeight(),
                raindrop.getY() + yMovement)));


            checkStageProgression();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

   private void checkStageProgression() {
           // Dividir la pantalla en tercios precisos
           float stageWidth = screenWidth / 3f;

           // Calcular la posición actual de la gota
           float dropPosition = raindrop.getX();

           // Lógica de progresión más estricta
           if (dropPosition < stageWidth && currentStage != 0) {
               // Volver a Evaporación
               currentStage = 0;
               updateStageDisplay();
               updateBackgroundColor();
               Toast.makeText(this, "Regresando a " + stages[currentStage], Toast.LENGTH_SHORT).show();
           } else if (dropPosition >= stageWidth && dropPosition < stageWidth * 2 && currentStage != 1) {
               // Cambiar a Condensación
               currentStage = 1;
               updateStageDisplay();
               updateBackgroundColor();
               Toast.makeText(this, stages[currentStage], Toast.LENGTH_SHORT).show();
           } else if (dropPosition >= stageWidth * 2 && currentStage != 2) {
               // Cambiar a Precipitación
               currentStage = 2;
               updateStageDisplay();
               updateBackgroundColor();
               Toast.makeText(this, stages[currentStage], Toast.LENGTH_SHORT).show();
           }

           // Reiniciar ciclo si se completa
           if (dropPosition >= screenWidth * 0.9) {
               raindrop.setX(0);
               currentStage = 0;
               updateStageDisplay();
               updateBackgroundColor();
               Toast.makeText(this, "Ciclo del Agua Reiniciado", Toast.LENGTH_SHORT).show();
           }
       }

    private void updateStageDisplay() {
        if (currentStage < stages.length) {
            stageTextView.setText(android.text.Html.fromHtml(stageDescriptions[currentStage]));
        }
    }


    private void updateBackgroundColor() {
        int backgroundColor;
        switch (currentStage) {
            case 0: // Evaporation
                backgroundColor = Color.parseColor("#87CEEB");
                break;
            case 1: // Condensation
                backgroundColor = Color.parseColor("#B0C4DE");
                break;
            case 2: // Precipitation
                backgroundColor = Color.parseColor("#4682B4");
                break;
            default:
                backgroundColor = Color.WHITE;
        }
        backgroundView.setBackgroundColor(backgroundColor);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            raindrop.setX(event.getX() - raindrop.getWidth() / 2f);
            raindrop.setY(event.getY() - raindrop.getHeight() / 2f);
            return true;
        }
        return super.onTouchEvent(event);
    }
}