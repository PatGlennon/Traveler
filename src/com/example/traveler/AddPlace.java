package com.example.traveler;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class AddPlace extends Activity {
	private static int RESULT_LOAD_IMAGE = 1;
	private static String picturePath = null;
	private static int lat, lng;
	private static String city, state, dateStr;
	private static EditText description;
	private static Date date;
    
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);
        
        Bundle extras = getIntent().getExtras();
        
        lat = extras.getInt("lat");
        lng = extras.getInt("lng");
        city = extras.getString("city");
        state = extras.getString("state");
        
        Button add = (Button) findViewById(R.id.add);
        description = (EditText) findViewById(R.id.description);
        
        date = new Date();
        
        dateStr = date.toString();
        
        
        add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), picturePath, Toast.LENGTH_LONG).show();
				TravelPoint newTP = new TravelPoint(description.getText().toString(), city, state, dateStr, lat, lng, picturePath);
				Home.aTP.add(newTP);
				finish();
			}
		});
        
        ImageButton back = (ImageButton) findViewById(R.id.back);
        
        //Back Button
        back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        
        
        
        //Load an image from the gallery
        Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View arg0) {
                 
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                 
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }
     
     
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
 
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
 
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
             
            ImageView imageView = (ImageView) findViewById(R.id.imgView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
         
        }
     
     
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_place, menu);
        return true;
    }
}
