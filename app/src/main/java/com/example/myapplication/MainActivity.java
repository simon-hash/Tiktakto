package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int activePlayer = 0;

    // null ist grün und 1 ist pink und 2 ist null
    int[] gameState = {2,2,2,2,2,2,2,2,2};

    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2,5,8},{0,4,8},{2,4,6}};

    boolean gameActive = true;

    Button playAgainButton;
    TextView winnerTextView;

    public void dropIn(View view){
        ImageView counter = (ImageView) view;
        Log.i("Tag",counter.getTag().toString());
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        // Nur wenn das feld 2 aktiv ist was bedeutet das es leer ist, darf es angecklickt werden
        if (gameState[tappedCounter] == 2 && gameActive) {

            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1500);

            if (activePlayer == 0){
                counter.setImageResource(R.drawable.grmarke);
                activePlayer = 1;
            }else if (activePlayer== 1){
                counter.setImageResource(R.drawable.romarke);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1500).rotation(3600).setDuration(200);

            for(int[] winningsPosition: winningPositions){

                if (gameState[winningsPosition[0]] == gameState[winningsPosition[1]] && gameState[winningsPosition[1]] == gameState[winningsPosition[2]] && gameState[winningsPosition[0]] !=2){

                    String winner = "";
                    gameActive= false;

                    if (activePlayer == 1){
                        winner = "Grün";
                    }else if(activePlayer == 0) {
                        winner = "Pink";
                    }

                    Toast.makeText(this, winner +" hat gewonnen", Toast.LENGTH_SHORT).show();

                    winnerTextView.setText(winner + " hat gewonnen");

                    playAgainButton.setVisibility(View.VISIBLE);
                    winnerTextView.setVisibility(View.VISIBLE);

                }else {
                    gameActive = false;
                    for (int counterState : gameState){
                        if(counterState == 2) gameActive = true;
                    }
                    if(!gameActive){

                        winnerTextView.setText(" unentschieden");

                        playAgainButton.setVisibility(View.VISIBLE);
                        winnerTextView.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void playAgain(View view){

        playAgainButton.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        // Leeren der Münzen wenn das game vorbei ist (visuell)
        for(int i = 0; i<gridLayout.getChildCount(); i++){
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }
        for(int i = 0; i<gameState.length; i++){
            gameState[i] = 2;
        }
        activePlayer = 0;
        gameActive = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playAgainButton = findViewById(R.id.playAgainButton);
        winnerTextView = findViewById(R.id.winnerTextView);
    }
}


