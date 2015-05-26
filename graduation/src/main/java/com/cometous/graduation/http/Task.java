package com.cometous.graduation.http;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.alibaba.fastjson.util.IdentityHashMap;
import com.cometous.graduation.activity.BaseActivity;
import com.cometous.graduation.activity.DetailActivity;
import com.cometous.graduation.http.volley.Request.Method;
import com.cometous.graduation.http.volley.Response.ErrorListener;
import com.cometous.graduation.http.volley.Response.Listener;
import com.cometous.graduation.util.Log4Utils;

public class Task {

    public static final String HOST = "http://45.56.82.203:3001/";

    /** 请求地址前缀 */


    /**
     * 主页获取更多地址
     */
    public static final String HOST_MORE = HOST + "more?skip=";
    /**
     * 详情页地址
     */
    public static final String HOST_DETAIL_URL = HOST + "action/pull/";
    /**
     * 参加活动地址
     */
    public static final String HOST_JOIN = HOST + "action/fork/";
    /**
     * 退出活动地址
     */
    public static final String HOST_QUIT = HOST + "action/exit/";
    /**
     * 新建活动
     */
    public static final String HOST_NEW_ACTIVITY = HOST + "action/new/";
    /**
     * 注册新用户
     */
    public static final String HOST_REGISTER = HOST + "users/signup";
    /**
     * 获取我参加的活动列表
     */
    public static final String HOST_LIST_MY_JOIN = HOST + "action/listAllMyjoin";
    /**
     * 获取我发起的活动列表
     */
    public static final String HOST_LIST_MY_INIT = HOST + "action/listAllofMy";

    /**
     * 获取个人信息
     */
    public static final String HOST_USER_INFO = HOST + "users/profile/";

    /**
     * 搜索
     */
    public static final String HOST_SEARCH = HOST + "search/";

    /**
     * 赞
     */
    public static final String HOST_ACTIVITY_ZAN = HOST + "action/starUp/";

    /**
     * 通知推送
     */
    public static final String HOST_NOTICE = HOST + "notification";

    /**
     * 通知列表
     */
    public static final String HOST_NOTICE_LIST = HOST + "notification/history";

    /**
     * 通知列表
     */
    public static final String HOST_SETTING_UPDATE = HOST + "users/update/";





    public static final String ACTION_GET_OPP_BASE = "0";


    /**
     * 存放所有请求地址
     */
    public static Map<String, String> API_URLS = new HashMap<String, String>();


    public static void init() {
        API_URLS.put(ACTION_GET_OPP_BASE, "getOpportunityBasic");


    }


