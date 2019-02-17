package com.alves.pedro.groupay.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class Invoice implements Serializable {

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("paid")
    private boolean mPaid;

    @SerializedName("dueDate")
    private long mDueDate;

    @SerializedName("date")
    private long mDate;

    @SerializedName("value")
    private double mValue;

    @SerializedName("user")
    private User mUser;

    @SerializedName("group")
    private Group mGroup;

    public Invoice() {
    }

    public Invoice(String id, String name, boolean paid, long dueDate, long date, double value, User user, Group group) {
        mId = id;
        mName = name;
        mPaid = paid;
        mDueDate = dueDate;
        mDate = date;
        mValue = value;
        mUser = user;
        mGroup = group;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public boolean isPaid() {
        return mPaid;
    }

    public void setPaid(boolean paid) {
        mPaid = paid;
    }

    public long getDueDate() {
        return mDueDate;
    }

    public void setDueDate(long dueDate) {
        mDueDate = dueDate;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }

    public double getValue() {
        return mValue;
    }

    public void setValue(double value) {
        mValue = value;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public Group getGroup() {
        return mGroup;
    }

    public void setGroup(Group group) {
        mGroup = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(mId, invoice.mId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId);
    }
}