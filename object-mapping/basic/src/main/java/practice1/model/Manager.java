package practice1.model;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Manager extends Employee{
    private List<String> reports;

    @Override
    public String toString() {
        /*
            StringBuilder sb = new StringBuilder();
            sb.append("Manager(reports=[");
            for (String report : reports) {
                sb.append(report).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length());
            sb.append("])");
        */
        String reportsString = String.join(", ", reports);
        return super.toString() + ", Manager(reports=[" + reportsString + "])";
    }
}
