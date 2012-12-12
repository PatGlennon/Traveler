package com.example.traveler;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

public class Log extends Activity {
	private Context mContext;
	String FILENAME = "userData";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        
        mContext = this;
        
        ListView myListView = (ListView) findViewById(R.id.logList);
        
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, final int position, long id){
        		Intent intent = new Intent(mContext, TravelPointActivity.class);
		    	intent.putExtra("id", position);
		  		startActivity(intent);
        	}
		});
        
	  	  // array list of activities
	  	  ArrayList<TravelPoint> panelArray = new ArrayList<TravelPoint>();
	
	  	  // array adapter 
	  	  final ArrayAdapter<TravelPoint> arrayAdapter;
	  	  //link to view holder
	  	  arrayAdapter = new viewHolder(this, panelArray);
	
	  	  	// set array adapter
	  	  myListView.setAdapter(arrayAdapter);
	  	  for (int i = 0; i < Home.aTP.size(); i++){
				panelArray.add(Home.aTP.get(i));
	  	  }
	  	  arrayAdapter.notifyDataSetChanged();	

        ImageButton home = (ImageButton) findViewById(R.id.home);
        ImageButton help = (ImageButton) findViewById(R.id.help);
        //Home Button
        home.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
        //Button to open the Help Activity
        help.setOnClickListener(new View.OnClickListener() {
        			
  	      	@Override
  	      	public void onClick(View v) {
  	      		Intent intent = new Intent(mContext, Help.class);
  	      		startActivity(intent);	
  	      		finish();
  	      	}
        });
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_log, menu);
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
