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

public class AddServiceActivity extends AppCompatActivity {


    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    EditText inputServiceTitle;
    EditText inputServiceAdresse;
    EditText inputServiceDescription;
    EditText inputServiceDateBegin;



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


        // Create button
        Button btnCreateService = findViewById(R.id.buttonCreateService);
        Button btnChooseTime = findViewById(R.id.buttonChooseTime);
        Button btnChooseDate = findViewById(R.id.buttonChooseDate);

        // button click event
        btnCreateService.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread
                new CreateNewService().execute();
            }
        });


        btnChooseTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                timeFragment = new TimePickerFragment();
                timeFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnChooseDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dateFragment = new DatePickerFragment();
                dateFragment.show(getSupportFragmentManager(), "datePicker");

            }
        });


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
                    Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
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
