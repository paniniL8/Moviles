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

            dbHelper.guardarUsuario(username, age);
            Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(RegistroActivity.this, MenuPrincipalAct.class);
            startActivity(intent);
            finish();
        });
    }

    private void setupAgeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.age_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(adapter);
    }
}
