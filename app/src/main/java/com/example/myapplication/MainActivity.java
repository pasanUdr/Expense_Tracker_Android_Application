package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import  androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<Trans> transList;
    private Button addBtn, catBtn;
    private DbHelper dbHelper;
    private Context context = this;
    private TextView incSum, expSum, currentBalance;


    @SuppressLint("MissingInflatedId")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(context);

        //setting value's decimal format
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

        //showing the total incomes
        incSum = findViewById(R.id.incSum);
        double sumInc = Double.parseDouble(dbHelper.sumInc());

        String formattedIncSum = decimalFormat.format(sumInc);
        incSum.setText(formattedIncSum);

        //showing the total expenses
        expSum = findViewById(R.id.expSum);
        double sumExp = Double.parseDouble(dbHelper.sumExp());

        String formattedExpSum = decimalFormat.format(sumExp);
        expSum.setText(formattedExpSum);

        //showing the balance
        currentBalance = findViewById(R.id.curBal);
        double balance = dbHelper.curBal();

        String formattedBalance = decimalFormat.format(balance);
        currentBalance.setText(formattedBalance);

        if (balance < 0) {
            currentBalance.setTextColor(getColorStateList(R.color.red));
        }
        else {
            currentBalance.setTextColor(getColorStateList(R.color.lightGreen));
        }


        listView = findViewById(R.id.transListView);        //setting trans list view
        transList = new ArrayList<>();      //allocating a memory for the transaction
        transList = dbHelper.getAllTrans();     //call getAllTrans() method in the DbHelper class object


//        TextView tran = findViewById(R.id.catSum);
//
//        if (Integer.parseInt(trans.getTranAmount()) > 0 ) {
//            tran.setTextColor(getColorStateList(R.color.green));
//        }
//        else {
//            tran.setTextColor(getColorStateList(R.color.red));
//        }

        Collections.sort(transList, new Comparator<Trans>() {       //arranging the list view in a descending order
            @Override
            public int compare(Trans trans, Trans trans2) {
                return trans2.getDate().compareTo(trans.getDate());
            }
        });





        //testing array
//        String[] trans = new String[] {"Transaction","February","March","April"};
//        ListAdapter listAdapter = new ArrayAdapter<String>(context, R.layout.single_dropcategory, transList); //adapting the array. (set wenna one block ekath set krnw "list_item" kiyna ek, array name ekai)
//        listView.setAdapter(listAdapter);

        //setting single raw design layout into transAdapter with transList list of values
        //passing the values into the TransAdaptor constructor
        TransAdapter transAdapter = new TransAdapter(context, R.layout.single_transaction, transList);

        listView.setAdapter(transAdapter);

        //list view click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Trans trans = transList.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(trans.getDesc());
                builder.setMessage("What you want to do with this?");
                builder.setNeutralButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                        builder1.setTitle("Are you sure to delete?");
                        builder1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int tranId = Integer.parseInt(trans.getId());
                                dbHelper.deleteTrans(tranId);
                                startActivity(new Intent(context, MainActivity.class));

                            }
                        });
                        builder1.show();
                    }
                });
                builder.show();
            }
        });

        //add btn
        addBtn = findViewById(R.id.addbtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, AddnewActivity.class);
                startActivity(intent1);
            }
        });

        //category button
        catBtn = findViewById(R.id.catbtn);

        catBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, CatList.class);
                startActivity(intent2);
            }
        });

    }

}