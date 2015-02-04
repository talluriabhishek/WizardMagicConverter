package com.app.wizardmagicconverter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
	  
public class Extractedaudios extends ListActivity {
	 
	 private List<String> item = null;
	 private List<String> path = null;
	 private String root;
	 private TextView myPath;

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.extractedaudiox);
	        myPath = (TextView)findViewById(R.id.path);
	        
	        root = Environment.getExternalStorageDirectory().getPath();
	        root = root.concat("/WizardsConverter/Extracted Audios") ;
	        
	        getDir(root);
	    }
	    
	    private void getDir(String dirPath)
	    {
	     myPath.setText("Location: " + dirPath);
	     item = new ArrayList<String>();
	     path = new ArrayList<String>();
	     File f = new File(dirPath);
	     File[] files = f.listFiles();
	     
	     if(!dirPath.equals(root))
	     {
	      item.add(root);
	      path.add(root);
	      item.add("../");
	      path.add(f.getParent()); 
	     }
	     
	     for(int i=0; i < files.length; i++)
	     {
	      File file = files[i];
	      
	      if(!file.isHidden() && file.canRead()){
	       path.add(file.getPath());
	          if(file.isDirectory()){
	           item.add(file.getName() + "/");
	          }else{
	           item.add(file.getName());
	          }
	      } 
	     }
	  //   String s = item.get(location);
	     Rowadapter fileList =
		 	       new Rowadapter(this, item , path);
		 	     setListAdapter(fileList);

	    }
	    
	   
	 @Override
	 protected void onListItemClick(ListView l, View v, int position, long id) {
	  // TODO Auto-generated method stub
	  final File file = new File(path.get(position));
	  final String s = getExtension(file);
	  final CharSequence[] items={"Convert Audio","Details","Play Audio"};
	  final String fname = file.getName();
	  final String audiopath = path.get(position);
	  //final CharSequence[] details = {("File Name\n"+fname+"\n"),("File Type\n"+s+"\n")};
	  if (file.isDirectory())
	  {
	   if(file.canRead()){
	    getDir(path.get(position));
	   }else{
	    new AlertDialog.Builder(this)
	     .setIcon(R.drawable.ic_launcher)
	     .setTitle("[" + file.getName() + "] folder can't be read!")
	     .setPositiveButton("OK", null).show(); 
	   } 
	  }else {
		  
		  AlertDialog.Builder builder=new AlertDialog.Builder(Extractedaudios.this);
		  builder.setTitle("Pick your choice").setItems(items, new DialogInterface.OnClickListener() {
		  @Override
		  public void onClick(DialogInterface dialog, int which) {
		  // TODO Auto-generated method stub
			  
			  switch(which){
				  case 0:
					 /* TextView audio format = (TextView)findViewById(R.id.textView2);
					  audioformat.setText(file.getName());*/
					  Intent myIntent = new Intent(Extractedaudios.this,Audiooption.class);
					  myIntent.putExtra("format", s);
				      startActivity(myIntent);
					  break;
				  case 1:
					  Dialog dialog1 = new Dialog(Extractedaudios.this);
					  dialog1.setContentView(R.layout.list);
					  ListView lv = (ListView ) dialog1.findViewById(R.id.list1);
					  final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
							  Extractedaudios.this,
			                    android.R.layout.simple_list_item_1);
			            arrayAdapter.add(("File Name:\n"+fname));
			            arrayAdapter.add(("File Type:\n"+s));
			            arrayAdapter.add(("File Size:\n")+(file.length()/(1024*1024))+" MB" );
			  
					  lv.setAdapter(arrayAdapter);
					  dialog1.setCancelable(true);
					  dialog1.setTitle("Details");
					  dialog1.show();
					 
					  break;
				  case 2:
					  Intent intent = new Intent(Intent.ACTION_VIEW);  
					   intent.setDataAndType(Uri.fromFile(file), "audio/*");  
					   startActivity(intent);
					  break;
			  }
		  }
		  
		});
		  builder.show();

	    }
	 }

	private String getExtension(File file) {
		// TODO Auto-generated method stub
		boolean value;
		String s = file.getName();
		value = s.endsWith(".mp3");
		if(value == true)
		{
			s = "mp3";
			return s;
		}
		else
		{
			s = "aac";
			return s;
		}
		
	}

	}