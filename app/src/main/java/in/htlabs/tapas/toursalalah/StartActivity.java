package in.htlabs.tapas.toursalalah;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Tapas on 6/16/2015.
 */
public class StartActivity extends Activity implements View.OnClickListener {
    Button menu, map;
    Intent i;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu = (Button) findViewById(R.id.menu);
        menu.setOnClickListener(this);
        map = (Button) findViewById(R.id.map);
        map.setOnClickListener(this);
    }

    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.menu:
                i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
            case R.id.map:
                i = new Intent(this, MapsActivity.class);
                startActivity(i);
                break;
        }
    }
}

