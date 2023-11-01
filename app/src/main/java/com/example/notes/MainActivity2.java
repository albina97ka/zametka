package com.example.notes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;

public class MainActivity2 extends AppCompatActivity {
    Notes notes;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        notes = (Notes) getIntent().getSerializableExtra("item");
        position = getIntent().getIntExtra("position", 0);

        EditText editText = findViewById(R.id.etxt1);
        EditText editText2 = findViewById(R.id.etxt2);

        editText.setText(notes.getTitle());
        editText2.setText(notes.getTxt());

        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                notes.setTxt(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                notes.setTitle(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void onBackPressed(){
        Intent intent = new Intent();
        intent.putExtra("newItem", notes);
        intent.putExtra("position1", position);
        setResult(RESULT_OK, intent);
        finish();
    }
}