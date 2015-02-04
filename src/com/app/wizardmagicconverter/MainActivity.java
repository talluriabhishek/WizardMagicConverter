package com.app.wizardmagicconverter;

import java.io.File;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// creating Directory on SD card
	       File nfile=new File(Environment.getExternalStorageDirectory()+"/WizardsConverter");
	       nfile.mkdir();
	    // creating folders for extracted audio's and converted videos
	       File nfile1=new File(Environment.getExternalStorageDirectory()+"/WizardsConverter"+"/Converted Videos");
	       nfile1.mkdir();
	       File nfile2=new File(Environment.getExternalStorageDirectory()+"/WizardsConverter"+"/Extracted Audios");
	       nfile2.mkdir();

	Button next = (Button) findViewById(R.id.button1);
	next.setOnClickListener(new View.OnClickListener() {
    public void onClick(View v) {
    	Intent myIntent = new Intent(MainActivity.this,Tabclass.class);
        startActivity(myIntent);
    }
});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
