package com.example.proyecto_finalmov;

import java.util.ArrayList;
import java.util.List;

public class PreguntasSisSolar {
    private String pregunta; // Texto de la pregunta
    private String[] opciones; // Opciones de respuesta
    private int respuestaCorrecta; // Índice de la respuesta correcta

    // Constructor
    public PreguntasSisSolar(String pregunta, String[] opciones, int respuestaCorrecta) {
        this.pregunta = pregunta;
        this.opciones = opciones;
        this.respuestaCorrecta = respuestaCorrecta;
    }

    // Métodos getters
    public String getPregunta() {
        return pregunta;
    }

    public String[] getOpciones() {
        return opciones;
    }

    public int getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    // Método estático para obtener las preguntas del sistema solar
    public static List<PreguntasSisSolar> obtenerPreguntas() {
        List<PreguntasSisSolar> preguntas = new ArrayList<>();
        preguntas.add(new PreguntasSisSolar(
                "¿Cuál es el planeta más cercano al Sol?",
                new String[]{"Mercurio", "Venus", "Tierra", "Marte"},
                0
        ));
        preguntas.add(new PreguntasSisSolar(
                "¿Cuál es el planeta más grande del Sistema Solar?",
                new String[]{"Saturno", "Urano", "Júpiter", "Neptuno"},
                2
        ));
        preguntas.add(new PreguntasSisSolar(
                "¿Qué estrella está en el centro del Sistema Solar?",
                new String[]{"El Sol", "La Luna", "Júpiter", "Saturno"},
                0
        ));
        preguntas.add(new PreguntasSisSolar(
                "¿Cuántos planetas tiene el Sistema Solar?",
                new String[]{"7", "8", "9", "10"},
                1
        ));
        preguntas.add(new PreguntasSisSolar(
                "¿Qué planeta tiene un gran sistema de anillos?",
                new String[]{"Júpiter", "Saturno", "Urano", "Neptuno"},
                1
        ));
        preguntas.add(new PreguntasSisSolar(
                "¿Cuál es el planeta más caliente del Sistema Solar?",
                new String[]{"Mercurio", "Venus", "Tierra", "Marte"},
                1
        ));
        return preguntas;
    }
}
