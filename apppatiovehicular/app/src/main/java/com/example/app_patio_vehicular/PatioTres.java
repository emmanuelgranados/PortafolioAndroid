package com.example.app_patio_vehicular;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class PatioTres extends AppCompatActivity {

    private LocationManager ubicacion;
    private EditText Latitud;
    private EditText Longitud;
    private static final int REQUEST_LOCATION_PERMISSION = 1000;

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
        setContentView(R.layout.activity_patio_tres);
        Latitud = findViewById(R.id.txtLatitud);
        Longitud = findViewById(R.id.txtLongitud);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            localizacion();
        }
    }


    private void localizacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION
            },1000);
        }
        ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location loc = ubicacion.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(ubicacion != null){
            Latitud.setText(String.valueOf(loc.getLatitude()));
            Longitud.setText(String.valueOf(loc.getLongitude()));
        }
    }
}