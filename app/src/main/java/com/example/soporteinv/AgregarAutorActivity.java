package com.example.soporteinv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AgregarAutorActivity extends AppCompatActivity {

    ImageButton btnAgregar;
    ControlDB helper;
    EditText editNombre;
    EditText editID;
    ImageButton btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.agregar_autor);

        helper = new ControlDB(this);
        btnAgregar = findViewById(R.id.btnAgregar);
        editNombre =findViewById(R.id.editTextAutor);
        editID = findViewById(R.id.editTextAutorID);
        btnRegresar = findViewById(R.id.btnRegresar);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(intent,0);
            }
        });



    }

    public void insertarAutor(View view){

        if(editID.getText().toString().isEmpty() || editNombre.getText().toString().isEmpty()){
            Toast.makeText(AgregarAutorActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_LONG).show();
        }else {

            String nombre = editNombre.getText().toString();
            Integer id = Integer.valueOf(editID.getText().toString());
            String regInsertados;

            Autor autor = new Autor();
            autor.setNombreAutor(nombre);
            autor.setId(id);
            helper.abrir();
            regInsertados = helper.insertar(autor);
            helper.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_LONG).show();
        }
    }

}