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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddProductActivity extends AppCompatActivity {

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
                new AddProductActivity.CreateNewProduct().execute();
            }
        });
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
