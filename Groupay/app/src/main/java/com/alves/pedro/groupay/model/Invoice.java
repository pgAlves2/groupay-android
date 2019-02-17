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
    private String mDueDate;

    @SerializedName("date")
    private String mDate;

    @SerializedName("value")
    private double mValue;

    @SerializedName("user")
    private User mUser;

    @SerializedName("group")
    private Group mGroup;

    @SerializedName("groupId")
    private String mGroupID;

    public Invoice() {
    }

    public Invoice(String id, String name, boolean paid, String dueDate, String date, double value, User user, Group group, String groupID) {
        mId = id;
        mName = name;
        mPaid = paid;
        mDueDate = dueDate;
        mDate = date;
        mValue = value;
        mUser = user;
        mGroup = group;
        mGroupID = groupID;
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

    public String getDueDate() {
        return mDueDate;
    }

    public void setDueDate(String dueDate) {
        mDueDate = dueDate;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
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

    public String getGroupID() {
        return mGroupID;
    }

    public void setGroupID(String groupID) {
        mGroupID = groupID;
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
