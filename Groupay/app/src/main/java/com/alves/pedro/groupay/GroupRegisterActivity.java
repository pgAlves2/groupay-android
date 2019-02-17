package com.alves.pedro.groupay;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.alves.pedro.groupay.data.APIController;
import com.alves.pedro.groupay.model.User;
import com.alves.pedro.groupay.utils.Utils;

public class GroupRegisterActivity extends AppCompatActivity {

    private EditText mEtName;
    private ProgressBar mProgressBar;
    private Button mBtnRegister;

    private GroupRegisterHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_register);

        mEtName = findViewById(R.id.etName);
        mProgressBar = findViewById(R.id.pbLoading);

        mBtnRegister = findViewById(R.id.btnRegister);
        mBtnRegister.setOnClickListener((v) -> registerGroup());
    }

    private void registerGroup() {
        mBtnRegister.setEnabled(false);

        if (mHandler == null)
            mHandler = new GroupRegisterHandler();

        boolean valid = false;
        String error = "";
        if (Utils.paramNotValid(mEtName))
            error = getString(R.string.msgErrorName);
        else
            valid = true;

        if (valid) {
            Utils.showProgressBar(mProgressBar);

        } else {
            Utils.showErrorDialog(error, this);
            mBtnRegister.setEnabled(true);
        }
    }

    @SuppressLint("HandlerLeak")
    class GroupRegisterHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Utils.hideProgressBar(mProgressBar);
            mBtnRegister.setEnabled(true);
            switch (msg.what) {
                case APIController.REQUEST_RESULT_OK:
                    break;
                case APIController.REQUEST_RESULT_ERROR:
                    break;
                default:
                    break;
            }
        }
    }
}
