package com.example.soporteinv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AgregarDocenteActivity extends AppCompatActivity {

    ImageButton btnAgregar;
    ControlDB helper;
    EditText editNombre;
    EditText editID;
    EditText editApellido;
    ImageButton btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_docente);

        helper = new ControlDB(this);
        btnAgregar = findViewById(R.id.btnAgregar);
        editNombre =findViewById(R.id.editTextNombre);
        editID = findViewById(R.id.editTextDocenteID);
        editApellido = findViewById(R.id.editTextApellido);
        btnRegresar = findViewById(R.id.btnRegresar);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuDocenteActivity.class);
                startActivityForResult(intent,0);
            }
        });

    }

    public void insertarDocente(View view){

        if(editID.getText().toString().isEmpty() || editNombre.getText().toString().isEmpty() || editApellido.getText().toString().isEmpty()){
            Toast.makeText(AgregarDocenteActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_LONG).show();
        }else {

            String nombre = editNombre.getText().toString();
            String apellido = editApellido.getText().toString();
            Integer id = Integer.valueOf(editID.getText().toString());

            String regInsertados;

            Docente docente = new Docente();
            docente.setNombre(nombre);
            docente.setApellido(apellido);
            docente.setId(id);
            helper.abrir();
            regInsertados = helper.insertar(docente);
            helper.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_LONG).show();
        }
    }


}