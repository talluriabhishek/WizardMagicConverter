package com.app.wizardmagicconverter;

import java.io.File;




import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Videooption extends Activity {
	String s;
	private String outFile ;
    
	private TextView text;
	public static native int  videoConverter(String inputFile , String outFile);
    static {

  		System.loadLibrary("ffmpeg");
  		System.loadLibrary("converter");
  	
      }
   
    private String getExtension(String filename) {
		// TODO Auto-generated method stub
			
		String extension = filename.substring(filename.lastIndexOf("."));
		return extension;
	}
    private void get_videooption() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		s = intent.getStringExtra("path");
		TextView t = (TextView)findViewById(R.id.textView2);
		t.setText(getExtension(s));
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
	
	setContentView(R.layout.videooptionx);
	get_videooption();
	
	Log.i("check", "After get_videoption");
	
}
	
	private class Worker extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... arg0) {
            Log.i("SomeTag",
                    "start do in background at " + System.currentTimeMillis());
            String data = null;

            try {
            	int ret = videoConverter(s, outFile);
            	if(ret!=0){
            		Exception e = new Exception();
            		throw e;
            	}
                Log.i("SomeTag",
                        "doInBackGround done at " + System.currentTimeMillis());
            } catch (Exception e) {
            	 e.printStackTrace();
            	Toast toast = Toast.makeText(getApplicationContext(), "Video Conversion Failed.Try Again", Toast.LENGTH_LONG);
                toast.show();
                
                	
            }
            return data;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("SomeTag", System.currentTimeMillis() / 1000L
                    + " post execute \n" + result);
            
            Toast toast = Toast.makeText(getApplicationContext(), "Video Converted Successfully", Toast.LENGTH_LONG);
            toast.show();
            videoConverter(s, Environment.getExternalStorageDirectory()+"/dummy.avi");
            
        }
    }

	
	
    public void onClick(View v) {
    	Log.i("onclick", "Extension");
    	final File file = new File(s);
    	Toast toast1 = Toast.makeText(Videooption.this, "Video Conversion Started", Toast.LENGTH_LONG);
        toast1.show();
    	
    	Spinner s1 = (Spinner)findViewById(R.id.spinner1);
    	String Text = s1.getSelectedItem().toString();
    	
    	
    	Spinner s2 = (Spinner)findViewById(R.id.spinner2);
    	String Text2 = s2.getSelectedItem().toString();
    	
    	Log.i(removeExtension(s), "Filename");
    	Log.i(getExtension(s), "Extension");
    	String s3 = file.getName();
    	
    	outFile = (Environment.getExternalStorageDirectory()+"/WizardsConverter"+"/Converted Videos/"+removeExtension(s)+"."+Text);
    	
    	Log.i(outFile, "Extension");
    	new Worker().execute();
    	Intent myIntent1 = new Intent(Videooption.this,Tabclass.class);
		startActivity(myIntent1);
    }
   
	
}
