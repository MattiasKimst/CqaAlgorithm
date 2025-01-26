package main.CQA;

import main.data.models.Database;
import main.data.queries.Query;
import main.data.relations.Fact;
import java.util.HashSet;

public class CqaAlgorithm {

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
                            break; //we can skip looking through all facts in block because we anyway don't add this S
                        }
                        checkedSUnionA++;
                    }
                    //if every fact in block satisfied the condition S′⊆S∪{a} add S to delta
                    if (shouldAddSToDelta) {
                        if (S.isEmpty()) {
                            //if we add empty set to delta we can cancel and return the answer that query is certain
                            concludeResults(true, query, checkedSUnionA, checkedPotentialKSet, checkedBlock, addedNewKSetToDelta);
                            return true;
                        }
                        delta.set.add(S);
                        addedNewKSetToDelta++;
                        //if we added S to delta, we must iterate over all possible S sets again
                        thereMightBeMoreKSetsToAddToDelta = true;
                        break; //we added S to delta, so we don't need to continue checking if there exists another satisfying block
                    }
                    checkedBlock++;
                }
                checkedPotentialKSet++;
            }
        } while (thereMightBeMoreKSetsToAddToDelta);
        concludeResults(false, query, checkedSUnionA, checkedPotentialKSet, checkedBlock, addedNewKSetToDelta);
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

    private void concludeResults(boolean isCertain, Query query, int checkedSUnionA, int checkedPotentialKSet,
                                 int checkedBlock, int addedNewKSetToDelta) {
        checkedSUnionATotal += checkedSUnionA;
        checkedPotentialKsetTotal += checkedPotentialKSet;
        checkedBlockTotal += checkedBlock;
        addedNewKSetToDeltaTotal += addedNewKSetToDelta;

        System.out.println("Answer " + query.getQueryAnswers() + (isCertain ? " is certain." : " is not certain.")
                + " In total checked " + checkedSUnionA
                + " times if there exists suitable S prim, " + checkedPotentialKSet + " potential KSets, " + checkedBlock
                + " blocks, added " + addedNewKSetToDelta + " times a new Kset to delta" );
    }
}
