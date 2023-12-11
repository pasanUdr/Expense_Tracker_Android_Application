package com.example.myapplication;

import android.content.Context;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.List;

public class CatAdapter extends ArrayAdapter<Category> {

    private  Context context;
    private  int catResource;
    private List<Category> categoryList;

    public CatAdapter(Context context, int catResource, List<Category> categoryList) {
        super(context, catResource, categoryList);
        this.context = context;
        this.catResource = catResource;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View row = inflater.inflate(catResource, parent, false);    //converting the design block into java view object

        TextView catTitle = row.findViewById(R.id.catName);
        TextView catSum = row.findViewById(R.id.catSum);

        Category category = categoryList.get(position);      //categories categoryList [cat1, cat2.....]/ combine db & listview

        catTitle.setText(category.getCatName());
        String x = category.getCatSum();

        if (Double.parseDouble(x) > 0) {
            catSum.setTextColor(ContextCompat.getColor(context, R.color.green));
        }
        else if (Double.parseDouble(x) < 0) {
            catSum.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
        else {
            catSum.setTextColor(ContextCompat.getColor(context, R.color.lightBlue));
        }
        catSum.setText(x);

        return row;

    }
}
