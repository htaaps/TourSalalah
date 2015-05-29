package in.htlabs.tapas.toursalalah.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import in.htlabs.tapas.toursalalah.R;
import in.htlabs.tapas.toursalalah.SingleHotel;
import in.htlabs.tapas.toursalalah.app.AppController;
import in.htlabs.tapas.toursalalah.model.Hotel;


public class CustomListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<Hotel> hotelItems;
	Hotel h;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public CustomListAdapter(Activity activity, List<Hotel> hotelItems) {
		this.activity = activity;
		this.hotelItems = hotelItems;
		Log.d("I am in cladapter", "adapter class");
	}

	@Override
	public int getCount() {
		return hotelItems.size();
	}

	@Override
	public Object getItem(int location) {
		return hotelItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null)
			convertView = inflater.inflate(R.layout.hotel_list_row, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		NetworkImageView h_image = (NetworkImageView) convertView.findViewById(R.id.h_image);
		TextView h_name          = (TextView) convertView.findViewById(R.id.h_name);
		TextView h_details       = (TextView) convertView.findViewById(R.id.h_details);

		// getting movie data for the row
		h = hotelItems.get(position);

		// thumbnail image
		h_image.setImageUrl(h.getHImageUrl(), imageLoader);
		
		// title
		h_name.setText(h.getHName());
		
		// rating
		h_details.setText(h.getHDetails());



		convertView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Get the position
				h = hotelItems.get(position);
				Intent intent = new Intent(activity, SingleHotel.class);
				// Pass all data rank
				intent.putExtra("h_name", h.getHName());
				// Pass all data country
				intent.putExtra("h_details", h.getHDetails());
				// Pass all data flag
				intent.putExtra("h_image", h.getHImageUrl());

				intent.putExtra("h_lat",h.getHLat());

				intent.putExtra("h_lon",h.getHLon());
				// Start SingleItemView Class
				activity.startActivity(intent);
			}
		});

		return convertView;
	}

}