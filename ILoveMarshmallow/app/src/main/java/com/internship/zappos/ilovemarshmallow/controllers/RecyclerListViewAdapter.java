package com.internship.zappos.ilovemarshmallow.controllers;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.internship.zappos.ilovemarshmallow.MainActivity;
import com.internship.zappos.ilovemarshmallow.R;
import com.internship.zappos.ilovemarshmallow.fragments.ProductViewFragment;
import com.internship.zappos.ilovemarshmallow.model.QueryResultItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by arjundatt.16 on 9/6/2015.
 */
public class RecyclerListViewAdapter extends RecyclerView.Adapter<ListItemViewHolder> {

    private Context mContext;
    private ArrayList<QueryResultItem> mList;
    private static RecyclerListViewAdapter mInstance;


    private RecyclerListViewAdapter(){}

    public static RecyclerListViewAdapter getInstance(Context context, ArrayList<QueryResultItem> list){
        if(mInstance==null) {
            mInstance = new RecyclerListViewAdapter();
        }
        mInstance.mContext = context;
        mInstance.mList = list;
        return mInstance;
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        ListItemViewHolder vHolder = new ListItemViewHolder(view);
        return vHolder;
    }


    @Override
    public void onBindViewHolder(ListItemViewHolder listItemViewHolder, int i) {
        listItemViewHolder.mProductName.setText(mList.get(i).getProductName());
        listItemViewHolder.mRating.setRating(mList.get(i).getRating());
        listItemViewHolder.mPrice.setText(mList.get(i).getPrice());
        listItemViewHolder.mBrand.setText(mList.get(i).getBrand());
        Picasso.with(mContext).load(mList.get(i).getImageUrl())
                .error(R.drawable.img_sample)
                .placeholder(R.drawable.img_sample)
                .into(listItemViewHolder.mImage);
        listItemViewHolder.mRoot.setOnClickListener(ItemClickEvent);
        listItemViewHolder.mProductUrl.setText(MainActivity.PRODUCT_URL_PREFIX + "" + mList.get(i).getAsin());
        //Log.i(MainActivity.TAG, "onBindViewHolder" + i);
    }

    View.OnClickListener ItemClickEvent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i(MainActivity.TAG,"clicked: "+(mContext instanceof MainActivity));
            if(mContext instanceof MainActivity){
                MainActivity mainActivity = (MainActivity) mContext;
                mainActivity.intiProductInfoPage(((TextView)v.findViewById(R.id.product_url_hidden)).getText().toString());
            }
        }
    };

    @Override
    public int getItemCount() {
        return null!=mList ? mList.size() : 0;
    }

}
