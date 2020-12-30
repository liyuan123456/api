package com.li.api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.li.api.exception.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 黎源
 * @date 2020/12/6 15:14
 */

//@Component
public class GenericAndJson {


    private static ObjectMapper mapper;

    static {
        GenericAndJson.mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }
//    private static ObjectMapper mapper;
//
//    @Autowired
//    public void setMapper(ObjectMapper mapper) {
//        GenericAndJson.mapper = mapper;
//    }

    public static <T> String ObjectToJson(T t) {
        try {
            return mapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerException(9999);
        }
    }

    public static <T> T JsonToObject(String jsonstr, TypeReference<T> tTypeReference) {
        try {
            if(jsonstr == null){
                return null;
            }
            T t = mapper.readValue(jsonstr, tTypeReference);
            return t;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerException(9999);
        }
    }

}
