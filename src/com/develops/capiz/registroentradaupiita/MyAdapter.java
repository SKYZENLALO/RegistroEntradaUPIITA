package com.develops.capiz.registroentradaupiita;

import java.util.LinkedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	Context context;
	LayoutInflater layoutInflater;
	LinkedList<Guardian> guardians;
	public MyAdapter(Context context, LinkedList<Guardian> cards) {
		this.context=context;
		layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.guardians = cards;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView nombre;
		TextView boleta;
		View view;
		if( convertView == null ){
			view = layoutInflater.inflate(R.layout.guardian_row, parent,false);
		}else{
			view = convertView;
		}
		nombre = (TextView)view.findViewById(R.id.Nombre);
		boleta = (TextView)view.findViewById(R.id.Boleta);		
		nombre.setText(guardians.get(position).getNombre());
		boleta.setText(guardians.get(position).getBoleta());
		return view;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return guardians.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
