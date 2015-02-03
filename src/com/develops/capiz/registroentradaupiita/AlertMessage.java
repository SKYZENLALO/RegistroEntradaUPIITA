package com.develops.capiz.registroentradaupiita;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AlertMessage extends ActionBarActivity {

	private String topHeader;
	private String body;
	private boolean weGettingResponse;

	@Override
	protected void onCreate(Bundle b) {
		super.onCreate(b);
		//getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar));
		setContentView(R.layout.alert_message);
		if (b == null) {
			Bundle extras = getIntent().getExtras();
			topHeader = extras.getString("topHeader");
			body = extras.getString("body");
			weGettingResponse = extras.getBoolean("gettingResponse");
		} else {
			topHeader = b.getString("topHeader");
			body = b.getString("body");
			weGettingResponse = b.getBoolean("gettingResponse");		
		}
		if( !weGettingResponse ){
			((EditText)findViewById(R.id.new_value)).setVisibility(View.INVISIBLE);
		}
		((TextView) findViewById(R.id.alert_header)).setText(topHeader);
		((TextView) findViewById(R.id.alert_body)).setText(body);
		((Button) findViewById(R.id.back))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View view) {
						Intent i = new Intent();
						i.putExtra("result",
								((EditText) findViewById(R.id.new_value))
										.getText().toString());
						setResult(RESULT_OK, i);
						finish();
					}
				});
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		outState.putString("topHeader",topHeader);
		outState.putString("body",body);
		outState.putBoolean("gettintResponse", weGettingResponse);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState){
		super.onRestoreInstanceState(savedInstanceState);
		topHeader = savedInstanceState.getString("topHeader");
		body = savedInstanceState.getString("body");
		weGettingResponse = savedInstanceState.getBoolean("gettingResponse");	
	}
}
