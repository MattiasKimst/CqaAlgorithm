package main.data;

import main.data.models.Database;
import main.data.facts.Fact;
import java.util.*;

/**
 * A class for removing inconsistent that is the facts that participate in any primary key violation
 * Used for an optimization step to find queries that are certain based on clean part of the database
 * without the need to run the computationally more expensive CQA algorithm
 */
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

            // Collect facts that are in groups of exactly one - meaning they don't share a primary key with other facts
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
