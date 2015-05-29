package in.htlabs.tapas.toursalalah;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Tapas on 5/29/2015.
 */
public class SingleRestaurant extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.single_restaurant);
    }
}
