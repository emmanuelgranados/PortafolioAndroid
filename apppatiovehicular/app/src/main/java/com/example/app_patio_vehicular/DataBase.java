package com.example.app_patio_vehicular;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.app_patio_vehicular.db.DbHelper;

public class DataBase extends AppCompatActivity {

    Button btnCrear;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return MenuUtils.onCreateOptionsMenu(menu, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuUtils.onOptionsItemSelected(item, this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);
        btnCrear = findViewById(R.id.btnCrearDB);

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper dbhelper = new DbHelper(DataBase.this);
                SQLiteDatabase db = dbhelper.getWritableDatabase();
                if(db != null){
                    Toast.makeText(DataBase.this,"Base de datos creada",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(DataBase.this,"Error al crear base de datos",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}