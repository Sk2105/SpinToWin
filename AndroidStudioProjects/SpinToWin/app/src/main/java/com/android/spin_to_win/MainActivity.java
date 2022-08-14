package com.android.spin_to_win;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;


public class MainActivity extends AppCompatActivity {
    TextView coins;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coins = findViewById(R.id.coins);
        new ToastClass(this);
        DataBase dataBase = new DataBase(this);
        MobileAds.initialize(this);
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
                "Sure You Want To exit ?").setNegativeButton("No", (dialog, which) -> dialog.dismiss()).setPositiveButton("Yes", (dialog, which) -> finish()).show();
    }

    public void startIntent(Class activity) {
        startActivity(new Intent(this, activity));
        finish();
    }
    public void startTryIntent(InterfaceClass interfaceClass) {
        interfaceClass.start();
    }

}