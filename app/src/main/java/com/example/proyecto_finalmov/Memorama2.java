package com.example.proyecto_finalmov;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class Memorama2 extends AppCompatActivity {

    private GridLayout gameGrid;
    private ArrayList<Pair> cardPairs; // Lista de pares (imagen - descripción)
    private ImageView firstCard, secondCard;
    private int firstCardIndex, secondCardIndex;
    private boolean isBusy = false;
    private CountDownTimer countDownTimer;
    private int matches = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memorama2);

        gameGrid = findViewById(R.id.gameGrid2);
        cardPairs = new ArrayList<>();
        addCardPairs();

        // Duplicar y barajar las tarjetas
        ArrayList<Pair> gameCards = new ArrayList<>();
        for (Pair pair : cardPairs) {
            gameCards.add(new Pair(pair.imageRes, pair.textRes, true));  // Imagen
            gameCards.add(new Pair(pair.imageRes, pair.textRes, false)); // Texto
        }
        Collections.shuffle(gameCards);

        // Crear las tarjetas en el tablero
        for (int i = 0; i < gameCards.size(); i++) {
            final int index = i;
            Pair pair = gameCards.get(i);

            ImageView card = new ImageView(this);
            card.setImageResource(R.drawable.card_back); // Imagen del dorso de la tarjeta
            card.setBackgroundResource(R.drawable.card_backgroud); // Fondo personalizado
            card.setScaleType(ImageView.ScaleType.CENTER_INSIDE); // Escala bien la imagen dentro de la tarjeta
            card.setTag(pair); // Asociar el par a la tarjeta

            // Configurar márgenes uniformes y tamaño dinámico
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0; // Automáticamente ajustado por el espacio disponible
            params.height = 0; // Automáticamente ajustado por el espacio disponible
            params.setMargins(12, 12, 12, 12); // Márgenes entre las tarjetas
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // Peso igual para 3 columnas
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);    // Peso igual para 3 filas
            card.setLayoutParams(params);

            card.setOnClickListener(v -> onCardClicked(card, index));
            gameGrid.addView(card);
        }

        // Iniciar temporizador
        countDownTimer = new CountDownTimer(60000, 1000) { // 1 minuto
            @Override
            public void onTick(long millisUntilFinished) {
                // Puedes mostrar un temporizador en pantalla si lo deseas
            }

            @Override
            public void onFinish() {
                Toast.makeText(Memorama2.this, "¡Tiempo agotado!", Toast.LENGTH_LONG).show();
            }
        }.start();
    }

    private void addCardPairs() {
        // Agregar pares de imágenes (imagen descriptiva y texto explicativo)
        cardPairs.add(new Pair(R.drawable.sisnervioso, R.drawable.sisnervioso_text, true));
        cardPairs.add(new Pair(R.drawable.costillas, R.drawable.costillas_text, true));
        cardPairs.add(new Pair(R.drawable.rinones, R.drawable.rinones_text, true));
        cardPairs.add(new Pair(R.drawable.sistemacirculatorio, R.drawable.sistemacirculatorio, true));
        cardPairs.add(new Pair(R.drawable.creaneo, R.drawable.creaneo_text, true));
        cardPairs.add(new Pair(R.drawable.sistemarespi, R.drawable.sistemarespi_text, true));
    }

    private void onCardClicked(ImageView card, int index) {
        if (isBusy || card.getVisibility() != View.VISIBLE) return;

        Pair pair = (Pair) card.getTag();

        // Mostrar la imagen asociada a la tarjeta
        if (pair.isImage) {
            card.setImageResource(pair.imageRes);
        } else {
            card.setImageResource(pair.textRes);
        }

        if (firstCard == null) {
            firstCard = card;
            firstCardIndex = index;
        } else if (secondCard == null && card != firstCard) {
            secondCard = card;
            secondCardIndex = index;
            checkMatch();
        }
    }

   private void checkMatch() {
       if (firstCard == null || secondCard == null) return;

       Pair firstPair = (Pair) firstCard.getTag();
       Pair secondPair = (Pair) secondCard.getTag();

       isBusy = true;

       new Handler().postDelayed(() -> {
           if (firstPair.matches(secondPair)) {
               firstCard.setVisibility(View.INVISIBLE);
               secondCard.setVisibility(View.INVISIBLE);
               matches++;

               if (matches == cardPairs.size()) {
                   // Cuando el memorama esté completo
                   Toast.makeText(this, "¡Felicidades! Has completado el memorama.", Toast.LENGTH_LONG).show();

                   // Enviar a la actividad de retroalimentación con mensaje de felicitación
                   Intent intent = new Intent(Memorama2.this, RetroalimentacionAct.class);
                   intent.putExtra("mensaje", "¡Felicidades! Has completado el memorama. 🌟");
                   startActivity(intent);
                   finish(); // Finaliza la actividad actual para evitar regresar
               }
           } else {
               firstCard.setImageResource(R.drawable.card_back);
               secondCard.setImageResource(R.drawable.card_back);
               Toast.makeText(this, "¡Intenta de nuevo!", Toast.LENGTH_SHORT).show();
           }

           firstCard = null;
           secondCard = null;
           isBusy = false;
    }, 1000);
}

    private static class Pair {
        int imageRes;
        int textRes;
        boolean isImage;

        Pair(int imageRes, int textRes, boolean isImage) {
            this.imageRes = imageRes;
            this.textRes = textRes;
            this.isImage = isImage;
        }

        boolean matches(Pair other) {
            return this.imageRes == other.imageRes && this.textRes == other.textRes;
        }
    }


}
