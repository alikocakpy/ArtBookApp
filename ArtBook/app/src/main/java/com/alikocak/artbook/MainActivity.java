package com.alikocak.artbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.alikocak.artbook.databinding.ActivityDetailsBinding;
import com.alikocak.artbook.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    ArrayList<Art> artArrayList;
    ArtAdapter artAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        artArrayList = new ArrayList<>();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        artAdapter = new ArtAdapter(artArrayList);
        binding.recyclerView.setAdapter(artAdapter);
        getData();


    }

    private void getData() {
        try{
            SQLiteDatabase db = this.openOrCreateDatabase("Arts",MODE_PRIVATE,null);
            Cursor cursor = db.rawQuery("SELECT * FROM arts",null);
            int nameIX = cursor.getColumnIndex("artname");
            int idIX = cursor.getColumnIndex("id");
            while(cursor.moveToNext()){
                String name = cursor.getString(nameIX);
                int id = cursor.getInt(idIX);
                Art art = new Art(id,name);
                artArrayList.add(art);
            }

            artAdapter.notifyDataSetChanged();
            cursor.close();

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.art_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("info","new");
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }


}