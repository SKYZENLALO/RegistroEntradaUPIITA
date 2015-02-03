package com.develops.capiz.registroentradaupiita;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroAlumno extends ActionBarActivity {

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar));
		setContentView(R.layout.registra_alumno);
		((Button)findViewById(R.id.insertar)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view){
				String[] datos = {
					((EditText)findViewById(R.id.nombre)).getText().toString(),
					((EditText)findViewById(R.id.aPaterno)).getText().toString(),
					((EditText)findViewById(R.id.aMaterno)).getText().toString(),
					((EditText)findViewById(R.id.carrera)).getText().toString(),
					((EditText)findViewById(R.id.correo)).getText().toString(),
					((EditText)findViewById(R.id.escuela)).getText().toString()					
				};
				EntradaAlumnos bd = new EntradaAlumnos(RegistroAlumno.this);
				boolean success = bd.insertaRegistroAlumno(
					((EditText)findViewById(R.id.boleta)).getText().toString(), datos);
				if( !success )
					Toast.makeText(RegistroAlumno.this, "Error al guardar el alumno", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(RegistroAlumno.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
