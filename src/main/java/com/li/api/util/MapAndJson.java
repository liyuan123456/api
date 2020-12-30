package com.li.api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.li.api.exception.ServerException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import javax.persistence.Converter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 黎源
 * @date 2020/12/22 16:04
 */
@Converter
public class MapAndJson implements AttributeConverter<Map<String,Object>,String> {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new ServerException(9999);
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {
        Map map;
        try {
            map = mapper.readValue(dbData,Map.class);
        } catch (JsonProcessingException e) {
            throw new ServerException(9999);
        }
        return map;
    }
}
