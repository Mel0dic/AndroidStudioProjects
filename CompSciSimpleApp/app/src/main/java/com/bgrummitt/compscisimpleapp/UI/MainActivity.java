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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        answers = new Answers(this);

        mAnswersTextView = findViewById(R.id.outputTextViewBox);
        mAnswersTextView.setMovementMethod(new ScrollingMovementMethod());

        mDropDownBox = findViewById(R.id.questionsDropDownBox);

        Button sendQuestionButton = findViewById(R.id.sendQuestionButton);

        sendQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionAsked(mDropDownBox.getSelectedItem().toString());
            }
        });

        Button clearTextArea = findViewById(R.id.clearAreaButton);

        clearTextArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnswersTextView.setText("");
            }
        });

        addAnswers("Welcome to this thing I am not really sure what is but welcome anyway. Select you question and ask a question.");

        addAnswers("Answer here");

        mDbHelper = new TestAdapter(this);
        mDbHelper.createDatabase();
        mDbHelper.open();

        String[] stringArrTemp = mDbHelper.getQuestions();
        setDropDownQuestions(stringArrTemp);
    }

    public void setDropDownQuestions(String[] questions){
        ArrayAdapter<String> gameKindArray = new ArrayAdapter<>(MainActivity.this, R.layout.spinner_item, questions);
        gameKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDropDownBox.setAdapter(gameKindArray);
    }

    public void addAnswers(String text){
        if(mAnswersTextView.getText().toString().isEmpty()){
            mAnswersTextView.setText(text);
        }else {
            mAnswersTextView.setText(String.format("%s\n%s", mAnswersTextView.getText(), text));
        }
    }

    public void questionAsked(String question){
        addAnswers(question);
        addAnswers(answers.QuestionAsked(mDbHelper.getNumber(question), mDbHelper.getAnswer(question)));
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDbHelper.close();
    }
}
