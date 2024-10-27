import java.util.HashSet;

public class Algorithm {

    public static boolean isQueryCertainOnGivenDatabase(HashSet<Fact> database) {
        //Step 1: Initialize set Delta with all sets S that make the query true
        HashSet<HashSet<Fact>> delta = Delta.initializeDelta(database);

        //Step 2: Add any set S of at most k facts to Δ if there exists a block B (i.e., a maximal set of facts
        // sharing the same key) such that for every fact a∈B there is a set S′⊆S∪{a} such that S′∈Δ

        //First find all such blocks B
        HashSet<HashSet<Fact>> blocks = Blocks.getBlocks(database);

        //find all possible k-sets in database
        HashSet<HashSet<Fact>> kSets = KSet.getAllKSets(database);

        boolean thereMightBeMoreKSetsToAddToDelta;
        do {
            thereMightBeMoreKSetsToAddToDelta = false;
            //for each possible S
            for (HashSet<Fact> S : kSets) {
                //if delta already contains S then skip
                if (Utils.containsSet(delta, S)) continue;
                //flag if current S should be added to Delta
                boolean shouldAddSToDelta = true;
                //Check in each block B
                for (HashSet<Fact> B : blocks) {
                    //that for each fact in block
                    for (Fact a : B) {
                        HashSet<Fact> SUnionA = Utils.union(S, a);
                        //if Fact a does not satisfy S′⊆S∪{a}
                        if (!isThereSPrimThatIsSubsetOfSUnionA(delta, SUnionA)) {
                            //do not add that S to delta
                            shouldAddSToDelta = false;
                        }
                    }
                    //if every fact in block satisfied the condition S′⊆S∪{a} add S to delta
                    if (shouldAddSToDelta) {
                        if (S.isEmpty())
                            //if we add empty set to delta we can cancel and return answer that query is certain
                            return true;
                        delta.add(S);
                        //if we added S to delta, we must iterate over all possible S sets again
                        thereMightBeMoreKSetsToAddToDelta = true;
                    }
                }
            }
        } while (thereMightBeMoreKSetsToAddToDelta);
        return false;
    }

    private static boolean isThereSPrimThatIsSubsetOfSUnionA(HashSet<HashSet<Fact>> delta, HashSet<Fact> SUnionA) {
        //check that there exists S' in delta that is subset of S∪{a}
        boolean existsSPrim = false;
        for (HashSet<Fact> SPrim : delta) {
            if (Utils.isSubSet(SPrim, SUnionA)) {
                existsSPrim = true;
                break;
            }
        }
        return existsSPrim;
    }
}
