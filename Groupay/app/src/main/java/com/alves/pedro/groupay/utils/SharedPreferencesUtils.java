package com.alves.pedro.groupay.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.alves.pedro.groupay.model.CreditCard;
import com.alves.pedro.groupay.model.User;

public class SharedPreferencesUtils {

    private static final String APP_KEY = "GroupayKey";
    private static final String USER_KEY = "GroupayKeyUser";
    private static final String CREDIT_CARD_KEY = "GroupayKeyCreditCard";

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(APP_KEY, Context.MODE_PRIVATE);
    }

    public static void saveUserPreference(SharedPreferences sharedPreferences, User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_KEY, GsonUtils.getInstance().toJson(user));
        editor.apply();
    }

    public static void saveCreditCardPreference(SharedPreferences sharedPreferences, CreditCard creditCard) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CREDIT_CARD_KEY, GsonUtils.getInstance().toJson(creditCard));
        editor.apply();
    }

    public static User getUserPreference(SharedPreferences sharedPreferences) {
        String userJson = sharedPreferences.getString(USER_KEY, "");
        if (userJson.length() <= 0)
            return null;
        return GsonUtils.getInstance().fromJson(userJson, User.class);
    }

    public static CreditCard getCreditCardPreference(SharedPreferences sharedPreferences) {
        String creditCardJson = sharedPreferences.getString(CREDIT_CARD_KEY, "");
        if (creditCardJson.length() <= 0)
            return null;
        return GsonUtils.getInstance().fromJson(creditCardJson, CreditCard.class);
    }
}
