package com.example.proyecto_finalmov;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;

public class EcosistemaAct extends AppCompatActivity {

    private static final String TAG = "EcosistemaAct";

    // Respuestas correctas
    private final String[] correctMatches = {
            "card_bosque",    // slot1
            "card_desierto",  // slot2
            "card_ecomarino", // slot3
            "card_selva",     // slot4
            "card_humedales", // slot5
            "card_tundra"     // slot6
    };

    // Coincidencias del usuario
    private final String[] userMatches = new String[6];

    private TextView errorMessage;
    private GridLayout cardsContainer; // Cambiar de LinearLayout a GridLayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ecosistemas);

        try {
            // Inicializar vistas
            errorMessage = findViewById(R.id.error_message);
            cardsContainer = findViewById(R.id.cards_container);

            if (errorMessage == null || cardsContainer == null) {
                throw new IllegalStateException("Error: Faltan vistas clave en el diseño ecosistemas.xml");
            }

            // Inicializar tarjetas y slots
            TextView[] cards = {
                    findViewById(R.id.card_humedales),
                    findViewById(R.id.card_bosque),
                    findViewById(R.id.card_desierto),
                    findViewById(R.id.card_ecomarino),
                    findViewById(R.id.card_tundra),
                    findViewById(R.id.card_selva)
            };

            TextView[] slots = {
                    findViewById(R.id.slot1),
                    findViewById(R.id.slot2),
                    findViewById(R.id.slot3),
                    findViewById(R.id.slot4),
                    findViewById(R.id.slot5),
                    findViewById(R.id.slot6)
            };

            // Validar que todas las tarjetas y slots existan
            for (TextView card : cards) {
                if (card == null) {
                    throw new IllegalStateException("Error: Una de las tarjetas no se encuentra en el diseño ecosistemas.xml");
                }
                setTouchListener(card);
            }

            for (int i = 0; i < slots.length; i++) {
                if (slots[i] == null) {
                    throw new IllegalStateException("Error: Uno de los slots no se encuentra en el diseño ecosistemas.xml");
                }
                setDragListeners(slots[i], i);
            }

        } catch (Exception e) {
            Log.e(TAG, "Error al inicializar EcosistemaAct", e);
            Toast.makeText(this, "Ocurrió un error al cargar la actividad. Verifica el diseño.", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void setTouchListener(TextView card) {
        card.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", getResources().getResourceEntryName(card.getId()));
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(card);
                v.startDragAndDrop(data, shadowBuilder, card, 0);
                return true;
            }
            return false;
        });
    }

    private void setDragListeners(TextView slot, int index) {
        slot.setOnDragListener((v, event) -> {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    return true;

                case DragEvent.ACTION_DROP:
                    View draggedCard = (View) event.getLocalState();
                    if (draggedCard == null) return false;

                    String draggedCardId = getResources().getResourceEntryName(draggedCard.getId());

                    // Verificar si es la combinación correcta
                    if (draggedCardId.equals(correctMatches[index])) {
                        slot.setBackground(draggedCard.getBackground());
                        userMatches[index] = draggedCardId;
                        draggedCard.setVisibility(View.INVISIBLE);
                        errorMessage.setVisibility(View.GONE);
                        validateMatches();
                    } else {
                        errorMessage.setVisibility(View.VISIBLE);
                        errorMessage.setText("¡Inténtalo de nuevo! Este ecosistema no es correcto.");
                        draggedCard.setVisibility(View.VISIBLE);
                    }
                    return true;

                case DragEvent.ACTION_DRAG_ENDED:
                    View view = (View) event.getLocalState();
                    if (!event.getResult() && view != null) {
                        view.setVisibility(View.VISIBLE);
                    }
                    return true;

                default:
                    return false;
            }
        });
    }

    private void validateMatches() {
        boolean isComplete = true;
        for (int i = 0; i < correctMatches.length; i++) {
            if (userMatches[i] == null || !userMatches[i].equals(correctMatches[i])) {
                isComplete = false;
                break;
            }
        }

        if (isComplete) {
            Toast.makeText(this, "¡Felicitaciones! Has clasificado correctamente todos los ecosistemas.", Toast.LENGTH_LONG).show();

            // Redirigir a EcosistemaMatching tras completar el juego
            Intent intent = new Intent(EcosistemaAct.this, EcosistemaSound.class);
            startActivity(intent);
            finish(); // Finalizar esta actividad
        }
    }
}
