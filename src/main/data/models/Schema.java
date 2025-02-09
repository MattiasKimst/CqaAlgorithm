package main.data.models;

import java.util.List;

/**
 * A class for representing database schema
 * Represents which relations (Fact classes) the database contains
 */
public class Schema {

    private final List<Class<?>> relations;

    public Schema(List<Class<?>> relations) {
        this.relations = relations;
    }

    public List<Class<?>> getRelations() {
        return relations;
    }

}
