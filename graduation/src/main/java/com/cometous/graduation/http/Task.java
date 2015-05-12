package com.cometous.graduation.http;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.alibaba.fastjson.util.IdentityHashMap;
import com.cometous.graduation.http.volley.Request.Method;
import com.cometous.graduation.http.volley.Response.ErrorListener;
import com.cometous.graduation.http.volley.Response.Listener;
import com.cometous.graduation.util.Log4Utils;

public class Task {
	
	public static final String HOST = "http://45.56.82.203:3001/";
//	public static final String HOST = "http://192.168.1.110:3001/";

	/** 请求地址前缀 */

	/** 详情页地址 */
	public static final String HOST_DETAIL_URL = HOST + "action/pull/";
	/** 参加活动地址 */
	public static final String HOST_JOIN = HOST + "action/fork/";
	/** 新建活动 */
	public static final String HOST_NEW_ACTIVITY = HOST + "action/new/";


	public static final String ACTION_GET_OPP_BASE = "0";


	/** 存放所有请求地址 */
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
	 *
	 */
	public static void getActivityList(HashMap<String, String> params, Listener<String> listener,
			ErrorListener errorListener) {
		StringJsonRequest request = new StringJsonRequest(Method.GET, HOST, params, listener, errorListener);
		RequestManager.getRequestQueue().add(request);
	}
	
	
	/**
	 *获取详情页
	 */
	public static void getActivityDetail(String id, Listener<String> listener, ErrorListener errorListener) {
		//get请求
		StringJsonRequest request = new StringJsonRequest( HOST_DETAIL_URL + id , listener, errorListener);
		RequestManager.getRequestQueue().add(request);
	}

	/**
	 * 参加活动
	 * @param id 活动ID
	 */
	public static void joinActivity(String id, Listener<String> listener, ErrorListener errorListener){
		StringJsonRequest request = new StringJsonRequest(HOST_JOIN + id , listener, errorListener);
		RequestManager.getRequestQueue().add(request);
	}

	/**
	 * 新建活动
	 * @param param
	 * @param listener
	 */
	public static void initiateActivityNet(HashMap<String,String> param,Listener<String> listener,ErrorListener errorListener){
		StringJsonRequest request = new StringJsonRequest(Method.POST,HOST_NEW_ACTIVITY,param,listener,errorListener);
		RequestManager.getRequestQueue().add(request);
	}

}
