package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import practice1.model.Clerk;
import practice1.model.Manager;
import practice2.model.Item;
import practice2.model.Response;
import practice3.model.School;
import practice4.model.University;
import practice5.model.Company;
import practice6.model.Event;
import practice6.model.LocalDateEvent;
import practice7.model.SuccessResponseDTO;
import practice8.config.CustomObjectMapper;
import practice8.model.Configuration;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/*
여기서는 JSON Object File을 요구사항에 맞게 Deserialize하는 코드를 다룹니다.
복잡한 JSON 코드 구성은 요구사항에 맞게 Customize한 ObjectMapper를 사용하도록 요구합니다.
JSON 파일 수는 11개입니니다.

각 Pratice에는 제목과 요구 사항이 제시되어 있습니다.

1. 상속 구조를 포함한 JSON
2. 제너릭을 포함한 복잡한 객체
3. 중첩 배열 구조
4. 복잡한 합성 및 상속 구조
5. 매우 복잡한 구성
6. 날짜 포맷 처리
7. 다중 계층 조직 구조
8. 복잡한 상품 카탈로그
9. 금융 트랜잭션 기록
10. 다국어 콘텐츠 관리 시스템
11. 의료 시스템 환자 정보
 */
@Slf4j
public class TrainingMain {
    private static Logger logger = LoggerFactory.getLogger(TrainingMain.class);

    public static void main(String[] args) throws IOException {

        logger.debug("Practice 1. 상속 구조를 포함한 JSON");
        practice1();
        logger.debug("Practice 2. 제너릭을 포함한 복잡한 객체");
        practice2();
        logger.debug("Practice 3. 중첩 배열 구조");
        practice3();
        logger.debug("Practice 4. 복잡한 합성 및 상속 구조");
        practice4();
        logger.debug("Practice 5. 매우 복잡한 구성");
        practice5();
        logger.debug("Practice 6. 날짜 포맷 처리");
        practice6();
        logger.debug("Practice 6_EX. 날짜 포맷 처리 확장판");
        practice6_ex();
        logger.debug("Practice 7. 중첩 제너릭 구조");
        practice7();
        logger.debug("Practice 8. 불필요한 필드는 받지 않고, Array를 곧바로 Object로 변환");
        practice8();
    }
    /*
    Pratice 1. 상속 구조를 포함한 JSON
        a. Employee 기본 클래스가 있고, Manager는 Employee를 확장합니다.
        b. Manager 클래스는 추가적으로 reports 필드(문자열 배열)를 가집니다.
    */
    private static void practice1() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File("json/practice1.json");
        Manager manager = objectMapper.readValue(file, Manager.class);
        logger.debug(manager.toString());

