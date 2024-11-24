package com.example.proyecto_finalmov;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class InicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pag_inicio);

        // Botón para redirigir al registro de usuarios
        Button RegButton = findViewById(R.id.RegButton);
        RegButton.setOnClickListener(v -> {
            Intent intent = new Intent(InicioActivity.this, RegistroActivity.class);
            startActivity(intent);
        });

        // Botón para iniciar sesión
        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(v -> {
            // Redirigir a MainActivity, que maneja el cuadro de diálogo
            Intent intent = new Intent(InicioActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
