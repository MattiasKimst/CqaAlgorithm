package main.CQA;

import main.data.models.Database;
import main.data.relations.Fact;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class IrrelevantFactsRemover {

    public static Database removeIrrelevantFacts(Database database, Delta delta) {

        List<String> primaryKeys = new ArrayList<>();
        for (HashSet<Fact> factsThatMakeTrue: delta.set) {
            for (Fact fact : factsThatMakeTrue) {
                primaryKeys.add(fact.getPrimaryKey());
            }
        }

        List<List<Fact>> relevantDatabaseContent = new ArrayList<>();
        for (List<Fact> relation : database.getDatabase()) {
            List<Fact> newRelation = new ArrayList<>();
            for (Fact fact : relation) {
                if (primaryKeys.contains(fact.getPrimaryKey())) {
                    newRelation.add(fact);
                }
            }
            relevantDatabaseContent.add(newRelation);
        }

        return new Database(relevantDatabaseContent);
    }
}
