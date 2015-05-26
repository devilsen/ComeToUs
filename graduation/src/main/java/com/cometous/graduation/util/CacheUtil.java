package com.cometous.graduation.util;

/**
 * Created by Devilsen on 2015/5/14.
 */
public class CacheUtil {


    public static void addMemory(String id,String text){
        PreferenceUtil.savePreference(id,text);
    }

    public static String getMemory(String id){
        String text = PreferenceUtil.getSharedPreferences().getString(id,null);
        return text;
    }

    public static void removeMemory(String id){
        PreferenceUtil.removePreference(id);
    }

}
