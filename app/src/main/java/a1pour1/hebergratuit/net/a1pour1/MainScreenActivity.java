package a1pour1.hebergratuit.net.a1pour1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainScreenActivity extends AppCompatActivity {


    static String COOKIES;
    private static String url_test = "http://1pour1.hebergratuit.net/";

    //Change
    Button btnViewProducts;
    Button btnNewProduct;
    private WebView myWebView;
    // to check wifi state after
    private WifiState wifiState = new WifiState(MainScreenActivity.this);





    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);




        // Buttons
        btnViewProducts = findViewById(R.id.btnViewProducts);
        btnNewProduct = findViewById(R.id.btnCreateProduct);

        // view products click event
        btnViewProducts.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (wifiState.haveNetworkConnection() == true) {
                    // Launching All products Activity
                    Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
                    startActivity(i);
                }
                else{
                    alertDialogInternet.showAlertConnection(MainScreenActivity.this);
                }

            }
        });

        // view products click event
        btnNewProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (wifiState.haveNetworkConnection() == true /*&& canClick == true*/) {
                    // Launching create new product activity
                    //BUTTON ADD PRODUCT ANTOINE
                    Intent i = new Intent(getApplicationContext(), AddProductActivity.class);
                    startActivity(i);
                }
                else{
                    alertDialogInternet.showAlertConnection(MainScreenActivity.this);
                }


            }
        });






        if (wifiState.haveNetworkConnection()) {
                myWebView = findViewById(R.id.CookieLoader);
                myWebView.getSettings().setJavaScriptEnabled(true);
                myWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
                    {
                        String errorMsg = "errorCode: " +  String.valueOf(errorCode) + " description: " + description + " failingUrl: " + failingUrl;
                        Log.e("MainScreenActivity", errorMsg);
                        // Handle the error
                    }
                    // we should get the cookies when the page finished loading
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        COOKIES = CookieManager.getInstance().getCookie(url_test);
                        Log.d("MainScreenActivity", "In Mobile: Cookies: " + COOKIES);
                        //canClick = true;

                    }
                });

            myWebView.loadUrl(url_test);

            myWebView.setVisibility(View.GONE);
            //myWebView.destroy();

        } else {
            alertDialogInternet.showAlertConnection(MainScreenActivity.this);
        }

    }

    //REGISTER BUTTON BY ANTOINE
    public void addServiceButton(View view){
        startActivity(new Intent(this, AddServiceActivity.class));
    }

    @Override
    public boolean onNavigateUp() {
        //canClick = false;

        if (wifiState.haveNetworkConnection()) {
            
            myWebView = findViewById(R.id.CookieLoader);
            myWebView.getSettings().setJavaScriptEnabled(true);
            myWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    String errorMsg = "errorCode: " + String.valueOf(errorCode) + " description: " + description + " failingUrl: " + failingUrl;
                    Log.e("MainScreenActivity", errorMsg);
                    // Handle the error
                }

                // we should get the cookies when the page finished loading
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    COOKIES = CookieManager.getInstance().getCookie(url_test);
                    Log.d("MainScreenActivity", "In Mobile: Cookies: " + COOKIES);

                }
            });

            myWebView.loadUrl(url_test);

        }
        return super.onNavigateUp();
    }
}
