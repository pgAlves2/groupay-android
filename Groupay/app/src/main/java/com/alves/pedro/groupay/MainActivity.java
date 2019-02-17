package com.alves.pedro.groupay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alves.pedro.groupay.model.CreditCard;
import com.alves.pedro.groupay.model.User;
import com.alves.pedro.groupay.utils.SharedPreferencesUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = SharedPreferencesUtils.getSharedPreferences(this);
        User user = SharedPreferencesUtils.getUserPreference(sharedPreferences);
        CreditCard creditCard = SharedPreferencesUtils.getCreditCardPreference(sharedPreferences);

        if (user != null && creditCard != null) {
            Intent dashBoardIntent = new Intent(this, DashBoardActivity.class);
            dashBoardIntent.putExtra(DashBoardActivity.USER_PARAM, user);
            dashBoardIntent.putExtra(DashBoardActivity.CREDIT_CARD_PARAM, creditCard);
            startActivity(dashBoardIntent);
        } else {
            Intent signUpIntent = new Intent(this, SignUpActivity.class);
            startActivity(signUpIntent);
        }

        finish();
    }
}
