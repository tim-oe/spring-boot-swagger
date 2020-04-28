package org.tec.noaa.rest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataDateTimeDeserializer extends StdDeserializer<LocalDateTime> {
    private static final long serialVersionUID = 1L;

    private static final DateTimeFormatter FIELD_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);

    /**
     * ctor
     */
    public DataDateTimeDeserializer(){
        super(LocalDateTime.class);
    }

    @Override
    public LocalDateTime deserialize(final JsonParser parser, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return LocalDateTime.parse(parser.getText(), FIELD_FORMATTER);
    }
}
