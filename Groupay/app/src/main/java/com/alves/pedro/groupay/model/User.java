package com.alves.pedro.groupay.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("email")
    private String mEmail;

    @SerializedName("cpf")
    private String mCPF;

    @SerializedName("phone")
    private String mPhone;

    @SerializedName("passworld")
    private String mPassworld;

    @SerializedName("balance")
    private String mBalance;

    @SerializedName("groups")
    private List<Group> mGroupList;

    @SerializedName("invoices")
    private List<Group> myInvoiceList;

    @SerializedName("groupInvoices")
    private List<Group> linkedInvoiceList;

    private transient CreditCard mCreditCard;

    public User() {
    }

    public User(String name, String email, String CPF, String phone, String passworld) {
        mName = name;
        mEmail = email;
        mCPF = CPF;
        mPhone = phone;
        mPassworld = passworld;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
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

    public String getBalance() {
        return mBalance;
    }

    public void setBalance(String balance) {
        mBalance = balance;
    }

    public CreditCard getCreditCard() {
        return mCreditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        mCreditCard = creditCard;
    }

    public List<Group> getGroupList() {
        return mGroupList;
    }

    public void setGroupList(List<Group> groupList) {
        mGroupList = groupList;
    }

    public List<Group> getMyInvoiceList() {
        return myInvoiceList;
    }

    public void setMyInvoiceList(List<Group> myInvoiceList) {
        this.myInvoiceList = myInvoiceList;
    }

    public List<Group> getLinkedInvoiceList() {
        return linkedInvoiceList;
    }

    public void setLinkedInvoiceList(List<Group> linkedInvoiceList) {
        this.linkedInvoiceList = linkedInvoiceList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(mId, user.mId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId);
    }
}
