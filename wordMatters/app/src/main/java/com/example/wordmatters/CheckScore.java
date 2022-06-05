package com.example.wordmatters;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CheckScore extends AppCompatActivity {

    TextView textScreen, textTitle, textBtn, textBigTitle, textBack;
    ImageView bigboss;
    Animation smalltobig, smallbigforth;

    @SuppressLint({"CutPasteId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score);

        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/FredokaOne-Regular.ttf");

        textScreen = findViewById(R.id.textScreen);
        textTitle = findViewById(R.id.textTitle);
        textBtn = findViewById(R.id.textBtn);
        textBigTitle = findViewById(R.id.textBigTitle);
        textBack = findViewById(R.id.textBack);

        bigboss = findViewById(R.id.bigboss);
        bigboss.startAnimation(smalltobig);

        textScreen.setTypeface(typeface);
        textTitle.setTypeface(typeface);
        textBtn.setTypeface(typeface);
        textBigTitle.setTypeface(typeface);
        textBack.setTypeface(typeface);

        smallbigforth = AnimationUtils.loadAnimation(this, R.anim.smallbigforth);
        Button btn = findViewById(R.id.textBtn);

        btn.setOnClickListener(view -> {
            startActivity(new Intent(CheckScore.this, MainActivity.class));
            btn.startAnimation(smallbigforth);
        });
        SharedPreferences scorePrefs = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);

        //get scores
        int score = scorePrefs.getInt("YOUR_SCORE", 0);

        int highScore = scorePrefs.getInt("HIGH_SCORE", 0);
        textBigTitle.setText("Best Score: " + highScore + "s");
        textTitle.setText("Your Score: " + score + "s");

        textBack.setOnClickListener(view -> startActivity(new Intent(CheckScore.this, StartGame.class)));

    }
}