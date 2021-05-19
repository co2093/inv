package com.example.soporteinv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProvider;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class MenuDocenteActivity extends AppCompatActivity {

    ImageButton btnAgregarDocente, btnEditarDocente, btnActualizar;
    ListView listadoDocente;
    ControlDB DBhelper;
    ArrayAdapter busquedaAdapter;
    ArrayAdapter docenteArrayAdapter;
    EditText editBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_docente);


        btnAgregarDocente = findViewById(R.id.btnAgregar);
        btnEditarDocente = findViewById(R.id.btnEditar);
        btnActualizar = findViewById(R.id.btnActualizar);
        listadoDocente = findViewById(R.id.listado_docente);
        editBusqueda = findViewById(R.id.editBuscar);

        btnAgregarDocente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AgregarDocenteActivity.class);
                startActivityForResult(intent,0);
            }
        });

        btnEditarDocente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditarDocenteActivity.class);
                startActivityForResult(intent,0);
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listaDocentes(DBhelper);
                editBusqueda.setText("");
            }
        });


        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listaDocentes(DBhelper);
                editBusqueda.setText("");
            }
        });

        DBhelper = new ControlDB(MenuDocenteActivity.this);
        List<Docente> docentesListado = DBhelper.getDocentes();

        listaDocentes(DBhelper);


        listadoDocente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Docente docente = (Docente) parent.getItemAtPosition(position);

                AlertDialog.Builder dialogo = new AlertDialog.Builder(MenuDocenteActivity.this);
                dialogo.setTitle("Eliminar Docente");
                dialogo.setMessage("Va a eliminar un docente");
                dialogo.setCancelable(false);
                dialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBhelper.eliminar(docente);
                        listaDocentes(DBhelper);

                        Toast.makeText(MenuDocenteActivity.this, "Eliminado " + docente.toString(), Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(MenuDocenteActivity.this, "VACIO",  Toast.LENGTH_LONG).show();
                        return true;
                    }else{

                        busquedaAdapter = new ArrayAdapter<Docente>(MenuDocenteActivity.this, android.R.layout.simple_expandable_list_item_1, DBhelper.consultaD(Integer.valueOf(editBusqueda.getText().toString())));
                        listadoDocente.setAdapter(busquedaAdapter);

                        if(busquedaAdapter.isEmpty()) {
                            Toast.makeText(MenuDocenteActivity.this, "No se ha encontrado registros con ese ID", Toast.LENGTH_LONG).show();
                        }

                        return true;
                    }

                }else{

                    return false;
                }
            }
        });








    }

    public void listaDocentes(ControlDB helper){
        docenteArrayAdapter = new ArrayAdapter<Docente>(MenuDocenteActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getDocentes());
        listadoDocente.setAdapter(docenteArrayAdapter);
    }

    public void cancelar(){
        Toast.makeText(MenuDocenteActivity.this, "Operacion cancelada", Toast.LENGTH_LONG).show();
    }
}