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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
	  
public class Browsefiles extends ListActivity {
	 
	 private List<String> item = null;
	 private List<String> path = null;
	 private String root;
	 private TextView myPath;

	 private String getExtension(String filename) {
			// TODO Auto-generated method stub
			
			String extension = filename.substring(filename.lastIndexOf("."));
			return extension;
		}
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.browsefilesx);
	        myPath = (TextView)findViewById(R.id.path);
	        
	        root = Environment.getExternalStorageDirectory().getPath();
	        
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
	           item.add(file.getName() );
	          }else{
	           item.add(file.getName() );
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
	  final CharSequence[] items={"Convert Video","Extract Audio","Details","Play Video"};
	  final CharSequence[] items1={"Convert Audio","Details","Play Audio"};
	  
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
		  final String s = getExtension(file.getName());
		  boolean ext;
		  ext = (s.endsWith(".avi") | s.endsWith(".mp4") | s.endsWith(".mkv") | s.endsWith(".flv") | s.endsWith(".mpg")
				  | s.endsWith(".wmv")| s.endsWith(".mov")| s.endsWith(".rm"));
		  if(ext==true)
		  {
		  AlertDialog.Builder builder3=new AlertDialog.Builder(Browsefiles.this);
		  builder3.setTitle("Pick your choice").setItems(items, new DialogInterface.OnClickListener() {
		  @Override
		  public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
			  switch(which){
				  case 0:
					 /* TextView audio format = (TextView)findViewById(R.id.textView2);
					  audioformat.setText(file.getName());*/
					  
				  Intent myIntent = new Intent(Browsefiles.this,Videooption.class);
				  myIntent.putExtra("path", s );
				  startActivity(myIntent);
					   					  
					   break;
				  case 1:
					  Intent myIntent1 = new Intent(Browsefiles.this,VideoAudioextract.class);
				  myIntent1.putExtra("path",file.getName());
			      startActivity(myIntent1);
					  break;
				  case 2:
					  Dialog dialog1 = new Dialog(Browsefiles.this);
					  dialog1.setContentView(R.layout.list);
					  ListView lv = (ListView ) dialog1.findViewById(R.id.list1);
					  final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
							  Browsefiles.this,
			                    android.R.layout.simple_list_item_1);
			            arrayAdapter.add(("File Name:\n"+file.getName()));
			            arrayAdapter.add(("File Type:\n"+getExtension(file.getName())));
			            arrayAdapter.add(("File Size:\n")+(file.length()/(1024*1024))+" MB"  );
			  
					  lv.setAdapter(arrayAdapter);
					  dialog1.setCancelable(true);
					  dialog1.setTitle("Details");
					  dialog1.show();
					 
					  break;
				  case 3:
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.fromFile(file), "video/*");
					startActivity(intent);
					break;
			  }
		  }
		  }); builder3.show();
		  }
		  else
		  {
			  
			  ext = (s.endsWith(".mp3") |s.endsWith(".wav") | s.endsWith(".aac") |  s.endsWith(".wma") );
			  if(ext==true)
			  {
				  
				  AlertDialog.Builder builder=new AlertDialog.Builder(Browsefiles.this);
				  builder.setTitle("Pick your choice").setItems(items1, new DialogInterface.OnClickListener() {
				 /* @Override*/
				  public void onClick(DialogInterface dialog, int which) {
				  // TODO Auto-generated method stub
					  
					  switch(which){
						  case 0:
							 /* TextView audio format = (TextView)findViewById(R.id.textView2);
							  audioformat.setText(file.getName());*/
							  Intent myIntent1 = new Intent(Browsefiles.this,Audiooption.class);
							  myIntent1.putExtra("path", file.getName());
						      startActivity(myIntent1);
							  break;
						  case 1:
							  Dialog dialog1 = new Dialog(Browsefiles.this);
							  dialog1.setContentView(R.layout.list);
							  ListView lv = (ListView ) dialog1.findViewById(R.id.list1);
							  final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
									  Browsefiles.this,
					                    android.R.layout.simple_list_item_1);
					            arrayAdapter.add(("File Name:\n"+file.getName()));
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
			  else
			  {
				  new AlertDialog.Builder(this)
				     .setIcon(R.drawable.android)
				     .setTitle("[" + file.getName() + "]")
				     .setPositiveButton("OK", null).show();  
			  }
			  
		  }
		  
		
		  
		}
		  
		}
		  

	 

	}
