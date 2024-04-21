package practice8.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Category {
    dev, module, test;

    @JsonCreator
    public static Category fromJson(String name) {
        return valueOf(name);
    }
}
