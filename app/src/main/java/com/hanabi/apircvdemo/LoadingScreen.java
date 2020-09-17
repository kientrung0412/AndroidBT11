package com.hanabi.apircvdemo;

import android.app.Activity;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;


public class LoadingScreen {

    private Activity activity;
    private AlertDialog dialog;

    public LoadingScreen(Activity activity) {
        this.activity = activity;
    }

    public void startLoadingdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.loading_screen, null));

        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }

    public void dismissLoadingDialog() {
        dialog.dismiss();
    }

}
