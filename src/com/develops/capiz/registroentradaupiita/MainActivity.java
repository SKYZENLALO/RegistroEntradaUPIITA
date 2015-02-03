package com.develops.capiz.registroentradaupiita;

import java.util.LinkedList;
import java.util.ListIterator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private EditText input1;
	private EditText input2;

	// private EntradaAlumnos entradaUPIITA_bd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		getSupportActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.drawable.actionbar));
		setContentView(R.layout.activity_main);
		input1 = (EditText) findViewById(R.id.input_box1);
		input2 = (EditText) findViewById(R.id.input_box2);
		Button go = (Button) findViewById(R.id.go);
		Button registro = (Button) findViewById(R.id.registro);
		go.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				validaUsuario();
			}
		});
		registro.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				launchValidaRegistro();
			}
		});
		// entradaUPIITA_bd = new EntradaAlumnos(this);
	}

	public void validaUsuario() {
		String[] datos = { "Nombre", "Password" };
		String[] vals = { input1.getText().toString(),
				input2.getText().toString() };
		EntradaAlumnos entradaUPIITA_bd = new EntradaAlumnos(this);
		LinkedList<String> admins = entradaUPIITA_bd.consultaMacho(datos,
				"Nombre=? and Password=?", vals);
		ListIterator<String> adminIter = admins.listIterator();
		String allAdmins = new String();
		while (adminIter.hasNext()) {
			allAdmins = allAdmins.concat(adminIter.next() + "\n");
		}
		// Toast.makeText(this, allAdmins, Toast.LENGTH_LONG).show();
		if (admins.size() != 1) {
			Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT)
					.show();
		} else {
			launchQueryWindow();
		}
	}

	private void launchValidaRegistro() {
		Intent i = new Intent(this, ValidaRegistro.class);
		startActivity(i);
	}

	public void launchQueryWindow() {
		Intent i = new Intent(this, WelcomeAdmin.class);
		i.putExtra("usr_name", input1.getText().toString());
		startActivity(i);
	}
}
