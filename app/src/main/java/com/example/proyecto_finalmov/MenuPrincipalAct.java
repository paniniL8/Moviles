package com.example.proyecto_finalmov;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MenuPrincipalAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);

        ImageButton homeButton = findViewById(R.id.homeButton);
        ImageButton usuarioButton = findViewById(R.id.usuarioButton);
        ImageButton cicloAguaButton = findViewById(R.id.cicloagua);
        ImageButton ecosistemaButton = findViewById(R.id.ecosistemas);
        ImageButton sistemasolButton = findViewById(R.id.sistemaSol);
        ImageButton cuerpohumButton = findViewById(R.id.cuerpoHum);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuPrincipalAct.this, MenuPrincipalAct.class);
            startActivity(intent);
        });

        usuarioButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuPrincipalAct.this, UserPreferences.class);
            startActivity(intent);
        });

        cicloAguaButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuPrincipalAct.this, CicloAguaAct.class);
            startActivity(intent);
        });

        ecosistemaButton.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(MenuPrincipalAct.this, EcosistemaAct.class);
                startActivity(intent);
            } catch (Exception e) {
                Log.e("MenuPrincipalAct", "Error al abrir EcosistemaAct", e);
                Toast.makeText(this, "No se puede abrir EcosistemaAct. Verifica el diseño.", Toast.LENGTH_LONG).show();
            }
        });

        sistemasolButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuPrincipalAct.this, SistemaSolarAct.class);
            startActivity(intent);
        });

        cuerpohumButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuPrincipalAct.this, CuerpoHumanoAct.class);
            startActivity(intent);
        });

    }
}
