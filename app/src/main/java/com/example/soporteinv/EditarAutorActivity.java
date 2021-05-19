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

public class EditarAutorActivity extends AppCompatActivity {

    ImageButton btnAgregar;
    ControlDB helper;
    EditText editNombre;
    EditText editID;
    ImageButton btnRegresar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_autor);

        btnRegresar = findViewById(R.id.btnRegresar);

        helper = new ControlDB(this);
        btnAgregar = findViewById(R.id.btnAgregar);
        editNombre =findViewById(R.id.editTextAutor);
        editID = findViewById(R.id.editTextAutorID);


        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(intent,0);
            }
        });
    }

    public void actualizarAutor(View V){

        if(editID.getText().toString().isEmpty() || editNombre.getText().toString().isEmpty()){
            Toast.makeText(EditarAutorActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_LONG).show();
        }else {
            Autor autor = new Autor();
            autor.setId(Integer.valueOf(editID.getText().toString()));
            autor.setNombreAutor(editNombre.getText().toString());
            helper.abrir();
            String estado = helper.actualizar(autor);
            helper.cerrar();

            Toast.makeText(this, estado, Toast.LENGTH_LONG).show();


        }
    }
}