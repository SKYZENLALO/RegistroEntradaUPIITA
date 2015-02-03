package com.develops.capiz.registroentradaupiita;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ListaUsuarios extends ActionBarActivity {

	private String serverHost;
	private String psswd;
	private String usrName;
	private String destination;
	private String[] usuarios;
	private static final int ALERT_ID = 1234;

	private void launchAlert(String header, String body,
			boolean requestSomething) {
		Intent i = new Intent(this, AlertMessage.class);
		i.putExtra("topHeader", header);
		i.putExtra("body", body);
		i.putExtra("gettingResponse", requestSomething);
		startActivityForResult(i, ALERT_ID);
	}

	private void launchSalaChat() {
		Intent i = new Intent(this, SalaChat.class);
		i.putExtra("usrName", usrName);
		i.putExtra("psswd", psswd);
		i.putExtra("host", serverHost);
		i.putExtra("destination", destination);
		startActivity(i);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar));
		setContentView(R.layout.main_view);
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			usrName = extras.getString("usrName");
			psswd = extras.getString("psswd");
			serverHost = extras.getString("host");
			usuarios = extras.getStringArray("usuarios");
		} else {
			usrName = savedInstanceState.getString("usrName");
			psswd = savedInstanceState.getString("psswd");
			serverHost = savedInstanceState.getString("host");
			usuarios = savedInstanceState.getStringArray("usuarios");
		}
		ListView users = (ListView) findViewById(R.id.usrs_list);
		MyListAdapter adapter = new MyListAdapter(usuarios,this);
		users.setAdapter(adapter);
		users.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {
				if (position == 0) {
					destination = "All";
				} else {
					destination = usuarios[position];
				}
				launchSalaChat();
			}
		});
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putStringArray("usuarios", usuarios);
		outState.putString("usrName", usrName);
		outState.putString("psswd", psswd);
		outState.putString("host", serverHost);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		usrName = savedInstanceState.getString("usrName");
		psswd = savedInstanceState.getString("psswd");
		serverHost = savedInstanceState.getString("host");
		usuarios = savedInstanceState.getStringArray("usuarios");
	}
}
