package com.alves.pedro.groupay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alves.pedro.groupay.model.User;

public class RegisterCreditCardActivity extends AppCompatActivity {

    public static final String USER_PARAM = "USER_PARAM";

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_credit_card);

        if (!getIntent().hasExtra(USER_PARAM))
            return;

        mUser = (User) getIntent().getSerializableExtra(USER_PARAM);
    }
}
