package com.example.archana.congress;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            String congressUrl = "https://congress.api.sunlightfoundation.com/legislators?apikey=85cf8790311f4b36a6a334a7d0396732";
            if(isNetworkAvailable()) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(congressUrl).build();

                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {


                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        try {
                            Log.v(TAG, response.body().string());
                            if (response.isSuccessful()) {

                            } else {
                                alertUserAboutError();
                            }

                        } catch (IOException e) {
                            Log.e(TAG, "Exception caught: ", e);
                        }

                    }
                });
            } else {
                Toast.makeText(this, R.string.network_unavailable_message,
                        Toast.LENGTH_LONG).show();
            }


            Log.d(TAG, "Main UI code is running");

        }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }


    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(),"error_dialog");
    }

}
