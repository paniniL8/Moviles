package com.example.proyecto_finalmov;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class SistemaSolarAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sistema_solar);

        // Inicializar la animación
        ImageView animacionCohete = findViewById(R.id.animacionCohete);
        animacionCohete.setBackgroundResource(R.drawable.animacion);
        AnimationDrawable animacion = (AnimationDrawable) animacionCohete.getBackground();

        animacionCohete.setZ(10f);

        // Iniciar la animación cuando la vista esté lista
        animacionCohete.post(() -> animacion.start());

        // Botón para iniciar las preguntas
        Button startQuizButton = findViewById(R.id.startQuizButton);
        startQuizButton.setOnClickListener(v -> {
            setContentView(R.layout.preg_sistemasolar);

            // Inicializar el cuestionario
            QuizManager quizManager = new QuizManager(SistemaSolarAct.this);
            quizManager.initQuiz();
        });
    }
}
