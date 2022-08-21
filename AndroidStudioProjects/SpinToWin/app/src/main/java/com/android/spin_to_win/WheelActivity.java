package com.android.spin_to_win;

import androidx.appcompat.app.AppCompatActivity;
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
        coins = findViewById(R.id.coins);
        coins.setText(String.valueOf(dataBase.fecthData()));
        wheel_icon = findViewById(R.id.Wheel_icon);
        spin = findViewById(R.id.spin);
        spin.setOnClickListener(v -> {
            spinning = true;
            startAnimation();
        });
    }


    public void startTime() {

        new CountDownTimer(11000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int min = (int) (millisUntilFinished / 1000) / 60;
                int sec = (int) (millisUntilFinished / 1000) % 60;
                String t = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
                spin.setText(t);
            }

            @Override
            public void onFinish() {
                spin.setText(getText(R.string.spin));
                spinning = false;
            }
        }.start();


    }


    @Override
    public void onBackPressed() {
        if (spinning) {
            ToastClass.toast("Please wait");
        } else {
            finish();
        }
    }

    public void startAnimation() {
        Random r = new Random();
        int d = r.nextInt(360);
        RotateAnimation rotate = new RotateAnimation(0, d + (360 * 10),
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
                endCall((int) (d / 51.5));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        wheel_icon.startAnimation(rotate);
    }


    private void endCall(int d) {
        if (d == 0) {
            updateDatabase(100);
        } else if (d == 1) {
            updateDatabase(50);
        } else if (d == 2) {
            updateDatabase(30);
        } else if (d == 3) {
            updateDatabase(20);
        } else if (d == 4) {
            updateDatabase(10);
        } else if (d == 5) {
            updateDatabase(5);
        } else if (d == 6) {
            updateDatabase(0);
        }
    }

    public void updateDatabase(int r) {
        try {
            new Handler().postDelayed(() -> rewardAdClass.interAds(() -> {
            }), 5000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            startTime();
            dataBase.updateDataBase(r);
            coins.setText(String.valueOf(dataBase.fecthData()));
            ToastClass.toast("You Earn +" + r + " Coins");
        }
    }


}