package com.example.proyecto_finalmov;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserPreferences extends AppCompatActivity {

    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_AGE = "age";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_usuario); // Cambia a dialog_usuario directamente

        // Obtener referencias a los TextView
        TextView usernameTextView = findViewById(R.id.username);
        TextView ageTextView = findViewById(R.id.ageTextView);

        // Cargar datos desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(KEY_USERNAME, "Usuario no registrado");
        String age = sharedPreferences.getString(KEY_AGE, "Edad no especificada");

        // Asignar los datos a los TextView
        usernameTextView.setText(username);
        ageTextView.setText("Edad: " + age);

        // Configurar el botón para regresar al menú principal
        ImageButton homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> finish());
    }
}
