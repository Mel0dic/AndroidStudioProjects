package com.bgrummitt.compscisimpleapp.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.bgrummitt.compscisimpleapp.Other.Answers;
import com.bgrummitt.compscisimpleapp.Other.TestAdapter;
import com.bgrummitt.compscisimpleapp.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mAnswersTextView;
    private Spinner mDropDownBox;
    private Answers answers;
    private TestAdapter mDbHelper;

    //On the apps creation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set the layout to activity_main xml file
        setContentView(R.layout.activity_main);

        //Create new answers object
        answers = new Answers(this);

        //Set the answer text view to the one in the activity_main.xml file
        mAnswersTextView = findViewById(R.id.outputTextViewBox);
        //Set the text view to a scrollable text view.
        mAnswersTextView.setMovementMethod(new ScrollingMovementMethod());

        //Set the drop down box to the spinner in the xml file
        mDropDownBox = findViewById(R.id.questionsDropDownBox);

        //Set the button to the enter button
        Button sendQuestionButton = findViewById(R.id.sendQuestionButton);

        //Set an on click listener to call question asked with the selected question as the argument
        sendQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionAsked(mDropDownBox.getSelectedItem().toString());
            }
        });

        //Set the button for clear text
        Button clearTextArea = findViewById(R.id.clearAreaButton);

        //Add an onclick listener to set the text in the Answers text view to ""
        clearTextArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnswersTextView.setText("");
            }
        });

        //Add a welcome message
        addAnswers("Welcome. Ask questions by selecting one from the drop down box and then press enter. Answers will appear below along with your question.");

        //Set the database to a new Test adapter, create the database and then open it
        mDbHelper = new TestAdapter(this);
        mDbHelper.createDatabase();
        mDbHelper.open();

        //Get the questions from the db and set the spinners questions to them
        String[] stringArrTemp = mDbHelper.getQuestions();
        setDropDownQuestions(stringArrTemp);
    }

    public void setDropDownQuestions(String[] questions){
        //Using an array adapter set the spinner to have the spinner_item look and the questions from the argument
        ArrayAdapter<String> gameKindArray = new ArrayAdapter<>(MainActivity.this, R.layout.spinner_item, questions);
        //Set the selection drop down to be the android simple_spinner_dropdown_item layout
        gameKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Set the spinners adapter
        mDropDownBox.setAdapter(gameKindArray);
    }

    public void addAnswers(String text){
        //If the text box is empty just set the text else set the text to what's already in the text box then the new text on a new line
        if(mAnswersTextView.getText().toString().isEmpty()){
            mAnswersTextView.setText(text);
        }else {
            mAnswersTextView.setText(String.format("%s\n%s", mAnswersTextView.getText(), text));
        }
    }

    //When a question is asked print the question to the text box then the answer from QuestionAsked passing the number and the answer template
    public void questionAsked(String question){
        addAnswers(question);
        addAnswers(answers.QuestionAsked(mDbHelper.getNumber(question), mDbHelper.getAnswer(question)));
    }

    //On pause close the database.
    @Override
    protected void onPause() {
        super.onPause();
        mDbHelper.close();
    }

    //On resume open the database again
    @Override
    protected void onResume() {
        super.onResume();
        mDbHelper.open();
    }

}
