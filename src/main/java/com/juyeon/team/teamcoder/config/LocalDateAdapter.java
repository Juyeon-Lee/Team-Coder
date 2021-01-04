package com.juyeon.team.teamcoder.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;

public class LocalDateAdapter extends JsonSerializer<LocalDate> {

    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//        gen.writeStartObject();
//
//        gen.writeFieldName("date");
        gen.writeString(String.valueOf(value));

        //gen.writeEndObject();
    }
}
