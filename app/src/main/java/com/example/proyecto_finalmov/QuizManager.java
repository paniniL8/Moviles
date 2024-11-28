package com.example.proyecto_finalmov;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class QuizManager {

    private final Context context;
    private TextView tvPregunta, tvPuntos, tvTiempo, tvIntentos;
    private Button btnOpcion1, btnOpcion2, btnOpcion3, btnOpcion4, btnRetroalimentacion;
    private List<PreguntasSisSolar> preguntas;
    private int preguntaActual = 0;
    private int puntos = 0;
    private int intentosRestantes = 2;
    private CountDownTimer temporizador;

    private static final long TIEMPO_TOTAL = 120000; // 2 minutos

    public QuizManager(Context context) {
        this.context = context;
    }

    public void initQuiz() {
        // Castea el contexto como AppCompatActivity para acceder a las vistas
        AppCompatActivity activity = (AppCompatActivity) context;

        // Referenciar las vistas del layout de preguntas
        tvPregunta = activity.findViewById(R.id.questionText);
        tvPuntos = activity.findViewById(R.id.scoreText);
        tvTiempo = activity.findViewById(R.id.timerText);
        tvIntentos = activity.findViewById(R.id.livesText);
        btnOpcion1 = activity.findViewById(R.id.option1);
        btnOpcion2 = activity.findViewById(R.id.option2);
        btnOpcion3 = activity.findViewById(R.id.option3);
        btnOpcion4 = activity.findViewById(R.id.option4);
        btnRetroalimentacion = activity.findViewById(R.id.retroButton);

        // Inicializar preguntas
        preguntas = PreguntasSisSolar.obtenerPreguntas();

        // Configurar botones
        configurarBotones();

        // Mostrar la primera pregunta
        mostrarPregunta();

        // Iniciar temporizador
        iniciarTemporizador();
    }

    private void configurarBotones() {
        View.OnClickListener listener = v -> {
            Button botonPresionado = (Button) v;
            verificarRespuesta(botonPresionado);
        };

        btnOpcion1.setOnClickListener(listener);
        btnOpcion2.setOnClickListener(listener);
        btnOpcion3.setOnClickListener(listener);
        btnOpcion4.setOnClickListener(listener);

        // Configurar el botón de retroalimentación
        btnRetroalimentacion.setOnClickListener(v -> {
            Intent intent = new Intent(context, RetroalimentacionAct.class);
            context.startActivity(intent);
            ((AppCompatActivity) context).finish(); // Finalizar la actividad actual
        });

        btnRetroalimentacion.setVisibility(View.GONE); // Ocultar el botón al inicio
    }

    private void verificarRespuesta(Button botonSeleccionado) {
        int respuestaSeleccionada = -1;
        if (botonSeleccionado == btnOpcion1) respuestaSeleccionada = 0;
        if (botonSeleccionado == btnOpcion2) respuestaSeleccionada = 1;
        if (botonSeleccionado == btnOpcion3) respuestaSeleccionada = 2;
        if (botonSeleccionado == btnOpcion4) respuestaSeleccionada = 3;

        if (respuestaSeleccionada == preguntas.get(preguntaActual).getRespuestaCorrecta()) {
            puntos += 10;
            preguntaActual++;
            intentosRestantes = 2;
            if (preguntaActual < preguntas.size()) {
                mostrarPregunta();
            } else {
                finalizarJuego("¡Felicitaciones! Has completado todas las preguntas", true);
            }
        } else {
            intentosRestantes--;
            if (intentosRestantes <= 0) {
                finalizarJuego("Game Over - Se acabaron los intentos", false);
            } else {
                puntos -= 5;
            }
        }
        actualizarUI();
    }

    private void mostrarPregunta() {
        if (preguntaActual < preguntas.size()) {
            PreguntasSisSolar preguntaActualObj = preguntas.get(preguntaActual);
            tvPregunta.setText(preguntaActualObj.getPregunta());
            String[] opciones = preguntaActualObj.getOpciones();

            if (opciones.length >= 4) {
                btnOpcion1.setText(opciones[0]);
                btnOpcion2.setText(opciones[1]);
                btnOpcion3.setText(opciones[2]);
                btnOpcion4.setText(opciones[3]);
            }
        } else {
            finalizarJuego("¡Felicitaciones! Has completado todas las preguntas", true);
        }
    }

    private void actualizarUI() {
        tvPuntos.setText(String.valueOf(puntos));
        tvIntentos.setText("❤️".repeat(intentosRestantes));
    }

    private void iniciarTemporizador() {
        temporizador = new CountDownTimer(TIEMPO_TOTAL, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int segundos = (int) (millisUntilFinished / 1000);
                int minutos = segundos / 60;
                segundos = segundos % 60;
                tvTiempo.setText(String.format("%02d:%02d", minutos, segundos));
            }

            @Override
            public void onFinish() {
                finalizarJuego("¡Se acabó el tiempo!", false);
            }
        }.start();
    }

    private void finalizarJuego(String mensaje, boolean jugadorCompletoJuego) {
        // Mostrar el mensaje de finalización
        tvPregunta.setText(mensaje + "\nPuntos finales: " + puntos);

        // Ocultar las opciones de preguntas
        btnOpcion1.setVisibility(View.GONE);
        btnOpcion2.setVisibility(View.GONE);
        btnOpcion3.setVisibility(View.GONE);
        btnOpcion4.setVisibility(View.GONE);

        // Mostrar el botón para ir a retroalimentación
        btnRetroalimentacion.setVisibility(View.VISIBLE);
        btnRetroalimentacion.setOnClickListener(v -> {
            Intent intent = new Intent(context, RetroalimentacionAct.class);
            if (jugadorCompletoJuego) {
                // Mensaje para el jugador que completó todas las preguntas
                intent.putExtra("mensaje", "¡Felicidades! Terminaste el juego con éxito.");
            } else {
                // Mensaje para el jugador que perdió todas las vidas
                intent.putExtra("mensaje", "Sigue intentándolo, no te rindas.");
            }
            context.startActivity(intent);
        });

        // Detener el temporizador si aún está activo
        if (temporizador != null) {
            temporizador.cancel();
        }
    }
}
