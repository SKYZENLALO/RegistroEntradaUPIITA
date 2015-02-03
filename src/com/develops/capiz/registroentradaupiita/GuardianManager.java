package com.develops.capiz.registroentradaupiita;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GuardianManager extends ActionBarActivity {

	private Button btn1;
	private Button btn2;
	@Override
	protected void onCreate(Bundle b){
		super.onCreate(b);
		getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar));
		setContentView(R.layout.guardians_manager);
		btn1 = (Button)findViewById(R.id.leftButton_guards_manager);
		btn2 = (Button)findViewById(R.id.rightButton_guards_manager);
		btn2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				launchAdminList();
			}
		});
		btn1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				launchRegistro();
			}
		});
	}
	private void launchAdminList(){
		Intent i = new Intent(this,Admins.class);
		startActivity(i);
	}
	private void launchRegistro(){
		Intent i = new Intent(this,Registro.class);
		startActivity(i);
	}
}
