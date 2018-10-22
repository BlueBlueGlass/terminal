package com.example.util.jsonutil;

import com.alibaba.fastjson.JSON;
import com.example.gson.communication_json.SignJson;

import org.json.JSONObject;

/**
 * Created by Administrator on 2018/9/8.
 */

public class JsonUtil {
    public static String getJsonString(Object object){
      return JSON.toJSONString(object);
    }
}
