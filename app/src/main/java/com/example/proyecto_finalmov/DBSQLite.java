package com.example.proyecto_finalmov;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBSQLite extends SQLiteOpenHelper {
    // Constantes
    public static final String DATABASE_NAME = "UserDatabase";
    public static final String TABLE_USUARIOS = "usuarios";
    public static final String TABLE_SESION = "sesion_actual";
    public static final int DATABASE_VERSION = 3;

    // Constructor
    public DBSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear las tablas
        String createTableUsuarios = "CREATE TABLE usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "edad INTEGER NOT NULL, " +
                "nivel INTEGER DEFAULT 0)";

        String createTableSesion = "CREATE TABLE sesion_actual (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuario_id INTEGER NOT NULL, " +
                "nombre TEXT NOT NULL, " +
                "edad INTEGER NOT NULL, " +
                "nivel INTEGER DEFAULT 0, " +
                "FOREIGN KEY (usuario_id) REFERENCES usuarios(id))";

        db.execSQL(createTableUsuarios);
        db.execSQL(createTableSesion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            // Actualización de la base de datos
            db.execSQL("DROP TABLE IF EXISTS sesion_actual");
            db.execSQL("DROP TABLE IF EXISTS usuarios");
            onCreate(db); // Si hay un cambio en la versión de la base de datos
        }
    }

    // Guardar usuario con nivel
    public long guardarUsuario(String nombre, int edad, int nivel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("edad", edad);
        values.put("nivel", nivel);

        long userId = db.insert(TABLE_USUARIOS, null, values);
        Log.d("DBSQLite", "Usuario guardado con ID: " + userId);
        return userId;
    }

    // Establecer usuario actual con nivel
    public void establecerUsuarioActual(long userId, String nombre, int edad, int nivel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SESION, null, null); // Limpia la tabla sesión

        ContentValues values = new ContentValues();
        values.put("usuario_id", userId);
        values.put("nombre", nombre);
        values.put("edad", edad);
        values.put("nivel", nivel);

        long result = db.insert(TABLE_SESION, null, values);
        if (result != -1) {
            Log.d("DBSQLite", "Usuario actual registrado: " + nombre + ", Nivel=" + nivel);
        } else {
            Log.e("DBSQLite", "Error al registrar usuario actual");
        }
    }

    // Obtener usuario actual con nivel
    public String[] obtenerUsuarioActual() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT nombre, edad, nivel FROM " + TABLE_SESION + " LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            String nombre = cursor.getString(0);
            int edad = cursor.getInt(1);
            int nivel = cursor.getInt(2);
            cursor.close();
            Log.d("DBSQLite", "Usuario actual encontrado: " + nombre + ", Edad=" + edad + ", Nivel=" + nivel);
            return new String[]{nombre, String.valueOf(edad), String.valueOf(nivel)};
        } else {
            cursor.close();
            Log.d("DBSQLite", "No se encontró usuario actual");
            return null;
        }
    }

    // Obtener nivel de usuario
    public int obtenerNivelActual(String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();
        int nivel = 0;

        try (Cursor cursor = db.rawQuery(
                "SELECT nivel FROM " + TABLE_USUARIOS + " WHERE nombre = ?",
                new String[]{nombre})) {
            if (cursor.moveToFirst()) {
                nivel = cursor.getInt(cursor.getColumnIndexOrThrow("nivel"));
            }
        } catch (Exception e) {
            Log.e("DBSQLite", "Error al obtener nivel", e);
        }

        return nivel;
    }

    // Método para listar las tablas en la base de datos
    public void listarTablas() {
        SQLiteDatabase db = this.getReadableDatabase();
        // Consulta para obtener las tablas de la base de datos
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        // Imprimir las tablas en el Logcat
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String tableName = cursor.getString(0);
                Log.d("DBTables", "Tabla: " + tableName);
            }
            cursor.close();
        }
        db.close();
    }

    // Método para verificar si un usuario ya existe en la base de datos
    public boolean existeUsuario(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Consulta para verificar si el usuario existe
        Cursor cursor = db.rawQuery("SELECT 1 FROM usuarios WHERE nombre = ?", new String[]{username});

        // Verificamos si se obtuvo un resultado
        boolean existe = cursor.moveToFirst();
        cursor.close();
        db.close();

        return existe;
    }
}
