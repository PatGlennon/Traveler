package com.example.traveler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

public class Home extends Activity {
	
	private static Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        mContext = this;
        
        ImageButton close = (ImageButton) findViewById(R.id.close);
        ImageButton help = (ImageButton) findViewById(R.id.help);
        ImageButton map_button = (ImageButton) findViewById(R.id.map_button);
        ImageButton list_button = (ImageButton) findViewById(R.id.list_button);
        ImageButton help_button = (ImageButton) findViewById(R.id.help_button);
        ImageButton twitter_button = (ImageButton) findViewById(R.id.twitter_button);
        
        
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
    			finish();
    		}
    	});
        
        //Button to open up the map Activity
		map_button.setOnClickListener(new View.OnClickListener() {
					
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, Map.class);
		        startActivity(intent);	
			}
		});
		//Button to open the Log activity
		list_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, Log.class);
		        startActivity(intent);	
			}
		});
		//Button to open the Help Activity
		help_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, Help.class);
		        startActivity(intent);	
			}
		});
		//Button to open the Twitter Activity
		twitter_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, Social.class);
		        startActivity(intent);	
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_home, menu);
        return true;
    }
}
