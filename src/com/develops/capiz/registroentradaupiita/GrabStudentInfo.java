package com.develops.capiz.registroentradaupiita;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GrabStudentInfo extends ActionBarActivity {

	private String[] datos;
	private String boleta;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar));
		setContentView(R.layout.get_alumno_info);
		Bundle extras = getIntent().getExtras();
		String nombre = extras.getString("nombre");
		boleta = extras.getString("Boleta");
		datos = nombre.split(" ");
		((Button) findViewById(R.id.registro))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						String nombre = datos[0];
						String apPaterno = null;
						String apMaterno = null;
						try {
							apPaterno = datos[1];
							apMaterno = datos[2];
						} catch (IndexOutOfBoundsException e) {

						}
						String datos[] = {
								nombre,
								apPaterno,
								apMaterno,
								((EditText) findViewById(R.id.carrera))
										.getText().toString(),
								((EditText) findViewById(R.id.correo))
										.getText().toString(),
								((EditText) findViewById(R.id.escuela))
										.getText().toString() };
						EntradaAlumnos db = new EntradaAlumnos(GrabStudentInfo.this);
						Intent i;
						if(!db.insertaRegistroAlumno(boleta, datos)){
							i = new Intent(GrabStudentInfo.this,Mensaje.class);
							i.putExtra("msj", "Error, el alumno ya existe");
							i.putExtra("isChoice", false);
							startActivity(i);
						}else{
							Intent data = new Intent();
							data.putExtra("success", true);
							setResult(RESULT_OK,data);
							finish();
						}
					}
				});
	}
}
