package a1pour1.hebergratuit.net.a1pour1;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by SPORE on 25/02/2018.
 */

public class MyServicesProductsActivity extends AppCompatActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> productsList;

    ArrayList<HashMap<String, String>> servicesList;

    // url to get my products and services list
    private static String url_my_prods_servs= "http://1pour1.hebergratuit.net/get_my.php";



    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "Produits";
    private static final String TAG_PID = "ProduitId";
    private static final String TAG_NAME = "Nom";

    private static final String TAG_SERVICES = "Services";
    private static final String TAG_SERVICEID = "ServiceId";
    private static final String TAG_TITLE = "Titre";
    //private static final String TAG_MARQUE = "Marque";
    //private static final String TAG_DESCRIPTION = "Description";

    private static final String TAG_CHECK_PRODUCTS = "checkProduits";
    private static final String TAG_CHECK_SERVICES = "checkServices";


    // products JSONArray
    private JSONArray products = null;

    //services JSONArray
    private JSONArray services = null;


    ListView listViewProd;
    ListView listViewServ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_servs_prods);





        // see https://stackoverflow.com/questions/25093546/android-os-networkonmainthreadexception-at-android-os-strictmodeandroidblockgua
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }






        // Hashmap for ListView
        productsList = new ArrayList<HashMap<String, String>>();

        servicesList = new ArrayList<HashMap<String, String>>();


        // Loading products in Background Thread
        new MyServicesProductsActivity.LoadAllMy().execute();

        // Get listviews
        listViewProd = findViewById(R.id.listProd);
        listViewServ = findViewById(R.id.listServ);

        // on selecting single product
        // launching Edit Product Screen
        listViewProd.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String pid = ((TextView) view.findViewById(R.id.pid)).getText()
                        .toString();

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        EditProductActivity.class);
                // sending pid to next activity
                in.putExtra(TAG_PID, pid);

                // starting new activity and expecting some response back
                startActivityForResult(in, 100);
            }
        });

        listViewServ.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String serviceId = ((TextView) view.findViewById(R.id.serviceId)).getText()
                        .toString();

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        EditProductActivity.class);
                // sending pid to next activity
                in.putExtra(TAG_SERVICEID, serviceId);

                // starting new activity and expecting some response back
                startActivityForResult(in, 100);
            }
        });

    }

    // Response from Edit Product Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if result code 100
        if (resultCode == 100) {
            // if result code 100 is received
            // means user edited/deleted product
            // reload this screen again
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

    }

    /**
     * Background Async Task to Load all My prods/servs by making HTTP Request
     * */
    class LoadAllMy extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {



            super.onPreExecute();
            pDialog = new ProgressDialog(MyServicesProductsActivity.this);
            pDialog.setMessage("Loading products. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();




        }



        /**
         * getting All My from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("UsagerId", "" + Utilisateur.getId()));



            JSONObject json = jParser.makeHttpRequest(url_my_prods_servs, "POST", params);


            // Check your log cat for JSON reponse
            Log.d("All My ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                int checkProduits = json.getInt(TAG_CHECK_PRODUCTS);
                int checkServices = json.getInt(TAG_CHECK_SERVICES);


                if (success == 1) {
                    // these checks below are in case we have products and don't have services or alternatively

                    if(checkProduits == 1){
                        // products found
                        // Getting Array of Products
                        products = json.getJSONArray(TAG_PRODUCTS);



                        // looping through All Products
                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);

                            // Storing each json item in variable
                            String id = c.getString(TAG_PID);
                            String name = c.getString(TAG_NAME);

                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            map.put(TAG_PID, id);
                            map.put(TAG_NAME, name);

                            // adding HashList to ArrayList
                            productsList.add(map);
                        }
                    }
                    if(checkServices == 1){
                        // services found
                        // Getting Array of services
                        services = json.getJSONArray(TAG_SERVICES);

                        // looping through All Services
                        for (int i = 0; i < services.length(); i++) {
                            JSONObject c = services.getJSONObject(i);

                            // Storing each json item in variable
                            String id = c.getString(TAG_SERVICEID);
                            String title = c.getString(TAG_TITLE);

                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            map.put(TAG_SERVICEID, id);
                            map.put(TAG_TITLE, title);

                            // adding HashList to ArrayList
                            servicesList.add(map);
                        }
                    }
                } else {
                    // no products and services found
                    // Launch Add New product Activity
                    Intent i = new Intent(getApplicationContext(),
                            NewProductActivity.class);
                    // Closing all previous activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
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

            // dismiss the dialog after getting all products
            pDialog.dismiss();

            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapterProducts = new SimpleAdapter(
                            MyServicesProductsActivity.this, productsList,
                            R.layout.list_item, new String[] { TAG_PID,
                            TAG_NAME},

                            new int[] { R.id.pid, R.id.name });

                    ListAdapter adapterServices = new SimpleAdapter(
                            MyServicesProductsActivity.this, servicesList,
                            R.layout.list_service, new String[] { TAG_SERVICEID,
                            TAG_TITLE},

                            new int[] { R.id.serviceId, R.id.serviceTitre });

                    // updating listview
                    listViewProd.setAdapter(adapterProducts);
                    listViewServ.setAdapter(adapterServices);

                }
            });

        }

    }
}
