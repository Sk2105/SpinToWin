package com.android.spin_to_win;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;


public class MainActivity extends AppCompatActivity {
    TextView coins;
    int i;
    SwipeRefreshLayout swipeRefreshLayout;
    DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coins = findViewById(R.id.coins);
        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setColorScheme(R.color.purple);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            setData();
            swipeRefreshLayout.setRefreshing(false);
        });
        new ToastClass(this);
        dataBase = new DataBase(this);
        MobileAds.initialize(this);
        setData();
    }

    public void setData() {
        if (dataBase.fecthData() == 0) {
            dataBase.insertDataBase(499);
        }
        i = dataBase.fecthData();
        coins.setText(String.valueOf(i));
    }

    public void SpinClick(View view) {
        startTryIntent(() -> startIntent(WheelActivity.class));
    }

    public void redeemClick(View view) {
        startTryIntent(() -> startIntent(RedeemActivity.class));
    }

    public void policyClick(View view) {
        startTryIntent(() -> startIntent(PolicyActivity.class));
    }

    public void shareClick(View view) {
        startTryIntent(() -> ToastClass.toast("Share App"));
    }

    public void exitClick(View view) {
        showAlert();
    }

    @Override
    public void onBackPressed() {
        showAlert();
    }

    public void showAlert() {
        new AlertDialog.Builder(this).setTitle("Alert").setMessage("Are You " +
                "sure You want To exit ?").setNegativeButton("No",
                (dialog, which) -> dialog.dismiss()).setPositiveButton("Yes", (dialog, which) -> finish()).show();
    }

    public void startIntent(Class activity) {
        startActivity(new Intent(this, activity));
    }

    public void startTryIntent(InterfaceClass interfaceClass) {
        interfaceClass.start();
    }

}