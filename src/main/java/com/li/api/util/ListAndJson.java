package com.li.api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.li.api.exception.ServerException;
import com.li.api.pojo.model.Specs;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 黎源
 * @date 2020/12/5 19:52
 */
@Converter
public class ListAndJson implements AttributeConverter<List<Specs>, String> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(List<Specs> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerException(9999);
        }
    }

    @Override
    public List<Specs> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, ArrayList.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerException(9999);
        }

    }
}
