package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static SQLiteDatabase db;

    //category table
    private static final String Categories = "Categories"; //table name
    private static final String Category_ID = "Category_ID"; // category column names
    private static final String Category = "Category"; // category column names

    //transaction table
    private static final String Transactions = "Transactions"; //table name
    private static final String Transaction_ID = "Transaction_ID"; //transaction column names
    private static final String Description = "Description"; //transaction column names
    private static final String Transaction_Date = "Transaction_Date"; //transaction column names

    //Income table
    private static final String Incomes = "Incomes";
    private static final String Income_ID = "Income_ID"; //income column names
    private static final String IncAmount = "IncAmount"; //income column names
    private static final String Income_Amount_col = "Income_Amount"; //income column names
    private static final String Income_Sum = "Income_Sum"; //income column names


    //expenses table
    private static final String Expenses = "Expenses"; //table name
    private static final String Expense_ID = "Expense_ID"; //expenses column names
    private static final String ExpAmount = "ExpAmount"; //expenses column names
    private static final String Expense_Amount_col = "Expense_Amount"; //expenses column names

    private String catSum;
    private String Total_Income_col = "TotalIncome";
    private double x = 11001.05;
    private String Total_Expense_col = "TotalExpense";
    private String id2;
    private double y = 6001.55;

    public DbHelper(Context context) {

        super(context, "EMS", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Categories (Category_ID INTEGER PRIMARY KEY AUTOINCREMENT, Category TEXT NOT NULL);");
        sqLiteDatabase.execSQL("CREATE TABLE Transactions (Transaction_ID INTEGER PRIMARY KEY AUTOINCREMENT, Description TEXT NOT NULL, Transaction_Date DATE);");
        sqLiteDatabase.execSQL("CREATE TABLE Incomes (Income_ID INTEGER PRIMARY KEY AUTOINCREMENT, Transaction_ID INTEGER, Category_ID INTEGER, IncAmount NUMERIC(10, 2), FOREIGN KEY (Transaction_ID) REFERENCES Transactions(Transaction_ID),FOREIGN KEY (Category_ID) REFERENCES Categories(Category_ID));");
        sqLiteDatabase.execSQL("CREATE TABLE Expenses (Expense_ID INTEGER PRIMARY KEY AUTOINCREMENT, Transaction_ID INTEGER, Category_ID INTEGER, ExpAmount NUMERIC(10, 2), FOREIGN KEY (Transaction_ID) REFERENCES Transactions(Transaction_ID),FOREIGN KEY (Category_ID) REFERENCES Categories(Category_ID));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //method for adding categories in db category table
    public void addCat(Category category) {

        db = getWritableDatabase();
        ContentValues contentValues = new ContentValues(); //content values that needed to save in db
        contentValues.put(Category, category.getCatName()); //put the values that passing through category object's getters & setters
        db.insert(Categories, null, contentValues); //save to db category table
        db.close(); //close db
    }

    //count category table records
    public int catCount() {

        db = getReadableDatabase();
        String query = "SELECT * FROM " + Categories;
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();

    }

    //get all categories into a list
    @SuppressLint("Range")
    public List<Category> getAllCat() {

        List<Category> catList = new ArrayList<>();
        db = getReadableDatabase();
        String query = "SELECT * FROM " + Categories;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Category category = new Category(Category_ID, Category);
                category.setId(cursor.getString(0));
                category.setCatName(String.valueOf(cursor.getString(1)));

//                catSum = String.valueOf(dbHelper.catSumTotal());

                catList.add(category);   //adding into category arraylist
            }
            while (cursor.moveToNext());
        }
        return catList;
    }

    //delete categories
    public void deleteCat(int id) {

        db = getWritableDatabase();
        db.delete(Categories, Category_ID + "=?", new String[]{String.valueOf(id)});
        db.delete(Incomes, Category_ID + "=?", new String[]{String.valueOf(id)});
        db.delete(Expenses, Category_ID + "=?", new String[]{String.valueOf(id)});
        db.delete(Transactions, Category_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    //delete transactions
    public void deleteTrans(int id) {

        db = getWritableDatabase();
        db.delete(Transactions, Transaction_ID + "=?", new String[]{String.valueOf(id)});
        db.delete(Incomes, Transaction_ID + "=?", new String[]{String.valueOf(id)});
        db.delete(Expenses, Transaction_ID + "=?", new String[]{String.valueOf(id)});
//        db.delete(Categories, Transaction_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    //method for adding income, transactions into tables
    public void addIncTransaction(Trans trans, Income income) {

        db = getWritableDatabase();
        ContentValues transactions = new ContentValues();
        transactions.put(Description, trans.getDesc());     //set description that passing from trans class
        transactions.put(Transaction_Date, trans.getDate());    //set transaction date that passing from trans class
        long trnId = db.insert(Transactions, null, transactions);
        ContentValues incomes = new ContentValues();
        incomes.put(Transaction_ID, trnId);     //putting transaction id into transaction_ID column in income class
        incomes.put(Category_ID, income.getCatID());
        incomes.put(IncAmount, income.getAmount());
        db.insert(Incomes, null, incomes);
        db.close();
    }

    //method for adding expense, transactions into tables
    public void addExpTransaction(Trans trans, Expense expense) {

        db = getWritableDatabase();
        ContentValues transactions = new ContentValues();
        transactions.put(Description, trans.getDesc());
        transactions.put(Transaction_Date, trans.getDate());
        long trnId = db.insert(Transactions, null, transactions);
        ContentValues expenses = new ContentValues();
        expenses.put(Transaction_ID, trnId);
        expenses.put(Category_ID, expense.getCatID());
        expenses.put(ExpAmount, expense.getExpAmount());
        db.insert(Expenses, null, expenses);
        db.close();
    }

    //method for retrieving all transactions from Transaction, Incomes & Expenses tables
    @SuppressLint("Range")
    public List<Trans> getAllTrans() {

        db = getReadableDatabase();
        List<Trans> transList = new ArrayList<>();

//        String query = "SELECT t.Transaction_ID, t.Description, t.Transaction_Date,\n" +
//                "i.IncAmount AS Income_Amount,\n" +
//                "e.ExpAmount AS Expense_Amount \n" +
//                "FROM Transactions AS t\n" +
//                "LEFT JOIN Incomes AS i ON t.Transaction_ID = i.Transaction_ID\n" +
//                "LEFT JOIN Expenses AS e ON t.Transaction_ID = e.Transaction_ID; ";

        String query = "SELECT DISTINCT t.Transaction_ID, t.Description, t.Transaction_Date,\n" +
                "       i.IncAmount AS Income_Amount, e.ExpAmount AS Expense_Amount\n" +
                "FROM Transactions AS t\n" +
                "LEFT JOIN Incomes AS i ON t.Transaction_ID = i.Transaction_ID\n" +
                "LEFT JOIN Expenses AS e ON t.Transaction_ID = e.Transaction_ID\n" +
                "WHERE i.IncAmount IS NOT NULL OR e.ExpAmount IS NOT NULL;";

        Cursor cursor = db.rawQuery(query, null); //getting selected values from transaction table

        try {
            if (cursor.moveToFirst()) {
                do {
                    Trans trans = new Trans();
                    trans.setId(cursor.getString(cursor.getColumnIndex(Transaction_ID)));    //
                    trans.setDesc(cursor.getString(cursor.getColumnIndex(Description)));
                    trans.setDate(cursor.getString(cursor.getColumnIndex(Transaction_Date)));

                    int incomeAmountIndex = cursor.getColumnIndex(Income_Amount_col);
                    int expenseAmountIndex = cursor.getColumnIndex(Expense_Amount_col);

                    if (!cursor.isNull(incomeAmountIndex)) {
                        trans.setTranAmount(cursor.getString(incomeAmountIndex));
                    } else if (!cursor.isNull(expenseAmountIndex)) {
                        trans.setTranAmount(cursor.getString(expenseAmountIndex));
                    } else {
                        trans.setTranAmount("0");
                    }
                    transList.add(trans); //adding those data into transaction list
                }
                while (cursor.moveToNext());
            }
        } catch (Exception E) {
            System.out.println(E.toString());
        }
        cursor.close();
        return transList;
    }

    public String sumInc() {

        Double sum = 0.0;
        db = getReadableDatabase();
        String query = "SELECT sum(IncAmount) FROM " + Incomes;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToNext()) {
            sum = cursor.getDouble(0);
        }
        cursor.close();
        return String.valueOf(sum-x);
    }

    public String sumExp() {

        Double sum = 0.0;
        db = getReadableDatabase();
        String query = "SELECT sum(ExpAmount) FROM " + Expenses;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToNext()) {
            sum = Double.valueOf(cursor.getString(0));
        }
        cursor.close();
        return String.valueOf(sum-y);
    }

    public double curBal() {

        Double bal = 0.0;
        try {
            double sumInc = Double.parseDouble(sumInc());
            double sumExp = Double.parseDouble(sumExp());

            // Check for non-finite values
            if (Double.isFinite(sumInc) && Double.isFinite(sumExp)) {
                return sumInc - sumExp;
            } else {
                // Handle non-finite values, return a default value, or throw an exception
                return 0.0;     // Default value (adjust as needed)
            }
        } catch (NumberFormatException e) {
            System.out.println(e);      // Handle parsing errors, return a default value, or throw an exception
        }
        return bal;
    }

    public List<Category> catSumTotal() {

        List<Category> catSumList = new ArrayList<>();
//        Double sumCat = 0.0;
        Double sumCat;
        db = getReadableDatabase();

                //getting totals of every category
        String sumQuery = "SELECT\n" +
                "    c.Category_ID,\n" +
                "    c.Category,\n" +
                "    COALESCE(SUM(i.IncAmount), 0) AS TotalIncome,\n" +
                "    COALESCE(SUM(e.ExpAmount), 0) AS TotalExpense\n" +
                "FROM Categories c\n" +
                "LEFT JOIN Incomes i ON c.Category_ID = i.Category_ID\n" +
                "LEFT JOIN Expenses e ON c.Category_ID = e.Category_ID\n" +
                "GROUP BY c.Category_ID, c.Category;";

        Cursor cursorSum = db.rawQuery(sumQuery, null);

        if (cursorSum.moveToFirst()) {
            do {
                Category category = new Category(Category_ID, Category, catSum); //

                category.setId(cursorSum.getString(0));
                category.setCatName(cursorSum.getString(1));

                double incomeTotal = Double.parseDouble(cursorSum.getString(2));
                double expenseTotal = Double.parseDouble(cursorSum.getString(3));

//                category.setCatSum(String.valueOf(incomeTotal));

                if (incomeTotal > 0 && expenseTotal > 0){
//                    sumCat = Double.parseDouble(String.valueOf(incomeTotal-expenseTotal));
//                    sumCat = Double.valueOf(incomeTotal-expenseTotal);
//                    sumCat= 100.5;
                    sumCat = incomeTotal - expenseTotal;
                    System.out.println(sumCat);

                }
                else if (incomeTotal == 0 && expenseTotal > 0) {
//                    sumCat = Double.parseDouble(String.valueOf(expenseTotal));
//                    sumCat = 200.5;
                    sumCat = expenseTotal;
                }
                else if (incomeTotal > 0 && expenseTotal == (double) 0) {
//                    sumCat = Double.parseDouble(String.valueOf(incomeTotal));
//                    sumCat = 300.5;
                    sumCat = incomeTotal;
                }
                else {
                    sumCat = 0.0;
                }

                category.setCatSum(String.valueOf(sumCat));

                catSumList.add(category);
            }
            while (cursorSum.moveToNext());
        }
        cursorSum.close();
        return catSumList;
    }

//    public void reset() {
//        db = getWritableDatabase();
//        String query = "DELETE FROM Expenses; ";
//        Cursor cursor = db.rawQuery(query, null);
//        System.out.println(cursor);
//
//
//    }

}