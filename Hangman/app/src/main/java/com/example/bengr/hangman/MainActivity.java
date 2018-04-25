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


public class MainActivity extends AppCompatActivity {

    private String wordToGuess;
    private List<Character> wrongLetters = new ArrayList<>();
    private int wordLength; //Set elsewhere
    private char[] guessedLetter;
    private Button enterTextButton;
    private EditText guessEntered;
    private int wordComplete = 0;
    private TextView incorrectLetters;
    private int wrongGuessesMade = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Initialize the instance and load the main activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Have the word be 5 characters long
        wordLength = 5;
        //Initialise the guessed letter array with length wordLength
        guessedLetter = new char[wordLength];
        //Loop through guessedLetter and set each element to _
        for(int i = 0; i < wordLength; i++){
            guessedLetter[i] = '_';
        }
        //Get a random word
        wordToGuess = getRandomWord();
        //TODO Remove debug print out word that is chosen
        System.out.println(wordToGuess);
        //Set the label at the bottom of the screen to be _ of length of word and with spaces
        //between _'s
        setGuessedLetterLabel();

        //Set the buttons and text view to id.
        enterTextButton = findViewById(R.id.enterGuess);
        guessEntered = findViewById(R.id.inputGuess);
        incorrectLetters = findViewById(R.id.wrongLettersGuessed);

        //Have the button be a listener to call buttonClicked when button pressed
        enterTextButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                buttonClicked();
            }
        });

    }

    //Function to getRandom word opens text file in assets folder
    private String getRandomWord(){
        ArrayList<String> dictionary = new ArrayList<String>();

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

        return dictionary.get((int)(Math.random() * dictionary.size()));
    }

    //Function to set label of correct guessed letter and yet to be guessed letters
    //as _'s
    private void setGuessedLetterLabel(){
        TextView lettersGuessed = findViewById(R.id.guessedLetters);
        String guessedString = "";
        for(int i = 0; i < wordLength; i++){
            //If at last letter in word set final letter to not have a character
            if(i == wordLength-1) {
                guessedString += String.format("%c", guessedLetter[i]);
            }else {
                guessedString += String.format("%c ", guessedLetter[i]);
            }
        }
        //Set the text to the guessedString
        lettersGuessed.setText(guessedString);
    }

    //Function when button to enter guess is clicked
    public void buttonClicked(){
        //Get the string from the text area and turn it to a lower case string
        String guess = guessEntered.getText().toString().toLowerCase();
        //Take the first char in word and take that as the guess
        char singleLetterGuess = guess.charAt(0);
        //Guess function with letter
        guess(singleLetterGuess);
        //Set the textArea to be empty
        guessEntered.setText("");

        //If out of guesses set content to lose game activity
        //Else if word has been correctly guessed
        if(wrongGuessesMade == 4){
            setContentView(R.layout.activity_losegame);
            TextView wordReveal = findViewById(R.id.revealTV);
            Button restartButton = findViewById(R.id.restartButton);
            //Reveal the word
            wordReveal.setText(String.format("Unlucky the word was: %s", wordToGuess));
            //Add onClickListener to change activity to main and recreate the activity.
            restartButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){ setContentView(R.layout.activity_main);recreate();}
            });
        }else if(wordComplete == wordLength){
            recreate();
        }
    }

    //Function guess pass value guess char.
    public void guess(char guess){
        //If there is an index of the the letter loop through and set the guessed letters to the letter instead of _
        if(wordToGuess.indexOf(guess) >= 0) {
			for(int i = 0; i < wordLength; i++) {
				if(guess == wordToGuess.charAt(i)) {
					guessedLetter[i] = guess;
					//Increase word complete
					wordComplete++;
				}
			}
			//Update the label to include guessed letters
			setGuessedLetterLabel();
        }else{
            //Add letter to letters not in the word
            wrongLetters.add(guess);
            //Update the label with wrong guesses
            wrongLettersUpdate();
            //Increase wrong guesses made
            wrongGuessesMade++;
        }
    }

    //Update the wrong letters label
    public void wrongLettersUpdate(){
        String theGuesses = "You have guessed: ";
        int count = 0;
        for(char i : wrongLetters){
            if(count == 0) {
                theGuesses += String.format("%c", i);
                count++;
            }else{
                theGuesses += String.format(", %c", i);
            }
        }
        incorrectLetters.setText(theGuesses);
    }

}
