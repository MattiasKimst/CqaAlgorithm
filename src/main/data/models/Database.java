package main.data.models;

import main.data.facts.Fact;
import java.util.List;

/**
 * A class for representing the database
 * Consists of a list of relations, which are lists of facts
 */
public class Database {

    private final List<List<Fact>> database;

    public Database(List<List<Fact>> listOfRelationFactsLists) {
        database = listOfRelationFactsLists;
    }

    public List<List<Fact>> getDatabase() {
        return database;
    }

}
