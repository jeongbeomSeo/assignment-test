package org.example.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfo {
    private final Integer MAX_GRADE = 9999;
    private final Integer BASE_GRADE = 0;
    private final Integer WEIGHT = 100;
    private Integer id;
    private Integer winCount;
    private Integer loseCount;
    private Integer grade;
    private Boolean isUpdated;

    public void updateWinCount() {
        this.winCount++;

        if (this.grade < MAX_GRADE) {
            this.grade = Math.min(MAX_GRADE, this.grade + Math.min(WEIGHT * winCount, 500));
            isUpdated = true;
            loseCount = 0;
        }
    }

    public void updateLoseCount() {
        this.loseCount++;

        if (this.grade > BASE_GRADE) {
            this.grade = Math.max(BASE_GRADE, this.grade - Math.min(WEIGHT * loseCount, 500));
            isUpdated = true;
            winCount = 0;
        }
    }

    public void updateGrade(Integer grade) {
        this.grade = grade;
    }

    public boolean isUpdated() {
        return this.isUpdated;
    }

    public void changeGradeUpdated() {
        this.isUpdated = false;
    }

    public void initGrade() {
        this.grade = BASE_GRADE;
        isUpdated = true;
    }
}
