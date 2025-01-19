package main.CQA;

import main.data.models.Database;
import main.data.queries.Query;
import main.data.relations.Fact;
import java.util.HashSet;

public class CqaAlgorithm {

    public boolean isQueryCertainOnGivenDatabase(Database database, Query query) {
        Delta delta = new Delta();
        Blocks blocks = new Blocks();
        KSets kSets = new KSets();

        //Step 1: Initialize set Delta with all sets S that satisfy the query
        delta.initialize(query, database);

        //Step 2: Add any set S of at most k facts to Δ if there exists a block B (i.e., a maximal set of facts
        // sharing the same key) such that for every fact a∈B there is a set S′⊆S∪{a} such that S′∈Δ

        //First find all such blocks B
        blocks.initialize(database);

        boolean thereMightBeMoreKSetsToAddToDelta;
        do {
            kSets.initialize(database.getDatabase(), query.getK());
            thereMightBeMoreKSetsToAddToDelta = false;
            //for each possible S
            while (kSets.hasNext()) {
                HashSet<Fact> S = kSets.next();
                //if delta already contains S then skip
                if (delta.set.contains(S)) continue;
                //Check in each block B
                for (HashSet<Fact> B : blocks.set) {
                    //flag if current S should be added to Delta
                    boolean shouldAddSToDelta = true;
                    //that for each fact in block
                    for (Fact a : B) {
                        HashSet<Fact> SUnionA = SetUtils.union(S, a);
                        //if Fact a does not satisfy S′⊆S∪{a}
                        if (!thereExistsSPrimThatIsSubsetOfSUnionA(delta.set, SUnionA)) {
                            //do not add that S to delta
                            shouldAddSToDelta = false;
                        }
                    }
                    //if every fact in block satisfied the condition S′⊆S∪{a} add S to delta
                    if (shouldAddSToDelta) {
                        if (S.isEmpty()) {
                            //if we add empty set to delta we can cancel and return the answer that query is certain
                            return true;
                        }
                        delta.set.add(S);
                        //if we added S to delta, we must iterate over all possible S sets again
                        thereMightBeMoreKSetsToAddToDelta = true;
                    }
                }
            }
        } while (thereMightBeMoreKSetsToAddToDelta);
        return false;
    }

    private boolean thereExistsSPrimThatIsSubsetOfSUnionA(HashSet<HashSet<Fact>> delta, HashSet<Fact> SUnionA) {
        //check that there exists S' in delta that is subset of S∪{a}
        boolean existsSPrim = false;
        for (HashSet<Fact> SPrim : delta) {
            if (SetUtils.isSubSet(SPrim, SUnionA)) {
                existsSPrim = true;
                break;
            }
        }
        return existsSPrim;
    }
}
