package com.example.soporteinv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ControlDB {

    private static final String [] camposAutor = new String[] {"id", "nombre"};
    private static final String [] camposAlumno = new String[] {"carnet", "nombre", "apellido"};

    private final Context context;
    private SQLiteDatabase db;

    private DatabaseHelper DBHelper;


    public ControlDB(Context context) {
        this.context = context;
        DBHelper = new DatabaseHelper(context);

    }

    private static class DatabaseHelper extends SQLiteOpenHelper{

        private static final String BASE_DATOS = "inventariopdm.s3db";
        private static final int VERSION = 1;

        public DatabaseHelper(Context context){

            super(context, BASE_DATOS, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {


            try{

                db.execSQL("CREATE TABLE autor(id INTEGER NOT NULL PRIMARY KEY, nombre VARCHAR (128));");
                db.execSQL("CREATE TABLE docente(id INTEGER NOT NULL PRIMARY KEY, nombre VARCHAR (128), apellido VARCHAR(128));");
                db.execSQL("DROP TABLE IF EXISTS alumno");
                db.execSQL("CREATE TABLE alumno (carnet VARCHAR(128) NOT NULL PRIMARY KEY, nombre VARCHAR (128), apellido VARCHAR(128));");

            }catch (SQLException exception){
                exception.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public void abrir() throws SQLException{
        db = DBHelper.getWritableDatabase();
        return;
    }

    public void cerrar(){
        DBHelper.close();
    }

    public String insertar (Autor autor){

        if(verificarIntegridad(autor,1)){
            return "Ya existe un autor con este ID";
        }else {
            String regInsertado = "Registro Insertado Numero = ";

            long contador = 0;

            ContentValues values = new ContentValues();
            values.put("id", autor.getId());
            values.put("nombre", autor.getNombreAutor());
            contador = db.insert("autor", null, values);

            if (contador == -1 || contador == 0) {
                regInsertado = "ERROR";
            } else {
                regInsertado = regInsertado + contador;
            }

            return regInsertado;

        }

    }

    public String insertar(Docente docente){

        if(verificarIntegridad(docente, 2)){
            return "Ya existe un docente con este ID";
        }else{
            String regInsertado = "Registro Insertado Numero = ";
            long contador = 0;

            ContentValues contentValues = new ContentValues();
            contentValues.put("id", docente.getId());
            contentValues.put("nombre", docente.getNombre());
            contentValues.put("apellido", docente.getApellido());

            contador = db.insert("docente", null, contentValues);

            if (contador == -1 || contador == 0) {
                regInsertado = "ERROR";
            } else {
                regInsertado = regInsertado + contador;
            }

            return regInsertado;

        }

    }

    public String insertar(Alumno alumno){

        if (verificarIntegridad(alumno, 3)){
            return "Ya existe un alumno con este ID";
        }else{
            String regInsertado = "Registro Insertado Numero  = ";
            long contador = 0;

            ContentValues contentValues = new ContentValues();
            contentValues.put("carnet", alumno.getCarnet());
            contentValues.put("nombre", alumno.getNombre());
            contentValues.put("apellido", alumno.getApellidos());

            contador = db.insert("alumno", null, contentValues);

            if (contador == -1 || contador == 0) {
                regInsertado = "ERROR";
            } else {
                regInsertado = regInsertado + contador;
            }

            return regInsertado;


        }
    }

    public List<Docente> getDocentes(){
        List<Docente> lista = new ArrayList<>();
        String queryString = "SELECT * FROM docente";
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String nombre = cursor.getString(1);
                String apellido = cursor.getString(2);
                Docente docente = new Docente(id,nombre, apellido);
                lista.add(docente);
            }while (cursor.moveToNext());
        }else{

        }
        cursor.close();
        db.close();
        return lista;
    }

    public List<Alumno> getAlumnos(){
        List<Alumno> lista = new ArrayList<>();
        String queryString = "SELECT * FROM alumno";
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                String carnet = cursor.getString(0);
                String nombre = cursor.getString(1);
                String apellido = cursor.getString(2);
                Alumno alumno = new Alumno(carnet,nombre, apellido);
                lista.add(alumno);
            }while (cursor.moveToNext());
        }else{

        }
        cursor.close();
        db.close();
        return lista;
    }

    public List<Autor> getEveryone(){
        List<Autor> lista = new ArrayList<>();

        String queryString = "SELECT * FROM autor";

        SQLiteDatabase db = DBHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String nombre = cursor.getString(1);

                Autor autor =  new Autor(id, nombre);
                lista.add(autor);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return lista;
    }

    public List<Autor> consulta(int idd){
        List<Autor> lista = new ArrayList<>();

        String queryString = "SELECT * FROM autor WHERE id = " + idd ;

        SQLiteDatabase db = DBHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String nombre = cursor.getString(1);

                Autor autor =  new Autor(id, nombre);
                lista.add(autor);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return lista;

    }

    public List<Docente> consultaD(int idd){
        List<Docente> lista = new ArrayList<>();

        String queryString = "SELECT * FROM docente WHERE id = " + idd ;

        SQLiteDatabase db = DBHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String nombre = cursor.getString(1);
                String apellido = cursor.getString(2);

                Docente docente =  new Docente(id, nombre, apellido);
                lista.add(docente);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return lista;

    }

    public List<Alumno> consultaA(String carnet){
        List<Alumno> lista = new ArrayList<>();

       // String queryString = "SELECT * FROM alumno WHERE carnet = " + carnet ;



        SQLiteDatabase db = DBHelper.getReadableDatabase();

        //Cursor cursor = db.rawQuery(queryString, null);

        String[] carnetd = {carnet};

        Cursor cursor = db.query("alumno", camposAlumno, "carnet = ?", carnetd, null,null,null);

        if(cursor.moveToFirst()){
            do {
                String carnet2 = cursor.getString(0);
                String nombre = cursor.getString(1);
                String apellido = cursor.getString(2);

                Alumno alumno =  new Alumno(carnet2, nombre, apellido);
                lista.add(alumno);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return lista;

    }



    public boolean eliminar(Autor autor){

        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String queryString = "DELETE FROM autor WHERE id = " + autor.getId();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }

    }

    public boolean eliminar(String alumno){

        String [] id = {alumno};
        SQLiteDatabase db = DBHelper.getWritableDatabase();


            db.delete("alumno", "carnet = ?",  id);

            return true;

    }


    public boolean eliminar(Docente docente){
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String queryString = "DELETE FROM docente WHERE id = " + docente.getId();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }
    }

    public String actualizar(Autor autor) {

        if (verificarIntegridad(autor, 1)){
            String[] id = {String.valueOf(autor.getId())};
        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre", autor.getNombreAutor());

        db.update("autor", contentValues, "id = ?", id);

        return "Actualizado";
    }
        else {
            return "Registro no existe";
        }

    }

    public String actualizar(Alumno alumno){
        if(verificarIntegridad(alumno,3)){
            String[] id = {alumno.getCarnet()};
            ContentValues contentValues = new ContentValues();
            contentValues.put("nombre", alumno.getNombre());
            contentValues.put("apellido", alumno.getApellidos());

            db.update("alumno", contentValues, "carnet = ?", id);
            return "Actualizado";
        }else {
            return "Registro no existe";
        }
    }

    public String actualizar(Docente docente) {

        if (verificarIntegridad(docente, 2)){
            String[] id = {String.valueOf(docente.getId())};
            ContentValues contentValues = new ContentValues();
            contentValues.put("nombre", docente.getNombre());
            contentValues.put("apellido", docente.getApellido());

            db.update("docente", contentValues, "id = ?", id);

            return "Actualizado";
        }
        else {
            return "Registro no existe";
        }

    }

    public boolean verificarIntegridad(Object dato, int relacion)throws SQLException {

        switch (relacion) {
            case 1:
            {
                Autor autor = (Autor) dato;
                String [] id  = {String.valueOf(autor.getId())};
                abrir();
                Cursor cursor = db.query("autor", null, "id = ?", id, null, null, null);

                if (cursor.moveToFirst()){
                    return true;
                }else {
                    return false;
                }

            }

            case 2:
            {
                Docente docente = (Docente) dato;
                String [] id = {String.valueOf(docente.getId())};
                abrir();
                Cursor cursor = db.query("docente", null, "id = ?", id, null, null, null);

                if (cursor.moveToFirst()){
                    return true;

                }else {
                    return false;
                }

            }

            case 3:
            {
                Alumno alumno = (Alumno)dato;
                String [] id = {String.valueOf(alumno.getCarnet())};
                abrir();
                Cursor cursor = db.query("alumno", null, "carnet = ?", id, null, null, null);

                if(cursor.moveToFirst()){
                    return true;
                }else {
                    return false;
                }


            }

            default:
                return false;
        }
    }

}
