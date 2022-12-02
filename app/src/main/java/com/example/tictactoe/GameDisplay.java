package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class GameDisplay extends AppCompatActivity {

    private final int[][] winningOptions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    String image;
    Integer index;
    Boolean gameOn;
    CurrentStatus status;
    GridLayout gridLayout;
    ImageView gameStatusImage;
    ImageView winMark;
    Button playAgainBTN;
    String[] gameBoard;

    private void initBoard() {
        this.gameBoard = new String[]{
                "N", "N", "N", "N", "N", "N", "N", "N", "N"
        };
    }

    private final int[] winningDraw = {
            R.drawable.mark6,
            R.drawable.mark7,
            R.drawable.mark8,
            R.drawable.mark3,
            R.drawable.mark4,
            R.drawable.mark5,
            R.drawable.mark1,
            R.drawable.mark2};

    private enum CurrentStatus {
        X_TURN,
        X_WIN,
        O_TURN,
        O_WIN,
        TIE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_display);

        initBoard();

        this.status = CurrentStatus.X_TURN;
        this.playAgainBTN = findViewById(R.id.play_again_btn);
        this.gameOn = true;
        this.playAgainBTN.setVisibility(View.GONE);
        this.gridLayout = findViewById(R.id.grid_layout);

        this.winMark = findViewById(R.id.win_mark);
        this.gameStatusImage = findViewById(R.id.game_status_img);
        this.gameStatusImage.setImageResource(getGameStatusImage());

    }

    private void setNextTurn() {
        this.status = this.status == CurrentStatus.X_TURN ? CurrentStatus.O_TURN : CurrentStatus.X_TURN;
        this.gameStatusImage.setImageResource(getGameStatusImage());
    }

    public void onCellClick(View view) {
        if (this.gameOn) {
            this.image = findViewById(view.getId()).toString();
            this.index = Integer.parseInt(String.valueOf(this.image.charAt(this.image.length() - 2))) - 1;
            if (Objects.equals(this.gameBoard[index], "N")) {
                this.gameBoard[this.index] = this.status == CurrentStatus.X_TURN ? "X" : "O";

                ((ImageView) gridLayout.getChildAt(this.index)).setImageResource(getPlayerImage());

                checkWinner();
                if (this.status == CurrentStatus.X_TURN || this.status == CurrentStatus.O_TURN) {
                    setNextTurn();
                }
            }
        }
    }

    private void checkWinner() {
        int counter = 0;

        for (int i = 0; i < this.gameBoard.length - 1; i++) {
            int[] temp = winningOptions[i];
            if (Objects.equals(this.gameBoard[i], "N")) {
                counter++;
            }

            if (!Objects.equals(gameBoard[temp[0]], "N") && Objects.equals(gameBoard[temp[0]], gameBoard[temp[1]]) && Objects.equals(gameBoard[temp[0]], gameBoard[temp[2]])) {

                this.winMark.setImageResource(winningDraw[i]);

                this.status = Objects.equals(gameBoard[temp[0]], "X") ? CurrentStatus.X_WIN : CurrentStatus.O_WIN;
                this.gameOn = false;
                this.playAgainBTN.setVisibility(View.VISIBLE);
                this.gameStatusImage.setImageResource(getGameStatusImage());
            }
        }
        if (counter == 0 && !Objects.equals(this.gameBoard[8], "N") && (this.status == CurrentStatus.X_TURN || this.status == CurrentStatus.O_TURN)) {
            this.status = CurrentStatus.TIE;
            this.gameOn = false;
            this.playAgainBTN.setVisibility(View.VISIBLE);
            this.gameStatusImage.setImageResource(getGameStatusImage());
        }
    }

    private void clearTheBoard() {
        for (int i = 0; i < this.gameBoard.length; i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(R.drawable.empty);
        }
    }

    public void playAgainButtonCLick(View view) {
        this.status = CurrentStatus.X_TURN;
        clearTheBoard();
        initBoard();
        this.playAgainBTN.setVisibility(View.GONE);
        this.gameOn = true;
        this.winMark.setImageResource(R.drawable.empty);
        this.gameStatusImage.setImageResource(getGameStatusImage());

    }

    private int getGameStatusImage() {
        switch (this.status) {
            case X_TURN:
                return R.drawable.xplay;
            case O_TURN:
                return R.drawable.oplay;
            case O_WIN:
                return R.drawable.owin;
            case X_WIN:
                return R.drawable.xwin;
            case TIE:
                return R.drawable.nowin;
            default:
                return R.drawable.empty;
        }
    }

    private int getPlayerImage() {
        switch (this.status) {
            case X_TURN:
                return R.drawable.x;
            case O_TURN:
                return R.drawable.o;
            default:
                return R.drawable.empty;
        }
    }
}