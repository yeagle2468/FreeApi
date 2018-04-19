package com.yeagle.freeapi.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.yeagle.freeapi.App;

/**
 * Created by yeagle on 2018/2/11.
 * 推荐使用 editor.apply() 性能更好，除非立马要得到值，
 * apply是先写到内存里面的
 */
public class Configure {
    public static final String CONFIG_LAST_TOKEN = "CONFIG_LAST_TOKEN";
    public static final String CONFIG_LAST_UID = "CONFIG_LAST_UID";
    public static final String CONFIG_LAST_REGISTER_STEP = "CONFIG_LAST_REG_STEP";

    private SharedPreferences _spf;
    private static Configure g_config = new Configure();

    private Configure() {
        Context app = App.getContext();
        _spf = app.getSharedPreferences(app.getPackageName(), Context.MODE_PRIVATE);
    }

    public static Configure ins() {
        return g_config;
    }

//    public void saveLoginAccount(LoginBean loginBean) {
//        SharedPreferences.Editor editor = _spf.edit();
//
//        editor.putString(CONFIG_LAST_TOKEN, EncryptUtil.eh(loginBean.token));
//        editor.putInt(CONFIG_LAST_UID, loginBean.user_id);
//        editor.putInt(CONFIG_LAST_REGISTER_STEP, loginBean.reg_step);
//        editor.commit();
//    }

    public String getLastToken() {
        return _spf.getString(CONFIG_LAST_TOKEN, "");
    }

    public int getLastUid() {
        return _spf.getInt(CONFIG_LAST_UID, 0);
    }
}
