package com.internship.zappos.ilovemarshmallow.controllers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.internship.zappos.ilovemarshmallow.R;


/**
 * Created by arjundatt.16 on 9/6/2015.
 */
public class ListItemViewHolder extends RecyclerView.ViewHolder {

    protected TextView mProductName;
    protected TextView mPrice;
    protected TextView mBrand;
    protected RatingBar mRating;
    protected ImageView mImage;
    protected LinearLayout mRoot;
    protected TextView mProductUrl;




    public ListItemViewHolder(View itemView) {
        super(itemView);
        mProductName = (TextView)itemView.findViewById(R.id.product_name);
        mPrice = (TextView)itemView.findViewById(R.id.price);
        mBrand = (TextView)itemView.findViewById(R.id.brand);
        mRating = (RatingBar)itemView.findViewById(R.id.rating);
        mImage = (ImageView)itemView.findViewById(R.id.product_img);
        mRoot = (LinearLayout) itemView.findViewById(R.id.list_item_root);
        mProductUrl = (TextView) itemView.findViewById(R.id.product_url_hidden);

    }
}
