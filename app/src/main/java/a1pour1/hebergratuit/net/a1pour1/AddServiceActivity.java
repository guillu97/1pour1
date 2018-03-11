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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddServiceActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();


    AutoCompleteTextView inputServiceTitle;
    AutoCompleteTextView inputServiceAdresse;
    AutoCompleteTextView inputServiceDescription;
    //AutoCompleteTextView inputServiceDateBegin;



    // url to create new product
    private static String url_create_service = "http://1pour1.hebergratuit.net/create_service.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";


    TimePickerFragment timeFragment;
    DatePickerFragment dateFragment;











    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);





        // Edit Text
        inputServiceTitle = findViewById(R.id.inputServiceTitle);
        inputServiceAdresse = findViewById(R.id.inputServiceAdresse);
        inputServiceDescription = findViewById(R.id.inputServiceDescription);
        populateAutoComplete();



        Spinner spinner = (Spinner) findViewById(R.id.categorie_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        timeFragment = new TimePickerFragment();
        dateFragment = new DatePickerFragment();


        // Create button

        Button btnChooseTime = findViewById(R.id.buttonChooseTime);
        Button btnChooseDate = findViewById(R.id.buttonChooseDate);

        Button btnCreateService = findViewById(R.id.buttonCreateService);


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



        btnCreateService.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            // creating new product in background thread
            attemptCreateService();
            }
        });



    }

    private void populateAutoComplete() {

        getLoaderManager().initLoader(0, null, this);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */

    private void attemptCreateService() {


        // Reset errors.
        inputServiceTitle.setError(null);
        inputServiceAdresse.setError(null);
        inputServiceDescription.setError(null);

        // Store values at the time of the attempt to create the service.
        String title = inputServiceTitle.getText().toString();
        String adresse = inputServiceAdresse.getText().toString();
        String description = inputServiceDescription.getText().toString();

        boolean cancel = false;
        View focusView = null;



        // Check for a non empty title.
        if (TextUtils.isEmpty(title)) {
            inputServiceTitle.setError(getString(R.string.error_field_required));
            focusView = inputServiceTitle;
            cancel = true;
        }
        // Check for a non empty adresse.
        if (TextUtils.isEmpty(adresse)) {
            inputServiceAdresse.setError(getString(R.string.error_field_required));
            focusView = inputServiceAdresse;
            cancel = true;
        }
        // Check for a non empty description.
        if (TextUtils.isEmpty(description)) {
            inputServiceDescription.setError(getString(R.string.error_field_required));
            focusView = inputServiceDescription;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // kick off a background task to
            // perform the create service attempt.
            new CreateNewService().execute();
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
    class CreateNewService extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {

            /*
            super.onPreExecute();
            pDialog = new ProgressDialog(AddServiceActivity.this);
            pDialog.setMessage("Creating Service...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            */


        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String titre = inputServiceTitle.getText().toString();
            String adresse = inputServiceAdresse.getText().toString();
            String description = inputServiceDescription.getText().toString();


            // format : 2018-02-24 17:31:02
            String dateBegin = "" + dateFragment.getYearChosen() + "-" + dateFragment.getMonthChosen()
                    + "-" + dateFragment.getDayChosen() + " " + timeFragment.getHourOfDayChosen()
                    + "-" + timeFragment.getMinuteChosen() + "-00" ;


            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Titre", titre));
            params.add(new BasicNameValuePair("DateExecution", dateBegin));
            params.add(new BasicNameValuePair("Description", description));
            params.add(new BasicNameValuePair("Lieu", adresse));

            params.add(new BasicNameValuePair("UsagerId", "" + Utilisateur.getId()));



            // getting JSON Object
            // Note that create service url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_service,
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
            //pDialog.dismiss();
        }

    }


}
