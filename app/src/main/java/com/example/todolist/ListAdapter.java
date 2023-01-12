package com.example.todolist;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.databinding.RecyclerRowBinding;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder> {

    String colors[]={"#CBD2EF","#CFE0D0","#EDCECC"};
    ArrayList<List> lists;
    SQLiteDatabase database;

    public ListAdapter(ArrayList<List> lists) {
        this.lists = lists;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding binding=RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ListHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        try {
            database = holder.itemView.getContext().openOrCreateDatabase("Lists", MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery("SELECT * FROM lists WHERE id=?", new String[]{String.valueOf(lists.get(position).id)});
            int favoriteIx = cursor.getColumnIndex("favorite");

            while(cursor.moveToNext()){
                String isFavorite = cursor.getString(favoriteIx);
                if(isFavorite.matches("false")){
                    holder.binding.star.setVisibility(View.INVISIBLE);
                }
            }
            cursor.close();



        }catch (Exception e){e.printStackTrace();}



        holder.binding.recyclerViewTextView.setText(lists.get(position).name);
        holder.binding.recyclerViewTextView2.setText(lists.get(position).surname);
        holder.binding.date.setText(lists.get(position).date);
        holder.binding.row.setBackgroundColor(Color.parseColor(colors[position%3]));
        int id=lists.get(position).id;
        String favorite=lists.get(position).favorite;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.itemView.getContext(),SelectedList.class);
                intent.putExtra("id",id);
                intent.putExtra("favorite",favorite);
                holder.itemView.getContext().startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder{
        private RecyclerRowBinding binding;
        public ListHolder(RecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
