package main.data;

import main.data.models.Database;
import main.data.queries.Query;
import main.data.relations.Fact;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QueryAnswersInserter {

    public void addQueryAnswers(Database database, int numberOfAnswersToSatisfyQuery, Query query) throws Exception {

        List<List<Fact>> selectedFactsFromRelations = new ArrayList<>();
        for (List<Fact> relation : database.getDatabase()) {
            List<Fact> factsToMakeIntoAnswers = getRandomFacts(relation, numberOfAnswersToSatisfyQuery);
            selectedFactsFromRelations.add(factsToMakeIntoAnswers);
        }
        for (int i = 0; i < numberOfAnswersToSatisfyQuery; i++) {
            List<Fact> factsToBeMadeToAnswer = new ArrayList<>();
            for (List<Fact> relation : selectedFactsFromRelations) {
                factsToBeMadeToAnswer.add(relation.get(i));
            }
            query.makeCombinationOfFactsSatisfyQuery(factsToBeMadeToAnswer);
        }
    }

    private List<Fact> getRandomFacts(List<Fact> factsInRelation, int numberOfFactsToBeSelected) {
        List<Fact> copy = new ArrayList<>(factsInRelation);
        Collections.shuffle(copy);
        return copy.subList(0, numberOfFactsToBeSelected);
    }
}
