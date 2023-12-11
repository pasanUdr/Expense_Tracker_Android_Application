package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddNewIncomeActivity extends AppCompatActivity {

    private Spinner catSpin;
    private List<Category> catList;
    private Context context;
    private Button addIncBtn;
    private String catId;
    private EditText incDesc, incAmount;
    private DbHelper dbHelper;
    private String catTitle;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewincome);

        context = this;
        catSpin = findViewById(R.id.catSpinAddInc);

        dbHelper = new DbHelper(context);
        catList = new ArrayList<>();
        catList = dbHelper.getAllCat();

        //testing array
//        String[] trans = new String[] {"Transaction","February","March","April"};
        ListAdapter listAdapter = new ArrayAdapter<>(context, R.layout.single_dropcategory, catList); //adapting the array. (set wenna one block ekath set krnw "list_item" kiyna ek, array name ekai)
//        listView.setAdapter(listAdapter);

//        CatAdapter adapter = new CatAdapter(context,R.layout.single_dropcategory, catList);    ///changed
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        catSpin.setAdapter(adapter);

        catSpin.setAdapter((SpinnerAdapter) listAdapter);

        catSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                Category selectedCat = (Category) adapterView.getItemAtPosition(position);

//                        selectedCat = (Category) adapterView.getItemAtPosition(position);
                catId = selectedCat.getId();

                System.out.println(catId);

                catTitle = selectedCat.getCatName();

                Income income = new Income();
                income.setCatID(catId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
//                Toast.makeText(context, "Select a category for your transaction", Toast.LENGTH_SHORT).show();
            }
        });

        //setting button to add values into tables
        addIncBtn = findViewById(R.id.addIncBtn);
        addIncBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                incDesc = findViewById(R.id.incDesc);
                incAmount = findViewById(R.id.incAmount);

                String desc = incDesc.getText().toString();

                long currentTimeMillis = System.currentTimeMillis();
                Date currentDate = new Date(currentTimeMillis);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //getiing system current date & time in a readable format
                String formattedDate = dateFormat.format(currentDate);

                String amount = incAmount.getText().toString();
                String CatID = catId;
                Trans trans = new Trans(desc, formattedDate);
                Income income = new Income(CatID, amount);

                dbHelper = new DbHelper(getApplicationContext());
                dbHelper.addIncTransaction(trans, income);

                Toast.makeText(getApplicationContext(), "Transaction Added", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AddNewIncomeActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }
}