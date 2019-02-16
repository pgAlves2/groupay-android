package com.alves.pedro.groupay.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    @SerializedName("cpf")
    private String mCPF;

    @SerializedName("passworld")
    private String mPassworld;

    @SerializedName("email")
    private String mEmail;

    @SerializedName("phone")
    private String mPhone;

    @SerializedName("name")
    private String mName;

    public User() {
    }

    public User(String CPF, String passworld, String email, String phone, String name) {
        mCPF = CPF;
        mPassworld = passworld;
        mEmail = email;
        mPhone = phone;
        mName = name;
    }

    public String getCPF() {
        return mCPF;
    }

    public void setCPF(String CPF) {
        mCPF = CPF;
    }

    public String getPassworld() {
        return mPassworld;
    }

    public void setPassworld(String passworld) {
        mPassworld = passworld;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(mCPF, user.mCPF);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mCPF);
    }
}
