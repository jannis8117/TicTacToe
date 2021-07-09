package com.ioatzi.tictactoe;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN) ;
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_rules);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());


        TextView rulesTextView = findViewById(R.id.rulesTextView);

        String para = "Die beiden Spieler*innen setzen abwechselnd ihr Zeichen (also Kreuze und Kreise) in die freien Kästchen des Spielfelds. Ziel ist es, das eigene Zeichen drei Mal in einer Zeile, in einer Spalte oder in einer Diagonale zu platzieren. Wem das zuerst gelingt, hat gewonnen.";
        rulesTextView.setText(para);
        rulesTextView.setMovementMethod(new ScrollingMovementMethod());

    }
}