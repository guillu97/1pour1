package a1pour1.hebergratuit.net.a1pour1;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by SPORE on 24/02/2018.
 */

public class AllServicesActivity extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> servicesList;

    // url to get all services list
    private static String url_all_services = "http://1pour1.hebergratuit.net/get_all_services.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_SERVICES = "Services";
    private static final String TAG_SERVICEID = "ServiceId";
    private static final String TAG_TITLE = "Titre";
    private static final String TAG_DESCRIPTION = "Description";


    // products JSONArray
    private JSONArray services = null;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_services);





        // see https://stackoverflow.com/questions/25093546/android-os-networkonmainthreadexception-at-android-os-strictmodeandroidblockgua
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }






        // Hashmap for ListView
        servicesList = new ArrayList<HashMap<String, String>>();

        // Loading products in Background Thread
        new LoadAllServices().execute();

        // Get listview
        ListView lv = getListView();

        // on selecting single product
        // launching Edit Product Screen

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting values from selected ListItem
                String serviceId = ((TextView) view.findViewById(R.id.serviceId)).getText()
                        .toString();

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        Activity_postule_service.class);
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
     * Background Async Task to Load all product by making HTTP Request
     * */

    class LoadAllServices extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */

        @Override
        protected void onPreExecute() {



            super.onPreExecute();
            pDialog = new ProgressDialog(AllServicesActivity.this);
            pDialog.setMessage("Loading services. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();




        }



        /**
         * getting All services from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();





            // getting JSON string from URL
            //StringRequest strReq = jParser.volleyStringRequest(url_all_products);
            // Adding String request to request queue
            //String  REQUEST_TAG = "net.hebergratuit.1pour1.volleyJsonObjectRequest";


            JSONObject json = jParser.makeHttpRequest(url_all_services, "GET", params);


            // Check your log cat for JSON reponse
            //Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // services found
                    // Getting Array of Services
                    services = json.getJSONArray(TAG_SERVICES);

                    // looping through All Services
                    for (int i = 0; i < services.length(); i++) {
                        JSONObject c = services.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(TAG_SERVICEID);
                        String title = c.getString(TAG_TITLE);
                        String description = c.getString(TAG_DESCRIPTION);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_SERVICEID, id);
                        map.put(TAG_TITLE, title);
                        map.put(TAG_DESCRIPTION, description);

                        // adding HashList to ArrayList
                        servicesList.add(map);
                    }
                } else {
                    // no products found
                    // Launch Add New service Activity

                    Intent i = new Intent(getApplicationContext(),
                            AddServiceActivity.class);
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
                    ListAdapter adapter = new SimpleAdapter(
                            AllServicesActivity.this, servicesList,
                            R.layout.list_service, new String[] { TAG_SERVICEID,
                            TAG_TITLE, TAG_DESCRIPTION},

                            new int[] { R.id.serviceId, R.id.serviceTitre, R.id.serviceDescription});
                    // updating listview
                    setListAdapter(adapter);

                }
            });

        }

    }

}
