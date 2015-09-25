package com.internship.zappos.ilovemarshmallow.utils;


import android.util.Log;

import com.internship.zappos.ilovemarshmallow.MainActivity;
import com.internship.zappos.ilovemarshmallow.model.ProductInfo;
import com.internship.zappos.ilovemarshmallow.model.QueryResultItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by arjundatt.16 on 9/4/2015.
 * This worker class parses the JSON data and
 * and returns the result in an ArrayList.
 */
public class JSONWorker {
    //private Context mContext;
    public JSONWorker(){
        //this.mContext = mContext;
    }

    public ArrayList<ProductInfo> parseJSONforProduct(String raw, String url){
        ArrayList<ProductInfo> mList = new ArrayList<>();
        String[] temp = url.split("/");
        String asinValue = temp[temp.length-1];
        Log.i(MainActivity.TAG, raw);
        try {
            JSONObject response = new JSONObject(raw);
            ProductInfo mItem = new ProductInfo();
            mItem.setBrandName(response.getString(QueryResultItem.BRAND_FIELD));
            mItem.setDescription(response.getString(ProductInfo.DESCRIPTION_FIELD));
            mItem.setProductName(response.getString(QueryResultItem.PRODUCT_NAME_FIELD));

            JSONArray results = response.getJSONArray(ProductInfo.LISTINGS);

            mItem.setImageUrl(response.getString(ProductInfo.DEFAULT_IMAGE_URL_FIELD));
            mItem.setColor(null);
            mItem.setOrigPrice(null);
            mItem.setPrice("Out of stock!");
            for(int i=0;i<results.length();i++){
                JSONObject jsonItem = results.getJSONObject(i);
                if(jsonItem.has(QueryResultItem.ASIN_FIELD) && asinValue.equals(jsonItem.getString(QueryResultItem.ASIN_FIELD))){
                    mItem.setColor(jsonItem.getString(ProductInfo.COLOR_FIELD));
                    mItem.setOrigPrice(""+jsonItem.getInt(ProductInfo.ORIG_PRICE_FIELD));
                    mItem.setPrice("" + jsonItem.getInt(QueryResultItem.PRICE_FIELD));
                    mItem.setImageUrl(jsonItem.getString(ProductInfo.IMAGE_URL_FIELD));

                }
                //mItem.setProductUrl(jsonItem.getString(QueryResultItem.PRODUCT_URL_FIELD));

            }
            mList.add(mItem);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mList;


    }
    public ArrayList<QueryResultItem> parseJSONforList(String raw){
        ArrayList<QueryResultItem> mList = new ArrayList<>();
        try {
            JSONObject response = new JSONObject(raw);
            JSONArray results = response.getJSONArray("results");
            for(int i=0;i<results.length();i++){
                QueryResultItem mItem = new QueryResultItem();
                JSONObject jsonItem = results.getJSONObject(i);
                mItem.setBrand(jsonItem.getString(QueryResultItem.BRAND_FIELD));
                mItem.setPrice(jsonItem.getString(QueryResultItem.PRICE_FIELD));
                mItem.setImageUrl(jsonItem.getString(QueryResultItem.IMAGE_URL_FIELD));
                mItem.setRating(jsonItem.getDouble(QueryResultItem.RATING_FIELD));
                mItem.setProductName(jsonItem.getString(QueryResultItem.PRODUCT_NAME_FIELD));
                mItem.setAsin(jsonItem.getString(QueryResultItem.ASIN_FIELD));
                //mItem.setProductUrl(jsonItem.getString(QueryResultItem.PRODUCT_URL_FIELD));
                mList.add(mItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mList;
    }
}
