package com.android.spin_to_win;


import android.content.Context;

import android.content.Intent;
import android.widget.Toast;

public class ToastClass {
    public static Context context;

    public ToastClass(Context context) {
        ToastClass.context = context;
    }

    public static void toast(String s) {
        Toast.makeText(context.getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

}
