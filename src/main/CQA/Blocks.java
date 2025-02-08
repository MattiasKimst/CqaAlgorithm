package main.CQA;

import main.data.models.Database;
import main.data.relations.Fact;
import java.util.*;

/**
 * A class containing database blocks and methods for finding the block from database
 * A block is a set of facts where all facts share the primary key
 */
public class Blocks {
    public HashSet<HashSet<Fact>> set;

    public void initialize(Database database) {
        set = new HashSet<>();
        for (List<Fact> relation : database.getDatabase()) {
            set.addAll(createBlocksFromRelation(relation));
        }
    }

    private HashSet<HashSet<Fact>> createBlocksFromRelation(List<Fact> relation) {
        Map<String, HashSet<Fact>> factsGroupedByKey = new HashMap<>();
        for (Fact fact : relation) {
            factsGroupedByKey
                    .computeIfAbsent(fact.getPrimaryKey(), k -> new HashSet<>())
                    .add(fact);
        }
        return new HashSet<>(factsGroupedByKey.values());
    }
}
