package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

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

public class AddNewExpenseActivity extends AppCompatActivity {

    private Spinner catSpin;
    private DbHelper dbHelper;
    private Context context;
    private List<Category> catList1;
    private Button addExpBtn;
    private String catId;
    private EditText expDesc, expAmount;
    private String catTitle ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewexpense);

        context = this;

        catSpin = findViewById(R.id.catSpinAddExp);

        dbHelper = new DbHelper(context);
        catList1 = new ArrayList<>();
        catList1 = dbHelper.getAllCat();

//        CatAdapter adapter = new CatAdapter(getApplicationContext(), R.layout.single_dropcategory, catList);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        catSpin.setAdapter(adapter);

        ListAdapter adapter = new ArrayAdapter<>(context, R.layout.single_dropcategory, catList1 );
        catSpin.setAdapter((SpinnerAdapter) adapter);

        catSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Category selectedCat = (Category) adapterView.getItemAtPosition(position);

                catId = selectedCat.getId();
                System.out.println(catId);

                catTitle = selectedCat.getCatName();

                Expense expense = new Expense();
                expense.setCatID(catId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //setting button to add values into tables
        addExpBtn = findViewById(R.id.addExpBtn);
        addExpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                expDesc = findViewById(R.id.expDesc);
                expAmount = findViewById(R.id.expAmount);

                String desc = expDesc.getText().toString();

                long currentTimeMillis = System.currentTimeMillis(); //ok
                Date currentDate = new Date(currentTimeMillis);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = dateFormat.format(currentDate);

                float amount = Float.parseFloat(expAmount.getText().toString());
                String Cat_ID = catId;

                Trans trans = new Trans(desc, formattedDate);
                Expense expense = new Expense(Cat_ID, amount);

                dbHelper = new DbHelper(getApplicationContext());
                dbHelper.addExpTransaction(trans, expense); //ok

                Toast.makeText(getApplicationContext(), "Transaction Added", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AddNewExpenseActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}