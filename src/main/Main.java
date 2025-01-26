package main;

import main.data.DataGenerator;
import main.data.queries.*;
import main.data.models.Schema;
import main.data.relations.*;
import main.data.models.Database;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static main.Logger.logQueryResults;
import static main.tests.TestCertainQuery.testCertainQuery;
import static main.tests.TestCertainQueryDuplicateFacts.testCertainQueryDuplicateFacts;
import static main.tests.TestUncertainQuery.testUncertainQuery;

public class Main {

    private static final int[] RELATION_SIZES = new int[] {100};
    private static final double[] RATES_OF_INCONSISTENCIES = new double[] {0, 0.01, 1};
    private static final int[] KEY_EQUAL_GROUPS_SIZES = new int[] {2, 3};

    private static final int NUMBER_OF_REPETIONS = 1;
    private static final double RATE_OF_INCONSISTENCIES = 0.2;
    private static final int RELATION_SIZE = 10;
    private static final int SIZE_OF_KEY_EQUAL_GROUPS = 2;
    private static final double RATE_OF_QUERY_ANSWERS_IN_DATABASE = 0.15;


    private static final Schema schema1 = new Schema(Arrays.asList(R1.class, R2.class));
    private static final Schema schema2 = new Schema(Arrays.asList(R1.class, R3.class, R2_2.class));
    private static final Schema schema3 = new Schema(Arrays.asList(R1.class, R4.class));
    private static final Schema schema4 = new Schema(Arrays.asList(R1.class, R2_3.class, R5.class));
    private static final Schema schema5 = new Schema(Arrays.asList(R1.class, R2_4.class, R5.class));
    private static final Schema schema6 = new Schema(Arrays.asList(R1.class, R2_4.class));
    private static final Schema schema7 = new Schema(Arrays.asList(R1.class, R2_4.class, R4_2.class));
    private static final Schema schema8 = new Schema(Arrays.asList(R3_2.class, R6.class, R1_2.class, R4_3.class));
    private static final Schema schema9 = new Schema(Arrays.asList(R3_2.class, R6.class, R7.class, R4_3.class));
    private static final Schema schema10 = new Schema(Arrays.asList(R3_2.class, R6.class, R1_2.class, R7_2.class));

    private static final Map<Query, Schema> schemaQueryPairs = createSchemaQueryPairs();

    public static void main(String[] args) throws Exception {
        // Running tests
        runTests();

        /*
        // Processing schema-query pairs
        for (int i = 0; i < NUMBER_OF_REPEATS; i++) {
            for (Map.Entry<Query, Schema> entry : schemaQueryPairs.entrySet()) {
                run(entry.getKey(), entry.getValue(), RELATION_SIZE, RATE_OF_INCONSISTENCIES, SIZE_OF_KEY_EQUAL_GROUPS);
            }
        }*/

        for (int relationSize : RELATION_SIZES) {
            for ( double rateOfInconsistency : RATES_OF_INCONSISTENCIES) {
                for (int keyEqualGroupSize : KEY_EQUAL_GROUPS_SIZES) {
                    for (int i = 0; i < NUMBER_OF_REPETIONS; i++) {
                        for (Map.Entry<Query, Schema> entry : schemaQueryPairs.entrySet()) {
                            run(entry.getKey(), entry.getValue(), relationSize, rateOfInconsistency, keyEqualGroupSize);
                        }
                    }
                }
            }
        }
    }

    private static void run(Query query, Schema schema, int relationSize, double rateOfInconsistency,
                            int sizeOfKeyEqualGroups) throws Exception {
        DataGenerator dataGenerator = new DataGenerator();
        FindConsistentAnswersAlgorithm findConsistentAnswersAlgorithm = new FindConsistentAnswersAlgorithm();

        Database database = dataGenerator.generateInconsistentDatabase(
                schema, rateOfInconsistency, relationSize,
                sizeOfKeyEqualGroups, query, RATE_OF_QUERY_ANSWERS_IN_DATABASE);

        List<List<String>> selectQueryResults = query.runSelectQuery(database);

        long startTime = System.nanoTime();
        List<List<String>> consistentAnswers =
                findConsistentAnswersAlgorithm.findConsistentAnswers(selectQueryResults, database, query);
        long endTime = System.nanoTime();
        long durationInMilliseconds = (endTime - startTime) / 1_000_000;

        int numberOfCleanFactsToBeGeneratedInEachRelation = dataGenerator.numberOfCleanFactsToBeGeneratedInEachRelation;
        int numberOfFactsToBeDuplicatedInEachRelation = dataGenerator.numberOfFactsToBeDuplicatedInEachRelation;
        int numberOfAnswersToBeGenerated = dataGenerator.numberOfAnswersToBeGenerated;
        int checkedSUnionATotal = findConsistentAnswersAlgorithm.cqaAlgorithm.checkedSUnionATotal;
        int checkedPotentialKsetTotal = findConsistentAnswersAlgorithm.cqaAlgorithm.checkedPotentialKsetTotal;
        int checkedBlockTotal = findConsistentAnswersAlgorithm.cqaAlgorithm.checkedBlockTotal;
        int addedNewKSetToDeltaTotal = findConsistentAnswersAlgorithm.cqaAlgorithm.addedNewKSetToDeltaTotal;

        logQueryResults(numberOfCleanFactsToBeGeneratedInEachRelation, numberOfFactsToBeDuplicatedInEachRelation,
                numberOfAnswersToBeGenerated, query, selectQueryResults, consistentAnswers, durationInMilliseconds,
                checkedSUnionATotal, checkedPotentialKsetTotal, checkedBlockTotal, addedNewKSetToDeltaTotal,
                rateOfInconsistency, sizeOfKeyEqualGroups);
    }

    private static Map<Query, Schema> createSchemaQueryPairs() {
        Map<Query, Schema> map = new LinkedHashMap<>();
        map.put(new Q1(), schema1);
        map.put(new Q2(), schema1);
        /*map.put(new Q3(), schema2);
        map.put(new Q4(), schema2);
        map.put(new Q5(), schema3);
        map.put(new Q6(), schema4);
        map.put(new Q7(), schema5);
        map.put(new Q8(), schema6);
        map.put(new Q9(), schema7);
        map.put(new Q10(), schema7);
        //map.put(new Q11(), schema6);
        map.put(new Q12(), schema8);
        map.put(new Q13(), schema9);
        map.put(new Q14(), schema10);*/
        return map;
    }

    private static void runTests() {
        System.out.println("Uncertain query test: " + (testUncertainQuery() ? "PASSED" : "FAILED") + "\n");
        System.out.println("Certain query test: " + (testCertainQuery() ? "PASSED" : "FAILED")+ "\n");
        System.out.println("Certain query with duplicate facts test: "
                + (testCertainQueryDuplicateFacts() ? "PASSED" : "FAILED")+ "\n");
    }
}