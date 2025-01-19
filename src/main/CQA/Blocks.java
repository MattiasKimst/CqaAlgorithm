package main.CQA;

import main.data.models.Database;
import main.data.relations.Fact;
import java.util.*;

public class Blocks {
    public HashSet<HashSet<Fact>> set;

    public void initialize(Database database) {
        set = new HashSet<>();
        for (List<Fact> relation : database.getDatabase()) {
            Map<String, HashSet<Fact>> factsByPrimaryKey = new HashMap<>();
            for (Fact fact : relation) {
                factsByPrimaryKey.computeIfAbsent(fact.getPrimaryKey(), k -> new HashSet<>()).add(fact);
            }
            HashSet<HashSet<Fact>> blocksOfRelation = new HashSet<>(factsByPrimaryKey.values());
            set.addAll(blocksOfRelation);
        }
    }
}
