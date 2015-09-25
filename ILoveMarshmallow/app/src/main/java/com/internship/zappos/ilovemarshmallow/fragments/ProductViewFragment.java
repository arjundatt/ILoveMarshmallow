package com.internship.zappos.ilovemarshmallow.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.internship.zappos.ilovemarshmallow.MainActivity;
import com.internship.zappos.ilovemarshmallow.R;
import com.internship.zappos.ilovemarshmallow.model.ProductInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductViewFragment.OnProductInfoInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductViewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private ArrayList<ProductInfo> mList;
    private String productUrl;
    private TextView mTitle;
    private TextView mDescription;
    private ImageView mImageView;
    private TextView mOrigPrice;
    private TextView mPrice;
    private static ProductViewFragment mInstance=null;

    private MenuItem mShareMenuItem;

    private View mView;
    private View mMenu;

    private OnProductInfoInteractionListener mListener;
    private Bitmap mImageBitmap;
    private Button mAddToCart;
    private TextView mProductColor;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param list Parameter 1.
     * @return A new instance of fragment ProductViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductViewFragment newInstance(Bundle list) {
        mInstance = new ProductViewFragment();
        mInstance.setArguments(list);
        return mInstance;
    }

    public ProductViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mList = getArguments().getParcelableArrayList(HeadlessFragment.KEY_FETCHED_RESULT);
            productUrl = getArguments().getString(HeadlessFragment.PRODUCT_INFO_URL,"");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_product_view, container, false);
        mTitle = (TextView)mView.findViewById(R.id.exp_title);
        mDescription = (TextView) mView.findViewById(R.id.exp_description);
        mImageView = (ImageView) mView.findViewById(R.id.exp_image);
        mOrigPrice = (TextView) mView.findViewById(R.id.exp_orig_price);
        mPrice = (TextView) mView.findViewById(R.id.exp_price);
        mProductColor = (TextView) mView.findViewById(R.id.product_color);
        if(mList.get(0).getColor() != null){
            mProductColor.setVisibility(View.VISIBLE);
            mProductColor.setText(mList.get(0).getColor());
        }
        mAddToCart = (Button) mView.findViewById(R.id.add_to_cart);
        mAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Not configured!",Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayout mOrigPriceLayout = (LinearLayout) mView.findViewById(R.id.orig_price_layout);
        if((mList.get(0).getOrigPrice()!=null) && !(mList.get(0).getOrigPrice().equals(mList.get(0).getPrice()))){
            mOrigPriceLayout.setVisibility(View.VISIBLE);
            mOrigPrice.setText(mList.get(0).getOrigPrice());
        }
        TextView mYourdeal = (TextView) mView.findViewById(R.id.your_deal);
        if(("Out of stock!").equals(mList.get(0).getPrice())){
            mYourdeal.setVisibility(View.GONE);
        }
        mPrice.setText(mList.get(0).getPrice());
        mTitle.setText(mList.get(0).getProductName());
        Spanned htmlString = Html.fromHtml(mList.get(0).getDescription());
        mDescription.setText(htmlString);
        return mView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
        mShareMenuItem = menu.findItem(R.id.menu_item_share);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.menu_item_share){
            Log.i(MainActivity.TAG, "itemSelected");

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, getActivity().getResources().getString(R.string.share_string) + "" + productUrl);
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
//            setShareIntent(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mImageBitmap==null)
            new ImageLoaderAsyncTask().execute(mList.get(0).getImageUrl());
        else
            mImageView.setImageBitmap(mImageBitmap);
    }

    private class ImageLoaderAsyncTask extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... params) {
            URL url = null;
            Bitmap bitmap = null;
            HttpURLConnection urlConnection =null;
            try {
                url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inStream = urlConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inStream);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(urlConnection!=null){
                    urlConnection.disconnect();
                }
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mImageBitmap = bitmap;
            mImageView.setImageBitmap(mImageBitmap);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.activateShare(productUrl);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnProductInfoInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setMenuVisibility(true);
        FrameLayout container = (FrameLayout) getActivity().findViewById(R.id.product_info_container);
        container.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStop() {
        super.onStop();
        FrameLayout container = (FrameLayout) getActivity().findViewById(R.id.product_info_container);
        container.setVisibility(View.GONE);
    }

    @Override
    public void onDetach() {
        if(mListener!=null){
            mListener.triggerVisibility(View.VISIBLE);
        }
        mListener = null;
        super.onDetach();
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
    public interface OnProductInfoInteractionListener {
        // TODO: Update argument type and name
        public void activateShare(String url);
        public void triggerVisibility(int choice);
    }

}
