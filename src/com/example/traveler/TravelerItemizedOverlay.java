package com.example.traveler;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

@SuppressWarnings("rawtypes")
public class TravelerItemizedOverlay extends ItemizedOverlay {
	
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	Context mContext;
	
	public TravelerItemizedOverlay(Drawable defaultMarker, Context context) {
		  super(boundCenterBottom(defaultMarker));
		  mContext = context;
	}
	
	public void clear(){
		mOverlays.clear();
	}

	@Override
	public int size() {
		return mOverlays.size();
	}
	
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
	  return mOverlays.get(i);
	}
	
	@Override
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        if(!shadow) {
            super.draw(canvas, mapView, false);
        }
    }
	
	//On tap show a dialog
	@Override
	protected boolean onTap(final int index) {
	  OverlayItem item = mOverlays.get(index);
	  AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
	  dialog.setPositiveButton("Log", new DialogInterface.OnClickListener() {
	  		
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(mContext, TravelPointActivity.class);
				intent.putExtra("id", index);
	      		mContext.startActivity(intent);
			}
	  });
	  dialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
	  		
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
	  });
	  dialog.setTitle(item.getTitle());
	  dialog.setMessage(item.getSnippet());
	  dialog.show();
	  return true;
	}
}
