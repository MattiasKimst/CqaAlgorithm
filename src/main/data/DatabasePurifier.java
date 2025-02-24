package main.data;

import main.data.facts.Fact;
import main.data.models.Database;
import main.data.queries.Query;
import java.util.*;

/**
 * A class that removes irrelevant blocks from database. A block is irrelevant if it contains at least one fact
 * that is not an answer to a query
 */
public class DatabasePurifier {
    public Database removeIrrelevantBlocksFromDatabase(Database database, Query query, boolean isPluggedQuery) {
        List<HashSet<HashSet<Fact>>> blocksOfRelations = new ArrayList<>();
        for (List<Fact> relation : database.getDatabase()) {
            blocksOfRelations.add(createBlocksFromRelation(relation));
        }
        List<HashSet<Fact>> relevantFactsInRelations = query.findRelevantFacts(database, isPluggedQuery);
        List<List<Fact>> purifiedRelations = new ArrayList<>();

        for (int i = 0; i < blocksOfRelations.size(); i++) {
            List<Fact> purifiedRelation = new ArrayList<>();
            for (HashSet<Fact> block : blocksOfRelations.get(i)) {
                boolean allFactsInBlockAreRelevant = true;
                for (Fact fact : block) {
                    if (!relevantFactsInRelations.get(i).contains(fact)) {
                        allFactsInBlockAreRelevant = false;
                        break;
                    }
                }
                if (allFactsInBlockAreRelevant) {
                    purifiedRelation.addAll(block);
                }
            }
            purifiedRelations.add(purifiedRelation);
        }
        return new Database(purifiedRelations);
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
