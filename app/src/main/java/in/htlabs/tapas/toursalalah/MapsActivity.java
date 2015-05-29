package in.htlabs.tapas.toursalalah;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.htlabs.tapas.toursalalah.adapter.CustomListAdapter;
import in.htlabs.tapas.toursalalah.app.AppController;
import in.htlabs.tapas.toursalalah.model.Hotel;
import in.htlabs.tapas.toursalalah.model.Restaurant;
import in.htlabs.tapas.toursalalah.model.TPlace;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    // Log tag
    private static final String TAG = MapsActivity.class.getSimpleName();
    // Movies json url
    private static final String url = "http://www.htlabs.in/student/salalahguide/alldetails.php";
    private ProgressDialog pDialog;
    private List<Hotel> hotelList = new ArrayList<Hotel>();
    private List<TPlace> placeList = new ArrayList<TPlace>();
    private List<Restaurant> restaurantList = new ArrayList<Restaurant>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        getAllDetails();

        setUpMapIfNeeded();

    }

    public void getAllDetails(){

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
                        JSONArray pArray_place = obj.getJSONArray("posts_place");
                        JSONArray pArray_res = obj.getJSONArray("posts_res");

                        for (int j = 0; j < pArray.length(); j++) {

                            JSONObject pro = pArray.getJSONObject(j);
                            Hotel item = new Hotel();
                            item.setHName(pro.getString("h_name"));
                            item.setHImageUrl(pro.getString("h_img"));
                            item.setHDetails(pro.getString("h_details"));
                            item.setHId(pro.getString("h_id"));
                            item.setHLat(pro.getString("h_lat"));
                            item.setHLon(pro.getString("h_lon"));
                            hotelList.add(item);
                        }

                        for (int j = 0; j < pArray_place.length(); j++) {

                            JSONObject pro = pArray_place.getJSONObject(j);
                            TPlace place_item = new TPlace();
                            place_item.setPName(pro.getString("p_name"));
                            place_item.setPImageUrl(pro.getString("p_img"));
                            place_item.setPDetails(pro.getString("p_details"));
                            place_item.setPId(pro.getString("p_id"));
                            place_item.setPLat(pro.getString("p_lat"));
                            place_item.setPLon(pro.getString("p_lon"));
                            placeList.add(place_item);
                        }

                        for (int j = 0; j < pArray_res.length(); j++) {

                            JSONObject pro = pArray_res.getJSONObject(j);
                            Restaurant res_item = new Restaurant();
                            res_item.setRName(pro.getString("r_name"));
                            res_item.setRImageUrl(pro.getString("r_img"));
                            res_item.setRDetails(pro.getString("r_details"));
                            res_item.setRId(pro.getString("r_id"));
                            res_item.setRLat(pro.getString("r_lat"));
                            res_item.setRLon(pro.getString("r_lon"));
                            restaurantList.add(res_item);
                        }

                        // adding movie to movies array
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
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

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
         mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
}
