package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;

import com.example.todolist.databinding.ActivityMainBinding;
import com.example.todolist.databinding.ActivitySelectedListBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;


    ArrayList<List> lists;

    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        lists=new ArrayList<>();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listAdapter=new ListAdapter(lists);
        binding.recyclerView.setAdapter(listAdapter);
        getData();
    }
    public void newList(View view){
        Intent intent=new Intent(MainActivity.this,NewList.class);
        startActivity(intent);
    }

    public void getData(){
        try {
            SQLiteDatabase database=this.openOrCreateDatabase("Lists",MODE_PRIVATE,null);
            Cursor cursor=database.rawQuery("SELECT * FROM lists WHERE favorite='true'",null);
            int nameIx=cursor.getColumnIndex("name");
            int idIx=cursor.getColumnIndex("id");
            int surnameIx=cursor.getColumnIndex("surname");
            int favoriteIx=cursor.getColumnIndex("favorite");
            int dateIx=cursor.getColumnIndex("date");
            int listeIx=cursor.getColumnIndex("list");

            while(cursor.moveToNext()){
                String surname=cursor.getString(surnameIx);
                String name=cursor.getString(nameIx);
                int id=cursor.getInt(idIx);
                String favorite=cursor.getString(favoriteIx);
                String date=cursor.getString(dateIx);
                String liste=cursor.getString(listeIx);
                List list=new List(name,id,favorite,surname,date,liste);
                lists.add(0,list);

            }
            listAdapter.notifyDataSetChanged();
            cursor.close();

        } catch (Exception e){
            e.printStackTrace();
        }
        try {
            SQLiteDatabase database=this.openOrCreateDatabase("Lists",MODE_PRIVATE,null);
            Cursor cursor=database.rawQuery("SELECT * FROM lists WHERE favorite='false'",null);
            int nameIx=cursor.getColumnIndex("name");
            int idIx=cursor.getColumnIndex("id");
            int surnameIx=cursor.getColumnIndex("surname");
            int favoriteIx=cursor.getColumnIndex("favorite");
            int dateIx=cursor.getColumnIndex("date");
            int listeIx=cursor.getColumnIndex("list");

            while(cursor.moveToNext()){
                String surname=cursor.getString(surnameIx);
                String name=cursor.getString(nameIx);
                int id=cursor.getInt(idIx);
                String favorite=cursor.getString(favoriteIx);
                String date=cursor.getString(dateIx);
                String liste=cursor.getString(listeIx);
                List list=new List(name,id,favorite,surname,date,liste);
                lists.add(list);

            }
            listAdapter.notifyDataSetChanged();
            cursor.close();

        } catch (Exception e){
            e.printStackTrace();
        }


    }
}