    /**
     * 打印request
     *
     * @param url
     * @param params
     */
    private static void printRequestUri(String url, HashMap<String, String> params) {
        StringBuilder builder = new StringBuilder(url + "?");
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            String key = next.getKey();
            String value = next.getValue();
            builder.append(key).append("=").append(value).append("&");
        }
        Log4Utils.i("Reuqest_Url:  ", builder.substring(0, builder.length() - 1));
    }

    /**
     * 获取主页信息
     */
    public static void getActivityList(HashMap<String, String> params, Listener<String> listener,
                                       ErrorListener errorListener) {
        StringJsonRequest request = new StringJsonRequest(Method.GET, HOST, params, listener, errorListener);
        RequestManager.getRequestQueue().add(request);
    }

    /**
     * 获取主页更多信息（上拉加载）
     */
    public static void getMoreActivityList(int count, Listener<String> listener, ErrorListener errorListener) {
        StringJsonRequest request = new StringJsonRequest(HOST_MORE + count, listener, errorListener);
        RequestManager.getRequestQueue().add(request);
    }


    /**
     * 获取详情页
     */
    public static void getActivityDetail(String id, Listener<String> listener, ErrorListener errorListener) {
        //get请求
        StringJsonRequest request = new StringJsonRequest(HOST_DETAIL_URL + id, listener, errorListener);
        RequestManager.getRequestQueue().add(request);
    }

    /**
     * 参加活动
     *
     * @param id 活动ID
     */
    public static void joinActivity(String id, Listener<String> listener, ErrorListener errorListener) {
        StringJsonRequest request = new StringJsonRequest(HOST_JOIN + id, listener, errorListener);
        RequestManager.getRequestQueue().add(request);
    }

    /**
     * 退出活动
     */
    public static void quitActivity(String id, Listener<String> listener, ErrorListener errorListener) {
        StringJsonRequest request = new StringJsonRequest(HOST_QUIT + id, listener, errorListener);
        RequestManager.getRequestQueue().add(request);
    }

    /**
     * 新建活动
     *
     * @param param
     * @param listener
     */
    public static void initiateActivityNet(HashMap<String, String> param, Listener<String> listener, ErrorListener errorListener) {
        StringJsonRequest request = new StringJsonRequest(Method.POST, HOST_NEW_ACTIVITY, param, listener, errorListener);
        RequestManager.getRequestQueue().add(request);
    }


    /**
     * 注册新用户
     */
    public static void register(HashMap<String, String> param, Listener<String> listener, ErrorListener errorListener) {
        StringJsonRequest request = new StringJsonRequest(Method.POST, HOST_REGISTER, param, listener, errorListener);
        RequestManager.getRequestQueue().add(request);
    }

    /**
     * 获取我参加的活动列表
     */
    public static void listAllMyjoin( Listener<String> listener, ErrorListener errorListener) {
        //get请求
        StringJsonRequest request = new StringJsonRequest(HOST_LIST_MY_JOIN, listener, errorListener);
        RequestManager.getRequestQueue().add(request);
    }

    /**
     * 获取我发起的活动列表
     */
    public static void listAllMyInit( Listener<String> listener, ErrorListener errorListener) {
        //get请求
        StringJsonRequest request = new StringJsonRequest(HOST_LIST_MY_INIT, listener, errorListener);
        RequestManager.getRequestQueue().add(request);
    }

    /**
     * 获取用户信息
     */
    public static void getUserInfo( String id,Listener<String> listener, ErrorListener errorListener) {
        String url = HOST_USER_INFO;
        if (id != null){
            url = HOST_USER_INFO + id;
        }
        StringJsonRequest request = new StringJsonRequest(url, listener, errorListener);
        RequestManager.getRequestQueue().add(request);
    }
    /**
     * 搜索
     */
    public static void search( String keyWord, Listener<String> listener, ErrorListener errorListener) {
        StringJsonRequest request = new StringJsonRequest(HOST_SEARCH + "name?key=" + keyWord, listener, errorListener);
        RequestManager.getRequestQueue().add(request);
    }

    /**
     * 点赞
     */
    public static void zanListener( String id, Listener<String> listener, ErrorListener errorListener) {
        StringJsonRequest request = new StringJsonRequest(HOST_ACTIVITY_ZAN + id , listener, errorListener);
        RequestManager.getRequestQueue().add(request);
    }

    /**
     * 设置更新
     */

    public static void settingUpdate(HashMap<String, String> param, Listener<String> listener, ErrorListener errorListener) {
        StringJsonRequest request = new StringJsonRequest(Method.POST, HOST_SETTING_UPDATE, param, listener, errorListener);
        RequestManager.getRequestQueue().add(request);
    }

    /**
     * 获取消息---推送
     */
    public static void getNotice(  Listener<String> listener, ErrorListener errorListener) {
        StringJsonRequest request = new StringJsonRequest(HOST_NOTICE , listener, errorListener);
        RequestManager.getRequestQueue().add(request);
    }

    /**
     * 获取消息--------列表
     */
    public static void getNoticeList( Listener<String> listener, ErrorListener errorListener) {
        StringJsonRequest request = new StringJsonRequest(HOST_NOTICE_LIST, listener, errorListener);
        RequestManager.getRequestQueue().add(request);
    }

}
