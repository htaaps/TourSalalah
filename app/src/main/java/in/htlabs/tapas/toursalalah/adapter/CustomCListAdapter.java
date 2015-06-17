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
import in.htlabs.tapas.toursalalah.SingleCar;
import in.htlabs.tapas.toursalalah.app.AppController;
import in.htlabs.tapas.toursalalah.model.Car;


/**
 * Created by usertajalain on 14/06/2015.
 */
public class CustomCListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Car> carItems;
    Car c;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomCListAdapter(Activity activity, List<Car> carItems) {
        this.activity = activity;
        this.carItems = carItems;
        Log.d("I am in cladapter", "adapter class");
    }

    @Override
    public int getCount() {
        return carItems.size();
    }

    @Override
    public Object getItem(int location) {
        return carItems.get(location);
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
            convertView = inflater.inflate(R.layout.car_list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView c_image = (NetworkImageView) convertView.findViewById(R.id.c_image);
        TextView c_name = (TextView) convertView.findViewById(R.id.c_name);


        // getting movie data for the row
        c = carItems.get(position);

        // thumbnail image
        c_image.setImageUrl(c.getCImageUrl(), imageLoader);

        // title
        c_name.setText(c.getCName());


        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get the position
                c = carItems.get(position);
                Intent intent = new Intent(activity, SingleCar.class);
                // Pass all data rank
                intent.putExtra("c_name", c.getCName());
                // Pass all data country
                intent.putExtra("c_details", c.getCDetails());
                // Pass all data flag
                intent.putExtra("c_image", c.getCImageUrl());

                intent.putExtra("c_lat", c.getCLat());

                intent.putExtra("c_lon", c.getCLon());

                intent.putExtra("price", c.getCPrice());
                // Start SingleItemView Class
                activity.startActivity(intent);
            }
        });

        return convertView;
    }


}
