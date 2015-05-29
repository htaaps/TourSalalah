package in.htlabs.tapas.toursalalah;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import in.htlabs.tapas.toursalalah.model.Restaurant;


public class MainActivity extends Activity implements View.OnClickListener{

    Button ho_btn_place,ho_btn_restaurant,ho_btn_hotel;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        ho_btn_place=(Button)findViewById(R.id.ho_bt_place);
        ho_btn_restaurant=(Button)findViewById(R.id.ho_bt_res);
        ho_btn_hotel=(Button)findViewById(R.id.ho_bt_hotel);

        ho_btn_place.setOnClickListener(this);
        ho_btn_hotel.setOnClickListener(this);
        ho_btn_restaurant.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ho_bt_place:
                i = new Intent(MainActivity.this,PlaceList.class);
                startActivity(i);
                break;
            case R.id.ho_bt_hotel:
                i = new Intent(MainActivity.this,HotelList.class);
                startActivity(i);
                break;
            case R.id.ho_bt_res:
                i = new Intent(MainActivity.this, RestaurantList.class);
                startActivity(i);
                break;
        }
    }
}
