package com.example.proyecto_finalmov;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DBSQLite dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pag_inicio);

        dbHelper = new DBSQLite(this);

        // Botón para registrar un nuevo usuario
        Button RegButton = findViewById(R.id.RegButton);
        RegButton.setOnClickListener(v -> {
            // Redirigir a la página de registro
            Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
            startActivity(intent);
        });

        // Botón para iniciar sesión
        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(v -> openLoginDialog());
    }

    // Método para abrir el cuadro de diálogo de inicio de sesión
    private void openLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Iniciar Sesión");

        // Agregar un campo de texto para el nombre del usuario
        final EditText input = new EditText(this);
        input.setHint("Ingrese su nombre de usuario");
        builder.setView(input);

        // Configurar botones
        builder.setPositiveButton("Aceptar", (dialog, which) -> {
            String username = input.getText().toString().trim();
            if (!username.isEmpty()) {
                verificarUsuario(username);
            } else {
                Toast.makeText(this, "El nombre de usuario no puede estar vacío", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        // Mostrar el cuadro de diálogo
        builder.show();
    }

    // Método para verificar si el usuario existe en la base de datos
    private void verificarUsuario(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Consultar si el usuario existe en la base de datos
        Cursor cursor = db.rawQuery("SELECT id, nombre, edad FROM usuarios WHERE nombre = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            long userId = cursor.getLong(0);
            String nombre = cursor.getString(1);
            int edad = cursor.getInt(2);

            // Registrar al usuario como actual
            dbHelper.establecerUsuarioActual(userId, nombre, edad);

            // Abrir el menú principal
            abrirMenuPrincipal();
        } else {
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        db.close();
    }

    // Método para abrir el menú principal
    private void abrirMenuPrincipal() {
        Intent intent = new Intent(MainActivity.this, MenuPrincipalAct.class);
        startActivity(intent);
        finish();
    }
}
