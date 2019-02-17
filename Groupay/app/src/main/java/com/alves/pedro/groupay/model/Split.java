package com.alves.pedro.groupay.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class Split implements Serializable {

    @SerializedName("paid")
    private boolean mPaid;

    @SerializedName("value")
    private double mValue;

    @SerializedName("userId")
    private String mUserId;

    private transient User mUser;

    public Split(){}

    public Split(boolean paid, double value, String userId) {
        mPaid = paid;
        mValue = value;
        mUserId = userId;
    }

    public boolean isPaid() {
        return mPaid;
    }

    public void setPaid(boolean paid) {
        mPaid = paid;
    }

    public double getValue() {
        return mValue;
    }

    public void setValue(double value) {
        mValue = value;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Split split = (Split) o;
        return Objects.equals(mUserId, split.mUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mUserId);
    }
}
