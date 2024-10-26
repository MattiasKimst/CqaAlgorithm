import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        HashSet<Fact> database = Database.populateDatabase();
        System.out.println(algorithm(database) ? "Query is certain" : "Query is not certain");
    }

    public static boolean algorithm(HashSet<Fact> database) {

        //k, number of atoms in query
        int k = 1;

        //Step 1: Initialize set Delta with all sets S that make the query true
        HashSet<HashSet<Fact>> delta = Delta.initializeDelta(database);
        System.out.println("Delta content:");
        System.out.println(delta);

        //Step 2: Add any set S of at most k facts to Δ if there exists a block B (i.e., a maximal set of facts
        // sharing the same key) such that for every fact a∈B there is a set S′⊆S∪{a} such that S′∈Δ

        //First find all such blocks B
        HashSet<HashSet<Fact>> blocks = Blocks.getBlocks(database);
        System.out.println("Blocks b: ");
        System.out.println(blocks);

        //find all possible k-sets in database
        //HashSet<HashSet<Fact>> SCand =
        HashSet<HashSet<Fact>> SCand = new HashSet<>();
        SCand.add(new HashSet<>());
        System.out.println("S candidates: ");
        System.out.println(SCand);

        //Check in each block for every fact a∈B if there is a set S′⊆S∪{a} such that S′∈Δ.
        for (HashSet<Fact> S : SCand) {
            boolean shouldAddSToDelta = true;
            for (HashSet<Fact> B : blocks) {
                for (Fact a : B) {
                    HashSet<Fact> SUnionA = union(S, a);
                    //exists S' that is subset of S∪{a}
                    boolean existsSPrim = false;
                    for (HashSet<Fact> SPrim : delta) {
                        if (isSubSet(SPrim, SUnionA)) {
                            existsSPrim = true;
                            break;
                        }
                    }
                    if (!existsSPrim) shouldAddSToDelta=false;
                }
                if (shouldAddSToDelta) {
                    if (S.isEmpty()) return true;
                    delta.add(S);
                }
            }
        }
        return false;
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