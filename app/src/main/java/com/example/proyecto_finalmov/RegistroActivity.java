package com.example.proyecto_finalmov;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistroActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private Spinner ageSpinner;
    private Button registerButton;

    private DBSQLite dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        dbHelper = new DBSQLite(this);

        usernameEditText = findViewById(R.id.usernameEditText);
        ageSpinner = findViewById(R.id.ageSpinner);
        registerButton = findViewById(R.id.registerButton);

        setupAgeSpinner();

        registerButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String ageString = ageSpinner.getSelectedItem().toString();

            if (username.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese un nombre de usuario", Toast.LENGTH_SHORT).show();
                return;
            }

            int age = Integer.parseInt(ageString);

            // Verificar si el nombre de usuario ya existe
            if (dbHelper.existeUsuario(username)) {
                Toast.makeText(this, "El nombre de usuario ya está registrado. Intente con otro.", Toast.LENGTH_LONG).show();
                return;
            }

            // Guardar en la base de datos
            long userId = dbHelper.guardarUsuario(username, age);

            if (userId != -1) {
                // Registrar al usuario actual en la tabla sesion_actual
                dbHelper.establecerUsuarioActual(userId, username, age);

                // Mostrar mensaje de éxito
                Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_LONG).show();

                // Redirigir a la página de inicio (InicioActivity)
                Intent intent = new Intent(RegistroActivity.this, InicioActivity.class);
                startActivity(intent);
                finish(); // Finalizar la actividad actual
            } else {
                Toast.makeText(this, "Error al registrar el usuario. Intente nuevamente.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setupAgeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.age_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(adapter);
    }
}
