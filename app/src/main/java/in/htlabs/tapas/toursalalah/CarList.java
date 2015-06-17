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

import in.htlabs.tapas.toursalalah.adapter.CustomCListAdapter;
import in.htlabs.tapas.toursalalah.app.AppController;
import in.htlabs.tapas.toursalalah.model.Car;


/**
 * Created by usertajalain on 14/06/2015.
 */
public class CarList extends Activity {

    // Log tag
    private static final String TAG = CarList.class.getSimpleName();

    // Movies json url
    private static final String url = "http://www.htlabs.in/student/salalahguide/car.php";
    private ProgressDialog pDialog;
    private List<Car> carList = new ArrayList<Car>();
    private ListView listView;
    private CustomCListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_list);
        listView = (ListView) findViewById(R.id.list_car);
        adapter = new CustomCListAdapter(this, carList);
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
                            Car item = new Car();
                            item.setCName(pro.getString("c_name"));
                            item.setCImageUrl(pro.getString("c_img"));
                            item.setCDetails(pro.getString("c_details"));
                            item.setCId(pro.getString("c_id"));
                            item.setCLat(pro.getString("c_lat"));
                            item.setCLon(pro.getString("c_lon"));
                            item.setCPrice(pro.getString("price"));

                            // adding movie to movies array
                            carList.add(item);
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
