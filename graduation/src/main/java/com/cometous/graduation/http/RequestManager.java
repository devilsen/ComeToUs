package com.cometous.graduation.http;



import com.cometous.graduation.http.volley.RequestQueue;
import com.cometous.graduation.http.volley.toolbox.Volley;

import android.content.Context;

/**
 * 
* @ClassName: RequestManager 
* @Description: 请求maneger 用于初始化requestqueen
*
 */
public class RequestManager {
	
	/**
	 * the queue :-)
	 */
	private static RequestQueue mRequestQueue;

	/**
	 * Nothing to see here.
	 */
	private RequestManager() {
	 // no instances
	} 

	/**
	 * @param context
	 * 			application context
	 */
	public static void init(Context context) {
		mRequestQueue = Volley.newRequestQueue(context);
	}


	public static RequestQueue getRequestQueue() {
	    if (mRequestQueue != null) {
	        return mRequestQueue;
	    } else {
	        throw new IllegalStateException("Not initialized");
	    }
	}
}
