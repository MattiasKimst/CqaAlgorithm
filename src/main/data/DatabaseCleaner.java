package main.data;

import main.data.models.Database;
import main.data.relations.Fact;

import java.util.*;

public class DatabaseCleaner {
    public Database cleanDatabase(Database database) {
        List<List<Fact>> factsInRelations = new ArrayList<>();

        for (List<Fact> relation : database.getDatabase()) {
            Map<String, HashSet<Fact>> factsGroupedByKey = new HashMap<>();

            // Group facts by primary key
            for (Fact fact : relation) {
                factsGroupedByKey
                        .computeIfAbsent(fact.getPrimaryKey(), k -> new HashSet<>())
                        .add(fact);
            }

            // Collect facts that are in groups of exactly one
            List<Fact> factsInCleanRelation = new ArrayList<>();
            for (Map.Entry<String, HashSet<Fact>> entry : factsGroupedByKey.entrySet()) {
                if (entry.getValue().size() == 1) {
                    factsInCleanRelation.addAll(entry.getValue());
                }
            }

            factsInRelations.add(factsInCleanRelation);
        }

        return new Database(factsInRelations);
    }
}
