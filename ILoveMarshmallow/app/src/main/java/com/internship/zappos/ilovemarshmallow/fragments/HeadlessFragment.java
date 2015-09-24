package com.internship.zappos.ilovemarshmallow.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.internship.zappos.ilovemarshmallow.MainActivity;
import com.internship.zappos.ilovemarshmallow.R;
import com.internship.zappos.ilovemarshmallow.model.ProductInfo;
import com.internship.zappos.ilovemarshmallow.model.QueryResultItem;
import com.internship.zappos.ilovemarshmallow.utils.JSONWorker;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HeadlessFragment.OnHeadlessInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HeadlessFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HeadlessFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SEARCH_QUERY = "url";
    public static final String PRODUCT_INFO_URL = "product_url";
    // TODO: Rename and change types of parameters
    private String queryContent;
    ArrayList<QueryResultItem> mQueryItemsList;
    private Context mContext;
    private ArrayList<QueryResultItem> mQueryList;
    private ArrayList<ProductInfo> mProductInfo;
    public static final String KEY_FETCHED_RESULT = "arrayList";

    private OnHeadlessInteractionListener mListener;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     *
     * @return A new instance of fragment HeadlessFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HeadlessFragment newInstance(String param1) {
        HeadlessFragment fragment = new HeadlessFragment();
        Bundle args = new Bundle();
        args.putString(SEARCH_QUERY, param1);
        fragment.setArguments(args);
        return fragment;
    }


    public HeadlessFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            queryContent = getArguments().getString(SEARCH_QUERY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return null;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            //mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnHeadlessInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new EndpointInteractionAsyncTask().execute(queryContent);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private class EndpointInteractionAsyncTask extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... params) {
            StringBuilder builder = new StringBuilder();
            URL url = null;
            HttpsURLConnection urlConnection =null;
            try {
                url = new URL(params[0]);
                urlConnection = (HttpsURLConnection) url.openConnection();
                InputStream inStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br= new BufferedReader(new InputStreamReader(inStream));
                String line;
                while((line=br.readLine())!=null){
                    builder.append(line);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), getResources().getString(R.string.not_valid_query), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(MainActivity.TAG, "IOExcepton");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), getResources().getString(R.string.not_valid_query), Toast.LENGTH_LONG).show();
                    }
                });
            } finally {
                urlConnection.disconnect();
            }
            JSONWorker workerInstance = new JSONWorker();
            if(params[0].startsWith(MainActivity.ENDPOINT_URL)){
                mQueryItemsList = workerInstance.parseJSONforList(builder.toString());
                return false;
            }
            else if(params[0].startsWith(MainActivity.PRODUCT_URL_PREFIX)){
                mProductInfo = workerInstance.parseJSONforProduct(builder.toString(),params[0]);
                return true;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Boolean isProductInfo) {
            super.onPostExecute(isProductInfo);
            //Log.i(MainActivity.TAG, mListener+"   "+list.toString());
            Bundle b = new Bundle();
            b.putBoolean("isProductInfo",isProductInfo);
            if(isProductInfo) {
                b.putParcelableArrayList(KEY_FETCHED_RESULT,mProductInfo);
                b.putString(PRODUCT_INFO_URL, queryContent);
            }
            else{
                b.putParcelableArrayList(KEY_FETCHED_RESULT,mQueryItemsList);
            }
            if(mListener!=null) {
                mListener.onResultFetched(b);
            }

        }
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnHeadlessInteractionListener {
        // TODO: Update argument type and name
        public void onResultFetched(Bundle list);
    }

}
