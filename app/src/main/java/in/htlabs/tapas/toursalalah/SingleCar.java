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
 * Created by usertajalain on 14/06/2015.
 */
public class SingleCar extends Activity implements View.OnClickListener {
    String c_name,c_details,c_image,c_lat,c_lon,price;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    Button si_c_map;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.single_car);
        si_c_map=(Button)findViewById(R.id.si_c_map);
        si_c_map.setOnClickListener(this);


        Intent i = getIntent();
        // Get the result of rank
        c_name = i.getStringExtra("c_name");
        // Get the result of country
        c_details = i.getStringExtra("c_details");
        // Get the result of population
        c_image = i.getStringExtra("c_image");

        c_lat=i.getStringExtra("c_lat");

        c_lon=i.getStringExtra("c_lon");

        price=i.getStringExtra("price");

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        // Locate the TextViews in singleitemview.xml
        TextView txtname = (TextView) findViewById(R.id.si_c_name);
        TextView txtdetails = (TextView) findViewById(R.id.si_c_details);
        NetworkImageView ivpic=(NetworkImageView)findViewById(R.id.si_c_image);


        // Set results to the TextViews
        txtname.setText(c_name);
        txtdetails.setText(c_details);
        ivpic.setImageUrl(c_image,imageLoader);

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.si_c_map:
                Intent intent = new Intent(this,Login.class);
                startActivity(intent);
                break;
        }
    }
}

