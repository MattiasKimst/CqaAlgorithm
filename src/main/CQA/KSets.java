package main.CQA;

import main.data.relations.Fact;

import java.util.*;

//to not save all possible combinations (k-sets) to a variable which would case OutOfMemoryError, we implement iterator that
//dynamically returns next combination by calling the method next()
public class KSets implements Iterator<HashSet<Fact>> {
    private List<Fact> allFacts;  // Flattened list of all facts
    private int k;               // Maximum subset size
    private int[] indices;       // Indices for generating subsets
    private int subsetSize;      // Current size of subset being generated
    private boolean hasNext;     // Whether there are more subsets

    public void initialize(List<List<Fact>> lists, int k) {
        // Flatten the lists into a single list of all facts
        this.allFacts = new ArrayList<>();
        for (List<Fact> list : lists) {
            this.allFacts.addAll(list);
        }
        this.k = k;
        this.subsetSize = 0;
        this.indices = new int[0];
        this.hasNext = true;
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public HashSet<Fact> next() {

        // Generate the current subset
        HashSet<Fact> subset = new HashSet<>();
        for (int i : indices) {
            subset.add(allFacts.get(i));
        }

        // Update indices to prepare for the next subset
        updateIndices();

        return subset;
    }

    private void updateIndices() {
        // If we're generating subsets of size 0, move to subsets of size 1
        if (subsetSize == 0) {
            subsetSize = 1;
            indices = new int[subsetSize];
            Arrays.fill(indices, 0);
            if (subsetSize > allFacts.size() || subsetSize > k) {
                hasNext = false;
            }
            return;
        }

        // Try to increment the current indices
        int i = subsetSize - 1;
        while (i >= 0) {
            indices[i]++;
            if (indices[i] < allFacts.size() - (subsetSize - 1 - i)) {
                // Adjust following indices to be sequential
                for (int j = i + 1; j < subsetSize; j++) {
                    indices[j] = indices[j - 1] + 1;
                }
                return;
            }
            i--;
        }

        // If we cannot increment, move to subsets of the next size
        subsetSize++;
        if (subsetSize > k || subsetSize > allFacts.size()) {
            hasNext = false;
            return;
        }
        indices = new int[subsetSize];
        for (int j = 0; j < subsetSize; j++) {
            indices[j] = j;
        }
    }
}
