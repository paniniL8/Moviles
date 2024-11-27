package com.example.proyecto_finalmov;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EcosistemaSound extends AppCompatActivity {

    private static final int TOTAL_PAIRS = 4; // Total de pares que deben emparejarse
    private final String[] soundPairs = {
            "sonido_bosque", "Bosque",
            "sonido_desierto", "Desierto",
            "sonido_oceano", "Océano",
            "sonido_selva", "Selva"
    };

    private LinearLayout soundsContainer, matchesContainer;
    private MaterialButton btnCheck;
    private MediaPlayer mediaPlayer;
    private String selectedSound = null;
    private final Map<String, String> userSelections = new HashMap<>();
    private int correctMatches = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ecosistemas_sound); // Asegúrate de que este layout exista
        initViews();
        setupSoundItems();
        setupMatchItems();
        setupButtons();
    }

    private void initViews() {
        soundsContainer = findViewById(R.id.soundsContainer);
        matchesContainer = findViewById(R.id.matchesContainer);
        btnCheck = findViewById(R.id.btnCheck);
    }

    private void setupSoundItems() {
        for (int i = 0; i < soundPairs.length; i += 2) {
            View soundItem = getLayoutInflater().inflate(R.layout.matching_item, soundsContainer, false);

            ImageView ivSound = soundItem.findViewById(R.id.ivSound);
            TextView tvMatch = soundItem.findViewById(R.id.tvMatch);

            if (ivSound != null) ivSound.setVisibility(View.VISIBLE);
            if (tvMatch != null) tvMatch.setVisibility(View.GONE);

            setupSoundItem(soundItem, soundPairs[i]);
            soundsContainer.addView(soundItem);
        }
    }

    private void setupMatchItems() {
        List<String> shuffledMatches = new ArrayList<>();
        for (int i = 1; i < soundPairs.length; i += 2) {
            shuffledMatches.add(soundPairs[i]);
        }
        Collections.shuffle(shuffledMatches);

        for (String match : shuffledMatches) {
            View matchItem = getLayoutInflater().inflate(R.layout.matching_item, matchesContainer, false);

            ImageView ivSound = matchItem.findViewById(R.id.ivSound);
            TextView tvMatch = matchItem.findViewById(R.id.tvMatch);

            if (ivSound != null) ivSound.setVisibility(View.GONE);
            if (tvMatch != null) {
                tvMatch.setVisibility(View.VISIBLE);
                tvMatch.setText(match);
            }
            setupMatchItem(matchItem, match);
            matchesContainer.addView(matchItem);
        }
    }

    private void setupSoundItem(View item, String soundResource) {
        ImageView ivSound = item.findViewById(R.id.ivSound);
        item.setTag(soundResource);

        item.setOnClickListener(v -> {
            playSound(soundResource);
            selectedSound = soundResource;
        });
    }

    private void setupMatchItem(View item, String matchText) {
        TextView tvMatch = item.findViewById(R.id.tvMatch);

        item.setOnClickListener(v -> {
            if (selectedSound != null) {
                userSelections.put(selectedSound, matchText);
                boolean isCorrect = false;
                for (int i = 0; i < soundPairs.length; i += 2) {
                    if (selectedSound.equals(soundPairs[i]) && soundPairs[i + 1].equals(matchText)) {
                        isCorrect = true;
                        correctMatches++;
                        break;
                    }
                }
                updateCardColor((CardView) item, isCorrect);
                if (correctMatches == TOTAL_PAIRS) {
                    Toast.makeText(this, "¡Emparejaste todos los sonidos correctamente!", Toast.LENGTH_LONG).show();
                }
                selectedSound = null;
            } else {
                Toast.makeText(this, "Primero selecciona un sonido", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateCardColor(View view, boolean isCorrect) {
        if (view instanceof CardView) {
            CardView cardView = (CardView) view;
            int colorFrom = cardView.getCardBackgroundColor().getDefaultColor();
            int colorTo = isCorrect ? getResources().getColor(R.color.primary_green) :
                    getResources().getColor(R.color.primary_red);
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
            colorAnimation.setDuration(300);
            colorAnimation.addUpdateListener(animator ->
                    cardView.setCardBackgroundColor((int) animator.getAnimatedValue()));
            colorAnimation.start();
        } else {
            Toast.makeText(this, "El elemento no es un CardView.", Toast.LENGTH_SHORT).show();
        }
    }

    private void playSound(String soundResource) {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }

            int resId = getResources().getIdentifier(soundResource, "raw", getPackageName());
            if (resId == 0) {
                Toast.makeText(this, "Archivo de sonido no encontrado: " + soundResource, Toast.LENGTH_SHORT).show();
                return;
            }

            mediaPlayer = MediaPlayer.create(this, resId);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al reproducir el sonido.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupButtons() {
        btnCheck.setOnClickListener(v -> {
            if (correctMatches == TOTAL_PAIRS) {
                Toast.makeText(this, "¡Felicidades! Terminaste el juego.", Toast.LENGTH_LONG).show();
                // Aquí puedes agregar lógica para avanzar a otra actividad o finalizar el juego
            } else {
                Toast.makeText(this, "Revisa los pares incorrectos.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
