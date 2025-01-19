package main.CQA;

import main.data.relations.Fact;
import java.util.HashSet;

public class SetUtils {
    public static <Fact> boolean isSubSet(HashSet<Fact> subSet, HashSet<Fact> mainSet) {
        return mainSet.containsAll(subSet);
    }
    public static <Fact> HashSet<Fact> union(HashSet<Fact> S, Fact a) {
        HashSet<Fact> result = new HashSet<>(S);
        result.add(a);
        return result;
    }
}
