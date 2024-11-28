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

        // Preguntas sobre Mercurio
        preguntas.add(new PreguntasSisSolar(
                "¿Cuál es el planeta más cercano al Sol?",
                new String[]{"Mercurio", "Venus", "Tierra", "Marte"},
                0
        ));
        preguntas.add(new PreguntasSisSolar(
                "¿Cuál es el planeta más pequeño del Sistema Solar?",
                new String[]{"Marte", "Venus", "Mercurio", "Neptuno"},
                2
        ));
        preguntas.add(new PreguntasSisSolar(
                "¿Cuál es el nombre del volcán más grande del Sistema Solar, ubicado en Marte?",
                new String[]{"Monte Everest", "Monte Olimpo", "Monte Fuji", "Monte Kilimanjaro"},
                1
        ));

        preguntas.add(new PreguntasSisSolar(
                "¿Cuál es el planeta más caliente del Sistema Solar?",
                new String[]{"Mercurio", "Venus", "Tierra", "Marte"},
                1
        ));

        // Preguntas sobre la Tierra
        preguntas.add(new PreguntasSisSolar(
                "¿Cuál es el único planeta conocido que tiene vida?",
                new String[]{"Marte", "Venus", "Tierra", "Júpiter"},
                2
        ));
        preguntas.add(new PreguntasSisSolar(
                "¿Qué planeta es conocido como el 'gemelo' de la Tierra por su tamaño similar?",
                new String[]{"Marte", "Venus", "Júpiter", "Saturno"},
                1
        ));

        // Preguntas sobre Marte
        preguntas.add(new PreguntasSisSolar(
                "¿Qué planeta es conocido como el 'planeta rojo'?",
                new String[]{"Júpiter", "Marte", "Saturno", "Urano"},
                1
        ));

        // Preguntas sobre Júpiter
        preguntas.add(new PreguntasSisSolar(
                "¿Cuál es el planeta más grande del Sistema Solar?",
                new String[]{"Saturno", "Urano", "Júpiter", "Neptuno"},
                2
        ));

        preguntas.add(new PreguntasSisSolar(
                "¿Qué planeta tiene una gran tormenta llamada la Gran Mancha Roja?",
                new String[]{"Júpiter", "Saturno", "Neptuno", "Urano"},
                0
        ));
        preguntas.add(new PreguntasSisSolar(
                "¿Qué planeta tiene los vientos más fuertes del Sistema Solar?",
                new String[]{"Júpiter", "Saturno", "Neptuno", "Urano"},
                2
        ));

        // Preguntas sobre Saturno
        preguntas.add(new PreguntasSisSolar(
                "¿Qué planeta es famoso por sus impresionantes anillos?",
                new String[]{"Júpiter", "Saturno", "Urano", "Neptuno"},
                1
        ));
        preguntas.add(new PreguntasSisSolar(
                "¿Qué planeta tiene un satélite natural llamado Luna?",
                new String[]{"Tierra", "Marte", "Saturno", "Neptuno"},
                0
        ));

        // Preguntas sobre Urano
        preguntas.add(new PreguntasSisSolar(
                "¿Qué planeta gira 'de lado' en su órbita alrededor del Sol?",
                new String[]{"Neptuno", "Urano", "Saturno", "Júpiter"},
                1
        ));
        preguntas.add(new PreguntasSisSolar(
                "¿Cuál es el segundo planeta más grande del Sistema Solar?",
                new String[]{"Saturno", "Júpiter", "Urano", "Neptuno"},
                0
        ));
        preguntas.add(new PreguntasSisSolar(
                "¿Cuál es el planeta que tiene un color azul verdoso debido al metano en su atmósfera?",
                new String[]{"Urano", "Neptuno", "Saturno", "Venus"},
                0
        ));

        // Preguntas sobre Neptuno
        preguntas.add(new PreguntasSisSolar(
                "¿Cuál es el planeta más lejano del Sol en el Sistema Solar?",
                new String[]{"Urano", "Neptuno", "Saturno", "Júpiter"},
                1
        ));

        return preguntas;
    }
}
