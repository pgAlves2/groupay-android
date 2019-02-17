package com.alves.pedro.groupay.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class CreditCard implements Serializable {

    @SerializedName("holder_name")
    private String mCardName;

    @SerializedName("card_number")
    private String mCardNumber;

    @SerializedName("expiration_month")
    private String mMonth;

    @SerializedName("expiration_year")
    private String mYear;

    @SerializedName("security_code")
    private String mCVV;

    public CreditCard(){}

    public CreditCard(String cardName, String cardNumber, String month, String year, String CVV) {
        mCardName = cardName;
        mCardNumber = cardNumber;
        mMonth = month;
        mYear = year;
        mCVV = CVV;
    }

    public String getCardName() {
        return mCardName;
    }

    public void setCardName(String cardName) {
        mCardName = cardName;
    }

    public String getCardNumber() {
        return mCardNumber;
    }

    public void setCardNumber(String cardNumber) {
        mCardNumber = cardNumber;
    }

    public String getMonth() {
        return mMonth;
    }

    public void setMonth(String month) {
        mMonth = month;
    }

    public String getYear() {
        return mYear;
    }

    public void setYear(String year) {
        mYear = year;
    }

    public String getCVV() {
        return mCVV;
    }

    public void setCVV(String CVV) {
        mCVV = CVV;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return Objects.equals(mCardNumber, that.mCardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mCardNumber);
    }
}
