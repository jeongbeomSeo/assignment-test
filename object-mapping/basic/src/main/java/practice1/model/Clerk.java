package practice1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Clerk extends Employee {
    private Long salary;

    @Override
    public String toString() {
        return super.toString() + ", Clerk(salary=" + salary + ")";
    }
}
