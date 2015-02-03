package com.develops.capiz.registroentradaupiita;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.ListIterator;

import TheChunk.DataChunk;
import TheUser.Usuario;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivityChat extends ActionBarActivity implements OnClickListener{
	
	private IniciaConexion starter;
	private String serverHost;
	private String psswd;
	private String usrName;
	private String[] users;
	private ListView usersView;
	private Socket socket;
	private static final int ALERT_ID = 1234;

	class IniciaConexion extends AsyncTask<String, String, String> {

		Cifrado cifrado;
		boolean isButtonLaunched;

		public IniciaConexion(boolean isButtonLauched) {
			cifrado = new Cifrado("MyPriceOfHistory");
			this.isButtonLaunched = isButtonLauched;
		}

		@Override
		protected String doInBackground(String... params) {
			Usuario sender = new Usuario(usrName, "", psswd);
			DataChunk chunk = new DataChunk();
			chunk.setSender(sender);
			chunk.setOption(0);
			boolean hasConnected = false;
			int intents = 5;
			if (isButtonLaunched)
				serverHost = "192.168.0.13";
			while (!hasConnected)
				try {
					socket = new Socket();
					InetSocketAddress remoteAddr;
					if (isButtonLaunched)
						remoteAddr = new InetSocketAddress(
								serverHost + intents, 5001);
					else
						remoteAddr = new InetSocketAddress(serverHost, 5001);
					socket.connect(remoteAddr, 5000);
					ObjectOutputStream salida = new ObjectOutputStream(
							socket.getOutputStream());
					salida.writeObject(chunk);
					salida.flush();
					ObjectInputStream entrada = new ObjectInputStream(
							socket.getInputStream());
					DataChunk responseFromServer = (DataChunk) entrada
							.readObject();
					if (cifrado
							.decipher(responseFromServer.getCipheredString())
							.equals("Ok")) {
						LinkedList<Usuario> usuarios = (LinkedList<Usuario>) entrada
								.readObject();
						users = new String[usuarios.size()];
						int i = 1;
						ListIterator<Usuario> currentUser = usuarios
								.listIterator();
						while (currentUser.hasNext()) {
							Usuario auxUser = currentUser.next();
							if (!auxUser.getNombre().equals(usrName)) {
								users[i] = auxUser.getNombre();
								i++;
							}
						}
						users[0] = "Todos";
						launchUserList(intents);
						/*
						 * if (usrName.equals("Admin") ||
						 * usrName.equals("Master2") || usrName.equals("Azaraf")
						 * || usrName.equals("Cesar UPIITA")) { if (
						 * isButtonLaunched ) launchUserList(intents); else
						 * launchUserList(); } else { if( isButtonLaunched )
						 * launchSalaChat(intents); else launchSalaChat(); }
						 */
						// publishProgress(users);
						// launchAlert("Yeah!","We have got here n.n",false);
					}
					entrada.close();
					salida.close();
					socket.close();
					hasConnected = true;
				} catch (IOException | ClassNotFoundException e) {
					if (intents == 10) {
						intents = 6;
						hasConnected = true;
						launchAlert(
								"IP incorrecta",
								"La direccion del servidor es incorrecta,"
										+ "por favor pregunta a algun compañero de soporte para "
										+ "conseguir la ip correcta. Puedes intentar con: 192.168.0.136-40",
								true);
					} else {
						intents++;
					}
				} catch (NullPointerException ex) {
					launchAlert("Error en usuarios", ex.toString(), false);
				}
			return null;
		}

		@Override
		protected void onProgressUpdate(String... params) {
			usersView = (ListView) findViewById(R.id.usrs_list);
			MyListAdapter mAdapter = new MyListAdapter(params,
					MainActivityChat.this);
			usersView.setAdapter(mAdapter);
		}
	}

	private void launchSalaChat() {
		Intent i = new Intent(this, SalaChat.class);
		i.putExtra("usrName", usrName);
		i.putExtra("psswd", psswd);
		i.putExtra("host", serverHost);
		i.putExtra("destination", "All");
		startActivity(i);
	}

	private void launchSalaChat(int sufix) {
		Intent i = new Intent(this, SalaChat.class);
		i.putExtra("usrName", usrName);
		i.putExtra("psswd", psswd);
		i.putExtra("host", serverHost + sufix);
		i.putExtra("destination", "All");
		startActivity(i);
	}

	private void launchAlert(String header, String body,
			boolean requestSomething) {
		Intent i = new Intent(this, AlertMessage.class);
		i.putExtra("topHeader", header);
		i.putExtra("body", body);
		i.putExtra("gettingResponse", requestSomething);
		startActivityForResult(i, ALERT_ID);
	}

	private void launchUserList() {
		Intent i = new Intent(this, ListaUsuarios.class);
		i.putExtra("usrName", usrName);
		i.putExtra("psswd", psswd);
		i.putExtra("host", serverHost);
		i.putExtra("usuarios", users);
		startActivity(i);
	}

	private void launchUserList(int sufix) {
		Intent i = new Intent(this, ListaUsuarios.class);
		i.putExtra("usrName", usrName);
		i.putExtra("psswd", psswd);
		i.putExtra("host", serverHost + sufix);
		i.putExtra("usuarios", users);
		startActivity(i);
	}
	
	@Override
	public void onClick(View view){
			starter = new IniciaConexion(true);
			starter.execute("");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar));
		setContentView(R.layout.another_main_layout);
		if (savedInstanceState == null) {
			serverHost = "192.168.0.13";
		}
		Bundle extras = getIntent().getExtras();
		usrName = extras.getString("usr_name");// ((EditText)
												// findViewById(R.id.text_box1)).getText().toString();
		psswd = "MyPriceOfHistory";// ((EditText)
									// findViewById(R.id.text_box2)).getText().toString();
		IniciaConexion starter = new IniciaConexion(true);
		starter.execute("");
		((Button) findViewById(R.id.aceptar))
				.setOnClickListener(this);
		// startService(new Intent(this,HelloService.class));
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("psswd", psswd);
		outState.putString("usrName", usrName);
		outState.putString("host", serverHost);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		psswd = savedInstanceState.getString("psswd");
		usrName = savedInstanceState.getString("usrName");
		serverHost = savedInstanceState.getString("host");
	}

	@Override
	protected void onActivityResult(int resultCode, int requestCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == ALERT_ID && requestCode == RESULT_OK) {
			serverHost = data.getExtras().getString("result");
			Toast.makeText(this, "Reconectando: " + serverHost,
					Toast.LENGTH_LONG).show();
			IniciaConexion starter = new IniciaConexion(false);
			starter.execute("");
		}
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		try{
			socket.close();
			starter.cancel(true);
		}catch(IOException | NullPointerException e){
			
		}
	}
}
