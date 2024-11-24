package com.example.proyecto_finalmov;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Vector;

public class DBSQLite extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "UserDatabase";
    public static final String TABLE_USUARIOS = "usuarios";
    public static final String TABLE_SESION = "sesion_actual";
    public static final int DATABASE_VERSION = 2; // Cambia a la siguiente versión


    public DBSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla de usuarios
        String createTableUsuarios = "CREATE TABLE usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "edad INTEGER NOT NULL)";

        // Crear tabla para la sesión actual
        String createTableSesion = "CREATE TABLE sesion_actual (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuario_id INTEGER NOT NULL, " +
                "nombre TEXT NOT NULL, " +
                "edad INTEGER NOT NULL)";

        db.execSQL(createTableUsuarios);
        db.execSQL(createTableSesion);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS sesion_actual");
        onCreate(db);
    }


    // Guardar usuario en la base de datos
    public long guardarUsuario(String nombre, int edad) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("edad", edad);

        long userId = db.insert(TABLE_USUARIOS, null, values);
        Log.d("DBSQLite", "Usuario guardado con ID: " + userId); // DEPURACIÓN
        return userId;
    }



    // Establecer usuario actual (sesión)
    public void establecerUsuarioActual(long userId, String nombre, int edad) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SESION, null, null); // Limpia la tabla

        ContentValues values = new ContentValues();
        values.put("usuario_id", userId);
        values.put("nombre", nombre);
        values.put("edad", edad);

        long result = db.insert(TABLE_SESION, null, values);
        if (result != -1) {
            Log.d("DBSQLite", "Usuario actual registrado: ID=" + userId + ", Nombre=" + nombre + ", Edad=" + edad); // DEPURACIÓN
        } else {
            Log.e("DBSQLite", "Error al registrar el usuario actual en la tabla sesion_actual");
        }
    }



    public String[] obtenerUsuarioActual() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT nombre, edad FROM " + TABLE_SESION + " LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            String nombre = cursor.getString(0);
            int edad = cursor.getInt(1);
            cursor.close();
            Log.d("DBSQLite", "Usuario actual encontrado: Nombre=" + nombre + ", Edad=" + edad); // DEPURACIÓN
            return new String[]{nombre, String.valueOf(edad)};
        } else {
            cursor.close();
            Log.d("DBSQLite", "No se encontró un usuario actual en la tabla sesion_actual."); // DEPURACIÓN
            return null;
        }
    }

    // Cerrar sesión
    public void cerrarSesion() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SESION, null, null);
    }

    public Vector listarUsuarios() {
        Vector usuarios = new Vector<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, nombre, edad FROM " + TABLE_USUARIOS + " ORDER BY id", null);
        while (cursor.moveToNext()) {
            usuarios.add("ID: " + cursor.getInt(0) + ", Nombre: " + cursor.getString(1) + ", Edad: " + cursor.getInt(2));
        }
        cursor.close();
        return usuarios;
    }

    public void eliminarUsuarios() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_USUARIOS);
        db.execSQL("DELETE FROM " + TABLE_SESION);
        db.close();
    }

    public boolean existeUsuario(String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT 1 FROM " + TABLE_USUARIOS + " WHERE nombre = ?";
        Cursor cursor = db.rawQuery(query, new String[]{nombre});
        boolean existe = cursor.moveToFirst();
        cursor.close();
        return existe;
    }



}
