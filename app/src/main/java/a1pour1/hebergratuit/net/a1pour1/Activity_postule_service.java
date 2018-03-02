package a1pour1.hebergratuit.net.a1pour1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SPORE on 02/03/2018.
 */

public class Activity_postule_service extends AppCompatActivity {

    TextView txtTitre;
    TextView txtAdresse;
    TextView txtDesc;

    Button btnPostule;

    String serviceId;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    // single service url
    private static final String url_service_details = "http://1pour1.hebergratuit.net/get_service_details.php";

    // url to apply for a product
    private static final String url_apply_service = "http://1pour1.hebergratuit.net/apply_service.php";


    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_SERVICES = "Service";
    private static final String TAG_SERVICEID = "ServiceId";
    private static final String TAG_TITLE = "Titre";
    private static final String TAG_ADRESSE = "Lieu";
    private static final String TAG_DESCRIPTION = "Description";
    private static final String TAG_USAGERID = "UsagerId";





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postule_service);

        // save button
        btnPostule = findViewById(R.id.btnPostuleService);

        // getting product details from intent
        Intent i = getIntent();

        // getting service id from intent
        serviceId = i.getStringExtra(TAG_SERVICEID);

        // Getting complete product details in background thread
        new Activity_postule_service.GetServiceDetails().execute();


        // button postule click event
        btnPostule.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // deleting product in background thread
                new Postule().execute();
            }
        });


    }

    /**
     * Background Async Task to Get complete product details
     * */
    class GetServiceDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Activity_postule_service.this);
            pDialog.setMessage("Loading service details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Getting service's details in background thread
         * */
        protected String doInBackground(String... params) {

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    // Check for success tag
                    int success;
                    try {
                        // Building Parameters
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("ServiceId", serviceId));



                        // getting product details by making HTTP request
                        // Note that product details url will use POST request
                        JSONObject json = jsonParser.makeHttpRequest(
                                url_service_details, "POST", params);


                        // check your log for json response
                        //Log.d("Single Service Details", json.toString());

                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            // successfully received service details
                            JSONArray productObj = json
                                    .getJSONArray(TAG_SERVICES); // JSON Array

                            // get first service object from JSON Array
                            JSONObject service = productObj.getJSONObject(0);

                            // service with this serviceId found
                            // Edit Text
                            txtTitre = findViewById(R.id.serviceName);
                            txtAdresse = findViewById(R.id.displayAdresse);
                            txtDesc = findViewById(R.id.displayDescription);



                            // display product data in EditText
                            txtTitre.setText(service.getString(TAG_TITLE));
                            txtAdresse.setText(service.getString(TAG_ADRESSE));
                            txtDesc.setText(service.getString(TAG_DESCRIPTION));





                        }else{
                            // product with pid not found
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
        }
    }

    /**
     * Background Async Task to  Postule for a product
     * */
    class Postule extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Activity_postule_service.this);
            pDialog.setMessage("Applying ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * applying for a product
         * */
        protected String doInBackground(String... args) {


            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_SERVICEID, serviceId));
            params.add(new BasicNameValuePair(TAG_USAGERID, "" + Utilisateur.getId()));

            // sending the pid and the UserId
            // Notice that update product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_apply_service,
                    "POST", params);

            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    Log.d("SuccessApply", "successfully applying for the service");

                    // successfully applied
                    Intent i = getIntent();
                    // send result code 100 to notify about product update
                    setResult(100, i);
                    finish();
                } else {
                    Log.d("FailedApply", "Failed to apply for the service");
                    // failed to apply for a service
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
            // dismiss the dialog once applied for the service
            pDialog.dismiss();
        }
    }


}
