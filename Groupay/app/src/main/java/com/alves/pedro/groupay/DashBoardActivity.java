package com.alves.pedro.groupay;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.alves.pedro.groupay.model.CreditCard;
import com.alves.pedro.groupay.model.User;
import com.alves.pedro.groupay.utils.SharedPreferencesUtils;

public class DashBoardActivity extends AppCompatActivity {

    public static final String USER_PARAM = "USER_PARAM";
    public static final String CREDIT_CARD_PARAM = "CREDIT_CARD_PARAM";

    private User mUser;
    private CreditCard mCreditCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        if (getIntent().hasExtra(USER_PARAM)) {
            mUser = (User) getIntent().getSerializableExtra(USER_PARAM);
            if (getIntent().hasExtra(CREDIT_CARD_PARAM))
                mCreditCard = (CreditCard) getIntent().getSerializableExtra(CREDIT_CARD_PARAM);
        } else {
            SharedPreferences sharedPreferences = SharedPreferencesUtils.getSharedPreferences(this);
            mUser = SharedPreferencesUtils.getUserPreference(sharedPreferences);
            mCreditCard = SharedPreferencesUtils.getCreditCardPreference(sharedPreferences);
        }

        if (mUser == null || mCreditCard == null)
            return;

        TextView tvUserName = findViewById(R.id.tvUserName);
        tvUserName.setText(mUser.getName());
        Log.e("T", mUser.getCreditCard()+"");

    }
}
