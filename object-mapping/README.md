# Object Mapping

## 목표

- JSON 데이터를 Java Object로 Deserialize하는 것을 목표로 합니다.
- 복잡한 JSON 구조를 객체지향적으로 객체로 구성하는 것을 목표로 합니다.
- ObjectMapper의 사용을 숙달하는 것을 목표로 합니다.
- 주어진 요구사항에 맞게 역직렬화를 하는 것을 목표로 합니다.
- 내부적인 동작을 이해하여 Customizing할 수 있도록 노력합니다.

## 개념

- 필요한 필드만 받아서 Java 객체로 역직렬화 
  - @JsonIgnoreProperties
  - ObejctMapper Customizing: DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES = false

- 다형성
  - json의 특정 필드를 이용하여 유연하게 자식 객체로 바인딩
  - @JsonTypeInfo
  - @JsonSubTypes
  - 참고: [Employee.java](basic/src/main/java/practice1/model/Employee.java)

- Custom Deserialization
  - StdDeserializer를 상속 후 역직렬화 설정
  - 동작
    - JSON Parsing은 key값을 필드 이름 혹은 설정해둔 이름으로 매칭하면서 역직렬화를 수행
    - Custom Deserialization을 수행할 key값(필드 이름)을 만나면 커스터마이징한 역직렬화를 수행
    - 역직렬화를 수행한 후 나머지 JSON 데이터 역직렬화 수행
  - 예시 코드: [CustomStudentDeserialize.java](basic/src/main/java/config/CustomStudentDeserialize.java)

- Enum
  - ObjectMapper의 경우 기본 생성자를 이용하여 객체를 바인딩
  - Enum은 타입형 객체이므로 기본 생성자 생성이 불가
  - @JsonCreator를 이용하여 적절한 Enum 객체 바인딩
  - 동작
    - 역직렬화 수행 중 Enum 객체를 사용할 필드 이름을 만나면 역직렬화를 수행
    - Enum은 value를 Enum의 name과 매칭하여 Enum 객체를 생성한다.
      - {"role": "Manager"}로 되어 있다면, "Manager"가 name으로 선언되어 있어야 한다.
      - 필요한 경우, Custom Deserializater를 만들기
  - 예시 코드: [Role.java](basic/src/main/java/practice5/model/Role.java)

- Nested Array to Object
  - 중첩 배열로 구성되어 있는 JSON 데이터를 Java Object로 바인딩
  - 중첩되어 있는 배열로 구성되어 있기 때문에 key, value의 구분이 없다.
  - 따라서, jsonToken을 이용하여 일일히 바인딩 해주는 작업으로 진행
  - category는 Enum 객체이므로 설정해둔 @JsonCreator로 적절하게 바인딩
  - 예시 코드: [ArrayDeserializer.java](basic/src/main/java/practice8/config/ArrayDeserializer.java), [CustomObjectMapper.java](basic/src/main/java/practice8/config/CustomObjectMapper.java)

- Time Format
  - ISO8601 날짜 포맷을 Date 객체와 LocalDateTime 객체로 바인딩
  - LocalDateFormat으로 역직렬화를 할 경우, jsr310 의존성이 요구되며 JavaTimeModule을 Customizing한 후 ObjectMapper에 추가해줘야 한다.
  - 예시 코드: [TrainingMain.java의 Practice 6, EX 6](basic/src/main/java/org/example/TrainingMain.java)

- Generic
  - Generic으로 구성되어 있는 객체의 경우 TypeReference를 이용하여 Type Erasure로 인해 타입 파라미터를 넘겨받지 못하는 것을 해결
  - TypeReference의 경우, Jackson 라이브러리에서 제공하는 클래스로 제너릭 타입을 안전하게 파싱하기 위해 사용 
  - ParameterizedTypeReference의 경우, Spring Framework에서 제공하는 클래스로 RestTemplate과 사용
  - 예시 코드: [TraningMain.java의 Practice 7](basic/src/main/java/org/example/TrainingMain.java)

## 요구사항 정리

해당 요구사항은 8개로 이루어져 있으며, [TrainingMain.java](basic/src/main/java/org/example/TrainingMain.java)에서 요구사항대로 역직렬화를 연습했습니다.

1. 상속 구조를 포함한 JSON
    a. Employee 기본 클래스가 있고, Manager는 Employee를 확장합니다.
    b. Manager 클래스는 추가적으로 reports 필드(문자열 배열)를 가집니다.
2. 제너릭을 포함한 복잡한 객체
    a. Response<T> 제너릭 클래스가 필요하며, T는 데이터 타입을 나타냅니다.
    b. 여기서 T는 Data 클래스로, Data 클래스 내에는 Item 객체의 리스트가 포함됩니다.
3. 중첩 배열 구조
   a. School 클래스 내에 Class 객체 리스트가 포함되어야 합니다.
   b. Class 객체는 grade를 숫자로, students를 Student 객체의 리스트로 가집니다.
4. 복잡한 합성 및 상속 구조
   a. University 클래스 내에 Department 객체의 리스트가 필요합니다.
   b. Department는 Course 객체의 리스트와 Head 객체를 가집니다.
   c. Head는 Professor 클래스를 확장하며, publications 문자열 배열을 포함합니다.
5. 매우 복잡한 구성
   a. Company 클래스는 Department 객체의 리스트를 포함합니다.
   b. Department는 Project 객체의 리스트를 포함합니다.
   c. Project는 budget을 숫자로, team을 Employee 객체의 리스트로 포함합니다.
   d. Employee는 skills 문자열 배열을 포함합니다.
   f. Role은 Enum 객체로 Lead, Dev를 포함합니다.
6. 날짜 포맷 처리
   a. Event 클래스는 title을 문자열로, date를 Date 객체로 포함합니다.
   b. ObjectMapper를 사용하여 ISO8601 날짜 포맷을 Date 객체로 파싱해야 합니다.
   c. 이를 위해 ObjectMapper의 날짜 파싱 기능을 커스터마이즈 해야 합니다.
7. 6_EX 날짜 포맷 처리 확장판
   a. Event 클래스는 title을 문자열로, date를 Date 객체로 포함합니다.
   b. ObjectMapper를 사용하여 ISO8601 날짜 포맷을 LocalDateTime 객체로 파싱해야 합니다.
   c. 이를 위해 ObjectMapper의 날짜 파싱 기능을 커스터마이즈 해야 합니다. 
8. 중첩 제너릭 타입 구조
9. 불필요한 필드는 받지 않고, Array를 곧바로 Object로 변환
   a. Configuration - 메인 객체, 내부에 name, 그리고 array를 포함합니다.
   b. ArrayElement - array의 각 요소를 표현하는 객체로, 다음 세 가지 필드를 가집니다: index1, index2, category (열거형).
   c. Category - category 필드를 위한 열거형 타입