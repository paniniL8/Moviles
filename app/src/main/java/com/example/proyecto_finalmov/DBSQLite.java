package com.example.proyecto_finalmov;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Vector;

public class DBSQLite extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "UserDatabase";
    public static final int DATABASE_VERSION = 1;

    public DBSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, " +
                "edad INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }

    public void guardarUsuario(String nombre, int edad) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO usuarios (nombre, edad) VALUES ('" + nombre + "', " + edad + ")");
    }

    public Vector<String> listarUsuarios() {
        Vector<String> usuarios = new Vector<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, nombre, edad FROM usuarios ORDER BY id", null);
        while (cursor.moveToNext()) {
            usuarios.add("ID: " + cursor.getInt(0) +
                    ", Nombre: " + cursor.getString(1) +
                    ", Edad: " + cursor.getInt(2));
        }
        cursor.close();
        return usuarios;
    }

    public void eliminarUsuarios() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM usuarios");
        db.close();
    }

    public String obtenerUltimoUsuario() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT nombre, edad FROM usuarios ORDER BY id DESC LIMIT 1", null);
        String resultado = "";
        if (cursor.moveToFirst()) {
            String nombre = cursor.getString(0);
            int edad = cursor.getInt(1);
            resultado = "Nombre: " + nombre + ", Edad: " + edad;
        }
        cursor.close();
        return resultado;
    }


}
