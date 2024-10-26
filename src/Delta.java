import java.util.HashSet;

public class Delta {
    public static HashSet<HashSet<Fact>> delta = new HashSet<>();

    public static HashSet<HashSet<Fact>> initializeDelta(HashSet<Fact> database) {
        for (Fact fact : database) {
            if (Query.query(fact)) { //if query evaluates to true with the fact, add it to delta
                HashSet<Fact> solution = new HashSet<>();
                solution.add(fact);
                delta.add(solution);
            }
        }
        return delta;
    }
}
