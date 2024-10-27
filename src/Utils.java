import java.util.HashSet;

public class Utils {
    public static <Fact> boolean isSubSet(HashSet<Fact> subSet, HashSet<Fact> mainSet) {
        return mainSet.containsAll(subSet);
    }
    public static HashSet<Fact> union(HashSet<Fact> S, Fact a) {
        HashSet<Fact> SUnionA = new HashSet<>();
        SUnionA.addAll(S);
        SUnionA.add(a);
        return SUnionA;
    }
    public static boolean containsSet(HashSet<HashSet<Fact>> setOfSets, HashSet<Fact> targetSet) {
        for (HashSet<Fact> set : setOfSets) {
            if (set.equals(targetSet)) {
                return true;
            }
        }
        return false;
    }
}
