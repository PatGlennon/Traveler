package com.example.traveler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.Toast;

public class Home extends Activity {
	
	private static Context mContext;
	String FILENAME = "userData";
	private FileInputStream fis;
	//Users locations;  Home will never be closed unless the app closes.
	public static ArrayList<TravelPoint> aTP = new ArrayList<TravelPoint>();

    @SuppressWarnings("unchecked")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        //Read all user data from the file.
        try {
			fis = openFileInput(FILENAME);
			byte[] dataArray = new byte[fis.available()];
			while (fis.read(dataArray) > 0){
				aTP.clear();
				aTP = (ArrayList<TravelPoint>) Serializer.deserializeObject(dataArray);
			}
		} catch (Exception e) {
			Toast.makeText(getBaseContext(), e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
        //Read data from the file.
        mContext = this;
        
        ImageButton close = (ImageButton) findViewById(R.id.close);
        ImageButton help = (ImageButton) findViewById(R.id.help);
        
        TableRow mapTable = (TableRow) findViewById(R.id.mapTable);
        TableRow logTable = (TableRow) findViewById(R.id.logTable);
        TableRow twitterTable = (TableRow) findViewById(R.id.twitterTable);
        TableRow helpTable = (TableRow) findViewById(R.id.helpTable);
        
        
      //Button to open the Help Activity
      help.setOnClickListener(new View.OnClickListener() {
      			
	      	@Override
	      	public void onClick(View v) {
	      		Intent intent = new Intent(mContext, Help.class);
	      		startActivity(intent);	
	      	}
      	});
      	//Button to close the app
    	close.setOnClickListener(new View.OnClickListener() {
    			
    		@Override
    		public void onClick(View v) {
    			AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
    			  dialog.setTitle("Quit?").setMessage("Are you sure you want to quit?").setCancelable(true)
    			  	.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    			  		
    					public void onClick(DialogInterface dialog, int which) {
    						finish();
    					}
    				})
    				.setNegativeButton("No", new DialogInterface.OnClickListener() {
    					
    					public void onClick(DialogInterface dialog, int which) {
    						dialog.dismiss();
    					}
    				});
    			  dialog.show();
    		}
    	});
        
        //Button to open up the map Activity
		mapTable.setOnClickListener(new View.OnClickListener() {
					
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, Map.class);
		        startActivity(intent);	
			}
		});
		//Button to open the Log activity
		logTable.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, Log.class);
		        startActivity(intent);	
			}
		});
		//Button to open the Help Activity
		helpTable.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, Help.class);
		        startActivity(intent);	
			}
		});
		//Button to open the Twitter Activity
		twitterTable.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, Social.class);
		        startActivity(intent);	
			}
		});
    }
    
    @Override
    public void onPause(){
    	super.onPause();
    	
    	byte[] data = Serializer.serializeObject(aTP);
    	try {
			FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
			fos.write(data);
			fos.close();
		} catch (Exception e) {
			//Error message
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.menu_settings:
        	goToMenu();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    public void goToMenu(){
    	Intent intent = new Intent(mContext, Help.class);
  		startActivity(intent);
    }
}
