package com.internship.zappos.ilovemarshmallow;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.internship.zappos.ilovemarshmallow.fragments.HeadlessFragment;
import com.internship.zappos.ilovemarshmallow.fragments.ListResultsFragment;
import com.internship.zappos.ilovemarshmallow.fragments.ProductViewFragment;
import com.internship.zappos.ilovemarshmallow.model.QueryResultItem;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  HeadlessFragment.OnHeadlessInteractionListener, ListResultsFragment.OnRecyclerInteractionListener, ProductViewFragment.OnProductInfoInteractionListener {

    SearchView mSearchView;
    Button mSearchBtn;
    Context mContext;
    FrameLayout mProgressBar;

    public static String TAG="LoveMarshmallow";
    public static final String URL_SCHEME = "https";
    public static final String URL_HOST = "zappos.amazon.com";
    public static final String PRODUCT_URL_PREFIX="https://zappos.amazon.com/mobileapi/v1/product/asin/";
    public static final String ENDPOINT_URL="https://zappos.amazon.com/mobileapi/v1/search?term=";

    HeadlessFragment mHeadlessFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        mSearchView = (SearchView) findViewById(R.id.search_field);
        mSearchBtn = (Button) findViewById(R.id.search_btn);
        mProgressBar = (FrameLayout) findViewById(R.id.query_in_progress);
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSearch(ENDPOINT_URL+""+mSearchView.getQuery().toString());
            }
        });
        Intent initIntent = getIntent();
        if(initIntent!=null && initIntent.getAction().equals(Intent.ACTION_VIEW)){
            Log.i(TAG, "initIntent "+initIntent.getData().getPath());
            initSearch(URL_SCHEME+"://"+URL_HOST+""+initIntent.getData().getPath());
            //initSearch(initIntent.getData().toString());
        }

    }

    private void initSearch(String query){
        if(query!=null && query!=null){
            mProgressBar.setVisibility(View.VISIBLE);
            mSearchBtn.setClickable(false);
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
            FragmentManager mFragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            mHeadlessFragment = HeadlessFragment.newInstance(query);
            ft.add(R.id.headless_fragment_container, mHeadlessFragment);
            ft.commit();

        }
        else{
            showToast("Enter a valid query.");
        }
    }

    void showToast(String msg){
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResultFetched(Bundle list) {
        FrameLayout container = (FrameLayout)findViewById(R.id.list_fragment_container);
        if(list.getBoolean("isProductInfo")){
            container.setVisibility(View.GONE);
            ProductViewFragment mFragment = ProductViewFragment.newInstance(list);
            FragmentManager mFragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.add(R.id.product_info_container, mFragment);
            ft.addToBackStack(null);
            ft.commit();
        }
        else {
            container.setVisibility(View.VISIBLE);
            ListResultsFragment mFragment = ListResultsFragment.newInstance(list);
            FragmentManager mFragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.replace(R.id.list_fragment_container, mFragment);
            ft.commit();
        }
        mProgressBar.setVisibility(View.GONE);
        mSearchBtn.setClickable(true);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void intiProductInfoPage(String url){
        initSearch(url);
    }

    @Override
    public void activateShare(String url) {
        //not in use
    }
}
