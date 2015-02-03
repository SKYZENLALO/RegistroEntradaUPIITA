package com.develops.capiz.registroentradaupiita;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeAdmin extends ActionBarActivity {

	private TextView name;
	private TextView lastTimeConnected;
	private TextView date;
	private TextView lastLocation;
	private static String usrName;
	private static String last_time_connected;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar));
		setContentView(R.layout.welcome_layout);
		name = (TextView)findViewById(R.id.user_name);
		lastTimeConnected = (TextView)findViewById(R.id.last_time_connected);
		lastLocation = (TextView)findViewById(R.id.last_location);
		usrName = getIntent().getExtras().getString("usr_name");
		name.setText("Bienvenido\n" + usrName);
		if( savedInstanceState == null ){
			last_time_connected = (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).format(new Date());
		}
		lastTimeConnected.setText(last_time_connected);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch(id) {
		case R.id.ingresar_registro:
			launchNuevoRegistro();
			return true;
		case R.id.iniciar_chat:
			launchChat();
			return true;
		case R.id.iniciar_lector_qr:
			launchQR();
			return true;
		case R.id.iniciar_registro_alumno:
			launchNuevoRegistroAlumno();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void launchNuevoRegistroAlumno(){
		Intent i = new Intent(this,RegistroAlumno.class);
		startActivity(i);
	}
	
	private void launchNuevoRegistro(){
	}
	
	private void launchChat(){
		Intent i = new Intent(this, MainActivityChat.class);
		i.putExtra("usr_name", usrName);
		startActivity(i);
	}
	
	private void launchQR(){		
	}
}
