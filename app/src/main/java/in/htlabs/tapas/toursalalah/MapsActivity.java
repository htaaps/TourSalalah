package in.htlabs.tapas.toursalalah;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.htlabs.tapas.toursalalah.model.Hotel;
import in.htlabs.tapas.toursalalah.model.Restaurant;
import in.htlabs.tapas.toursalalah.model.TPlace;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener, View.OnClickListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private List<Hotel> hotelList = new ArrayList<Hotel>();
    private List<TPlace> placeList = new ArrayList<TPlace>();
    private List<Restaurant> restaurantList = new ArrayList<Restaurant>();
    private  Marker mPlace[],mRes[],mHotel[];
    private static final double SLAT=17.0197;
    private static final double SLON=54.0897;
    private Intent i;
    private Button am_btn_booking;
    private static int selection=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        am_btn_booking=(Button)findViewById(R.id.am_btn_booking);
        am_btn_booking.setOnClickListener(this);
        new GetAllLocations().execute();
    }

    @Override
    public void onClick(View v) {
        switch ((v.getId())){
            case R.id.am_btn_booking:
                switch(selection){
                    case 0:
                        Toast.makeText(MapsActivity.this,"Please select a hotel or restaurant or place or car for booking",
                                Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        i=new Intent(MapsActivity.this,HotelList.class);
                        startActivity(i);

                        break;
                    case 2:
                        i=new Intent(MapsActivity.this,PlaceList.class);
                        startActivity(i);

                        break;
                    case 3:
                        i=new Intent(MapsActivity.this,RestaurantList.class);
                        startActivity(i);

                        break;
                }
                break;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(mHotel[0])){
            selection=1;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(hotelList.get(0).getHLat()),
                    Double.parseDouble(hotelList.get(0).getHLon())), 17));

        }else if(marker.equals(mHotel[1])){
            selection=1;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(hotelList.get(1).getHLat()),
                    Double.parseDouble(hotelList.get(1).getHLon())), 17));
        }else if(marker.equals(mHotel[2])){
            selection=1;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(hotelList.get(2).getHLat()),
                    Double.parseDouble(hotelList.get(2).getHLon())), 17));
        }else if(marker.equals(mPlace[0])){
            selection=2;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(placeList.get(0).getPLat()),
                    Double.parseDouble(placeList.get(0).getPLon())), 17));

        }else if(marker.equals(mPlace[1])){
            selection=2;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(placeList.get(1).getPLat()),
                    Double.parseDouble(placeList.get(1).getPLon())), 17));
        }else if(marker.equals(mPlace[2])){
            selection=2;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(placeList.get(2).getPLat()),
                    Double.parseDouble(placeList.get(2).getPLon())), 17));
        }else if(marker.equals(mRes[0])){
            selection=3;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(restaurantList.get(0).getRLat()),
                    Double.parseDouble(restaurantList.get(0).getRLon())), 17));

        }else if(marker.equals(mRes[1])){
            selection=3;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(restaurantList.get(1).getRLat()),
                    Double.parseDouble(restaurantList.get(1).getRLon())), 17));
        }else if(marker.equals(mRes[2])){
            selection=3;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(restaurantList.get(2).getRLat()),
                    Double.parseDouble(restaurantList.get(2).getRLon())), 17));
        }else{
            selection=0;
        }

        return false;
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

        Double lat,lon;
        mHotel=new Marker[hotelList.size()];
        mPlace=new Marker[placeList.size()];
        mRes=new Marker[restaurantList.size()];

        mMap.setOnMarkerClickListener(this);
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        LatLng salalah = new LatLng(SLAT,SLON);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(salalah, 10));


        for(int j=0;j<hotelList.size();j++){
            lat=Double.parseDouble(hotelList.get(j).getHLat());
            lon=Double.parseDouble(hotelList.get(j).getHLon());
            mHotel[j]=mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon)).title(hotelList.get(j).getHName())
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.hotel)));
        }
        for(int j=0;j<placeList.size();j++){
            lat=Double.parseDouble(placeList.get(j).getPLat());
            lon=Double.parseDouble(placeList.get(j).getPLon());
            mPlace[j]=mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title(placeList.get(j).getPName())
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.chair)));
        }
        for(int j=0;j<restaurantList.size();j++){
            lat=Double.parseDouble(restaurantList.get(j).getRLat());
            lon=Double.parseDouble(restaurantList.get(j).getRLon());
            mRes[j]=mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon)).title(restaurantList.get(j).getRName())
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.restaurant)));
        }

    }

    class GetAllLocations extends AsyncTask<String, String, String> {

        // Movies json url
        private static final String LOC_URL = "http://www.htlabs.in/student/salalahguide/allloc.php";

        private ProgressDialog pDialog;

        JSONParser jsonParser = new JSONParser();

        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";
        private static final String TAG_POSTS = "posts";
        private static final String TAG_POSTS_PLACE = "posts_place";
        private static final String TAG_POSTS_RES = "posts_res";

        JSONArray posts=null;
        JSONArray posts_place=null;
        JSONArray posts_res=null;

        /**
         * Before starting background thread Show Progress Dialog
         */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MapsActivity.this);
            pDialog.setMessage("Getting all the details...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();

                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(LOC_URL, "POST", params);

                // check your log for json response
                Log.d("Login attempt", json.toString());

                posts=json.getJSONArray(TAG_POSTS);
                posts_place=json.getJSONArray(TAG_POSTS_PLACE);
                posts_res=json.getJSONArray(TAG_POSTS_RES);

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Login Successful!", json.toString());
                    for(int j=0;j< posts.length();j++){
                        JSONObject obj=posts.getJSONObject(j);
                        Hotel h=new Hotel();
                        h.setHId(obj.getString("h_id"));
                        h.setHName(obj.getString("h_name"));
                        h.setHLat(obj.getString("h_lat"));
                        h.setHLon(obj.getString("h_lon"));

                        hotelList.add(h);
                    }
                    for(int j=0;j< posts_place.length();j++){
                        JSONObject obj=posts_place.getJSONObject(j);
                        TPlace p=new TPlace();
                        p.setPId(obj.getString("p_id"));
                        p.setPName(obj.getString("p_name"));
                        p.setPLat(obj.getString("p_lat"));
                        p.setPLon(obj.getString("p_lon"));

                        placeList.add(p);
                    }
                    for(int j=0;j< posts_res.length();j++){
                        JSONObject obj=posts_res.getJSONObject(j);
                        Restaurant r=new Restaurant();
                        r.setRId(obj.getString("r_id"));
                        r.setRName(obj.getString("r_name"));
                        r.setRLat(obj.getString("r_lat"));
                        r.setRLon(obj.getString("r_lon"));

                        restaurantList.add(r);
                    }

                        return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;

        }

        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(MapsActivity.this, file_url, Toast.LENGTH_LONG).show();
            }
            setUpMapIfNeeded();
        }

    }
}