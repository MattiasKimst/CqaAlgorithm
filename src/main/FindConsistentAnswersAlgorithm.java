package main;

import main.CQA.CqaAlgorithm;
import main.data.DatabaseCleaner;
import main.data.models.Database;
import main.data.queries.Query;
import java.util.List;
import java.util.stream.Collectors;

public class FindConsistentAnswersAlgorithm {
    CqaAlgorithm cqaAlgorithm = new CqaAlgorithm();
    DatabaseCleaner databaseCleaner = new DatabaseCleaner();

    public List<List<String>> findConsistentAnswers(List<List<String>> answers, Database database, Query query) {
        Database cleanDatabase = databaseCleaner.cleanDatabase(database);
        System.out.println("Database cleaned ");
        List<List<String>> consistentAnswers = query.runSelectQuery(cleanDatabase);

        List<List<String>> answersToBeCheckedWithCqa = answers.parallelStream()
                .filter(answer -> !consistentAnswers.contains(answer))
                .collect(Collectors.toList());
        System.out.println("Consistent answers on clean db found");

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
