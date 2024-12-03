package com.example.proyecto_finalmov;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RetroalimentacionAct extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retroalimentacion);

        // Obtener el mensaje del intent
        String mensaje = getIntent().getStringExtra("mensaje");

        // Actualizar el TextView con el mensaje
        TextView tvMensaje = findViewById(R.id.tvMensajeRetroalimentacion);
        tvMensaje.setText(mensaje);

        // Configurar el botón de retroceso
        ImageButton flechaButton = findViewById(R.id.flecha);
        flechaButton.setOnClickListener(v -> {
            Intent intent = new Intent(RetroalimentacionAct.this, SistemaSolarAct.class);
            startActivity(intent);
            finish();
        });

        // Configurar el botón de inicio
        ImageButton homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(RetroalimentacionAct.this, MenuPrincipalAct.class);
            startActivity(intent);
            finish();
        });

        // Configurar el botón de nivel
        ImageButton nivelButton = findViewById(R.id.nivel);
        nivelButton.setOnClickListener(v -> {
            Intent intent = new Intent(RetroalimentacionAct.this, SubirNivel.class);
            startActivity(intent);
            finish();
        });
    }
}
