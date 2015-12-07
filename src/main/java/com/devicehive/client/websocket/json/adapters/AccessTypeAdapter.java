package com.devicehive.client.websocket.json.adapters;


import com.devicehive.client.websocket.model.AccessType;
import com.devicehive.client.websocket.util.Messages;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Converter from JSON into AccessType, and AccessType into JSON
 */
public class AccessTypeAdapter extends TypeAdapter<AccessType> {

    @Override
    public void write(JsonWriter out, AccessType value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.getValue());
        }
    }

    @Override
    public AccessType read(JsonReader in) throws IOException {
        JsonToken jsonToken = in.peek();
        if (jsonToken == JsonToken.NULL) {
            in.nextNull();
            return null;
        } else {
            try {
                return AccessType.forName(in.nextString());
            } catch (RuntimeException e) {
                throw new IOException(Messages.INCORRECT_ACCESS_TYPE, e);
            }
        }
    }
}
