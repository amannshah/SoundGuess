package com.aman.soundguess;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.CollapsibleActionView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class MainActivity extends Activity {

    ArrayList<Integer> id,copyId;
    Button c;
    Button playButton;
    CountDownTimer Count;
    int correctButton;
    MediaPlayer mediaPlayer;
    Button button0,button1,button2,button3;
    static int playerScore=0;
    int count = 0;
    TextView score,timerTextView;
    HashMap<Integer, String> correctAnswer = new HashMap<>();
    HashSet<Integer> h1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        count =0;
        h1=new HashSet<Integer>();
        playerScore=0;
        setContentView(R.layout.activity_main);
        score= findViewById(R.id.score);
        playButton=findViewById(R.id.playButton);
        timerTextView=findViewById(R.id.timerTextView);
        button0 = (Button)findViewById(R.id.button0);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button0.setVisibility(View.INVISIBLE);
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        id = new ArrayList<Integer>();

        /******* This code puts relates the ID of sounds to String containing the name of the movie
         corresponding to that sound in the hashmap */

        correctAnswer.put(R.raw.bahubali_2,"BAHUBALI2");
        correctAnswer.put(R.raw.galliyan_theme,"EK VILLIAN");
        correctAnswer.put(R.raw.khalnayak,"KHALNAYAK");
        correctAnswer.put(R.raw.kuch_kuch_hota_hai,"KUCH KUCH HOTA HAIN");
        correctAnswer.put(R.raw.soch_na_sake_female,"AIRLIFT");
        correctAnswer.put(R.raw.phir_bhi_tumko,"HALF GIRLFRIEND");
        correctAnswer.put(R.raw.bol_do_na_zara,"AZHAR");
        correctAnswer.put(R.raw.ashique2_heartbreak,"AASHIQUI 2");
        correctAnswer.put(R.raw.ramaiya_vastavaiya,"RAMAIYA VASTAVAIYA");
        correctAnswer.put(R.raw.jab_tak_ms_dhoni,"MS DHONI: THE UNTOLD STORY");

        /* This code save the ID's of all sounds in the arrayList*/

        for (int i =R.raw.ashique2_heartbreak,x=1;x<=10;i++,x++) {
            id.add(i);
        }
        copyId = (ArrayList<Integer>) id.clone();


    }

    /* This method is called whenever the user clicks on Play Sound Button*/
    /***** The code generates a question randomly and displays on the screen along with playing a sound byte*/
    /***** IT also sets the 4 buttons */

    public void play(View view) {

        button0.setClickable(true);
        button1.setClickable(true);
        button2.setClickable(true);
        button3.setClickable(true);
        playButton.setClickable(false);
        count++;
        button0.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        button0.setBackground(getDrawable(R.drawable.round_button));
        button1.setBackground(getDrawable(R.drawable.round_button));
        button2.setBackground(getDrawable(R.drawable.round_button));
        button3.setBackground(getDrawable(R.drawable.round_button));
        Random random = new Random();
        int checkSound=0;
        while (true) {
            checkSound = random.nextInt(id.size());
            if (!h1.contains(id.get(checkSound))) {
                h1.add(id.get(checkSound));
                break;
            }
            }


        copyId.remove(checkSound);
        correctButton = random.nextInt(4);
        switch (correctButton)
        {
            case 0: c=findViewById(R.id.button0);
                break;
            case 1: c=findViewById(R.id.button1);
                break;
            case 2: c=findViewById(R.id.button2);
                break;
            case 3: c=findViewById(R.id.button3);
                break;
        }
        ArrayList<String> ans = new ArrayList<>();

        //Here the 4 buttons are set with one as correct options and others as incorrect by using the ID of the sound track.

        for (int i=0;i<4;i++) {
            int j=0;
            if (i == correctButton) {
                ans.add(correctAnswer.get(id.get(checkSound)));
            }
            else {
                int incorrectAnswer = random.nextInt(copyId.size());
                ans.add(correctAnswer.get(copyId.get(incorrectAnswer)));
                j = copyId.get(incorrectAnswer);
                copyId.remove(new Integer(j));
            }
        }

        button0.setText(ans.get(0));
        button1.setText(ans.get(1));
        button2.setText(ans.get(2));
        button3.setText(ans.get(3));

        copyId = (ArrayList<Integer>) id.clone();

        mediaPlayer = MediaPlayer.create(this, id.get(checkSound));
        mediaPlayer.start();

        // A times or 10 seconds is initialized.
        // If the user answers the question the timer gets reinitialised.
        // Otherwise a message pops up displaying "Time's Up" and controls transfer to the next question

        Count = new CountDownTimer(10100,1000){
            @Override
            public void onTick(long l) {
                timerTextView.setText(String.valueOf(l / 1000) + "s");
            }

            @Override
            public void onFinish() {
                timerTextView.setText("0s");
                Toast.makeText(MainActivity.this, "Time UP !!", Toast.LENGTH_SHORT).show();
                c.setBackgroundColor(Color.GREEN);
                setConditions();
            }
        }.start();

    }

    public void setConditions() {
        button0.setClickable(false);
        button1.setClickable(false);
        button2.setClickable(false);
        button3.setClickable(false);
        playButton.setClickable(true);
        mediaPlayer.stop();
        score.setText("Score :"+playerScore);

        //If 5 questions are completed the control is transferred to the End Activity.

        if(count==5) {
            Intent i=new Intent(getApplicationContext(),EndActivity.class);
            i.putExtra("Score", playerScore);
            startActivity(i);
        }
    }

    /* Here it is evaluated whether the used has entered the correct answer or not and change the colour of the button accordingly.
     */

    public void checkAnswer(View view) {


        Count.cancel();
        Log.i("Correct Button", Integer.toString(correctButton));
        if ((view.getTag().toString()).equals(Integer.toString(correctButton))) {
            view.setBackgroundColor(Color.GREEN);
            playerScore+=5;
        } else {
            view.setBackgroundColor(Color.RED);
            c.setBackgroundColor(Color.GREEN);
        }
        setConditions();

    }
}
