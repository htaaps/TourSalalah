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
public class SingleRestaurant extends Activity implements View.OnClickListener{

    String r_name,r_details,r_image,r_lat,r_lon;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    Button si_r_book;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.single_restaurant);
        si_r_book=(Button)findViewById(R.id.si_r_book);
        si_r_book.setOnClickListener(this);

        Intent i = getIntent();
        // Get the result of rank
        r_name = i.getStringExtra("r_name");
        // Get the result of country
        r_details = i.getStringExtra("r_details");
        // Get the result of population
        r_image = i.getStringExtra("r_image");

        r_lat=i.getStringExtra("r_lat");

        r_lon=i.getStringExtra("r_lon");

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        // Locate the TextViews in singleitemview.xml
        TextView txtname = (TextView) findViewById(R.id.si_r_name);
        TextView txtdetails = (TextView) findViewById(R.id.si_r_details);
        NetworkImageView ivpic=(NetworkImageView)findViewById(R.id.si_r_image);

        // Set results to the TextViews
        txtname.setText(r_name);
        txtdetails.setText(r_details);
        ivpic.setImageUrl(r_image,imageLoader);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.si_r_book:
                MainActivity.selection=2;
                Intent intent = new Intent(this,Login.class);
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
