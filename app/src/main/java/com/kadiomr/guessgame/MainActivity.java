package com.kadiomr.guessgame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    void WipeAndInitialize(DbManager db){
        db.addWord(new Word("FISH"));
        db.addWord(new Word("KING"));
        db.addWord(new Word("TIME"));
        db.addWord(new Word("STAR"));
        db.addWord(new Word("FOOT"));
        db.addWord(new Word("BALL"));
        db.addWord(new Word("WORK"));
        db.addWord(new Word("GOLF"));
        db.addWord(new Word("FIRE"));
        db.addWord(new Word("CITY"));
    }

    DbManager wordsDB;
    int answered = 0;
    List<Word> words;
    Word currentWord;
    CharSequence correct = "Correct Answer !!";
    CharSequence tryAgain = "Try Again!";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordsDB = new DbManager(this);
        // getAllWords
        words = wordsDB.getAllWords();
        if(words.size()==0){
            this.WipeAndInitialize(wordsDB);
            words = wordsDB.getAllWords();
        }
        //INITILIAZE SCOREBOARD
        int numberoflines = words.size();
        words.forEach((word) -> {
            if (word.getAnswered() == 1) {
                answered++;
            }
        });
        final TextView scoreboard = (TextView) findViewById(R.id.scoreboard);
        final TextView firstChar = (TextView) findViewById(R.id.firstChar);
        final EditText secondChar = (EditText) findViewById(R.id.secondChar);
        final EditText thirdChar = (EditText) findViewById(R.id.thirdChar);
        final TextView fourthChar = (TextView) findViewById(R.id.fourthChar);
        final Button submitButton = (Button) findViewById(R.id.submitButton);
        scoreboard.setText(answered + "/" + numberoflines);
        if(answered==words.size()){
            firstChar.setText("D");
            secondChar.setText("O");
            thirdChar.setText("N");
            fourthChar.setText("E");
            secondChar.setEnabled(false);
            thirdChar.setEnabled(false);
            submitButton.setEnabled(false);
        }
        else{
            currentWord = words.get(answered);
            firstChar.setText(String.valueOf(currentWord.getWord().charAt(0)));
            fourthChar.setText(String.valueOf(currentWord.getWord().charAt(3)));
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast correctToast = Toast.makeText(context, correct, duration);
                    Toast wrongToast = Toast.makeText(context, tryAgain, duration);
                    if(secondChar.getText().toString().equalsIgnoreCase(String.valueOf(currentWord.getWord().charAt(1)))&&
                            thirdChar.getText().toString().equalsIgnoreCase(String.valueOf(currentWord.getWord().charAt(2)))){
                        correctToast.show();
                        wordsDB.setAnswered(currentWord.getId());
                        answered++;
                        scoreboard.setText(answered + "/" + numberoflines);
                        if(answered<words.size()){
                            currentWord = words.get(answered);
                            firstChar.setText(String.valueOf(currentWord.getWord().charAt(0)));
                            fourthChar.setText(String.valueOf(currentWord.getWord().charAt(3)));
                            secondChar.setText("");
                            thirdChar.setText("");
                            secondChar.requestFocus();
                        }
                        else{
                            firstChar.setText("D");
                            secondChar.setText("O");
                            thirdChar.setText("N");
                            fourthChar.setText("E");
                            secondChar.setEnabled(false);
                            thirdChar.setEnabled(false);
                            submitButton.setEnabled(false);
                        }

                    }
                    else{
                        wrongToast.show();
                        secondChar.setText("");
                        thirdChar.setText("");
                        secondChar.requestFocus();
                    }
                }
            });
        }
    }
}



//Fish
//KING
//TIME
//Star
//FOOT
//Ball
//Work
//GOlf
//Fire
//City

/*wordsDB.addWord(new Word("FISH"));
        wordsDB.addWord(new Word("KING"));
        wordsDB.addWord(new Word("TIME"));
        wordsDB.addWord(new Word("STAR"));
        wordsDB.addWord(new Word("FOOT"));
        wordsDB.addWord(new Word("BALL"));
        wordsDB.addWord(new Word("WORK"));
        wordsDB.addWord(new Word("GOLF"));
        wordsDB.addWord(new Word("FIRE"));
        wordsDB.addWord(new Word("CITY"));
 */