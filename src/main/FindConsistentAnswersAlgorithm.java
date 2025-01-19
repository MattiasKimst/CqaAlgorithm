package main;

import main.CQA.CqaAlgorithm;
import main.data.models.Database;
import main.data.queries.Query;
import java.util.ArrayList;
import java.util.List;

public class FindConsistentAnswersAlgorithm {

    CqaAlgorithm cqaAlgorithm = new CqaAlgorithm();

    public List<List<String>> findConsistentAnswers(List<List<String>> answers, Database database, Query query) {
        List<List<String>> consistentAnswers = new ArrayList<>();
        for (List<String> answer : answers) {
            query.plug(answer);
            if (cqaAlgorithm.isQueryCertainOnGivenDatabase(database, query)) {
                consistentAnswers.add(answer);
            }
        }
        return consistentAnswers;
    }
}
