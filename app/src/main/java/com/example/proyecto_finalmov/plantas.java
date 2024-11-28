package com.example.proyecto_finalmov;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class plantas extends AppCompatActivity {

    private final String[] correctMatches = {
            "card_bosque",    // slot1
            "card_desierto",  // slot2
            "card_sabana",    // slot3
            "card_selva",     // slot4
            "card_humedales", // slot5
            "card_tundra"     // slot6
    };

    private final String[] userMatches = new String[6];
    private TextView errorMessage;
    private GridLayout cardsContainer; // Cambiado a GridLayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantas);

        errorMessage = findViewById(R.id.error_message);
        cardsContainer = findViewById(R.id.cards_container); // Cambiado a GridLayout

        // Inicializar las tarjetas
        TextView[] cards = {
                findViewById(R.id.card_bosque),
                findViewById(R.id.card_desierto),
                findViewById(R.id.card_sabana),
                findViewById(R.id.card_selva),
                findViewById(R.id.card_humedales),
                findViewById(R.id.card_tundra)
        };

        // Inicializar los slots
        TextView[] slots = {
                findViewById(R.id.slot1),
                findViewById(R.id.slot2),
                findViewById(R.id.slot3),
                findViewById(R.id.slot4),
                findViewById(R.id.slot5),
                findViewById(R.id.slot6)
        };

        // Configurar listeners para las tarjetas y slots
        for (TextView card : cards) {
            setTouchListener(card);
        }

        for (int i = 0; i < slots.length; i++) {
            setDragListeners(slots[i], i);
        }
    }

    private void setTouchListener(TextView card) {
        card.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", card.getId() + "");
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
                    String draggedCardId = getResources().getResourceEntryName(draggedCard.getId());

                    // Verificar si es la combinación correcta
                    if (draggedCardId.equals(correctMatches[index])) {
                        // Copiar la imagen de la tarjeta al slot
                        slot.setBackground(draggedCard.getBackground());

                        // Registrar la coincidencia
                        userMatches[index] = draggedCardId;

                        // Ocultar la tarjeta original
                        draggedCard.setVisibility(View.INVISIBLE);

                        errorMessage.setVisibility(View.GONE);
                        validateMatches();
                    } else {
                        // Mostrar error
                        errorMessage.setVisibility(View.VISIBLE);
                        errorMessage.setText("¡Inténtalo de nuevo! Esta planta no corresponde a este ecosistema.");
                        draggedCard.setVisibility(View.VISIBLE);
                    }
                    return true;

                case DragEvent.ACTION_DRAG_ENDED:
                    View view = (View) event.getLocalState();
                    if (!event.getResult()) {
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
            if (userMatches[i] == null || !correctMatches[i].equals(userMatches[i])) {
                isComplete = false;
                break;
            }
        }
        if (isComplete) {
            // Enviar a la actividad de retroalimentación con mensaje de felicitación
            Intent intent = new Intent(plantas.this, RetroalimentacionAct.class);
            intent.putExtra("mensaje", "¡Felicitaciones! Has terminado la actividad con exito.");
            startActivity(intent);
            finish(); // Finaliza la actividad actual para evitar regresar
        }
    }

}
