package com.example.app_patio_vehicular;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Maps extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {
    EditText txtLalitud, txtLongitud;
    MapView mapView;
    GoogleMap mMap;

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
        setContentView(R.layout.activity_maps);

        txtLalitud = findViewById(R.id.txtLatitud);
        txtLongitud = findViewById(R.id.txtLongitud);

        mapView = findViewById(R.id.mapViewRutas);
        mapView.onCreate(savedInstanceState); // Important for MapView

        mapView.getMapAsync(this); // This will trigger onMapReady when the map is ready
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume(); // Important for MapView
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause(); // Important for MapView
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy(); // Important for MapView
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        this.mMap.setOnMapClickListener(this);
        this.mMap.setOnMapLongClickListener(this);

        LatLng mexico = new LatLng(20.7316329, -103.5440278);
        mMap.addMarker(new MarkerOptions().position(mexico).title("Oleofinos"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mexico));

        float zoomLevel = 15.0f; // You can adjust this value as needed
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mexico, zoomLevel));
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        txtLalitud.setText("" + latLng.latitude);
        txtLongitud.setText("" + latLng.longitude);
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        txtLalitud.setText("" + latLng.latitude);
        txtLongitud.setText("" + latLng.longitude);
    }
}