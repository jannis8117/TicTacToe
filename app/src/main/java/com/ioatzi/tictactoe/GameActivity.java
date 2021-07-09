package com.ioatzi.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Objects;

public class GameActivity extends AppCompatActivity {

    int activePlayer = 0;
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8},{0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};
    boolean stillPlaying = true;
    Button playAgainButton;
    Button backThreeButton;
    TextView winnerTextView;
    TextView playerOne;
    TextView playerTwo;
    TextView winnerView;
    TextView scoreView;
    int plOne = 0;
    int plTwo = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN) ;
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_game);

        playAgainButton = findViewById(R.id.playAgain);
        backThreeButton = findViewById(R.id.backThreeButton);
        playerOne = findViewById(R.id.playerOneView);
        playerTwo = findViewById(R.id.playerTwoView);
        winnerView = findViewById(R.id.GewinnerView);
        scoreView = findViewById(R.id.scoreView);

        winnerTextView = findViewById(R.id.winnerTextView);
        playAgainButton.setOnClickListener(this::playAgain);
        backThreeButton.setOnClickListener(v -> {
            Intent intent = new Intent(GameActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            finish();
        });

        Intent intent = getIntent();
        if (intent.getExtras().get("playerOne").toString().isEmpty() && intent.getExtras().get("playerTwo").toString().isEmpty()){
            playerOne.setText("Player One");
            playerTwo.setText("Player Two");
        } else if(intent.getExtras().get("playerOne").toString().isEmpty()){
            playerOne.setText("Player One");
            playerTwo.setText(intent.getExtras().get("playerOne").toString());
        } else if(intent.getExtras().get("playerTwo").toString().isEmpty()){
            playerOne.setText(intent.getExtras().get("playerOne").toString());
            playerTwo.setText("Player Two");
        } else {
            playerOne.setText(intent.getExtras().get("playerOne").toString());
            playerTwo.setText(intent.getExtras().get("playerTwo").toString());
        }

        if (intent.getExtras().get("limit").toString().equals("1")){
            winnerView.setText("Spiel auf " +intent.getExtras().get("limit").toString() + " Punkt");
        } else {
            winnerView.setText("Spiel auf " +intent.getExtras().get("limit").toString() + " Punkten");
        }

        scoreView.setText(getPlOneScore() + " : " + getPlTwoScore());
    }

    public int getPlOneScore(){
        return plOne;
    }

    public int getPlTwoScore(){
        return plTwo;
    }

    @SuppressLint("SetTextI18n")
    public void zug(View view){
        ImageView cntr = (ImageView) view;
        Intent intent = getIntent();
        int tappedCntr = Integer.parseInt(cntr.getTag().toString());
        int limit = Integer.parseInt(String.valueOf(intent.getExtras().get("limit")));
        if(gameState[tappedCntr] == 2 && stillPlaying) {
            gameState[tappedCntr] = activePlayer;
            cntr.setTranslationY(-1500);

            if (activePlayer == 0) {
                cntr.setImageResource(R.drawable.cross);
                activePlayer = 1;
            } else if (activePlayer == 1) {
                cntr.setImageResource(R.drawable.circle);
                activePlayer = 0;
            }
            cntr.animate().translationYBy(+1500).rotation(3600).setDuration(300);
            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
                    if(activePlayer == 1 && getPlOneScore() < limit -1){
                        plOne++;
                        playAgain(view);
                        scoreView.setText(getPlOneScore() + " : " +getPlTwoScore());
                    } else if(activePlayer == 0 && getPlTwoScore() < limit){
                        plTwo++;
                        playAgain(view);
                        scoreView.setText(getPlOneScore() + " : " +getPlTwoScore());
                    } else {
                        String winner = "";
                        stillPlaying = false;
                        if (activePlayer == 1) {
                            winner = "Kreuz";
                            plOne++;
                            scoreView.setText(getPlOneScore() + " : " +getPlTwoScore());
                        } else if (activePlayer == 0) {
                            winner = "Kreis";
                            plTwo++;
                            scoreView.setText(getPlOneScore() + " : " +getPlTwoScore());
                        }

                        winnerTextView.setText(winner + " hat gewonnen");
                        playAgainButton.setVisibility(View.VISIBLE);
                        backThreeButton.setVisibility(View.VISIBLE);
                        winnerTextView.setVisibility(View.VISIBLE);
                        break;
                    }

                } else  {
                    stillPlaying = false;

                    for(int counterState: gameState){
                        if (counterState == 2) {
                            stillPlaying = true;
                            break;
                        }
                    }

                    if(!stillPlaying){
                        playAgain(view);
                    }
                }
            }
        }
    }

    public void playAgain(View view){
        playAgainButton.setVisibility(View.INVISIBLE);
        backThreeButton.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for(int i = 0; i < gridLayout.getChildCount(); i++){
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }

        Arrays.fill(gameState, 2);

        activePlayer = 0;
        stillPlaying = true;
    }
}