package main;

import main.data.DataGenerator;
import main.data.InconsistenciesInserter;
import main.data.queries.Q1;
import main.data.models.Schema;
import main.data.queries.Query;
import main.data.relations.R1;
import main.data.relations.R2;
import main.data.models.Database;
import java.util.Arrays;
import java.util.List;

import static main.tests.TestCertainQuery.testCertainQuery;
import static main.tests.TestCertainQueryDuplicateFacts.testCertainQueryDuplicateFacts;
import static main.tests.TestUncertainQuery.testUncertainQuery;

public class Main {

    private static int NUMBER_OF_FACTS_TO_BE_GENERATED_IN_EACH_RELATION = 95;
    private static int NUMBER_OF_FACTS_TO_BE_DUPLICATED_IN_EACH_RELATION = 5;
    private static int SIZE_OF_KEY_EQUAL_GROUPS = 2;

    private static Schema schema = new Schema(Arrays.asList(R1.class, R2.class));
    private static Query query = new Q1(); //specify here the query

    private static final DataGenerator generator = new DataGenerator();
    private static final FindConsistentAnswersAlgorithm findConsitentAnswersAlgorithm = new FindConsistentAnswersAlgorithm();

    public static void main(String[] args) throws Exception {
        Database database = generator.generateFacts(schema, NUMBER_OF_FACTS_TO_BE_GENERATED_IN_EACH_RELATION);
        InconsistenciesInserter.insertInconsistencies(database, NUMBER_OF_FACTS_TO_BE_DUPLICATED_IN_EACH_RELATION, SIZE_OF_KEY_EQUAL_GROUPS);

        System.out.println("Uncertain query test " + (testUncertainQuery() ? "PASSED" : "FAILED"));
        System.out.println("Certain query test " + (testCertainQuery() ? "PASSED" : "FAILED"));
        System.out.println("Certain query with duplicate facts test " + (testCertainQueryDuplicateFacts() ? "PASSED" : "FAILED"));

        List<List<String>> selectQueryResults = query.runSelectQuery(database);

        long startTime = System.nanoTime();
        List<List<String>> consitentAnswers = findConsitentAnswersAlgorithm.findConsistentAnswers(selectQueryResults, database, query);
        long endTime = System.nanoTime();

        long durationInMilliseconds = (endTime - startTime) / 1_000_000;
        System.out.println("The query returned " + selectQueryResults.size()
                + " out of which were certain " + consitentAnswers.size() + " answers");
        System.out.println("Time taken to find certain answers: " + durationInMilliseconds + " ms");
    }
}