package com.app.wizardmagicconverter;

import java.io.File;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Audiooption extends Activity {
	String s;
	private String outFile ;
	
	public static native int  audioConverter(String inputFile , String outFile);
    static {

  		System.loadLibrary("ffmpeg");
  		System.loadLibrary("converter");
  	
      }
    
    private class Worker1 extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... arg0) {
            Log.i("SomeTag",
                    "start do in background at " + System.currentTimeMillis());
            String data = null;

            try {
            	int ret = audioConverter(s, outFile);
            	if(ret!=0){
            		Exception e = new Exception();
            		throw e;
            	}
                Log.i("SomeTag",
                        "doInBackGround done at " + System.currentTimeMillis());
            } catch (Exception e) {
            	
            	Toast toast = Toast.makeText(getApplicationContext(), "Audio Extraction Failed.Try Again", Toast.LENGTH_LONG);
                toast.show();
                	
            }
            return data;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("SomeTag", System.currentTimeMillis() / 1000L
                    + " post execute \n" + result);
            
            Toast toast = Toast.makeText(getApplicationContext(), "Audio Extracted Successfully", Toast.LENGTH_LONG);
            toast.show();
            
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("SomeTag", System.currentTimeMillis() / 1000L + "  onDestory()");
    }
    
    private String getExtension(String filename) {
		// TODO Auto-generated method stub
			
		String extension = filename.substring(filename.lastIndexOf("."));
		return extension;
	}
    
    public String removeExtension(String str) {

        String separator = System.getProperty("file.separator");
        String filen;

        // Remove the path upto the filename.
        int lastSeparatorIndex = str.lastIndexOf(separator);
        if (lastSeparatorIndex == -1) {
        	filen = str;
        } else {
        	filen = str.substring(lastSeparatorIndex + 1);
        }

        // Remove the extension.
        int extensionIndex = filen.lastIndexOf(".");
        if (extensionIndex == -1)
            return filen;

        return filen.substring(0, extensionIndex);
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	setContentView(R.layout.audiooptionx);
	get_audiooption();
	

	Button next = (Button) findViewById(R.id.button1);
	next.setOnClickListener(new View.OnClickListener() {
    public void onClick(View v) {
    	Log.i("onclick", "Extension");
    	
    	Toast toast1 = Toast.makeText(Audiooption.this, "Audio Extraction Started", Toast.LENGTH_LONG);
        toast1.show();
    	
    	Spinner s1 = (Spinner)findViewById(R.id.spinner1);
    	String Text = s1.getSelectedItem().toString();
    	
    	outFile = (Environment.getExternalStorageDirectory()+"/WizardsConverter"+"/Extracted Audios/"+removeExtension(s)+"."+Text);
    	System.gc();
    	new Worker1().execute();
    	Intent myIntent1 = new Intent(Audiooption.this,Tabclass.class);
		startActivity(myIntent1);
    }
    });
	
	}

	private void get_audiooption() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		s = intent.getStringExtra("path");
		String extn = getExtension(s);
		TextView t = (TextView)findViewById(R.id.textView2);
		t.setText(extn);
	}
	
	

}
