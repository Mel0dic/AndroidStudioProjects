package com.bgrummitt.compscisimpleapp.UI;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bgrummitt.compscisimpleapp.Other.Answers;
import com.bgrummitt.compscisimpleapp.Other.TestAdapter;
import com.bgrummitt.compscisimpleapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mAnswersTextView;
    private Spinner mDropDownBox;
    private Answers answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAnswersTextView = findViewById(R.id.outputTextViewBox);
        mAnswersTextView.setMovementMethod(new ScrollingMovementMethod());

        mDropDownBox = findViewById(R.id.questionsDropDownBox);
        setDropDownQuestions(getQuestions());

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

        TestAdapter mDbHelper = new TestAdapter(this);
        mDbHelper.createDatabase();
        mDbHelper.open();

        Cursor testdata = mDbHelper.getTestData();

        Log.d(TAG, testdata.getInt(0) + "");

        mDbHelper.close();

        answers = new Answers(this);

        Log.d(TAG, answers.getTime());
        Log.d(TAG, answers.getDate());
    }

    public void setDropDownQuestions(String[] questions){
        ArrayAdapter<String> gameKindArray = new ArrayAdapter<>(MainActivity.this, R.layout.spinner_item, questions);
        gameKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDropDownBox.setAdapter(gameKindArray);
    }

    public String[] getQuestions(){
        String[] array = {"Hello", "Goodbye", "Something Else"};
        return array;
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
    }

}
