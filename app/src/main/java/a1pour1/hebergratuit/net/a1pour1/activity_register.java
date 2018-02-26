package a1pour1.hebergratuit.net.a1pour1;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

public class activity_register extends AppCompatActivity {

    JSONParser jsonParser = new JSONParser();

    EditText inputFirstName;
    EditText inputSurname;
    EditText inputEmail;
    EditText inputPhone;
    EditText inputCity;
    EditText inputAdresse;
    EditText inputPassword;

    // url to create new product
    private static String url_register = "http://1pour1.hebergratuit.net/register.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";


    boolean displayDialog = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // dialogAlreadyReg = new Dialog(activity_register.this);

        // Edit Text
        inputFirstName = findViewById(R.id.firstNameTxt);
        inputSurname = findViewById(R.id.surnameTxt);
        inputEmail = findViewById(R.id.emailTxt);
        inputPhone = findViewById(R.id.phoneTxt);
        inputCity = findViewById(R.id.cityTxt);
        inputAdresse = findViewById(R.id.adresseTxt);
        inputPassword = findViewById(R.id.passwordTxt);


        // Create button
        Button btnRegister = findViewById(R.id.buttonRegister);

        // button click event
        btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread
                new activity_register.CreateNewUser().execute();
            }
        });
    }

    /**
     * Background Async Task to Create new product
     */
    class CreateNewUser extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*
            pDialog = new ProgressDialog(activity_register.this);
            pDialog.setMessage("Creating Product..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            */
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
            displayDialog = false;

            String firstName = inputFirstName.getText().toString();
            String surname = inputSurname.getText().toString();
            String email = inputEmail.getText().toString();
            String phone = inputPhone.getText().toString();
            String city = inputCity.getText().toString();
            String adresse = inputAdresse.getText().toString();
            String password = inputPassword.getText().toString();


            // the order: the same as the database
            //$nom', '$prenom', '$adresseMail', '$mdp', $numTel, '$adresse', '$ville

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Nom", surname));
            params.add(new BasicNameValuePair("Prenom", firstName));
            params.add(new BasicNameValuePair("AdresseMail", email));
            params.add(new BasicNameValuePair("Mdp", password));
            params.add(new BasicNameValuePair("NumTel", phone));
            params.add(new BasicNameValuePair("Adresse", adresse));
            params.add(new BasicNameValuePair("Ville", city));



            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_register,
                    "POST", params);

            // check log cat for response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if(success == 2){
                    Log.d("activity_register", "the user is already in the database");
                    displayDialog = true;

                }

                else if (success == 1) {
                    // successfully register
                    //Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    //startActivity(i);

                    // closing this screen
                    finish();
                } else {
                    // failed to register
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {

            if(displayDialog) {
                AlertDialog alertDialog = new AlertDialog.Builder(activity_register.this).create();
                alertDialog.setTitle("Already registered");
                alertDialog.setMessage("You are already registered!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            // dismiss the dialog once done
            //pDialog.dismiss();
        }
    }
}
