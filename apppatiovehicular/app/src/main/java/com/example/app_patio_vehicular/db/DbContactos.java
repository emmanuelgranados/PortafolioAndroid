package com.example.app_patio_vehicular.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.app_patio_vehicular.entidades.Contactos;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DbContactos extends DbHelper {

    Context context;

    public DbContactos(@Nullable Context context){
        super(context);
        this.context = context;
    }

    public long registrarContacto(String nombre,String telefono,String correo_electronico){

        long id = 0;
        try{
            DbHelper dbhelper = new DbHelper(context);
            SQLiteDatabase db = dbhelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre",nombre);
            values.put("telefono",telefono);
            values.put("correo_electronico",correo_electronico);

            id = db.insert(TABLE_CONTACTOS,null,values);

        }catch(Exception ex){
            ex.toString();
        }

        return id;
    }

    public boolean editarContacto(int id, String nombre,String telefono,String correo_electronico){
        boolean correcto = false;

        DbHelper dbhelper = new DbHelper(context);
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        try{
            db.execSQL("UPDATE "+ TABLE_CONTACTOS + " SET nombre = '" + nombre + "', telefono = '" + telefono + "', correo_electronico = '" + correo_electronico +"' WHERE id = '" + id + "'");
            correcto = true;
        }catch(Exception ex){
            ex.toString();
        }finally {
            db.close();
        }

        return correcto;
    }

    public boolean eliminarContacto(int id){
        boolean correcto = false;

        DbHelper dbhelper = new DbHelper(context);
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        try{
            db.execSQL("DELETE FROM "+ TABLE_CONTACTOS + " WHERE id = '" + id + "'");
            correcto = true;
        }catch(Exception ex){
            ex.toString();
        }finally {
            db.close();
        }

        return correcto;
    }

    public ArrayList<Contactos> mostrarContactos() {
        ArrayList<Contactos> listaContactos = new ArrayList<>();
        try {
            DbHelper dbhelper = new DbHelper(context);
            SQLiteDatabase db = dbhelper.getWritableDatabase();

            Cursor cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS, null);

            if (cursorContactos.moveToFirst()) {
                do {
                    Contactos contacto = new Contactos();
                    contacto.setId(cursorContactos.getInt(0));
                    contacto.setNombre(cursorContactos.getString(1));
                    contacto.setTelefono(cursorContactos.getString(2));
                    contacto.setCorreo_electronico(cursorContactos.getString(3));
                    listaContactos.add(contacto);
                } while (cursorContactos.moveToNext());
            }
            cursorContactos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listaContactos;
    }

    public Contactos verContacto(int id) {

        Contactos contacto = null;
        try {
            DbHelper dbhelper = new DbHelper(context);
            SQLiteDatabase db = dbhelper.getWritableDatabase();

            Cursor cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS + " WHERE id = " + id + " LIMIT 1", null);

            if (cursorContactos.moveToFirst()) {
                contacto = new Contactos();
                contacto.setId(cursorContactos.getInt(0));
                contacto.setNombre(cursorContactos.getString(1));
                contacto.setTelefono(cursorContactos.getString(2));
                contacto.setCorreo_electronico(cursorContactos.getString(3));
            }
            cursorContactos.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return contacto;
    }


}
