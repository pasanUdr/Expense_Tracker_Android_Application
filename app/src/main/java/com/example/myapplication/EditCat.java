package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditCat extends AppCompatActivity {

    private TextView editCatTxt;
    private Button saveBtn;
    private EditText editCat;
    private DbHelper dbHelper;
    private Category category;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cat);

        saveBtn = findViewById(R.id.saveCatBtn);
        editCatTxt = findViewById(R.id.editCatTxt);
        editCat = findViewById(R.id.editCat);

        final String id = getIntent().getStringExtra("id");


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = editCat.getText().toString();

                category = new Category();
            }
        });
    }
}