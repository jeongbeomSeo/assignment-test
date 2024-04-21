package model.inheritance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Student extends Person{
    private static int STUDENT_ID = 1;
    private int grade;
    private int studentId;

    public Student() {
        this.studentId = makeStudentId();
    }

    public Student(int age, String name, String address, int grade) {
        super(age, name, address);
        this.grade = grade;
        this.studentId = STUDENT_ID++;
    }

    public static int makeStudentId() {
        return STUDENT_ID++;
    }
}
