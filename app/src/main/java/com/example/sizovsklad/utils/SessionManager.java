package com.example.sizovsklad.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.sizovsklad.models.User;

public class SessionManager {
    private static final String PREF_NAME = "SizovSkladPref";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_ROLE = "user_role";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void saveUser(User user) {
        editor.putInt(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_EMAIL, user.getEmail());
        editor.putString(KEY_USER_NAME, user.getFullName());
        editor.putString(KEY_USER_ROLE, user.getRole());
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public User getUser() {
        if (!isLoggedIn()) return null;
        User user = new User();
        user.setId(pref.getInt(KEY_USER_ID, 0));
        user.setEmail(pref.getString(KEY_USER_EMAIL, null));
        user.setFullName(pref.getString(KEY_USER_NAME, null));
        user.setRole(pref.getString(KEY_USER_ROLE, null));
        return user;
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}