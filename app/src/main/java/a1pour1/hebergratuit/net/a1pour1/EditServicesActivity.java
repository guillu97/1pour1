package a1pour1.hebergratuit.net.a1pour1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EditServicesActivity extends AppCompatActivity {
    TextView txtTitre;
    EditText txtAdresse;
    EditText txtDesc;


    Button btnSave;
    Button btnDelete;

    String serviceId;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    // single product url
    private static final String url_service_details = "http://1pour1.hebergratuit.net/get_service_details.php";

    // url to update product
    private static final String url_update_service = "http://1pour1.hebergratuit.net/update_service.php";

    // url to delete product
    private static final String url_delete_service = "http://1pour1.hebergratuit.net/delete_service.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";


    private static final String TAG_SERVICES = "Service";
    private static final String TAG_SERVICEID = "ServiceId";
    private static final String TAG_TITLE = "Titre";
    private static final String TAG_ADRESSE= "Lieu";
    private static final String TAG_DESCRIPTION = "Description";
    private static final String TAG_USAGERID = "UsagerId";

    private static final String TAG_DATE_EXECUTION = "DateExecution";



    TimePickerFragment timeFragment = new TimePickerFragment();
    DatePickerFragment dateFragment = new DatePickerFragment();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_services);

        // save button
        btnSave = findViewById(R.id.btnServiceSave);
        btnDelete = findViewById(R.id.btnServiceDelete);

        // getting product details from intent
        Intent i = getIntent();

        // getting product id (pid) from intent
        serviceId = i.getStringExtra(TAG_SERVICEID);

        // Getting complete product details in background thread
        new EditServicesActivity.GetServiceDetails().execute();




        

        // Create button

        Button btnChooseTime = findViewById(R.id.buttonNewChooseTime);
        Button btnChooseDate = findViewById(R.id.buttonNewChooseDate);



        // button click event
        btnChooseTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                timeFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnChooseDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dateFragment.show(getSupportFragmentManager(), "datePicker");

            }
        });

        // save button click event
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // starting background task to update product
                new EditServicesActivity.SaveServiceDetails().execute();
            }
        });

        // Delete button click event
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // deleting product in background thread
                new EditServicesActivity.DeleteService().execute();
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
            pDialog = new ProgressDialog(EditServicesActivity.this);
            pDialog.setMessage("Loading product details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Getting product details in background thread
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
                            txtAdresse = findViewById(R.id.inputNewServiceAdresse);
                            txtDesc = findViewById(R.id.inputNewServiceDescription);



                            // display product data in EditText
                            txtTitre.setText(service.getString(TAG_TITLE));
                            txtAdresse.setText(service.getString(TAG_ADRESSE));
                            txtDesc.setText(service.getString(TAG_DESCRIPTION));

                            String dateExecution = service.getString(TAG_DATE_EXECUTION);

                            // 2018-00-00 00:00:00
                            String year = dateExecution.substring(0,4);
                            String month = dateExecution.substring(5,7);
                            String day = dateExecution.substring(8,10);

                            String hour = dateExecution.substring(11,13);
                            String minute = dateExecution.substring(14,16);

                            timeFragment.setHourOfDayChosen(Integer.parseInt(hour));
                            timeFragment.setMinuteChosen(Integer.parseInt(minute));

                            dateFragment.setYearChosen(Integer.parseInt(year));
                            dateFragment.setMonthChosen(Integer.parseInt(month));
                            dateFragment.setDayChosen(Integer.parseInt(day));

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
     * Background Async Task to  Save service Details
     * */
    class SaveServiceDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditServicesActivity.this);
            pDialog.setMessage("Saving product ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Saving service
         * */
        protected String doInBackground(String... args) {

            // getting updated data from EditTexts
            //String titre= txtTitre.getText().toString();
            String adresse = txtAdresse.getText().toString();
            String description = txtDesc.getText().toString();

            // format : 2018-02-24 17:31:02
            String dateExecution = "" + dateFragment.getYearChosen() + "-" + dateFragment.getMonthChosen()
                    + "-" + dateFragment.getDayChosen() + " " + timeFragment.getHourOfDayChosen()
                    + "-" + timeFragment.getMinuteChosen() + "-00" ;

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_SERVICEID, serviceId));
            //params.add(new BasicNameValuePair(TAG_TITLE, titre));
            params.add(new BasicNameValuePair(TAG_ADRESSE, adresse));
            params.add(new BasicNameValuePair(TAG_DESCRIPTION, description));
            params.add(new BasicNameValuePair(TAG_DATE_EXECUTION, dateExecution));
            params.add(new BasicNameValuePair(TAG_USAGERID, "" + Utilisateur.getId()));

            // sending modified data through http request
            // Notice that update product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_update_service,
                    "POST", params);

            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    Log.d("SuccessUpdate", "successfully updated the service");

                    // successfully updated
                    Intent i = getIntent();
                    // send result code 100 to notify about service update
                    setResult(100, i);
                    finish();
                } else {
                    Log.d("FailedUpdate", "Failed to update the service");
                    // failed to update service
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
            // dismiss the dialog once service updated
            pDialog.dismiss();
        }
    }

    /*****************************************************************
     * Background Async Task to Delete Product
     * */
    class DeleteService extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = new ProgressDialog(EditServicesActivity.this);
            pDialog.setMessage("Deleting Product...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        /**
         * Deleting product
         * */
        protected String doInBackground(String... args) {

            // Check for success tag
            int success;
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                Log.d("ServiceId", serviceId);
                params.add(new BasicNameValuePair("ServiceId", serviceId));

                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                        url_delete_service, "POST", params);

                // check your log for json response
                Log.d("Delete Product", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {

                    Log.d("DeleteServiceSuccessful", "The service has been successfully deleted");

                    // service successfully deleted
                    // notify previous activity by sending code 100
                    Intent i = getIntent();
                    // send result code 100 to notify about product deletion
                    setResult( 100, i);
                    finish();
                }
            } catch (JSONException e) {
                Log.d("DeleteServiceFailed", "The service hasn't been deleted");
                e.printStackTrace();
            }

            return null;
        }


        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once service has been deleted
            pDialog.dismiss();

        }

    }
}
