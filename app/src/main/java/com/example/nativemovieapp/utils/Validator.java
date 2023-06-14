package com.example.nativemovieapp.utils;

import android.util.Patterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Validator {

    public static boolean isValidEmail(String email)
    {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public static boolean isValidPassword(String password) {
        if (password.length() < 8 || password.length() > 20) {
            return false;
        }
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (!(c >= 'a' && c <= 'z')
                    && !(c >= '0' && c <= '9')
                    && !(c >= 'A' && c <= 'Z')) {
                return true;
            }
        }
        return false;
    }
}
