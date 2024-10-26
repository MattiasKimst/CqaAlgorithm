import java.util.*;

public class Blocks {
    public static HashSet<HashSet<Fact>> blocks;

    //finds sets of facts with same key (ID in this case)
    public static HashSet<HashSet<Fact>> getBlocks(HashSet<Fact> database) {
        Map<Integer, HashSet<Fact>> FactsById = new HashMap<>();

        for (Fact fact : database) {
            //in next line, fact.ID defines a key
            FactsById.computeIfAbsent(fact.ID, k -> new HashSet<>()).add(fact);
        }

        // Collect lists of facts grouped by the same key into a single list
        blocks = new HashSet<>(FactsById.values());

        return blocks;
    }
}
