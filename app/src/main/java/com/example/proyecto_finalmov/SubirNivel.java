package com.example.proyecto_finalmov;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SubirNivel extends AppCompatActivity {

    private DBSQLite dbHelper;
    private int correctAnswers = 0;
    private int currentLevel;

    private static final String[][][] preguntasNivel = {
            // Nivel 1
            {
                    {
                            "¿Qué es el ciclo del agua?",
                            "Es el proceso por el cual el agua se mueve de la tierra al aire y vuelve a la tierra",
                            "Es el proceso en el que el agua desaparece",
                            "Es el proceso en el que el agua se convierte en hielo",
                            "Es el proceso por el cual el agua se mueve de la tierra al aire y vuelve a la tierra"
                    },
                    {
                            "¿Cuál es el planeta más cercano al sol?",
                            "Mercurio",
                            "Venus",
                            "Tierra",
                            "Mercurio"
                    },
                    {
                            "¿Qué es un ecosistema?",
                            "Un lugar donde viven los animales y las plantas",
                            "Un lugar donde solo viven los animales",
                            "Un lugar donde las plantas crecen",
                            "Un lugar donde viven los animales y las plantas"
                    },
                    {
                            "¿Qué parte del cuerpo tiene los huesos?",
                            "El esqueleto",
                            "El cerebro",
                            "El estómago",
                            "El esqueleto"
                    },
                    {
                            "¿Qué es la fotosíntesis?",
                            "El proceso por el cual las plantas producen su propio alimento",
                            "El proceso por el cual las plantas se alimentan de los animales",
                            "El proceso por el cual los animales producen energía",
                            "El proceso por el cual las plantas producen su propio alimento"
                    }
            },
            // Nivel 2
            {
                    {
                            "¿Qué causa que el agua se evapore?",
                            "El sol",
                            "El viento",
                            "La lluvia",
                            "El sol"
                    },
                    {
                            "¿De qué color es Marte?",
                            "Rojo",
                            "Azul",
                            "Verde",
                            "Rojo"
                    },
                    {
                            "¿Qué animales viven en un ecosistema?",
                            "Muchos tipos de animales",
                            "Solo un tipo de animal",
                            "Ningún animal",
                            "Muchos tipos de animales"
                    },
                    {
                            "¿Qué órgano nos ayuda a pensar?",
                            "El cerebro",
                            "Los pulmones",
                            "El corazón",
                            "El cerebro"
                    },
                    {
                            "¿Qué hace el corazón en nuestro cuerpo?",
                            "Bombea sangre",
                            "Respira aire",
                            "Nos da energía",
                            "Bombea sangre"
                    }
            },
            // Nivel 3
            {
                    {
                            "¿Cómo sube el agua al cielo durante el ciclo del agua?",
                            "Como vapor",
                            "Como agua",
                            "Como hielo",
                            "Como vapor"
                    },
                    {
                            "¿Cuántos planetas hay en el sistema solar?",
                            "Ocho",
                            "Nueve",
                            "Diez",
                            "Ocho"
                    },
                    {
                            "¿Qué pueden hacer las plantas en un ecosistema?",
                            "Producir oxígeno",
                            "Producir comida",
                            "Producir agua",
                            "Producir oxígeno"
                    },
                    {
                            "¿Qué es el reciclaje?",
                            "Reutilizar materiales",
                            "Destruir materiales",
                            "Producir más basura",
                            "Reutilizar materiales"
                    }
            }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subir_nivel);

        dbHelper = new DBSQLite(this);

        // Cargar el nivel actual desde la base de datos
        currentLevel = obtenerNivelActual(dbHelper.obtenerUsuarioActual()[0]);

        cargarPreguntas(currentLevel);

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(v -> {
            if (checkAnswers()) {
                verificarYAsignarNivel();
            } else {
                Toast.makeText(SubirNivel.this, "Algunas respuestas son incorrectas. Intenta de nuevo.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkAnswers() {
        correctAnswers = 0;

        // Comprobar respuestas
        for (int i = 0; i < 5; i++) {
            correctAnswers += checkSingleAnswer(
                    getResources().getIdentifier("question" + (i + 1) + "Group", "id", getPackageName()),
                    preguntasNivel[currentLevel - 1][i][4]
            );
        }

        return correctAnswers >= 4; // Al menos 4 respuestas correctas para pasar
    }

    private void cargarPreguntas(int nivel) {
        String[][] preguntas = preguntasNivel[nivel - 1];

        for (int i = 0; i < preguntas.length; i++) {
            TextView preguntaTextView = findViewById(getResources().getIdentifier("question" + (i + 1), "id", getPackageName()));
            RadioGroup radioGroup = findViewById(getResources().getIdentifier("question" + (i + 1) + "Group", "id", getPackageName()));

            preguntaTextView.setText(preguntas[i][0]);
            ((RadioButton) radioGroup.getChildAt(0)).setText(preguntas[i][1]);
            ((RadioButton) radioGroup.getChildAt(1)).setText(preguntas[i][2]);
            ((RadioButton) radioGroup.getChildAt(2)).setText(preguntas[i][3]);
        }
    }

    private int checkSingleAnswer(int groupId, String respuestaCorrecta) {
        RadioGroup group = findViewById(groupId);
        int selectedId = group.getCheckedRadioButtonId();

        if (selectedId != -1) {
            RadioButton selectedButton = findViewById(selectedId);
            return selectedButton.getText().toString().equals(respuestaCorrecta) ? 1 : 0;
        }
        return 0;
    }

    private int obtenerNivelActual(String nombre) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT nivel FROM " + DBSQLite.TABLE_USUARIOS + " WHERE nombre = ?";
        Cursor cursor = db.rawQuery(query, new String[]{nombre});

        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex("nivel"));
        }
        return 1; // Nivel inicial por defecto
    }

    private void verificarYAsignarNivel() {
        String[] usuarioActual = dbHelper.obtenerUsuarioActual();
        if (usuarioActual != null) {
            String nombreUsuario = usuarioActual[0];
            currentLevel = obtenerNivelActual(nombreUsuario);

            if (currentLevel < preguntasNivel.length) {
                asignarNivel(currentLevel + 1);
                mostrarMensajeDeFelicidades(nombreUsuario, currentLevel + 1);
            } else {
                Toast.makeText(this, "¡Has alcanzado el nivel máximo!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void asignarNivel(int nuevoNivel) {
        String[] usuarioActual = dbHelper.obtenerUsuarioActual();
        if (usuarioActual != null) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nivel", nuevoNivel);

            db.update(DBSQLite.TABLE_USUARIOS, values, "nombre = ?", new String[]{usuarioActual[0]});
        }
    }

    private void mostrarMensajeDeFelicidades(String nombreUsuario, int nuevoNivel) {
        new AlertDialog.Builder(this)
                .setTitle("¡Felicidades!")
                .setMessage("¡Has subido al nivel " + nuevoNivel + ", " + nombreUsuario + "!")
                .setPositiveButton("OK", (dialog, which) -> {
                    Intent intent = new Intent(SubirNivel.this, MenuPrincipalAct.class);
                    startActivity(intent);
                    finish();
                })
                .show();
    }
}
