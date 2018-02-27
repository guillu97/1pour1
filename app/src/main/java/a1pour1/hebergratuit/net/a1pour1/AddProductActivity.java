package a1pour1.hebergratuit.net.a1pour1;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddProductActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    EditText inputProductName;
    EditText inputProductBrand;
    EditText inputProductDescription;
    EditText inputProductAge;



    // url to create new product
    private static String url_create_product = "http://1pour1.hebergratuit.net/create_product.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Edit Text
        inputProductName = findViewById(R.id.inputProductName);
        inputProductBrand = findViewById(R.id.inputProductBrand);
        inputProductDescription = findViewById(R.id.inputProductDescription);
        inputProductAge = findViewById(R.id.inputProductAge);


        // Create button
        Button btnCreateProduct = findViewById(R.id.buttonCreateProduct);

        // button click event
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread
                //new AddProductActivity.CreateNewProduct().execute();
                attemptCreateProduct();
            }
        });
    }

    private void attemptCreateProduct() {
        // Reset errors.
        inputProductName.setError(null);
        inputProductBrand.setError(null);
        inputProductDescription.setError(null);
        inputProductAge.setError(null);

        // Store values at the time of the attempt to create the service.
        String name = inputProductName.getText().toString();
        String brand= inputProductBrand.getText().toString();
        String description = inputProductDescription.getText().toString();
        String age = inputProductAge.getText().toString();

        boolean cancel = false;
        View focusView = null;



        // Check for a non empty title.
        if (TextUtils.isEmpty(name)) {
            inputProductName.setError(getString(R.string.error_field_required));
            focusView = inputProductName;
            cancel = true;
        }
        // Check for a non empty adresse.
        if (TextUtils.isEmpty(brand)) {
            inputProductBrand.setError(getString(R.string.error_field_required));
            focusView = inputProductBrand;
            cancel = true;
        }
        // Check for a non empty description.
        if (TextUtils.isEmpty(description)) {
            inputProductDescription.setError(getString(R.string.error_field_required));
            focusView = inputProductDescription;
            cancel = true;
        }
        // Check for a non empty description.
        if (TextUtils.isEmpty(age)) {
            inputProductAge.setError(getString(R.string.error_field_required));
            focusView = inputProductAge;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // kick off a background task to
            // perform the create service attempt.
            new AddProductActivity.CreateNewProduct().execute();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    /**
     * Background Async Task to Create new product
     * */
    class CreateNewProduct extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddProductActivity.this);
            pDialog.setMessage("Creating Product..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String name = inputProductName.getText().toString();
            String brand = inputProductBrand.getText().toString();
            String description = inputProductDescription.getText().toString();
            String age = inputProductAge.getText().toString();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Nom", name));
            params.add(new BasicNameValuePair("Marque", brand));
            params.add(new BasicNameValuePair("Description", description));
            params.add(new BasicNameValuePair("Age", age));
            params.add(new BasicNameValuePair("UsagerId", "" + Utilisateur.getId()));



            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);

            // check log cat for response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    Intent i = new Intent(getApplicationContext(), MyServicesProductsActivity.class);
                    startActivity(i);


                    // closing this screen
                    finish();
                } else {
                    // failed to create product
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
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }


}
