package com.develops.capiz.registroentradaupiita;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ValidaRegistro extends ActionBarActivity {

	private Button yes;
	private Button no;
	private TextView label;
	private EditText psswdField;

	@Override
	protected void onCreate(Bundle b) {
		super.onCreate(b);
		getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar));
		setContentView(R.layout.valida_registro);
		yes = (Button) findViewById(R.id.yes);
		no = (Button) findViewById(R.id.no);
		label = (TextView) findViewById(R.id.valida_pablo);
		psswdField = (EditText) findViewById(R.id.pablo_psswd);
		yes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!yes.getText().toString().equals("Validar")) {
					no.setVisibility(Button.INVISIBLE);
					label.setVisibility(TextView.VISIBLE);
					psswdField.setVisibility(EditText.VISIBLE);
					yes.setText("Validar");
				} else {
					if (checaRegistro()) {
						launchElementoNuevo();
						yes.setText("YES");
						no.setVisibility(Button.VISIBLE);
						label.setVisibility(TextView.INVISIBLE);
						psswdField.setVisibility(EditText.INVISIBLE);
					}
				}
			}
		});
		no.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				launchMensaje("Tons buscas a Pablo y le dices que quieres registrarte jaja");
			}
		});
	}

	public void launchMensaje(String msj) {
		Intent i = new Intent(this,Mensaje.class);
		i.putExtra("msj", msj);
		startActivity(i);
	}

	public void launchElementoNuevo() {
		Intent i = new Intent(this, GuardianManager.class);
		startActivity(i);
	}

	public boolean checaRegistro() {
		EntradaAlumnos bd = new EntradaAlumnos(this);
		String[] datos = { "Password" };
		String[] vals = { psswdField.getText().toString() };
		if (bd.consultaMacho(datos, "Password=?", vals).size() != 1) {
			Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT)
					.show();
			return false;
		} else {
			return true;
		}
	}
}
