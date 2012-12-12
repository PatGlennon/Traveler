package com.example.traveler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class TravelPointActivity extends Activity {
	
	private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_point);
        
        mContext = this;
        
        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("id");
        
        String city = Home.aTP.get(id).getCity();
        String state = Home.aTP.get(id).getState();
        String dateStr = Home.aTP.get(id).getDate();
        String picturePath = Home.aTP.get(id).getImageLocation();
        String description = Home.aTP.get(id).getDescription();
        
        TextView place = (TextView) findViewById(R.id.place);
        TextView date = (TextView) findViewById(R.id.date);
        TextView desc = (TextView) findViewById(R.id.description);
        
        place.setText(city + ", " + state);
        date.setText(dateStr);
        desc.setText(description);
        
        if (picturePath != null){
        	ImageView imageView = (ImageView) findViewById(R.id.img);
        	imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
        
        ImageButton back = (ImageButton) findViewById(R.id.back);
        
        //Back Button
        back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_travel_point, menu);
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
