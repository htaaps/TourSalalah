package in.htlabs.tapas.toursalalah;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Tapas on 4/10/2015.
 */
public class Register extends Activity implements  View.OnClickListener, View.OnTouchListener {

    //Views declaration
    EditText re_et_email,re_et_pass1,re_et_pass2,re_et_name,re_et_mobile;
    Button re_bt_register,re_bt_login;

    //Data entry variables
    String username,password,repassword,name,mobile;

    //Validation variable
    Boolean validate=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        //initializing all the views
        re_et_email=(EditText)findViewById(R.id.re_et_email);
        re_et_pass1=(EditText)findViewById(R.id.re_et_pass1);
        re_et_pass2=(EditText)findViewById(R.id.re_et_pass2);
        re_et_name=(EditText)findViewById(R.id.re_et_name);
        re_et_mobile=(EditText)findViewById(R.id.re_et_mobile);
        re_bt_register=(Button)findViewById(R.id.re_bt_register);
        re_bt_login=(Button)findViewById(R.id.re_bt_login);

        //adding click listener to the views
        re_bt_register.setOnTouchListener(this);
        re_bt_login.setOnClickListener(this);
    }

    public boolean onTouch(View v,MotionEvent me){
        switch(v.getId()){
            case R.id.re_bt_register:
                if(me.getAction()== MotionEvent.ACTION_DOWN){
                    validate=checkInput();
                }
                if(me.getAction()== MotionEvent.ACTION_UP){

                    if (validate) {
                        new RegisterUser().execute();
                    }else{
                        Toast.makeText(getApplicationContext(), "Device already Registered", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
        return false;
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.re_bt_login:
                Intent i=new Intent(Register.this,Login.class);
                startActivity(i);
                finish();
                break;
        }
    }

    //Method to check all the inputs by the user is correct
    public boolean checkInput(){
        username=re_et_email.getText().toString().toLowerCase();
        password=re_et_pass1.getText().toString();
        repassword=re_et_pass2.getText().toString();
        name=re_et_name.getText().toString().toLowerCase();
        mobile=re_et_mobile.getText().toString();

        //username must have @
        if(!username.contains("@")){
            re_et_email.setText("");
            re_et_email.requestFocus();
            Toast.makeText(getApplicationContext(), "please enter correct email", Toast.LENGTH_SHORT).show();
            return false;
        }

        //password greater than 6 chars
        if(password.length()<6){
            re_et_pass1.setText("");
            re_et_pass2.setText("");
            re_et_pass1.requestFocus();
            Toast.makeText(getApplicationContext(), "please enter password greater than 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        //repassword greater than 6 characters
        if(repassword.length()<6){
            re_et_pass2.setText("");
            re_et_pass2.setText("");
            re_et_pass2.requestFocus();
            Toast.makeText(getApplicationContext(), "please enter password greater than 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        //mobile no should be of 8 digits
        if(mobile.length()>8 || mobile.length()<8){
            re_et_mobile.setText("");
            re_et_mobile.requestFocus();
            Toast.makeText(getApplicationContext(), "please enter mobile number of 10 digits", Toast.LENGTH_SHORT).show();
            return false;
         }

        //password matching
        if(!password.equals(repassword)){
            Toast.makeText(getApplicationContext(), "password do not match", Toast.LENGTH_SHORT).show();
            re_et_pass1.setText("");
            re_et_pass2.setText("");
            re_et_pass1.requestFocus();
            return false;
        }
        return true;
    }

    class RegisterUser extends AsyncTask<String, String, String> {

        // Progress Dialog
        private ProgressDialog pDialog;

        // JSON parser class
        JSONParser jsonParser = new JSONParser();

        //testing from a real server:
        //private static final String REGISTER_URL = "http://www.yourdomain.com/webservice/login.php";
        private static final String REGISTER_URL = "http://www.htlabs.in/student/salalahguide/register.php";

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
            pDialog = new ProgressDialog(Register.this);
            pDialog.setMessage("Registering user to server...");
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
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));
                params.add(new BasicNameValuePair("name", name));
                params.add(new BasicNameValuePair("mobile", mobile));

                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(REGISTER_URL, "POST", params);

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Login Successful!", json.toString());
                    Intent i = new Intent(Register.this,Login.class);
                    startActivity(i);
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
                Toast.makeText(Register.this, file_url, Toast.LENGTH_LONG).show();
            }
        }
    }
}
