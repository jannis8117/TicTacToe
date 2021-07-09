package com.ioatzi.tictactoe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class StartGameActivity extends AppCompatActivity {
    private final Button[] btn = new Button[4];
    private Button btn_unfocus;
    private final int[] btn_id = {R.id.optButton0, R.id.optButton1, R.id.optButton2, R.id.optButton3};
    EditText playerOne;
    EditText playerTwo;
    String focusedButton = "1";
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN) ;
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_start_game);

        playerOne = findViewById(R.id.playerOneInput);
        playerTwo = findViewById(R.id.playerTwoInput);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        String playerOneRestore = sh.getString("plOne", String.valueOf(0));
        String playerTwoRestore = sh.getString("plTwo", String.valueOf(0));

        if(playerOneRestore.isEmpty() && playerTwoRestore.isEmpty()){
            playerOne.setText("");
            playerTwo.setText("");
        } else if (playerOneRestore.isEmpty()) {
            playerOne.setText("");
            playerTwo.setText(playerTwoRestore);
        } else if (playerTwoRestore.isEmpty()) {
            playerTwo.setText("");
            playerOne.setText(playerOneRestore);
        } else {
            playerOne.setText(playerOneRestore);
            playerTwo.setText(playerTwoRestore);
        }



        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(v -> openGameActivity());
        Button backButton = findViewById(R.id.backTwoButton);
        backButton.setOnClickListener(v -> finish());


        for(int i = 0; i < btn.length; i++){
            btn[i] = findViewById(btn_id[i]);
            btn[i].setOnClickListener(v -> {
                switch (v.getId()){
                    case R.id.optButton0 :
                        setFocus(btn_unfocus, btn[0]);
                        break;

                    case R.id.optButton1 :
                        setFocus(btn_unfocus, btn[1]);
                        break;

                    case R.id.optButton2 :
                        setFocus(btn_unfocus, btn[2]);
                        break;

                    case R.id.optButton3 :
                        setFocus(btn_unfocus, btn[3]);
                        break;
                }
            });
        }



    }

    private void openGameActivity() {
        Intent intent = new Intent(StartGameActivity.this, GameActivity.class);
        intent.putExtra("playerOne", playerOne.getText().toString());
        intent.putExtra("playerTwo", playerTwo.getText().toString());
        intent.putExtra("limit", focusedButton);
        startActivity(intent);
        finish();
    }

    private void setFocus(Button btn_unfocus, Button btn_focus){
        btn_unfocus.setTextColor(Color.rgb(255, 255, 255));
        btn_unfocus.setBackgroundColor(Color.parseColor("#DA0303"));
        btn_focus.setTextColor(Color.rgb(0, 0, 0));
        btn_focus.setBackgroundColor(Color.parseColor("#f09a9a"));
        focusedButton = btn_focus.getText().toString();
        this.btn_unfocus = btn_focus;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("plOne", playerOne.getText().toString());
        savedInstanceState.putString("plTwo", playerTwo.getText().toString());
        savedInstanceState.putString("limit", focusedButton);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        playerOne.setText(savedInstanceState.getString("plOne"));
        playerTwo.setText(savedInstanceState.getString("plTwo"));
        switch (savedInstanceState.getString("limit")) {
            case "7":
                btn_unfocus = btn[3];
                setFocus(btn_unfocus,btn[3]);
                break;
            case "3":
                btn_unfocus = btn[1];
                setFocus(btn_unfocus,btn[1]);
                break;
            case "5":
                btn_unfocus = btn[2];
                setFocus(btn_unfocus,btn[2]);
                break;
            default:
                btn_unfocus = btn[0];
                setFocus(btn_unfocus,btn[0]);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        String weightRestore = sh.getString("plOne", String.valueOf(0));
        String heightRestore = sh.getString("plTwo", String.valueOf(0));
        playerOne.setText(weightRestore);
        playerTwo.setText(heightRestore);
        switch (sh.getString("limit", String.valueOf(0))) {
            case "7":
                btn_unfocus = btn[3];
                setFocus(btn_unfocus,btn[3]);
                break;
            case "3":
                btn_unfocus = btn[1];
                setFocus(btn_unfocus,btn[1]);
                break;
            case "5":
                btn_unfocus = btn[2];
                setFocus(btn_unfocus,btn[2]);
                break;
            default:
                btn_unfocus = btn[0];
                setFocus(btn_unfocus,btn[0]);
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putString("plOne", String.valueOf(playerOne.getText()));
        myEdit.putString("plTwo", String.valueOf(playerTwo.getText()));
        myEdit.putString("limit", focusedButton);
        myEdit.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putString("plOne", String.valueOf(playerOne.getText()));
        myEdit.putString("plTwo", String.valueOf(playerTwo.getText()));
        myEdit.putString("limit", focusedButton);
        myEdit.apply();
    }
}