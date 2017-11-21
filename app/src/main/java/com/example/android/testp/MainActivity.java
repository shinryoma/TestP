package com.example.android.testp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    DBHelper myDb;
    EditText editName, editFamily, editMarks, editID;
    Button btnAddData;
    Button btnViewAll;
    Button btnUpdate;
    Button btnDelete;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DBHelper(this);

        editName = (EditText) findViewById(R.id.editText);
        editFamily = (EditText) findViewById(R.id.editText2);
        editMarks = (EditText) findViewById(R.id.editText3);
        editID = (EditText) findViewById(R.id.editText4);
        btnAddData = (Button) findViewById(R.id.button_add);
        btnViewAll = (Button) findViewById(R.id.buttonQuery);
        btnUpdate = (Button) findViewById(R.id.buttonUpdate);
        btnDelete = (Button) findViewById(R.id.buttonDelete);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        AddData();
        viewAll();
        updateData();
        deleteData();

    }

        public void AddData(){
        btnAddData.setOnClickListener(
        new View.OnClickListener() {
            @Override
            public void onClick(View v){
               boolean isInserted =  myDb.insertData(editName.getText().toString(), editFamily.getText().toString(), editMarks.getText().toString());

               if(isInserted == true)
                   Toast.makeText(MainActivity.this, "data inserted", Toast.LENGTH_LONG).show();
               else
                   Toast.makeText(MainActivity.this, "data not inserted", Toast.LENGTH_LONG).show();
            }
        }
        );
        }

        public void updateData() {
            btnUpdate.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v){
                            boolean isUpdate = myDb.updateData(editID.getText().toString(), editName.getText().toString(),editFamily.getText().toString(),editMarks.getText().toString());

                            if(isUpdate == true) {
                                Toast.makeText(MainActivity.this, "data update", Toast.LENGTH_LONG).show();
                            }
                              else
                                Toast.makeText(MainActivity.this, "data not updated", Toast.LENGTH_LONG).show();

                        }
                    }
            );

        }

        public void viewAll() {
            btnViewAll.setOnClickListener(
                    new View.OnClickListener(){
                            @Override
                            public void onClick(View v){
                                Cursor res = myDb.getAll();
                                if (res.getCount() == 0) {
                                    // no data
                                    showMessage("Error","No Data");
                                    return;
                                }
                                else {
                                    StringBuffer buffer = new StringBuffer();
                                    while (res.moveToNext()){
                                        buffer.append("Id :" + res.getString(0)+"\n");
                                        buffer.append("Name :" + res.getString(1)+"\n");
                                        buffer.append("Family :" + res.getString(2)+"\n");
                                        buffer.append("Grades :" + res.getString(3)+"\n\n");
                                    }

                                    showMessage("Data",buffer.toString());

                                }



                            }
                    }
            );
        }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void deleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        int deletedRows = myDb.deleteData(editID.getText().toString());
                        if (deletedRows > 0){
                            Toast.makeText(MainActivity.this, "data deleted", Toast.LENGTH_LONG).show();
                    }
                              else
                                  Toast.makeText(MainActivity.this, "data not deleted", Toast.LENGTH_LONG).show();

                }
                }
        );
    }

}
