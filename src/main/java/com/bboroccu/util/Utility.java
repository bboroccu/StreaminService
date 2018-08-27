package com.bboroccu.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by bboroccu on 16. 1. 11..
 */
public class Utility {
    public static String getRtnMsg(int code, String msg) throws JSONException
    {
        JSONObject obj = new JSONObject();
        obj.put("code", code);
        obj.put("msg", msg);
        return obj.toString();
    }
    public static String getRtnStrAndMsg(String result, String msg) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("result", result);
        object.put("message", msg);
        return object.toString();
    }
    /**
     * Json 객체 유효성 검사
     * @param object -> Json객체
     * @param key
     * @return
     */
    public static Object JsonDataValid(JSONObject object, String key)
    {
        try {
            Object obj = object.get(key);
            return obj;
        } catch (NullPointerException nex) {
            return null;
        } catch (JSONException jex) {
            return null;
        }
    }

    /**
     * 스트링 -> 날짜 변환
     * @param str
     * @return
     */
    public static Date getStrToDate(String str)
    {
        if (str == null || str.equals(""))
            return new Date(System.currentTimeMillis());
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd H:mm");
            return formatter.parse(str);
        } catch (Exception ex) {
            return new Date(System.currentTimeMillis());
        }
    }

    /**
     * 날짜 -> 스트링 변환
     * @param date
     * @return
     */
    public static String getDateToStr(Date date)
    {
        if (date == null)
            return "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:mm");
            return format.format(date);
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * 맵 -> 스트링 변환
     * @param params
     * @return
     */
    public static String getMapToStr(Map<String, String> params) {
        String result = "";
        Iterator<String> keys = params.keySet().iterator();
        while(keys.hasNext()) {
            String key = keys.next();
            if (result.equals(""))
                result += ",";
            result += params.get(key);
        }
        return result;
    }

    /**
     * 유니크한 스트링 생성
     * @param str
     * @return
     */
    public static String getUniqueStr(String str)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String uniqueStr = formatter.format(new Date()) + "_" + str;
        return uniqueStr;
    }

    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        long length = file.length();
        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
        // Close the input stream and return bytes
        is.close();
        return bytes;
    }
}
