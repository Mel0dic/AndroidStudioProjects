package com.example.bengr.hangman;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private String wordToGuess;
    private List<String> wordList = new ArrayList<>();
    private Random rand = new Random();
    private ArrayList<String> dictionary;
    private int wordLength; //Set elsewhere
    private char[] guessedLetter;
    private Button enterTextButton;
    private EditText guessEntered;
    private int wordComplete = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordLength = 5;
        guessedLetter = new char[wordLength];
        for(int i = 0; i < wordLength; i++){
            guessedLetter[i] = '_';
        }
        wordToGuess = getRandomWord();
        System.out.println(wordToGuess);
        setGuessedLetterLabel();

        enterTextButton = findViewById(R.id.enterGuess);
        guessEntered = findViewById(R.id.inputGuess);

        enterTextButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                buttonClicked();
            }
        });

    }

    public String getRandomWord() {
        createDictionary();
        return dictionary.get((int)(Math.random() * dictionary.size()));
    }

    private void createDictionary(){
        dictionary = new ArrayList<String>();

        BufferedReader dict = null; //Holds the dictionary file
        AssetManager am = this.getAssets();

        try {
            //dictionary.txt should be in the assets folder.
            dict = new BufferedReader(new InputStreamReader(am.open("dictionary.txt")));

            String word;
            while((word = dict.readLine()) != null){
                if(word.length() == wordLength){
                    dictionary.add(word);
                }
            }

        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            dict.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (NullPointerException f){
            f.printStackTrace();
        }
    }

    private void setGuessedLetterLabel(){
        TextView lettersGuessed = findViewById(R.id.guessedLetters);
        String guessedString = "";
        for(int i = 0; i < wordLength; i++){
            if(i == wordLength-1) {
                guessedString += String.format("%c", guessedLetter[i]);
            }else {
                guessedString += String.format("%c ", guessedLetter[i]);
            }
        }
        lettersGuessed.setText(guessedString);
    }

    public void buttonClicked(){
        String guess = guessEntered.getText().toString();
        char singleLetterGuess = guess.charAt(0);
        guess(singleLetterGuess);
    }

    public void guess(char guess){
        if(wordToGuess.indexOf(guess) >= 0) {
			for(int i = 0; i < wordLength; i++) {
				if(guess == wordToGuess.charAt(i)) {
					guessedLetter[i] = guess;
					wordComplete++;
				}
			}
			setGuessedLetterLabel();
        }
    }

}
