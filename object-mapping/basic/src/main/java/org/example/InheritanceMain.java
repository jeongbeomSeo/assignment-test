package org.example;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.databind.module.SimpleModule;
import config.CustomStudentDeserialize;
import lombok.extern.slf4j.Slf4j;
import model.inheritance.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.inheritance.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

@Slf4j
public class InheritanceMain {
    public static void main(String[] args) throws IOException {
        Logger logger = LoggerFactory.getLogger(InheritanceMain.class);

        ObjectMapper objectMapper = new ObjectMapper();

        int age = 19;
        String name = "Name";
        String address = "address";

        Person person = new Person(age, name, address);

        // 출력 결과: {"age":19,"name":"Name","address":"address"}
        logger.debug(objectMapper.writeValueAsString(person));

        // Person.json Deserialize
        File personFile = new File("src/main/java/model/inheritance/json/Person.json");
        File exPersonFile = new File("src/main/java/model/inheritance/json/ExPerson.json");
        File shortPersonFile = new File("src/main/java/model/inheritance/json/ShortPerson.json");

        Person personFromJson = objectMapper.readValue(personFile, Person.class);
        logger.debug("personFromJson.getName = {}", personFromJson.getName());

        try {
            // Fail ExPerson.json Deserialize
            Person exPersonFromJson = objectMapper.readValue(exPersonFile, Person.class);
        } catch (UnrecognizedPropertyException e) {
            // Unrecognized field "phone"
            log.debug("Person 객체에서 설정하지 않은 필드로 인한 예외 발생: UnrecognizedPropertyException");
        }

        try {
            // Success ExPerson.json Deserialize
            ObjectMapper customObjectMapper = new ObjectMapper();
            customObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            Person exPersonFromJson = customObjectMapper.readValue(exPersonFile, Person.class);
            log.debug("설정 성공: {}", exPersonFromJson.getName());
        } catch (UnrecognizedPropertyException e) {
            log.debug("Person 객체에서 설정하지 않은 필드로 인한 예외 발생: UnrecognizedPropertyException");
        }

        try {
            // Fail ShortPerson.json Deserialize
            Person shortPersonFromJson = objectMapper.readValue(shortPersonFile, Person.class);
        } catch (UnrecognizedPropertyException e) {
            log.debug("누락되어 있는 필드로 인한 예외 발생: UnrecognizedPropertyException");
        }

        try {
            // Success ShortPerson.json Deserialize
            ObjectMapper customObjectMapper = new ObjectMapper();
            customObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Person shortPersonFromJson = customObjectMapper.readValue(shortPersonFile, Person.class);

            // Person.address = null
            log.debug("Person.address = {}", shortPersonFromJson.getAddress());
        } catch (UnrecognizedPropertyException e) {
            log.debug("누락되어 있는 필드로 인한 예외 발생: UnrecognizedPropertyException");
        }


        // Student
        Student student = new Student(age, name, address, 2);
        String studentJsonFromObj = objectMapper.writeValueAsString(student);
        log.debug(studentJsonFromObj);

        File studentFile = new File("src/main/java/model/inheritance/json/Student.json");
        Student studentFromJson = objectMapper.readValue(studentFile, Student.class);

        // 예상한 값: 2, 결과 값: 2
        log.debug("예상한 값: 2, 결과 값: {}", studentFromJson.getStudentId());


        // Use Custom StudentDeserialize
        ObjectMapper customObjectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(Student.class, new CustomStudentDeserialize());
        customObjectMapper.registerModule(simpleModule);

        Student studentFromjson = customObjectMapper.readValue(studentFile, Student.class);
        log.debug("예상한 값: 3, 결과 값: {}", studentFromjson.getStudentId());
    }
}