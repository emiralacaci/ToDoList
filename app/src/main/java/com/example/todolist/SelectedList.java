package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.todolist.databinding.ActivitySelectedListBinding;

import java.util.ArrayList;
import java.util.StringJoiner;

public class SelectedList extends AppCompatActivity {
    private ActivitySelectedListBinding binding;

    Boolean clicked;
    SQLiteDatabase database;
    int listId;
    String isFavorite;
    ArrayList<List> lists;
    String space;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectedListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        space    = " ";
        database=this.openOrCreateDatabase("Lists",MODE_PRIVATE,null);
        Intent intent=getIntent();
        listId=intent.getIntExtra("id",1);

        isFavorite=intent.getStringExtra("favorite");
        isFavorite();
        getData();


    }

    public void favorite(View view){
        if(clicked){
            binding.favorite.setImageResource(R.drawable.ic_star_full);
            database.execSQL("UPDATE lists SET favorite='true' WHERE id=?",new String[] {String.valueOf(listId)});

            clicked=false;
        }else{
            binding.favorite.setImageResource(R.drawable.ic_star_border);
            database.execSQL("UPDATE lists SET favorite='false' WHERE id=?",new String[] {String.valueOf(listId)});
            clicked=true;
        }


    }

    public void isFavorite(){
        if(isFavorite.matches("true")){
            binding.favorite.setImageResource(R.drawable.ic_star_full);
            clicked=false;
        }
        if(isFavorite.matches("false")){
            binding.favorite.setImageResource(R.drawable.ic_star_border);
            clicked=true;
        }

    }

    public void getData(){
        try {
            Cursor cursor=database.rawQuery("SELECT * FROM lists WHERE id=?",new String[] {String.valueOf(listId)});
            int listNameIx=cursor.getColumnIndex("name");
            int dateIx=cursor.getColumnIndex("date");
            int listeIx=cursor.getColumnIndex("list");

            while(cursor.moveToNext()){
                binding.selectedListTextView.setText(cursor.getString(listNameIx));
                binding.editTextTextPersonName3.setText(cursor.getString(listeIx));
                String date=cursor.getString(dateIx);
                String dateEdited=date.substring(date.indexOf(space) + 1,
                        date.length());
                binding.date.setText("Last modified "+dateEdited);

            }
            cursor.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update(View view){

        new AlertDialog.Builder(SelectedList.this).setTitle("  Emin misiniz ?")
                .setMessage("Güncellemek istediğine emin misin ?")
                .setIcon(R.drawable.ic_baseline_update_24)
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String updatedList=binding.editTextTextPersonName3.getText().toString();
                        String sqlString="UPDATE lists SET list=? WHERE id=?";
                        SQLiteStatement sqLiteStatement=database.compileStatement(sqlString);
                        sqLiteStatement.bindString(1,updatedList);
                        sqLiteStatement.bindString(2,String.valueOf(listId));
                        sqLiteStatement.execute();

                        Toast.makeText(SelectedList.this, "Listeniz güncellenmiştir.", Toast.LENGTH_LONG ).show();

                    }
                })
                .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(SelectedList.this, "İptal edildi.", Toast.LENGTH_LONG ).show();
                    }
                })
                .show();




    }


    /*public void clicked(View view){
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBox1:
                if (checked) {binding.checkBox1.setPaintFlags(binding.checkBox1.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                    //binding.checkBox1.setTextColor(R.color.gray);
                }
                // Put some meat on the sandwich
                else {
                    binding.checkBox1.setPaintFlags(binding.checkBox1.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    //binding.checkBox1.setTextColor(R.color.black);
                }
                // Remove the meat
                break;
            case R.id.checkBox2:
                if (checked) {binding.checkBox2.setPaintFlags(binding.checkBox2.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                    //binding.checkBox2.setTextColor(R.color.gray);
                    }
                // Put some meat on the sandwich
                else {binding.checkBox2.setPaintFlags(binding.checkBox2.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    //binding.checkBox2.setTextColor(R.color.black);
                }
                // I'm lactose intolerant
                break;
            case R.id.checkBox3:
                if (checked) {binding.checkBox3.setPaintFlags(binding.checkBox3.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                    //binding.checkBox3.setTextColor(R.color.gray);
                    }
                // Put some meat on the sandwich
                else {binding.checkBox3.setPaintFlags(binding.checkBox3.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    //binding.checkBox3.setTextColor(R.color.black);
                }
                // Remove the meat
                break;
            case R.id.checkBox4:
                if (checked) {binding.checkBox4.setPaintFlags(binding.checkBox4.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                    //binding.checkBox4.setTextColor(R.color.gray);
                    }
                // Put some meat on the sandwich
                else {binding.checkBox4.setPaintFlags(binding.checkBox3.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    //binding.checkBox4.setTextColor(R.color.black);
                }
                // Remove the meat
                break;
            // TODO: Veggie sandwich
        }
    }

     */

    public void back(View view){
        Intent intent=new Intent(SelectedList.this,MainActivity.class);
        startActivity(intent);
    }
}