package com.example.app_patio_vehicular;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_patio_vehicular.db.DbContactos;

public class Registro extends AppCompatActivity {

    EditText txtNombre, txtTelefono, txtCorreoElectronico;
    Button btnGuardar;

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
        setContentView(R.layout.activity_registro);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtNombre = findViewById(R.id.txtNombre);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtCorreoElectronico = findViewById(R.id.txtCorreoElectronico);
        btnGuardar = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtNombre.getText().toString().isEmpty()){
                    Toast.makeText(Registro.this, "No puede guardar un contacto vacio", Toast.LENGTH_SHORT).show();
                }else{
                    DbContactos dbContactos = new DbContactos(Registro.this);
                    long id = dbContactos.registrarContacto(txtNombre.getText().toString(),txtTelefono.getText().toString(),txtCorreoElectronico.getText().toString());

                    if(id > 0){
                        Toast.makeText(Registro.this, "Registro gurdado correctamente", Toast.LENGTH_SHORT).show();
                        limpiar();
                    }else{
                        Toast.makeText(Registro.this, "Error al guardar registro", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void limpiar(){
        txtNombre.setText("");
        txtTelefono.setText("");
        txtCorreoElectronico.setText("");
    }
}