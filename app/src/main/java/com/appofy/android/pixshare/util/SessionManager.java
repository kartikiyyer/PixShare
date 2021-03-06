package com.appofy.android.pixshare.util;

/**
 * Created by user on 18-04-2015.
 */
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.appofy.android.pixshare.LandingActivity;
import com.appofy.android.pixshare.LoginActivity;
import com.appofy.android.pixshare.MainActivity;
import com.facebook.login.LoginManager;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "PixSharePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User id (make variable public to access from outside)
    public static final String KEY_USER_ID = "userId";

    // Email address (make variable public to access from outside)
    //public static final String KEY_EMAIL = "email";

    // User password (make variable public to access from outside)
    public static final String KEY_PASSWORD = "password";

    // User login type (make variable public to access from outside)
    public static final String KEY_SOCIAL_MEDIA_FLAG = "socialMediaFlag";

    // User Social login id (make variable public to access from outside)
    public static final String KEY_SOCIAL_MEDIA_ID = "socialMediaId";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String userId,String password,String socialMediaFlag, String socialMediaId){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing userId in pref
        editor.putString(KEY_USER_ID, userId);

        // Storing email in pref
        //editor.putString(KEY_EMAIL, email);

        // Storing password in pref
        editor.putString(KEY_PASSWORD, password);

        // Storing login type in pref
        editor.putString(KEY_SOCIAL_MEDIA_FLAG, socialMediaFlag);

        // Storing social media is in pref
        editor.putString(KEY_SOCIAL_MEDIA_ID, socialMediaId);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }else{
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LandingActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

        }

    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_USER_ID, pref.getString(KEY_USER_ID, null));

        // user email id
        //user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // user password
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));

        // user login type
        user.put(KEY_SOCIAL_MEDIA_FLAG, pref.getString(KEY_SOCIAL_MEDIA_FLAG, null));

        // user social media id
        user.put(KEY_SOCIAL_MEDIA_ID, pref.getString(KEY_SOCIAL_MEDIA_ID, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){

        System.out.println("KEY_SOCIAL_MEDIA_FLAG value:::::::::: "+pref.getString(KEY_SOCIAL_MEDIA_FLAG,null));
        System.out.println("KEY_SOCIAL_MEDIA_ID value:::::::::: "+pref.getString(KEY_SOCIAL_MEDIA_ID,null));
        if(pref.getString(KEY_SOCIAL_MEDIA_FLAG,null).equals("T")){
            if(pref.getString(KEY_SOCIAL_MEDIA_ID,null).equals("1")){
                System.out.println("############in if statement############");
                LoginManager.getInstance().logOut(); //logout from facebook
            }else if(pref.getString(KEY_SOCIAL_MEDIA_ID,null).equals("2")){
                //logout from twitter
            }

        }

        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
