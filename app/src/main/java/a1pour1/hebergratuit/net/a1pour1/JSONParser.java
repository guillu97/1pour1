package a1pour1.hebergratuit.net.a1pour1;

/**
 * Created by SPORE on 06/02/2018.
 */

import android.util.Log;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

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

                // needed to bypass the testcookie-nginx-module see here : https://stackoverflow.com/questions/31912000/byethost-server-passing-html-values-checking-your-browser-with-json-string


                Log.d("JSOnParser", "In Post : Cookies: " + MainScreenActivity.COOKIES);
                httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.10240 ");
                //httpPost.addHeader("Cookie", "__test=" + getCookieContent(url) +"; expires=Fri, 01-Jan-38 00:55:55 GMT; path=/");
                if(MainScreenActivity.COOKIES != null) {
                    httpPost.addHeader("Cookie", MainScreenActivity.COOKIES + "; expires=Fri, 31-Dec-37 23:55:55 GMT; path=/");
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

                // needed to bypass the testcookie-nginx-module see here : https://stackoverflow.com/questions/31912000/byethost-server-passing-html-values-checking-your-browser-with-json-string

                Log.d("JSOnParser", "In Get : Cookies: " + MainScreenActivity.COOKIES);
                httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.10240 ");
                if(MainScreenActivity.COOKIES != null) {
                    httpGet.addHeader("Cookie", MainScreenActivity.COOKIES + "; expires=Fri, 1-Jan-38 00:55:55 GMT; path=/");
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
                String error = "{\"products\":[{\"productID\":\"21\",\"name\":\"error\",\"price\":\"20\",\"created_at\":\"2018-02-06 19:26:23\",\"updated_at\":\"0000-00-00 00:00:00\"}],\"success\":0}";
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

    // if we add volley module here
    // https://www.androidtutorialpoint.com/networking/android-volley-tutorial/
    // and this to bypass the nginx module   https://stackoverflow.com/questions/40806052/generating-cookie-from-website-that-implements-testcookie-nginx-module




}