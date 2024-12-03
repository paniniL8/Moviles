package com.example.proyecto_finalmov;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserPreferences extends AppCompatActivity {

    private DBSQLite dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_usuario);

        dbHelper = new DBSQLite(this);

        // Referencias a los TextViews
        TextView usernameTextView = findViewById(R.id.username);
        TextView ageTextView = findViewById(R.id.ageTextView);
        TextView levelTextView = findViewById(R.id.levelTextView);

        // Obtener datos del Intent
        String nombre = getIntent().getStringExtra("nombre");
        int edad = getIntent().getIntExtra("edad", -1);

        if (nombre != null && edad != -1) {
            // Mostrar datos del Intent
            mostrarDatosUsuario(usernameTextView, ageTextView, levelTextView, nombre, edad);
        } else {
            // Mostrar datos del usuario actual desde la sesión
            mostrarUsuarioActual(usernameTextView, ageTextView, levelTextView);
        }

        // Botón para regresar al menú principal
        ImageButton homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserPreferences.this, MenuPrincipalAct.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void mostrarDatosUsuario(TextView usernameTextView, TextView ageTextView, TextView levelTextView, String nombre, int edad) {
        usernameTextView.setText("Nombre: " + nombre);
        ageTextView.setText("Edad: " + edad);

        try {
            // Obtener nivel del usuario desde la base de datos
            int nivel = dbHelper.obtenerNivelActual(nombre);
            levelTextView.setText("Nivel: " + nivel);
        } catch (Exception e) {
            Log.e("UserPreferences", "Error al obtener el nivel del usuario", e);
            levelTextView.setText("Nivel: No disponible");
        }
    }

    private void mostrarUsuarioActual(TextView usernameTextView, TextView ageTextView, TextView levelTextView) {
        String[] usuarioActual = dbHelper.obtenerUsuarioActual();

        if (usuarioActual != null) {
            String nombre = usuarioActual[0];
            String edad = usuarioActual[1];

            usernameTextView.setText("Nombre: " + nombre);
            ageTextView.setText("Edad: " + edad);

            try {
                // Obtener nivel del usuario actual
                int nivel = dbHelper.obtenerNivelActual(nombre);
                levelTextView.setText("Nivel: " + nivel);
            } catch (Exception e) {
                Log.e("UserPreferences", "Error al obtener nivel del usuario actual", e);
                levelTextView.setText("Nivel: No disponible");
            }
        } else {
            usernameTextView.setText("Nombre: No disponible");
            ageTextView.setText("Edad: No disponible");
            levelTextView.setText("Nivel: No disponible");
        }
    }
}
