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

    public static final String NOTICE_CONFIG = "com.cometous.notice";

    public static final String NOTICE_LIST_CONFIG = "com.cometous.noticelist";

    public static final String SETTING_CONFIG = "com.cometous.setting";


    public static SharedPreferences getSharedPreferences(){
        return MyApplication.getInstance().getSharedPreferences(PREFERENCE_CONFIG, Context.MODE_PRIVATE);
    }

    public static void savePreference(String id,String text){
        SharedPreferences sp = getSharedPreferences();
        SharedPreferences.Editor editor = sp.edit();

        editor.putString( id, text);
        editor.apply();
    }

    public static void removePreference(String id){
        SharedPreferences sp = getSharedPreferences();
        SharedPreferences.Editor editor = sp.edit();

        editor.remove( id );
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

    /**
     * 是否查看了通知
     * @param text
     */
    public static void saveNoticeStatus(boolean text){
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(NOTICE_CONFIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putBoolean("noticestatus", text);
        editor.apply();
    }

    public static boolean getNoticeStatus( ){
        return MyApplication.getInstance().getSharedPreferences(NOTICE_CONFIG, Context.MODE_PRIVATE).getBoolean("noticestatus", false);
    }

    /**
     * 通知缓存
     * @param text
     */
    public static void saveNoticeList(String text){
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(NOTICE_LIST_CONFIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString("noticechace", text);
        editor.apply();
    }

    public static String getNoticeList( ){
        return MyApplication.getInstance().getSharedPreferences(NOTICE_LIST_CONFIG, Context.MODE_PRIVATE).getString("noticechace", null);
    }

    /**
     * 保存设置
     * @param text
     */
    public static void saveSettingPreference(String id,boolean text){
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(SETTING_CONFIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putBoolean(id, text);
        editor.apply();
    }

    public static boolean getSettingPreferences(String key){
        return MyApplication.getInstance().getSharedPreferences(SETTING_CONFIG, Context.MODE_PRIVATE).getBoolean(key, true);
    }


}
