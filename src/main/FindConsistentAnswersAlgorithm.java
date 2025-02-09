package main;

import main.CQA.CqaAlgorithm;
import main.data.DatabaseCleaner;
import main.data.models.Database;
import main.data.queries.Query;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class with wrapper algorithm that
 *      1. creates a clean database copy where inconsistent facts are removed
 *      2. finds answers that are certain on clean database copy
 *      3. runs CQA for the remaining answers that were not detected to be certain in step 2. to find which of those
 *          are certain
 */
public class FindConsistentAnswersAlgorithm {

    CqaAlgorithm cqaAlgorithm = new CqaAlgorithm();
    DatabaseCleaner databaseCleaner = new DatabaseCleaner();

    public List<List<String>> findConsistentAnswers(List<List<String>> answers, Database database, Query query) {
        Database cleanDatabase = databaseCleaner.cleanDatabase(database);
        //System.out.println("Database cleaned ");
        List<List<String>> consistentAnswers = query.runSelectQuery(cleanDatabase);

        List<List<String>> answersToBeCheckedWithCqa = answers.parallelStream()
                .filter(answer -> !consistentAnswers.contains(answer))
                .collect(Collectors.toList());
        //System.out.println("Consistent answers on clean db found");

        consistentAnswers.addAll(runCqa(answersToBeCheckedWithCqa, database, query));
        return consistentAnswers;
    }

    private List<List<String>> runCqa(List<List<String>> answers, Database database, Query query) {
        return answers.parallelStream()
                .filter(answer -> {
                    Query pluggedQuery = query.createWithPluggedVariables(answer);
                    return cqaAlgorithm.isQueryCertainOnGivenDatabase(database, pluggedQuery);
                })
                .collect(Collectors.toList());
    }

}
