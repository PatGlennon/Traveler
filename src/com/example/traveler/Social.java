package com.example.traveler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Social extends Activity {
	Context mContext;
	
	//
	
	
	//
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        
        ///TWITTER TESTING
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        //TWITTER TESTING
        
        Button tweet = (Button) findViewById(R.id.tweet);
        ImageButton home = (ImageButton) findViewById(R.id.home);
        ImageButton help = (ImageButton) findViewById(R.id.help);
        //Home Button
        home.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        help.setOnClickListener(new View.OnClickListener() {
			
  	      	@Override
  	      	public void onClick(View v) {
  	      		Intent intent = new Intent(mContext, Help.class);
  	      		startActivity(intent);	
  	      		finish();
  	      	}
        });
        
        tweet.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String tweetUrl = "https://twitter.com/intent/tweet?text=PUT TEXT HERE &url="
                        + "https://www.google.com";
				Uri uri = Uri.parse(tweetUrl);
				startActivity(new Intent(Intent.ACTION_VIEW, uri));
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_social, menu);
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
  		finish();
    }
}
