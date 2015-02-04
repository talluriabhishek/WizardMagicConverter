package com.app.wizardmagicconverter;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;



public class Tabclass extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabclassx);
        TabHost tabHost = getTabHost();
        
        // Tab for List Videos
        TabSpec listvideospec = tabHost.newTabSpec("List Videos");
        listvideospec.setIndicator("List Videos", getResources().getDrawable(R.drawable.ic_launcher));
        Intent listvideoIntent = new Intent(this, Listvideos.class);
        listvideospec.setContent(listvideoIntent);
        
        // Tab for Browse Files
        TabSpec browsefilespec = tabHost.newTabSpec("Browse Files");
        // setting Title and Icon for the Tab
        browsefilespec.setIndicator("Browse Files", getResources().getDrawable(R.drawable.ic_launcher));
        Intent browsefileIntent = new Intent(this, Browsefiles.class);
        browsefilespec.setContent(browsefileIntent);
        
        // Tab for Extracted Audio's
        TabSpec extractaudiospec = tabHost.newTabSpec("Extracted Audio's");
        // setting Title and Icon for the Tab
        extractaudiospec.setIndicator("Extracted Audios", getResources().getDrawable(R.drawable.ic_launcher));
        Intent extractaudiosIntent = new Intent(this, Extractedaudios.class);
        extractaudiospec.setContent(extractaudiosIntent);
        
        // Tab for Converted  videos
        TabSpec convertvideospec = tabHost.newTabSpec("Converted Video's");
        // setting Title and Icon for the Tab
        convertvideospec.setIndicator("Converted Videos", getResources().getDrawable(R.drawable.ic_launcher));
        Intent convertvideosIntent = new Intent(this, Convertedvideos.class);
        convertvideospec.setContent(convertvideosIntent);
       	        
        // Adding all TabSpec to TabHost
        tabHost.addTab(listvideospec); // Adding list video tab
        tabHost.addTab(browsefilespec); // Adding browse files tab
        tabHost.addTab(extractaudiospec); // Adding extracted audio's tab
        tabHost.addTab(convertvideospec); // Adding Converted  videos tab
        
       
    }
}