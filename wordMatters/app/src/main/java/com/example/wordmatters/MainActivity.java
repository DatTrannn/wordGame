package com.example.wordmatters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private int presCounter = 0;
    private final int maxPresCounter = 4;
    private String[] keys = {"R", "I", "B", "D", "X"};
    private final String textAnswer = "BIRD";
    TextView textScreen, textQuestion, textTitle, speed;
    Animation smallbigforth;
    CountDownTimer t;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);

        smallbigforth = AnimationUtils.loadAnimation(this, R.anim.smallbigforth);

        shuffleArray(keys);

        for (String key : keys) {
            addView(findViewById(R.id.layoutParent), key, findViewById(R.id.editText));
        }
        speed = findViewById(R.id.speed);
        int[] cnt = {0};

        t = new CountDownTimer(Long.MAX_VALUE, 1000) {

            @SuppressLint("DefaultLocale")
            @Override
            public void onTick(long millisUntilFinished) {
                cnt[0]++;

                int millis = cnt[0];
                score = millis;
                int seconds = (int) (millis / 60);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                millis = millis % 60;

                speed.setText(String.format("%d:%02d:%02d", minutes, seconds, millis));
            }

            @Override
            public void onFinish() {

            }
        };
        t.start();
    }

    /**
     * Add character boxes
     **/
    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    private void addView(LinearLayout viewParent, String text, EditText editText) {
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        linearLayoutParams.rightMargin = 30;
        TextView textView = new TextView(this);

        textView.setLayoutParams(linearLayoutParams);
        textView.setBackground(this.getResources().getDrawable(R.drawable.bgpink));
        textView.setTextColor(this.getResources().getColor(R.color.purple));
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setClickable(true);
        textView.setFocusable(true);
        textView.setTextSize(32);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/FredokaOne-Regular.ttf");

        textQuestion = findViewById(R.id.textQuestion);
        textScreen = findViewById(R.id.textScreen);
        textTitle = findViewById(R.id.textTitle);

        textQuestion.setTypeface(typeface);
        textScreen.setTypeface(typeface);
        textTitle.setTypeface(typeface);
        editText.setTypeface(typeface);
        textView.setTypeface(typeface);

        textView.setOnClickListener(view -> {
            if (presCounter < maxPresCounter) {
//                if (presCounter == 0)
//                    editText.setText("");

                editText.setText(editText.getText().toString() + text);
                textView.startAnimation(smallbigforth);
                textView.animate().alpha(0).setDuration(300);
                presCounter++;

                if (presCounter == maxPresCounter)
                    doValidate();
            }
        });

        viewParent.addView(textView);
    }

    /**
     * Check whether user has correct answer
     **/
    private void doValidate() {
        presCounter = 0;
        EditText editText = findViewById(R.id.editText);
        LinearLayout linearLayout = findViewById(R.id.layoutParent);
        if (editText.getText().toString().equals(textAnswer)) {
            // pops up "Correct" text when user inputs incorrect answer
            Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_SHORT).show();
            Intent a = new Intent(MainActivity.this, CheckScore.class);
            startActivity(a);
            t.cancel();   // stop time counter

            SharedPreferences scorePrefs = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
            int highScore = scorePrefs.getInt("HIGH_SCORE", 0);

            SharedPreferences.Editor editor = scorePrefs.edit();
            if (score < highScore || highScore == 0) {
                editor.putInt("HIGH_SCORE", score);
            }
            editor.putInt("YOUR_SCORE", score);
            editor.apply();
        } else {
            // pops up "Wrong" text when user inputs incorrect answer
            Toast.makeText(MainActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
        }
        editText.setText("");
        shuffleArray(keys);
        linearLayout.removeAllViews();
        for (String key : keys) {
            addView(linearLayout, key, editText);
        }
    }

    /**
     * Shuffle the character box randomly
     **/
    private void shuffleArray(String[] array) {
        for (int i = 0; i < array.length; i++) {
            int index = (int) (Math.random() * (array.length - 1));
            String tmp = array[index];
            array[index] = array[i];
            array[i] = tmp;
        }
    }


}