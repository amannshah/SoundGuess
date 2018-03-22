package com.aman.soundguess;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndActivity extends Activity implements View.OnClickListener{

    //The score is sent by the main activity to this activity
    //The score is displayed on the screen.
    MediaPlayer mediaPlayer;
    TextView text;
    int score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        Intent i = getIntent();
        score = i.getIntExtra("Score",0);

        text = findViewById(R.id.textView6);
        text.setText(Integer.toString(score));

        if (score == 25) {
            mediaPlayer = MediaPlayer.create(this, R.raw.wo_sikandar);
            mediaPlayer.start();
        }

        Button playAgain = findViewById(R.id.playAgain);
        playAgain.setOnClickListener(this);
    }

        //When the play again button is clicked this method controls transfers back to main activity and restarts the game
        @Override
        public void onClick (View view){
            Intent i = new Intent(EndActivity.this, MainActivity.class);
            if (score == 25) {
                mediaPlayer.stop();
            }
            startActivity(i);
        }
}
