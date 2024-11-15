package com.example.proyecto_finalmov;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class RetroalimentacionAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retroalimentacion); // Asume que el XML se llama pantalla_retroalimentacion

        // Configurar el botón homeButton
        ImageButton homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(RetroalimentacionAct.this, MenuPrincipalAct.class);
            startActivity(intent);
            finish(); // Finaliza la actividad de retroalimentación
        });
    }
}
