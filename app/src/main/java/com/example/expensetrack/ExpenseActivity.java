
package com.example.expensetrack;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ExpenseActivity extends AppCompatActivity {




    ImageView addExp;
    ArrayList<Expense> expenses = new ArrayList<Expense>();
    ListView expList;
    LinearLayout expHint;
    String uid;
    DatabaseReference expensesRef;
    DatabaseReference ref;
    String item;



    FirebaseAuth mAuth;
    final static String[] categories = {"Groceries","Invoice","Transportation","Shopping","Rent","Trips","Utilities","Other","Food","Health","Household","Sports","Clothes","Entertainment","General","Hygiene","Presents"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        getSupportActionBar().setTitle(R.string.app_name_Expense);
        ref = FirebaseDatabase.getInstance().getReference();





//        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
//        mRewardedVideoAd.setRewardedVideoAdListener(this);







//        mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        loadAllViews();
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logOut) {

//            AuthUI.getInstance()
//                    .signOut(ExpenseActivity.this)
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(ExpenseActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                }
//            });
            mAuth.signOut();
            Intent intent = new Intent(ExpenseActivity.this, MainActivity.class);
            startActivity(intent);
        }

        else  if (item.getItemId() == R.id.Graph) {
            Intent i = new Intent( ExpenseActivity.this, ExpensePieGraph.class);
            startActivity(i);
        }



        else  if (item.getItemId() == R.id.profile) {
            Intent i = new Intent( ExpenseActivity.this, Profile.class);
            startActivity(i);
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        expensesRef = ref.child("users").child(uid).child("expenses");
        if(expensesRef == null) {
            expHint.setVisibility(View.GONE);
            expList.setVisibility(View.VISIBLE);
        } else {
            expensesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    expenses.clear();
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        Expense exps = ds.getValue(Expense.class);
                        exps.setId(ds.getKey());
                        expenses.add(exps);
                    }
                    showExpenseTable();


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    public void loadAllViews() {
        expList = (ListView) findViewById(R.id.expensesListView);
        addExp = (ImageView) findViewById(R.id.addExpenseButton);
        expHint = (LinearLayout) findViewById(R.id.addExpenseBody);
        addExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpenseActivity.this, AddExpenseActivity.class);
                startActivity(intent);
            }
        });
        expList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Expense exp = expenses.get(position);
                expensesRef.child(exp.getId()).removeValue();
                Toast.makeText(ExpenseActivity.this,"Expense Deleted",Toast.LENGTH_LONG).show();
                return true;
            }
        });
        expList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Expense exp = expenses.get(position);
                Intent intent = new Intent(ExpenseActivity.this, ViewExpenseActivity.class);
                intent.putExtra("expense",exp);
                startActivity(intent);
            }
        });
    }

    public void showExpenseTable() {
        if(expenses.size() == 0) {
            expHint.setVisibility(View.VISIBLE);
            expList.setVisibility(View.GONE);
        } else {
            expHint.setVisibility(View.GONE);
            expList.setVisibility(View.VISIBLE);
            final ExpenseAdapter adapter = new ExpenseAdapter(ExpenseActivity.this,R.layout.row_layout,expenses);
            expList.setAdapter(adapter);


        }
    }


    public void onBackPressed() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Confirm Exit");

        alertDialogBuilder.setIcon(R.drawable.ic_exit);
        alertDialogBuilder.setMessage("Are you Sure You want to Exit");

        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();

            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(ExpenseActivity.this," You Clicked On Cancel", Toast.LENGTH_LONG).show();

            }
        });



        AlertDialog alertDialog=alertDialogBuilder.create();
        alertDialog.show();



















    }





}
