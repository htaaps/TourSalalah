package in.htlabs.tapas.toursalalah;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import in.htlabs.tapas.toursalalah.app.AppController;


/**
 * Created by Tapas on 5/24/2015.
 */
public class SingleHotel extends Activity implements View.OnClickListener{

    String h_name,h_details,h_image,h_lat,h_lon;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    Button si_h_map;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.single_hotel);
        si_h_map=(Button)findViewById(R.id.si_h_map);
        si_h_map.setOnClickListener(this);

        Intent i = getIntent();
        // Get the result of rank
        h_name = i.getStringExtra("h_name");
        // Get the result of country
        h_details = i.getStringExtra("h_details");
        // Get the result of population
        h_image = i.getStringExtra("h_image");

        h_lat=i.getStringExtra("h_lat");

        h_lon=i.getStringExtra("h_lon");

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        // Locate the TextViews in singleitemview.xml
        TextView txtname = (TextView) findViewById(R.id.si_h_name);
        TextView txtdetails = (TextView) findViewById(R.id.si_h_details);
        NetworkImageView ivpic=(NetworkImageView)findViewById(R.id.si_h_image);

        // Set results to the TextViews
        txtname.setText(h_name);
        txtdetails.setText(h_details);
        ivpic.setImageUrl(h_image,imageLoader);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.si_h_map:
                Intent intent = new Intent(this,Login.class);
                startActivity(intent);
                break;
        }
    }
}
