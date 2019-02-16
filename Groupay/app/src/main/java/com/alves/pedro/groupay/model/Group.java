package com.alves.pedro.groupay.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Group implements Serializable {

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("userList")
    private List<User> mUserList;

    @SerializedName("user")
    private User mUserOwner;

    public Group() {
    }

    public Group(String id, String name, List<User> userList, User userOwner) {
        mId = id;
        mName = name;
        mUserList = userList;
        mUserOwner = userOwner;
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

    public List<User> getUserList() {
        return mUserList;
    }

    public void setUserList(List<User> userList) {
        mUserList = userList;
    }

    public User getUserOwner() {
        return mUserOwner;
    }

    public void setUserOwner(User userOwner) {
        mUserOwner = userOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(mId, group.mId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId);
    }
}
