package com.example.soporteinv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AgregarAlumnoActivity extends AppCompatActivity {

    ImageButton btnAgregar;
    ControlDB helper;
    EditText editNombre;
    EditText editID;
    EditText editApellido;
    ImageButton btnRegresar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_alumno);

        helper = new ControlDB(this);
        btnAgregar = findViewById(R.id.btnAgregarAl);
        editNombre =findViewById(R.id.editTextNombreAl);
        editID = findViewById(R.id.editTextAlumnoId);


        InputFilter[] editFilters = editID.getFilters();
        InputFilter[] newFilters = new InputFilter[editFilters.length + 1];
        System.arraycopy(editFilters, 0, newFilters, 0, editFilters.length);
        newFilters[editFilters.length] = new InputFilter.AllCaps();
        editID.setFilters(newFilters);



        editApellido = findViewById(R.id.editTextApellidoAl);
        btnRegresar = findViewById(R.id.btnRegresarAl);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuAlumnoActivity.class);
                startActivityForResult(intent,0);
            }
        });


    }

    public void insertarAlumno(View view){

        if(editID.getText().toString().isEmpty() || editNombre.getText().toString().isEmpty() || editApellido.getText().toString().isEmpty()){
            Toast.makeText(AgregarAlumnoActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_LONG).show();
        }else {

            String nombre = editNombre.getText().toString();
            String apellido = editApellido.getText().toString();
            String carnet = editID.getText().toString();

            String regInsertados;

            Alumno alumno = new Alumno();
            alumno.setNombre(nombre);
            alumno.setApellidos(apellido);
            alumno.setCarnet(carnet);
            helper.abrir();
            regInsertados = helper.insertar(alumno);
            helper.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_LONG).show();
        }
    }


}