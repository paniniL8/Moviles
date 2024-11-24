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

        // Referencias a los TextView
        TextView usernameTextView = findViewById(R.id.username);
        TextView ageTextView = findViewById(R.id.ageTextView);

        // Obtener los datos del Intent
        String nombre = getIntent().getStringExtra("nombre");
        int edad = getIntent().getIntExtra("edad", -1);

        Log.d("UserPreferences", "Datos recibidos del Intent: nombre=" + nombre + ", edad=" + edad); // DEPURACIÓN

        if (nombre != null && edad != -1) {
            // Mostrar datos del Intent (usuario recién registrado o iniciado sesión)
            usernameTextView.setText("Nombre: " + nombre);
            ageTextView.setText("Edad: " + edad);

            Log.d("UserPreferences", "Datos mostrados desde el Intent: Nombre=" + nombre + ", Edad=" + edad);
        } else {
            // Si no hay datos en el Intent, cargar desde la tabla sesion_actual
            mostrarUsuarioActual(usernameTextView, ageTextView);
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

    private void mostrarUsuarioActual(TextView usernameTextView, TextView ageTextView) {
        String[] usuarioActual = dbHelper.obtenerUsuarioActual();

        // DEPURACIÓN
        if (usuarioActual != null) {
            Log.d("UserPreferences", "Usuario actual cargado: " + usuarioActual[0] + ", " + usuarioActual[1]);
        } else {
            Log.d("UserPreferences", "No se encontró un usuario actual en la tabla sesion_actual.");
        }

        if (usuarioActual != null) {
            String nombre = usuarioActual[0];
            String edad = usuarioActual[1];

            usernameTextView.setText("Nombre: " + nombre);
            ageTextView.setText("Edad: " + edad);
        } else {
            usernameTextView.setText("Nombre: No disponible");
            ageTextView.setText("Edad: No disponible");
        }
    }



}