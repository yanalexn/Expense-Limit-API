//package com.yanalexn.expense_limit_api.service;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.*;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.apache.commons.lang3.time.DateUtils;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import java.io.IOException;
//import java.time.OffsetDateTime;
//import java.time.format.DateTimeFormatter;
//
//@Configuration
//public class JacksonOffsetDateTimeMapper{
//
//    @Primary
//    @Bean
//    public ObjectMapper objectMapper() {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        SimpleModule simpleModule = new SimpleModule();
//        simpleModule.addSerializer(OffsetDateTime.class, new JsonSerializer<OffsetDateTime>() {
//            @Override
//            public void serialize(OffsetDateTime offsetDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
//                jsonGenerator.writeString(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(offsetDateTime));
//            }
//        });
//
//        simpleModule.addDeserializer(OffsetDateTime.class, new  JsonDeserializer<OffsetDateTime>() {
//            @Override
//            public OffsetDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
//                return OffsetDateTime.parse(parser.getText(), this.formatter);
//            }
//        });
//        objectMapper.registerModule(simpleModule);
//
//        return objectMapper;
//    }
//
//}
