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
        HashSet<HashSet<Fact>> SCandidatesList = SCandidates.getSCandidates(database);

        //Check in each block for every fact a∈B if there is a set S′⊆S∪{a} such that S′∈Δ.

        //for each possible S candidate
        for (HashSet<Fact> S : SCandidatesList) {
            boolean shouldAddSToDelta = true;
            //Check in each block
            for (HashSet<Fact> B : blocks) {
                //that for each fact in block
                for (Fact a : B) {
                    //there is a set S′⊆S∪{a}
                    HashSet<Fact> SUnionA = union(S, a);
                    if (!isThereSPrimThatIsSubsetOfSUnionA(delta, SUnionA)) shouldAddSToDelta=false;
                }
                //if every fact in block satisfied the condition S′⊆S∪{a} add S to delta
                if (shouldAddSToDelta) {
                    if (S.isEmpty()) return true; //if we add empty set to delta we can cancel and return answer that query is certain
                    delta.add(S);
                }
            }
        }
        return false;
    }

    private static boolean isThereSPrimThatIsSubsetOfSUnionA(HashSet<HashSet<Fact>> delta, HashSet<Fact> SUnionA){
        //check that there exists S' in delta that is subset of S∪{a}
        boolean existsSPrim = false;
        for (HashSet<Fact> SPrim : delta) {
            if (isSubSet(SPrim, SUnionA)) {
                existsSPrim = true;
                break;
            }
        }
        return existsSPrim;
    }

    public static <Fact> boolean isSubSet(HashSet<Fact> subSet, HashSet<Fact> mainSet) {
        return mainSet.containsAll(subSet);
    }

    public static HashSet<Fact> union(HashSet<Fact> S, Fact a) {
        HashSet<Fact> SUnionA = new HashSet<>();
        SUnionA.addAll(S);
        SUnionA.add(a);
        return SUnionA;
    }
}
