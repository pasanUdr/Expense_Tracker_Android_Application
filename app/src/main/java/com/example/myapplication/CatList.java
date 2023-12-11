package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CatList extends AppCompatActivity {

    private Context context;
    private ListView listView;
    private List<Category> catList;
    private Button catAddBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_list);

        context = this;

        TextView count = findViewById(R.id.catCount);

        DbHelper dbHelper = new DbHelper(context);

        //get category count from table
        int catCount = dbHelper.catCount();

        count.setText("You have " + catCount + " Categories");

        //setting the category list view
        catList = new ArrayList<>(); //allocate a memory
        catList = dbHelper.catSumTotal(); //get all categories from table ***** ///changed method get all cat into cat sum total

        //setting add category btn
        catAddBtn = findViewById(R.id.catAddBtn);
        catAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatList.this, AddNewCategoryActivity.class);
                startActivity(intent);
            }
        });

        CatAdapter catAdapter = new CatAdapter(getApplicationContext(), R.layout.single_cat, catList);

        listView = findViewById(R.id.catListView); //setting list view initializing by ID
        listView.setAdapter(catAdapter); //setting the catAdapter into list view

        //setting a alert dialog box on selected item listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Category category = catList.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle(category.getCatName());

                builder.setMessage("What you want to do with this?");

                builder.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent editCatIntent = new Intent(CatList.this, EditCat.class);
                        startActivity(editCatIntent);
                    }
                });

                builder.setNeutralButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                        builder1.setTitle("Are you sure to delete?");
                        builder1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                int categoryId = Integer.parseInt(category.getId());
                                dbHelper.deleteCat(categoryId);

                                startActivity(new Intent(context, CatList.class));
                                Toast.makeText(CatList.this, "Category Deleted", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(context, CatList.class));
                            }
                        });
                        builder1.show();
                    }
                });

                builder.show();
            }
        });


    }
}