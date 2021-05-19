package com.example.soporteinv;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    ImageButton btnAgregarAutor, btnEditarAutor, btnActualizar;
    ListView listadoAutor;
    ControlDB DBhelper;
    ArrayAdapter autorArrayAdapter;
    ArrayAdapter busquedaAdapter;
    EditText editBusqueda;



    String[] values={
            "Facebook", "Youtube", "Twitter"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAgregarAutor = findViewById(R.id.btnAgregar);
        btnEditarAutor = findViewById(R.id.btnEditar);
        btnActualizar = findViewById(R.id.btnActualizar);
        listadoAutor = findViewById(R.id.listado_autor);
        editBusqueda = findViewById(R.id.editBuscar);

        btnEditarAutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditarAutorActivity.class);
                startActivityForResult(intent,0);
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listaAutores(DBhelper);
                editBusqueda.setText("");
            }
        });

        btnAgregarAutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AgregarAutorActivity.class);
                        startActivityForResult(intent,0);
            }
        });


        DBhelper = new ControlDB(MainActivity.this);
        List<Autor> everyone = DBhelper.getEveryone();

        listaAutores(DBhelper);




        listadoAutor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Autor autor = (Autor) parent.getItemAtPosition(position);

                AlertDialog.Builder dialogo = new AlertDialog.Builder(MainActivity.this);
                dialogo.setTitle("Eliminar Autor");
                dialogo.setMessage("Va a eliminar un autor");
                dialogo.setCancelable(false);
                dialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBhelper.eliminar(autor);
                        listaAutores(DBhelper);

                        Toast.makeText(MainActivity.this, "Eliminado " + autor.toString(), Toast.LENGTH_SHORT).show();

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

        TextView textView = (TextView) findViewById(R.id.textHome);
        SpannableString mitextoU = new SpannableString("Home");
        mitextoU.setSpan(new UnderlineSpan(), 0, mitextoU.length(), 0);
        textView.setText(mitextoU);

        editBusqueda.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if((event.getAction() == KeyEvent.ACTION_DOWN)&&(keyCode == KeyEvent.KEYCODE_ENTER)){

                    if(editBusqueda.getText().toString().isEmpty()){
                        Toast.makeText(MainActivity.this, "VACIO",  Toast.LENGTH_LONG).show();
                        return true;
                    }else{

                            busquedaAdapter = new ArrayAdapter<Autor>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, DBhelper.consulta(Integer.valueOf(editBusqueda.getText().toString())));

                            listadoAutor.setAdapter(busquedaAdapter);

                        if(busquedaAdapter.isEmpty()) {
                            Toast.makeText(MainActivity.this, "No se ha encontrado registros con ese ID", Toast.LENGTH_LONG).show();
                        }

                        return true;
                    }

                }else{

                return false;
                }
            }
        });


    }

    private void listaAutores(ControlDB helper) {
         autorArrayAdapter = new ArrayAdapter<Autor>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getEveryone());
        listadoAutor.setAdapter(autorArrayAdapter);
    }

    public void cancelar(){
        Toast.makeText(MainActivity.this, "Operacion cancelada", Toast.LENGTH_LONG).show();
    }



}