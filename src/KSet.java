import java.util.HashSet;

public class KSet {

    // Method to find all sublists of maximum size k, including the empty set
    public static <Fact> HashSet<HashSet<Fact>> getAllKSets(HashSet<Fact> database) {
        HashSet<HashSet<Fact>> result = new HashSet<>();
        result.add(new HashSet<>()); // Add the empty set
        generateSublists(database, new HashSet<>(), Query.K, result);
        return result;
    }

    // Helper method to generate sublists using recursion
    private static <Fact> void generateSublists(HashSet<Fact> database, HashSet<Fact> currentSet, int k, HashSet<HashSet<Fact>> result) {
        // Add current sublist to result if its size does not exceed k
        if (!currentSet.isEmpty() && currentSet.size() <= k) {
            result.add(new HashSet<>(currentSet));
        }

        // Stop if we've reached the maximum size k for sublist
        if (currentSet.size() == k) {
            return;
        }

        // Recursive case: iterate through the database and try adding each element to the current set
        for (Fact element : database) {
            if (!currentSet.contains(element)) {  // Avoid re-adding elements
                currentSet.add(element);  // Add element to the current set
                HashSet<Fact> remainingSet = new HashSet<>(database);
                remainingSet.removeAll(currentSet); // Prepare the remaining elements
                generateSublists(remainingSet, currentSet, k, result); // Recurse with the reduced set
                currentSet.remove(element); // Backtrack to remove the element after recursion
            }
        }
    }
}
