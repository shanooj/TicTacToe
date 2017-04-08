package com.karbaros.tictactoe;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int activePlayer; // 0 for O 1 for X
    boolean isGameActive;
    int[] gameState = new int[9];
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    ImageView ivPlace;
    FrameLayout layout;
    GridLayout gridLayout;
    TextView winnerMessage;
    ImageView ivResult;
    ImageView ivTic;
    private Animation animFadeIn;
    private Animation animFadeOut;
    private Animation animBounce;
    private Animation animBlink;
    private Handler handler;


    public void setButton(View view) {
        ivPlace = (ImageView) view;
        int tappedPlace = Integer.parseInt(ivPlace.getTag().toString());
        if (gameState[tappedPlace] == 2 && isGameActive) {
            gameState[tappedPlace] = activePlayer;
            ivPlace.setTranslationY(-1000f);
            if (activePlayer == 0) {
                ivPlace.setImageResource(R.drawable.ic_icon);
                activePlayer = 1;
            } else {
                ivPlace.setImageResource(R.drawable.ic_cross);
                activePlayer = 0;
            }
            ivPlace.animate().translationYBy(1000f).rotation(360).setDuration(300);
            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2) {

                    // Someone has won!
                    isGameActive = false;
                    String winner = "X";
                    if (gameState[winningPosition[0]] == 0) {
                        winner = "O";
                    }

                    if (winner == "X")
                        setWinnigResult("WINNER !", R.drawable.ic_cross);
                    else
                        setWinnigResult("WINNER !", R.drawable.ic_icon);

                } else {
                    boolean gameIsOver = true;
                    for (int counterState : gameState) {
                        if (counterState == 2)
                            gameIsOver = false;
                    }
                    if (gameIsOver) {
                        setWinnigResult("DRAW !", R.drawable.ic_draw);
                    }

                }
            }
        }

    }

    public void restartGame(View view) {

        layout.setAnimation(animFadeOut);
        gridLayout.setAnimation(animFadeIn);
        initilizeVariable();


        for (int i = 0; i < gridLayout.getChildCount(); i++) {

            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (FrameLayout) findViewById(R.id.flResult);
        winnerMessage = (TextView) findViewById(R.id.tvWinner);
        ivResult = (ImageView) findViewById(R.id.ivResult);
        gridLayout = (GridLayout) findViewById(R.id.glBoard);
        ivTic = (ImageView) findViewById(R.id.Tic);
        animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        animFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        animBounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
        animBlink = AnimationUtils.loadAnimation(this, R.anim.blink);
        handler = new Handler();

        initilizeVariable();
    }

    public void initilizeVariable() {

        isGameActive = true;
        activePlayer = 0;
        
        layout.setVisibility(View.INVISIBLE);
        ivTic.setVisibility(View.VISIBLE);


        gridLayout.setVisibility(View.VISIBLE);


        for (int i = 0; i < gameState.length; i++)
            gameState[i] = 2;
    }

    public void setWinnigResult(String result, int drawableId) {

        gridLayout.setVisibility(View.INVISIBLE);
        ivTic.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.VISIBLE);

        winnerMessage.setText(result);
        winnerMessage.setAnimation(animBounce);
        ivResult.setImageResource(drawableId);
        ivResult.setAnimation(animBlink);



        layout.setAnimation(animFadeIn);
        //gridLayout.setAnimation(animFadeOut);

    }
}
