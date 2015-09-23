package com.internship.zappos.ilovemarshmallow.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by arjundatt.16 on 9/4/2015.
 */
public class QueryResultItem implements Parcelable{
    public static final String BRAND_FIELD = "brandName";
    public static final String PRICE_FIELD = "price";
    public static final String IMAGE_URL_FIELD = "imageUrl";
    public static final String ASIN_FIELD = "asin";
    public static final String PRODUCT_URL_FIELD = "productUrl";
    public static final String RATING_FIELD = "productRating";
    public static final String MAP_FIELD = "map";
    public static final String PRODUCT_NAME_FIELD = "productName";

    private String brand;
    private String price;
    private String imageUrl = null;
    private String asin;
    private String productUrl =null;
    private float rating;
    private String productName;

    public QueryResultItem(){}

    public QueryResultItem(Parcel in) {
        brand = in.readString();
        price = in.readString();
        imageUrl = in.readString();
        asin = in.readString();
        productUrl = in.readString();
        rating = in.readFloat();
        productName = in.readString();
    }

    public static final Creator<QueryResultItem> CREATOR = new Creator<QueryResultItem>() {
        @Override
        public QueryResultItem createFromParcel(Parcel in) {
            return new QueryResultItem(in);
        }

        @Override
        public QueryResultItem[] newArray(int size) {
            return new QueryResultItem[size];
        }
    };

    public void setBrand(String brand) {
        this.brand = brand;
    }


    public void setPrice(String price) {
        this.price = price;
    }

    public void setImageUrl(String url) {
            this.imageUrl = url;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public void setProductUrl(String url) {
            this.productUrl = url;
    }

    public void setRating(double rating) {
        this.rating = (float)rating;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrand() {
        return brand;
    }

    public String getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAsin() {
        return asin;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public float getRating() {
        return rating;
    }

    public String getProductName() {
        return productName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(brand);
        dest.writeString(price);
        dest.writeString(imageUrl);
        dest.writeString(asin);
        dest.writeFloat(rating);
        dest.writeString(productName);

    }
}
