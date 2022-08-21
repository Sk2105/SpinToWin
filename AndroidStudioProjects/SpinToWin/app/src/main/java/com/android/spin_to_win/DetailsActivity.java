package com.android.spin_to_win;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
    TextView coins, text;
    EditText name, upi;
    String n, u;
    int c, i;
    DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        coins = findViewById(R.id.coins);
        text = findViewById(R.id.text);
        new ToastClass(this);
        dataBase = new DataBase(this);
        i = dataBase.fecthData();
        coins.setText(String.valueOf(i));
        name = findViewById(R.id.name);
        upi = findViewById(R.id.upi);
        n = name.getText().toString();
        u = upi.getText().toString();
        c = getIntent().getIntExtra("Coins", 0);
        text.setText(String.valueOf(c / 100));
    }

    private void checkEmpty() {
        if (n.isEmpty()) {
            name.setError("Enter Name");
        } else if (u.isEmpty() || u.length() < 10) {
            upi.setError("Enter Vailed UPI No");
        }
    }


    public void startIntent() {
        finish();
    }

    @Override
    public void onBackPressed() {
        startIntent();
    }

    public void redeemClick(View view) {
        checkEmpty();
        dataBase.updateDataBase(-c);
        et();
        ToastClass.toast("Your Request is Process");
        new Handler().postDelayed(() -> ToastClass.toast("Please Wait 1 Day"), 2000);
        coins.setText(String.valueOf(dataBase.fecthData()));
    }

    public void et() {
        name.setText("");
        upi.setText("");
    }
}