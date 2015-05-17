package com.cometous.graduation.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.cometous.graduation.MyApplication;

import java.util.HashMap;

/**
 * Created by Devilsen on 2015/5/14.
 */
public class PreferenceUtil {

    public static final String PREFERENCE_CONFIG = "com.cometous.preference";

    public static final String INITIATE_CONFIG = "com.cometous.initiate";


    public static SharedPreferences getSharedPreferences(){
        return MyApplication.getInstance().getSharedPreferences(PREFERENCE_CONFIG, Context.MODE_PRIVATE);
    }

    public static void savePreference(String id,String text){
        SharedPreferences sp = getSharedPreferences();
        SharedPreferences.Editor editor = sp.edit();

        editor.putString( id, text);
        editor.apply();
    }


    /**
     * 发起活动
     * @param id
     * @param text
     */
    public static void saveInitiatePreference(String id,String text){
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(INITIATE_CONFIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString( id, text);
        editor.apply();
    }

    public static String getInitiatePreferencesByKey(String key){
        return MyApplication.getInstance().getSharedPreferences(INITIATE_CONFIG, Context.MODE_PRIVATE).getString(key,null);
    }


}
