package com.android.spin_to_win;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;

import java.util.Locale;
import java.util.Random;

public class WheelActivity extends AppCompatActivity {
    ImageView wheel_icon;
    Button spin;
    DataBase dataBase;
    TextView coins;
    Boolean spinning;
    int res;
    RewardedInterstitialAd ad;
    RewardAdClass rewardAdClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel);
        spinning = false;
        rewardAdClass = new RewardAdClass(this, ad, getString(R.string.adId),
                new AdRequest.Builder().build());


        new ToastClass(getApplicationContext());
        dataBase = new DataBase(this);
        coins = findViewById(R.id.coins);
        coins.setText(String.valueOf(dataBase.fecthData()));
        wheel_icon = findViewById(R.id.Wheel_icon);
        spin = findViewById(R.id.spin);
        spin.setOnClickListener(v -> {
            startAnimation();
        });
    }


    public void startTime() {

        new CountDownTimer(11000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int min = (int) (millisUntilFinished / 1000) / 60;
                int sec = (int) (millisUntilFinished / 1000) % 60;
                spinning = true;
                String t = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
                spin.setText(t);
                spin.setEnabled(false);
            }

            @Override
            public void onFinish() {
                spin.setText(getText(R.string.spin));
                spinning = false;
                spin.setEnabled(true);
            }
        }.start();


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void startAnimation() {
        Random r = new Random();
        int d = r.nextInt(360);
        RotateAnimation rotate = new RotateAnimation(0, d + (360 * 5),
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(4000);
        rotate.setFillAfter(true);
        rotate.setStartTime(3000);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                endCall(d);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        wheel_icon.startAnimation(rotate);

    }


    private void endCall(int d) {
        if (d >= 0 && d < 40) {
            res = 10;
        } else if (d >= 40 && d < 80) {
            res = 0;
        } else if (d >= 80 && d < 120) {
            res = 15;

        } else if (d >= 120 && d < 160) {
            res = 25;
        } else if (d >= 160 && d < 200) {
            res = 35;
        } else if (d >= 200 && d < 240) {
            res = 40;

        } else if (d >= 240 && d < 280) {
            res = 30;

        } else if (d >= 280 && d < 320) {
            res = 20;

        } else if (d >= 320 && d < 360) {
            res = 5;

        }
        new Handler().postDelayed(() -> rewardAdClass.interAds(() -> {
        }), 5000);
        startTime();
        dataBase.updateDataBase(res);
        coins.setText(String.valueOf(dataBase.fecthData()));
        ToastClass.toast("You Earn +" + res + " Coins");

    }


}