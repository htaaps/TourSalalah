package in.htlabs.tapas.toursalalah;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import in.htlabs.tapas.toursalalah.adapter.CustomRListAdapter;
import in.htlabs.tapas.toursalalah.app.AppController;
import in.htlabs.tapas.toursalalah.model.Restaurant;


/**
 * Created by Tapas on 5/24/2015.
 */
public class RestaurantList extends Activity {

    // Log tag
    private static final String TAG = RestaurantList.class.getSimpleName();

    // Movies json url
    private static final String url = "http://www.htlabs.in/student/salalahguide/restaurant.php";
    private ProgressDialog pDialog;
    private List<Restaurant> restaurantList = new ArrayList<Restaurant>();
    private ListView listView;
    private CustomRListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_list);
        listView = (ListView) findViewById(R.id.list_restaurant);
        adapter = new CustomRListAdapter(this, restaurantList);
        listView.setAdapter(adapter);
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        // Creating volley request obj
        JsonArrayRequest prodReq = new JsonArrayRequest(url,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                hidePDialog();

                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject obj = response.getJSONObject(i);
                        JSONArray pArray = obj.getJSONArray("posts");

                        for (int j = 0; j < pArray.length(); j++) {

                            JSONObject pro = pArray.getJSONObject(j);
                            Restaurant item = new Restaurant();
                            item.setRName(pro.getString("r_name"));
                            item.setRImageUrl(pro.getString("r_img"));
                            item.setRDetails(pro.getString("r_details"));
                            item.setRId(pro.getString("r_id"));
                            item.setRLat(pro.getString("r_lat"));
                            item.setRLon(pro.getString("r_lon"));

                            // adding movie to movies array
                            restaurantList.add(item);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // notifying list adapter about data changes
                // so that it renders the list view with updated data
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(prodReq);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

}
