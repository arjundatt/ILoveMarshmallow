package com.internship.zappos.ilovemarshmallow.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by arjundatt.16 on 9/8/2015.
 */
public class ProductInfo implements Parcelable{
    public static final String DESCRIPTION_FIELD="description";
    public static final String GENDER_FIELD="genders";
    public static final String IMAGE_URL_FIELD="imageUrl";
    public static final String LISTINGS="childAsins";
    public static final String SIZING_MAIN_FIELD="sizing";
    public static final String SIZE_WEIGHT_FIELD="weight";
    public static final String LAUNGUAGE__MAIN_FIELD="languageTagged";
    public static final String SIZE_FIELD="displaySize";
    public static final String COLOR_FIELD="color";
    public static final String ORIG_PRICE_FIELD = "originalPrice";


    private String description;
    private String gender;
    private String imageUrl;
    private float weight;
    private String size;
    private String color;
    private String origPrice;
    private String brandName;
    private String productName;
    private String price;

    public ProductInfo(){}

    protected ProductInfo(Parcel in) {
        description = in.readString();
        gender = in.readString();
        imageUrl = in.readString();
        weight = in.readFloat();
        size = in.readString();
        color = in.readString();
        origPrice = in.readString();
        brandName = in.readString();
        productName = in.readString();
        price = in.readString();
    }

    public static final Creator<ProductInfo> CREATOR = new Creator<ProductInfo>() {
        @Override
        public ProductInfo createFromParcel(Parcel in) {
            return new ProductInfo(in);
        }

        @Override
        public ProductInfo[] newArray(int size) {
            return new ProductInfo[size];
        }
    };

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getProductName() {
        return productName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setOrigPrice(String origPrice) {
        this.origPrice = origPrice;
    }

    public String getDescription() {
        return description;
    }

    public String getGender() {
        return gender;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public float getWeight() {
        return weight;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public String getOrigPrice() {
        return origPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeString(gender);
        dest.writeString(imageUrl);
        dest.writeFloat(weight);
        dest.writeString(size);
        dest.writeString(color);
        dest.writeString(origPrice);
        dest.writeString(brandName);
        dest.writeString(productName);
        dest.writeString(price);
    }
}
