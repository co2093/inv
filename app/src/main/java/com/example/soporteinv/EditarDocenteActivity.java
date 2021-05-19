package com.example.soporteinv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class EditarDocenteActivity extends AppCompatActivity {


    ImageButton btnAgregar;
    ControlDB helper;
    EditText editNombre;
    EditText editID;
    EditText editApellido;
    ImageButton btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_docente);

        btnRegresar = findViewById(R.id.btnRegresar);

        helper = new ControlDB(this);
        btnAgregar = findViewById(R.id.btnAgregar);
        editNombre =findViewById(R.id.editTextNombreD);
        editID = findViewById(R.id.editTextDocenteIDD);
        editApellido = findViewById(R.id.editTextApellidoD);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuDocenteActivity.class);
                startActivityForResult(intent,0);
            }
        });

    }

    public void actualizarDocente(View V){

        if(editID.getText().toString().isEmpty() || editNombre.getText().toString().isEmpty() || editApellido.getText().toString().isEmpty()){
            Toast.makeText(EditarDocenteActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_LONG).show();
        }else {
            Docente docente = new Docente();
            docente.setId(Integer.valueOf(editID.getText().toString()));
            docente.setNombre(editNombre.getText().toString());
            docente.setApellido(editApellido.getText().toString());
            helper.abrir();
            String estado = helper.actualizar(docente);
            helper.cerrar();

            Toast.makeText(this, estado, Toast.LENGTH_LONG).show();


        }
    }



}