        File file_ex = new File("json/practice1_ex.json");
        Clerk clerk = objectMapper.readValue(file_ex, Clerk.class);
        logger.debug(clerk.toString());
    }
    /*
    Pratice 2. 제너릭을 포함한 복잡한 객체
        a. Response<T> 제너릭 클래스가 필요하며, T는 데이터 타입을 나타냅니다.
        b. 여기서 T는 Data 클래스로, Data 클래스 내에는 Item 객체의 리스트가 포함됩니다.
    */
    private static void practice2() throws IOException{
        File file = new File("json/practice2.json");

        ObjectMapper objectMapper = new ObjectMapper();
        Response<List<Item>> response = objectMapper.readValue(file, new TypeReference<>() {});

        logger.debug(response.toString());
    }
    /*
    Pratice 3. 중첩 배열 구조
        a. School 클래스 내에 Class 객체 리스트가 포함되어야 합니다.
        b. Class 객체는 grade를 숫자로, students를 Student 객체의 리스트로 가집니다.
     */
    private static void practice3() throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File("json/practice3.json");
        School school = objectMapper.readValue(file, School.class);
        log.debug(school.toString());
    }
    /*
    Pratice 4. 복잡한 합성 및 상속 구조
        a. University 클래스 내에 Department 객체의 리스트가 필요합니다.
        b. Department는 Course 객체의 리스트와 Head 객체를 가집니다.
        c. Head는 Professor 클래스를 확장하며, publications 문자열 배열을 포함합니다.
     */
    private static void practice4() throws IOException{
        File file = new File("json/practice4.json");

        ObjectMapper objectMapper = new ObjectMapper();
        University university = objectMapper.readValue(file, University.class);
        System.out.println(university.toString());
    }
    /*
    Pratice 5. 매우 복잡한 구성
        a. Company 클래스는 Department 객체의 리스트를 포함합니다.
        b. Department는 Project 객체의 리스트를 포함합니다.
        c. Project는 budget을 숫자로, team을 Employee 객체의 리스트로 포함합니다.
        d. Employee는 skills 문자열 배열을 포함합니다.
        f. Role은 Enum 객체로 Lead, Dev를 포함합니다.
     */
    private static void practice5() throws IOException{
        File file = new File("json/practice5.json");

        ObjectMapper objectMapper = new ObjectMapper();
        Company company = objectMapper.readValue(file, Company.class);
        log.debug(company.toString());
    }
    /*
    Pratice 6. 날짜 포맷 처리
        a. Event 클래스는 title을 문자열로, date를 Date 객체로 포함합니다.
        b. ObjectMapper를 사용하여 ISO8601 날짜 포맷을 Date 객체로 파싱해야 합니다.
        c. 이를 위해 ObjectMapper의 날짜 파싱 기능을 커스터마이즈 해야 합니다.
     */
    private static void practice6() throws IOException {
        File file = new File("json/practice6.json");

        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        objectMapper.setDateFormat(df);
        // ISO8601을 사용하는 경우에는 이 설정을 비활성화
        objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);

        Event event = objectMapper.readValue(file, Event.class);
        log.debug(event.toString());
    }
    /*
    Pratice 6_EX. 날짜 포맷 처리 확장판
        a. Event 클래스는 title을 문자열로, date를 Date 객체로 포함합니다.
        b. ObjectMapper를 사용하여 ISO8601 날짜 포맷을 LocalDateTime 객체로 파싱해야 합니다.
        c. 이를 위해 ObjectMapper의 날짜 파싱 기능을 커스터마이즈 해야 합니다.
     */
    public static void practice6_ex() throws IOException{
        File file = new File("json/practice6.json");

        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        // ISO8601 날짜 포맷 설정
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
        objectMapper.registerModule(javaTimeModule);

        LocalDateEvent localDateEvent = objectMapper.readValue(file, LocalDateEvent.class);
        logger.debug(localDateEvent.toString());
    }
    /*
    Pratice 7. 중첩 제너릭 타입 구조
        1. TypeReference (Jackson)
        설명
        TypeReference는 Jackson 라이브러리에서 제공하는 클래스로, 제너릭 타입을 안전하게 파싱하기 위해 사용됩니다.
        Java는 타입 소거(Type Erasure) 정책을 사용하기 때문에 런타임에 제너릭 타입 정보가 사라지는데, TypeReference를 사용하면 이 정보를 유지할 수 있습니다.

        역할
        타입 안전성 제공: JSON을 Java 객체로 변환할 때 제너릭 타입의 구체적인 정보를 유지하게 해줍니다.
        이를 통해 List<Item>, Map<String, User>와 같은 복잡한 제너릭 타입도 정확히 역직렬화할 수 있습니다.
        제너릭 타입 처리: 컬렉션, 맵 등과 같은 제너릭 컨테이너의 타입 정보를 파싱 프로세스에 전달하여 올바르게 객체를 재구성할 수 있게 돕습니다.

        2. ParameterizedTypeReference (Spring Framework)(spring.core)
        설명
        ParameterizedTypeReference는 Spring Framework에서 제공하는 클래스로, 주로 Spring의 REST 템플릿(RestTemplate)과 같은 Spring의 HTTP 통신 도구에서 제너릭 타입의 정보를 전달하기 위해 사용됩니다.
        이 클래스도 TypeReference와 비슷한 목적으로 제너릭 타입의 소거를 우회하기 위해 사용됩니다.

        역할
        HTTP 응답 처리: Spring에서 HTTP 요청을 보내고 응답을 받을 때, 응답 바디의 제너릭 타입 정보를 정확히 처리할 수 있도록 도와줍니다.
        Spring 통합: RestTemplate과 같은 Spring의 API에서 쉽게 사용할 수 있도록 설계되었습니다.
    */
    private static void practice7() throws IOException {
        File file = new File("json/practice7.json");

        ObjectMapper objectMapper = new ObjectMapper();
        Response<SuccessResponseDTO<List<practice7.model.Item>>> response = objectMapper.readValue(
                file, new TypeReference<>() {
                }
        );

        logger.debug(response.toString());
    }
    /*
    Practice 8. 불필요한 필드는 받지 않고, Array를 곧바로 Object로 변환
        a. Configuration - 메인 객체, 내부에 name, 그리고 array를 포함합니다.
        b. ArrayElement - array의 각 요소를 표현하는 객체로, 다음 세 가지 필드를 가집니다: index1, index2, category (열거형).
        c. Category - category 필드를 위한 열거형 타입

        불필요한 필드 제거 방법
        1. 객체에 @JsonIgnoreProperties 선언
        2. ObjectMapper의 설정을 objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)로 설정

        Array to Object는 array를 만났을 때 List<ArrayElement>롤 역직렬화 될 수 있도록 Customizing 요구
        practice8.config 참고
     */
    private static void practice8() throws IOException{
        File file = new File("json/practice8.json");

        ObjectMapper customObjectMapper = CustomObjectMapper.getCustomObjectMapper();
        Configuration configuration = customObjectMapper.readValue(file, Configuration.class);
        logger.debug(configuration.toString());
    }
}
