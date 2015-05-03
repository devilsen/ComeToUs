package com.cometous.graduation.http;


import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.cometous.graduation.exception.BusinessError;
import com.cometous.graduation.http.volley.AuthFailureError;
import com.cometous.graduation.http.volley.NetworkResponse;
import com.cometous.graduation.http.volley.ParseError;
import com.cometous.graduation.http.volley.Request;
import com.cometous.graduation.http.volley.Response;
import com.cometous.graduation.http.volley.toolbox.HttpHeaderParser;
import com.cometous.graduation.util.Log4Utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


/**
 * 
* @ClassName: StringJsonRequest 
* @Description: 返回json字符串的网络请求
*
 */
public class StringJsonRequest extends Request<String> {
    private final Response.Listener<String> mListener;
    /**
	 * Method POST request params map
	 */
	private final Map<String, String> mParams;

	
    public StringJsonRequest(int method, String url, Map<String,String> params, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url,errorListener);
        this.mParams = params;
        mListener = listener;
    }

    public StringJsonRequest(String url, Map<String, String> params, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(Method.POST, url, params, listener, errorListener);
    }

    /**
     * get请求
     * @param url
     * @param listener
     * @param errorListener
     */
    public StringJsonRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url , null, listener, errorListener);
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
    	return mParams;
    }

	@Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
        	String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        	if(!TextUtils.isEmpty(json)){
        		Log4Utils.d("Json_From_Server", json);
        	} else {
        		Log4Utils.d("Json_From_Server", "NULL");
        	}

			return Response.success(json, HttpHeaderParser.parseCacheHeaders(response));
        	
        } catch (UnsupportedEncodingException e) {
        	return Response.error(new ParseError(e));
        } catch(JSONException e){
        	return Response.error(new ParseError(e));
        }
    }

	
}

