package com.example.todolist;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.todolist.databinding.ActivityNewListBinding;
import com.example.todolist.databinding.ActivitySelectedListBinding;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class NewList extends AppCompatActivity {
    private ActivityNewListBinding binding;


    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

    }


    public void save(View view){
        String name=binding.editTextTextPersonName.getText().toString();
        String surname=binding.editTextTextPersonName2.getText().toString();
        String list=binding.editTextTextPersonName3.getText().toString();

         SimpleDateFormat formatter = new SimpleDateFormat(" dd, yyyy");
         Date d = new Date();
         int dateMonthInt=d.getMonth()+1;
        String month;
        switch (dateMonthInt) {
            case 1:
                month= "january";
                break;
            case 2:
                month= "february";
                break;
            case 3:
                month= "march";
                break;
            case 4:
                month= "april";
                break;
            case 5:
                month= "may";
                break;
            case 6:
                month= "june";
                break;
            case 7:
                month= "july";
                break;
            case 8:
                month= "august";
                break;
            case 9:
                month= "september";
                break;
            case 10:
                month= "october";
                break;
            case 11:
                month= "november";
                break;
            case 12:
                month= "devember";
                break;
            default:
                month= "undefined";
        }
        String date=month+formatter.format(d).toString();



        try {
            database=this.openOrCreateDatabase("Lists",MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS lists(id INTEGER PRIMARY KEY, name VARCHAR, surname VARCHAR, favorite VARCHAR, date VARCHAR,list VARCHAR )");

            String sqlString= "INSERT INTO lists (name,surname,favorite,date,list) VALUES (?,?,?,?,?)";
            SQLiteStatement sqLiteStatement=database.compileStatement(sqlString);
            sqLiteStatement.bindString(1,name);
            sqLiteStatement.bindString(2,surname);
            sqLiteStatement.bindString(3,"false");
            sqLiteStatement.bindString(4,date);
            sqLiteStatement.bindString(5,list);
            sqLiteStatement.execute();


        }catch (Exception e){
            e.printStackTrace();
        }



        Intent intent=new Intent(NewList.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}