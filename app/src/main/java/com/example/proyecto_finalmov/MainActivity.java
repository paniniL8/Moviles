
package com.example.proyecto_finalmov;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_AGE = "age";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.pag_inicio);

        // Aplicar el degradado como fondo
        View rootView = findViewById(android.R.id.content);
        rootView.setBackground(ContextCompat.getDrawable(this, R.drawable.degradado));

        // Configuración de los insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pagInicio), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configuración del botón "INICIAR!!" para ir a la pantalla de registro
        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(v -> openRegistrationPage());
    }

    private void openRegistrationPage() {
        setContentView(R.layout.registro);

        // Configurar el botón de registro
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        EditText usernameEditText = findViewById(R.id.usernameEditText);
        Spinner ageSpinner = findViewById(R.id.ageSpinner);

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

        // Ir a la página del menú principal
        openMenuPage();
    }

    private void openMenuPage() {
        setContentView(R.layout.menu_principal);

        // Configurar el botón de inicio en el menú para regresar al menú principal
        findViewById(R.id.homeButton).setOnClickListener(v -> openMenuPage());

        // Configurar el botón de usuario para abrir la pantalla de usuario
        findViewById(R.id.usuarioButton).setOnClickListener(v -> openUserPreferencesPage());
    }

    private void openUserPreferencesPage() {
        // Abre la actividad UserPreferences
        Intent intent = new Intent(MainActivity.this, UserPreferences.class);
        startActivity(intent);
    }
}
