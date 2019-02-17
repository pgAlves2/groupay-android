package com.alves.pedro.groupay.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.alves.pedro.groupay.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static boolean paramNotValid(EditText editText) {
        return (editText.getText().toString().trim().length() <= 0);
    }

    public static void showErrorDialog(String value, Context context) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        mBuilder.setMessage(value)
                .setPositiveButton(R.string.msgOk, (dialog, id) -> dialog.dismiss())
                .create()
                .show();
    }

    public static void showProgressBar(ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
    }

    public static void hideProgressBar(ProgressBar progressBar) {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getReadableDate(String dateString){
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = fmt.parse(dateString);
            SimpleDateFormat fmtOut = new SimpleDateFormat("dd/MM/yyyy");
            return fmtOut.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
