package com.example.app_patio_vehicular;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_patio_vehicular.db.DbContactos;
import com.example.app_patio_vehicular.entidades.Contactos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditarActivity extends AppCompatActivity {

    EditText txtNombre, txtTelefono, txtCorreo;
    Button btnGuarda;
    Contactos contacto;
    FloatingActionButton fabEditar, fabEliminar;
    boolean correcto = false;
    int id = 0;

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
        setContentView(R.layout.activity_ver);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtNombre = findViewById(R.id.txtNombre);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtCorreo = findViewById(R.id.txtCorreoElectronico);
        btnGuarda = findViewById(R.id.btnGuardar);
        fabEditar = findViewById(R.id.fabEditar);
        fabEditar.setVisibility(View.INVISIBLE);
        fabEliminar = findViewById(R.id.fabEliminar);
        fabEliminar.setVisibility(View.INVISIBLE);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = Integer.parseInt(null);
            }else{
                id = extras.getInt("ID");
            }
        }else  {
            id = (int) savedInstanceState.getSerializable("ID");
        }
        DbContactos dbContactos = new DbContactos(EditarActivity.this);
        contacto = dbContactos.verContacto(id);

        if(contacto != null){
            txtNombre.setText(contacto.getNombre());
            txtTelefono.setText(contacto.getTelefono());
            txtCorreo.setText(contacto.getCorreo_electronico());
        }
        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtNombre.getText().toString().equals("") && !txtTelefono.getText().toString().equals("")){
                   correcto = dbContactos.editarContacto(id,txtNombre.getText().toString(),txtTelefono.getText().toString(),txtCorreo.getText().toString());
                   if(correcto){
                       Toast.makeText(EditarActivity.this, "Registro modificado", Toast.LENGTH_SHORT).show();
                       verRegistro();
                   }else {
                       Toast.makeText(EditarActivity.this,"Error al modificar el registro",Toast.LENGTH_SHORT).show();
                   }
                } else {
                    Toast.makeText(EditarActivity.this,"Debe de llenar los campos obligatorios",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void verRegistro(){
        Intent intent = new Intent(this,EditarActivity.class);
        intent.putExtra("ID",id);
        startActivity(intent);
    }
}