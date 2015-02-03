package com.develops.capiz.registroentradaupiita;

import java.util.LinkedList;
import java.util.ListIterator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ListView;

public class Admins extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle b){
		super.onCreate(b);
		getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar));
		setContentView(R.layout.admins_view);
		ListView list = (ListView)findViewById(R.id.admin_list);
		EntradaAlumnos bd = new EntradaAlumnos(this);
		String[] columns = {"Boleta","Nombre"};
		LinkedList<String> results = bd.consultaMacho(columns, null, null);
		LinkedList<Guardian> guardians = new LinkedList<Guardian>();
		ListIterator<String> iterator = results.listIterator();		
		while( iterator.hasNext() ){
			Guardian g = new Guardian();
			String aux = iterator.next();
			String[] datos = aux.split(" \\_");
			if( datos.length == 2 ){
				g.setNombre(datos[1]);
				g.setBoleta(datos[0]);
			}else{
				g.setBoleta("2014579863");
				g.setNombre(aux);
			}
			guardians.add(g);
		}
		MyAdapter adapter = new MyAdapter(this,guardians);
		list.setAdapter(adapter);
	}
}
