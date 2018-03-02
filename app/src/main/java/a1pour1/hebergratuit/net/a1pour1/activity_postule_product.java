package a1pour1.hebergratuit.net.a1pour1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

/**
 * Created by SPORE on 02/03/2018.
 */

public class activity_postule_product extends AppCompatActivity {

    TextView txtName;
    TextView txtBrand;
    TextView txtAge;
    TextView txtDesc;

    Button btnPostule;

    String pid;

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    // single product url
    private static final String url_product_details = "http://1pour1.hebergratuit.net/get_product_details.php";

    // url to apply for a product
    private static final String url_apply_product = "http://1pour1.hebergratuit.net/apply_product.php";


    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCT = "Produit";
    private static final String TAG_PID = "ProduitId";
    private static final String TAG_NAME = "Nom";
    private static final String TAG_BRAND = "Marque";
    private static final String TAG_AGE = "Age";
    private static final String TAG_DESCRIPTION = "Description";
    private static final String TAG_USAGERID = "UsagerId";





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postule_product);

        // save button
        btnPostule = findViewById(R.id.btnPostuleProduct);

        // getting product details from intent
        Intent i = getIntent();

        // getting product id (pid) from intent
        pid = i.getStringExtra(TAG_PID);

        // Getting complete product details in background thread
        new GetProductDetails().execute();


        // Delete button click event
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
    class GetProductDetails extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity_postule_product.this);
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
                        params.add(new BasicNameValuePair("ProduitId", pid));



                        // getting product details by making HTTP request
                        // Note that product details url will use POST request
                        JSONObject json = jsonParser.makeHttpRequest(
                                url_product_details, "POST", params);


                        // check your log for json response
                        //Log.d("Single Product Details", json.toString());

                        // json success tag
                        success = json.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            // successfully received product details
                            JSONArray productObj = json
                                    .getJSONArray(TAG_PRODUCT); // JSON Array

                            // get first product object from JSON Array
                            JSONObject product = productObj.getJSONObject(0);

                            // product with this pid found
                            // TextViews
                            txtName = findViewById(R.id.name);
                            txtBrand = findViewById(R.id.displayBrand);
                            txtAge = findViewById(R.id.displayAge);
                            txtDesc = findViewById(R.id.displayDescription);

                            // display product data in EditText
                            txtName.setText(product.getString(TAG_NAME));
                            txtBrand.setText(product.getString(TAG_BRAND));
                            txtAge.setText(product.getString(TAG_AGE));
                            txtDesc.setText(product.getString(TAG_DESCRIPTION));

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
            pDialog = new ProgressDialog(activity_postule_product.this);
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
            params.add(new BasicNameValuePair(TAG_PID, pid));
            params.add(new BasicNameValuePair(TAG_USAGERID, "" + Utilisateur.getId()));

            // sending the pid and the UserId
            // Notice that update product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_apply_product,
                    "GET", params);

            // check json success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    Log.d("SuccessApply", "successfully applying for the product");

                    // successfully applied
                    Intent i = getIntent();
                    // send result code 100 to notify about product update
                    setResult(100, i);
                    finish();
                } else {
                    Log.d("FailedApply", "Failed to apply for the product");
                    // failed to apply for a product
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
            // dismiss the dialog once applied for the product
            pDialog.dismiss();
        }
    }


}