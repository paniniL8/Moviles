package com.example.proyecto_finalmov;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Vector;

public class UserPreferences extends AppCompatActivity {

    private DBSQLite dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_usuario); // Cambia a dialog_usuario directamente

        dbHelper = new DBSQLite(this);

        // Obtener referencias a los TextView
        TextView usernameTextView = findViewById(R.id.username);
        TextView ageTextView = findViewById(R.id.ageTextView);

        // Mostrar los usuarios almacenados en la base de datos
        mostrarUsuarioActual(usernameTextView, ageTextView);

        // Configurar el botón para regresar al menú principal
        ImageButton homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> finish());
    }

    private void mostrarUsuarioActual(TextView usernameTextView, TextView ageTextView) {
        // Obtener el último usuario registrado
        String ultimoUsuario = dbHelper.obtenerUltimoUsuario();

        // Extraer nombre y edad del formato de texto
        if (!ultimoUsuario.isEmpty()) {
            String[] partes = ultimoUsuario.split(", ");
            String nombre = partes[0].replace("Nombre: ", "");
            String edad = partes[1].replace("Edad: ", "");

            // Mostrar en los TextView
            usernameTextView.setText("Nombre: " + nombre);
            ageTextView.setText("Edad: " + edad);
        } else {
            usernameTextView.setText("Nombre: N/A");
            ageTextView.setText("Edad: N/A");
        }
    }

}
