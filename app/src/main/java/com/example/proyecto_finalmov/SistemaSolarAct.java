package com.example.proyecto_finalmov;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class SistemaSolarAct extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.sistema_solar); // Carga el diseño de la página ciclo_agua

            // Configurar el botón para regresar al menú principal
            ImageButton homeButton = findViewById(R.id.homeButton); // Busca el botón en el layout
            homeButton.setOnClickListener(v -> finish()); // Agrega el listener para terminar la actividad
        }

}