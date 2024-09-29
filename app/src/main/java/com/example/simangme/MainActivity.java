package com.example.simangme;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private CardView cardRed, cardBlue, cardGreen, cardYellow;
    private Button startButton;
    private TextView gameStatus , scoreTextView;

    private List<Integer> sequence = new ArrayList<>();
    private List<Integer> userSequence = new ArrayList<>();
    private boolean userTurn = false;
    private int level = 0;
    private int score = 0; // Score variable






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        cardRed = findViewById(R.id.card_red);
        cardBlue = findViewById(R.id.card_blue);
        cardGreen = findViewById(R.id.card_green);
        cardYellow = findViewById(R.id.card_yellow);
        startButton = findViewById(R.id.start_button);
        gameStatus = findViewById(R.id.game_status);
        scoreTextView = findViewById(R.id.score_text_view);

        // Setting up card click listeners
        cardRed.setOnClickListener(v -> checkUserInput(0));
        cardBlue.setOnClickListener(v -> checkUserInput(1));
        cardGreen.setOnClickListener(v -> checkUserInput(2));
        cardYellow.setOnClickListener(v -> checkUserInput(3));

        startButton.setOnClickListener(v -> startGame());





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private void startGame() {
        sequence.clear();
        userSequence.clear();
        level = 0;
        score = 0; // Reset score
        updateScoreDisplay(); // Update score display
        gameStatus.setText("Watch the sequence!");
        addNextMove();
    }

    private void addNextMove() {
        Random random = new Random();
        sequence.add(random.nextInt(4));  // Random number between 0-3
        showSequence();
    }

    private void showSequence() {
        userTurn = false;
        for (int i = 0; i < sequence.size(); i++) {
            final int colorIndex = sequence.get(i);
            final int delay = (i + 1) * 1000;
            cardFlash(colorIndex, delay);
        }
        gameStatus.postDelayed(() -> {
            gameStatus.setText("Your turn!");
            userTurn = true;
            userSequence.clear();
        }, sequence.size() * 1000);
    }

    private void cardFlash(int colorIndex, int delay) {
        CardView card = getCardByIndex(colorIndex);
        card.postDelayed(() -> card.setAlpha(0.5f), delay);
        card.postDelayed(() -> card.setAlpha(1f), delay + 500);
    }

    private CardView getCardByIndex(int index) {
        switch (index) {
            case 0:
                return cardRed;
            case 1:
                return cardBlue;
            case 2:
                return cardGreen;
            case 3:
                return cardYellow;
            default:
                return null;
        }
    }

    private void checkUserInput(int colorIndex) {
        if (!userTurn) return;
        userSequence.add(colorIndex);
        if (userSequence.size() == sequence.size()) {
            userTurn = false;
            checkSequence();
        }
    }

    private void checkSequence() {
        for (int i = 0; i < sequence.size(); i++) {
            if (!sequence.get(i).equals(userSequence.get(i))) {
                gameStatus.setText("Game Over! Try again.");
                return;
            }

        }

        score++; // Increment score for successful level completion
        updateScoreDisplay(); // Update score display

        gameStatus.setText("Good job! Next level.");
        addNextMove();
    }

    private void updateScoreDisplay() {
        scoreTextView.setText("Score: " + score); // Update score TextView
    }

}




