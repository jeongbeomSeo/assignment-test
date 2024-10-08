package org.example.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class ChangeGradeDTO {
    private Integer id;
    private Integer grade;

    @Override
    public boolean equals(Object object) {
        if (object instanceof ChangeGradeDTO) {
            ChangeGradeDTO o = (ChangeGradeDTO) object;

            return this.id.equals(o.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
