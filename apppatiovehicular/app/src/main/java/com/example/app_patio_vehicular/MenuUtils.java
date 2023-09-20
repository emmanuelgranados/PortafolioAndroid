package com.example.app_patio_vehicular;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Toast;

import androidx.core.app.NavUtils;

public class MenuUtils {

    public static boolean onCreateOptionsMenu(Menu menu, Activity activity) {
        activity.getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public static boolean onOptionsItemSelected(MenuItem item, Activity activity) {

        int id = item.getItemId();
        Class<?> targetClass = null;
        String toastMessage = null;

        switch (id) {
            case R.id.item1:
                targetClass = PatioUno.class;
                toastMessage = "Patio vehicular 1";
                break;
            case R.id.item2:
                targetClass = PatioDos.class;
                toastMessage = "Patio vehicular 2";
                break;
            case R.id.item3:
                targetClass = PatioTres.class;
                toastMessage = "Patio vehicular 3";
                break;
            case R.id.main:
                targetClass = MainActivity.class;
                toastMessage = "Inicio";
                break;
            case R.id.settings:
                targetClass = Settings.class;
                toastMessage = "Configuraciones";
                break;
            case R.id.itemmapa:
                targetClass = Maps.class;
                toastMessage = "Mapa";
                break;
            case R.id.itemrutas:
                targetClass = Rutas.class;
                toastMessage = "Rutas";
                break;
            case R.id.item4:
                targetClass = PatioCuatro.class;
                toastMessage = "Reconocimiento Facial";
                break;
            case R.id.item5:
                targetClass = DataBase.class;
                toastMessage = "Data Base";
                break;
            case R.id.item6:
                targetClass = Registro.class;
                toastMessage = "Nuevo Registro";
                break;
            case R.id.item7:
                targetClass = Contactos.class;
                toastMessage = "Lista de Contactos";
                break;
        }

        if (targetClass != null) {
            Intent intent = new Intent(activity, targetClass);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }

        if (toastMessage != null) {
            Toast.makeText(activity, toastMessage, Toast.LENGTH_SHORT).show();
        }
        NavUtils.navigateUpFromSameTask(activity);
        return true;
    }
}
