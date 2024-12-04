package com.example.proyecto_finalmov;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProgressActivity extends AppCompatActivity {

    private CircularProgresoBar circularProgresoBar;
    private TextView progressPercentage;
    private TextView progressMessage;
    private int progreso = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        circularProgresoBar = findViewById(R.id.CircularProgresoBar);
        progressPercentage = findViewById(R.id.progressPercentage);
        progressMessage = findViewById(R.id.progressMessage);

        // Simular progreso
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progreso < 100) {
                    progreso += 2; // Incrementar en 2%
                    circularProgresoBar.setProgress(progreso);
                    progressPercentage.setText(progreso + "%");

                    if (progreso < 50) {
                        progressMessage.setText("Creando el perfil del usuario...");
                    } else if (progreso < 100) {
                        progressMessage.setText("Finalizando configuración...");
                    }

                    handler.postDelayed(this, 100);
                } else {
                    // Al completar el progreso, redirigir a la actividad principal
                    Intent intent = new Intent(ProgressActivity.this, InicioActivity.class);
                    startActivity(intent);
                    finish(); // Finalizar esta actividad
                }
            }
        }, 100);
    }
}
