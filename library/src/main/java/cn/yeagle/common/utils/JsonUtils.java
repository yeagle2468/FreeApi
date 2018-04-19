package cn.yeagle.common.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 *
 */
public class JsonUtils {
    public static JSONObject getJSONObjectFromMap(Map<String, String> map) {
        JSONObject eventObject = new JSONObject();
        try {
            for (String key : map.keySet()) {
                eventObject.put(key, map.get(key));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return eventObject;
    }
}
