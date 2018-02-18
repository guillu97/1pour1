package a1pour1.hebergratuit.net.a1pour1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.widget.Button;

public class MainScreenActivity extends AppCompatActivity {

    // test 2
    //Guillu TEST
    //MACANTOINE
    //Change
    Button btnViewProducts;
    Button btnNewProduct;

    private WebView myWebView;
    private static String url_all_products = "http://1pour1.hebergratuit.net/get_all_products.php";
    static String COOKIES;



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
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
                startActivity(i);

            }
        });

        // view products click event
        btnNewProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching create new product activity
                Intent i = new Intent(getApplicationContext(), NewProductActivity.class);
                startActivity(i);

            }
        });



        myWebView = findViewById(R.id.CookieLoader);
        myWebView.setVisibility(View.GONE);
        myWebView.getSettings().setJavaScriptEnabled(true);

        String cookies = null;





        if(isConnectingToInternet(getApplicationContext())) {
            myWebView.loadUrl(url_all_products);
            cookies = CookieManager.getInstance().getCookie(url_all_products);
        }else{

            testInternetConnection.showAlertConnection(MainScreenActivity.this);
        }

        System.out.println(cookies);

        COOKIES = cookies;
        myWebView.destroy();


    }


    public static boolean isConnectingToInternet(Context mContext) {
        ConnectivityManager connectivity = (ConnectivityManager)mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }






}
