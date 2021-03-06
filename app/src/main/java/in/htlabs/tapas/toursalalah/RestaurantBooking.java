package in.htlabs.tapas.toursalalah;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
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

import in.htlabs.tapas.toursalalah.adapter.CustomRListAdapter;

/**
 * Created by usertajalain on 09/06/2015.
 */
public class RestaurantBooking extends Activity implements View.OnClickListener {
    EditText rb_et_date,rb_et_time,rb_et_table;

    Button rb_bt_submit;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private SimpleDateFormat dateFormatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_booking);

        rb_et_date=(EditText)findViewById(R.id.rb_et_date);
        rb_et_time=(EditText)findViewById(R.id.rb_et_time);
        rb_et_table=(EditText)findViewById(R.id.rb_et_table);
        rb_bt_submit=(Button)findViewById(R.id.rb_bt_submit);

        rb_et_date.setOnClickListener(this);
        rb_et_time.setOnClickListener(this);
        rb_bt_submit.setOnClickListener(this);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                rb_et_date.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        timePickerDialog = new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,int minute) {
                        rb_et_time.setText(hourOfDay + ":" + minute);
                    }
                }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), false);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.rb_et_date:
                datePickerDialog.show();
                break;
            case R.id.rb_et_time:
                timePickerDialog.show();
                break;
            case R.id.rb_bt_submit:
                new BookRestaurant().execute();
                break;
        }
    }

    class BookRestaurant extends AsyncTask<String, String, String> {

        // Progress Dialog
        private ProgressDialog pDialog;

        // JSON parser class
        JSONParser jsonParser = new JSONParser();

        //testing from a real server:
        //private static final String REGISTER_URL = "http://www.yourdomain.com/webservice/login.php";
        private static final String REGISTER_URL = "http://www.htlabs.in/student/salalahguide/restuarant_booking.php";

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
            String r_id,u_id,date_of_book,time_of_book,no_of_table;
            r_id= CustomRListAdapter.restaurant_selection;
            u_id=Login.user_selection;
            date_of_book=rb_et_date.getText().toString();
            time_of_book=rb_et_time.getText().toString();
            no_of_table=rb_et_table.getText().toString();

            int success;
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("r_id", r_id));
                params.add(new BasicNameValuePair("date_of_book",date_of_book));
                params.add(new BasicNameValuePair("time_of_book", time_of_book));
                params.add(new BasicNameValuePair("no_of_table", no_of_table));
                params.add(new BasicNameValuePair("u_id", u_id));

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
            Intent i=new Intent(RestaurantBooking.this,MainActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
