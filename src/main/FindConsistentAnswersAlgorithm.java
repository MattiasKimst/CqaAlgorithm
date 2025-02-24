package main;

import main.CQA.CqaAlgorithm;
import main.data.DatabasePurifier;
import main.data.models.Database;
import main.data.queries.Query;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class with wrapper algorithm that iterates over a list of answers, plugs each answer into query and checks if the
 * answer is certain either on purified or original database depending on if purifyDatabase value is true or false
 */
public class FindConsistentAnswersAlgorithm {

    CqaAlgorithm cqaAlgorithm = new CqaAlgorithm();
    private final DatabasePurifier databasePurifier = new DatabasePurifier();

    public List<List<String>> findConsistentAnswers(List<List<String>> answers, Database database, Query query,
                                                    boolean purifyDatabase) {
        Database databaseToBeUsed = purifyDatabase
                ? databasePurifier.removeIrrelevantBlocksFromDatabase(database, query, false)
                : database;

        return runCqa(answers, databaseToBeUsed, query, purifyDatabase);
    }

    private List<List<String>> runCqa(List<List<String>> answers, Database database, Query query, boolean purifyDatabase) {
        return answers.parallelStream()
                .filter(answer -> isAnswerCertain(answer, database, query, purifyDatabase))
                .collect(Collectors.toList());
    }

    private boolean isAnswerCertain(List<String> answer, Database database, Query query, boolean purifyDatabase) {

        Query pluggedQuery = query.createWithPluggedVariables(answer);
        Database databaseToBeUsed = purifyDatabase
                ? databasePurifier.removeIrrelevantBlocksFromDatabase(database, pluggedQuery, true)
                : database;
        return cqaAlgorithm.isQueryCertainOnGivenDatabase(databaseToBeUsed, pluggedQuery);
    }

}
