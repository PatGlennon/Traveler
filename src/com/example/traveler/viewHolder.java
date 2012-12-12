package com.example.traveler;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class viewHolder extends ArrayAdapter<TravelPoint> {
	
	  private final List<TravelPoint> list;
	  private final Activity context;

	  public viewHolder(Activity context, List<TravelPoint> list) {
	    super(context, R.layout.activity_panel, list);
	    this.list = list;
	    this.context = context;
	  }

	  static class ViewHolder {
	    protected TextView place, date;
	    
	    
	  }

	  @Override
	  public View getView(final int position, View convertView, ViewGroup parent) {
	     View view = null;
	     
	    if (convertView == null) {
	      LayoutInflater inflator = context.getLayoutInflater();
	      view = inflator.inflate(R.layout.activity_panel, null);
	      final ViewHolder vHolder = new ViewHolder();
	      vHolder.place = (TextView) view.findViewById(R.id.place);
	      vHolder.date = (TextView) view.findViewById(R.id.date);
	      view.setTag(vHolder);
	    } else {
	      view = convertView;
	    }
	    ViewHolder vholder = (ViewHolder) view.getTag();
	    vholder.place.setText(list.get(position).getCity() + ", " + list.get(position).getState());
	    vholder.date.setText(list.get(position).getDate());
	    return view;
	  }

}
