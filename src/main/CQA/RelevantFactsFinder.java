package main.CQA;

import main.data.models.Database;
import main.data.facts.Fact;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * A helper class with a method for finding relevant facts in database
 * - the facts that satisfy the query or share primary key with facts that satisfy the query
 */
public class RelevantFactsFinder {

    public static Database findRelevantFacts(Database database, Delta delta) {

        //find primary keys of the facts that satisfy the query
        List<String> primaryKeys = new ArrayList<>();
        for (HashSet<Fact> factsThatMakeTrue: delta.set) {
            for (Fact fact : factsThatMakeTrue) {
                primaryKeys.add(fact.getPrimaryKey());
            }
        }

        //filter out relevant facts
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
