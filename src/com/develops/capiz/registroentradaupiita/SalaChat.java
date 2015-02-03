package com.develops.capiz.registroentradaupiita;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.ListIterator;

import TheChunk.DataChunk;
import TheUser.Usuario;
import android.annotation.TargetApi;
import android.support.v7.app.ActionBarActivity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class SalaChat extends ActionBarActivity {

	private static LinkedList<Message> mensajes;
	private int counter;
	ListIterator<Message> msg;
	private String host;
	private String usrName;
	private String psswd;
	private String destination;
	private ServerSocket server;
	private LinearLayout messageContainer;
	private LayoutInflater inflater;

	class SendMessage extends AsyncTask<String, String, String> {

		Cifrado cifrado = new Cifrado("MyPriceOfHistory");
		View view;
		TextView message;

		public SendMessage() {
			view = inflater.inflate(R.layout.output_message, messageContainer,
					false);
			message = (TextView) view.findViewById(R.id.output_message);
		}

		@Override
		protected String doInBackground(String... params) {
			Usuario sender = new Usuario(usrName, "", psswd);
			Usuario destinationUsr = new Usuario(destination, "", "");
			DataChunk chunk = new DataChunk();
			chunk.setSender(sender);
			chunk.setDestination(destinationUsr);
			chunk.setOption(1);
			chunk.setCipheredString(cifrado.cipher(params[0]));
			publishProgress(params);
			// boolean wasSent = false;
			// while (!wasSent) {
			try {
				Socket socket = new Socket(host, 5001);
				ObjectOutputStream salida = new ObjectOutputStream(
						socket.getOutputStream());
				salida.writeObject(chunk);
				salida.flush();
				salida.close();
				socket.close();
				// wasSent = true;
			} catch (Exception e) {
				// wasSent = false;
				// launchAlert("peje", e.toString(), false);
			}
			// }
			return params[0];
		}

		@Override
		protected void onProgressUpdate(String... params) {
			messageContainer.addView(view);
			ScrollView alert_body_container = (ScrollView)findViewById(R.id.alert_body_container);
			alert_body_container.fullScroll(View.FOCUS_DOWN);
		}

		@Override
		protected void onPostExecute(String result) {
			message.setText(result);
			mensajes.add(new Message("Me - " + destination, result));
			messageContainer.requestChildFocus(message, null);
			message.requestFocus(View.FOCUS_DOWN);
		}
	}

	class EscuchaMensaje extends AsyncTask<String, String, String> {

		Cifrado cifrado;
		LayoutInflater inflater;

		public EscuchaMensaje() {
			cifrado = new Cifrado("MyPriceOfHistory");
			inflater = (LayoutInflater) getSystemService(ActionBarActivity.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		protected String doInBackground(String... strings) {
			try {
				server = new ServerSocket(5002);
				while (true) {
					Socket socket = server.accept();
					ObjectInputStream entrada = new ObjectInputStream(
							socket.getInputStream());
					DataChunk chunk = (DataChunk) entrada.readObject();
					publishProgress(chunk.getSender().getNombre(),
							cifrado.decipher(chunk.getCipheredString()));
					entrada.close();
					socket.close();
				}
			} catch (IOException | ClassNotFoundException e) {
				// launchAlert("Algo malo", e.toString(), false);
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(String... params) {
			View view = inflater.inflate(R.layout.incomming_message,
					messageContainer, false);
			TextView sender_label = (TextView) view
					.findViewById(R.id.sender_label);
			sender_label.setText(params[0]);
			TextView text = (TextView) view.findViewById(R.id.incommingMessage);
			text.setText(params[1]);
			messageContainer.addView(view);
			mensajes.add(new Message(params[0], params[1]));
			ScrollView alert_body_container = (ScrollView)findViewById(R.id.alert_body_container);
			alert_body_container.fullScroll(View.FOCUS_DOWN);
			sender_label.requestFocus(View.FOCUS_DOWN);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			//getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar));
			setContentView(R.layout.sala_chat);
			if (savedInstanceState == null) {
				Bundle extras = getIntent().getExtras();
				usrName = extras.getString("usrName");
				psswd = extras.getString("psswd");
				host = extras.getString("host");
				destination = extras.getString("destination");
			} else {
				usrName = savedInstanceState.getString("usrName");
				psswd = savedInstanceState.getString("psswd");
				host = savedInstanceState.getString("host");
				destination = savedInstanceState.getString("destination");
			}
			inflater = (LayoutInflater) getSystemService(ActionBarActivity.LAYOUT_INFLATER_SERVICE);
			if (mensajes == null) {
				mensajes = new LinkedList<Message>();
			}
			messageContainer = (LinearLayout) findViewById(R.id.message_container);
			((Button) findViewById(R.id.send))
					.setOnClickListener(new OnClickListener() {

						@TargetApi(Build.VERSION_CODES.HONEYCOMB)
						@Override
						public void onClick(View v) {
							EditText message = ((EditText) findViewById(R.id.message));
							if ((message.getText().toString().trim()).length() > 0) {
								SendMessage sender = new SendMessage();
								sender.executeOnExecutor(
										AsyncTask.THREAD_POOL_EXECUTOR, message
												.getText().toString());
								message.setText("");
							}
						}

					});
			counter = mensajes.size();
			ListIterator<Message> messagesIterator = mensajes.listIterator();
			while (messagesIterator.hasNext()
					&& (mensajes.size() - counter) < 10) {
				Message currentMessage = messagesIterator.next();
				if (currentMessage.getSender().contains("Me")
						&& currentMessage.getSender().contains(destination)) {
					View view = inflater.inflate(R.layout.output_message,
							messageContainer, false);
					((TextView) view.findViewById(R.id.output_message))
							.setText(currentMessage.getMessage());
					messageContainer.addView(view);
				} else {
					if (destination.equals("All")) {
						View view = inflater.inflate(
								R.layout.incomming_message, messageContainer,
								false);
						((TextView) view.findViewById(R.id.sender_label))
								.setText(currentMessage.getSender());
						((TextView) view.findViewById(R.id.incommingMessage))
								.setText(currentMessage.getMessage());
						messageContainer.addView(view);
					} else {
						if (destination.equals(currentMessage.getSender())) {
							View view = inflater.inflate(
									R.layout.incomming_message,
									messageContainer, false);
							((TextView) view.findViewById(R.id.sender_label))
									.setText(currentMessage.getSender());
							((TextView) view
									.findViewById(R.id.incommingMessage))
									.setText(currentMessage.getMessage());
							messageContainer.addView(view);
						}
					}
				}
				counter--;
			}
			EscuchaMensaje messageListener = new EscuchaMensaje();
			messageListener.execute("");			
		} catch (Exception e) {
			// launchAlert("Testing", "Error: " + e.toString(), false);
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("usrName", usrName);
		outState.putString("psswd", psswd);
		outState.putString("host", host);
		outState.putString("destination", destination);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		usrName = savedInstanceState.getString("usrName");
		psswd = savedInstanceState.getString("psswd");
		host = savedInstanceState.getString("host");
		destination = savedInstanceState.getString("destination");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			server.close();
		} catch (IOException e) {
		}
	}
}