package com.findingdata.oabank.utils.http;

import com.findingdata.oabank.entity.BaseEntity;
import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by Loong on 2019/11/21.
 * Version: 1.0
 * Describe: JSON解析
 */
public class JsonParse {
    public static BaseEntity parse(String json){
        return new Gson().fromJson(json, BaseEntity.class);
    }

    public static <T> BaseEntity<T> parse( String json, Class<T> clazz){
        Type type = new ParameterizedTypeImpl(BaseEntity.class, new Class[]{clazz});
        return new Gson().fromJson(json, type);
    }

}
