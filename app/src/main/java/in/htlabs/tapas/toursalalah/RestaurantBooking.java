package in.htlabs.tapas.toursalalah;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by usertajalain on 09/06/2015.
 */
public class RestaurantBooking extends Activity implements View.OnClickListener {
    EditText hb_et_cin,hb_et_cout,hb_et_table,hb_et_adult,hb_et_child;

    Button hb_bt_submit;

    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restuarant_booking);
        hb_et_cin=(EditText)findViewById(R.id.hb_et_cin);
        hb_et_cout=(EditText)findViewById(R.id.hb_et_cout);
        hb_et_table=(EditText)findViewById(R.id.hb_et_table);
        hb_et_adult=(EditText)findViewById(R.id.hb_et_adult);
        hb_et_child=(EditText)findViewById(R.id.hb_et_child);
        hb_bt_submit=(Button)findViewById(R.id.hb_bt_submit);

        hb_et_cin.setOnClickListener(this);
        hb_et_cout.setOnClickListener(this);
        hb_bt_submit.setOnClickListener(this);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        Calendar newCalendar = Calendar.getInstance();

        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                hb_et_cin.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                hb_et_cout.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.hb_et_cin:
                fromDatePickerDialog.show();
                break;
            case R.id.hb_et_cout:
                toDatePickerDialog.show();
                break;
            case R.id.hb_bt_submit:
                new BookHotel().execute();
                break;
        }
    }

    class BookHotel extends AsyncTask<String, String, String> {

        // Progress Dialog
        private ProgressDialog pDialog;

        // JSON parser class
        JSONParser jsonParser = new JSONParser();

        //testing from a real server:
        //private static final String REGISTER_URL = "http://www.yourdomain.com/webservice/login.php";
        private static final String REGISTER_URL = "http://www.htlabs.in/student/salalahguide/booking.php";

        // JSON IDS:
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "message";
        /**
         * Before starting background thread Show Progress Dialog
         * */

        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RestaurantBooking.this);
            pDialog.setMessage("Registering user to server...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            String h_id,check_in,check_out,no_rooms,no_adults,no_child,u_id,payment;
            h_id="1";
            check_in=hb_et_cin.getText().toString();
            check_out=hb_et_cout.getText().toString();
            no_rooms=hb_et_table.getText().toString();
            no_adults=hb_et_adult.getText().toString();
            no_child=hb_et_child.getText().toString();
            u_id="2";
            payment="100";

            int success;
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("h_id", h_id));
                params.add(new BasicNameValuePair("check_in",check_in));
                params.add(new BasicNameValuePair("check_out", check_out));
                params.add(new BasicNameValuePair("no_rooms", no_rooms));
                params.add(new BasicNameValuePair("no_child", no_child));
                params.add(new BasicNameValuePair("no_adults",no_adults));
                params.add(new BasicNameValuePair("u_id", u_id));
                params.add(new BasicNameValuePair("payment", payment));

                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(REGISTER_URL, "POST", params);

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Login Successful!", json.toString());
                    return json.getString(TAG_MESSAGE);
                }else{
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
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(RestaurantBooking.this, file_url, Toast.LENGTH_LONG).show();
            }
        }
    }

}
