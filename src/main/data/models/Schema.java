package main.data.models;

import java.util.List;

public class Schema {
    private final List<Class<?>> relations;

    public Schema(List<Class<?>> relations) {
        this.relations = relations;
    }

    public List<Class<?>> getRelations() {
        return relations;
    }
}
