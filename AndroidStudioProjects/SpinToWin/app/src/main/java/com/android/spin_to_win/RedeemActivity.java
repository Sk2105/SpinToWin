package com.android.spin_to_win;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class RedeemActivity extends AppCompatActivity {
    TextView coins;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem);
        new ToastClass(this);
        DataBase dataBase = new DataBase(this);
        coins = findViewById(R.id.coins);
        coins.setText(String.valueOf(dataBase.fecthData()));
        i = Integer.parseInt(coins.getText().toString());
    }

    public void a1(View view) {
        int c = 10000;
        startTryIntent(c);


    }

    public void a2(View view) {
        int c = 20000;
        startTryIntent(c);


    }

    public void a5(View view) {
        int c = 50000;
        startTryIntent(c);


    }

    public void a10(View view) {
        int c = 100000;

        startTryIntent(c);


    }

    public void a50(View view) {
        int c = 500000;
        startTryIntent(c);
    }

    public void startTryIntent(int c) {
        if (i > c) {
            startIntent(DetailsActivity.class, c);
        } else {
            ToastClass.toast("Please Collect " + (c - i) + " Coins");
        }
    }

    public void startIntent(Class activity, int i) {
        Intent intent = new Intent(this, activity);
        intent.putExtra("Coins", i);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
      finish();
    }

}