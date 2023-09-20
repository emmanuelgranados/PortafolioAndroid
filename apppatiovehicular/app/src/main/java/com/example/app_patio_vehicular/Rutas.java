package com.example.app_patio_vehicular;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Rutas extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    Polyline routePolyline;
    MapView mapView;
    GoogleMap mMap;
    Button btnCalculate;
    String start,end,Latitud,Longitud;
    Polyline poly = null;
    private LocationManager ubicacion;



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
        setContentView(R.layout.activity_rutas);

        mapView = findViewById(R.id.mapViewRutas);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        btnCalculate = findViewById(R.id.btnCancularRuta);


        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                end = "";
                if (poly != null) {
                    poly.remove();
                    poly = null;
                }

                mMap.clear();
                addCurrentLocationMarker();
                Toast.makeText(Rutas.this, "Selecciona el destino", Toast.LENGTH_SHORT).show();

                // Establece un listener de clic en el mapa para seleccionar el punto final
                if (mMap != null) {
                    mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            if (end.isEmpty()) {
                                end = latLng.longitude + "," + latLng.latitude;
                                // Marcar el punto de fin en el mapa
                                mMap.addMarker(new MarkerOptions()
                                        .position(latLng)
                                        .title("Destino"));
                                createRoute();
                            }
                        }
                    });
                }
            }
        });
    }


    private void addCurrentLocationMarker() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            // Obtener la ubicación actual
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                // Crear un objeto LatLng con las coordenadas de la ubicación actual
                LatLng currentLatLng = new LatLng(latitude, longitude);

                // Agregar un marcador en la ubicación actual con un título
                mMap.addMarker(new MarkerOptions()
                        .position(currentLatLng)
                        .title("Mi Ubicación Actual"));

                // Mover la cámara del mapa a la ubicación actual
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 13.0f));
            }
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION
            }, 1000);
        }

        ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location loc = ubicacion.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (loc != null) {
            mMap = googleMap;
            LatLng startPoint = new LatLng(loc.getLatitude(), loc.getLongitude());

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startPoint, 13.0f));

            // Agregar un marcador en tu ubicación actual
            mMap.addMarker(new MarkerOptions()
                    .position(startPoint)
                    .title("Aqui estoy"));
        }
    }


    private void createRoute() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ApiService apiService = getRetrofit().create(ApiService.class);
                    retrofit2.Call<RouteResponse> call = apiService.getRoute("5b3ce3597851110001cf624805a771df94ab48539835a30b0d74ddb9", start, end);
                    retrofit2.Response<RouteResponse> response = call.execute();

                    if (response.isSuccessful()) {
                        RouteResponse routeResponse = response.body();
                        if (routeResponse != null && !routeResponse.getFeatures().isEmpty()) {
                            Log.i("aris", "AQUI");
                            Gson gson = new Gson();
                            String jsonResponse = gson.toJson(routeResponse);
                            Log.i("aris", "Respuesta JSON: " + jsonResponse);
                        }
                    } else {
                        Log.i("aris", "KO");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://api.openrouteservice.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume(); // Importante para MapView
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause(); // Importante para MapView
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy(); // Importante para MapView
    }

    private void drawRoute(LatLng startPoint, LatLng endPoint) {
        PolylineOptions polylineOptions = new PolylineOptions()
                .add(startPoint, endPoint)
                .width(5) // Ancho de la línea en píxeles
                .color(Color.RED); // Color de la línea en RGB

        routePolyline = mMap.addPolyline(polylineOptions);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        // Manejar clics en el mapa si es necesario
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        // Manejar clics largos en el mapa si es necesario
    }
}