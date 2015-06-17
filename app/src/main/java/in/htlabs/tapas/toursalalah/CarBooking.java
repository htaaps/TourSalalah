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

import in.htlabs.tapas.toursalalah.adapter.CustomCListAdapter;

/**
 * Created by Tapas on 6/17/2015.
 */
public class CarBooking extends Activity implements View.OnClickListener {
    EditText cb_et_date,cb_et_time;

    Button rb_bt_submit;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_booking);

        cb_et_date=(EditText)findViewById(R.id.cb_et_date);
        cb_et_time=(EditText)findViewById(R.id.cb_et_time);
        rb_bt_submit=(Button)findViewById(R.id.cb_bt_submit);

        cb_et_date.setOnClickListener(this);
        cb_et_time.setOnClickListener(this);
        rb_bt_submit.setOnClickListener(this);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                cb_et_date.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        timePickerDialog = new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay,int minute) {
                cb_et_time.setText(hourOfDay + ":" + minute);
            }
        }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), false);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.cb_et_date:
                datePickerDialog.show();
                break;
            case R.id.cb_et_time:
                timePickerDialog.show();
                break;
            case R.id.cb_bt_submit:
                new BookCar().execute();
                break;
        }
    }

    class BookCar extends AsyncTask<String, String, String> {

        // Progress Dialog
        private ProgressDialog pDialog;

        // JSON parser class
        JSONParser jsonParser = new JSONParser();

        //testing from a real server:
        //private static final String REGISTER_URL = "http://www.yourdomain.com/webservice/login.php";
        private static final String REGISTER_URL = "http://www.htlabs.in/student/salalahguide/car_booking.php";

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
            pDialog = new ProgressDialog(CarBooking.this);
            pDialog.setMessage("Booking a car pls wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            String c_id,u_id,date_of_book,time_of_book;
            c_id= CustomCListAdapter.car_selection;
            u_id=Login.user_selection;
            date_of_book=cb_et_date.getText().toString();
            time_of_book=cb_et_time.getText().toString();

            int success;
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("c_id", c_id));
                params.add(new BasicNameValuePair("date_of_book",date_of_book));
                params.add(new BasicNameValuePair("time_of_book", time_of_book));
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
                Toast.makeText(CarBooking.this, file_url, Toast.LENGTH_LONG).show();
            }
            Intent i=new Intent(CarBooking.this,MainActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
