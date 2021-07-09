package com.ioatzi.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN) ;
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        Button startGameButton = findViewById(R.id.startGameButton);
        Button helpButton = findViewById(R.id.helpButton);

        startGameButton.setOnClickListener(v -> openStartGame());
        
        helpButton.setOnClickListener(v -> openRules());
    }

    private void openRules() {
        Intent intent = new Intent(MainActivity.this, RulesActivity.class);
        startActivity(intent);
    }

    private void openStartGame() {
        Intent intent = new Intent(MainActivity.this, StartGameActivity.class);
        startActivity(intent);
    }
}