package main.CQA;

import main.data.models.Database;
import main.data.queries.Query;
import main.data.facts.Fact;
import java.util.HashSet;

import static main.CQA.RelevantFactsFinder.findRelevantFacts;

/**
 * A class containing the most central part - the CQA algorithm
 * the method isQueryCertain takes a boolean conjunctive query obtained by plugging a SELECT query answer to SELECT query
 * and returns boolean value; True if the answer is certain; False if the answer is not certain
 */
public class CqaAlgorithm {

    //counters for counting steps performed by the algorithm
    public int checkedSUnionATotal;
    public int checkedPotentialKsetTotal;
    public int checkedBlockTotal;
    public int addedNewKSetToDeltaTotal;

    public boolean isQueryCertainOnGivenDatabase(Database database, Query query) {
        int checkedSUnionA = 0;
        int checkedPotentialKSet = 0;
        int checkedBlock = 0;
        int addedNewKSetToDelta = 0;

        Delta delta = new Delta();
        Blocks blocks = new Blocks();
        KSets kSets = new KSets();

        //Step 1: Initialize set Delta with all sets S that satisfy the query
        delta.initialize(query, database);

        // Optimization step - remove irrelevant facts to avoid making unnecessary steps
        Database relevantDatabase = findRelevantFacts(database, delta);

        //Step 2: Add any set S of at most k facts to Δ if there exists a block B (i.e., a maximal set of facts
        // sharing the same key) such that for every fact a∈B there is a set S′⊆S∪{a} such that S′∈Δ

        //First find all such blocks B
        blocks.initialize(relevantDatabase);

        boolean thereMightBeMoreKSetsToAddToDelta;
        do {
            kSets.initialize(relevantDatabase.getDatabase(), query.getK());

            //flag that helps us to determine if we need to look through all set S candidates again
            thereMightBeMoreKSetsToAddToDelta = false;

            //for each possible S set
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
                        //find S∪{a}
                        HashSet<Fact> SUnionA = SetUtils.union(S, a);

                        //if fact a does not satisfy S′⊆S∪{a}
                        if (!thereExistsSPrimeThatIsSubsetOfSUnionA(delta.set, SUnionA)) {
                            //do not add that S to delta
                            shouldAddSToDelta = false;
                            //we can skip looking through all facts in block because we anyway don't add this S
                            break;
                        }
                        checkedSUnionA++;
                    }

                    //if every fact in block satisfied the condition S′⊆S∪{a} add S to delta
                    if (shouldAddSToDelta) {
                        if (S.isEmpty()) {
                            //if we add empty set to delta we can cancel and return the answer that query is certain
                            concludeResults(checkedSUnionA, checkedPotentialKSet, checkedBlock, addedNewKSetToDelta);
                            return true;
                        }
                        delta.set.add(S);
                        addedNewKSetToDelta++;

                        //if we added S to delta, we must iterate over all possible S sets again
                        thereMightBeMoreKSetsToAddToDelta = true;

                        //we added S to delta, so we don't need to continue checking if there exists another block that
                        //could satisfy the condition allowing us to add the current S candidate to delta
                        break;
                    }
                    checkedBlock++;
                }
                checkedPotentialKSet++;
            }
        } while (thereMightBeMoreKSetsToAddToDelta);

        concludeResults(checkedSUnionA, checkedPotentialKSet, checkedBlock, addedNewKSetToDelta);

        //we never reached adding empty set to delta - answer is not certain
        return false;
    }

    private boolean thereExistsSPrimeThatIsSubsetOfSUnionA(HashSet<HashSet<Fact>> delta, HashSet<Fact> SUnionA) {
        //check that there exists S' in delta that is subset of S∪{a}
        boolean existsSPrime = false;
        for (HashSet<Fact> SPrim : delta) {
            if (SetUtils.isSubSet(SPrim, SUnionA)) {
                existsSPrime = true;
                break;
            }
        }
        return existsSPrime;
    }

    //for logging and performance measuring purpose
    private void concludeResults(int checkedSUnionA, int checkedPotentialKSet, int checkedBlock,
                                 int addedNewKSetToDelta) {
        checkedSUnionATotal += checkedSUnionA;
        checkedPotentialKsetTotal += checkedPotentialKSet;
        checkedBlockTotal += checkedBlock;
        addedNewKSetToDeltaTotal += addedNewKSetToDelta;
    }
}
