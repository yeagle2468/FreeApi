package com.yeagle.freeapi.network;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import cn.yeagle.common.utils.EncryptUtil;

/**
 * Created by xc_office on 2017/10/28.
 * get参数解析
 */

public class GetRequestUtil {
    /**
     * 解析出url请求的路径，包括页面
     *
     * @param strURL url地址
     * @return url路径
     */
    public static String UrlPage(String strURL) {
        String strPage = null;
        String[] arrSplit = null;

        strURL = strURL.trim().toLowerCase();

        arrSplit = strURL.split("[?]");
        if (strURL.length() > 0) {
            if (arrSplit.length > 1) {
                if (arrSplit[0] != null) {
                    strPage = arrSplit[0];
                }
            }
        }

        return strPage;
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String TruncateUrlPage(String strURL) {
        String strAllParam = null;
        String[] arrSplit = null;

        strURL = strURL.trim();

        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }

        return strAllParam;
    }

    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param URL url地址
     * @return url请求参数部分
     */
    public static Map<String, String> URLRequest(String URL) {
        Map<String, String> mapRequest = new HashMap<String, String>();

        String[] arrSplit = null;

        String strUrlParam = TruncateUrlPage(URL);
        if (strUrlParam == null) {
            return mapRequest;
        }
        //每个键值为一组
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;

            arrSplitEqual = strSplit.split("[=]");

            if (arrSplitEqual.length > 1) {       //解析键值
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else {
                if (arrSplitEqual[0] != "") {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }


    public static String getParamsE(String url) {
        Map<String, String> stringStringMap = GetRequestUtil.URLRequest(url);
        return getA(stringStringMap);
    }

    /**
     * 获取请求参数的加密
     *
     * @param m
     * @return
     */
    public static String getParamsE(Map<String, String> m) {
        return getA(m);
    }

    public static String getA(Map<String, String> m) {
        Set<String> set = m.keySet();
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.addAll(set);
        StringBuffer sb = new StringBuffer("");
        for (String s : treeSet) {
            String s1 = m.get(s);
            sb.append(s1 + ".");
        }
        String key = EncryptUtil.getE2();
        sb.append(key);
        String s1 = "";
        try {
            s1 = EncryptUtil.sha1(sb.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return s1;
    }

}
