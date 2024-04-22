package practice8.config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import practice8.model.ArrayElement;
import practice8.model.Category;
import practice8.model.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArrayDeserializer extends StdDeserializer<List<ArrayElement>> {

    protected ArrayDeserializer() {
        super((Class<?>) null);
    }
    /*
    // 해당 코드는 StackOverFlow가 발생하는데, jsonParser.readValueAs(List.class) 메서드를 사용하는 부분에서 무한 재귀가 발생해서 그렇다.
    // 이 메서드가 내부적으로 다시 ArrayDeserializer를 호출하고 있는 문제가 발생하는 것이다.
    @Override
    public List<ArrayElement> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        List<List<Object>> rawArray = jsonParser.readValueAs(List.class);
        List<ArrayElement> elements = new ArrayList<>();
        for (List<Object> rawElement: rawArray) {
            int index1 = (Integer) rawElement.get(0);
            int index2 = (Integer) rawElement.get(1);
            String category = (String) rawElement.get(2);
            elements.add(new ArrayElement(index1, index2, Category.fromJson(category)));
        }
        return elements;
    }*/

    /*
    JsonParser
        값 토큰:
            숫자 (JsonToken.VALUE_NUMBER_INT 또는 JsonToken.VALUE_NUMBER_FLOAT)
            문자열 (JsonToken.VALUE_STRING)
            불리언 (JsonToken.VALUE_TRUE 또는 JsonToken.VALUE_FALSE)
            null (JsonToken.VALUE_NULL)
        구조적 기호:
            객체 시작 (JsonToken.START_OBJECT), 객체 끝 (JsonToken.END_OBJECT)
            배열 시작 (JsonToken.START_ARRAY), 배열 끝 (JsonToken.END_ARRAY)
        구분자 토큰:
            콤마 (JsonToken.VALUE_SEPARATOR)
            콜론 (필드 이름과 값 사이에 위치, JSON 객체 내부에서 사용)
     */

    @Override
    public List<ArrayElement> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.getCurrentToken() != JsonToken.START_ARRAY) {
            throw new IOException("Expected start of array");
        }
        List<ArrayElement> elements = new ArrayList<>();
        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
            if (jsonParser.getCurrentToken() == JsonToken.START_ARRAY) {
                jsonParser.nextToken();  // Move to first integer (comma로 이동하지만 comma를 명시적으로 구분자로 사용하기 때문에 내부적으로 넘어간다.)
                int index1 = jsonParser.getIntValue();
                jsonParser.nextToken();  // Move to second integer (skip comma)
                int index2 = jsonParser.getIntValue();
                jsonParser.nextToken();  // Move to category string
                String category = jsonParser.getText();
                elements.add(new ArrayElement(index1, index2, Category.fromJson(category.toUpperCase())));
                jsonParser.nextToken();  // Skip past the end of the current array
            }
        }
        return elements;
    }


    /*
    @Override
    public List<ArrayElement> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.getCurrentToken() != JsonToken.START_ARRAY) {
            throw new IOException("Expected start of array");
        }
        List<ArrayElement> elements = new ArrayList<>();
        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
            if (jsonParser.getCurrentToken() == JsonToken.START_ARRAY) {
                int index1 = jsonParser.nextIntValue(-1); // read next integer
                int index2 = jsonParser.nextIntValue(-1); // read next integer
                jsonParser.nextToken();
                String category = jsonParser.getText(); // read next text
                elements.add(new ArrayElement(index1, index2, Category.fromJson(category)));
                jsonParser.nextToken(); // move to end of inner array
            }
        }
        return elements;
    }
    */

}
