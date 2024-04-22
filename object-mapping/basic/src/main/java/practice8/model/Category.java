package practice8.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Category {
    DEV, MODULE, TEST;

    @JsonCreator
    public static Category fromJson(String name) {
        return valueOf(name);
    }
}
