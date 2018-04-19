package cn.yeagle.common.utils;

import android.text.TextUtils;
import android.util.Base64;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import cn.yeagle.common.utils.*;
import cn.yeagle.common.utils.E2;

import static cn.yeagle.common.utils.JsonUtils.getJSONObjectFromMap;

/**
 * Created by xc_office on 2017/10/25.
 */
public class EncryptUtil {

    private static final String s3 = "xoT$9kCXVS%OMFY$zU";
    private static final String e3 = "4SrFmFPWFcMY2KbT9uK89A4KEtnnS+LGWQd8J4hYW9SLa1JUv+Ztev7OH8aHnjDw";
    private static final String pe3 = "U5Ebx2rRjnIwXb7dBb+7hEJdpnaaXGI37GxdyjQL/UTBQXo7iMFqq2E/Q/Gag+KG";
    public static final String pk3 = "GN-@pg#yZe@K.EQ";

    public static final String XOR_KEY = "71jSD9t118RBVPZ7ONNkdxx9A3jQZ8FY";

    private static final int STRING_S = 0x001;
    private static final int STRING_E = 0x002;
    private static final int STRING_PE = 0x003;
    private static final int STRING_PK = 0x004;


    private static String e;
    private static String pe;

    /**
     * E
     *
     * @return
     */
    private static String getE() {
        if (TextUtils.isEmpty(e)) {
            e = getA(getS(STRING_S), getS(STRING_E), cn.yeagle.common.utils.E1.salt1);
        }
        return e;
    }

    private static String getXORKey() {
        return XOR_KEY;
    }

    /**
     * PE
     *
     * @return
     */
    public static String getE2() {
        if (TextUtils.isEmpty(pe)) {
            pe = getA(getS(STRING_PK), getS(STRING_PE), cn.yeagle.common.utils.E1.salt2);
        }
        return pe;
    }

    private static String getA(String s1, String s2, String salt) {
        String p = "";
        AesCbcWithIntegrity.SecretKeys keys = null;
        try {
            keys = AesCbcWithIntegrity.generateKeyFromPassword(s1, salt);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        AesCbcWithIntegrity.CipherTextIvMac cipherTextIvMac1 = new AesCbcWithIntegrity.CipherTextIvMac(s2);
        try {
            p = AesCbcWithIntegrity.decryptString(cipherTextIvMac1, keys);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return p;
    }

    private static String getS(int type) {
        switch (type) {
            case STRING_E:
                return cn.yeagle.common.utils.E1.e1 + cn.yeagle.common.utils.E2.e2 + e3;
            case STRING_S:
                return cn.yeagle.common.utils.E1.s1 + cn.yeagle.common.utils.E2.s2 + s3;
            case STRING_PE:
                return cn.yeagle.common.utils.E1.pe1 + cn.yeagle.common.utils.E2.pe2 + pe3;
            case STRING_PK:
                return cn.yeagle.common.utils.E1.pk1 + E2.pk2 + pk3;
        }
        return "";
    }

    public static String ehp(Map map) {
        JSONObject jsonObjectFromMap = getJSONObjectFromMap(map);
        String s = jsonObjectFromMap.toString();
        String s1 = EncryptUtil.eh(s);
        String s2 = s1.replaceAll(System.getProperty("line.separator"), "");
        return s2.trim();
    }

    public static String eh(String h) {
        String key = getXORKey();//getE(); // 这边XOR变了
        int l1 = h.length();
        int l2 = key.length();
        String r = "";
        for (int i = 0; i < l1; i = i + l2) {
            int j = i + l2;
            if (j >= h.length())
                j = h.length();
            r += xor(h.substring(i, j), key);
        }
        String s = Base64.encodeToString(r.getBytes(), Base64.DEFAULT);
        return s;
    }

    public static String xor(String s1, String s2) {
        byte b1[] = s1.getBytes();
        byte b2[] = s2.getBytes();
        byte lb[], sb[];
        lb = b1.length >= b2.length ? b1 : b2;
        sb = b1.length >= b2.length ? b2 : b1;
        byte rb[] = new byte[sb.length];
        int i = 0;
        for (; i < sb.length; i++) {
            rb[i] = (byte) (sb[i] ^ lb[i]);
        }
        return new String(rb);
    }

    public static String sha1(String s) throws NoSuchAlgorithmException {
        MessageDigest sha1 = MessageDigest.getInstance("SHA1");
        sha1.update(s.getBytes());
        StringBuffer buf = new StringBuffer();
        byte[] b = sha1.digest();
        for (int i = 0; i < b.length; i++) {
            int a = b[i];
            if (a < 0) a += 256;
            if (a < 16) buf.append("0");
            buf.append(Integer.toHexString(a));
        }
        return buf.toString();
    }


}
