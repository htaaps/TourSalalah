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
 * Created by Tapas on 5/29/2015.
 */
public class SinglePlace extends Activity implements View.OnClickListener{

    String p_name,p_details,p_image,p_lat,p_lon;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    Button si_p_map;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.single_place);
        si_p_map=(Button)findViewById(R.id.si_p_map);
        si_p_map.setOnClickListener(this);

        Intent i = getIntent();
        // Get the result of rank
        p_name = i.getStringExtra("p_name");
        // Get the result of country
        p_details = i.getStringExtra("p_details");
        // Get the result of population
        p_image = i.getStringExtra("p_image");

        p_lat=i.getStringExtra("p_lat");

        p_lon=i.getStringExtra("p_lon");

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        // Locate the TextViews in singleitemview.xml
        TextView txtname = (TextView) findViewById(R.id.si_p_name);
        TextView txtdetails = (TextView) findViewById(R.id.si_p_details);
        NetworkImageView ivpic=(NetworkImageView)findViewById(R.id.si_p_image);

        // Set results to the TextViews
        txtname.setText(p_name);
        txtdetails.setText(p_details);
        ivpic.setImageUrl(p_image,imageLoader);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.si_p_map:
                Intent intent = new Intent(this,MapsActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
