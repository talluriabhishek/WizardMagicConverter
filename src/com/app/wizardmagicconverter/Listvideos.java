package com.app.wizardmagicconverter;

import java.io.File;




import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;

public class Listvideos extends Activity {
      private Cursor videocursor;
      
      private int video_column_index;
      ListView videolist;
      int count;
   
     

      /** Called when the activity is first created. */
      @Override
      public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.listvideosx);
            init_phone_video_grid();
      }
      
      private void init_phone_video_grid() {
            System.gc();
            String[] proj = { MediaStore.Video.Media._ID,
MediaStore.Video.Media.DATA,
MediaStore.Video.Media.DISPLAY_NAME,
MediaStore.Video.Media.SIZE };
            videocursor = managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
proj, null, null, null);
            
           
            count = videocursor.getCount();
            
           
            videolist = (ListView) findViewById(R.id.PhoneVideoList);
            videolist.setAdapter(new VideoAdapter(getApplicationContext()));
            videolist.setOnItemClickListener(videogridlistener);
      }

      private OnItemClickListener videogridlistener = new OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position,
long id) {
            	
                  System.gc();
                  video_column_index = videocursor
.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                  videocursor.moveToPosition(position);
                  /*This gives the path of the video that we are using*/
                  final String filename = videocursor.getString(video_column_index); 
                  final File file = new File(filename);
                /*  Intent intent = new Intent(Listvideos.this, Viewvideo.class);
                  intent.putExtra("video filename", filename);
                  startActivity(intent);*/
            	
            	final CharSequence[] items={"Convert Video","Extract Audio","Details","Play Video"};
            	
            	AlertDialog.Builder builder=new AlertDialog.Builder(Listvideos.this);
      		  builder.setTitle("Pick your choice").setItems(items, new DialogInterface.OnClickListener() {
      		  @Override
      		  public void onClick(DialogInterface dialog, int which) {
      		  // TODO Auto-generated method stub
      			  
      			  switch(which){
      				  case 0:
      					 /* TextView audio format = (TextView)findViewById(R.id.textView2);
      					  audioformat.setText(file.getName());*/
      					Intent myIntent = new Intent(Listvideos.this,Videooption.class);
    					  myIntent.putExtra("path", filename );
    					  startActivity(myIntent);
      					  
      					   break;
      				  case 1:
      					Intent myIntent1 = new Intent(Listvideos.this,VideoAudioextract.class);
    					  myIntent1.putExtra("path",filename);
    				      startActivity(myIntent1);
      					  break;
      				  case 2:
      					  Dialog dialog1 = new Dialog(Listvideos.this);
      					  dialog1.setContentView(R.layout.list);
      					  ListView lv = (ListView ) dialog1.findViewById(R.id.list1);
      					  final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
      							Listvideos.this,
      			                    android.R.layout.simple_list_item_1);
      			            arrayAdapter.add(("File Name:\n"+file.getName()));
      			            arrayAdapter.add(("File Type:\n"+getExtension(filename)));
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

			private String getExtension(String filename) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				
				String extension = filename.substring(filename.lastIndexOf("."));
				return extension;
			}
      		  
      		});
      		  builder.show();

      	    }
      };
      
     

      public class VideoAdapter extends BaseAdapter {
            private Context vContext;
            
            private class ViewHolder {
    		    public TextView text;
    		    public ImageView image;
    		  }

            public VideoAdapter(Context c) {
                  vContext = c;
            }

            public int getCount() {
                  return count;
            }

            public Object getItem(int position) {
                  return position;
            }

            public long getItemId(int position) {
                  return position;
            }
            
            public View getView(int position, View convertView, ViewGroup parent) {
                  System.gc();
                  View rowView = convertView;
                  String id = null;
                  long vid;
                  if (rowView == null) {
                	  
                	  LayoutInflater inflater = (LayoutInflater) vContext
              		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                  rowView = inflater.inflate(R.layout.list_row, null);
                  ViewHolder viewHolder = new ViewHolder();
                  viewHolder.text = (TextView) rowView.findViewById(R.id.text1);
                  viewHolder.image = (ImageView) rowView
                      .findViewById(R.id.list_image);
                  rowView.setTag(viewHolder);
                  }
                  ViewHolder holder = (ViewHolder) rowView.getTag();
                        video_column_index = videocursor
.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
                        videocursor.moveToPosition(position);
                        id = videocursor.getString(video_column_index);
                        video_column_index = videocursor
.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
                        
                        videocursor.moveToPosition(position);
                        id += " Size:" + videocursor.getString(video_column_index) + "KB";
                        holder.text.setText(id);
                        video_column_index = videocursor
                        		.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
                        vid = videocursor.getLong(video_column_index);
                        
                        ContentResolver crThumb = getContentResolver();
                		BitmapFactory.Options options=new BitmapFactory.Options();
                		options.inSampleSize = 1;
                		Bitmap curThumb = MediaStore.Video.Thumbnails.getThumbnail(crThumb, vid, MediaStore.Video.Thumbnails.MICRO_KIND, (BitmapFactory.Options) null);
                		 /*Bitmap bitmap = MediaStore.Video.Thumbnails.getThumbnail(
                	                getActivity().getContentResolver(),
                	                Integer.parseInt(intent.getData().getLastPathSegment()),
                	                MediaStore.Video.Thumbnails.MICRO_KIND,
                	                (BitmapFactory.Options) null );*/
                		holder.image.setScaleType(ScaleType.CENTER_CROP);
                		holder.image.setImageBitmap(curThumb);
                		// holder.image.setImageResource(R.drawable.ic_launcher);
                 
                  return rowView;
            }
      }
}