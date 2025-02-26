package main;

import main.data.DataGenerator;
import main.data.queries.*;
import main.data.models.Schema;
import main.data.facts.*;
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

    private static final int REPEATS = 25;
    private static final int[] CLEAN_FACTS_TO_BE_GENERATED_IN_EACH_RELATION = new int[] {9750, 9668, 9625, 9600, 9500, 9334, 9250, 9200, 9250, 9000, 8875, 8800};
    private static final int[] FACTS_TO_BE_DUPLICATED_IN_EACH_RELATION = new int[] {250, 166, 125, 100, 500, 333, 250, 200, 750, 500, 375, 300};
    private static final int[] SIZE_OF_KEY_EQUAL_GROUPS = new int[] {2, 3, 4, 5, 2, 3, 4, 5, 2, 3, 4, 5};
    private static final int[] QUERY_ANSWERS_TO_BE_INSERTED = new int[] {1500, 1500, 1500, 1500, 1500, 1500, 1500, 1500, 1500, 1500, 1500, 1500};

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

        // Processing schema-query pairs
        for (int i = 0; i < REPEATS; i++) {
            for (int j = 0; j < CLEAN_FACTS_TO_BE_GENERATED_IN_EACH_RELATION.length; j++) {
                for (Map.Entry<Query, Schema> entry : schemaQueryPairs.entrySet()) {
                    run(entry.getKey(), entry.getValue(),
                            CLEAN_FACTS_TO_BE_GENERATED_IN_EACH_RELATION[j],
                            FACTS_TO_BE_DUPLICATED_IN_EACH_RELATION[j],
                            SIZE_OF_KEY_EQUAL_GROUPS[j],
                            QUERY_ANSWERS_TO_BE_INSERTED[j]);
                }
            }
        }
    }

    private static void run(Query query, Schema schema, int cleanFactsToBeGeneratedInEachRelation,
                            int factsToBeDuplicatedInEachRelation, int sizeOfKeyEqualGroups,
                            int queryAnswersToBeInserted) throws Exception {
        DataGenerator dataGenerator = new DataGenerator();

        Database database = dataGenerator.generateInconsistentDatabase(
                schema, cleanFactsToBeGeneratedInEachRelation, factsToBeDuplicatedInEachRelation,
                sizeOfKeyEqualGroups, query, queryAnswersToBeInserted);

        long startTime = System.nanoTime();
        List<List<String>> selectQueryResults = query.runSelectQuery(database);
        long endTime = System.nanoTime();
        long durationInMillisecondsSelect = (endTime - startTime) / 1_000_000;

        findCertainAnswers(true, database, query, selectQueryResults, durationInMillisecondsSelect,
                cleanFactsToBeGeneratedInEachRelation, factsToBeDuplicatedInEachRelation, sizeOfKeyEqualGroups,
                queryAnswersToBeInserted);
        //findCertainAnswers(false, database, query, selectQueryResults, durationInMillisecondsSelect,
          //      cleanFactsToBeGeneratedInEachRelation, factsToBeDuplicatedInEachRelation, sizeOfKeyEqualGroups,
            //    queryAnswersToBeInserted);
    }


        private static void findCertainAnswers(boolean purifyDatabase, Database database, Query query,
                                               List<List<String>> selectQueryResults, long durationInMillisecondsSelect,
                                                int cleanFactsToBeGeneratedInEachRelation,
                                               int factsToBeDuplicatedInEachRelation, int sizeOfKeyEqualGroups,
                                               int queryAnswersToBeInserted) throws Exception {

            FindConsistentAnswersAlgorithm findConsistentAnswersAlgorithm = new FindConsistentAnswersAlgorithm();

            long startTime = System.nanoTime();
            List<List<String>> consistentAnswers = findConsistentAnswersAlgorithm.findConsistentAnswers(
                    selectQueryResults, database, query, purifyDatabase);
            long endTime = System.nanoTime();
            long durationInMillisecondsCqa = (endTime - startTime) / 1_000_000;

            int checkedSUnionATotal = findConsistentAnswersAlgorithm.cqaAlgorithm.checkedSUnionATotal;
            int checkedPotentialKsetTotal = findConsistentAnswersAlgorithm.cqaAlgorithm.checkedPotentialKsetTotal;
            int checkedBlockTotal = findConsistentAnswersAlgorithm.cqaAlgorithm.checkedBlockTotal;
            int addedNewKSetToDeltaTotal = findConsistentAnswersAlgorithm.cqaAlgorithm.addedNewKSetToDeltaTotal;

            logQueryResults(purifyDatabase, cleanFactsToBeGeneratedInEachRelation,
                    factsToBeDuplicatedInEachRelation, queryAnswersToBeInserted, query,
                    selectQueryResults, consistentAnswers, durationInMillisecondsSelect,
                    durationInMillisecondsCqa, checkedSUnionATotal, checkedPotentialKsetTotal,
                    checkedBlockTotal, addedNewKSetToDeltaTotal, sizeOfKeyEqualGroups);

        }


    private static Map<Query, Schema> createSchemaQueryPairs() {
        Map<Query, Schema> map = new LinkedHashMap<>();
        map.put(new Q1(), schema1);
        map.put(new Q2(), schema1);
        //map.put(new Q3(), schema2);
        //map.put(new Q4(), schema2);
        map.put(new Q5(), schema3);
        //map.put(new Q6(), schema4);
        //map.put(new Q7(), schema5);
        map.put(new Q8(), schema6);
        //map.put(new Q9(), schema7);
        //map.put(new Q10(), schema7);
        map.put(new Q11(), schema6);
        //map.put(new Q12(), schema8);
        //map.put(new Q13(), schema9);
        //map.put(new Q14(), schema10);
        return map;
    }

    private static void runTests() {
        System.out.println("Uncertain query test: " + (testUncertainQuery() ? "PASSED" : "FAILED") + "\n");
        System.out.println("Certain query test: " + (testCertainQuery() ? "PASSED" : "FAILED")+ "\n");
        System.out.println("Certain query with duplicate facts test: "
                + (testCertainQueryDuplicateFacts() ? "PASSED" : "FAILED")+ "\n");
    }

}