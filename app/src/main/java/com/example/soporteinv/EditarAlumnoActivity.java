package com.example.soporteinv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class EditarAlumnoActivity extends AppCompatActivity {

    ImageButton btnAgregar;
    ControlDB helper;
    EditText editNombre;
    EditText editID;
    EditText editApellido;
    ImageButton btnRegresar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_alumno);

        helper = new ControlDB(this);
        btnAgregar = findViewById(R.id.btnAgregarAlEd);
        editNombre =findViewById(R.id.editTextNombreAlEd);
        editID = findViewById(R.id.editTextAlIDEd);
        InputFilter[] editFilters = editID.getFilters();
        InputFilter[] newFilters = new InputFilter[editFilters.length + 1];
        System.arraycopy(editFilters, 0, newFilters, 0, editFilters.length);
        newFilters[editFilters.length] = new InputFilter.AllCaps();
        editID.setFilters(newFilters);


        editApellido = findViewById(R.id.editTextApellidoAlEd);
        btnRegresar2 = findViewById(R.id.btnRegresarAlEd);

        btnRegresar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuAlumnoActivity.class);

                startActivityForResult(intent, 0);

            }
        });



    }


    public void actualizarAlumno(View V){

        if(editID.getText().toString().isEmpty() || editNombre.getText().toString().isEmpty() || editApellido.getText().toString().isEmpty()){
            Toast.makeText(EditarAlumnoActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_LONG).show();
        }else {
            Alumno alumno = new Alumno();
            alumno.setCarnet(editID.getText().toString());
            alumno.setNombre(editNombre.getText().toString());
            alumno.setApellidos(editApellido.getText().toString());
            helper.abrir();
            String estado = helper.actualizar(alumno);
            helper.cerrar();

            Toast.makeText(this, estado, Toast.LENGTH_LONG).show();


        }
    }

}