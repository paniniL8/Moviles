package com.example.proyecto_finalmov;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MenuPrincipalAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);

        ImageButton homeButton = findViewById(R.id.homeButton);
        ImageButton usuarioButton = findViewById(R.id.usuarioButton);
        ImageButton cicloAguaButton = findViewById(R.id.cicloagua);

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
    }
}
