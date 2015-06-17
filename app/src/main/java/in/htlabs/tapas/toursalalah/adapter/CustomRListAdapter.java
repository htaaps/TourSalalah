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
import in.htlabs.tapas.toursalalah.SingleRestaurant;
import in.htlabs.tapas.toursalalah.app.AppController;
import in.htlabs.tapas.toursalalah.model.Restaurant;


public class CustomRListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Restaurant> restaurantItems;
    Restaurant r;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    public static String restaurant_selection=null;

    public CustomRListAdapter(Activity activity, List<Restaurant> restaurantItems) {
        this.activity = activity;
        this.restaurantItems = restaurantItems;
        Log.d("I am in cladapter", "adapter class");
    }

    @Override
    public int getCount() {
        return restaurantItems.size();
    }

    @Override
    public Object getItem(int location) {
        return restaurantItems.get(location);
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
            convertView = inflater.inflate(R.layout.restaurant_list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView r_image = (NetworkImageView) convertView.findViewById(R.id.r_image);
        TextView r_name          = (TextView) convertView.findViewById(R.id.r_name);

        // getting movie data for the row
        r = restaurantItems.get(position);

        // thumbnail image
        r_image.setImageUrl(r.getRImageUrl(), imageLoader);

        // title
        r_name.setText(r.getRName());


        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get the position
                r = restaurantItems.get(position);
                Intent intent = new Intent(activity, SingleRestaurant.class);
                restaurant_selection=r.getRId();
                // Pass all data rank
                intent.putExtra("r_name", r.getRName());
                // Pass all data country
                intent.putExtra("r_details", r.getRDetails());
                // Pass all data flag
                intent.putExtra("r_image", r.getRImageUrl());

                intent.putExtra("r_lat",r.getRLat());

                intent.putExtra("r_lon",r.getRLon());
                // Start SingleItemView Class
                activity.startActivity(intent);
            }
        });

        return convertView;
    }

}