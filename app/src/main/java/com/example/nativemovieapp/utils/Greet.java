package com.example.nativemovieapp.utils;

import android.app.Activity;
import android.view.Gravity;

import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;

public class Greet {
    public static void showWelcome(String displayName, String greet, Activity activity)
    {
        AestheticDialog show = new AestheticDialog.Builder(activity, DialogStyle.EMOTION, DialogType.SUCCESS)
                .setTitle("Welcome " + displayName)
                .setMessage(greet)
                .setGravity(Gravity.TOP)
                .setAnimation(DialogAnimation.IN_OUT)
                .show();
    }
}
