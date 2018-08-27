package com.bboroccu.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by bboroccu on 16. 1. 12..
 */
public class HttpUtil {
    /**
     * POST
     * @param url       주소
     * @param params    데이터
     * @param encoding  URL encoding
     * @return          결과 값
     * @throws JSONException
     */
    public static String post(String url, Map params, String encoding) throws JSONException{
        HttpClient client = new DefaultHttpClient();
        try {
            HttpPost post = new HttpPost(url);
            List<NameValuePair> paramList = convertParam(params);
            post.setEntity(new UrlEncodedFormEntity(paramList, encoding));
            ResponseHandler<String> rh = new BasicResponseHandler();
            return client.execute(post, rh);
        } catch (UnsupportedEncodingException uex) {
            uex.printStackTrace();
        } catch (IOException iex) {
            iex.printStackTrace();
        } finally {
            client.getConnectionManager().shutdown();
        }
        return Utility.getRtnMsg(0, "error");
    }
    public static String post(String url, Map params) throws JSONException {
        return post(url, params, "UTF-8");
    }

    /**
     * GET
     * @param url       주소
     * @param params    데이터
     * @param encoding  URL encoding
     * @return          결과값
     * @throws JSONException
     */
    public static String get(String url, Map params, String encoding) throws JSONException {
        HttpClient client = new DefaultHttpClient();
        try {
            List<NameValuePair> paramList = convertParam(params);
            HttpGet get = new HttpGet(url + "?" + URLEncodedUtils.format(paramList, encoding));
            ResponseHandler<String> rh = new BasicResponseHandler();
            return client.execute(get, rh);
        } catch (UnsupportedEncodingException uex) {
            uex.printStackTrace();
        } catch (IOException iex) {
            iex.printStackTrace();
        } finally {
            client.getConnectionManager().shutdown();
        }
        return Utility.getRtnMsg(0, "error");
    }
    public  static String get(String url, Map params) throws JSONException {
        return get(url, params);
    }
    private static List<NameValuePair> convertParam(Map params){
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        Iterator<String> keys = params.keySet().iterator();
        while(keys.hasNext()){
            String key = keys.next();
            try {
                paramList.add(new BasicNameValuePair(key, URLEncoder.encode(params.get(key).toString(), "UTF-8")));
            } catch (UnsupportedEncodingException uex) {
                uex.printStackTrace();
            }
        }
        return paramList;
    }
}
