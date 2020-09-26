package com.example.expensetrack;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public class ExpensePieGraph extends AppCompatActivity {

    DatabaseReference ref;
    DatabaseReference expensesRef;

    String item;

    PieChart pieChart;
    private FirebaseAuth mAuth;

    Expense expense;


    PieDataSet pieDataSet = new PieDataSet(null,null);
    ArrayList<IPieDataSet> iPieDataSets = new ArrayList<>();
    ArrayList<Expense> expenses = new ArrayList<Expense>();

    int[] colorClassArray = new int[] {Color.GREEN,Color.MAGENTA,Color.RED};
    private Object PieData;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_pie_graph);
        getSupportActionBar().setTitle(R.string.app_name_Graph);
        mAuth = FirebaseAuth.getInstance();
















        pieChart = findViewById(R.id.piechart);

        PieDataSet pieDataSet = new PieDataSet(dataValues1(),"");
        pieDataSet.setColors(colorClassArray);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.invalidate();











    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logOut) {
            mAuth.signOut();
            Intent intent = new Intent(ExpensePieGraph.this, MainActivity.class);
            startActivity(intent);
        }

        else  if (item.getItemId() == R.id.profile) {
            Intent i = new Intent( ExpensePieGraph.this, Profile.class);
            startActivity(i);
        }





        return true;
    }

    private ArrayList<PieEntry> dataValues1 (){
        ArrayList<PieEntry> dataVals = new ArrayList<>();


        dataVals.add(new PieEntry(111,"D-mart"));
        dataVals.add(new PieEntry(111,"Household"));
        dataVals.add(new PieEntry(111,"Medical"));
//        dataVals.add(new PieEntry(111,"Travelling"));
//        dataVals.add(new PieEntry(111,"Personal"));






        pieDataSet.setColors(colorClassArray);


        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.invalidate();
        pieChart.setDrawEntryLabels(true);
        pieChart.setUsePercentValues(true);
        pieChart.setCenterText("Expense Data");
        pieChart.setCenterTextSize(20);
        pieChart.setHoleRadius(30);
        pieChart.setTransparentCircleRadius(40);
        pieChart.setTransparentCircleColor(Color.RED);
        pieChart.setTransparentCircleAlpha(50);
//        pieChart.setMaxAngle(180);
        return dataVals;



    }




























}
