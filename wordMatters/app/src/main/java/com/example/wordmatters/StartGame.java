package com.example.wordmatters;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class StartGame extends AppCompatActivity {

    TextView textScreen, textTitle, textBtn;
    ImageView bigboss;
    Animation smalltobig, smallbigforth;

    @SuppressLint({"CutPasteId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/FredokaOne-Regular.ttf");

        textScreen = (TextView) findViewById(R.id.textScreen);
        textTitle = (TextView)findViewById(R.id.textTitle);
        textBtn = (TextView)findViewById(R.id.textBtn);

        bigboss = findViewById(R.id.bigboss);
        bigboss.startAnimation(smalltobig);

        textScreen.setTypeface(typeface);
        textTitle.setTypeface(typeface);
        textBtn.setTypeface(typeface);
        smallbigforth = AnimationUtils.loadAnimation(this, R.anim.smallbigforth);

        Button btn = findViewById(R.id.textBtn);

        btn.setOnClickListener(view -> {
                startActivity(new Intent(StartGame.this, MainActivity.class));
                btn.startAnimation(smallbigforth);
        });
        SharedPreferences scorePrefs = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);

        // get high score
        int highScore = scorePrefs.getInt("HIGH_SCORE", 0);
        textTitle.setText("Best Score: " + highScore + "s");

        Button settingBtn = findViewById(R.id.settingBtn);
        settingBtn.setOnClickListener(view -> startActivity(new Intent(StartGame.this, Setting.class)));
    }

}