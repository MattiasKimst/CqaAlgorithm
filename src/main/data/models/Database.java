package main.data.models;

import main.data.relations.Fact;
import java.util.List;

public class Database {
    private final List<List<Fact>> database;

    public Database(List<List<Fact>> listOfRelationFactsLists) {
        database = listOfRelationFactsLists;
    }

    public List<List<Fact>> getDatabase() {
        return database;
    }
}
