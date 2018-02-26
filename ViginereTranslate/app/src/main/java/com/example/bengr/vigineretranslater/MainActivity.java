package com.example.bengr.vigineretranslater;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public viginereTranslate translater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        translater = new viginereTranslate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.action_menu, menu);

        MenuItem itemSwitch = menu.findItem(R.id.mainSwitch);

        itemSwitch.setActionView(R.layout.use_switch);

        final Switch sw = menu.findItem(R.id.mainSwitch).getActionView().findViewById(R.id.action_switch);

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(getBaseContext(), "Decrypt", Toast.LENGTH_SHORT).show();
                    setContentView(R.layout.activity_decrypt);
                }else{
                    Toast.makeText(getBaseContext(), "Decrypt", Toast.LENGTH_SHORT).show();
                    setContentView(R.layout.activity_main);
                }
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void onEncryptClick(View view){
        EditText MainTextBox = findViewById(R.id.MainTextBox);
        EditText CipherWord = findViewById(R.id.cipherWord);
        TextView InformationOutput = findViewById(R.id.InformationOutput);

        String toEncrypt = MainTextBox.getText().toString();
        String encryptionKey = CipherWord.getText().toString();

        if(encryptionKey.matches("")) {
            Toast.makeText(this, "You did not enter an encryption key", Toast.LENGTH_SHORT).show();
        }else if(toEncrypt.matches("")){
            Toast.makeText(this, "You did not enter a phrase to encrypt", Toast.LENGTH_SHORT).show();
        }else{
            InformationOutput.setText(translater.encrypt(toEncrypt, encryptionKey));
        }
    }

    public void onDecryptClick(View view){
        EditText MainTextBox = findViewById(R.id.MainTextBox);
        EditText CipherWord = findViewById(R.id.cipherWord);
        TextView InformationOutput = findViewById(R.id.InformationOutput);

        String toEncrypt = MainTextBox.getText().toString();
        String encryptionKey = CipherWord.getText().toString();

        if(encryptionKey.matches("")) {
            Toast.makeText(this, "You did not enter an encryption key", Toast.LENGTH_SHORT).show();
        }else if(toEncrypt.matches("")){
            Toast.makeText(this, "You did not enter a phrase to encrypt", Toast.LENGTH_SHORT).show();
        }else{
            InformationOutput.setText(translater.decrypt(toEncrypt, encryptionKey));
        }
    }

}

