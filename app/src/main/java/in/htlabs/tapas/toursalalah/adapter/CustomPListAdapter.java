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
import in.htlabs.tapas.toursalalah.SinglePlace;
import in.htlabs.tapas.toursalalah.app.AppController;
import in.htlabs.tapas.toursalalah.model.TPlace;

/**
 * Created by Tapas on 5/29/2015.
 */
public class CustomPListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<TPlace> placeItems;
    TPlace p;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomPListAdapter(Activity activity, List<TPlace> placeItems) {
        this.activity = activity;
        this.placeItems = placeItems;
        Log.d("I am in cladapter", "adapter class");
    }

    @Override
    public int getCount() {
        return placeItems.size();
    }

    @Override
    public Object getItem(int location) {
        return placeItems.get(location);
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
            convertView = inflater.inflate(R.layout.place_list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView p_image = (NetworkImageView) convertView.findViewById(R.id.p_image);
        TextView p_name          = (TextView) convertView.findViewById(R.id.p_name);
        TextView p_details       = (TextView) convertView.findViewById(R.id.p_details);

        // getting movie data for the row
        p = placeItems.get(position);

        // thumbnail image
        p_image.setImageUrl(p.getPImageUrl(), imageLoader);

        // title
        p_name.setText(p.getPName());

        // rating
        p_details.setText(p.getPDetails());



        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get tpe position
                p = placeItems.get(position);
                Intent intent = new Intent(activity, SinglePlace.class);
                // Pass all data rank
                intent.putExtra("p_name", p.getPName());
                // Pass all data country
                intent.putExtra("p_details", p.getPDetails());
                // Pass all data flag
                intent.putExtra("p_image", p.getPImageUrl());

                intent.putExtra("p_lat",p.getPLat());

                intent.putExtra("p_lon",p.getPLon());
                // Start SingleItemView Class
                activity.startActivity(intent);
            }
        });

        return convertView;
    }

}