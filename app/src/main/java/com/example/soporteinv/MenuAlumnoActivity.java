package com.example.soporteinv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProvider;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.ImageButton;
import android.app.AlertDialog;

import java.util.List;

public class MenuAlumnoActivity extends AppCompatActivity {

    ImageButton btnAgregarAlumno, btnEditarAlumno, btnActualizar;
    ListView listadoAlumno;
    ControlDB DBhelper;
    ArrayAdapter busquedaAdapter;
    ArrayAdapter alumnoArrayAdapter;
    EditText editBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_alumno);

        btnAgregarAlumno = findViewById(R.id.btnAgregarAl);
        btnEditarAlumno = findViewById(R.id.btnEditarAl);
        btnActualizar = findViewById(R.id.btnActualizarAl);
        listadoAlumno = findViewById(R.id.listado_alumno);
        editBusqueda = findViewById(R.id.editBuscar);

        InputFilter[] editFilters = editBusqueda.getFilters();
        InputFilter[] newFilters = new InputFilter[editFilters.length + 1];
        System.arraycopy(editFilters, 0, newFilters, 0, editFilters.length);
        newFilters[editFilters.length] = new InputFilter.AllCaps();
        editBusqueda.setFilters(newFilters);


        btnAgregarAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AgregarAlumnoActivity.class);
                startActivityForResult(intent,0);
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listadoAlumno(DBhelper);
                editBusqueda.setText("");
            }
        });


        btnEditarAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditarAlumnoActivity.class);
                startActivityForResult(intent,0);
            }
        });

        DBhelper = new ControlDB(MenuAlumnoActivity.this);
        List<Alumno> alumnosListado = DBhelper.getAlumnos();

        listadoAlumno(DBhelper);

        listadoAlumno.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Alumno alumno = (Alumno) parent.getItemAtPosition(position);

                AlertDialog.Builder dialogo = new AlertDialog.Builder(MenuAlumnoActivity.this);
                dialogo.setTitle("Eliminar Alumno");
                dialogo.setMessage("Va a eliminar un alumno");
                dialogo.setCancelable(false);
                dialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBhelper.eliminar(alumno.getCarnet());
                        listadoAlumno(DBhelper);

                        Toast.makeText(MenuAlumnoActivity.this, "Eliminado " + alumno.toString(), Toast.LENGTH_SHORT).show();

                    }
                });

                dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        cancelar();
                    }
                });

                dialogo.show();
            }
        });



        editBusqueda.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if((event.getAction() == KeyEvent.ACTION_DOWN)&&(keyCode == KeyEvent.KEYCODE_ENTER)){

                    if(editBusqueda.getText().toString().isEmpty()){
                        Toast.makeText(MenuAlumnoActivity.this, "VACIO",  Toast.LENGTH_LONG).show();
                        return true;
                    }else{

                        busquedaAdapter = new ArrayAdapter<Alumno>(MenuAlumnoActivity.this, android.R.layout.simple_expandable_list_item_1, DBhelper.consultaA(editBusqueda.getText().toString()));
                        listadoAlumno.setAdapter(busquedaAdapter);

                        if(busquedaAdapter.isEmpty()) {
                            Toast.makeText(MenuAlumnoActivity.this, "No se ha encontrado registros con ese Carnet", Toast.LENGTH_LONG).show();
                        }


                        return true;
                    }

                }else{

                    return false;
                }
            }
        });

    }

    public void listadoAlumno(ControlDB helper){
        alumnoArrayAdapter= new ArrayAdapter<Alumno>(MenuAlumnoActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getAlumnos());
        listadoAlumno.setAdapter(alumnoArrayAdapter);
    }

    public void cancelar(){
        Toast.makeText(MenuAlumnoActivity.this, "Operacion cancelada", Toast.LENGTH_LONG).show();
    }


}