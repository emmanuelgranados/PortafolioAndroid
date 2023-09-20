package com.example.app_patio_vehicular;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.app_patio_vehicular.adaptadores.ListaContactosAdapter;
import com.example.app_patio_vehicular.db.DbContactos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Contactos extends AppCompatActivity implements SearchView.OnQueryTextListener {

    RecyclerView listaContactos;
    ArrayList<Contactos> listaArrayContactos;
    SearchView txtBuscar;
    FloatingActionButton fabNuevo;
    ListaContactosAdapter adapter;

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
        setContentView(R.layout.activity_contactos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtBuscar = findViewById(R.id.txtBuscar);
        listaContactos = findViewById(R.id.listaContactos);
        fabNuevo = findViewById(R.id.fabNuevo);
        listaContactos.setLayoutManager(new LinearLayoutManager(this));
        DbContactos dbContactos = new DbContactos(Contactos.this);
        listaArrayContactos = new ArrayList<>();

        adapter = new ListaContactosAdapter(dbContactos.mostrarContactos());
        listaContactos.setAdapter(adapter);

        fabNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevoRegistro();
            }
        });

        txtBuscar.setOnQueryTextListener(this);

    }
    public void nuevoRegistro(){
        Intent intent = new Intent(Contactos.this,Registro.class);
        startActivity(intent);
    }
    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filtrado(s);
        return false;
    }
}