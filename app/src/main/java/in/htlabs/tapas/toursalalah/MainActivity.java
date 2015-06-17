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

    Button ho_bt_place,ho_bt_restaurant,ho_bt_hotel,ho_bt_exit,ho_bt_tran;
    Intent i;
    public static int selection = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        ho_bt_place=(Button)findViewById(R.id.ho_bt_place);
        ho_bt_restaurant=(Button)findViewById(R.id.ho_bt_res);
        ho_bt_hotel=(Button)findViewById(R.id.ho_bt_hotel);
        ho_bt_tran=(Button)findViewById(R.id.ho_bt_tran);
        ho_bt_exit=(Button)findViewById(R.id.ho_bt_exit);

        ho_bt_place.setOnClickListener(this);
        ho_bt_hotel.setOnClickListener(this);
        ho_bt_restaurant.setOnClickListener(this);
        ho_bt_tran.setOnClickListener(this);
        ho_bt_exit.setOnClickListener(this);

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
            case R.id.ho_bt_tran:
                i = new Intent(MainActivity.this, CarList.class);
                startActivity(i);
                break;
            case R.id.ho_bt_exit:
                finish();
                break;
        }
    }
}
