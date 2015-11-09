package com.example.archana.congress.ui;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.archana.congress.models.AlertDialogFragment;
import com.example.archana.congress.R;
import com.example.archana.congress.models.Representative;
import com.example.archana.congress.adapters.RepAdapter;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends ListActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private String mZipcode;
    private ArrayList<Representative> mRepresentatives;
    private RepAdapter mAdapter;
    private ListView mRepList;

    @Bind(R.id.zipcodeEditText) EditText mZipCodeInput;
    @Bind(R.id.goButton) Button mGoButton;
    @Bind(R.id.newSearchButton) Button mNewSearchButton;
   // @Bind(R.id.repWebsite) TextView mRepWebsite;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRepList = (ListView) findViewById(android.R.id.list);
        mRepresentatives = new ArrayList<Representative>();
        mAdapter = new RepAdapter(this, mRepresentatives);
        setListAdapter((ListAdapter) mAdapter);

        mNewSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleViews();
            }
        });

        mGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mZipcode = mZipCodeInput.getText().toString();
                getRepresentatives(mZipcode);
                toggleViews();
                mZipCodeInput.setText("");
            }

        });

//        mRepWebsite.setOnClickListener(new View.OnClickListener() {
//            @Override
//        public void onClick(View v) {
//                Uri webpage = Uri.parse("http://www.android.com");
//                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
//                startActivity(webIntent);
//
//
//
//
//            }
//        });


        //int zipcode = 97204;
    }

    private void getRepresentatives(String zipcode) {
        String apiKey = "85cf8790311f4b36a6a334a7d0396732";

        String url = "https://congress.api.sunlightfoundation.com/legislators/locate?zip=" + zipcode + "&apikey=" + apiKey;
        if (isNetworkAvailable()) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    Log.e(TAG, "response");
                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            getRepDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mAdapter.notifyDataSetChanged();
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "OH NO! IOException caught.");
                    } catch (JSONException e) {
                        Log.e(TAG, "OH NO! JSONException caught.");
                    }
                }
            });
        } else {
            alertUserAboutError();
        }
    }


    private void getRepDetails(String jsonData) throws JSONException{
            mRepresentatives.clear();
            JSONObject data = new JSONObject(jsonData);
            JSONArray representatives = data.getJSONArray("results");
            for(int index = 0; index < representatives.length(); index++) {
                JSONObject representativeJSON = representatives.getJSONObject(index);
                String repName = representativeJSON.getString("first_name") + " "
                        + representativeJSON.getString("last_name");
                String repParty = representativeJSON.getString("party");
                String repGender = representativeJSON.getString("gender");
                String repBirthday = representativeJSON.getString("birthday");
                String repPhone = representativeJSON.getString("phone");
                String repWebsite = representativeJSON.getString("website");
                String repOffice = representativeJSON.getString("office");
                Representative representative = new Representative();
                representative.setName(repName);
                representative.setParty(repParty);
                representative.setGender(repGender);
                representative.setBirthday(repBirthday);
                representative.setPhone(repPhone);
                representative.setWebsite(repWebsite);
                representative.setOffice(repOffice);

                mRepresentatives.add(representative);
            }
    }


    private void toggleViews() {
        if (mZipCodeInput.getVisibility() == View.VISIBLE) {
            mZipCodeInput.setVisibility(View.INVISIBLE);
            mGoButton.setVisibility(View.INVISIBLE);
            mRepList.setVisibility(View.VISIBLE);
            mNewSearchButton.setVisibility(View.VISIBLE);

        } else {
            mZipCodeInput.setVisibility(View.VISIBLE);
            mGoButton.setVisibility(View.VISIBLE);
            mRepList.setVisibility(View.INVISIBLE);
            mNewSearchButton.setVisibility(View.INVISIBLE);
        }
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
