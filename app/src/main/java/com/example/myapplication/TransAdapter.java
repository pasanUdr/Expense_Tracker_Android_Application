package com.example.myapplication;

import android.content.Context;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.List;

public class TransAdapter extends ArrayAdapter<Trans> {

    private Context context;
    private int transResource;
    private List<Trans> transList;

    public TransAdapter(@NonNull Context context, int transResource, @NonNull List<Trans> transList ) {
        super(context, transResource, transList);
        this.context = context;
        this.transResource =  transResource;
        this.transList = transList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View row1 = inflater.inflate(transResource, parent, false);

        //set components in single_transaction layout
        TextView transDesc = row1.findViewById(R.id.transDesc);
        TextView transDate = row1.findViewById(R.id.transDate);
        TextView transAmount = row1.findViewById(R.id.transAmount);

        Trans trans = transList.get(position);

        transDesc.setText(trans.getDesc()); //set description to which is in the transaction table
        transDate.setText(trans.getDate()); //set date from the transaction table
        transAmount.setText(trans.getTranAmount());

        Income income = new Income();
        Expense expense = new Expense();
        DbHelper dbHelper = new DbHelper(context);


//        String queryInc = "SELECT t.Transaction_ID, t.Description, t.Transaction_Date, i.IncAmount\n" +
//                "FROM Transactions t\n" +
//                "JOIN Incomes i ON t.Transaction_ID = i.Transaction_ID;";
//        Cursor cursorInc = dbHelper.getReadableDatabase().rawQuery(queryInc,null);
//
//        if ( Double.parseDouble(cursorInc.getString(3)) )  {
//            transAmount.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.lightGreen));
//        }
//        else if ( trans.getId() == expense.getTransID())  {
//            transAmount.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.red));
//        }
//        else {
//            transAmount.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.lightBlue));
//        }

        return row1;
    }
}
