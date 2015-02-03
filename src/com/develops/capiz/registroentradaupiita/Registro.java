package com.develops.capiz.registroentradaupiita;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Registro extends ActionBarActivity {

	private static final int MENSAJE_ID = 134;
	private static final int REGISTRY_ID = 319;
	private ImageView logo;
	private TextView errorMessage;
	private EditText EditText1;
	private EditText EditText2;
	private EditText EditText3;
	private EditText EditText4;
	private RelativeLayout container0;
	private RelativeLayout container1;
	private RelativeLayout container2;
	private RelativeLayout container3;
	private Button go2;
	private EntradaAlumnos entradaAlumnosUPIITA;

	@Override
	protected void onCreate(Bundle b) {
		super.onCreate(b);
		getSupportActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.drawable.actionbar));
		setContentView(R.layout.elemento_nuevo);
		logo = (ImageView) findViewById(R.id.logo3);
		errorMessage = (TextView) findViewById(R.id.error_message);
		errorMessage.setTextColor(Color.RED);
		EditText1 = (EditText) findViewById(R.id.input_name_register1);
		EditText2 = (EditText) findViewById(R.id.input_boleta_register1);
		EditText3 = (EditText) findViewById(R.id.input_psswd_register1);
		EditText4 = (EditText) findViewById(R.id.input_psswd_register2);
		container0 = (RelativeLayout) findViewById(R.id.container);
		container1 = (RelativeLayout) findViewById(R.id.container1);
		container2 = (RelativeLayout) findViewById(R.id.container2);
		container3 = (RelativeLayout) findViewById(R.id.container3);
		go2 = (Button) findViewById(R.id.go2);
		entradaAlumnosUPIITA = new EntradaAlumnos(this);
		go2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String[] fullName = EditText1.getText().toString().split(" ");
				String nombre = fullName[0];
				String apPaterno = null;
				String apMaterno = null;
				try {
					apPaterno = fullName[1];
					apMaterno = fullName[2];
				} catch (IndexOutOfBoundsException e) {
				}
				String[] datos = { nombre, apPaterno, apMaterno,
						EditText2.getText().toString(),
						EditText3.getText().toString(),
						EditText4.getText().toString() };
				if (datos[0].length() == 0) {
					errorMessage.setText("Debe escribir un nombre de usuario.");
					container0.setVisibility(View.VISIBLE);
					logo.setVisibility(View.INVISIBLE);
				}
				if (datos[3].length() != 10) {
					errorMessage
							.setText("La boleta debe ser de 10 caracteres.");
					container1.setVisibility(View.VISIBLE);
					logo.setVisibility(View.INVISIBLE);
				}
				if (datos[4].equals(datos[5])) {
					if (datos[4].length() > 5) {
						String[] admin = { datos[0], datos[4] };
						String result = entradaAlumnosUPIITA
								.insertaRegistroAdmin(datos[3], admin);
						Toast.makeText(Registro.this, result, Toast.LENGTH_LONG)
								.show();
						if (result.equals("OK"))
							limpiarPantalla();
						else
							launchRegistraAlumno();
						// insertarValoresMacho(datos);
					} else {
						errorMessage
								.setText("Debes escribir una contraseña con más de 5 caracteres.");
						container2.setVisibility(View.VISIBLE);
						container3.setVisibility(View.VISIBLE);
						EditText3.setText("");
						EditText4.setText("");
						logo.setVisibility(View.INVISIBLE);
					}
				} else {
					errorMessage.setText("Las contraseñas no coinciden.");
					EditText4.setText("");
					logo.setVisibility(View.INVISIBLE);
					Toast.makeText(Registro.this, "Valores incorrectos",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	private void launchRegistraAlumno() {
		Intent i = new Intent(this, Mensaje.class);
		i.putExtra("msj", "¿Desea registrar al alumno?");
		i.putExtra("isChoice", true);
		startActivityForResult(i, MENSAJE_ID);
	}

	private void limpiarPantalla() {
		EditText1.setText("");
		EditText2.setText("");
		EditText3.setText("");
		EditText4.setText("");
		errorMessage.setText(""); 
		container0.setVisibility(View.INVISIBLE);
		container1.setVisibility(View.INVISIBLE);
		container2.setVisibility(View.INVISIBLE);
		container3.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case MENSAJE_ID:
				if (data.getBooleanExtra("response", false)) {
					Intent i = new Intent(this, GrabStudentInfo.class);
					i.putExtra("Boleta", EditText2.getText().toString());
					i.putExtra("nombre", EditText1.getText().toString());
					startActivityForResult(i, REGISTRY_ID);
				} else {
					limpiarPantalla();
					Toast.makeText(this, "n.n", Toast.LENGTH_SHORT).show();
				}
				break;
			case REGISTRY_ID:
				if (data.getBooleanExtra("success", false)) {
					String[] datos = {EditText1.getText().toString(), EditText3.getText().toString()};
					String result = entradaAlumnosUPIITA.insertaRegistroAdmin(
							EditText2.getText().toString(), datos);
					if( !result.equals("Nope") ){
						Toast.makeText(this, "Admin registrado", Toast.LENGTH_SHORT).show();
						limpiarPantalla();
					}else
						Toast.makeText(this, "Admin ya existe", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
}
