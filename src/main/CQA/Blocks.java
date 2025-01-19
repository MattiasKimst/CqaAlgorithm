package main.CQA;

import main.data.models.Database;
import main.data.relations.Fact;
import java.util.*;

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
