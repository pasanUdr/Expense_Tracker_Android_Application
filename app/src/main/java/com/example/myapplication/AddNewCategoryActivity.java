package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewcategory);

        Button button = findViewById(R.id.saveCatBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTextCat = findViewById(R.id.editCat);

                String catName = editTextCat.getText().toString();

                Category category = new Category(catName);
                DbHelper dbHelper = new DbHelper(getApplicationContext());
                dbHelper.addCat(category);

                Toast.makeText(AddNewCategoryActivity.this, "Category Added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddNewCategoryActivity.this, CatList.class);
                startActivity(intent);

            }
        });
    }
}