package com.indianbhaskartools.whatsappstatusdownloader;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.indianbhaskartools.whatsappstatusdownloader.adapter.myadapter;
import com.indianbhaskartools.whatsappstatusdownloader.models.AppModel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BackActivity extends AppCompatActivity {
    private static final String TAG = "BackActivity";
    private AdView adView;

    TextView btnNo;
    TextView btnyes;
    boolean doubleBackToExitPressedOnce = false;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    List<AppModel> mDataset;
    public static final String JSON_URL = "https://samarthshah2866.000webhostapp.com/lovevideostatus/moreapp.json";

    ProgressBar progress_bar;
    private TextView tt_Rate;


    class C02061 implements OnClickListener {
        C02061() {
        }

        public void onClick(View view) {
            BackActivity.this.finish();
        }
    }

    class C02072 implements OnClickListener {
        C02072() {
        }

        public void onClick(View view) {
            Intent myAppLinkToMarket = new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName()));
            myAppLinkToMarket.setFlags(268468224);
            try {
                BackActivity.this.startActivity(myAppLinkToMarket);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(BackActivity.this, "You don't have Google Play installed", 1).show();
            }
        }
    }

    class C02083 implements OnClickListener {
        C02083() {
        }

        public void onClick(View view) {
            Intent intent = new Intent(BackActivity.this, MainActivity.class);
            intent.setFlags(268468224);
            BackActivity.this.startActivity(intent);
        }
    }

    class C02138 implements Runnable {
        C02138() {
        }

        public void run() {
            BackActivity.this.doubleBackToExitPressedOnce = false;
        }
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_back);
        this.adView = (AdView) findViewById(R.id.adView);
        this.adView.loadAd(new Builder().build());
        this.btnyes = (TextView) findViewById(R.id.btnyes);
        this.btnNo = (TextView) findViewById(R.id.btnNo);
        this.tt_Rate = (TextView) findViewById(R.id.tt_Rate);
        this.btnyes.setOnClickListener(new C02061());
        this.tt_Rate.setOnClickListener(new C02072());
        this.btnNo.setOnClickListener(new C02083());

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        sendRequest();

    }

    private void sendRequest(){


        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress_bar.setVisibility(View.INVISIBLE);
                        Log.d("data==", "onResponse: "+response.toString());
                        JSONParse pj = new JSONParse(response);
                        pj.parseJSON();
                        mDataset = pj.getMovies();
                        mAdapter = new myadapter(mDataset,BackActivity.this);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BackActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public void onBackPressed() {
        if (this.doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Snackbar snackbar = Snackbar.make(this.btnyes, (CharSequence) "click BACK again to exit", -1);
        ((TextView) snackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(-1);
        snackbar.show();
        new Handler().postDelayed(new C02138(), 2000);
    }
}
