package a1pour1.hebergratuit.net.a1pour1;

/**
 * Created by SPORE on 06/02/2018.
 */

import java.io.InputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;

import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

public class JSONParser  {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";


    // constructor
    public JSONParser() {

    }






    // function get json from url
    // by making HTTP POST or GET mehtod
    public JSONObject makeHttpRequest(String url, String method,
                                      List<NameValuePair> params) {


        // Making HTTP request
        try {

            // check for request method
            if(method == "POST"){
                // request method is POST
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(params));

                if(MainScreenActivity.COOKIES != null) {
                    // needed to bypass the testcookie-nginx-module see here : https://stackoverflow.com/questions/31912000/byethost-server-passing-html-values-checking-your-browser-with-json-string
                    httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.10240 ");
                    //httpPost.addHeader("Cookie", "__test=" + getCookieContent(url) +"; expires=Fri, 01-Jan-38 00:55:55 GMT; path=/");
                    httpPost.addHeader("Cookie", MainScreenActivity.COOKIES + "; expires=Fri, 01-Jan-38 00:55:55 GMT; path=/");
                }


                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            }else if(method == "GET"){
                // request method is GET
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;



                HttpGet httpGet = new HttpGet(url);

                if(MainScreenActivity.COOKIES != null) {
                    // needed to bypass the testcookie-nginx-module see here : https://stackoverflow.com/questions/31912000/byethost-server-passing-html-values-checking-your-browser-with-json-string
                    httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.10240 ");
                    httpGet.addHeader("Cookie", MainScreenActivity.COOKIES + "; expires=Fri, 01-Jan-38 00:55:55 GMT; path=/");
                }


                HttpResponse httpResponse = httpClient.execute(httpGet);

                Log.d("TestURL",url);

                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);

            StringBuilder sb = new StringBuilder();
            String line = null;


            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            Log.d("Reader Result", sb.toString() );

            is.close();

            String temp = "";
            try {
                temp = sb.toString();
            }catch(Exception e){
                Log.e("Converting Error", "Error converting sb (StringBuilder) to temp (String) " + e.toString());
            }
            // search the json object in the page
            // the indexOf return the index of the first char found in the string   ex: in the string "test JSON:" , indexOf("JSON:") returns 5
            int startIndex = temp.indexOf("JSON:") + "JSON:".length();
            int endIndex = temp.indexOf(":END");

            if(startIndex != -1 && endIndex != -1){
                json = temp.substring(startIndex, endIndex);
            }
            else{
                Log.w("SubString error", "the substring hasn't been created");
                String error = "{\"products\":[{\"productID\":\"21\",\"name\":\"error\",\"price\":\"20\",\"created_at\":\"2018-02-06 19:26:23\",\"updated_at\":\"0000-00-00 00:00:00\"}],\"success\":1}";
                json = error;
            }




        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            Log.d("jOBJ Line 127", json.toString());
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", " Error parsing data " + e.toString());
        }


        // return JSON String
        return jObj;

    }

    // tests for volley module
    // TO DO : https://www.androidtutorialpoint.com/networking/android-volley-tutorial/
    // and this to bypass the nginx module   https://stackoverflow.com/questions/40806052/generating-cookie-from-website-that-implements-testcookie-nginx-module



    private static final String TAG = "JSONParser";
    //ProgressDialog progressDialog;

    public StringRequest volleyStringRequest(String url){



        //And that's all, do you have any other solution yet? if not you could check this :D

        String  REQUEST_TAG = "net.hebergratuit.1pour1.volleyStringRequest";


        //progressDialog.setMessage("Loading...");
        //progressDialog.show();


        // DEBUG
        Log.d(TAG,"test");

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());


                /*LayoutInflater li = LayoutInflater.from(MainActivity.this);
                showDialogView = li.inflate(R.layout.show_dialog, null);
                outputTextView = (TextView)showDialogView.findViewById(R.id.text_view_dialog);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setView(showDialogView);
                alertDialogBuilder
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .setCancelable(false)
                        .create();

                outputTextView.setText(response.toString());
                alertDialogBuilder.show();
                */
               //progressDialog.hide();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // DEBUG
                Log.d(TAG,"test on error");
                //progressDialog.hide();
            }
        });

        return strReq;


    }

    public void volleyJsonObjectRequest(String url){

        /*
        String  REQUEST_TAG = "net.hebergratuit.1pour1.volleyJsonObjectRequest";

        progressDialog.setMessage("Loading...");
        progressDialog.show();


        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                        /*
                        LayoutInflater li = LayoutInflater.from(MainActivity.this);
                        showDialogView = li.inflate(R.layout.show_dialog, null);
                        outputTextView = (TextView)showDialogView.findViewById(R.id.text_view_dialog);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        alertDialogBuilder.setView(showDialogView);
                        alertDialogBuilder
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                })
                                .setCancelable(false)
                                .create();
                        outputTextView.setText(response.toString());
                        alertDialogBuilder.show();

                        //progressDialog.hide();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //progressDialog.hide();
            }
        });

        // Adding JsonObject request to request queue
        //AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq,REQUEST_TAG);
        */
    }
    public void volleyJsonArrayRequest(String url){
        /*

        String  REQUEST_TAG = "net.hebergratuit.1pour1.volleyJsonArrayRequest";

        progressDialog.setMessage("Loading...");
        progressDialog.show();


        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        /*
                        LayoutInflater li = LayoutInflater.from(MainActivity.this);
                        showDialogView = li.inflate(R.layout.show_dialog, null);
                        outputTextView = (TextView)showDialogView.findViewById(R.id.text_view_dialog);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        alertDialogBuilder.setView(showDialogView);
                        alertDialogBuilder
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                })
                                .setCancelable(false)
                                .create();
                        outputTextView.setText(response.toString());
                        alertDialogBuilder.show();

                        //progressDialog.hide();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //progressDialog.hide();
            }
        });

        // Adding JsonObject request to request queue
        //AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayReq, REQUEST_TAG);
        */
    }

    public void volleyImageLoader(String url){
        /*
        ImageLoader imageLoader = AppSingleton.getInstance(getApplicationContext()).getImageLoader();

        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Image Load Error: " + error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {


                    LayoutInflater li = LayoutInflater.from(MainActivity.this);
                    showDialogView = li.inflate(R.layout.show_dialog, null);
                    outputImageView = (ImageView)showDialogView.findViewById(R.id.image_view_dialog);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setView(showDialogView);
                    alertDialogBuilder
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            })
                            .setCancelable(false)
                            .create();
                    outputImageView.setImageBitmap(response.getBitmap());
                    alertDialogBuilder.show();

                }
            }
        });
        */
    }

    public void volleyCacheRequest(String url){
        /*
        Cache cache = AppSingleton.getInstance(getApplicationContext()).getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        if(entry != null){
            try {
                String data = new String(entry.data, "UTF-8");
                // handle data, like converting it to xml, json, bitmap etc.,
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        else{

        }
        */
    }

    public void volleyInvalidateCache(String url){
        //AppSingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().invalidate(url, true);
    }

    public void volleyDeleteCache(String url){
        //AppSingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().remove(url);
    }

    public void volleyClearCache(){
        //AppSingleton.getInstance(getApplicationContext()).getRequestQueue().getCache().clear();
    }


}