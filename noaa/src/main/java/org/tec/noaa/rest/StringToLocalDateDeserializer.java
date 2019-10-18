package org.tec.noaa.rest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;

/**
 * convert string date to localdate
 */
public class StringToLocalDateDeserializer extends StdDeserializer<LocalDate> {
    /**
     * ctor
     */
    public StringToLocalDateDeserializer() {
        super(LocalDate.class);
    }

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return LocalDate.parse(jsonParser.getText());
    }
}
