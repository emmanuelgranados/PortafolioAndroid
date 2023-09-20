package com.example.app_patio_vehicular;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class PatioUno extends AppCompatActivity {

    Button btnScan;
    EditText txtResultado;

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
        setContentView(R.layout.activity_patio_uno);

       btnScan = findViewById(R.id.btnScan);
       txtResultado = findViewById(R.id.txtresultadoscan);

       btnScan.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               IntentIntegrator integrador = new IntentIntegrator(PatioUno.this);
               integrador.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
               integrador.setPrompt("Acerca el codigo");
               integrador.setCameraId(0);
               integrador.setBeepEnabled(true);
               integrador.setBarcodeImageEnabled(true);
               integrador.addExtra(Intents.Scan.ORIENTATION_LOCKED, 90);
               integrador.initiateScan();
           }
       });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(this,"Lectora cancelada",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,result.getContents(),Toast.LENGTH_LONG).show();
                txtResultado.setText(result.getContents());
            }
        }else{
            super.onActivityResult(requestCode,resultCode,data);
        }


    }
}