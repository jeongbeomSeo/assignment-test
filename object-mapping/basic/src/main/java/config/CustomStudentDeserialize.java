package config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import model.inheritance.Student;

import java.io.IOException;

public class CustomStudentDeserialize extends StdDeserializer<Student> {
    public CustomStudentDeserialize() {
        this(null);
    }
    public CustomStudentDeserialize(Class<?> vc) {
        super(vc);
    }

    @Override
    public Student deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        int age = jsonNode.get("age").asInt();
        String name = jsonNode.get("name").asText();
        String address = jsonNode.get("address").asText();
        int grade = jsonNode.get("address").asInt();
        return new Student(age, name, address, grade);
    }
}
