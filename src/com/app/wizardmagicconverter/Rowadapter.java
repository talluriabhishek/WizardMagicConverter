package com.app.wizardmagicconverter;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Rowadapter extends ArrayAdapter<String> {
	  private  Context context;
	  private  List<String> item ;
	  private  List<String> path ;
	 // private String dirPath;
	  
	  static class ViewHolder {
		    public TextView text;
		    public ImageView image;
		  }
public Rowadapter(Context context,/*String dirPath, */ List<String> item , List<String> path) {
    super(context, R.layout.list_row, item);
    this.context = context;
    this.item = item;
    this.path = path;
   // this.dirPath = dirPath;
  }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
  View rowView = convertView;
  if (rowView == null) {
	  LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    rowView = inflater.inflate(R.layout.list_row, null);
    ViewHolder viewHolder = new ViewHolder();
    viewHolder.text = (TextView) rowView.findViewById(R.id.text1);
    viewHolder.image = (ImageView) rowView
        .findViewById(R.id.list_image);
    rowView.setTag(viewHolder);
  }
  boolean extension;
  
  ViewHolder holder = (ViewHolder) rowView.getTag();
  String s = item.get(position);
  holder.text.setText(s);
  String p = path.get(position);
  File nfile = new File(p); 
  /*String filenameArray[] = s.split(".");
  String extension = filenameArray[filenameArray.length-1];*/
  if(!nfile.isDirectory())
  {
  extension = (s.endsWith(".avi") | s.endsWith(".mp4") | s.endsWith(".mkv") | s.endsWith(".flv") | s.endsWith(".mpg")
		  | s.endsWith(".wmv")| s.endsWith(".mov")| s.endsWith(".rm"));
  if(extension == true)
  { 
      holder.image.setImageResource(R.drawable.video);
  }
  else
  {
	  extension = (s.endsWith(".mp3") |s.endsWith(".wav") | s.endsWith(".aac") |  s.endsWith(".wma") );
	  if(extension == true)
	  {
	  holder.image.setImageResource(R.drawable.audio);
	  }
	  else
	  {
		  holder.image.setImageResource(R.drawable.doc);
	  }
  }
  }
  else
  {
	  holder.image.setImageResource(R.drawable.folder);
  }
  
  return rowView;
}
}	