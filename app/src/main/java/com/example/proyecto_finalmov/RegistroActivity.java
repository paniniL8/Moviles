package com.example.proyecto_finalmov;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistroActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_AGE = "age";

    private EditText usernameEditText;
    private Spinner ageSpinner;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        // Inicializar vistas
        usernameEditText = findViewById(R.id.usernameEditText);
        ageSpinner = findViewById(R.id.ageSpinner);
        registerButton = findViewById(R.id.registerButton);

        // Configurar el Spinner de edad
        setupAgeSpinner();

        // Configurar el listener del botón de registro
        registerButton.setOnClickListener(v -> {
            // Capturar los datos ingresados por el usuario
            String username = usernameEditText.getText().toString().trim();
            String age = ageSpinner.getSelectedItem().toString();

            if (username.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese un nombre de usuario", Toast.LENGTH_SHORT).show();
                return;
            }

            // Guardar los datos en SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_USERNAME, username);
            editor.putString(KEY_AGE, age);
            editor.apply();

            // Navegar al menú principal
            Intent intent = new Intent(RegistroActivity.this, MenuPrincipalAct.class);
            startActivity(intent);

            // Finalizar esta actividad para evitar que el usuario regrese al registro
            finish();
        });
    }

    private void setupAgeSpinner() {
        // Configurar el adaptador para el spinner de edad
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.age_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(adapter);
    }
}
