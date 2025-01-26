package main;

import main.CQA.CqaAlgorithm;
import main.data.models.Database;
import main.data.queries.Query;
import java.util.List;
import java.util.stream.Collectors;

public class FindConsistentAnswersAlgorithm {
    CqaAlgorithm cqaAlgorithm = new CqaAlgorithm();

    public List<List<String>> findConsistentAnswers(List<List<String>> answers, Database database, Query query) {
        return answers.stream()
                .filter(answer -> {
                    Query pluggedQuery = query.createWithPluggedVariables(answer);
                    return cqaAlgorithm.isQueryCertainOnGivenDatabase(database, pluggedQuery);
                })
                .collect(Collectors.toList());
    }
}
