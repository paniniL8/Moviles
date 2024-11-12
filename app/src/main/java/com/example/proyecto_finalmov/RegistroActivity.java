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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        usernameEditText = findViewById(R.id.usernameEditText);
        ageSpinner = findViewById(R.id.ageSpinner);
        registerButton = findViewById(R.id.registerButton);

        setupAgeSpinner();

        registerButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String age = ageSpinner.getSelectedItem().toString();

            if (!username.isEmpty()) {
                UserPreferences.saveUserData(this, username, age);
                Intent intent = new Intent(RegistroActivity.this, MenuPrincipalAct.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Por favor ingrese todos los datos", Toast.LENGTH_SHORT).show();
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
