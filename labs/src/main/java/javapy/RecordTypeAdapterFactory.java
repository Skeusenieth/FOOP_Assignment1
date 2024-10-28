package javapy;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class RecordTypeAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<? super T> rawType = type.getRawType();
        if (!rawType.isRecord()) {
            return null;
        }

        Constructor<?> constructor = rawType.getDeclaredConstructors()[0];
        Parameter[] parameters = constructor.getParameters();
        Field[] fields = rawType.getDeclaredFields();

        return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter out, T value) throws IOException {
                out.beginObject();
                for (Field field : fields) {
                    field.setAccessible(true);
                    out.name(field.getName());
                    try {
                        gson.toJson(field.get(value), field.getType(), out);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
                out.endObject();
            }

            @Override
            public T read(JsonReader in) throws IOException {
                Map<String, Object> args = new HashMap<>();
                in.beginObject();
                while (in.hasNext()) {
                    String name = in.nextName();
                    for (Parameter parameter : parameters) {
                        if (parameter.getName().equals(name)) {
                            args.put(name, gson.fromJson(in, parameter.getType()));
                        }
                    }
                }
                in.endObject();
                try {
                    Object[] paramValues = new Object[parameters.length];
                    for (int i = 0; i < parameters.length; i++) {
                        paramValues[i] = args.get(parameters[i].getName());
                    }
                    return (T) constructor.newInstance(paramValues);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
